package com.cova.ws.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cova.ws.constants.BitacoraOperacion;
import com.cova.ws.constants.ServiciosEnum;
import com.cova.ws.entities.Bitacora;
import com.cova.ws.exception.BadRequestException;
import com.cova.ws.exception.ControlledException;
import com.cova.ws.exception.ManageExcepcion;
import com.cova.ws.exception.PermisosException;
import com.cova.ws.service.BitacoraPermisosErroresService;
import com.cova.ws.sqlmanager.DAO;
import com.cova.ws.sqlmanager.SQLDBException;
import com.cova.ws.util.Util;


@Service
public class BitacoraPermisosErroresServiceImpl implements  BitacoraPermisosErroresService {
	
	public static Logger log = LoggerFactory.getLogger(BitacoraPermisosErroresServiceImpl.class);
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private DAO DAO;
	
	
	
	
	
	
	
	
	
	/**********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 * 
	 *                              B  I  T  A  C  O  R  A       D  E      O  P  E  R  A  C  I  O  N
	 * 
	 * *********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 **********************************************************************************************************************************************************************************************************/
	
	
	/**
	 * Insertar en la bitacora de operaciones (Registro de actividad del usuario)
	 * @param operacion Identificador de la operacion
	 * @param companyId CompanyId
	 * @param user Usuario
	 * @param descripcion Descripcion especifica de la operacion
	 * @param filtros
	 * @param statusAntes
	 * @param statusDespues
	 * @throws SQLDBException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void insertBitacora (BitacoraOperacion operacion, Long companyId, String user, String descripcion, String filtros, String statusAntes, String statusDespues) throws SQLDBException, Exception{
		log.info("Entrando a insertBitacora......");
		if (operacion != null && companyId != null && Util.isNotEmpty(user)){
			
			log.info("User: " + user + ", Pantalla: " + operacion.getPantalla().getDesc() + ", Operacion: " + operacion.getDesc() + ", Tipo: " + operacion.getTipoOperacion().getDesc());
			
			Bitacora bitacora = new Bitacora();
			bitacora.setFecha(new Date());
			bitacora.setUsuario(user);
			bitacora.setCompanyId(companyId);
			bitacora.setDescripcion(Util.isNotEmpty(descripcion) ? descripcion : operacion.getDesc());
			bitacora.setFiltros(filtros);
			bitacora.setStatusAntes(statusAntes);
			bitacora.setStatusDespues(statusDespues);
			
			bitacora.setOperacionId(operacion.getId());
			bitacora.setOperacionDesc(operacion.getDesc());
			bitacora.setPantallaId(operacion.getPantalla().getIdentificador());
			bitacora.setPantallaDesc(operacion.getPantalla().getDesc());
			bitacora.setTipoOperacionId(operacion.getTipoOperacion().getId());
			bitacora.setTipoOperacionDesc(operacion.getTipoOperacion().getDesc());
			
			DAO.insert(bitacora);
			if (bitacora == null || bitacora.getId() == null){
				throw new Exception ("Error al insertar en bitacora..");
			} else {
				log.info("Operacion ingresada en bitacora con Id: [" + bitacora.getId() + "]");
			}
		} else {
			throw new Exception ("Informacion incompleta para escribir en bitacora..");
		}
	}
	
	
	
	
	
	/**********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 * 
	 *                              M  A  N  E  J  O     D   E      E  R  R  O  R  E  S
	 * 
	 * *********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 **********************************************************************************************************************************************************************************************************/
	
	/**
	 * Error
	 */
	private static final String ERROR = "Error ";
	private static final Integer CODIGO_ERROR_CONTROLADO = -3;
	
	
	/**
	 * 
	 * @param response Respuesta que se va a entregar del servicio
	 * @param e Excepcion no categorizada
	 * @param servicio Servicio que presenta el error
	 * @param user Usuario
	 * @param companyId Company Id
	 * @throws ManageExcepcion Excepciones o errores de la aplicacion
	 * @throws PermisosException Acceso denegado por los permisos del usuario
	 * @throws ControlledException Excepciones controladas, porque son reglas de negocio
	 * @throws BadRequestException 
	 */
	public void setError (Exception e, ServiciosEnum servicio, String user, Long companyId) throws ManageExcepcion, PermisosException, ControlledException, BadRequestException{
		if (e instanceof PermisosException) {
			PermisosException pe = (PermisosException) e;
			throw pe;
		} else if (e instanceof ControlledException) {
			ControlledException controlledException = (ControlledException) e;
			throw controlledException;
		} else if (e instanceof BadRequestException) {
			BadRequestException badRequestException = (BadRequestException) e;
			throw badRequestException;
		} else if (e instanceof SQLDBException) {
			SQLDBException sqlException = (SQLDBException) e;
			if (sqlException.getCode().intValue() == CODIGO_ERROR_CONTROLADO.intValue()) {
				throw new ControlledException(sqlException.getDescription());
			} else {
				String msj = ERROR  + " en base de datos. Codigo: " + sqlException.getCode() + ", Descripcion: " + sqlException.getDescription() + ". Mensaje: "+   sqlException.getMessage();
				log.error(msj);
				sqlException.printStackTrace();
				throw new ManageExcepcion(servicio.name(), user, companyId, sqlException.getCode(), sqlException.getDescription(), sqlException.getMessage());
			}
		} else {
			String msj = ERROR + e.getMessage();
			log.error(msj);
			e.printStackTrace();
			throw new ManageExcepcion(servicio.name(), user, companyId, null, null, e.getMessage());
		}
	}
	
	
	
	
	
	/**********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 * 
	 *                              P  E  R  M  I  S  O  S        D   E       A  C  C  E  S  S  O
	 * 
	 * *********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 * *********************************************************************************************************************************************************************************************************
	 **********************************************************************************************************************************************************************************************************/
	
	
	
	/**
	 * Valida que un usuario pueda acceder al servicio web.
	 * Entre otras cosas valida que tenga una seccion activa, que cuente con sesion activa y actualiza el estado de su ultimo movimiento
	 * @param servicio
	 * @param user
	 * @param companyId
	 * @throws PermisosException
	 */
	public void validarPerfilPermisiosServicio (ServiciosEnum servicio, String user, Long companyId) throws PermisosException{
		log.info("Entrando  a validarPerfilPermisiosServicio..... User:" + user + ", CompanyId: " + companyId + "Servicio: " +servicio.name() );
		//No se hace nada para validar permisos
	}
	
	

}
