package com.cova.ws.sqlmanager.dtos;

import java.lang.reflect.Field;
import java.util.List;

import com.cova.ws.sqlmanager.annotations.AnnotationTableField;



public class TableFields {
	
	private String className; 
	@SuppressWarnings("rawtypes")
	private Class clase;
	private String schema;
	private String tableName;
	private List<AnnotationTableField> fieldTag;
	private List<Field> field;
	private AnnotationTableField idAnotation;
	private Field id;
	
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
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<AnnotationTableField> getFieldTag() {
		return fieldTag;
	}
	public void setFieldTag(List<AnnotationTableField> fieldTag) {
		this.fieldTag = fieldTag;
	}
	public List<Field> getField() {
		return field;
	}
	public void setField(List<Field> field) {
		this.field = field;
	}
	public AnnotationTableField getIdAnotation() {
		return idAnotation;
	}
	public void setIdAnotation(AnnotationTableField idAnotation) {
		this.idAnotation = idAnotation;
	}
	public Field getId() {
		return id;
	}
	public void setId(Field id) {
		this.id = id;
	}
	
	
	
	

}
