package com.cova.ws.controller;

import java.util.List;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CompanyClient {
	
	public static void main (String [] args) {
		System.out.println ("Hello Compqny Client");
		
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9095)
				.usePlaintext()
				.build();
		
		//Creating Greet Client (Blocking - synchronus)
		System.out.println ("Creating Company Client...");
		CompanyServiceGrpc.CompanyServiceBlockingStub companyClient = CompanyServiceGrpc.newBlockingStub(channel);
		
		FiltrosRequest filtros = FiltrosRequest.newBuilder()
				.setId(1)
				.setInstagram("Instagram")
				.setFacebook("Facebbok").build();
		
		//Call the service and get Response
		CompanyResponse response = companyClient.loadCompanies(filtros);
		
		List<Company> companies = response.getCompaniesList();
		
		for (Company c : companies)
			System.out.println (c);
		
		
		
		//Shutting down channel
		//System.out.println ("Shutting down channel...");
		//channel.shutdown();
	}
	

}
