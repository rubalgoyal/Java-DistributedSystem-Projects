import io.grpc.netty.NettyServerBuilder;
import java.util.logging.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import io.grpc.Server;

public class CoordinatorServer {
    private HashMap<Integer, String> dns = new HashMap<>();
    private HashMap<Integer, IdentityServer> nodes = new HashMap<>();
    private int port;
    private String certiFilePath;
    private String keyFilePath;
    private int maxNodes;
    private Logger logger =  Logger.getLogger("Server");
    private boolean anyCoordinatorNode = false;

    public CoordinatorServer(int port, String certiFilePath, String keyFilePath, int maxNodes){
        this.port = port;
        this.certiFilePath = certiFilePath;
        this.keyFilePath = keyFilePath;
        this.maxNodes = maxNodes;
    }

    public void start() throws IOException, InterruptedException {
        Server server = NettyServerBuilder.forPort(port)
                .addService(new GetCoordinatorImpl())
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
                IdentityUtil.jedisSaveBeforeShutDown();
                redisServer.destroy();
                logger.info("\nRedis server killed");
            }
        }
        ));

        createClusterOfNodes();
        logger.info("Number of nodes started: " + nodes.size());
        server.awaitTermination();
    }

    private void startElection(){
        int numNodes = nodes.size();
        int maxNodeId = Collections.max(nodes.keySet());
        boolean isElected = false;

        while (!isElected){
            //TODO: Need to implement this part.
        }
    }

    private void createClusterOfNodes(){
        int randomStartPort = 5050;
        for(int i = 0; i < maxNodes; i++){
            if (randomStartPort == this.port){
                if (i != 0)
                    --i;
                continue;
            }
            IdentityServer node = new IdentityServer(i, randomStartPort+ i, this.certiFilePath, this.keyFilePath);
            nodes.put(i, node);
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        //TODO: Need to uncomment this part to read from command line.
//        int port = Integer.parseInt(args[0]);
//        String certiFilePath = args[1];
//        String keyFilePath = args[2];
//        int maxNodes = 3;
//        if (args.length == 4)
//            maxNodes = Integer.parseInt(args[3]);

        int port = 5051;
        String certiFilePath = "certificate.cer";
        String keyFilePath = "mykey.pem.pkcs8";
        int maxNodes = 5;

        CoordinatorServer coordinatorServer = new CoordinatorServer(port, certiFilePath, keyFilePath, maxNodes);
        coordinatorServer.start();

    }
}
