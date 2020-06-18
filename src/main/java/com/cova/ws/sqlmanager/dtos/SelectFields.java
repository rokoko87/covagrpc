package com.cova.ws.sqlmanager.dtos;

import java.lang.reflect.Field;
import java.util.Map;

public class SelectFields {
	
	private String className; 
	@SuppressWarnings("rawtypes")
	private Class clase;
	private Map<String, Field> fields ;
	private String query;
	
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@SuppressWarnings("rawtypes")
	public Class getClase() {
		return clase;
	}
	public void setClase(@SuppressWarnings("rawtypes") Class clase) {
		this.clase = clase;
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Map<String, Field> getFields() {
		return fields;
	}
	public void setFields(Map<String, Field> fields) {
		this.fields = fields;
	}
	
	
	
	

}
