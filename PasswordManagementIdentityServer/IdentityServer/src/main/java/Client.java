import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import java.io.*;
import java.util.Scanner;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;

public class Client {
    private final int port;
    private final String address;
    private final String certiFilePath;

    private UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub = null ;

    public Client(int port, String address, String certiFilePath){
//        blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(channel);
        this.port = port;
        this.address = address;
        this.certiFilePath = certiFilePath;
    }

    public void start() {
        try {

            ManagedChannel channel = NettyChannelBuilder.forAddress(address, port).sslContext(
                    GrpcSslContexts.forClient()
                            .trustManager(new File(certiFilePath))
                            .build()
            ).build();
            blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(channel);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void createUser(String loginName, String realName, String password ){
        Identity.NewUserId response = null;
        Identity.User request = Identity.User.newBuilder().setLoginName(loginName).setRealName(realName).setPassword(password).build();

        try{
            response = blockingStub.create(request);
        }
        catch (StatusRuntimeException e){
            throw new RuntimeException(e);
        }
        if(response.getAlreadyExist())
            System.out.println("User already exist");
        else
            System.out.println("New client user id is " +response.getUserID());
    }

    public void modifyLoginName(String oldName, String newLoginName, String password){
        Identity.ModifyUser response = null;
        Identity.User request = Identity.User.newBuilder().setOldLoginName(oldName).setLoginName(newLoginName).setPassword(password).build();
        try{
            response = blockingStub.modify(request);
        }
        catch(StatusRuntimeException e){
            throw new RuntimeException(e);
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
        Identity.DeleteUser response = null;
        Identity.DeleteUserName request = Identity.DeleteUserName.newBuilder().setLoginName(loginName).setPassword(password).build();
        try{
            response = blockingStub.delete(request);
        }
        catch(StatusRuntimeException e){
            throw new RuntimeException(e);
        }
        if(response.getIsDeleted()){
            if(response.getIsPassMatch())
                System.out.println("Login name has been deleted from database");
            else
                System.out.println("Password incorrect, please enter correct password");
                System.out.println("Password incorrect, please enter correct password");
        }
        else if(!response.getLoginName().isEmpty() || response.getLoginName().equals(loginName) )
            System.out.println("Password incorrect, please enter correct password");
        else
            System.out.println("Login name does not exist in database" );
    }

    public void lookup(String loginName){
        Identity.IsUserExist response = null;
        Identity.LookUpLoginName request = Identity.LookUpLoginName.newBuilder().setLoginName(loginName).build();
        try{
            response = blockingStub.lookup(request);
        }
        catch(StatusRuntimeException e) {
            throw new RuntimeException(e);
        }
        if(response.getIsUserExist())
            System.out.println("User entry of" +" " + loginName + " exists." + "\n" + response.getPrintMessage());
        else
            System.out.println("User does not exist in database");

    }
    public void reverseLookup(int userId){
        Identity.IsUserExist response = null;
        Identity.LookUpUserId request = Identity.LookUpUserId.newBuilder().setUserID(userId).build();
        try{
            response = blockingStub.reverseLookup(request);
        }
        catch(StatusRuntimeException e) {
            throw new RuntimeException(e);
        }

        if(response.getIsUserExist())
            System.out.println("User entry of" +" " + userId + " exists." + "\n" + response.getPrintMessage());
        else
            System.out.println("User does not exist in database");
    }
    public void listAllInfo(){
        Identity.AllInfoPrint response = null;
        Identity.EmptyRequest request = Identity.EmptyRequest.newBuilder().build();
        try{
            response = blockingStub.listAllInfo(request);
        }
        catch(StatusRuntimeException e){
            throw new RuntimeException(e);
        }
        System.out.println(response.getAllInfoPrint());
    }
    public void listLogins(){
        Identity.UserLoginsPrint response = null;
        Identity.EmptyRequest request = Identity.EmptyRequest.newBuilder().build();

        try{
            response = blockingStub.listLogins(request);
        }
        catch(StatusRuntimeException e){
            throw new RuntimeException(e);
        }
        System.out.println(response.getUserLoginsPrint());
    }

    public void listIds(){
        Identity.UserIdsPrint response = null;
        Identity.EmptyRequest request = Identity.EmptyRequest.newBuilder().build();
        try{
            response = blockingStub.listIds(request);
        }
        catch(StatusRuntimeException e){
            throw new RuntimeException(e);
        }
        System.out.println(response.getUserIdsPrint());
    }

    public void getHelp(){
        Identity.HelpResponse response = null;
        Identity.EmptyRequest request = Identity.EmptyRequest.newBuilder().build();
        try{
            response = blockingStub.help(request);
        }
        catch(StatusRuntimeException e){
            throw new RuntimeException(e);
        }
        System.out.println(response.getHelpMessage());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n");
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
                try{
                    reverseLookup(Integer.parseInt(userInput[1]));
                } catch (NumberFormatException e){
                    System.out.println("Please enter integer for user ID");
                }

            } else if (userInput[0].equals("listLogins")) {
                listLogins();
            }
            else if (userInput[0].equals("listIds")) {
                listIds();
            }
            else if (userInput[0].equals("listAllInfo")) {
                listAllInfo();
            }
            else if (userInput[0].equals("help")) {
                getHelp();
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
        String certiFilePath = args[2];     //certificate.cer
        Client client = new Client(port, address, certiFilePath);
        client.start();
        System.out.println("Client Started. Please start typing below. You may begin by typing 'help' for instructions");
        client.run();
    }
}
