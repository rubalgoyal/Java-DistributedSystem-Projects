import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class PingPongServant {
    public String name;
    private Server server;
    private Client client = null;

    public PingPongServant(String host, int port, int otherPort){
        if(checkServerRunning(host,port)){
            client = new Client(host, port);
            server = new Server(otherPort);
        }
        else if (checkServerRunning(host,otherPort)){
            client = new Client(host, otherPort);
            server = new Server(port);
        }
        else {
            System.out.println("No Server is running. Creating a new Server");
            server = new Server(port);
        }
    }
    public void runServant() throws IOException, InterruptedException {
        server.start();
        Thread.sleep(5000);
        if (client != null)
            client.start();
    }
    private class Server extends Thread{

       private ServerSocket serverSocket;
       private int port;

       public Server(int port){
           this.port = port;
           try {
               serverSocket = new ServerSocket(port);
           }

           catch (IOException e){
               System.out.println(e);
           }

       }

       public void run(){
           System.out.println("Server is running at port" + this.port);
           Socket client;
           int gameNumber = 0;
           while (true){
               try{
                   client = serverSocket.accept();
                   ServerHandler serverHandler = new ServerHandler(client, gameNumber);
                   serverHandler.play();
               }
               catch (Exception e){
                   System.out.println();
               }
           }
       }
    }

    public class Client extends Thread{
       private String host;
       private int port;

       public Client(String host, int port){
           this.host = host;
           this.port = port;
       }

       public void run() {
           System.out.println("Client is running at Port "+ this.port);
           Socket clientSideSocket = null;
           try {
               clientSideSocket = new Socket(this.host, this.port);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           ClientHandler clientHandler = new ClientHandler(clientSideSocket);
           clientHandler.play();
       }
    }

    public boolean checkServerRunning(String host, int port){
        Socket tempSocket = null;
        try{
            tempSocket = new Socket(host, port);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        int otherPort = Integer.parseInt(args[2]);
        PingPongServant pingPongServant = new PingPongServant(host, port, otherPort);
        pingPongServant.runServant();
   }
}