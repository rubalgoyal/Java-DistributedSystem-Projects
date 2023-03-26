import io.grpc.ServerBuilder;
import io.grpc.Server;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ChatServer {
    private static final Logger logger = Logger.getLogger(ChatServer.class.getName());
    private static int port;
    private Server server;

    private long idleTime ;

    public ChatServer(int port, long idleTime){

        this.port = port;
        this.idleTime = idleTime;
    }

    public void start() throws IOException, InterruptedException {
        server = ServerBuilder.forPort(port)
                .addService(new ChatServerImpl())
                .build();

        server.start();
        logger.info("Server started , listening on "  + port);
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 5050;
        long idleTime = 30;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);
        if(args.length >=2)
            idleTime = (long)Integer.parseInt(args[1]);

        ChatServer chatServer = new ChatServer(port, idleTime);
        chatServer.start();
    }

}
