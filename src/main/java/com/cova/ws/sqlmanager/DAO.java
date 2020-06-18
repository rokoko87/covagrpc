package com.cova.ws.sqlmanager;

import java.util.List;

public interface DAO<T> {
	
	
	public static final String DATABASE_SCHEMA = "cova";
	
	public  void insert (T o) throws SQLDBException, Exception;
	
	public  Boolean updateById (T o) throws SQLDBException, Exception;
	
	public  Integer delete (T o, String... restrinctions) throws SQLDBException, Exception;
	
	public  Boolean deleteById (T o) throws SQLDBException, Exception;
	
	public  Integer deleteIN (T o, Number... restrinctions) throws SQLDBException, Exception;
	
	public  Integer executeSQL (String sqlSentense, Object... parameters) throws SQLDBException, Exception;
	
	public  List<T> select(T o, String... restrinctions) throws SQLDBException, Exception;
	
	public  T selectById(T o) throws SQLDBException, Exception;
	
	public  List<T> selectIN(T o, Number... restrinctions) throws SQLDBException, Exception;

	public  List<T> genericSelect (T o, Object... parameters) throws SQLDBException, Exception;

	public  List<T> genericSelect (T o, String query, Object... parameters) throws SQLDBException, Exception;
	

}
