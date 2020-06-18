package com.cova.ws.service;


import java.util.List;

import com.cova.ws.exception.BadRequestException;
import com.cova.ws.exception.ControlledException;
import com.cova.ws.exception.ManageExcepcion;
import com.cova.ws.exception.PermisosException;
import com.cova.ws.rest.pojos.BitacoraErroresPOJO;
import com.cova.ws.rest.pojos.BitacoraPOJO;
import com.cova.ws.rest.request.EmptyRequest;
import com.cova.ws.rest.request.POJORequest;




public interface BitacoraService {
	
	public List<BitacoraPOJO> find (POJORequest<BitacoraPOJO> request) throws ManageExcepcion, PermisosException, ControlledException, BadRequestException;
	
	public List<BitacoraErroresPOJO> bitacoraErrores (EmptyRequest request) throws ManageExcepcion, PermisosException, ControlledException, BadRequestException;
	
}
