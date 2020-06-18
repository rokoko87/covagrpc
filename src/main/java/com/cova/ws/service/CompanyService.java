package com.cova.ws.service;

import java.util.List;

import com.cova.ws.exception.BadRequestException;
import com.cova.ws.exception.ControlledException;
import com.cova.ws.exception.ManageExcepcion;
import com.cova.ws.exception.PermisosException;
import com.cova.ws.rest.pojos.CompanyPOJO;
import com.cova.ws.rest.pojos.CompanySearcherPOJO;




public interface CompanyService {
	
	
	public List<CompanySearcherPOJO> loadCompanies (CompanyPOJO request) throws ManageExcepcion, PermisosException, ControlledException, BadRequestException;
	
	
	
}
