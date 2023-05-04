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
            Identity.EmptyRequest request = Identity.EmptyRequest.newBuilder().build();
            blockingStub.help(request);
            channel.shutdown();
        });
        String expectedMessgae = "File does not contain valid certificate";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessgae));
    }
    @Test
    public void checkUserExists() throws IOException, InterruptedException {
        ManagedChannel channel = NettyChannelBuilder.forAddress(address, port).sslContext(
                GrpcSslContexts.forClient()
                        .trustManager(new File(certiFilePath))
                        .build()
            ).build();
        UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(channel);
        String loginName = "TTTTTTTTTTTTTTTTTTTTTTTT";
        String password = "Test@1234";
        String actualResponse = deleteUser(blockingStub, loginName, password);
        channel.shutdown();
        String expectedMessgae = "Login name does not exist in database";
        Assertions.assertTrue(expectedMessgae.equals(actualResponse));
    }

    @Test
    public void checkPasswordAuthentication() throws IOException, InterruptedException {
        ManagedChannel channel = NettyChannelBuilder.forAddress(address, port).sslContext(
                GrpcSslContexts.forClient()
                        .trustManager(new File(certiFilePath))
                        .build()
        ).build();
        UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub = UserAuthenticationServiceGrpc.newBlockingStub(channel);

        String actualResponse = createUser(blockingStub);
        channel.shutdown();
        String expectedMessgae = "Password incorrect, please enter correct password";
        Assertions.assertTrue(expectedMessgae.equals(actualResponse));
    }



    private String deleteUser(UserAuthenticationServiceGrpc.UserAuthenticationServiceBlockingStub blockingStub,
                              String loginName,
                              String password){

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
        Identity.User request = Identity.User.newBuilder().setLoginName(loginName).setRealName(realName).setPassword(password).build();
        blockingStub.create(request);
        outputResponse = deleteUser(blockingStub, loginName, wrongPassword);
        //Actually delete the user
        deleteUser(blockingStub, loginName, password);
        return outputResponse;
    }

}
