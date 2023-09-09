import io.grpc.stub.StreamObserver;

public class GetCoordinatorImpl extends GetCoordinatorGrpc.GetCoordinatorImplBase{
    @Override
    public void getCoordinator(Communication.Ping request, StreamObserver<Communication.Pong> responseObserver) {
        super.getCoordinator(request, responseObserver);
    }
}
