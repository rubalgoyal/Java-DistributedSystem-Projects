import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class IdentityServer {
    public int serverId;
    public boolean isCoordinator;
    private static int port;
    public Server server;
    private String certiFilePath;
    private String keyFilePath;
    ConcurrentHashMap<String, UserModel> inSessionUser = new ConcurrentHashMap<>();

    public IdentityServer(int id, int port, String certiFilePath, String keyFilePath){
        this.serverId = id;
        this.port = port;
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
            server = NettyServerBuilder.forPort(port)
                    .addService(new UserAuthenticationImpl(inSessionUser))
                    .sslContext(
                            sslContext
                    )
                    .build();
            server.start();
            System.out.println("Server started at port "+server.getPort());

            server.awaitTermination();
        }catch (Exception e){
            System.out.println(e);
        }

    }

}
