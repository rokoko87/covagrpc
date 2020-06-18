package com.cova.ws.exception;


public class BadRequestException extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Mensaje que usualmentr muestra cual es la implicacion de la excepción
	 */
	private String mensaje;
	
	public BadRequestException(String mensaje) {
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
