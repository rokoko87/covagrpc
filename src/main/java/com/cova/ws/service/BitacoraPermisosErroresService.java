package com.cova.ws.service;

import com.cova.ws.constants.BitacoraOperacion;
import com.cova.ws.constants.ServiciosEnum;
import com.cova.ws.exception.BadRequestException;
import com.cova.ws.exception.ControlledException;
import com.cova.ws.exception.ManageExcepcion;
import com.cova.ws.exception.PermisosException;
import com.cova.ws.sqlmanager.SQLDBException;



public interface BitacoraPermisosErroresService {
	
	public void insertBitacora (BitacoraOperacion operacion, Long companyId, String user, String descripcion, String filtros, String statusAntes, String statusDespues) throws SQLDBException, Exception;
		
	public void setError (Exception e, ServiciosEnum servicio, String user, Long companyId) throws ManageExcepcion, PermisosException, ControlledException, BadRequestException;
	
	public void validarPerfilPermisiosServicio (ServiciosEnum servicio, String user, Long companyId) throws PermisosException;
	
	
	
}
