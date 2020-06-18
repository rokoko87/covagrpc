package com.cova.ws.entities;

import java.util.Date;

import com.cova.ws.sqlmanager.DAO;
import com.cova.ws.sqlmanager.DBTable;
import com.cova.ws.sqlmanager.annotations.AnnotationTable;
import com.cova.ws.sqlmanager.annotations.AnnotationTableField;



@AnnotationTable(schema=DAO.DATABASE_SCHEMA, name="BITACORA_ERRORES")
public class BitacoraErrores  extends DBTable{
	
	@AnnotationTableField(id = true, autogenetaratedKey=true, name="BITACORA_ERRORES_ID", nullable=false)
	public Long id;
	
	@AnnotationTableField(name="PROCEDENCIA", nullable=false)
	public Integer procedencia;
	
	@AnnotationTableField(name="PROCESO", maxLenght=50, nullable=false)
	public String proceso;
	
	@AnnotationTableField(name="USUARIO", maxLenght=20)
	public String usuario;
	
	@AnnotationTableField(name="COMPANY_ID")
	public Long companyId;
	
	@AnnotationTableField(name="CODIGO_ERROR")
	public Integer codigoError;
	
	@AnnotationTableField(name="DESCRIPCION_ERROR", maxLenght=200)
	public String descripcionError;
	
	@AnnotationTableField(name="ERROR", maxLenght=1000)
	public String error;
	
	@AnnotationTableField(name="FECHA", nullable=false)
	public Date fecha;

	@Override
	public BitacoraErrores createNew() {
		return new BitacoraErrores();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(Integer procedencia) {
		this.procedencia = procedencia;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(Integer codigoError) {
		this.codigoError = codigoError;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	


	


	
	

	
	
	
	

}


