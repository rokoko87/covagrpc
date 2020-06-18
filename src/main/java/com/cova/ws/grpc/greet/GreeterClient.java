package com.cova.ws.grpc.greet;



import com.cova.ws.grpc.greet.GreeterGrpc.GreeterBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreeterClient {
	
	
	public static void main (String [] args) {
		System.out.println ("Hello ServergRPC Client");
		
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9095)
				.usePlaintext()
				.build();
		
		//Creating Greet Client (Blocking - synchronus)
		System.out.println ("Creating Greet Client...");
		GreeterBlockingStub greetClient = GreeterGrpc.newBlockingStub(channel);
		
		HelloRequest greetRequest = HelloRequest.newBuilder()
				.setName("Puta")
				.build();
		
		//Call the service and get Response
		HelloReply response = greetClient.sayHello(greetRequest);
		
		System.out.println ("Response: " + response.getReply());
		
		
		
		//Shutting down channel
		//System.out.println ("Shutting down channel...");
		//channel.shutdown();
	}
	
	
	
	
	

}
