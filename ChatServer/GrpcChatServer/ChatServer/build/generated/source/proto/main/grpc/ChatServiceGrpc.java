import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.53.0)",
    comments = "Source: Chat.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChatServiceGrpc {

  private ChatServiceGrpc() {}

  public static final String SERVICE_NAME = "ChatService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Chat.ClientChannelAction,
      Chat.ServerChannelActionMessage> getListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "list",
      requestType = Chat.ClientChannelAction.class,
      responseType = Chat.ServerChannelActionMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<Chat.ClientChannelAction,
      Chat.ServerChannelActionMessage> getListMethod() {
    io.grpc.MethodDescriptor<Chat.ClientChannelAction, Chat.ServerChannelActionMessage> getListMethod;
    if ((getListMethod = ChatServiceGrpc.getListMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getListMethod = ChatServiceGrpc.getListMethod) == null) {
          ChatServiceGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<Chat.ClientChannelAction, Chat.ServerChannelActionMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "list"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ClientChannelAction.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ServerChannelActionMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("list"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Chat.ClientChannelAction,
      Chat.ServerChannelActionMessage> getJoinMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "join",
      requestType = Chat.ClientChannelAction.class,
      responseType = Chat.ServerChannelActionMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<Chat.ClientChannelAction,
      Chat.ServerChannelActionMessage> getJoinMethod() {
    io.grpc.MethodDescriptor<Chat.ClientChannelAction, Chat.ServerChannelActionMessage> getJoinMethod;
    if ((getJoinMethod = ChatServiceGrpc.getJoinMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getJoinMethod = ChatServiceGrpc.getJoinMethod) == null) {
          ChatServiceGrpc.getJoinMethod = getJoinMethod =
              io.grpc.MethodDescriptor.<Chat.ClientChannelAction, Chat.ServerChannelActionMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "join"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ClientChannelAction.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ServerChannelActionMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("join"))
              .build();
        }
      }
    }
    return getJoinMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Chat.ClientChannelAction,
      Chat.ServerChannelActionMessage> getLeaveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "leave",
      requestType = Chat.ClientChannelAction.class,
      responseType = Chat.ServerChannelActionMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<Chat.ClientChannelAction,
      Chat.ServerChannelActionMessage> getLeaveMethod() {
    io.grpc.MethodDescriptor<Chat.ClientChannelAction, Chat.ServerChannelActionMessage> getLeaveMethod;
    if ((getLeaveMethod = ChatServiceGrpc.getLeaveMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getLeaveMethod = ChatServiceGrpc.getLeaveMethod) == null) {
          ChatServiceGrpc.getLeaveMethod = getLeaveMethod =
              io.grpc.MethodDescriptor.<Chat.ClientChannelAction, Chat.ServerChannelActionMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "leave"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ClientChannelAction.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ServerChannelActionMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("leave"))
              .build();
        }
      }
    }
    return getLeaveMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Chat.ClientPostMessage,
      Chat.ServerChannelActionMessage> getPostMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "post",
      requestType = Chat.ClientPostMessage.class,
      responseType = Chat.ServerChannelActionMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<Chat.ClientPostMessage,
      Chat.ServerChannelActionMessage> getPostMethod() {
    io.grpc.MethodDescriptor<Chat.ClientPostMessage, Chat.ServerChannelActionMessage> getPostMethod;
    if ((getPostMethod = ChatServiceGrpc.getPostMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getPostMethod = ChatServiceGrpc.getPostMethod) == null) {
          ChatServiceGrpc.getPostMethod = getPostMethod =
              io.grpc.MethodDescriptor.<Chat.ClientPostMessage, Chat.ServerChannelActionMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "post"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ClientPostMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ServerChannelActionMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("post"))
              .build();
        }
      }
    }
    return getPostMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Chat.ClientChannelAction,
      Chat.ServerChannelActionMessage> getHelpMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "help",
      requestType = Chat.ClientChannelAction.class,
      responseType = Chat.ServerChannelActionMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<Chat.ClientChannelAction,
      Chat.ServerChannelActionMessage> getHelpMethod() {
    io.grpc.MethodDescriptor<Chat.ClientChannelAction, Chat.ServerChannelActionMessage> getHelpMethod;
    if ((getHelpMethod = ChatServiceGrpc.getHelpMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getHelpMethod = ChatServiceGrpc.getHelpMethod) == null) {
          ChatServiceGrpc.getHelpMethod = getHelpMethod =
              io.grpc.MethodDescriptor.<Chat.ClientChannelAction, Chat.ServerChannelActionMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "help"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ClientChannelAction.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ServerChannelActionMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("help"))
              .build();
        }
      }
    }
    return getHelpMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Chat.ClientChannelAction,
      Chat.ServerChannelActionMessage> getReceiveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "receive",
      requestType = Chat.ClientChannelAction.class,
      responseType = Chat.ServerChannelActionMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<Chat.ClientChannelAction,
      Chat.ServerChannelActionMessage> getReceiveMethod() {
    io.grpc.MethodDescriptor<Chat.ClientChannelAction, Chat.ServerChannelActionMessage> getReceiveMethod;
    if ((getReceiveMethod = ChatServiceGrpc.getReceiveMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getReceiveMethod = ChatServiceGrpc.getReceiveMethod) == null) {
          ChatServiceGrpc.getReceiveMethod = getReceiveMethod =
              io.grpc.MethodDescriptor.<Chat.ClientChannelAction, Chat.ServerChannelActionMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "receive"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ClientChannelAction.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Chat.ServerChannelActionMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("receive"))
              .build();
        }
      }
    }
    return getReceiveMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub>() {
        @java.lang.Override
        public ChatServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceStub(channel, callOptions);
        }
      };
    return ChatServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub>() {
        @java.lang.Override
        public ChatServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceBlockingStub(channel, callOptions);
        }
      };
    return ChatServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub>() {
        @java.lang.Override
        public ChatServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceFutureStub(channel, callOptions);
        }
      };
    return ChatServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ChatServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void list(Chat.ClientChannelAction request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMethod(), responseObserver);
    }

    /**
     */
    public void join(Chat.ClientChannelAction request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getJoinMethod(), responseObserver);
    }

    /**
     */
    public void leave(Chat.ClientChannelAction request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLeaveMethod(), responseObserver);
    }

    /**
     */
    public void post(Chat.ClientPostMessage request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPostMethod(), responseObserver);
    }

    /**
     */
    public void help(Chat.ClientChannelAction request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHelpMethod(), responseObserver);
    }

    /**
     */
    public void receive(Chat.ClientChannelAction request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReceiveMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                Chat.ClientChannelAction,
                Chat.ServerChannelActionMessage>(
                  this, METHODID_LIST)))
          .addMethod(
            getJoinMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                Chat.ClientChannelAction,
                Chat.ServerChannelActionMessage>(
                  this, METHODID_JOIN)))
          .addMethod(
            getLeaveMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                Chat.ClientChannelAction,
                Chat.ServerChannelActionMessage>(
                  this, METHODID_LEAVE)))
          .addMethod(
            getPostMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                Chat.ClientPostMessage,
                Chat.ServerChannelActionMessage>(
                  this, METHODID_POST)))
          .addMethod(
            getHelpMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                Chat.ClientChannelAction,
                Chat.ServerChannelActionMessage>(
                  this, METHODID_HELP)))
          .addMethod(
            getReceiveMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                Chat.ClientChannelAction,
                Chat.ServerChannelActionMessage>(
                  this, METHODID_RECEIVE)))
          .build();
    }
  }

  /**
   */
  public static final class ChatServiceStub extends io.grpc.stub.AbstractAsyncStub<ChatServiceStub> {
    private ChatServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceStub(channel, callOptions);
    }

    /**
     */
    public void list(Chat.ClientChannelAction request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void join(Chat.ClientChannelAction request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getJoinMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void leave(Chat.ClientChannelAction request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getLeaveMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void post(Chat.ClientPostMessage request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getPostMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void help(Chat.ClientChannelAction request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getHelpMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void receive(Chat.ClientChannelAction request,
        io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getReceiveMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ChatServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ChatServiceBlockingStub> {
    private ChatServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<Chat.ServerChannelActionMessage> list(
        Chat.ClientChannelAction request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<Chat.ServerChannelActionMessage> join(
        Chat.ClientChannelAction request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getJoinMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<Chat.ServerChannelActionMessage> leave(
        Chat.ClientChannelAction request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getLeaveMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<Chat.ServerChannelActionMessage> post(
        Chat.ClientPostMessage request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getPostMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<Chat.ServerChannelActionMessage> help(
        Chat.ClientChannelAction request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getHelpMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<Chat.ServerChannelActionMessage> receive(
        Chat.ClientChannelAction request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getReceiveMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ChatServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ChatServiceFutureStub> {
    private ChatServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_LIST = 0;
  private static final int METHODID_JOIN = 1;
  private static final int METHODID_LEAVE = 2;
  private static final int METHODID_POST = 3;
  private static final int METHODID_HELP = 4;
  private static final int METHODID_RECEIVE = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChatServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChatServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST:
          serviceImpl.list((Chat.ClientChannelAction) request,
              (io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage>) responseObserver);
          break;
        case METHODID_JOIN:
          serviceImpl.join((Chat.ClientChannelAction) request,
              (io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage>) responseObserver);
          break;
        case METHODID_LEAVE:
          serviceImpl.leave((Chat.ClientChannelAction) request,
              (io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage>) responseObserver);
          break;
        case METHODID_POST:
          serviceImpl.post((Chat.ClientPostMessage) request,
              (io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage>) responseObserver);
          break;
        case METHODID_HELP:
          serviceImpl.help((Chat.ClientChannelAction) request,
              (io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage>) responseObserver);
          break;
        case METHODID_RECEIVE:
          serviceImpl.receive((Chat.ClientChannelAction) request,
              (io.grpc.stub.StreamObserver<Chat.ServerChannelActionMessage>) responseObserver);
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

  private static abstract class ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChatServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Chat.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChatService");
    }
  }

  private static final class ChatServiceFileDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier {
    ChatServiceFileDescriptorSupplier() {}
  }

  private static final class ChatServiceMethodDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ChatServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ChatServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChatServiceFileDescriptorSupplier())
              .addMethod(getListMethod())
              .addMethod(getJoinMethod())
              .addMethod(getLeaveMethod())
              .addMethod(getPostMethod())
              .addMethod(getHelpMethod())
              .addMethod(getReceiveMethod())
              .build();
        }
      }
    }
    return result;
  }
}
