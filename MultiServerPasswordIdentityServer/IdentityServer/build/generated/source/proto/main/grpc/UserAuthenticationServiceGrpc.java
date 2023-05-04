import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.53.0)",
    comments = "Source: Identity.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class UserAuthenticationServiceGrpc {

  private UserAuthenticationServiceGrpc() {}

  public static final String SERVICE_NAME = "UserAuthenticationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Identity.WriteRequest,
      Identity.WriteResponse> getWriteRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "writeRequest",
      requestType = Identity.WriteRequest.class,
      responseType = Identity.WriteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Identity.WriteRequest,
      Identity.WriteResponse> getWriteRequestMethod() {
    io.grpc.MethodDescriptor<Identity.WriteRequest, Identity.WriteResponse> getWriteRequestMethod;
    if ((getWriteRequestMethod = UserAuthenticationServiceGrpc.getWriteRequestMethod) == null) {
      synchronized (UserAuthenticationServiceGrpc.class) {
        if ((getWriteRequestMethod = UserAuthenticationServiceGrpc.getWriteRequestMethod) == null) {
          UserAuthenticationServiceGrpc.getWriteRequestMethod = getWriteRequestMethod =
              io.grpc.MethodDescriptor.<Identity.WriteRequest, Identity.WriteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "writeRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.WriteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.WriteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserAuthenticationServiceMethodDescriptorSupplier("writeRequest"))
              .build();
        }
      }
    }
    return getWriteRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Identity.ReadRequest,
      Identity.ReadResponse> getReadRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readRequest",
      requestType = Identity.ReadRequest.class,
      responseType = Identity.ReadResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Identity.ReadRequest,
      Identity.ReadResponse> getReadRequestMethod() {
    io.grpc.MethodDescriptor<Identity.ReadRequest, Identity.ReadResponse> getReadRequestMethod;
    if ((getReadRequestMethod = UserAuthenticationServiceGrpc.getReadRequestMethod) == null) {
      synchronized (UserAuthenticationServiceGrpc.class) {
        if ((getReadRequestMethod = UserAuthenticationServiceGrpc.getReadRequestMethod) == null) {
          UserAuthenticationServiceGrpc.getReadRequestMethod = getReadRequestMethod =
              io.grpc.MethodDescriptor.<Identity.ReadRequest, Identity.ReadResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.ReadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.ReadResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserAuthenticationServiceMethodDescriptorSupplier("readRequest"))
              .build();
        }
      }
    }
    return getReadRequestMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserAuthenticationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserAuthenticationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserAuthenticationServiceStub>() {
        @java.lang.Override
        public UserAuthenticationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserAuthenticationServiceStub(channel, callOptions);
        }
      };
    return UserAuthenticationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserAuthenticationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserAuthenticationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserAuthenticationServiceBlockingStub>() {
        @java.lang.Override
        public UserAuthenticationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserAuthenticationServiceBlockingStub(channel, callOptions);
        }
      };
    return UserAuthenticationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserAuthenticationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserAuthenticationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserAuthenticationServiceFutureStub>() {
        @java.lang.Override
        public UserAuthenticationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserAuthenticationServiceFutureStub(channel, callOptions);
        }
      };
    return UserAuthenticationServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class UserAuthenticationServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void writeRequest(Identity.WriteRequest request,
        io.grpc.stub.StreamObserver<Identity.WriteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWriteRequestMethod(), responseObserver);
    }

    /**
     */
    public void readRequest(Identity.ReadRequest request,
        io.grpc.stub.StreamObserver<Identity.ReadResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadRequestMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getWriteRequestMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Identity.WriteRequest,
                Identity.WriteResponse>(
                  this, METHODID_WRITE_REQUEST)))
          .addMethod(
            getReadRequestMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Identity.ReadRequest,
                Identity.ReadResponse>(
                  this, METHODID_READ_REQUEST)))
          .build();
    }
  }

  /**
   */
  public static final class UserAuthenticationServiceStub extends io.grpc.stub.AbstractAsyncStub<UserAuthenticationServiceStub> {
    private UserAuthenticationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserAuthenticationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserAuthenticationServiceStub(channel, callOptions);
    }

    /**
     */
    public void writeRequest(Identity.WriteRequest request,
        io.grpc.stub.StreamObserver<Identity.WriteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getWriteRequestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void readRequest(Identity.ReadRequest request,
        io.grpc.stub.StreamObserver<Identity.ReadResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadRequestMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UserAuthenticationServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<UserAuthenticationServiceBlockingStub> {
    private UserAuthenticationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserAuthenticationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserAuthenticationServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public Identity.WriteResponse writeRequest(Identity.WriteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getWriteRequestMethod(), getCallOptions(), request);
    }

    /**
     */
    public Identity.ReadResponse readRequest(Identity.ReadRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadRequestMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UserAuthenticationServiceFutureStub extends io.grpc.stub.AbstractFutureStub<UserAuthenticationServiceFutureStub> {
    private UserAuthenticationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserAuthenticationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserAuthenticationServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Identity.WriteResponse> writeRequest(
        Identity.WriteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getWriteRequestMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Identity.ReadResponse> readRequest(
        Identity.ReadRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadRequestMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_WRITE_REQUEST = 0;
  private static final int METHODID_READ_REQUEST = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UserAuthenticationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UserAuthenticationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_WRITE_REQUEST:
          serviceImpl.writeRequest((Identity.WriteRequest) request,
              (io.grpc.stub.StreamObserver<Identity.WriteResponse>) responseObserver);
          break;
        case METHODID_READ_REQUEST:
          serviceImpl.readRequest((Identity.ReadRequest) request,
              (io.grpc.stub.StreamObserver<Identity.ReadResponse>) responseObserver);
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

  private static abstract class UserAuthenticationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserAuthenticationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Identity.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserAuthenticationService");
    }
  }

  private static final class UserAuthenticationServiceFileDescriptorSupplier
      extends UserAuthenticationServiceBaseDescriptorSupplier {
    UserAuthenticationServiceFileDescriptorSupplier() {}
  }

  private static final class UserAuthenticationServiceMethodDescriptorSupplier
      extends UserAuthenticationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UserAuthenticationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (UserAuthenticationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserAuthenticationServiceFileDescriptorSupplier())
              .addMethod(getWriteRequestMethod())
              .addMethod(getReadRequestMethod())
              .build();
        }
      }
    }
    return result;
  }
}
