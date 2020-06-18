package com.cova.ws.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cova.ws.constants.ServiciosEnum;
import com.cova.ws.exception.BadRequestException;
import com.cova.ws.exception.ControlledException;
import com.cova.ws.exception.ManageExcepcion;
import com.cova.ws.exception.PermisosException;
import com.cova.ws.querys.CompanySearcher;
import com.cova.ws.rest.pojos.CompanyPOJO;
import com.cova.ws.rest.pojos.CompanySearcherPOJO;
import com.cova.ws.rest.request.POJORequest;
import com.cova.ws.service.BitacoraPermisosErroresService;
import com.cova.ws.service.CompanyService;
import com.cova.ws.sqlmanager.DAO;
import com.cova.ws.util.Filler;
import com.cova.ws.util.Util;



@Service
public class CompanyServiceImpl implements CompanyService {
	
	public static Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	
	
	@Autowired
	private BitacoraPermisosErroresService bitacoraService;
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	@Autowired 
	private DAO DAO;
	
	
	
	
	
	
	/*************************************************************************
	 * ***********************************************************************
	 * 					C O N S U L T A R    E M P R E S A S
	 * ***********************************************************************
	 * ***********************************************************************
	 * ***********************************************************************/
	
	
	/**
	 * Consultar empresas activas con filtros
	 * @param request
	 * @param httpRequest
	 * @param conexionDB
	 * @return
	 * @throws BadRequestException 
	 * @throws ControlledException 
	 * @throws PermisosException 
	 * @throws ManageExcepcion 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = ManageExcepcion.class)
	public List<CompanySearcherPOJO> loadCompanies (CompanyPOJO request) throws ManageExcepcion, PermisosException, ControlledException, BadRequestException{
		log.info(request.toString());
		List<CompanySearcherPOJO> response = null;
		
		ServiciosEnum servicio = ServiciosEnum.COMPANY_LOAD;
		
		try {
			bitacoraService.validarPerfilPermisiosServicio(servicio, null, null);
			
			List<CompanySearcher> empresas = DAO.genericSelect(new CompanySearcher(), formulateQueryEmpresa(request), new Object[]{});
			Filler<CompanySearcher, CompanySearcherPOJO> filler = new Filler<CompanySearcher, CompanySearcherPOJO>();
			response = filler.fillList(empresas, new CompanySearcherPOJO());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
	
	
	public static String formulateQueryEmpresa (CompanyPOJO filtros){
		StringBuilder sb = new StringBuilder("");
		
		sb.append(" SELECT  ");
		sb.append(" C.COMPANY_ID, ");
		sb.append(" C.NOMBRE, ");
		sb.append(" C.RUTA_ACCESO, ");
		sb.append(" C.SUBDOMINIO_ACTIVO, ");
		sb.append(" C.DOMINIO, ");
		sb.append(" C.OWNER, ");
		sb.append(" C.CONTACTO, ");
		sb.append(" C.CORREO_ELECTRONICO, ");
		sb.append(" C.IMAGEN_ID, ");
		sb.append(" I.RUTA AS IMAGEN_RUTA, ");
		sb.append(" C.TIPO_MOD_IMAGEN_ID AS IMAGEN_MOD_IMAGEN_ID, ");
		sb.append(" CAT_MOD_IMAG.DESCRIPCION AS IMAGEN_MOD_IMAGEN_DESC, ");
		
		sb.append(" C.DESCRIPCION, ");
		sb.append(" C.DESCRIPCION_CORTA, ");
		sb.append(" C.TIPO_ACTIVIDAD_ECONOMICA_ID, ");
		sb.append(" CAT_TIPO_ACT.DESCRIPCION AS TIPO_ACTIVIDAD_ECONOMICA_DESC, ");
		sb.append(" C.ACTIVIDAD_ECONOMICA_ID, ");
		sb.append(" CAT_ACT_ECON.DESCRIPCION AS ACTIVIDAD_ECONOMICA_DESC, ");
		sb.append(" C.TIPO_TAMANO_EMPRESA_ID, ");
		sb.append(" CAT_TIPO_TAM.DESCRIPCION AS TIPO_TAMANO_EMPRESA_DESC, ");
		sb.append(" C.TIPO_CAPITAL_EMPRESA_ID, ");
		sb.append(" CAT_TIPO_CAP.DESCRIPCION AS TIPO_CAPITAL_EMPRESA_DESC, ");
		sb.append(" C.NIVEL AS NIVEL, ");
		
		
		sb.append(" C.FACEBOOK, ");
		sb.append(" C.INSTAGRAM, ");
		sb.append(" C.TWITTER, ");
		sb.append(" C.PINTEREST, ");
		sb.append(" C.LINKED_IN, ");
		sb.append(" C.WEB, ");
		sb.append(" C.WATSAPP, ");
		
		
		sb.append(" C.STATUS ");
		sb.append(" FROM COMPANY C ");
		sb.append(" LEFT JOIN IMAGEN I ON C.IMAGEN_ID = I.IMAGEN_ID ");
		sb.append(" LEFT JOIN CATALOGO CAT_MOD_IMAG ON C.TIPO_MOD_IMAGEN_ID = CAT_MOD_IMAG.CATALOGO_ID ");
		sb.append(" LEFT JOIN CATALOGO CAT_TIPO_ACT ON C.TIPO_ACTIVIDAD_ECONOMICA_ID = CAT_TIPO_ACT.CATALOGO_ID ");
		sb.append(" LEFT JOIN CATALOGO CAT_ACT_ECON ON C.ACTIVIDAD_ECONOMICA_ID = CAT_ACT_ECON.CATALOGO_ID ");
		sb.append(" LEFT JOIN CATALOGO CAT_TIPO_TAM ON C.TIPO_TAMANO_EMPRESA_ID = CAT_TIPO_TAM.CATALOGO_ID ");
		sb.append(" LEFT JOIN CATALOGO CAT_TIPO_CAP ON C.TIPO_CAPITAL_EMPRESA_ID = CAT_TIPO_CAP.CATALOGO_ID ");
		sb.append(" WHERE C.COMPANY_ID > 0 ");
		
		
		if (filtros != null) {
			
			if (filtros.getId() != null)
				sb.append(" AND C.COMPANY_ID = " + filtros.getId());
			
			if (Util.isNotEmpty(filtros.getNombre())) 
				sb.append(" AND C.NOMBRE LIKE '%" + filtros.getNombre().trim() + "%' ");
			
			if (Util.isNotEmpty(filtros.getRutaAcceso())) 
				sb.append(" AND C.RUTA_ACCESO = '" + filtros.getRutaAcceso().trim() + "' ");
			
			if (filtros.getSubdominioActivo() != null)
				sb.append(" AND C.SUBDOMINIO_ACTIVO = " + ( filtros.getSubdominioActivo() ? "1" : "0" ));
			
			
			if (Util.isNotEmpty(filtros.getDominio())) 
				sb.append(" AND C.DOMINIO = '" + filtros.getDominio().trim() + "' ");
			
			if (filtros.getTipoActividadEconomicaId() != null)
				sb.append(" AND C.TIPO_ACTIVIDAD_ECONOMICA_ID = " + filtros.getTipoActividadEconomicaId());
			
			if (filtros.getActividadEconomicaId() != null)
				sb.append(" AND C.ACTIVIDAD_ECONOMICA_ID = " + filtros.getActividadEconomicaId());
			
			if (filtros.getTipoTamanioEmpresaId() != null)
				sb.append(" AND C.TIPO_TAMANO_EMPRESA_ID = " + filtros.getTipoTamanioEmpresaId());
			
			if (filtros.getTipoCapitalEmpresaId() != null)
				sb.append(" AND C.TIPO_CAPITAL_EMPRESA_ID = " + filtros.getTipoCapitalEmpresaId());
			
			if (filtros.getStatus() != null)
				sb.append(" AND C.STATUS = " + ( filtros.getStatus() ? "1" : "0" ));
			
		}
		
		return sb.toString();
	}
	
	 
	
	
	

}
