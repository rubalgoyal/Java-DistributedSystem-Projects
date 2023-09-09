import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.NettyServerBuilder;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import io.grpc.Server;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

public class MasterServer {
    private HashMap<Integer, IdentityServer> nodes = new HashMap<>();
    private HashMap<Integer, Integer> clusterNodePorts = new HashMap<>(); // List of ports to forward writes to
    private int port;
    private String address;
    private String certiFilePath;
    private String keyFilePath;
    private int maxNodes;
    private Logger logger =  Logger.getLogger("MasterServer");
    private boolean anyCoordinatorNode = false;
    public int coordinatorPort;
    public int coordinatorNodeId;

    public MasterServer(int port, String address, String certiFilePath, String keyFilePath, int maxNodes){
        this.port = port;
        this.certiFilePath = certiFilePath;
        this.keyFilePath = keyFilePath;
        this.maxNodes = maxNodes;
        this.address = address;
    }

    public void start() throws IOException, InterruptedException {
        SslContext sslContext = SslContextBuilder.forServer(new File(certiFilePath), new File(keyFilePath))
                .applicationProtocolConfig(
                        new ApplicationProtocolConfig(
                                ApplicationProtocolConfig.Protocol.ALPN,
                                ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE,
                                ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT,
                                ApplicationProtocolNames.HTTP_2))
                .build();

        // Add ClientCommunication service with MasterServer instance as the argument.
        Server server = NettyServerBuilder.forPort(port)
                .addService(new ClientCommunicationImpl(this))
                .sslContext(sslContext)
                .build();
        server.start();

        Process redisServer = IdentityUtil.startRedisServer();
        if(redisServer != null)
            logger.info("Redis Server started in background at the default port 6379");

        // Execute a Task scheduler after the server is started to save data after every 5 mins.
        IdentityUtil.jedisSaveTaskScheduler();

        // Add a shutdown hook for redis server
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            if(redisServer != null){
                // Save the redis data before network shutdown
                IdentityUtil.jedisSaveBeforeShutDown();

                // Kill the Server Nodes running.
                killAllNodesOnExit();

                // Kill the Redis server running in the background.
                redisServer.destroy();
                logger.info("\nRedis server killed");
            }
        }
        ));

        // Create a cluster of Identity Servers.
        createClusterOfNodes();
        logger.info("Number of nodes started: " + nodes.size());
        System.out.println("\n");

        //Start election for the first time
        startElection(false);
        // Task scheduler to run after 15 mins to check if coordinator is working o/w elect a coordinator
        checkCoordinatorServerStatusReelection();
        // Functionality to add nodes or kill any existing node.
        addKillNodes();

        server.awaitTermination();
    }

    private void startElection(boolean doCheckPointing){
        logger.info("Starting election process");
        int numNodes = nodes.size();
        int maxNodeId = Collections.max(nodes.keySet());
        boolean isElected = false;

        while (!isElected){
            int highestId = -1;
            for (int nodeId : nodes.keySet()) {
                if (nodeId > highestId)
                    highestId = nodeId;
            }

            IdentityServer highestNode = nodes.get(highestId);
            try{
                ManagedChannel channel = NettyChannelBuilder.forAddress(address, highestNode.getPort()).sslContext(
                        GrpcSslContexts.forClient()
                                .trustManager(new File(certiFilePath))
                                .build()
                ).build();
                ServerCommunicationGrpc.ServerCommunicationBlockingStub stub = ServerCommunicationGrpc.newBlockingStub(channel);
                ServerCommunicationOuterClass.Ping request= ServerCommunicationOuterClass.Ping.newBuilder().setMessage("hello").build();
                ServerCommunicationOuterClass.Pong response = stub.checkStatus(request);
                channel.shutdown();

                // Send election message to higher nodes
                for(int i = highestId+1; i< maxNodeId; i++){
                    IdentityServer node = nodes.get(i);
                    if(node != null){
                        try{
                            channel = NettyChannelBuilder.forAddress(address, node.getPort()).sslContext(
                                    GrpcSslContexts.forClient()
                                            .trustManager(new File(certiFilePath))
                                            .build()
                            ).build();
                            stub = ServerCommunicationGrpc.newBlockingStub(channel);
                            stub.election(
                                        ServerCommunicationOuterClass.Election.newBuilder()
                                            .setNodeId(highestNode.getServerId())
                                            .setPort(highestNode.getPort())
                                            .build()
                            );
                            channel.shutdown();
                        }catch (IOException e){
                            logger.warning("Failed to connect to node " + node.getServerId() + ": " + e.getMessage());
                            nodes.remove(i);
                            channel.shutdown();
                        }
                    }
                }
                // Wait for a response from nodes with higher ids
                boolean gotResponse = false;
                for(int i = highestId+1; i<= maxNodeId; i++){
                    IdentityServer node = nodes.get(i);
                    if(node != null){
                        try{
                            channel = NettyChannelBuilder.forAddress(address, node.getPort()).sslContext(
                                    GrpcSslContexts.forClient()
                                            .trustManager(new File(certiFilePath))
                                            .build()
                            ).build();
                            stub = ServerCommunicationGrpc.newBlockingStub(channel);
                            ServerCommunicationOuterClass.CheckCoordinator checkResponse = stub.checkCoordinator(ServerCommunicationOuterClass.Empty.newBuilder().build());
                            if(checkResponse.getIsCoordinator()){
                                //Another node is coordinator, election failed
                                gotResponse = true;
                                channel.shutdown();
                                break;
                            }
                        } catch (IOException e){
                            logger.warning("Failed to connect to node " + node.getServerId() + ": " + e.getMessage());
                            nodes.remove(i);
                            channel.shutdown();
                        }
                    }
                }
                if(!gotResponse){
                    // This current node is the new coordinator node
                    isElected = true;
                    anyCoordinatorNode = true;
                    coordinatorPort = highestNode.getPort();
                    coordinatorNodeId = highestNode.getServerId();
                    highestNode.setCoordinator(true);
                }
            } catch (IOException e) {
                logger.warning("Failed to connect to node " + highestNode.getServerId() + ": " + e.getMessage());
                nodes.remove(highestNode.getServerId());
            }
        }

        // Update list of nodes to each of the server running.
        for(int nodeId : nodes.keySet() ){
            nodes.get(nodeId).setNodes(nodes);
        }
        if(doCheckPointing)
            // Swap the insession users on the new coordinator node.
            nodes.get(coordinatorNodeId).updateSessionUsersToCoordinator();
        logger.info("Node "+ coordinatorNodeId + " is coordinator and port is " + coordinatorPort);

    }

    private void createClusterOfNodes(){
        int randomStartPort = 50511;
        IdentityServer node;
        for(int i = 0; i < maxNodes; i++){
            if (randomStartPort == this.port)
                node = new IdentityServer(i, randomStartPort+ maxNodes + 1, this.address, this.certiFilePath, this.keyFilePath);
            else
                node = new IdentityServer(i, randomStartPort+ i, this.address, this.certiFilePath, this.keyFilePath);
            nodes.put(i, node);
            // Add node to list of ports
            clusterNodePorts.put(i, randomStartPort+ i);
        }

        for (int nodeId : nodes.keySet()) {
            new Thread(() -> {
                try {
                    nodes.get(nodeId).start();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        }
        try {
            Thread.sleep(1000);
            logger.info("Waiting for all cluster node to start in the back....Please wait");
            Thread.sleep(10000); // Pauses execution for 1 second (1000 milliseconds)
        } catch (InterruptedException e) {
            // Handle exception
        }

    }

    private void addNewNode(){
        int maxNodeId = Collections.max(nodes.keySet());
        int maxNodePort = nodes.get(maxNodeId).getPort();
        IdentityServer node = new IdentityServer(maxNodeId + 1, maxNodePort+ 1, this.address, this.certiFilePath, this.keyFilePath);
        nodes.put(maxNodeId+1, node);
        new Thread(() -> {
            try {
                node.start();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            Thread.sleep(1000);
            logger.info("Waiting for new node to start....Please wait");
        } catch (InterruptedException e) {
            // Handle exception
        }

        // Update list of nodes to each of the server running.
        for(int nodeId : nodes.keySet() ){
            nodes.get(nodeId).setNodes(nodes);
        }
        logger.info("New Node Id: " + Integer.toString(maxNodeId+1) + " added and updated to the cluster as a child.");

    }

    private void killExistingNode(int nodeId){
        if(nodes.containsKey(nodeId)){
            nodes.get(nodeId).stop();
            try {
                Thread.sleep(1000);
                logger.info("Shutting down Node Id " + nodeId + " please wait for a few moments");
            } catch (InterruptedException e) {
                // Handle exception
            }
            nodes.remove(nodeId);
            logger.info("Checking and starting election of Coordinator if necessary");
            if(nodeId == coordinatorNodeId && nodes.size() > 0)
                startElection(true);
            else{
                // Update list of nodes to each of the server running.
                for(int i : nodes.keySet() ){
                    nodes.get(i).setNodes(nodes);
                }
            }

        }
        else if (nodes.size() == 0)
            logger.warning("No Server Nodes are running to kill. Please Add Nodes before killing");
        else {
            logger.info("No such Node exists. Please choose id from below");
            for(int id : nodes.keySet())
                System.out.println("Node Id: " + id);
        }
    }

    private void addKillNodes(){
        Scanner scanner = new Scanner(System.in);
        logger.info("For adding/killing a new node, please type 'add-node'/'SIGKILL <nodeId>'  anytime below");
        while (true) {
            System.out.println("\n");
            String[] userInput =  scanner.nextLine().split(" ");
            if(userInput.length > 0 && userInput[0].equals("add-node") )
                addNewNode();
            else if (userInput.length >= 2 && userInput[0].toLowerCase().equals("sigkill"))
                killExistingNode(Integer.parseInt(userInput[1]));
            else
                logger.info("Incorrect input, please type 'add-node' or 'SIGKILL <nodeId>' for adding or killing");
        }
    }

    private void checkCoordinatorServerStatusReelection(){

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleWithFixedDelay(() -> {
            if(anyCoordinatorNode){
                try{
                    ManagedChannel channel = NettyChannelBuilder.forAddress(address, coordinatorPort).sslContext(
                            GrpcSslContexts.forClient()
                                    .trustManager(new File(certiFilePath))
                                    .build()
                    ).build();
                    ServerCommunicationGrpc.ServerCommunicationBlockingStub stub = ServerCommunicationGrpc.newBlockingStub(channel);
                    ServerCommunicationOuterClass.Ping request= ServerCommunicationOuterClass.Ping.newBuilder().setMessage("hello").build();
                    ServerCommunicationOuterClass.Pong response = stub.checkStatus(request);
                    logger.info("Coordinator server is in a good health and running fine");
                    channel.shutdown();
                } catch (IOException e){
                    logger.warning("Coordinator server failed. Starting re-election");
                    startElection(true);
                }

            }
        }, 0, 15, TimeUnit.MINUTES);

    }

    private void killAllNodesOnExit(){
        for(int nodeId : nodes.keySet()){
            nodes.get(nodeId).stop();
            logger.info("Killed Node Id: " + nodeId);
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        int port = Integer.parseInt(args[0]);
        String address = args[1];
        String certiFilePath = args[2];
        String keyFilePath = args[3];
        int maxNodes = Integer.parseInt(args[4]);
//        int port = 5051;
//        String address = "localhost";
//        String certiFilePath = "certificate.cer";
//        String keyFilePath = "mykey.pem.pkcs8";
//        int maxNodes = 4;

        MasterServer masterServer = new MasterServer(port, address, certiFilePath, keyFilePath, maxNodes);
        masterServer.start();
    }
}
