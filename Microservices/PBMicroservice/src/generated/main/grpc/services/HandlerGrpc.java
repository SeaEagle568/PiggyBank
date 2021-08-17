package services;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: event.proto")
public final class HandlerGrpc {

  private HandlerGrpc() {}

  public static final String SERVICE_NAME = "services.Handler";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<services.RPCEvent,
      services.RPCEventResult> getHandleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Handle",
      requestType = services.RPCEvent.class,
      responseType = services.RPCEventResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<services.RPCEvent,
      services.RPCEventResult> getHandleMethod() {
    io.grpc.MethodDescriptor<services.RPCEvent, services.RPCEventResult> getHandleMethod;
    if ((getHandleMethod = HandlerGrpc.getHandleMethod) == null) {
      synchronized (HandlerGrpc.class) {
        if ((getHandleMethod = HandlerGrpc.getHandleMethod) == null) {
          HandlerGrpc.getHandleMethod = getHandleMethod =
              io.grpc.MethodDescriptor.<services.RPCEvent, services.RPCEventResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Handle"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  services.RPCEvent.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  services.RPCEventResult.getDefaultInstance()))
              .setSchemaDescriptor(new HandlerMethodDescriptorSupplier("Handle"))
              .build();
        }
      }
    }
    return getHandleMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HandlerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HandlerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HandlerStub>() {
        @java.lang.Override
        public HandlerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HandlerStub(channel, callOptions);
        }
      };
    return HandlerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HandlerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HandlerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HandlerBlockingStub>() {
        @java.lang.Override
        public HandlerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HandlerBlockingStub(channel, callOptions);
        }
      };
    return HandlerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HandlerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HandlerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HandlerFutureStub>() {
        @java.lang.Override
        public HandlerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HandlerFutureStub(channel, callOptions);
        }
      };
    return HandlerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class HandlerImplBase implements io.grpc.BindableService {

    /**
     */
    public void handle(services.RPCEvent request,
        io.grpc.stub.StreamObserver<services.RPCEventResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHandleMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                services.RPCEvent,
                services.RPCEventResult>(
                  this, METHODID_HANDLE)))
          .build();
    }
  }

  /**
   */
  public static final class HandlerStub extends io.grpc.stub.AbstractAsyncStub<HandlerStub> {
    private HandlerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HandlerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HandlerStub(channel, callOptions);
    }

    /**
     */
    public void handle(services.RPCEvent request,
        io.grpc.stub.StreamObserver<services.RPCEventResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class HandlerBlockingStub extends io.grpc.stub.AbstractBlockingStub<HandlerBlockingStub> {
    private HandlerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HandlerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HandlerBlockingStub(channel, callOptions);
    }

    /**
     */
    public services.RPCEventResult handle(services.RPCEvent request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class HandlerFutureStub extends io.grpc.stub.AbstractFutureStub<HandlerFutureStub> {
    private HandlerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HandlerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HandlerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<services.RPCEventResult> handle(
        services.RPCEvent request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HANDLE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HandlerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HandlerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HANDLE:
          serviceImpl.handle((services.RPCEvent) request,
              (io.grpc.stub.StreamObserver<services.RPCEventResult>) responseObserver);
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

  private static abstract class HandlerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HandlerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return services.Event.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Handler");
    }
  }

  private static final class HandlerFileDescriptorSupplier
      extends HandlerBaseDescriptorSupplier {
    HandlerFileDescriptorSupplier() {}
  }

  private static final class HandlerMethodDescriptorSupplier
      extends HandlerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HandlerMethodDescriptorSupplier(String methodName) {
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
      synchronized (HandlerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HandlerFileDescriptorSupplier())
              .addMethod(getHandleMethod())
              .build();
        }
      }
    }
    return result;
  }
}
