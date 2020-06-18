package com.cova.ws.exception;

import java.io.Serializable;

public class ManageExcepcion  extends Exception implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String servicioWeb;
	
	private String user;
	
	private Long companyId;
	
	private Integer codigo;
	
	private String descripcion;
	
	private String mensaje;

	
	
	
	public ManageExcepcion(String servicioWeb, String user, Long companyId, Integer codigo, String descripcion,
			String mensaje) {
		super();
		this.servicioWeb = servicioWeb;
		this.user = user;
		this.companyId = companyId;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.mensaje = mensaje;
	}

	public String getServicioWeb() {
		return servicioWeb;
	}

	public void setServicioWeb(String servicioWeb) {
		this.servicioWeb = servicioWeb;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	

}
