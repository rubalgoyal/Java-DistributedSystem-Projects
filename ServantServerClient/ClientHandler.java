import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ClientHandler {
    private ObjectInputStream objectInput = null;
    private ObjectOutputStream objectOutput = null;
    private Socket clientSideSocket;

    public ClientHandler(Socket clientSideSocket){
        this.clientSideSocket = clientSideSocket;
    }

    public void play(){
        Ball b = new Ball();
        try{

            objectOutput = new ObjectOutputStream(clientSideSocket.getOutputStream());
            objectInput = new ObjectInputStream(new BufferedInputStream(clientSideSocket.getInputStream()));

            b.tossValue = generateRandomNumber();
            objectOutput.writeObject(b);
            while(true){
                b =  (Ball) objectInput.readObject();
                if(b.tossResult.equals("Tie")){
                    b.tossValue = generateRandomNumber();
                    objectOutput.writeObject(b);
                }
                else {
                    System.out.println(b.tossResult +" won the toss");
                    break;
                }

            }

            if(b.tossResult.equals("Client")){
                b.message = "Ping";
                objectOutput.writeObject(b);
            }
            else {
                b.message = null;
                objectOutput.writeObject(b);
            }

            while (true){
                if(b.tossResult.equals("Client")){
                    b = (Ball) objectInput.readObject();
                    if(b.message != null) System.out.println("CLIENT received ->" + b.message);
                    b.message = "Ping";
                    objectOutput.writeObject(b);
                }
                else{
                    b = (Ball) objectInput.readObject();
                    if(b.message != null) System.out.println("CLIENT received ->" + b.message);
                    b.message = "Pong";
                    objectOutput.writeObject(b);
                }
                TimeUnit.SECONDS.sleep(2);
            }
        }
        catch(Exception i){
            throw new RuntimeException(i);
        }
    }
    public int generateRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(2);
    }

}
