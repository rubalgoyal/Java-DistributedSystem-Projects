import io.grpc.stub.StreamObserver;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class UserAuthenticationImpl extends UserAuthenticationServiceGrpc.UserAuthenticationServiceImplBase {
    ConcurrentHashMap<String, UserModel> inSessionUser = new ConcurrentHashMap<>();
    private int maxUserId = 0;

    private UserModel createUserId(String loginName, String realName, String password) throws NoSuchAlgorithmException {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        UserModel newUser = new UserModel(0,loginName, realName, password,null, date,false, null);
        String passwordHash = IdentityUtil.generateHash(password, newUser);
        newUser.setPassword(passwordHash);
        IdentityUtil.insertUser(newUser, String.valueOf(newUser.getUserId()),true);
        return newUser;
    }
    @Override
    public void create(Identity.User request, StreamObserver<Identity.ServerActions> responseObserver)  {
        String loginName = request.getLoginName();
        String realName = request.getRealName();
        String password = request.getPassword();
        try {
            Identity.ServerActions response = null;
            if(IdentityUtil.isUserExist(loginName)){
                System.out.println("User already exist");
                response = Identity.ServerActions.newBuilder().setAlreadyExist(true).build();
                responseObserver.onNext(response);
            }
            else{
                UserModel users = createUserId(loginName, realName, password);
                response = Identity.ServerActions.newBuilder().setUserID(users.getUserId()).build();
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
    public void modify(Identity.User request, StreamObserver<Identity.ServerActions> responseObserver) {
        String oldLoginName = request.getOldLoginName();
        String newLoginName = request.getLoginName();
        String password = request.getPassword();
        Identity.ServerActions response = null;
        if(IdentityUtil.isUserExist(oldLoginName)){
            if(!IdentityUtil.isUserExist(newLoginName)){
                UserModel nuser = IdentityUtil.returnUser(oldLoginName);

                if(IdentityUtil.isPasswordMatch(nuser, password)){
                    nuser.setOldLoginName(oldLoginName);
                    nuser.setLoginName(newLoginName);
                    IdentityUtil.insertUser(nuser, String.valueOf(nuser.getUserId()), false);
                    response = Identity.ServerActions.newBuilder().setAlreadyExist(false).setIsPassMatch(true).setLoginName(nuser.getLoginName()).build();
                }
                else{
                    response = Identity.ServerActions.newBuilder().setAlreadyExist(false).setIsPassMatch(false).build();
                }
                responseObserver.onNext(response);
            }
            else{
                response = Identity.ServerActions.newBuilder().setAlreadyExist(true).build();
                responseObserver.onNext(response);
            }
        }
        else{
            response = Identity.ServerActions.newBuilder().setAlreadyExist(false).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void delete(Identity.User request, StreamObserver<Identity.ServerActions> responseObserver) {
        String loginName = request.getLoginName();
        String password = request.getPassword();
        Identity.ServerActions response = null;
        UserModel nuser = IdentityUtil.returnUser(loginName);
        if(nuser != null && IdentityUtil.isPasswordMatch(nuser, password))
            response = Identity.ServerActions.newBuilder().setIsDeleted(IdentityUtil.deleteUser(loginName)).setIsPassMatch(true).build();
        else if(nuser != null)
            response = Identity.ServerActions.newBuilder().setLoginName(loginName).build();
        else
            response = Identity.ServerActions.newBuilder().setIsPassMatch(false).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void lookup(Identity.User request, StreamObserver<Identity.ServerActions> responseObserver) {
        String loginName = request.getLoginName();
        Identity.ServerActions response = null;
        if(IdentityUtil.isUserExist(loginName)){
            UserModel user = IdentityUtil.returnUser(loginName);
            response = Identity.ServerActions.newBuilder()
                    .setUserID(user.getUserId()).setAlreadyExist(true).setLoginName(user.getLoginName())
                    .setRealName(user.getRealName())
                    .setCreatedOn(user.getCreatedOn()).setPassword(user.getPassword()).build();
            responseObserver.onNext(response);
        }
        else {
            response = Identity.ServerActions.newBuilder().setAlreadyExist(false).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void reverseLookup(Identity.User request, StreamObserver<Identity.ServerActions> responseObserver){
        int userId = request.getUserID();
        Identity.ServerActions response = null;
        if(IdentityUtil.isUserExist(userId)){
            UserModel user = IdentityUtil.returnUser(userId);
            response = Identity.ServerActions.newBuilder()
                    .setUserID(user.getUserId()).setAlreadyExist(true).setLoginName(user.getLoginName())
                    .setRealName(user.getRealName())
                    .setCreatedOn(user.getCreatedOn()).setPassword(user.getPassword()).build();
            responseObserver.onNext(response);
        }
        else {
            response = Identity.ServerActions.newBuilder().setAlreadyExist(false).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
    @Override
    public void listLogins(Identity.EmptyRequest request, StreamObserver<Identity.StringResponse> responseObserver){
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
        Identity.StringResponse response = Identity.StringResponse.newBuilder().setInfo(stringBuilder.toString()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void listIds(Identity.EmptyRequest request, StreamObserver<Identity.StringResponse> responseObserver){
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
        Identity.StringResponse response = Identity.StringResponse.newBuilder().setInfo(stringBuilder.toString()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    @Override
    public void listAllInfo(Identity.EmptyRequest request, StreamObserver<Identity.StringResponse> responseObserver) {
        ConcurrentHashMap<String, UserModel> allUsers = IdentityUtil.fetchAllUsers();
        StringBuilder stringBuilder = new StringBuilder();
        if (allUsers.isEmpty())
            stringBuilder.append("There is no user in database");
        else {
            stringBuilder.append("Following is the user's info \n");
            stringBuilder.append("User Id\t\tLogin Name\t\tReal Name\t\tOld Login Name\t\tCreated On\t\tPassword\t\t\t\tSalt \n");
            stringBuilder.append(new String(new char[150]).replace("\0", "-")+"\n");
            allUsers.forEach((key, value) -> {
                        stringBuilder.append(value.getUserId() + "\t\t"
                                + value.getLoginName() + "\t\t"
                                + value.getRealName() + "\t\t"
                                + value.getOldLoginName() + "\t\t"
                                + value.getCreatedOn() + "\t\t"
                                + value.getPassword() + "\t\t"
                                + value.getSalt() +"\n"
                        );
                    }
            );
        }
        Identity.StringResponse response = Identity.StringResponse.newBuilder().setInfo(stringBuilder.toString()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
