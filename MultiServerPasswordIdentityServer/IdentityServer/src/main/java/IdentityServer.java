import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.*;
import java.io.File;
import java.io.IOException;

public class IdentityServer {
    private static int port;
    public Server server;
    private String certiFilePath;
    private String keyFilePath;
    private boolean isAwaitTermination;

    public IdentityServer(int port, String certiFilePath, String keyFilePath, boolean isAwaitTermination){
        this.port = port;
        this.certiFilePath = certiFilePath;
        this.keyFilePath = keyFilePath;
        this.isAwaitTermination = isAwaitTermination;
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
            server = NettyServerBuilder.forPort(port)
                    .addService(new UserAuthenticationImpl())
                    .sslContext(
                            sslContext
                    )
                    .build();
            server.start();
            System.out.println("Server started at port "+server.getPort());
            Process redisServer = IdentityUtil.startRedisServer();
            if(redisServer != null)
                System.out.println("Redis Server started in background at the default port 6379");
            // Add a shutdown hook for redis server
            Runtime.getRuntime().addShutdownHook(new Thread( () -> {
                    if(redisServer != null){
                        redisServer.destroy();
                        System.out.println("\nRedis server killed");
                    }
                }
            ));
            if (isAwaitTermination)
                server.awaitTermination();
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void stop(){
        if(server != null){
            server.shutdownNow();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = Integer.parseInt(args[0]);
        String certiFilePath = args[1];
        String keyFilePath = args[2];
        boolean isAwaitTermination = Boolean.parseBoolean(args[3]);
//        int port = 5050;
//        String certiFilePath = "certificate.cer";
//        String keyFilePath = "identity.pem.pkcs8";
        IdentityServer identityServer = new IdentityServer(port, certiFilePath, keyFilePath, isAwaitTermination);
        identityServer.start();
    }
}
