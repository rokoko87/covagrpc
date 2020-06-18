package com.cova.ws.grpc.greet;

import com.cova.ws.grpc.greet.GreeterGrpc.GreeterImplBase;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreeterImpl extends GreeterImplBase{

	@Override
	public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
		
		String message = "Hello " + request.getName();
		
		HelloReply reply = HelloReply.newBuilder().setReply(message).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
		
		
	}
	
	
	
	
	

}
