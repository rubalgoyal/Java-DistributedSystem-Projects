import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class IdentityServer {
    private static int port;
    private Server server;

    public IdentityServer(int port){
        this.port = port;
    }

    public void start() throws IOException, InterruptedException {
       server = ServerBuilder.forPort(port).addService(new UserAuthenticationImpl()).build();
       server.start();
       System.out.println("Server started at port "+server.getPort());
       server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
//        int port = 5050;
        int port = Integer.parseInt(args[0]);
        IdentityServer identityServer = new IdentityServer(port);
        identityServer.start();
    }
}
