import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.53.0)",
    comments = "Source: Communication.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GetCoordinatorGrpc {

  private GetCoordinatorGrpc() {}

  public static final String SERVICE_NAME = "GetCoordinator";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Communication.Ping,
      Communication.Pong> getGetCoordinatorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getCoordinator",
      requestType = Communication.Ping.class,
      responseType = Communication.Pong.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Communication.Ping,
      Communication.Pong> getGetCoordinatorMethod() {
    io.grpc.MethodDescriptor<Communication.Ping, Communication.Pong> getGetCoordinatorMethod;
    if ((getGetCoordinatorMethod = GetCoordinatorGrpc.getGetCoordinatorMethod) == null) {
      synchronized (GetCoordinatorGrpc.class) {
        if ((getGetCoordinatorMethod = GetCoordinatorGrpc.getGetCoordinatorMethod) == null) {
          GetCoordinatorGrpc.getGetCoordinatorMethod = getGetCoordinatorMethod =
              io.grpc.MethodDescriptor.<Communication.Ping, Communication.Pong>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getCoordinator"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Communication.Ping.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Communication.Pong.getDefaultInstance()))
              .setSchemaDescriptor(new GetCoordinatorMethodDescriptorSupplier("getCoordinator"))
              .build();
        }
      }
    }
    return getGetCoordinatorMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GetCoordinatorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GetCoordinatorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GetCoordinatorStub>() {
        @java.lang.Override
        public GetCoordinatorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GetCoordinatorStub(channel, callOptions);
        }
      };
    return GetCoordinatorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GetCoordinatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GetCoordinatorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GetCoordinatorBlockingStub>() {
        @java.lang.Override
        public GetCoordinatorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GetCoordinatorBlockingStub(channel, callOptions);
        }
      };
    return GetCoordinatorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GetCoordinatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GetCoordinatorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GetCoordinatorFutureStub>() {
        @java.lang.Override
        public GetCoordinatorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GetCoordinatorFutureStub(channel, callOptions);
        }
      };
    return GetCoordinatorFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class GetCoordinatorImplBase implements io.grpc.BindableService {

    /**
     */
    public void getCoordinator(Communication.Ping request,
        io.grpc.stub.StreamObserver<Communication.Pong> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCoordinatorMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetCoordinatorMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Communication.Ping,
                Communication.Pong>(
                  this, METHODID_GET_COORDINATOR)))
          .build();
    }
  }

  /**
   */
  public static final class GetCoordinatorStub extends io.grpc.stub.AbstractAsyncStub<GetCoordinatorStub> {
    private GetCoordinatorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GetCoordinatorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GetCoordinatorStub(channel, callOptions);
    }

    /**
     */
    public void getCoordinator(Communication.Ping request,
        io.grpc.stub.StreamObserver<Communication.Pong> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCoordinatorMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class GetCoordinatorBlockingStub extends io.grpc.stub.AbstractBlockingStub<GetCoordinatorBlockingStub> {
    private GetCoordinatorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GetCoordinatorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GetCoordinatorBlockingStub(channel, callOptions);
    }

    /**
     */
    public Communication.Pong getCoordinator(Communication.Ping request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCoordinatorMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class GetCoordinatorFutureStub extends io.grpc.stub.AbstractFutureStub<GetCoordinatorFutureStub> {
    private GetCoordinatorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GetCoordinatorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GetCoordinatorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Communication.Pong> getCoordinator(
        Communication.Ping request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCoordinatorMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_COORDINATOR = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GetCoordinatorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GetCoordinatorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_COORDINATOR:
          serviceImpl.getCoordinator((Communication.Ping) request,
              (io.grpc.stub.StreamObserver<Communication.Pong>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GetCoordinatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GetCoordinatorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Communication.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("GetCoordinator");
    }
  }

  private static final class GetCoordinatorFileDescriptorSupplier
      extends GetCoordinatorBaseDescriptorSupplier {
    GetCoordinatorFileDescriptorSupplier() {}
  }

  private static final class GetCoordinatorMethodDescriptorSupplier
      extends GetCoordinatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GetCoordinatorMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GetCoordinatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GetCoordinatorFileDescriptorSupplier())
              .addMethod(getGetCoordinatorMethod())
              .build();
        }
      }
    }
    return result;
  }
}
