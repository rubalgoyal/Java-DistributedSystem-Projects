import java.io.*;
import java.lang.ref.Cleaner;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class RateLimitedServer {
    public int port;
    public int reqLimit;
    public long timeInterval;
    public ServerSocket serverSocket = null;
    public long startTime;
    private int numClientConnected = 0;

    public RateLimitedServer(int port, int reqLimit, long timeInterval){
        this.port = port;
        this.reqLimit = reqLimit;
        this.timeInterval = timeInterval;
    }

    public void buildServer() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            this.startTime = System.currentTimeMillis();

            System.out.println("Server Started at address "+ serverSocket.getLocalPort());

            while (true) {
                Socket serverSideSocket = serverSocket.accept();
                long elapsed = System.currentTimeMillis() - startTime;
                ++this.numClientConnected;
                System.out.println("Client # "+ this.numClientConnected +" connected at address " + serverSideSocket.getRemoteSocketAddress() + " after " + elapsed + " ms");
                ClientHandler clientHandler = new ClientHandler(serverSideSocket, timeInterval, reqLimit, numClientConnected);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }



    public static void main(String[] args) throws Exception {
        if(args.length < 3)
            throw new Exception("Please enter 3 arguments");

        int port = Integer.parseInt(args[0]);
        int reqLimit = Integer.parseInt(args[1]);
        long timeInterval = (long)Integer.parseInt(args[2]);

        RateLimitedServer server = new RateLimitedServer(port, reqLimit, timeInterval);
        server.buildServer();
    }
}
