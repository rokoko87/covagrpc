package com.cova.ws.exception;

/**
 * Excepcion que no es un error en el sistema, sino una validacion o una regla
 * de negocio que no se esta cumpliendo
 * @author alberto.vazquez
 *
 */
public class ControlledException extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Mensaje que usualmentr muestra cual es la implicacion de la excepción
	 */
	private String mensaje;
	
	public ControlledException(String mensaje) {
		super();
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	

}
