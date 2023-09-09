import io.grpc.stub.StreamObserver;

public class ClientCommunicationImpl extends ClientCommunicationGrpc.ClientCommunicationImplBase{

    public final MasterServer masterServer;

    public ClientCommunicationImpl(MasterServer masterServer){
        this.masterServer = masterServer;
    }

    @Override
    public void getCoordinator(ClientCommunicationOuterClass.Request request,
                               StreamObserver<ClientCommunicationOuterClass.Response> responseStreamObserver){
        System.out.println("Request recieved for coordinator server- ");
        ClientCommunicationOuterClass.Response response = ClientCommunicationOuterClass.Response.newBuilder()
                                                                    .setCoordinatorNodeId(masterServer.coordinatorNodeId)
                                                                    .setCoordinatorPort(masterServer.coordinatorPort)
                                                                    .build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }
}
