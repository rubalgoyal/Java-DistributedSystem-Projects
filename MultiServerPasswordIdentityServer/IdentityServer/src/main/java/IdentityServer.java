import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class IdentityServer {
    private int serverId;
    private boolean isCoordinator;
    private int port;
    private String address;
    private Server server;

    private String certiFilePath;
    private String keyFilePath;
    // This one has a list of the nodes. From here we can get the Child node easily.
    private HashMap<Integer, IdentityServer> nodes;
    private int coordinatorPort;
    public ConcurrentHashMap<String, UserModel> inSessionUser = new ConcurrentHashMap<>();

    public int getServerId() {
        return serverId;
    }

    public int getPort() {
        return port;
    }

    public boolean isCoordinator() {
        return isCoordinator;
    }

    public void setCoordinator(boolean coordinator) {
        isCoordinator = coordinator;
    }

    public void setCoordinatorPort(int coordinatorPort) {
        this.coordinatorPort = coordinatorPort;
    }

    public void setNodes(HashMap<Integer, IdentityServer> nodes) {
        this.nodes = nodes;
    }

    public HashMap<Integer, IdentityServer> getNodes() {
        return nodes;
    }

    public String getCertiFilePath() {
        return certiFilePath;
    }

    public String getKeyFilePath() {
        return keyFilePath;
    }

    public String getAddress() {
        return address;
    }

    public IdentityServer(int id, int port, String address, String certiFilePath, String keyFilePath){
        this.serverId = id;
        this.port = port;
        this.address = address;
        this.certiFilePath = certiFilePath;
        this.keyFilePath = keyFilePath;
        this.isCoordinator = false;
    }

    public void start() throws IOException, InterruptedException {
        try{
            // Create an SSL Context to add to the netty server.
            SslContext sslContext = SslContextBuilder.forServer(new File(certiFilePath), new File(keyFilePath))
                    .applicationProtocolConfig(
                            new ApplicationProtocolConfig(
                                    ApplicationProtocolConfig.Protocol.ALPN,
                                    ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE,
                                    ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT,
                                    ApplicationProtocolNames.HTTP_2))
                    .build();
            
//            userAuthenticationImpl = new UserAuthenticationImpl(inSessionUser, clusterNodePorts, port);
            server = NettyServerBuilder.forPort(port)
                    .addService(new UserAuthenticationImpl(this))
                    .addService(new ServerCommunicationImpl(this))
                    .sslContext(
                            sslContext
                    )
                    .build();
            server.start();
            System.out.println("Server " + serverId +" started at port "+server.getPort());

            // Add a shutdown hook for redis server
            Runtime.getRuntime().addShutdownHook(new Thread( () -> {
                updateSessionUsersToCoordinator();
            }
            ));

            server.awaitTermination();
        }catch (Exception e){
            System.out.println(e);
        }

    }
    
    public void stop(){
        if(isCoordinator)
            updateSessionUsersToCoordinator();
        server.shutdownNow();
    }

    public boolean isTerminated(){
        return server.isTerminated();
    }

    public void updateSessionUsersToCoordinator(){
        ConcurrentHashMap<String, UserModel> currentUsersOnCoordinator = new ConcurrentHashMap<>();
        for(int nodeId : nodes.keySet()){
            if(nodes.get(nodeId).inSessionUser.size() > 0){
                currentUsersOnCoordinator.putAll(nodes.get(nodeId).inSessionUser);
                nodes.get(nodeId).inSessionUser.clear();
            }
        }

        // Update it on coordinator node
        for(int nodeId : nodes.keySet()){
            if(nodes.get(nodeId).isCoordinator){
                nodes.get(nodeId).inSessionUser = currentUsersOnCoordinator;
                break;
            }
        }
        System.out.println("Checkpointing: Snapshot all the in session users to Coordinator server");

    }
}
