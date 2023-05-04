import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.function.BooleanSupplier;

public class IdentityServerTest {
    private String address = "localhost";
    private int port = 5051;
    private String certiFilePath = "certificate.cer";

    @Test
    public void checkValidCertificateException() throws IOException, InterruptedException {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            ManagedChannel channel = NettyChannelBuilder.forAddress(address, port).sslContext(
                    GrpcSslContexts.forClient()
                            .trustManager(new File(""))
                            .build()
            ).build();
            UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(channel);
            Identity.ReadRequest readRequest = Identity.ReadRequest.newBuilder().setHelpRequest(true).build();
            blockingStub.readRequest(readRequest).getHelpResponse();
            channel.shutdown();
        });
        String expectedMessgae = "File does not contain valid certificate";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessgae));
    }

    @Test
    public void checkUserExists() throws IOException, InterruptedException {
        // Simulates client making a request to a known node in the cluster for the address of the coodinator node
        ManagedChannel channel = NettyChannelBuilder.forAddress(address, port).sslContext(
            GrpcSslContexts.forClient()
                    .trustManager(new File(certiFilePath))
                    .build()
        ).build();
        ClientCommunicationGrpc.ClientCommunicationBlockingStub stub = ClientCommunicationGrpc.newBlockingStub(channel);
        ClientCommunicationOuterClass.Response response = stub.getCoordinator(
                    ClientCommunicationOuterClass.Request.newBuilder().build()
        );

        int coordinatorPort = response.getCoordinatorPort();

        // Connect to the coordinator node, creating a channel
        ManagedChannel coordinatorChannel = NettyChannelBuilder.forAddress(address, coordinatorPort).sslContext(
                GrpcSslContexts.forClient()
                        .trustManager(new File(certiFilePath))
                        .build()
        ).build();
        UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(coordinatorChannel);

        // Check for a user that does not exist
        String loginName = "TTTTTTTTTTTTTTTTTTTTTTTT";
        String password = "Test@1234";
        String actualResponse = deleteUser(blockingStub, loginName, password);
        channel.shutdown();
        String expectedMessgae = "Login name does not exist in database";

        // Succeeds if the does not exist error message returned
        Assertions.assertTrue(expectedMessgae.equals(actualResponse));
    }

    @Test
    public void checkPasswordAuthentication() throws IOException, InterruptedException {
        // Simulates client making a request to a known node in the cluster for the address of the coodinator node
        ManagedChannel channel = NettyChannelBuilder.forAddress(address, port).sslContext(
            GrpcSslContexts.forClient()
                    .trustManager(new File(certiFilePath))
                    .build()
        ).build();
        ClientCommunicationGrpc.ClientCommunicationBlockingStub stub = ClientCommunicationGrpc.newBlockingStub(channel);
        ClientCommunicationOuterClass.Response response = stub.getCoordinator(
                    ClientCommunicationOuterClass.Request.newBuilder().build()
        );

        int coordinatorPort = response.getCoordinatorPort();

        // Connect to the coordinator node, creating a channel
        ManagedChannel coordinatorChannel = NettyChannelBuilder.forAddress(address, coordinatorPort).sslContext(
                GrpcSslContexts.forClient()
                        .trustManager(new File(certiFilePath))
                        .build()
        ).build();
        UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(coordinatorChannel);

        String actualResponse = createUser(blockingStub);
        channel.shutdown();
        String expectedMessgae = "Password incorrect, please enter correct password";

        // Passes if the incorrect password error message returned
        Assertions.assertTrue(expectedMessgae.equals(actualResponse));
    }

    @Test
    public void testWriteLock() throws IOException, InterruptedException {
        // Simulates client making a request to a known node in the cluster for the address of the coodinator node
        ManagedChannel channel = NettyChannelBuilder.forAddress(address, port).sslContext(
            GrpcSslContexts.forClient()
                    .trustManager(new File(certiFilePath))
                    .build()
        ).build();
        ClientCommunicationGrpc.ClientCommunicationBlockingStub stub = ClientCommunicationGrpc.newBlockingStub(channel);
        ClientCommunicationOuterClass.Response response = stub.getCoordinator(
                    ClientCommunicationOuterClass.Request.newBuilder().build()
        );

        int coordinatorPort = response.getCoordinatorPort();

        // Connect to the coordinator node, creating a channel
        ManagedChannel coordinatorChannel = NettyChannelBuilder.forAddress(address, coordinatorPort).sslContext(
                GrpcSslContexts.forClient()
                        .trustManager(new File(certiFilePath))
                        .build()
        ).build();
        UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(coordinatorChannel);

        // Passes if the user is modified by one of the concurrent calls
        Assertions.assertTrue(rapidlyModifyUser(blockingStub));
    }

    @Test
    public void testReadLock() throws IOException, InterruptedException {
        // Simulates client making a request to a known node in the cluster for the address of the coodinator node
        ManagedChannel channel = NettyChannelBuilder.forAddress(address, port).sslContext(
            GrpcSslContexts.forClient()
                    .trustManager(new File(certiFilePath))
                    .build()
        ).build();
        ClientCommunicationGrpc.ClientCommunicationBlockingStub stub = ClientCommunicationGrpc.newBlockingStub(channel);
        ClientCommunicationOuterClass.Response response = stub.getCoordinator(
                    ClientCommunicationOuterClass.Request.newBuilder().build()
        );

        int coordinatorPort = response.getCoordinatorPort();

        // Connect to the coordinator node, creating a channel
        ManagedChannel coordinatorChannel = NettyChannelBuilder.forAddress(address, coordinatorPort).sslContext(
                GrpcSslContexts.forClient()
                        .trustManager(new File(certiFilePath))
                        .build()
        ).build();
        UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(coordinatorChannel);

        // No real way to fail this without getting server output, the lookup should block in server
        rapidlyWriteRead(blockingStub);
    }

    
    private String deleteUser(UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub,
    String loginName,
    String password){

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
                return "Login name has been deleted from database";
                else
                return  "Password incorrect, please enter correct password";
            }
            else if(!response.getLoginName().isEmpty() || response.getLoginName().equals(loginName) )
            return "Password incorrect, please enter correct password";
            else
            return "Login name does not exist in database";
    }
    
    public String createUser(UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub){
        String loginName = "Test@1234";
        String realName = "Test@1234";
        String password = "Test@1234";
        String wrongPassword = "Thhhhhhhhh";
        String outputResponse = null;
        
        Identity.WriteRequest.CreateUserRequest request = Identity.WriteRequest.CreateUserRequest.newBuilder().setLoginName(loginName).setRealName(realName).setPassword(password).build();
        Identity.WriteRequest writeRequest = Identity.WriteRequest.newBuilder().setCreateUserRequest(request).build();
        
        blockingStub.writeRequest(writeRequest).getCreateUserResponse();
        outputResponse = deleteUser(blockingStub, loginName, wrongPassword);
        //Actually delete the user
        deleteUser(blockingStub, loginName, password);
        return outputResponse;
    }

    private boolean rapidlyModifyUser(UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub) {
        String loginName = "ModifyTest";
        String realName = "ModifyTest";
        String password = "pass";
        boolean success = false;

        // Create user
        Identity.WriteRequest.CreateUserRequest request = Identity.WriteRequest.CreateUserRequest.newBuilder().setLoginName(loginName).setRealName(realName).setPassword(password).build();
        Identity.WriteRequest writeRequest = Identity.WriteRequest.newBuilder().setCreateUserRequest(request).build();
        
        blockingStub.writeRequest(writeRequest).getCreateUserResponse();
        
        // Submit two requests to modify user on two threads (One should block on server side)
        new ModifyUser(loginName, loginName + "ONE", password, blockingStub).start();
        new ModifyUser(loginName, loginName + "TWO", password, blockingStub).start();
        

        // Wait for requests to submit before deleting
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get result, true if one of them is successfully modified
        Identity.ReadRequest.LookUpLoginNameRequest lookupRequest = Identity.ReadRequest.LookUpLoginNameRequest.newBuilder().setLoginName(loginName + "ONE").build();
        Identity.ReadRequest readRequest = Identity.ReadRequest.newBuilder().setLookUpLoginNameRequest(lookupRequest).build();
        
        success = (success || blockingStub.readRequest(readRequest).getIsUserExistResponse().getIsUserExist());

        lookupRequest = Identity.ReadRequest.LookUpLoginNameRequest.newBuilder().setLoginName(loginName + "TWO").build();
        readRequest = Identity.ReadRequest.newBuilder().setLookUpLoginNameRequest(lookupRequest).build();

        success = (success || blockingStub.readRequest(readRequest).getIsUserExistResponse().getIsUserExist());
        
        
        // Delete user
        deleteUser(blockingStub, (loginName + "ONE"), password);
        deleteUser(blockingStub, (loginName + "TWO"), password);


        return success;
    }

    private void rapidlyWriteRead(UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub) {
        String loginName = "ReadWriteTest";
        String realName = "ReadWriteTest";
        String password = "pass";

        // Create user
        Identity.WriteRequest.CreateUserRequest request = Identity.WriteRequest.CreateUserRequest.newBuilder().setLoginName(loginName).setRealName(realName).setPassword(password).build();
        Identity.WriteRequest writeRequest = Identity.WriteRequest.newBuilder().setCreateUserRequest(request).build();
        
        blockingStub.writeRequest(writeRequest).getCreateUserResponse();
        
        // Submit a request to modify and lookup user on two threads (One should block on server side)
        new ModifyUser(loginName, loginName + "NEW", password, blockingStub).start();
        new LookupUser(loginName, blockingStub).start();

        // Wait for requests to submit before deleting
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // Delete user
        deleteUser(blockingStub, (loginName + "NEW"), password);
    }

    class ModifyUser extends Thread {
        String oldName;
        String newName;
        String pass;
        UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub;
    
        public ModifyUser(String oldName, String newLoginName, String password, UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub) {
            this.oldName = oldName;
            this.newName = newLoginName;
            this.pass = password;
            this.blockingStub = blockingStub;
        }
    
        public void run() {
            Identity.WriteRequest.ModifyUserRequest modifyRequest = Identity.WriteRequest.ModifyUserRequest.newBuilder().setOldLoginName(oldName).setLoginName(newName).setPassword(pass).build();
            Identity.WriteRequest modifyWriteRequest = Identity.WriteRequest.newBuilder().setModifyUserRequest(modifyRequest).build();
            blockingStub.writeRequest(modifyWriteRequest).getModifyUserResponse();
        }
    }

    class LookupUser extends Thread {
        String name;
        String newName;
        String pass;
        UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub;
    
        public LookupUser(String name, UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub) {
            this.name = name;
            this.blockingStub = blockingStub;
        }
    
        public void run() {
            Identity.ReadRequest.LookUpLoginNameRequest lookupRequest = Identity.ReadRequest.LookUpLoginNameRequest.newBuilder().setLoginName(name).build();
            Identity.ReadRequest readRequest = Identity.ReadRequest.newBuilder().setLookUpLoginNameRequest(lookupRequest).build();
            blockingStub.readRequest(readRequest).getIsUserExistResponse().getIsUserExist();
        }
    }
}
