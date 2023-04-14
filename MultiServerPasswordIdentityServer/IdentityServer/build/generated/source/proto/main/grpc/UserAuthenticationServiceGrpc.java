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
  private static volatile io.grpc.MethodDescriptor<Identity.User,
      Identity.ServerActions> getCreateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create",
      requestType = Identity.User.class,
      responseType = Identity.ServerActions.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Identity.User,
      Identity.ServerActions> getCreateMethod() {
    io.grpc.MethodDescriptor<Identity.User, Identity.ServerActions> getCreateMethod;
    if ((getCreateMethod = UserAuthenticationServiceGrpc.getCreateMethod) == null) {
      synchronized (UserAuthenticationServiceGrpc.class) {
        if ((getCreateMethod = UserAuthenticationServiceGrpc.getCreateMethod) == null) {
          UserAuthenticationServiceGrpc.getCreateMethod = getCreateMethod =
              io.grpc.MethodDescriptor.<Identity.User, Identity.ServerActions>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.User.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.ServerActions.getDefaultInstance()))
              .setSchemaDescriptor(new UserAuthenticationServiceMethodDescriptorSupplier("create"))
              .build();
        }
      }
    }
    return getCreateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Identity.User,
      Identity.ServerActions> getModifyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "modify",
      requestType = Identity.User.class,
      responseType = Identity.ServerActions.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Identity.User,
      Identity.ServerActions> getModifyMethod() {
    io.grpc.MethodDescriptor<Identity.User, Identity.ServerActions> getModifyMethod;
    if ((getModifyMethod = UserAuthenticationServiceGrpc.getModifyMethod) == null) {
      synchronized (UserAuthenticationServiceGrpc.class) {
        if ((getModifyMethod = UserAuthenticationServiceGrpc.getModifyMethod) == null) {
          UserAuthenticationServiceGrpc.getModifyMethod = getModifyMethod =
              io.grpc.MethodDescriptor.<Identity.User, Identity.ServerActions>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "modify"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.User.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.ServerActions.getDefaultInstance()))
              .setSchemaDescriptor(new UserAuthenticationServiceMethodDescriptorSupplier("modify"))
              .build();
        }
      }
    }
    return getModifyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Identity.User,
      Identity.ServerActions> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "delete",
      requestType = Identity.User.class,
      responseType = Identity.ServerActions.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Identity.User,
      Identity.ServerActions> getDeleteMethod() {
    io.grpc.MethodDescriptor<Identity.User, Identity.ServerActions> getDeleteMethod;
    if ((getDeleteMethod = UserAuthenticationServiceGrpc.getDeleteMethod) == null) {
      synchronized (UserAuthenticationServiceGrpc.class) {
        if ((getDeleteMethod = UserAuthenticationServiceGrpc.getDeleteMethod) == null) {
          UserAuthenticationServiceGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<Identity.User, Identity.ServerActions>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.User.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.ServerActions.getDefaultInstance()))
              .setSchemaDescriptor(new UserAuthenticationServiceMethodDescriptorSupplier("delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Identity.User,
      Identity.ServerActions> getLookupMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "lookup",
      requestType = Identity.User.class,
      responseType = Identity.ServerActions.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Identity.User,
      Identity.ServerActions> getLookupMethod() {
    io.grpc.MethodDescriptor<Identity.User, Identity.ServerActions> getLookupMethod;
    if ((getLookupMethod = UserAuthenticationServiceGrpc.getLookupMethod) == null) {
      synchronized (UserAuthenticationServiceGrpc.class) {
        if ((getLookupMethod = UserAuthenticationServiceGrpc.getLookupMethod) == null) {
          UserAuthenticationServiceGrpc.getLookupMethod = getLookupMethod =
              io.grpc.MethodDescriptor.<Identity.User, Identity.ServerActions>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "lookup"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.User.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.ServerActions.getDefaultInstance()))
              .setSchemaDescriptor(new UserAuthenticationServiceMethodDescriptorSupplier("lookup"))
              .build();
        }
      }
    }
    return getLookupMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Identity.User,
      Identity.ServerActions> getReverseLookupMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reverseLookup",
      requestType = Identity.User.class,
      responseType = Identity.ServerActions.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Identity.User,
      Identity.ServerActions> getReverseLookupMethod() {
    io.grpc.MethodDescriptor<Identity.User, Identity.ServerActions> getReverseLookupMethod;
    if ((getReverseLookupMethod = UserAuthenticationServiceGrpc.getReverseLookupMethod) == null) {
      synchronized (UserAuthenticationServiceGrpc.class) {
        if ((getReverseLookupMethod = UserAuthenticationServiceGrpc.getReverseLookupMethod) == null) {
          UserAuthenticationServiceGrpc.getReverseLookupMethod = getReverseLookupMethod =
              io.grpc.MethodDescriptor.<Identity.User, Identity.ServerActions>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reverseLookup"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.User.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.ServerActions.getDefaultInstance()))
              .setSchemaDescriptor(new UserAuthenticationServiceMethodDescriptorSupplier("reverseLookup"))
              .build();
        }
      }
    }
    return getReverseLookupMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Identity.EmptyRequest,
      Identity.StringResponse> getListLoginsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listLogins",
      requestType = Identity.EmptyRequest.class,
      responseType = Identity.StringResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Identity.EmptyRequest,
      Identity.StringResponse> getListLoginsMethod() {
    io.grpc.MethodDescriptor<Identity.EmptyRequest, Identity.StringResponse> getListLoginsMethod;
    if ((getListLoginsMethod = UserAuthenticationServiceGrpc.getListLoginsMethod) == null) {
      synchronized (UserAuthenticationServiceGrpc.class) {
        if ((getListLoginsMethod = UserAuthenticationServiceGrpc.getListLoginsMethod) == null) {
          UserAuthenticationServiceGrpc.getListLoginsMethod = getListLoginsMethod =
              io.grpc.MethodDescriptor.<Identity.EmptyRequest, Identity.StringResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listLogins"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.StringResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserAuthenticationServiceMethodDescriptorSupplier("listLogins"))
              .build();
        }
      }
    }
    return getListLoginsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Identity.EmptyRequest,
      Identity.StringResponse> getListIdsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listIds",
      requestType = Identity.EmptyRequest.class,
      responseType = Identity.StringResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Identity.EmptyRequest,
      Identity.StringResponse> getListIdsMethod() {
    io.grpc.MethodDescriptor<Identity.EmptyRequest, Identity.StringResponse> getListIdsMethod;
    if ((getListIdsMethod = UserAuthenticationServiceGrpc.getListIdsMethod) == null) {
      synchronized (UserAuthenticationServiceGrpc.class) {
        if ((getListIdsMethod = UserAuthenticationServiceGrpc.getListIdsMethod) == null) {
          UserAuthenticationServiceGrpc.getListIdsMethod = getListIdsMethod =
              io.grpc.MethodDescriptor.<Identity.EmptyRequest, Identity.StringResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listIds"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.StringResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserAuthenticationServiceMethodDescriptorSupplier("listIds"))
              .build();
        }
      }
    }
    return getListIdsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Identity.EmptyRequest,
      Identity.StringResponse> getListAllInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listAllInfo",
      requestType = Identity.EmptyRequest.class,
      responseType = Identity.StringResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Identity.EmptyRequest,
      Identity.StringResponse> getListAllInfoMethod() {
    io.grpc.MethodDescriptor<Identity.EmptyRequest, Identity.StringResponse> getListAllInfoMethod;
    if ((getListAllInfoMethod = UserAuthenticationServiceGrpc.getListAllInfoMethod) == null) {
      synchronized (UserAuthenticationServiceGrpc.class) {
        if ((getListAllInfoMethod = UserAuthenticationServiceGrpc.getListAllInfoMethod) == null) {
          UserAuthenticationServiceGrpc.getListAllInfoMethod = getListAllInfoMethod =
              io.grpc.MethodDescriptor.<Identity.EmptyRequest, Identity.StringResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listAllInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.StringResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserAuthenticationServiceMethodDescriptorSupplier("listAllInfo"))
              .build();
        }
      }
    }
    return getListAllInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Identity.EmptyRequest,
      Identity.StringResponse> getHelpMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "help",
      requestType = Identity.EmptyRequest.class,
      responseType = Identity.StringResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Identity.EmptyRequest,
      Identity.StringResponse> getHelpMethod() {
    io.grpc.MethodDescriptor<Identity.EmptyRequest, Identity.StringResponse> getHelpMethod;
    if ((getHelpMethod = UserAuthenticationServiceGrpc.getHelpMethod) == null) {
      synchronized (UserAuthenticationServiceGrpc.class) {
        if ((getHelpMethod = UserAuthenticationServiceGrpc.getHelpMethod) == null) {
          UserAuthenticationServiceGrpc.getHelpMethod = getHelpMethod =
              io.grpc.MethodDescriptor.<Identity.EmptyRequest, Identity.StringResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "help"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Identity.StringResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserAuthenticationServiceMethodDescriptorSupplier("help"))
              .build();
        }
      }
    }
    return getHelpMethod;
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
    public void create(Identity.User request,
        io.grpc.stub.StreamObserver<Identity.ServerActions> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateMethod(), responseObserver);
    }

    /**
     */
    public void modify(Identity.User request,
        io.grpc.stub.StreamObserver<Identity.ServerActions> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getModifyMethod(), responseObserver);
    }

    /**
     */
    public void delete(Identity.User request,
        io.grpc.stub.StreamObserver<Identity.ServerActions> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    /**
     */
    public void lookup(Identity.User request,
        io.grpc.stub.StreamObserver<Identity.ServerActions> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLookupMethod(), responseObserver);
    }

    /**
     */
    public void reverseLookup(Identity.User request,
        io.grpc.stub.StreamObserver<Identity.ServerActions> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReverseLookupMethod(), responseObserver);
    }

    /**
     */
    public void listLogins(Identity.EmptyRequest request,
        io.grpc.stub.StreamObserver<Identity.StringResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListLoginsMethod(), responseObserver);
    }

    /**
     */
    public void listIds(Identity.EmptyRequest request,
        io.grpc.stub.StreamObserver<Identity.StringResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListIdsMethod(), responseObserver);
    }

    /**
     */
    public void listAllInfo(Identity.EmptyRequest request,
        io.grpc.stub.StreamObserver<Identity.StringResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListAllInfoMethod(), responseObserver);
    }

    /**
     */
    public void help(Identity.EmptyRequest request,
        io.grpc.stub.StreamObserver<Identity.StringResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHelpMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Identity.User,
                Identity.ServerActions>(
                  this, METHODID_CREATE)))
          .addMethod(
            getModifyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Identity.User,
                Identity.ServerActions>(
                  this, METHODID_MODIFY)))
          .addMethod(
            getDeleteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Identity.User,
                Identity.ServerActions>(
                  this, METHODID_DELETE)))
          .addMethod(
            getLookupMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Identity.User,
                Identity.ServerActions>(
                  this, METHODID_LOOKUP)))
          .addMethod(
            getReverseLookupMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Identity.User,
                Identity.ServerActions>(
                  this, METHODID_REVERSE_LOOKUP)))
          .addMethod(
            getListLoginsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Identity.EmptyRequest,
                Identity.StringResponse>(
                  this, METHODID_LIST_LOGINS)))
          .addMethod(
            getListIdsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Identity.EmptyRequest,
                Identity.StringResponse>(
                  this, METHODID_LIST_IDS)))
          .addMethod(
            getListAllInfoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Identity.EmptyRequest,
                Identity.StringResponse>(
                  this, METHODID_LIST_ALL_INFO)))
          .addMethod(
            getHelpMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Identity.EmptyRequest,
                Identity.StringResponse>(
                  this, METHODID_HELP)))
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
    public void create(Identity.User request,
        io.grpc.stub.StreamObserver<Identity.ServerActions> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void modify(Identity.User request,
        io.grpc.stub.StreamObserver<Identity.ServerActions> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getModifyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(Identity.User request,
        io.grpc.stub.StreamObserver<Identity.ServerActions> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void lookup(Identity.User request,
        io.grpc.stub.StreamObserver<Identity.ServerActions> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLookupMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void reverseLookup(Identity.User request,
        io.grpc.stub.StreamObserver<Identity.ServerActions> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReverseLookupMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listLogins(Identity.EmptyRequest request,
        io.grpc.stub.StreamObserver<Identity.StringResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListLoginsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listIds(Identity.EmptyRequest request,
        io.grpc.stub.StreamObserver<Identity.StringResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListIdsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listAllInfo(Identity.EmptyRequest request,
        io.grpc.stub.StreamObserver<Identity.StringResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListAllInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void help(Identity.EmptyRequest request,
        io.grpc.stub.StreamObserver<Identity.StringResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHelpMethod(), getCallOptions()), request, responseObserver);
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
    public Identity.ServerActions create(Identity.User request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateMethod(), getCallOptions(), request);
    }

    /**
     */
    public Identity.ServerActions modify(Identity.User request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getModifyMethod(), getCallOptions(), request);
    }

    /**
     */
    public Identity.ServerActions delete(Identity.User request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }

    /**
     */
    public Identity.ServerActions lookup(Identity.User request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLookupMethod(), getCallOptions(), request);
    }

    /**
     */
    public Identity.ServerActions reverseLookup(Identity.User request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReverseLookupMethod(), getCallOptions(), request);
    }

    /**
     */
    public Identity.StringResponse listLogins(Identity.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListLoginsMethod(), getCallOptions(), request);
    }

    /**
     */
    public Identity.StringResponse listIds(Identity.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListIdsMethod(), getCallOptions(), request);
    }

    /**
     */
    public Identity.StringResponse listAllInfo(Identity.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListAllInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public Identity.StringResponse help(Identity.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHelpMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<Identity.ServerActions> create(
        Identity.User request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Identity.ServerActions> modify(
        Identity.User request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getModifyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Identity.ServerActions> delete(
        Identity.User request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Identity.ServerActions> lookup(
        Identity.User request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLookupMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Identity.ServerActions> reverseLookup(
        Identity.User request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReverseLookupMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Identity.StringResponse> listLogins(
        Identity.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListLoginsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Identity.StringResponse> listIds(
        Identity.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListIdsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Identity.StringResponse> listAllInfo(
        Identity.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListAllInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Identity.StringResponse> help(
        Identity.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHelpMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE = 0;
  private static final int METHODID_MODIFY = 1;
  private static final int METHODID_DELETE = 2;
  private static final int METHODID_LOOKUP = 3;
  private static final int METHODID_REVERSE_LOOKUP = 4;
  private static final int METHODID_LIST_LOGINS = 5;
  private static final int METHODID_LIST_IDS = 6;
  private static final int METHODID_LIST_ALL_INFO = 7;
  private static final int METHODID_HELP = 8;

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
        case METHODID_CREATE:
          serviceImpl.create((Identity.User) request,
              (io.grpc.stub.StreamObserver<Identity.ServerActions>) responseObserver);
          break;
        case METHODID_MODIFY:
          serviceImpl.modify((Identity.User) request,
              (io.grpc.stub.StreamObserver<Identity.ServerActions>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((Identity.User) request,
              (io.grpc.stub.StreamObserver<Identity.ServerActions>) responseObserver);
          break;
        case METHODID_LOOKUP:
          serviceImpl.lookup((Identity.User) request,
              (io.grpc.stub.StreamObserver<Identity.ServerActions>) responseObserver);
          break;
        case METHODID_REVERSE_LOOKUP:
          serviceImpl.reverseLookup((Identity.User) request,
              (io.grpc.stub.StreamObserver<Identity.ServerActions>) responseObserver);
          break;
        case METHODID_LIST_LOGINS:
          serviceImpl.listLogins((Identity.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<Identity.StringResponse>) responseObserver);
          break;
        case METHODID_LIST_IDS:
          serviceImpl.listIds((Identity.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<Identity.StringResponse>) responseObserver);
          break;
        case METHODID_LIST_ALL_INFO:
          serviceImpl.listAllInfo((Identity.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<Identity.StringResponse>) responseObserver);
          break;
        case METHODID_HELP:
          serviceImpl.help((Identity.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<Identity.StringResponse>) responseObserver);
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
              .addMethod(getCreateMethod())
              .addMethod(getModifyMethod())
              .addMethod(getDeleteMethod())
              .addMethod(getLookupMethod())
              .addMethod(getReverseLookupMethod())
              .addMethod(getListLoginsMethod())
              .addMethod(getListIdsMethod())
              .addMethod(getListAllInfoMethod())
              .addMethod(getHelpMethod())
              .build();
        }
      }
    }
    return result;
  }
}
