import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.util.Random;

public class Server {
    public int port = 5050;
    public String address;
    public Socket serverSideSocket = null;

    public ServerSocket server = null;

    public Server() {

    }

    public Server(int port) {

        try {
            server = new ServerSocket(port);
            System.out.println("Waiting for client.....");
            serverSideSocket = server.accept();
            System.out.println("Client accepted");

            Ball b;

            ObjectInputStream objectInput = new ObjectInputStream(serverSideSocket.getInputStream());
            ObjectOutputStream objectOutput = new ObjectOutputStream(serverSideSocket.getOutputStream());

            while(true) {
                String result = null;
                int serverTossValue = generateRandomNumber();
                b = (Ball) objectInput.readObject();

                if (b.tossValue < serverTossValue) {
                    result = "Client";
                } else if (serverTossValue < b.tossValue) {
                    result = "Server";

                } else {
                    result = "Tie";
                }

                if (result.equals("Client") || result.equals("Server")){
                    b.tossResult = result;
                    System.out.println(b.tossResult +" won the toss");
                    objectOutput.writeObject(b);
                    break;
                }
                else{
                    b.tossResult = result;
                    objectOutput.writeObject(b);
                    continue;
                }
            }


            int counter = 0;
            while (true){
                if (counter > 10) break;
                if(b.tossResult.equals("Server")){
                    b = (Ball) objectInput.readObject();
                    if(b.message != null) System.out.println(b.message);
                    b.message = "Ping";
                    objectOutput.writeObject(b);
                }
                else{
                    b = (Ball) objectInput.readObject();
                    if(b.message != null) System.out.println(b.message);
                    b.message = "Pong";
                    objectOutput.writeObject(b);
                }
                counter++;
            }


        }
        catch (IOException i) {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public int generateRandomNumber(){
        Random rand = new Random();
        return rand.nextInt(2);
    }

    public static void main(String[] args) {
        Server server = new Server(5050);
    }
}




//
//
////
////
////    public Server() throws IOException {
////    }
//}
