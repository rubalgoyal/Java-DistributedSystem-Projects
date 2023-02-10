import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ServerHandler {
    private Socket clientSocket;
    private int gameNumber;

    private Ball b;
    public ServerHandler(Socket incomingClientSocket, int gameNumber){
        this.clientSocket = incomingClientSocket;
        this.gameNumber = gameNumber;
    }

    public void play() {
        try {

            ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());

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
                    System.out.println("Game Number: " +this.gameNumber + "-> "+ b.tossResult +" won the toss");
                    objectOutput.writeObject(b);
                    break;
                }
                else{
                    b.tossResult = result;
                    objectOutput.writeObject(b);
                    continue;
                }
            }


            while (true){

                if(b.tossResult.equals("Server")){
                    b = (Ball) objectInput.readObject();
                    if(b.message != null) System.out.println("Game Number: " + this.gameNumber + "-> "+ b.message);
                    b.message = "Ping";
                    objectOutput.writeObject(b);
                }
                else{
                    b = (Ball) objectInput.readObject();
                    if(b.message != null) System.out.println("Game Number: " + this.gameNumber + "-> "+ b.message);
                    b.message = "Pong";
                    objectOutput.writeObject(b);
                }
                TimeUnit.SECONDS.sleep(2);
            }


        }
        catch (Exception i) {
            System.out.println(i);
        }

    }

    public int generateRandomNumber(){
        Random rand = new Random();
        return rand.nextInt(2);
    }
}


