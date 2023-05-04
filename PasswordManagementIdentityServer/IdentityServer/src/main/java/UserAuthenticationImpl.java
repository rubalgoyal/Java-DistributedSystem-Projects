import io.grpc.stub.StreamObserver;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class UserAuthenticationImpl extends UserAuthenticationServiceGrpc.UserAuthenticationServiceImplBase {
    ConcurrentHashMap<String, UserModel> inSessionUser = null;
    public UserAuthenticationImpl(){}
    public UserAuthenticationImpl(ConcurrentHashMap<String, UserModel> inSessionUser){
        this.inSessionUser = inSessionUser;
    }

    private UserModel createUserId(String loginName, String realName, String password) throws NoSuchAlgorithmException {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        int nextId = IdentityUtil.getLastUserId();
        UserModel newUser = new UserModel(nextId,loginName, realName, password,null, date,false, null);
        String passwordHash = IdentityUtil.generateHash(password, newUser);
        newUser.setPassword(passwordHash);

        inSessionUser.put(String.valueOf(newUser.getUserId()), newUser);
        boolean isSuccessWrite = false;
        while (!isSuccessWrite)
            isSuccessWrite =  IdentityUtil.bulkInsert(inSessionUser, true);

        inSessionUser.remove(String.valueOf(newUser.getUserId()));
        return newUser;
    }
    @Override
    public void create(Identity.User request, StreamObserver<Identity.NewUserId> responseObserver)  {
        String loginName = request.getLoginName();
        String realName = request.getRealName();
        String password = request.getPassword();
        try {
            Identity.NewUserId response = null;
            if(IdentityUtil.isUserExist(loginName)){
                System.out.println("User already exist");
                response = Identity.NewUserId.newBuilder().setAlreadyExist(true).build();
                responseObserver.onNext(response);
            }
            else{
                UserModel users = createUserId(loginName, realName, password);
                response = Identity.NewUserId.newBuilder().setUserID(users.getUserId()).build();
                responseObserver.onNext(response);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            responseObserver.onCompleted();
        }
    }
    @Override
    public void modify(Identity.User request, StreamObserver<Identity.ModifyUser> responseObserver) {
        String oldLoginName = request.getOldLoginName();
        String newLoginName = request.getLoginName();
        String password = request.getPassword();
        Identity.ModifyUser response = null;
        if(IdentityUtil.isUserExist(oldLoginName)){
            if(!IdentityUtil.isUserExist(newLoginName)){
                UserModel nuser = IdentityUtil.returnUser(oldLoginName);

                if(IdentityUtil.isPasswordMatch(nuser, password)){
                    nuser.setOldLoginName(oldLoginName);
                    nuser.setLoginName(newLoginName);
                    IdentityUtil.insertUser(nuser, String.valueOf(nuser.getUserId()), false);
                    response = Identity.ModifyUser.newBuilder().setAlreadyExist(false).setIsPassMatch(true).setLoginName(nuser.getLoginName()).build();
                }
                else{
                    response = Identity.ModifyUser.newBuilder().setAlreadyExist(false).setIsPassMatch(false).build();
                }
                responseObserver.onNext(response);
            }
            else{
                response = Identity.ModifyUser.newBuilder().setAlreadyExist(true).build();
                responseObserver.onNext(response);
            }
        }
        else{
            response = Identity.ModifyUser.newBuilder().setAlreadyExist(false).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void delete(Identity.DeleteUserName request, StreamObserver<Identity.DeleteUser> responseObserver) {
        String loginName = request.getLoginName();
        String password = request.getPassword();
        Identity.DeleteUser response = null;
        UserModel nuser = IdentityUtil.returnUser(loginName);
        if(nuser != null && IdentityUtil.isPasswordMatch(nuser, password))
            response = Identity.DeleteUser.newBuilder().setIsDeleted(IdentityUtil.deleteUser(loginName)).setIsPassMatch(true).build();
        else if(nuser != null)
            response = Identity.DeleteUser.newBuilder().setLoginName(loginName).build();
        else
            response = Identity.DeleteUser.newBuilder().setIsPassMatch(false).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void lookup(Identity.LookUpLoginName request, StreamObserver<Identity.IsUserExist> responseObserver) {
        String loginName = request.getLoginName();
        Identity.IsUserExist response = null;
        if(IdentityUtil.isUserExist(loginName)){
            UserModel user = IdentityUtil.returnUser(loginName);
            String userString = IdentityUtil.convertUserToString(user);
            response = Identity.IsUserExist.newBuilder().setIsUserExist(true).setPrintMessage(userString).build();
            responseObserver.onNext(response);
        }
        else {
            response = Identity.IsUserExist.newBuilder().setIsUserExist(false).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void reverseLookup(Identity.LookUpUserId request, StreamObserver<Identity.IsUserExist> responseObserver){
        int userId = request.getUserID();
        Identity.IsUserExist response = null;
        if(IdentityUtil.isUserExist(userId)){
            UserModel user = IdentityUtil.returnUser(userId);
            String userString = IdentityUtil.convertUserToString(user);
            response = Identity.IsUserExist.newBuilder().setIsUserExist(true).setPrintMessage(userString).build();
            responseObserver.onNext(response);
        }
        else {
            response = Identity.IsUserExist.newBuilder().setIsUserExist(false).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
    @Override
    public void listLogins(Identity.EmptyRequest request, StreamObserver<Identity.UserLoginsPrint> responseObserver){
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
        Identity.UserLoginsPrint response = Identity.UserLoginsPrint.newBuilder().setUserLoginsPrint(stringBuilder.toString()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void listIds(Identity.EmptyRequest request, StreamObserver<Identity.UserIdsPrint> responseObserver){
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
        Identity.UserIdsPrint response = Identity.UserIdsPrint.newBuilder().setUserIdsPrint(stringBuilder.toString()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    @Override
    public void listAllInfo(Identity.EmptyRequest request, StreamObserver<Identity.AllInfoPrint> responseObserver) {
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
        Identity.AllInfoPrint response = Identity.AllInfoPrint.newBuilder().setAllInfoPrint(stringBuilder.toString()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
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

    @Override
    public void help(Identity.EmptyRequest request, StreamObserver<Identity.HelpResponse> responseObserver){
        Identity.HelpResponse response = Identity.HelpResponse.newBuilder().setHelpMessage(showHelp()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
