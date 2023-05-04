import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.checkerframework.checker.signature.qual.CanonicalNameOrEmpty;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.net.ssl.SSLException;

public class UserAuthenticationImpl extends UserAuthenticationServiceGrpc.UserAuthenticationServiceImplBase {

    private IdentityServer identityServer;
    private Logger logger =  Logger.getLogger("");
    private ArrayList<String> lockUserIds = new ArrayList<String>(); // Set of users currently being written to, block concurrent writes

    public UserAuthenticationImpl(){}


    public UserAuthenticationImpl(IdentityServer identityServer){
        this.identityServer = identityServer;
    }

    private UserModel createUserId(String loginName, String realName, String password) throws NoSuchAlgorithmException {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        int nextId = IdentityUtil.getLastUserId();
        UserModel newUser = new UserModel(nextId,loginName, realName, password,null, date,false, null);
        String passwordHash = IdentityUtil.generateHash(password, newUser);
        newUser.setPassword(passwordHash);

        identityServer.inSessionUser.put(String.valueOf(newUser.getUserId()), newUser);
        boolean isSuccessWrite = false;
        while (!isSuccessWrite)
            isSuccessWrite =  IdentityUtil.bulkInsert(identityServer.inSessionUser, true);

        identityServer.inSessionUser.remove(String.valueOf(newUser.getUserId()));
        return newUser;
    }
    

    public Identity.WriteResponse.CreateUserResponse create(Identity.WriteRequest.CreateUserRequest request)  {
        String loginName = request.getLoginName();
        String realName = request.getRealName();
        String password = request.getPassword();
        try {
            if(IdentityUtil.isUserExist(loginName)){
                System.out.println("User already exist");
                return Identity.WriteResponse.CreateUserResponse.newBuilder().setAlreadyExist(true).build();
            }
            else{
                UserModel users = createUserId(loginName, realName, password);
                return Identity.WriteResponse.CreateUserResponse.newBuilder().setUserID(users.getUserId()).build();
            }
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    

    public Identity.WriteResponse.ModifyUserResponse modify(Identity.WriteRequest.ModifyUserRequest request) {
        String oldLoginName = request.getOldLoginName();
        String newLoginName = request.getLoginName();
        String password = request.getPassword();

        if(IdentityUtil.isUserExist(oldLoginName)){
            if(!IdentityUtil.isUserExist(newLoginName)){
                UserModel nuser = IdentityUtil.returnUser(oldLoginName);

                if(IdentityUtil.isPasswordMatch(nuser, password)){
                    nuser.setOldLoginName(oldLoginName);
                    nuser.setLoginName(newLoginName);
                    IdentityUtil.insertUser(nuser, String.valueOf(nuser.getUserId()), false);
                    return Identity.WriteResponse.ModifyUserResponse.newBuilder().setAlreadyExist(false).setIsPassMatch(true).setLoginName(nuser.getLoginName()).build();
                }
                else{
                    return Identity.WriteResponse.ModifyUserResponse.newBuilder().setAlreadyExist(false).setIsPassMatch(false).build();
                }
            }
            else{
                return Identity.WriteResponse.ModifyUserResponse.newBuilder().setAlreadyExist(true).build();
            }
        }
        else{
            return Identity.WriteResponse.ModifyUserResponse.newBuilder().setAlreadyExist(false).build();
        }
    }


    public Identity.WriteResponse.DeleteUserResponse delete(Identity.WriteRequest.DeleteUserRequest request) {
        String loginName = request.getLoginName();
        String password = request.getPassword();

        UserModel nuser = IdentityUtil.returnUser(loginName);
        if(nuser != null && IdentityUtil.isPasswordMatch(nuser, password))
            return Identity.WriteResponse.DeleteUserResponse.newBuilder().setIsDeleted(IdentityUtil.deleteUser(loginName)).setIsPassMatch(true).build();
        else if(nuser != null)
            return Identity.WriteResponse.DeleteUserResponse.newBuilder().setLoginName(loginName).build();
        else
            return Identity.WriteResponse.DeleteUserResponse.newBuilder().setIsPassMatch(false).build();
    }


    public Identity.ReadResponse.IsUserExistResponse lookup(Identity.ReadRequest.LookUpLoginNameRequest request) {
        String loginName = request.getLoginName();
        if(IdentityUtil.isUserExist(loginName)){
            UserModel user = IdentityUtil.returnUser(loginName);
            String userString = IdentityUtil.convertUserToString(user);
            return Identity.ReadResponse.IsUserExistResponse.newBuilder().setIsUserExist(true).setPrintMessage(userString).build();
        }
        else {
            return Identity.ReadResponse.IsUserExistResponse.newBuilder().setIsUserExist(false).build();
        }
    }


    public Identity.ReadResponse.IsUserExistResponse reverseLookup(Identity.ReadRequest.LookUpUserIdRequest request){
        int userId = request.getUserID();
        if(IdentityUtil.isUserExist(userId)){
            UserModel user = IdentityUtil.returnUser(userId);
            String userString = IdentityUtil.convertUserToString(user);
            return Identity.ReadResponse.IsUserExistResponse.newBuilder().setIsUserExist(true).setPrintMessage(userString).build();
        }
        else {
            return Identity.ReadResponse.IsUserExistResponse.newBuilder().setIsUserExist(false).build();
        }
    }


    public Identity.ReadResponse.UserLoginsPrintResponse listLogins(){
        ConcurrentHashMap<String, UserModel> allUsers = IdentityUtil.fetchAllUsers();
        StringBuilder stringBuilder = new StringBuilder();
        if(allUsers.isEmpty())
            stringBuilder.append("There is no user in database");
        else{
            stringBuilder.append("Following login names exist in database \n");
            allUsers.forEach((key, value) ->{
                stringBuilder.append(value.getLoginName() + "\n");
                    }
                );
        }
        return Identity.ReadResponse.UserLoginsPrintResponse.newBuilder().setUserLoginsPrint(stringBuilder.toString()).build();
    }

    public Identity.ReadResponse.UserIdsPrintResponse listIds(){
        ConcurrentHashMap<String, UserModel> allUsers = IdentityUtil.fetchAllUsers();
        StringBuilder stringBuilder = new StringBuilder();
        if(allUsers.isEmpty())
            stringBuilder.append("There is no user in database");
        else{
            stringBuilder.append("Following user id's exist in database \n");
            allUsers.forEach((key, value) ->{
                        stringBuilder.append(value.getUserId() + "\n");
                    }
            );
        }
        return Identity.ReadResponse.UserIdsPrintResponse.newBuilder().setUserIdsPrint(stringBuilder.toString()).build();
    }

    public Identity.ReadResponse.AllInfoPrintResponse listAllInfo() {
        ConcurrentHashMap<String, UserModel> allUsers = IdentityUtil.fetchAllUsers();
        StringBuilder stringBuilder = new StringBuilder();
        if (allUsers.isEmpty())
            stringBuilder.append("There is no user in database");
        else {
            stringBuilder.append("Following is the user's info \n");
            stringBuilder.append("User Id\t\tLogin Name\t\tReal Name\t\tOld Login Name\t\tCreated On\n");
            stringBuilder.append(new String(new char[150]).replace("\0", "-")+"\n");
            allUsers.forEach((key, value) -> {
                        stringBuilder.append(value.getUserId() + "\t\t"
                                + value.getLoginName() + "\t\t"
                                + value.getRealName() + "\t\t"
                                + value.getOldLoginName() + "\t\t"
                                + value.getCreatedOn() + "\t\t"
                                +"\n"
                        );
                    }
            );
        }
        return Identity.ReadResponse.AllInfoPrintResponse.newBuilder().setAllInfoPrint(stringBuilder.toString()).build();
    }

    private String showHelp(){
        StringBuilder printMessage = new StringBuilder();
        String seperator = "-";
        printMessage.append("Please enter one of the following option along with suitable parameter\n");
        printMessage.append("Please mention your values at places <> \n");
        printMessage.append(seperator.repeat(50));
        printMessage.append("\ncreate <loginName> <realName> <password> [To create a new user]\n");
        printMessage.append("modify <oldLoginName> <newLoginName> <password>  [To modify existing user]\n");
        printMessage.append("delete <loginName> <password> [To delete the user]\n");
        printMessage.append("lookup <loginName> [To look up user by login name]\n");
        printMessage.append("reverseLookup <userID> [To look up user by user id]\n");
        printMessage.append("listLogins [To list all the logins]\n");
        printMessage.append("listIds [To list all the user ids]\n");
        printMessage.append("listAllInfo [To list all info of users]");

        return printMessage.toString();
    }


    public Identity.ReadResponse.HelpResponse help(){
        return Identity.ReadResponse.HelpResponse.newBuilder().setHelpMessage(showHelp()).build();
    }

    @Override
    public void writeRequest(Identity.WriteRequest request, StreamObserver<Identity.WriteResponse> responseObserver) {
        logger = Logger.getLogger(getLoggerName());
        logger.info("Received WriteRequest");
        // Initialize write response
        Identity.WriteResponse writeResponse = null;

        // If node that received this is a coordinator, then forward it to all children
        if (identityServer.isCoordinator()) {
            logger.info("This node is a coordinator, forwarding to child nodes");

            // Check if user is not in blocking set
            String loginName = getWriteRequestLogin(request);
            while (this.lockUserIds.contains(loginName)) {
                logger.info("User " + loginName + " is currently being written to, blocking write...");
            }

            lockUserIds.add(loginName);
            
            logger.info("User " + loginName + " does not have a lock, forwarding to child nodes...");
            writeResponse = sendWriteToChildNodes(request);

            lockUserIds.remove(loginName);
            logger.info("Removing lock on " + loginName);

        } else {
            logger.info("This node is a child node, getting response");
            // Get the correct response for the request
            if (request.hasCreateUserRequest()) {
                writeResponse = Identity.WriteResponse.newBuilder().setCreateUserResponse(create(request.getCreateUserRequest())).build();
            }
    
            if (request.hasModifyUserRequest()) {
                writeResponse = Identity.WriteResponse.newBuilder().setModifyUserResponse(modify(request.getModifyUserRequest())).build();
            }
    
            if (request.hasDeleteUserRequest()) {
                writeResponse = Identity.WriteResponse.newBuilder().setDeleteUserResponse(delete(request.getDeleteUserRequest())).build();
            }
        }

        responseObserver.onNext(writeResponse);
        responseObserver.onCompleted();
    }

    private String getWriteRequestLogin(Identity.WriteRequest request) {
        String loginName = null;
        if (request.hasCreateUserRequest()) {
            loginName = request.getCreateUserRequest().getLoginName();
        }

        if (request.hasModifyUserRequest()) {
            loginName = request.getModifyUserRequest().getOldLoginName();
        }

        if (request.hasDeleteUserRequest()) {
            loginName = request.getDeleteUserRequest().getLoginName();
        }

        return loginName;
    }

    private String getReadRequestLogin(Identity.ReadRequest request) {
        String loginName = null;
        if (request.hasLookUpLoginNameRequest()) {
            loginName = request.getLookUpLoginNameRequest().getLoginName();
        }

        if (request.hasLookUpUserIdRequest()) {

            loginName = IdentityUtil.returnUser(request.getLookUpUserIdRequest().getUserID()).getLoginName();
        }

        return loginName;
    }

    @Override
    public void readRequest(Identity.ReadRequest request, StreamObserver<Identity.ReadResponse> responseObserver) {
        logger = Logger.getLogger(getLoggerName());
        logger.info("Received ReadRequest");
        // Initialize read response
        Identity.ReadResponse readResponse = null;

        // Check if user is not in blocking set
        String loginName = getReadRequestLogin(request);
        while (this.lockUserIds.contains(loginName)) {
            logger.info("User " + loginName + " is currently being written to, blocking read...");
        }

        // Get the correct response for the request
        if (request.hasLookUpLoginNameRequest()) {
            readResponse = Identity.ReadResponse.newBuilder().setIsUserExistResponse(lookup(request.getLookUpLoginNameRequest())).build();
        }

        if (request.hasLookUpUserIdRequest()) {
            readResponse = Identity.ReadResponse.newBuilder().setIsUserExistResponse(reverseLookup(request.getLookUpUserIdRequest())).build();
        }

        if (request.getHelpRequest() == true) {
            readResponse = Identity.ReadResponse.newBuilder().setHelpResponse(help()).build();
        }

        if (request.getListIdsRequest() == true) {
            readResponse = Identity.ReadResponse.newBuilder().setUserIdsPrintResponse(listIds()).build();
        }

        if (request.getListLoginsRequest() == true) {
            readResponse = Identity.ReadResponse.newBuilder().setUserLoginsPrintResponse(listLogins()).build();
        }

        if (request.getAllInfoRequest() == true) {
            readResponse = Identity.ReadResponse.newBuilder().setAllInfoPrintResponse(listAllInfo()).build();
        }

        responseObserver.onNext(readResponse);
        responseObserver.onCompleted();
    }

    
    
    private Identity.WriteResponse sendWriteToChildNodes(Identity.WriteRequest writeRequest) {
        ManagedChannel serverChannel;
        Identity.WriteResponse response = null;

        for (int nodeId : identityServer.getNodes().keySet()) {
            int port = identityServer.getNodes().get(nodeId).getPort();
            if (port != identityServer.getPort()) {
                logger.info("Sending WriteRequest to port " + port);
                try {
                    serverChannel = NettyChannelBuilder.forAddress(identityServer.getAddress(), port).sslContext(
                        GrpcSslContexts.forClient()
                                .trustManager(new File(identityServer.getCertiFilePath()))
                                .build()
                    ).build();
    
                    // TODO: Maybe make asynch?
                    UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub stub = UserAuthenticationServiceGrpc.newBlockingStub(serverChannel);
    
                    // Forward request
                    response =  stub.writeRequest(writeRequest); // TODO: can return immediately if all child nodes using same redis instance, otherwise forward write to each. 
                    
                    serverChannel.shutdown();
                    return response;
    
                } catch (SSLException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    private String getNodesPort(){
        String port = "{";
        for(int nodeId : identityServer.getNodes().keySet())
            port  = port + Integer.toString(identityServer.getNodes().get(nodeId).getPort()) + " ,";
        return port + "}";
    }

    private String getLoggerName(){
        String name = "ChildNode";
        if ( identityServer.isCoordinator() )
            name = "Coordinator";

        return name;
    }
}
