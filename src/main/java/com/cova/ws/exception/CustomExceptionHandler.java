package com.cova.ws.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cova.ws.constants.Constants;
import com.cova.ws.entities.BitacoraErrores;
import com.cova.ws.rest.exeption.ErrorResponse;
import com.cova.ws.sqlmanager.DAO;
import com.cova.ws.sqlmanager.SQLDBException;
 
@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	public static Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);
	
	
	@Autowired
	private DAO DAO;
	
	
	private static final Integer CODIGO_PERMISOS = 401;
	private static final String PROCESO_NO_IDENTIFICADO = "NO IDENTIFICADO";
	private static final String USUARIO_NO_IDENTIFICADO = "NI";
	private static final Integer CODIGO_NO_IDENTIFICADO = 0;
	private static final Long COMPANY_NO_IDENTIFICADA = 0L;
	
	
	@ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> badRequestException(BadRequestException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add("Descripcion: " + ex.getMensaje());
        ErrorResponse error = new ErrorResponse("Informacion en el request faltante", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
	
	
	
	/**
	 * Manejo de excepciones para validaciones con reglas de negocio. Por lo que no es un error,
	 * sino una excepcion a alguna regla de negocio que no permite completar el proceso. No se regisra en la bitacora de errores
	 * @param ex Excepcion controlada
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ControlledException.class)
    public final ResponseEntity<String> controlledException(ControlledException ex, WebRequest request) {
       return new ResponseEntity(ex.getMensaje(), HttpStatus.CONFLICT);
    }
	
	/**
	 * MAnejo de excepciones para errores relacionados con base de datos
	 * @param ex Exception de base de datos
	 * @param request
	 * @return
	 */
    @ExceptionHandler(SQLDBException.class)
    public final ResponseEntity<Object> sqlException(SQLDBException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add("Code: " + ex.getCode());
        details.add("Descripcion: " + ex.getDescription());
        ErrorResponse error = new ErrorResponse("Error en base de datos", details);
        insertBitacoraErrores(PROCESO_NO_IDENTIFICADO, USUARIO_NO_IDENTIFICADO, COMPANY_NO_IDENTIFICADA, ex.getCode(), ex.getDescription(), ex.getMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    /**
     * Manejo de excepcicones relacionadas con permisos de lo que puede hacer un usuario en la aplicacion,
     * basado en los permisos otorgados por el administrador. Se registra en la bitacora de errores porque alguiene esta intentando
     * acceder sin permisos
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(PermisosException.class)
    public final ResponseEntity<Object> permisosException(PermisosException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add("User: " + ex.getUser());
        details.add("Company: " + ex.getCompanyId());
        details.add("Servicio Web: " + ex.getServicio().name());
        details.add("Descripcion: " + ex.getDescripcion());
        ErrorResponse error = new ErrorResponse("Permisos insuficientes", details);
        insertBitacoraErrores(ex.getServicio().name(), ex.getUser(), ex.getCompanyId(), CODIGO_PERMISOS, ex.getDescripcion(), null);
        return new ResponseEntity(error, HttpStatus.UNAUTHORIZED);
    }
    
    /**
     * Manejo de errores no controlados, es decir, son errores de la aplicacion, que tienen que ser
     * corregidos con urgencia, porque no permiten el correcto funcionamiento del sistema
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ManageExcepcion.class)
    public final ResponseEntity<Object> manageException(ManageExcepcion ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add("Servicio Web: " + ex.getServicioWeb());
        details.add("User: " + ex.getUser());
        details.add("Company: " + ex.getCompanyId());
        details.add("Codigo: " + ex.getCodigo());
        details.add("Descripcion: " + ex.getDescripcion());
        details.add("Mensaje: " + ex.getMensaje());
        ErrorResponse error = new ErrorResponse("Error interno regristrado", details);
        insertBitacoraErrores(ex.getServicioWeb(), ex.getUser(), ex.getCompanyId(), ex.getCodigo(), ex.getDescripcion(), ex.getMensaje());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    /**
     * Manejo de Excepciones generales, aquellas que no fueron identificadas, pero errores que tienen que ser 
     * regisrados
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add("Mensaje: " + ex.getMessage());
        details.add("Detalle: " + ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Error interno", details);
        insertBitacoraErrores(PROCESO_NO_IDENTIFICADO, USUARIO_NO_IDENTIFICADO, COMPANY_NO_IDENTIFICADA, CODIGO_NO_IDENTIFICADO, ex.getMessage(), ex.getLocalizedMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    /**
     * Se dispara cuando no se cumplen las validaciones en el request con las etiquetas
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) 
        	details.add(error.getDefaultMessage());
        
        ErrorResponse error = new ErrorResponse("Validacion Fallida", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
    
    
    /**
	 * Inserta en la tabla de bitacora de errores el error sucedido
	 * @param proceso proceso del que viene el error
	 * @param usuario usuario que estaba logeado
	 * @param companyId company que esta que estaba logeada
	 * @param codigoError Codigo del error
	 * @param descripcionError Descripcion del error
	 * @param error Especificacion del error
	 */
	private void insertBitacoraErrores (String proceso, String usuario, Long companyId, Integer codigoError, String descripcionError, String error ){
		
		try {
			BitacoraErrores registro = new BitacoraErrores();
			registro.setProcedencia(Constants.UNO); //De servicios web
			registro.setProceso(proceso);
			registro.setUsuario(usuario);
			registro.setCompanyId(companyId);
			registro.setCodigoError(codigoError);
			registro.setDescripcionError(descripcionError);
			registro.setError(error);
			registro.setFecha(new Date());
			DAO.insert(registro);
			if (registro != null && registro.getId() != null)
				log.error("Error registrado en bitacora con id: " + registro.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al registrar en bitacora de errores");
		}
	}
    
    
    
}