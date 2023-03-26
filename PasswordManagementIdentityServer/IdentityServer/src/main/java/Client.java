import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.Scanner;

public class Client {
    private int port;
    private String address;
    private final UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub  ;

    public Client(Channel channel ){
        blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(channel);
    }

    public void createUser(String loginName, String realName, String password ){
        Identity.ServerActions response = null;
        Identity.User request = Identity.User.newBuilder().setLoginName(loginName).setRealName(realName).setPassword(password).build();

        try{
            response = blockingStub.create(request);
        }
        catch (StatusRuntimeException e){
            System.out.println(e);
        }
        if(response.getAlreadyExist())
            System.out.println("User already exist");
        else
            System.out.println("New client user id is " +response.getUserID());
    }

    public void modifyLoginName(String oldName, String newLoginName, String password){
        Identity.ServerActions response = null;
        Identity.User request = Identity.User.newBuilder().setOldLoginName(oldName).setLoginName(newLoginName).setPassword(password).build();
        try{
            response = blockingStub.modify(request);
        }
        catch(StatusRuntimeException e){
            System.out.println(e);
        }
        if(response.getAlreadyExist()){
            System.out.println("Login Name already taken, Please set unique login name");
        }
        else{
            if(!response.getIsPassMatch())
                System.out.println("Password incorrect, please enter correct password");
            else
                System.out.println("New login name is" + " " +response.getLoginName());
        }
    }

    public void deleteUser(String loginName, String password){
        Identity.ServerActions response = null;
        Identity.User request = Identity.User.newBuilder().setLoginName(loginName).setPassword(password).build();
        try{
            response = blockingStub.delete(request);
        }
        catch(StatusRuntimeException e){
            System.out.println(e);
        }
        if(response.getIsDeleted()){
            if(response.getIsPassMatch())
                System.out.println("Login name has been deleted from database");
            else
                System.out.println("Password incorrect, please enter correct password");
        }
        else if(!response.getLoginName().isEmpty() || response.getLoginName().equals(loginName) )
            System.out.println("Password incorrect, please enter correct password");
        else
            System.out.println("Login name does not exist in database" );
    }

    public void lookup(String loginName){
        Identity.ServerActions response = null;
        Identity.User request = Identity.User.newBuilder().setLoginName(loginName).build();
        try{
            response = blockingStub.lookup(request);
        }
        catch(StatusRuntimeException e) {
            System.out.println(e);
        }
        if(response.getAlreadyExist())
            System.out.println("User entry of" +" " + loginName + " " +response);
        else
            System.out.println("User does not exist in database");

    }
    public void reverseLookup(int userId){
        Identity.ServerActions response = null;
        Identity.User request = Identity.User.newBuilder().setUserID(userId).build();
        try{
            response = blockingStub.reverseLookup(request);
        }
        catch(StatusRuntimeException e) {
            System.out.println(e);
        }

        if(response.getAlreadyExist())
            System.out.println("User entry of" +" " + userId + " " +response);
        else
            System.out.println("User does not exist in database");
    }
    public void listAllInfo(){
        Identity.StringResponse response = null;
        Identity.EmptyRequest request = Identity.EmptyRequest.newBuilder().build();
        try{
            response = blockingStub.listAllInfo(request);
        }
        catch(StatusRuntimeException e){
            System.out.println(e);
        }
        System.out.println(response.getInfo());
    }
    public void listLogins(){
        Identity.StringResponse response = null;
        Identity.EmptyRequest request = Identity.EmptyRequest.newBuilder().build();

        try{
            response = blockingStub.listLogins(request);
        }
        catch(StatusRuntimeException e){
            System.out.println(e);
        }
        System.out.println(response.getInfo());
    }

    public void listIds(){
        Identity.StringResponse response = null;
        Identity.EmptyRequest request = Identity.EmptyRequest.newBuilder().build();
        try{
            response = blockingStub.listIds(request);
        }
        catch(StatusRuntimeException e){
            System.out.println(e);
        }
        System.out.println(response.getInfo());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String[] userInput = scanner.nextLine().split(" ");
            if (userInput[0].equals("create")) {
                if (userInput.length < 4) {
                    System.out.println("Please provide login name , realness and password along with create");
                    continue;
                }
                createUser(userInput[1], userInput[2], userInput[3]);
            } else if (userInput[0].equals("modify")) {
                if (userInput.length < 4) {
                    System.out.println("Please provide oldLoginName , newLoginName and password along with modify");
                    continue;
                }
                modifyLoginName(userInput[1], userInput[2], userInput[3]);

            } else if(userInput[0].equals("delete")){
                if(userInput.length < 3){
                    System.out.println("Please provide login name and password along with delete");
                    continue;
                }
                deleteUser(userInput[1], userInput[2]);
            }
            else if(userInput[0].equals("lookup")){
                if(userInput.length < 2){
                    System.out.println("Please provide login name along with lookup");
                    continue;
                }
                lookup(userInput[1]);
            }
            else if(userInput[0].equals("reverseLookup")){
                if(userInput.length < 2){
                    System.out.println("Please provide user id along with reverselookup");
                    continue;
                }
                reverseLookup(Integer.parseInt(userInput[1]));
            } else if (userInput[0].equals("listLogins")) {
                listLogins();
            }
            else if (userInput[0].equals("listIds")) {
                listIds();
            }
            else if (userInput[0].equals("listAllInfo")) {
                listAllInfo();
            }
            else{
                System.out.println("Incorrect request, type help and choose correct option");
            }
        }
    }
    public static void main(String[] args){
//        String address  = "localhost";
//        int port = 5050;
        String address = args[0];
        int port = Integer.parseInt(args[1]);
        String serverAddress = String.format("%s:%s",address,port);
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serverAddress).usePlaintext().build();
        try{
            Client client = new Client(channel);
            client.run();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
