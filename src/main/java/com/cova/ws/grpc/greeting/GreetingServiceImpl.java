package com.cova.ws.grpc.greeting;

import io.grpc.stub.StreamObserver;


public class GreetingServiceImpl extends GreetServiceGrpc.GreetServiceImplBase{
	
	@Override
	public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
		
		//Extract the request info
		Greeting  greeting = request.getGreeting();
		String firstName = greeting.getFirstName();
		String lastName = greeting.getLastName();
		
		//Creaate the response
		String result = "Hello " + firstName + " " + lastName + "!.";
		GreetResponse response = GreetResponse.newBuilder().setResult(result).build();
		
		//Send the response
		responseObserver.onNext(response);
		
		//Complete the response call
		responseObserver.onCompleted();
		
	}

	@Override
	public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {
		
		//Extract the request info
		Greeting  greeting = request.getGreeting();
		String firstName = greeting.getFirstName();
		String lastName = greeting.getLastName();
		
		try {
			for (int i=0; i< 10; i++) {
				String result =  "Hello " + firstName + " " + lastName + " " + i + "time !.";
				GreetManyTimesResponse response = GreetManyTimesResponse.newBuilder().setResult(result).build();
				responseObserver.onNext(response);
				
				Thread.sleep(1000l);
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			//Complete the response call
			responseObserver.onCompleted();
		}
	}
	
	
	
	

}
