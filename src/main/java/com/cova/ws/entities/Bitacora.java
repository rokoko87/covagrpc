package com.cova.ws.entities;

import java.util.Date;

import com.cova.ws.sqlmanager.DAO;
import com.cova.ws.sqlmanager.DBTable;
import com.cova.ws.sqlmanager.annotations.AnnotationTable;
import com.cova.ws.sqlmanager.annotations.AnnotationTableField;



@AnnotationTable(schema=DAO.DATABASE_SCHEMA, name="BITACORA")
public class Bitacora  extends DBTable{
	
	@AnnotationTableField(id = true, autogenetaratedKey=true, name="BITACORA_ID", nullable=false)
	public Long id;
	
	@AnnotationTableField(name="OPERACION_ID", nullable=false)
	public Long operacionId;
	
	@AnnotationTableField(name="OPERACION_DESC", maxLenght=40, nullable=false)
	public String operacionDesc;
	
	@AnnotationTableField(name="PANTALLA_ID", nullable=false)
	public Long pantallaId;
	
	@AnnotationTableField(name="PANTALLA_DESC", maxLenght=40, nullable=false)
	public String pantallaDesc;
	
	@AnnotationTableField(name="TIPO_OPERACION_ID", nullable=false)
	public Long tipoOperacionId;
	
	@AnnotationTableField(name="TIPO_OPERACION_DESC", maxLenght=12, nullable=false)
	public String tipoOperacionDesc;
	
	@AnnotationTableField(name="USUARIO", maxLenght=10, nullable=false)
	public String usuario;
	
	@AnnotationTableField(name="USUARIO_PERFIL")
	public Long usuarioPerfil;
	
	@AnnotationTableField(name="FECHA", nullable=false)
	public Date fecha;
	
	@AnnotationTableField(name="STATUS_ANTES", maxLenght=15)
	public String statusAntes;
	
	@AnnotationTableField(name="STATUS_DESPUES", maxLenght=15)
	public String statusDespues;
	
	@AnnotationTableField(name="FILTROS", maxLenght=200)
	public String filtros;
	
	@AnnotationTableField(name="DESCRIPCION", maxLenght=200, nullable=false)
	public String descripcion;
	
	@AnnotationTableField(name="COMPANY_ID", nullable=false)
	public Long companyId;
	
	
	
	@Override
	public Bitacora createNew() {
		return new Bitacora();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	



	public String getUsuario() {
		return usuario;
	}



	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	public String getStatusAntes() {
		return statusAntes;
	}



	public void setStatusAntes(String statusAntes) {
		this.statusAntes = statusAntes;
	}



	public String getStatusDespues() {
		return statusDespues;
	}



	public void setStatusDespues(String statusDespues) {
		this.statusDespues = statusDespues;
	}



	public String getFiltros() {
		return filtros;
	}



	public void setFiltros(String filtros) {
		this.filtros = filtros;
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



	public Long getPantallaId() {
		return pantallaId;
	}



	public void setPantallaId(Long pantallaId) {
		this.pantallaId = pantallaId;
	}



	public String getPantallaDesc() {
		return pantallaDesc;
	}



	public void setPantallaDesc(String pantallaDesc) {
		this.pantallaDesc = pantallaDesc;
	}



	public Long getTipoOperacionId() {
		return tipoOperacionId;
	}



	public void setTipoOperacionId(Long tipoOperacionId) {
		this.tipoOperacionId = tipoOperacionId;
	}



	public String getTipoOperacionDesc() {
		return tipoOperacionDesc;
	}



	public void setTipoOperacionDesc(String tipoOperacionDesc) {
		this.tipoOperacionDesc = tipoOperacionDesc;
	}



	public Long getUsuarioPerfil() {
		return usuarioPerfil;
	}



	public void setUsuarioPerfil(Long usuarioPerfil) {
		this.usuarioPerfil = usuarioPerfil;
	}

	

	public Long getOperacionId() {
		return operacionId;
	}



	public void setOperacionId(Long operacionId) {
		this.operacionId = operacionId;
	}



	public String getOperacionDesc() {
		return operacionDesc;
	}



	public void setOperacionDesc(String operacionDesc) {
		this.operacionDesc = operacionDesc;
	}



	@Override
	public String toString() {
		return "Bitacora [id=" + id + ", operacionId=" + operacionId + ", operacionDesc=" + operacionDesc
				+ ", pantallaId=" + pantallaId + ", pantallaDesc=" + pantallaDesc + ", tipoOperacionId="
				+ tipoOperacionId + ", tipoOperacionDesc=" + tipoOperacionDesc + ", usuario=" + usuario
				+ ", usuarioPerfil=" + usuarioPerfil + ", fecha=" + fecha + ", statusAntes=" + statusAntes
				+ ", statusDespues=" + statusDespues + ", filtros=" + filtros + ", descripcion=" + descripcion
				+ ", companyId=" + companyId + "]";
	}



	


	
	

	
	
	
	

}


