import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private ConcurrentLinkedQueue<String> requests = new ConcurrentLinkedQueue<String>();
    private long timeInterval;
    private long startTime;
    private int reqLimit;
    private int clientNumber;
    private long reqTimeInterval = 0;
    private int reqProcessed = 0;

    public ClientHandler(Socket clientSocket, long timeInterval, int reqLimit, int clientNumber){
        this.clientSocket = clientSocket;
        this.timeInterval = timeInterval;
        this.startTime = System.currentTimeMillis();
        this.reqLimit = reqLimit;
        this.clientNumber = clientNumber;
    }

    public void run(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            try {
                receiveClientRequests();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.submit(() -> {
            try {
                sendResponse();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        executorService.shutdown();
    }

    private void receiveClientRequests() throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        while (true){
            String requestFromClient = reader.readLine();
            requests.add(requestFromClient);
        }
    }

    private void sendResponse() throws IOException, InterruptedException {
        PrintWriter printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
        System.out.println("processing requests");
        reqTimeInterval = System.currentTimeMillis();
        while (true){
            // If Time Window elapsed, reset both time window and req processed
            if (System.currentTimeMillis() - reqTimeInterval > timeInterval){
                reqTimeInterval = System.currentTimeMillis();
                reqProcessed = 0;
                Thread.sleep(timeInterval);
            }

            // If request limit reached, then reset the limit and sleep for rest of the remaining time + 1 sec
            if(reqProcessed >= reqLimit){
                Thread.sleep(timeInterval + 1000 - (System.currentTimeMillis() - reqTimeInterval));
                reqTimeInterval = System.currentTimeMillis();
                reqProcessed = 0;
            }

            if(requests.isEmpty())
                continue;

            ++reqProcessed;
            String requestFromClient = requests.remove();
            long elapsedTime = System.currentTimeMillis() - startTime;
            String sendResponse = requestFromClient + ", server elapsed times is: " + elapsedTime;
            printWriter.println(sendResponse);
            String message = String.format("Responding to client#"+ this.clientNumber +  " with elapsed time %s", elapsedTime);
            System.out.println(message);
        }

    }


}
