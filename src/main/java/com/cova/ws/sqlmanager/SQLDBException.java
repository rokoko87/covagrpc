package com.cova.ws.sqlmanager;

/**
 * Exception arrojada por la ejecucion de una sentencia SQL
 * @author alberto.vazquez
 *
 */
public class SQLDBException  extends Exception{
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -5619873885857376106L;
	
	public static Integer DEFALT_CODE = 100;
	
	
	
	
	private Integer code;
	private String description;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SQLDBException(Integer code, String description) {
		super();
		this.code = code;
		this.description = description;
	}
	
	
	

}
