import java.io.*;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Client {
    private final int PORT;
    private final String ADDRESS;
    private final int NUM_REQUEST;
    private final long DELAY;
    private long startTime;


    public Client(String address, int port, int numReq, long delay){
        this.PORT = port;
        this.ADDRESS = address;
        this.NUM_REQUEST = numReq;
        this.DELAY = delay;
    }

    public void run() throws ExecutionException, InterruptedException, IOException {
        Socket socket = new Socket(ADDRESS, PORT);
        if (socket.isConnected())
            System.out.println("Connection established at port "+ socket.getLocalPort());
        startTime = System.currentTimeMillis();
        try {
            for (int i = 0; i < NUM_REQUEST; i++) {
                int finalI = i+1;
                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(
                        () -> {
                            try {
                                sendRequest(finalI, socket);
                                Thread.sleep(DELAY, TimeUnit.MILLISECONDS.ordinal());
                            } catch (IOException  e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
                completableFuture.get();
            }
        } finally {
            socket.close();
        }
    }


    private void sendRequest(int reqNum, Socket socket) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println("Request number from Client is " + reqNum);
        long elapsedTime = System.currentTimeMillis() - startTime;
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();
        System.out.println(response);
    }


    public static void main(String[] args) throws Exception {
        if(args.length < 4)
            throw new Exception("Please enter 4 arguments");
        String address = args[0];
        int port = Integer.parseInt(args[1]);
        int numRequest = Integer.parseInt(args[2]);
        long delay = (long)Integer.parseInt(args[1]);

        Client client = new Client(address, port, numRequest, delay);
        client.run();
    }

}
