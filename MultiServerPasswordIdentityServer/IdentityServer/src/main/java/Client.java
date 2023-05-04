import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import java.io.*;
import java.util.Scanner;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.xds.shaded.com.google.api.expr.v1alpha1.Expr.Ident;

public class Client {
    private final int port;
    private final String address;
    private final String certiFilePath;
    private int coordinatorPort;
    private int coordinatorNodeId;
    private ManagedChannel coordinatorChannel;
    private UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub = null ;

    public Client(int port, String address, String certiFilePath){
//        blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(channel);
        this.port = port;
        this.address = address;
        this.certiFilePath = certiFilePath;
    }

    private void start() {
        try {

            coordinatorChannel = NettyChannelBuilder.forAddress(address, coordinatorPort).sslContext(
                    GrpcSslContexts.forClient()
                            .trustManager(new File(certiFilePath))
                            .build()
            ).build();
            blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(coordinatorChannel);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    private void createUser(String loginName, String realName, String password ){
        Identity.WriteResponse.CreateUserResponse response = null;
        Identity.WriteRequest.CreateUserRequest request = Identity.WriteRequest.CreateUserRequest.newBuilder().setLoginName(loginName).setRealName(realName).setPassword(password).build();
        Identity.WriteRequest writeRequest = Identity.WriteRequest.newBuilder().setCreateUserRequest(request).build();
        
        try{
            response = blockingStub.writeRequest(writeRequest).getCreateUserResponse();
        }
        catch (StatusRuntimeException e){
            throw new RuntimeException(e);
        }
        if(response.getAlreadyExist())
            System.out.println("User already exist");
        else
            System.out.println("New client user id is " +response.getUserID());
    }

    private void modifyLoginName(String oldName, String newLoginName, String password){
        Identity.WriteResponse.ModifyUserResponse response = null;
        Identity.WriteRequest.ModifyUserRequest request = Identity.WriteRequest.ModifyUserRequest.newBuilder().setOldLoginName(oldName).setLoginName(newLoginName).setPassword(password).build();
        Identity.WriteRequest writeRequest = Identity.WriteRequest.newBuilder().setModifyUserRequest(request).build();
        
        try{
            response = blockingStub.writeRequest(writeRequest).getModifyUserResponse();
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

    private void deleteUser(String loginName, String password){
        Identity.WriteResponse.DeleteUserResponse response = null;
        Identity.WriteRequest.DeleteUserRequest request = Identity.WriteRequest.DeleteUserRequest.newBuilder().setLoginName(loginName).setPassword(password).build();
        Identity.WriteRequest writeRequest = Identity.WriteRequest.newBuilder().setDeleteUserRequest(request).build();

        try{
            response = blockingStub.writeRequest(writeRequest).getDeleteUserResponse();
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

    private void lookup(String loginName){
        Identity.ReadResponse.IsUserExistResponse response = null;
        Identity.ReadRequest.LookUpLoginNameRequest request = Identity.ReadRequest.LookUpLoginNameRequest.newBuilder().setLoginName(loginName).build();
        Identity.ReadRequest readRequest = Identity.ReadRequest.newBuilder().setLookUpLoginNameRequest(request).build();

        try{
            response = blockingStub.readRequest(readRequest).getIsUserExistResponse();
        }
        catch(StatusRuntimeException e) {
            throw new RuntimeException(e);
        }
        if(response.getIsUserExist())
            System.out.println("User entry of" +" " + loginName + " exists." + "\n" + response.getPrintMessage());
        else
            System.out.println("User does not exist in database");

    }

    private void reverseLookup(int userId){
        Identity.ReadResponse.IsUserExistResponse response = null;
        Identity.ReadRequest.LookUpUserIdRequest request = Identity.ReadRequest.LookUpUserIdRequest.newBuilder().setUserID(userId).build();
        Identity.ReadRequest readRequest = Identity.ReadRequest.newBuilder().setLookUpUserIdRequest(request).build();

        try{
            response = blockingStub.readRequest(readRequest).getIsUserExistResponse();
        }
        catch(StatusRuntimeException e) {
            throw new RuntimeException(e);
        }

        if(response.getIsUserExist())
            System.out.println("User entry of" +" " + userId + " exists." + "\n" + response.getPrintMessage());
        else
            System.out.println("User does not exist in database");
    }

    private void listAllInfo(){
        Identity.ReadResponse.AllInfoPrintResponse response = null;
        Identity.ReadRequest readRequest = Identity.ReadRequest.newBuilder().setAllInfoRequest(true).build();

        try{
            response = blockingStub.readRequest(readRequest).getAllInfoPrintResponse();
        }
        catch(StatusRuntimeException e){
            throw new RuntimeException(e);
        }
        System.out.println(response.getAllInfoPrint());
    }

    private void listLogins(){
        Identity.ReadResponse.UserLoginsPrintResponse response = null;
        Identity.ReadRequest readRequest = Identity.ReadRequest.newBuilder().setListLoginsRequest(true).build();

        try{
            response = blockingStub.readRequest(readRequest).getUserLoginsPrintResponse();
        }
        catch(StatusRuntimeException e){
            throw new RuntimeException(e);
        }
        System.out.println(response.getUserLoginsPrint());
    }

    private void listIds(){
        Identity.ReadResponse.UserIdsPrintResponse response = null;
        Identity.ReadRequest readRequest = Identity.ReadRequest.newBuilder().setListIdsRequest(true).build();

        try{
            response = blockingStub.readRequest(readRequest).getUserIdsPrintResponse();
        }
        catch(StatusRuntimeException e){
            throw new RuntimeException(e);
        }
        System.out.println(response.getUserIdsPrint());
    }

    private void getHelp(){
        Identity.ReadResponse.HelpResponse response = null;
        Identity.ReadRequest readRequest = Identity.ReadRequest.newBuilder().setHelpRequest(true).build();

        try{
            response = blockingStub.readRequest(readRequest).getHelpResponse();
        }
        catch(StatusRuntimeException e){
            throw new RuntimeException(e);
        }
        System.out.println(response.getHelpMessage());
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
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
        scanner.close();
    }

    private void getCoordinatorAddress(){
        try{
            ManagedChannel channel = NettyChannelBuilder.forAddress(address, port).sslContext(
                    GrpcSslContexts.forClient()
                            .trustManager(new File(certiFilePath))
                            .build()
            ).build();
            ClientCommunicationGrpc.ClientCommunicationBlockingStub stub = ClientCommunicationGrpc.newBlockingStub(channel);
            ClientCommunicationOuterClass.Response response = stub.getCoordinator(
                    ClientCommunicationOuterClass.Request.newBuilder().build()
            );

            this.coordinatorPort = response.getCoordinatorPort();
            this.coordinatorNodeId = response.getCoordinatorNodeId();

            System.out.println("Coordinator Node port " + coordinatorPort + " and node id " +coordinatorNodeId);
            channel.shutdown();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
//        String address  = "localhost";
//        int port = 5051;
//        String certiFilePath = "certificate.cer";

        String address = args[0];
        int port = Integer.parseInt(args[1]);
        String certiFilePath = args[2];     //certificate.cer

        Client client = new Client(port, address, certiFilePath);
        client.getCoordinatorAddress();
        client.start();
        System.out.println("Client Started. Please start typing below. You may begin by typing 'help' for instructions");
        client.run();
    }
}
