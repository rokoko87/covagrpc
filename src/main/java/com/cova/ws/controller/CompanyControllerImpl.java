package com.cova.ws.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cova.ws.exception.BadRequestException;
import com.cova.ws.exception.ControlledException;
import com.cova.ws.exception.ManageExcepcion;
import com.cova.ws.exception.PermisosException;
import com.cova.ws.rest.pojos.CompanyPOJO;
import com.cova.ws.rest.pojos.CompanySearcherPOJO;
import com.cova.ws.service.CompanyService;

import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CompanyControllerImpl extends CompanyServiceGrpc.CompanyServiceImplBase{

	@Autowired
	private CompanyService companyService;
	
	@Override
	public void loadCompanies(FiltrosRequest request,
	        io.grpc.stub.StreamObserver<CompanyResponse> responseObserver) {
	    
		System.out.println ("Se imprime Request");
		System.out.println (request);
		
		CompanyPOJO filtros =  new CompanyPOJO();
		filtros.setId(request.getId() != 0 ? new Long(request.getId()) : null);
		filtros.setNombre(request.getNombre() != null ? request.getNombre() : null);
		
		List<Company> empresas = new ArrayList<Company>();
		
		try {
			List<CompanySearcherPOJO> companies = companyService.loadCompanies(filtros);
			if (companies != null && !companies.isEmpty()) {
				for (CompanySearcherPOJO c : companies) {
					Company company = Company.newBuilder().setNombre(c.getNombre())
						    .setOwner(c.getOwner())
						    .setCorreoElectronico(c.getCorreoElectronico())
						    .setCompanyId(c.getCompanyId() != null ? c.getCompanyId().intValue(): 0)
						    .setDescripcion(c.getDescripcion())
						    .setCorreoElectronico(c.getCorreoElectronico())
						    .build();
					empresas.add(company);
					
				}
			}
			
			
		} catch (ManageExcepcion | PermisosException | ControlledException | BadRequestException e) {
			e.printStackTrace();
		}
		
		
		CompanyResponse reply = CompanyResponse.newBuilder().addAllCompanies(empresas).build();
		
		
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
		
		
	}
	
}
