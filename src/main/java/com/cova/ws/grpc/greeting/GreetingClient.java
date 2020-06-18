package com.cova.ws.grpc.greeting;

import com.cova.ws.grpc.greeting.GreetServiceGrpc.GreetServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
	
	
	public static void main (String [] args) {
		System.out.println ("Hello gRPC Client");
		
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50056)
				.usePlaintext()
				.build();
		
		//Creating Greet Client (Blocking - synchronus)
		System.out.println ("Creating Greet Client...");
		GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);
		
		//UnaryAppi
		//unaryAppi(greetClient);
		
		//Stream server
		stramingServer(greetClient);
		
		
		//Shutting down channel
		System.out.println ("Shutting down channel...");
		channel.shutdown();
	}
	
	
	public static void unaryAppi (GreetServiceBlockingStub greetClient) {
		//Creating protocol buffer greating message
		Greeting greeting  = Greeting.newBuilder()
				.setFirstName("Omar")
				.setLastName("Leonardo")
				.build();
		
		//Creating greetRequest with the message
		GreetRequest greetRequest = GreetRequest.newBuilder()
				.setGreeting(greeting)
				.build();
		
		//Call the service and get Response
		GreetResponse response = greetClient.greet(greetRequest);
		
		System.out.println ("Response: " + response.getResult());
	}
	
	
	public static void stramingServer (GreetServiceBlockingStub greetClient) {
		Greeting greeting  = Greeting.newBuilder()
				.setFirstName("Ricardo")
				.setLastName("Francisco")
				.build();
		
		
		//Creating greetRequest with the message
		GreetManyTimesRequest greetRequest = GreetManyTimesRequest.newBuilder()
				.setGreeting(greeting)
				.build();
		
		//Call the client and receive the streaming
		greetClient.greetManyTimes(greetRequest).forEachRemaining(response -> {
			System.out.println ("Response: " + response.getResult());
		});
		
	}
	

}
