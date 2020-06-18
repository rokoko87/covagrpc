package com.cova.ws.controller;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.30.0)",
    comments = "Source: company/company.proto")
public final class CompanyServiceGrpc {

  private CompanyServiceGrpc() {}

  public static final String SERVICE_NAME = "com.cova.ws.controller.CompanyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.cova.ws.controller.FiltrosRequest,
      com.cova.ws.controller.CompanyResponse> getLoadCompaniesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LoadCompanies",
      requestType = com.cova.ws.controller.FiltrosRequest.class,
      responseType = com.cova.ws.controller.CompanyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cova.ws.controller.FiltrosRequest,
      com.cova.ws.controller.CompanyResponse> getLoadCompaniesMethod() {
    io.grpc.MethodDescriptor<com.cova.ws.controller.FiltrosRequest, com.cova.ws.controller.CompanyResponse> getLoadCompaniesMethod;
    if ((getLoadCompaniesMethod = CompanyServiceGrpc.getLoadCompaniesMethod) == null) {
      synchronized (CompanyServiceGrpc.class) {
        if ((getLoadCompaniesMethod = CompanyServiceGrpc.getLoadCompaniesMethod) == null) {
          CompanyServiceGrpc.getLoadCompaniesMethod = getLoadCompaniesMethod =
              io.grpc.MethodDescriptor.<com.cova.ws.controller.FiltrosRequest, com.cova.ws.controller.CompanyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LoadCompanies"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cova.ws.controller.FiltrosRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cova.ws.controller.CompanyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CompanyServiceMethodDescriptorSupplier("LoadCompanies"))
              .build();
        }
      }
    }
    return getLoadCompaniesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CompanyServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CompanyServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CompanyServiceStub>() {
        @java.lang.Override
        public CompanyServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CompanyServiceStub(channel, callOptions);
        }
      };
    return CompanyServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CompanyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CompanyServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CompanyServiceBlockingStub>() {
        @java.lang.Override
        public CompanyServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CompanyServiceBlockingStub(channel, callOptions);
        }
      };
    return CompanyServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CompanyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CompanyServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CompanyServiceFutureStub>() {
        @java.lang.Override
        public CompanyServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CompanyServiceFutureStub(channel, callOptions);
        }
      };
    return CompanyServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class CompanyServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *Unary
     * </pre>
     */
    public void loadCompanies(com.cova.ws.controller.FiltrosRequest request,
        io.grpc.stub.StreamObserver<com.cova.ws.controller.CompanyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getLoadCompaniesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLoadCompaniesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.cova.ws.controller.FiltrosRequest,
                com.cova.ws.controller.CompanyResponse>(
                  this, METHODID_LOAD_COMPANIES)))
          .build();
    }
  }

  /**
   */
  public static final class CompanyServiceStub extends io.grpc.stub.AbstractAsyncStub<CompanyServiceStub> {
    private CompanyServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CompanyServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CompanyServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *Unary
     * </pre>
     */
    public void loadCompanies(com.cova.ws.controller.FiltrosRequest request,
        io.grpc.stub.StreamObserver<com.cova.ws.controller.CompanyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLoadCompaniesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CompanyServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<CompanyServiceBlockingStub> {
    private CompanyServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CompanyServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CompanyServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *Unary
     * </pre>
     */
    public com.cova.ws.controller.CompanyResponse loadCompanies(com.cova.ws.controller.FiltrosRequest request) {
      return blockingUnaryCall(
          getChannel(), getLoadCompaniesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CompanyServiceFutureStub extends io.grpc.stub.AbstractFutureStub<CompanyServiceFutureStub> {
    private CompanyServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CompanyServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CompanyServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *Unary
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cova.ws.controller.CompanyResponse> loadCompanies(
        com.cova.ws.controller.FiltrosRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getLoadCompaniesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOAD_COMPANIES = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CompanyServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CompanyServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOAD_COMPANIES:
          serviceImpl.loadCompanies((com.cova.ws.controller.FiltrosRequest) request,
              (io.grpc.stub.StreamObserver<com.cova.ws.controller.CompanyResponse>) responseObserver);
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

  private static abstract class CompanyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CompanyServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cova.ws.controller.CompanyOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CompanyService");
    }
  }

  private static final class CompanyServiceFileDescriptorSupplier
      extends CompanyServiceBaseDescriptorSupplier {
    CompanyServiceFileDescriptorSupplier() {}
  }

  private static final class CompanyServiceMethodDescriptorSupplier
      extends CompanyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CompanyServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (CompanyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CompanyServiceFileDescriptorSupplier())
              .addMethod(getLoadCompaniesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
