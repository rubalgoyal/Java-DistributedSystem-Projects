import io.grpc.stub.StreamObserver;

public class ServerCommunicationImpl extends ServerCommunicationGrpc.ServerCommunicationImplBase {
    private final IdentityServer identityServer;
    public ServerCommunicationImpl(IdentityServer identityServer){
        this.identityServer = identityServer;
    }

    @Override
    public void checkStatus(ServerCommunicationOuterClass.Ping request, StreamObserver<ServerCommunicationOuterClass.Pong> responseObserver) {
        ServerCommunicationOuterClass.Pong response = ServerCommunicationOuterClass.Pong.newBuilder().setMessage("working").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    @Override
    public void election(ServerCommunicationOuterClass.Election request, StreamObserver<ServerCommunicationOuterClass.Empty> responseObserver){
        identityServer.setCoordinatorPort(request.getPort());
        identityServer.setCoordinator(false);
        responseObserver.onNext(ServerCommunicationOuterClass.Empty.newBuilder().build());
        responseObserver.onCompleted();

    }

    @Override
    public void checkCoordinator(ServerCommunicationOuterClass.Empty request, StreamObserver<ServerCommunicationOuterClass.CheckCoordinator> responseObserver){
        ServerCommunicationOuterClass.CheckCoordinator response = ServerCommunicationOuterClass.CheckCoordinator
                                                                                                .newBuilder()
                                                                                                .setIsCoordinator(identityServer.isCoordinator())
                                                                                                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
