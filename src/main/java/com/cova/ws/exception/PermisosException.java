package com.cova.ws.exception;

import com.cova.ws.constants.ServiciosEnum;

public class PermisosException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long companyId;
	
	/**
	 * Usuario que invoca el servicio
	 */
	private String user;
	
	/**
	 * Servicio web que presenta la excepcion de permisos
	 */
	private ServiciosEnum servicio;
	
	/**
	 * Motivo por el cual el usuario no tiene acceso
	 */
	private String descripcion;
	
	
	

	public PermisosException(Long companyId, String user, ServiciosEnum servicio, String descripcion) {
		super();
		this.companyId = companyId;
		this.user = user;
		this.servicio = servicio;
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ServiciosEnum getServicio() {
		return servicio;
	}

	public void setServicio(ServiciosEnum servicio) {
		this.servicio = servicio;
	}
	
	
	

}
