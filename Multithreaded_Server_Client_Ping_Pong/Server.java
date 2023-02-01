import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.util.Random;

public class Server {

    public String address;
//    public Socket serverSideSocket = null;
    public ServerSocket server = null;

    public Server() {

    }

    public Server(int port) {

        try {
            server = new ServerSocket(port);
            server.setReuseAddress(true);

            System.out.println("Waiting for client.....");
            int gameNumber = 0;
            while (true){
                Socket serverSideSocket = server.accept();
                System.out.println("Client accepted");
                gameNumber++;
                ServerClientHandler serverClientHandler = new ServerClientHandler(serverSideSocket, gameNumber);

                new Thread(serverClientHandler).start();
            }


        }
        catch (IOException i) {
            System.out.println(i);
        }


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
