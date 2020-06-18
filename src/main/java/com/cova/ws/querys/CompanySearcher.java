package com.cova.ws.querys;



import com.cova.ws.sqlmanager.DBTable;
import com.cova.ws.sqlmanager.annotations.AnnotationSelectField;
import com.cova.ws.sqlmanager.annotations.AnnotationSelectQuery;



@AnnotationSelectQuery(sentense="")
public class CompanySearcher  extends DBTable{
	
	@AnnotationSelectField(nameColumn="COMPANY_ID")
	public Long companyId;
	
	@AnnotationSelectField(nameColumn="NOMBRE")
	public String nombre;
	
	@AnnotationSelectField(nameColumn="RUTA_ACCESO")
	public String rutaAcceso;
	
	@AnnotationSelectField(nameColumn="SUBDOMINIO_ACTIVO")
	public Boolean subdominioActivo;
	
	@AnnotationSelectField(nameColumn="DOMINIO")
	public String dominio;
	
	@AnnotationSelectField(nameColumn="OWNER")
	public String owner;
	
	@AnnotationSelectField(nameColumn="CONTACTO")
	public String contacto;
	
	@AnnotationSelectField(nameColumn="CORREO_ELECTRONICO")
	public String correoElectronico;
	
	
	
	@AnnotationSelectField(nameColumn="IMAGEN_ID")
	public Long imagenId;
	
	@AnnotationSelectField(nameColumn="IMAGEN_RUTA")
	public String imagenRuta;
	
	@AnnotationSelectField(nameColumn="IMAGEN_MOD_IMAGEN_ID")
	public Long imagenModalidadId;
	
	@AnnotationSelectField(nameColumn="IMAGEN_MOD_IMAGEN_DESC")
	public String imagenModalidadDesc;
	
	
	
	@AnnotationSelectField(nameColumn="DESCRIPCION")
	public String descripcion;
	
	@AnnotationSelectField(nameColumn="DESCRIPCION_CORTA")
	public String descripcionCorta;
	
	
	
	@AnnotationSelectField(nameColumn="TIPO_ACTIVIDAD_ECONOMICA_ID")
	public Long tipoActividadEconomicaId;
	
	@AnnotationSelectField(nameColumn="TIPO_ACTIVIDAD_ECONOMICA_DESC")
	public String tipoActividadEconomicaDesc;
	
	@AnnotationSelectField(nameColumn="ACTIVIDAD_ECONOMICA_ID")
	public Long actividadEconomicaId;
	
	@AnnotationSelectField(nameColumn="ACTIVIDAD_ECONOMICA_DESC")
	public String actividadEconomicaDesc;
	
	@AnnotationSelectField(nameColumn="TIPO_TAMANO_EMPRESA_ID")
	public Long tipoTamanoEmpresaId;
	
	@AnnotationSelectField(nameColumn="TIPO_TAMANO_EMPRESA_DESC")
	public String tipoTamanoEmpresaDesc;
	
	
	@AnnotationSelectField(nameColumn="TIPO_CAPITAL_EMPRESA_ID")
	public Long tipoCapitalEmpresaId;
	
	@AnnotationSelectField(nameColumn="TIPO_CAPITAL_EMPRESA_DESC")
	public String tipoCapitalEmpresaDesc;
	
	@AnnotationSelectField(nameColumn="NIVEL")
	public String nivel;
	
	
	
	@AnnotationSelectField(nameColumn="FACEBOOK")
	public String facebook;
	
	@AnnotationSelectField(nameColumn="INSTAGRAM")
	public String instagram;
	
	@AnnotationSelectField(nameColumn="TWITTER")
	public String twitter;
	
	@AnnotationSelectField(nameColumn="PINTEREST")
	public String pinterest;
	
	@AnnotationSelectField(nameColumn="LINKED_IN")
	public String linkedIn;
	
	@AnnotationSelectField(nameColumn="WEB")
	public String web;
	
	@AnnotationSelectField(nameColumn="WATSAPP")
	public String watsapp;
	
	
	@AnnotationSelectField(nameColumn="STATUS")
	public Boolean status;



	@Override
	public Object createNew() {
		return new CompanySearcher();
	}



	public Long getCompanyId() {
		return companyId;
	}



	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getRutaAcceso() {
		return rutaAcceso;
	}



	public void setRutaAcceso(String rutaAcceso) {
		this.rutaAcceso = rutaAcceso;
	}



	public String getOwner() {
		return owner;
	}



	public void setOwner(String owner) {
		this.owner = owner;
	}



	public String getContacto() {
		return contacto;
	}



	public void setContacto(String contacto) {
		this.contacto = contacto;
	}



	public String getCorreoElectronico() {
		return correoElectronico;
	}



	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}



	public Long getImagenId() {
		return imagenId;
	}



	public void setImagenId(Long imagenId) {
		this.imagenId = imagenId;
	}



	public String getImagenRuta() {
		return imagenRuta;
	}



	public void setImagenRuta(String imagenRuta) {
		this.imagenRuta = imagenRuta;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getDescripcionCorta() {
		return descripcionCorta;
	}



	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}



	public Long getTipoActividadEconomicaId() {
		return tipoActividadEconomicaId;
	}



	public void setTipoActividadEconomicaId(Long tipoActividadEconomicaId) {
		this.tipoActividadEconomicaId = tipoActividadEconomicaId;
	}



	public String getTipoActividadEconomicaDesc() {
		return tipoActividadEconomicaDesc;
	}



	public void setTipoActividadEconomicaDesc(String tipoActividadEconomicaDesc) {
		this.tipoActividadEconomicaDesc = tipoActividadEconomicaDesc;
	}



	public Long getActividadEconomicaId() {
		return actividadEconomicaId;
	}



	public void setActividadEconomicaId(Long actividadEconomicaId) {
		this.actividadEconomicaId = actividadEconomicaId;
	}



	public String getActividadEconomicaDesc() {
		return actividadEconomicaDesc;
	}



	public void setActividadEconomicaDesc(String actividadEconomicaDesc) {
		this.actividadEconomicaDesc = actividadEconomicaDesc;
	}



	public Long getTipoTamanoEmpresaId() {
		return tipoTamanoEmpresaId;
	}



	public void setTipoTamanoEmpresaId(Long tipoTamanoEmpresaId) {
		this.tipoTamanoEmpresaId = tipoTamanoEmpresaId;
	}



	public String getTipoTamanoEmpresaDesc() {
		return tipoTamanoEmpresaDesc;
	}



	public void setTipoTamanoEmpresaDesc(String tipoTamanoEmpresaDesc) {
		this.tipoTamanoEmpresaDesc = tipoTamanoEmpresaDesc;
	}



	public Long getTipoCapitalEmpresaId() {
		return tipoCapitalEmpresaId;
	}



	public void setTipoCapitalEmpresaId(Long tipoCapitalEmpresaId) {
		this.tipoCapitalEmpresaId = tipoCapitalEmpresaId;
	}



	public String getTipoCapitalEmpresaDesc() {
		return tipoCapitalEmpresaDesc;
	}



	public void setTipoCapitalEmpresaDesc(String tipoCapitalEmpresaDesc) {
		this.tipoCapitalEmpresaDesc = tipoCapitalEmpresaDesc;
	}



	public Boolean getStatus() {
		return status;
	}



	public void setStatus(Boolean status) {
		this.status = status;
	}



	public String getFacebook() {
		return facebook;
	}



	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}



	public String getInstagram() {
		return instagram;
	}



	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}



	public String getTwitter() {
		return twitter;
	}



	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}



	public String getPinterest() {
		return pinterest;
	}



	public void setPinterest(String pinterest) {
		this.pinterest = pinterest;
	}



	public String getLinkedIn() {
		return linkedIn;
	}



	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}



	public String getWeb() {
		return web;
	}



	public void setWeb(String web) {
		this.web = web;
	}



	public Boolean getSubdominioActivo() {
		return subdominioActivo;
	}



	public void setSubdominioActivo(Boolean subdominioActivo) {
		this.subdominioActivo = subdominioActivo;
	}



	public String getDominio() {
		return dominio;
	}



	public void setDominio(String dominio) {
		this.dominio = dominio;
	}



	public Long getImagenModalidadId() {
		return imagenModalidadId;
	}



	public void setImagenModalidadId(Long imagenModalidadId) {
		this.imagenModalidadId = imagenModalidadId;
	}



	public String getImagenModalidadDesc() {
		return imagenModalidadDesc;
	}



	public void setImagenModalidadDesc(String imagenModalidadDesc) {
		this.imagenModalidadDesc = imagenModalidadDesc;
	}



	public String getNivel() {
		return nivel;
	}



	public void setNivel(String nivel) {
		this.nivel = nivel;
	}



	public String getWatsapp() {
		return watsapp;
	}



	public void setWatsapp(String watsapp) {
		this.watsapp = watsapp;
	}


		
	

	
	
	
	
	

}
