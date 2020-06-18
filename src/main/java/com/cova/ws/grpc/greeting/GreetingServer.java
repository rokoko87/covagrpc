package com.cova.ws.grpc.greeting;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;


public class GreetingServer {
	
	
	public static void main (String [] args) {
		System.out.println ("Hello gRPC Server");
		
		
		Server server = ServerBuilder.forPort(50056)
				.addService(new GreetingServiceImpl())
				.build();
		try {
			server.start();
			server.awaitTermination();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		Runtime.getRuntime().addShutdownHook(new Thread( () ->  {
			System.out.println("Receiving shutdown request");
			server.shutdown();
			System.out.println("Succesfull Stoped");
		}));
	}

}
