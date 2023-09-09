import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.53.0)",
    comments = "Source: ServerCommunication.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ServerCommunicationGrpc {

  private ServerCommunicationGrpc() {}

  public static final String SERVICE_NAME = "ServerCommunication";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ServerCommunicationOuterClass.Ping,
      ServerCommunicationOuterClass.Pong> getCheckStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "checkStatus",
      requestType = ServerCommunicationOuterClass.Ping.class,
      responseType = ServerCommunicationOuterClass.Pong.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ServerCommunicationOuterClass.Ping,
      ServerCommunicationOuterClass.Pong> getCheckStatusMethod() {
    io.grpc.MethodDescriptor<ServerCommunicationOuterClass.Ping, ServerCommunicationOuterClass.Pong> getCheckStatusMethod;
    if ((getCheckStatusMethod = ServerCommunicationGrpc.getCheckStatusMethod) == null) {
      synchronized (ServerCommunicationGrpc.class) {
        if ((getCheckStatusMethod = ServerCommunicationGrpc.getCheckStatusMethod) == null) {
          ServerCommunicationGrpc.getCheckStatusMethod = getCheckStatusMethod =
              io.grpc.MethodDescriptor.<ServerCommunicationOuterClass.Ping, ServerCommunicationOuterClass.Pong>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "checkStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ServerCommunicationOuterClass.Ping.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ServerCommunicationOuterClass.Pong.getDefaultInstance()))
              .setSchemaDescriptor(new ServerCommunicationMethodDescriptorSupplier("checkStatus"))
              .build();
        }
      }
    }
    return getCheckStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ServerCommunicationOuterClass.Election,
      ServerCommunicationOuterClass.Empty> getElectionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "election",
      requestType = ServerCommunicationOuterClass.Election.class,
      responseType = ServerCommunicationOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ServerCommunicationOuterClass.Election,
      ServerCommunicationOuterClass.Empty> getElectionMethod() {
    io.grpc.MethodDescriptor<ServerCommunicationOuterClass.Election, ServerCommunicationOuterClass.Empty> getElectionMethod;
    if ((getElectionMethod = ServerCommunicationGrpc.getElectionMethod) == null) {
      synchronized (ServerCommunicationGrpc.class) {
        if ((getElectionMethod = ServerCommunicationGrpc.getElectionMethod) == null) {
          ServerCommunicationGrpc.getElectionMethod = getElectionMethod =
              io.grpc.MethodDescriptor.<ServerCommunicationOuterClass.Election, ServerCommunicationOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "election"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ServerCommunicationOuterClass.Election.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ServerCommunicationOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ServerCommunicationMethodDescriptorSupplier("election"))
              .build();
        }
      }
    }
    return getElectionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ServerCommunicationOuterClass.Empty,
      ServerCommunicationOuterClass.CheckCoordinator> getCheckCoordinatorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "checkCoordinator",
      requestType = ServerCommunicationOuterClass.Empty.class,
      responseType = ServerCommunicationOuterClass.CheckCoordinator.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ServerCommunicationOuterClass.Empty,
      ServerCommunicationOuterClass.CheckCoordinator> getCheckCoordinatorMethod() {
    io.grpc.MethodDescriptor<ServerCommunicationOuterClass.Empty, ServerCommunicationOuterClass.CheckCoordinator> getCheckCoordinatorMethod;
    if ((getCheckCoordinatorMethod = ServerCommunicationGrpc.getCheckCoordinatorMethod) == null) {
      synchronized (ServerCommunicationGrpc.class) {
        if ((getCheckCoordinatorMethod = ServerCommunicationGrpc.getCheckCoordinatorMethod) == null) {
          ServerCommunicationGrpc.getCheckCoordinatorMethod = getCheckCoordinatorMethod =
              io.grpc.MethodDescriptor.<ServerCommunicationOuterClass.Empty, ServerCommunicationOuterClass.CheckCoordinator>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "checkCoordinator"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ServerCommunicationOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ServerCommunicationOuterClass.CheckCoordinator.getDefaultInstance()))
              .setSchemaDescriptor(new ServerCommunicationMethodDescriptorSupplier("checkCoordinator"))
              .build();
        }
      }
    }
    return getCheckCoordinatorMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServerCommunicationStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerCommunicationStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerCommunicationStub>() {
        @java.lang.Override
        public ServerCommunicationStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerCommunicationStub(channel, callOptions);
        }
      };
    return ServerCommunicationStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServerCommunicationBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerCommunicationBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerCommunicationBlockingStub>() {
        @java.lang.Override
        public ServerCommunicationBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerCommunicationBlockingStub(channel, callOptions);
        }
      };
    return ServerCommunicationBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServerCommunicationFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerCommunicationFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerCommunicationFutureStub>() {
        @java.lang.Override
        public ServerCommunicationFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerCommunicationFutureStub(channel, callOptions);
        }
      };
    return ServerCommunicationFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ServerCommunicationImplBase implements io.grpc.BindableService {

    /**
     */
    public void checkStatus(ServerCommunicationOuterClass.Ping request,
        io.grpc.stub.StreamObserver<ServerCommunicationOuterClass.Pong> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckStatusMethod(), responseObserver);
    }

    /**
     */
    public void election(ServerCommunicationOuterClass.Election request,
        io.grpc.stub.StreamObserver<ServerCommunicationOuterClass.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getElectionMethod(), responseObserver);
    }

    /**
     */
    public void checkCoordinator(ServerCommunicationOuterClass.Empty request,
        io.grpc.stub.StreamObserver<ServerCommunicationOuterClass.CheckCoordinator> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckCoordinatorMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCheckStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ServerCommunicationOuterClass.Ping,
                ServerCommunicationOuterClass.Pong>(
                  this, METHODID_CHECK_STATUS)))
          .addMethod(
            getElectionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ServerCommunicationOuterClass.Election,
                ServerCommunicationOuterClass.Empty>(
                  this, METHODID_ELECTION)))
          .addMethod(
            getCheckCoordinatorMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ServerCommunicationOuterClass.Empty,
                ServerCommunicationOuterClass.CheckCoordinator>(
                  this, METHODID_CHECK_COORDINATOR)))
          .build();
    }
  }

  /**
   */
  public static final class ServerCommunicationStub extends io.grpc.stub.AbstractAsyncStub<ServerCommunicationStub> {
    private ServerCommunicationStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerCommunicationStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerCommunicationStub(channel, callOptions);
    }

    /**
     */
    public void checkStatus(ServerCommunicationOuterClass.Ping request,
        io.grpc.stub.StreamObserver<ServerCommunicationOuterClass.Pong> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void election(ServerCommunicationOuterClass.Election request,
        io.grpc.stub.StreamObserver<ServerCommunicationOuterClass.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getElectionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void checkCoordinator(ServerCommunicationOuterClass.Empty request,
        io.grpc.stub.StreamObserver<ServerCommunicationOuterClass.CheckCoordinator> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckCoordinatorMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ServerCommunicationBlockingStub extends io.grpc.stub.AbstractBlockingStub<ServerCommunicationBlockingStub> {
    private ServerCommunicationBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerCommunicationBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerCommunicationBlockingStub(channel, callOptions);
    }

    /**
     */
    public ServerCommunicationOuterClass.Pong checkStatus(ServerCommunicationOuterClass.Ping request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    public ServerCommunicationOuterClass.Empty election(ServerCommunicationOuterClass.Election request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getElectionMethod(), getCallOptions(), request);
    }

    /**
     */
    public ServerCommunicationOuterClass.CheckCoordinator checkCoordinator(ServerCommunicationOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckCoordinatorMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ServerCommunicationFutureStub extends io.grpc.stub.AbstractFutureStub<ServerCommunicationFutureStub> {
    private ServerCommunicationFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerCommunicationFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerCommunicationFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ServerCommunicationOuterClass.Pong> checkStatus(
        ServerCommunicationOuterClass.Ping request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckStatusMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ServerCommunicationOuterClass.Empty> election(
        ServerCommunicationOuterClass.Election request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getElectionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ServerCommunicationOuterClass.CheckCoordinator> checkCoordinator(
        ServerCommunicationOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckCoordinatorMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHECK_STATUS = 0;
  private static final int METHODID_ELECTION = 1;
  private static final int METHODID_CHECK_COORDINATOR = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServerCommunicationImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServerCommunicationImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CHECK_STATUS:
          serviceImpl.checkStatus((ServerCommunicationOuterClass.Ping) request,
              (io.grpc.stub.StreamObserver<ServerCommunicationOuterClass.Pong>) responseObserver);
          break;
        case METHODID_ELECTION:
          serviceImpl.election((ServerCommunicationOuterClass.Election) request,
              (io.grpc.stub.StreamObserver<ServerCommunicationOuterClass.Empty>) responseObserver);
          break;
        case METHODID_CHECK_COORDINATOR:
          serviceImpl.checkCoordinator((ServerCommunicationOuterClass.Empty) request,
              (io.grpc.stub.StreamObserver<ServerCommunicationOuterClass.CheckCoordinator>) responseObserver);
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

  private static abstract class ServerCommunicationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServerCommunicationBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ServerCommunicationOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ServerCommunication");
    }
  }

  private static final class ServerCommunicationFileDescriptorSupplier
      extends ServerCommunicationBaseDescriptorSupplier {
    ServerCommunicationFileDescriptorSupplier() {}
  }

  private static final class ServerCommunicationMethodDescriptorSupplier
      extends ServerCommunicationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServerCommunicationMethodDescriptorSupplier(String methodName) {
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
      synchronized (ServerCommunicationGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServerCommunicationFileDescriptorSupplier())
              .addMethod(getCheckStatusMethod())
              .addMethod(getElectionMethod())
              .addMethod(getCheckCoordinatorMethod())
              .build();
        }
      }
    }
    return result;
  }
}
