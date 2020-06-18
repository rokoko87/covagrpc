package com.cova.ws.sqlmanager;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cova.ws.sqlmanager.annotations.AnnotationSelectField;
import com.cova.ws.sqlmanager.annotations.AnnotationSelectQuery;
import com.cova.ws.sqlmanager.annotations.AnnotationTable;
import com.cova.ws.sqlmanager.annotations.AnnotationTableField;
import com.cova.ws.sqlmanager.dtos.SelectFields;
import com.cova.ws.sqlmanager.dtos.TableFields;



@Repository
public class DAOImpl<T extends DBTable> implements DAO<T>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	
	
	public static Logger log = LoggerFactory.getLogger(DAOImpl.class);
	
	/**
	 * Inserta en la tabla indicada por el objeto o. Si la llave primaria es autogenerada, se agrega el valor
	 * @param o Entidad relacionada con una tabla
	 * @throws SQLDBException
	 * @throws Exception
	 */
	@Override
	public void insert (T o) throws SQLDBException, Exception  {
		TableFields tableFields = getTableAndFields (o);
		SQLSentenses<T> sqlSenetences = new SQLSentenses<T>();
		String sql = sqlSenetences.getInsertSentence(o, tableFields);
		log.info("SQL [insert]: " + sql);
		int inserto = 0;
		if (tableFields.getIdAnotation().autogenetaratedKey()){
			//Se autogenera la key
			KeyHolder keyHolder = new GeneratedKeyHolder();
			inserto = jdbcTemplate.update(new PreparedStatementCreator(){
	            public PreparedStatement createPreparedStatement(Connection connection)
	                throws SQLException {
	                PreparedStatement ps =connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	                return ps;
	            }
	        },keyHolder);
			
		   //Colocar el valor del Id generado en el objeto
	       if(inserto > 0 && tableFields.getId() != null){
            	if (tableFields.getId().getType().equals(java.lang.Long.class)){
            		tableFields.getId().set(o, keyHolder.getKey().longValue());
            	} else if (tableFields.getId().getType().equals(java.lang.Integer.class)){
            		tableFields.getId().set(o, keyHolder.getKey().intValue());
            	} else if (tableFields.getId().getType().equals(java.lang.Short.class)){
            		tableFields.getId().set(o, keyHolder.getKey().shortValue());
            	} else if (tableFields.getId().getType().equals(java.math.BigDecimal.class)){
            		tableFields.getId().set(o, new BigDecimal(keyHolder.getKey().longValue()));
            	} else if (tableFields.getId().getType().equals(java.lang.Double.class)){
            		tableFields.getId().set(o, keyHolder.getKey().doubleValue());
            	} else if (tableFields.getId().getType().equals(java.lang.Float.class)){
            		tableFields.getId().set(o, keyHolder.getKey().floatValue());
            	} else if (tableFields.getId().getType().equals(java.lang.Byte.class)){
            		tableFields.getId().set(o, keyHolder.getKey().byteValue());
            	} else if (tableFields.getId().getType().equals(java.math.BigInteger.class)){
            		tableFields.getId().set(o, new BigInteger(keyHolder.getKey().toString()));
            	} else if (tableFields.getId().getType().equals(java.lang.String.class)){
            		tableFields.getId().set(o, keyHolder.getKey().toString());
            	} 
            }
	        
	    } else {
	    	//La llave primaria viene en la informacion para insertarse
	    	inserto = jdbcTemplate.update(sql);
		}
		
		
		if (inserto <= 0){
			throw new SQLDBException(SQLDBException.DEFALT_CODE, 
					"No fue posible insertar el registro en la tabla " + tableFields.getTableName());
		}
	}
	
	
	/**
	 * Realiza la operacion de UPDATE en la tabla indicada por o. La informacion del SET proviene de la
	 * informacion contenida en o (se excluye el id), y los campos contenidos en restricciones, anaden clausulas WHERE y AND 
	 * con la informacion contenida en o
	 * @param o Entidad relacionada con una tabla
	 * @param restrinctions Campos especificos de la tabla que tendran una restriccion
	 * @return
	 * @throws SQLDBException
	 * @throws Exception
	 */
	@Deprecated
	public Integer update (T o, String[] restrinctions) throws SQLDBException, Exception  {
		Integer valoresActualizados = 0;
		if (restrinctions != null && restrinctions.length > 0){
			TableFields tableFields = getTableAndFields (o);
			SQLSentenses<T> sentense = new SQLSentenses<T>();
			StringBuilder sql = new StringBuilder();
			sql.append(sentense.getUpdateSentence(o, tableFields));
			sql.append(sentense.delimit(o, tableFields, restrinctions));
			String query = sql.toString();
			log.info("SQL [update]: " + query);
			valoresActualizados = jdbcTemplate.update(query);
		} else {
			throw new SQLDBException (SQLDBException.DEFALT_CODE, "Debes de especificar alguna restriccion en el UPDATE" );
		}
		
		return valoresActualizados;
	}
	
	
	/**
	 * Realiza la operacion de UPDATE en la tabla indicada por o. La informacion del SET proviene de la
	 * informacion contenida en o (se excluye el id), la restriccion se toma del campo id por lo que este valor
	 * tiene que estar poblado y la tabla debe de tener un id
	 * @param o Entidad relacionada con una tabla
	 * @return True Si se realizo la actualizacion, False no se realizo la actualizacion
	 * @throws SQLDBException
	 * @throws Exception
	 */
	@Override
	public Boolean updateById (T o) throws SQLDBException, Exception  {
		TableFields tableFields = getTableAndFields (o);
		SQLSentenses<T> sentense = new SQLSentenses<T>();
		StringBuilder sql = new StringBuilder();
		sql.append(sentense.getUpdateSentence(o, tableFields));
		sql.append(sentense.byId(o, tableFields));
		String query = sql.toString();
		log.info("SQL [update]: " + query);
		Integer valoresActualizados = jdbcTemplate.update(query);
		return valoresActualizados == 1 ? true :false;
		
	}
	
	
	/**
	 * Realiza la operacion de UPDATE en la tabla indicada por o. La informacion del SET proviene de la
	 * informacion contenida en o (se excluye el id), se restringe por todos los Ids contenidos en el arreglo de ids
	 * @param o Entidad relacionada con una tabla
	 * @param ids Ids para restringir
	 * @return
	 * @throws SQLDBException
	 * @throws Exception
	 */
	@Deprecated
	public Integer updateIN(T o, Number... ids) throws SQLDBException, Exception {
		Integer valoresActualizados = 0;
		if (ids != null && ids.length > 0){
			TableFields tableFields = getTableAndFields (o);
			SQLSentenses<T> sentense = new SQLSentenses<T>();
			StringBuilder sql = new StringBuilder();
			sql.append(sentense.getUpdateSentence(o, tableFields));
			sql.append(sentense.idIn(o, tableFields ,numberArrayToStringArray(ids)));
			String query = sql.toString();
			log.info("SQL [updateIN]: " + query);
			valoresActualizados = jdbcTemplate.update(query);
		} else {
			throw new SQLDBException (SQLDBException.DEFALT_CODE, "Debes de especificar por lo menos un id para realizar el UPDATE" );
		}
		
		return valoresActualizados;
	}
	
	
	
	@Override
	public Integer delete (T o, String ... restrinctions) throws SQLDBException, Exception {
		Integer valoresEliminados = 0;
		if (restrinctions != null && restrinctions.length > 0){
			TableFields tableFields = getTableAndFields (o);
			SQLSentenses<T> sentense = new SQLSentenses<T>();
			StringBuilder sql = new StringBuilder();
			sql.append(sentense.getDeleteSentence(o, tableFields));
			sql.append(sentense.delimit(o, tableFields, restrinctions));
			String query = sql.toString();
			log.info("SQL [delete]: " + query);
			valoresEliminados = jdbcTemplate.update(query);
		} else {
			throw new SQLDBException (SQLDBException.DEFALT_CODE, "Debes de especificar alguna restriccion en el DELETE" );
		}
		
		return valoresEliminados;
	}
	
	
	
	
	@Override
	public Boolean deleteById (T o) throws SQLDBException, Exception  {
		TableFields tableFields = getTableAndFields (o);
		SQLSentenses<T> sentense = new SQLSentenses<T>();
		StringBuilder sql = new StringBuilder();
		sql.append(sentense.getDeleteSentence(o, tableFields));
		sql.append(sentense.byId(o, tableFields));
		String query = sql.toString();
		log.info("SQL [deleteById]: " + query);
		Integer valoresEliminados = jdbcTemplate.update(query);
		return valoresEliminados == 1 ? true :false;
		
	}
	
	
	
	@Override
	public Integer deleteIN(T o, Number... ids) throws SQLDBException, Exception {
		Integer valoresEliminados = 0;
		if (ids != null && ids.length > 0){
			TableFields tableFields = getTableAndFields (o);
			SQLSentenses<T> sentense = new SQLSentenses<T>();
			StringBuilder sql = new StringBuilder();
			sql.append(sentense.getDeleteSentence(o, tableFields));
			sql.append(sentense.idIn(o, tableFields, numberArrayToStringArray(ids)));
			String query = sql.toString();
			log.info("SQL [deleteIN]: " + query);
			valoresEliminados = jdbcTemplate.update(query);
		} else {
			throw new SQLDBException (SQLDBException.DEFALT_CODE, "Debes de especificar por lo menos un id para realizar el DELETE IN" );
		}
		
		return valoresEliminados;
	}
	
	
	
	
	
	@Override
	public List<T> select(T o, String... restrinctions) throws SQLDBException, Exception  {
		SQLSentenses<T> sentense = new SQLSentenses<T>();
		TableFields tableFields = getTableAndFields(o);
		StringBuilder sql = new StringBuilder();
		sql.append(sentense.getSelectSentence(o, tableFields));
		if (restrinctions != null && restrinctions.length > 0)
			sql.append(sentense.delimit(o, tableFields, restrinctions));
		String query = sql.toString();
		return llenarLista(selectOperation(o, tableFields, query, null));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T selectById(T o) throws SQLDBException, Exception  {
		T regreso = null;
		SQLSentenses<T> sentense = new SQLSentenses<T>();
		TableFields tableFields = getTableAndFields (o);
		StringBuilder sql = new StringBuilder();
		sql.append(sentense.getSelectSentence(o, tableFields));
		sql.append(sentense.byId(o, tableFields));
		String query = sql.toString();
		List<Object> lista = selectOperation(o, tableFields, query, null);
		if (lista != null && !lista.isEmpty()){
			regreso = (T)lista.get(0);
		}
		return regreso;
	}
	
	
	@Override
	public List<T> selectIN(T o, Number... ids) throws SQLDBException, Exception  {
		List<T> regreso = null;
		if (ids != null && ids.length > 0){
			SQLSentenses<T> sentense = new SQLSentenses<T>();
			TableFields tableFields = getTableAndFields (o);
			StringBuilder sql = new StringBuilder();
			sql.append(sentense.getSelectSentence(o, tableFields));
			sql.append(sentense.idIn(o, tableFields, numberArrayToStringArray(ids)));
			String query = sql.toString();
			List<Object> lista = selectOperation(o, tableFields, query, null);
			regreso = llenarLista(lista);
		} else {
			throw new SQLDBException (SQLDBException.DEFALT_CODE, "Debes de especificar por lo menos un id para la busqueda por ids" );
		}
		
		return regreso;
	}
	
	
	@Override
	public List<T> genericSelect (T o, Object... parameters) throws SQLDBException, Exception  {
		SelectFields selectFields = getQueryAndFields(o);
		return llenarLista(selectOperation(o, selectFields, selectFields.getQuery(), parameters));
	}
	
	@Override
	public List<T> genericSelect (T o, String query, Object... parameters) throws SQLDBException, Exception  {
		SelectFields selectFields = getQueryAndFields(o);
		return llenarLista(selectOperation(o, selectFields, query, parameters));
	}
	
	
	
	
	
	/**
	 * Realiza la operacion de select
	 * @param o Entidad relacionada con una tabla
	 * @param fields Campos de la tabla o del query TableFields --> Campos de la tabla, SelectFields --> Campos del query
	 * @param sqlSentense Query select
	 * @param parameters Parametros que sustiyuyen los ? del query
	 * @return Resultado de la consulta
	 * @throws SQLDBException
	 * @throws Exception
	 */
	private List<Object> selectOperation(T o, Object fields, String sqlSentense, Object[] parameters) throws SQLDBException, Exception  {
		List<Object> regreso = new ArrayList<Object>();
		log.info("SQL [select-query]: " + sqlSentense);
		log.info("SQL [select-parameters]: " + parameters);
		List<Map<String, Object>> registros = jdbcTemplate.queryForList(sqlSentense, parameters);
		if (registros != null && !registros.isEmpty()) {
			regreso = new ArrayList<Object>();
			for (Map<String, Object> info : registros) {
				if (fields instanceof TableFields)
					regreso.add(setValFields(o,info, (TableFields)fields));
				else if (fields instanceof SelectFields)
					regreso.add(setValFields(o, info, (SelectFields)fields));
			}
		}
		return regreso;
	}
	
	
	/**
	 * Metodo generico para ejecutar un query de update, insert u delete
	 * @param sqlSentense Sentencia SQL
	 * @param parameters  Parametros del query, los que sustituyen los ?
	 * @return
	 * @throws SQLDBException
	 * @throws Exception
	 */
	@Override
	public Integer executeSQL(String sqlSentense, Object... parameters) throws Exception {
		log.info("SQL [executeSQL]: " + sqlSentense);
		Integer result = 0;
		result = jdbcTemplate.update(new PreparedStatementCreator(){
            public PreparedStatement createPreparedStatement(Connection connection)
                throws SQLException {
                PreparedStatement ps =connection.prepareStatement(sqlSentense);
                if (parameters != null && parameters.length > 0){
    				//Colocar los parametros en los ? del query
    				setParametersQuery(ps, parameters);
    			}
                return ps;
            }
        });
		return result;
	}
	
	
	
	
	private void setParametersQuery (PreparedStatement ps, Object[]parameters) throws SQLException{
		int indice = 1;
		for (Object pa : parameters){
			if (pa instanceof java.lang.String){
				String val = (String) pa;
				setString(ps, indice, val);
			} else if (pa instanceof Integer){
				Integer val = (Integer)pa;
				setInteger(ps, indice, val);
			} else if (pa instanceof Long){
				Long val = (Long)pa;
				setLong(ps, indice, val);
			} else if (pa instanceof  Double){
				Double val = (Double) pa;
				setDouble(ps, indice, val);
			} else if (pa instanceof Short){
				Short val = (Short)pa;
				setShort(ps, indice, val);
			} else if (pa instanceof Float){
				Float val = (Float)pa;
				setFloat(ps, indice, val);
			} else if (pa instanceof Boolean){
				Boolean val =(Boolean) pa;
				setBoolean(ps, indice, val);
			} else if (pa instanceof  java.sql.Timestamp){
				java.sql.Timestamp val = (java.sql.Timestamp)pa;
				setTimestamp(ps, indice, val);
			} else if (pa instanceof  java.util.Date){
				java.util.Date val = (java.util.Date)pa;
				setTimestamp(ps, indice, new Timestamp(val.getTime()));
			} else if (pa instanceof  java.sql.Date){
				java.sql.Date val = (java.sql.Date)pa;
				setTimestamp(ps, indice, new Timestamp(val.getTime()));
			} else if (pa instanceof BigDecimal){
				BigDecimal val = (BigDecimal)pa;
				setBigDecimal(ps, indice, val);
			} else if (pa instanceof Byte){
				Byte val = (Byte)pa;
				setByte(ps, indice, val);
			} else if (pa instanceof BigInteger){
				BigInteger val = (BigInteger)pa;
				setBigInteger(ps, indice, val);
			}
			indice ++;
		}
			
			
	}
	
	
	
	
	
	private Object setValFields (T o, Map<String, Object> info, SelectFields fields) throws SQLDBException, Exception  {
		Object nuevo = o.createNew();
		Set<String> llaves = fields.getFields().keySet();
		for (String columna : llaves){
			Field atributo = fields.getFields().get(columna);
			extract(info, columna, nuevo, atributo);
		}
		return nuevo;
	}

	
	
	private Object setValFields (T o,Map<String, Object> info, TableFields tableFields) throws SQLDBException, Exception  {
		Object nuevo = o.createNew();
		for (int i=0; i < tableFields.getFieldTag().size(); i++){
			Field atributo = tableFields.getField().get(i);
			String columna = tableFields.getFieldTag().get(i).name();
			extract(info, columna, nuevo, atributo);
		}
		return nuevo;
	}
	
	@SuppressWarnings("rawtypes")
	private void  extract (Map<String, Object> info, String columna,Object nuevo, Field atributo) throws IllegalArgumentException, IllegalAccessException{
		if (info.get(columna) != null) {
			Class tipo = atributo.getType();
			if (tipo.equals(java.lang.String.class)){
				String val = (String) info.get(columna);
				atributo.set(nuevo, val);
			} else if (tipo.equals(java.lang.Integer.class)){
				Object valor = info.get(columna);
				if (valor instanceof java.lang.Byte){
					Byte va = (Byte) valor;
					atributo.set(nuevo, va.intValue());
				} else if (valor instanceof java.lang.Short){
					Short va = (Short) valor;
					atributo.set(nuevo, va.intValue());
				} else if (valor instanceof java.lang.Integer) {
					Integer va = (Integer) valor;
					atributo.set(nuevo, va);
				} else if (valor instanceof java.lang.Long) {
					Long va = (Long) valor;
					atributo.set(nuevo, va.intValue());
				} else if (valor instanceof java.lang.Float) {
					Float va = (Float) valor;
					atributo.set(nuevo, va.intValue());
				} else if (valor instanceof java.lang.Double) {
					Double va = (Double) valor;
					atributo.set(nuevo, va.intValue());
				} else if (valor instanceof java.math.BigDecimal) {
					BigDecimal va = (BigDecimal) valor;
					atributo.set(nuevo, va.intValue());
				} else if (valor instanceof java.math.BigInteger) {
					BigInteger va = (BigInteger) valor;
					atributo.set(nuevo, va.intValue());
				}
			} else if (tipo.equals(java.lang.Long.class)){
				Object valor = info.get(columna);
				if (valor instanceof java.lang.Byte){
					Byte va = (Byte) valor;
					atributo.set(nuevo, va.longValue());
				} else if (valor instanceof java.lang.Short){
					Short va = (Short) valor;
					atributo.set(nuevo, va.longValue());
				} else if (valor instanceof java.lang.Integer) {
					Integer va = (Integer) valor;
					atributo.set(nuevo, va.longValue());
				} else if (valor instanceof java.lang.Long) {
					Long va = (Long) valor;
					atributo.set(nuevo, va);
				} else if (valor instanceof java.lang.Float) {
					Float va = (Float) valor;
					atributo.set(nuevo, va.longValue());
				} else if (valor instanceof java.lang.Double) {
					Double va = (Double) valor;
					atributo.set(nuevo, va.longValue());
				} else if (valor instanceof java.math.BigDecimal) {
					BigDecimal va = (BigDecimal) valor;
					atributo.set(nuevo, va.longValue());
				} else if (valor instanceof java.math.BigInteger) {
					BigInteger va = (BigInteger) valor;
					atributo.set(nuevo, va.longValue());
				}
			} else if (tipo.equals(java.lang.Double.class)){
				Object valor = info.get(columna);
				if (valor instanceof java.lang.Byte){
					Byte va = (Byte) valor;
					atributo.set(nuevo, va.doubleValue());
				} else if (valor instanceof java.lang.Short){
					Short va = (Short) valor;
					atributo.set(nuevo, va.doubleValue());
				} else if (valor instanceof java.lang.Integer) {
					Integer va = (Integer) valor;
					atributo.set(nuevo, va.doubleValue());
				} else if (valor instanceof java.lang.Long) {
					Long va = (Long) valor;
					atributo.set(nuevo, va.doubleValue());
				} else if (valor instanceof java.lang.Float) {
					Float va = (Float) valor;
					atributo.set(nuevo, va.doubleValue());
				} else if (valor instanceof java.lang.Double) {
					Double va = (Double) valor;
					atributo.set(nuevo, va);
				} else if (valor instanceof java.math.BigDecimal) {
					BigDecimal va = (BigDecimal) valor;
					atributo.set(nuevo, va.doubleValue());
				} else if (valor instanceof java.math.BigInteger) {
					BigInteger va = (BigInteger) valor;
					atributo.set(nuevo, va.doubleValue());
				}
			} else if (tipo.equals(java.lang.Short.class)){
				Object valor = info.get(columna);
				if (valor instanceof java.lang.Byte){
					Byte va = (Byte) valor;
					atributo.set(nuevo, va.shortValue());
				} else if (valor instanceof java.lang.Short){
					Short va = (Short) valor;
					atributo.set(nuevo, va);
				} else if (valor instanceof java.lang.Integer) {
					Integer va = (Integer) valor;
					atributo.set(nuevo, va.shortValue());
				} else if (valor instanceof java.lang.Long) {
					Long va = (Long) valor;
					atributo.set(nuevo, va.shortValue());
				} else if (valor instanceof java.lang.Float) {
					Float va = (Float) valor;
					atributo.set(nuevo, va.shortValue());
				} else if (valor instanceof java.lang.Double) {
					Double va = (Double) valor;
					atributo.set(nuevo, va.shortValue());
				} else if (valor instanceof java.math.BigDecimal) {
					BigDecimal va = (BigDecimal) valor;
					atributo.set(nuevo, va.shortValue());
				} else if (valor instanceof java.math.BigInteger) {
					BigInteger va = (BigInteger) valor;
					atributo.set(nuevo, va.shortValue());
				}
			} else if (tipo.equals(java.lang.Float.class)){
				Object valor = info.get(columna);
				if (valor instanceof java.lang.Byte){
					Byte va = (Byte) valor;
					atributo.set(nuevo, va.floatValue());
				} else if (valor instanceof java.lang.Short){
					Short va = (Short) valor;
					atributo.set(nuevo, va.floatValue());
				} else if (valor instanceof java.lang.Integer) {
					Integer va = (Integer) valor;
					atributo.set(nuevo, va.floatValue());
				} else if (valor instanceof java.lang.Long) {
					Long va = (Long) valor;
					atributo.set(nuevo, va.floatValue());
				} else if (valor instanceof java.lang.Float) {
					Float va = (Float) valor;
					atributo.set(nuevo, va);
				} else if (valor instanceof java.lang.Double) {
					Double va = (Double) valor;
					atributo.set(nuevo, va.floatValue());
				} else if (valor instanceof java.math.BigDecimal) {
					BigDecimal va = (BigDecimal) valor;
					atributo.set(nuevo, va.floatValue());
				} else if (valor instanceof java.math.BigInteger) {
					BigInteger va = (BigInteger) valor;
					atributo.set(nuevo, va.floatValue());
				}
			} else if (tipo.equals(java.lang.Boolean.class)){
				Boolean val = (Boolean) info.get(columna);
				atributo.set(nuevo, val);
			} else if (tipo.equals(java.util.Date.class)){
				Object valor = info.get(columna);
				if (valor instanceof java.util.Date){
					java.util.Date val = (java.util.Date) info.get(columna);
					atributo.set(nuevo, convertJavaDateToJavaDate(val));
				} else if (valor instanceof java.sql.Date) {
					java.sql.Date val = (java.sql.Date) info.get(columna);
					atributo.set(nuevo, convertSqlDateToJavaDate(val));
				} else if (valor instanceof java.sql.Timestamp) {
					java.sql.Timestamp val = (java.sql.Timestamp) info.get(columna);
					atributo.set(nuevo, convertTimestampToJavaDate(val));
				}
			} else if (tipo.equals(java.sql.Date.class)){
				Object valor = info.get(columna);
				if (valor instanceof java.util.Date){
					java.util.Date val = (java.util.Date) info.get(columna);
					atributo.set(nuevo, convertJavaDateToSQLDate(val));
				} else if (valor instanceof java.sql.Date) {
					java.sql.Date val = (java.sql.Date) info.get(columna);
					atributo.set(nuevo, convertSqlDateToSQLDate(val));
				} else if (valor instanceof java.sql.Timestamp) {
					java.sql.Timestamp val = (java.sql.Timestamp) info.get(columna);
					atributo.set(nuevo, convertTimestampToSqlDate(val));
				}
			} else if (tipo.equals(java.sql.Timestamp.class)){
				Object valor = info.get(columna);
				if (valor instanceof java.util.Date){
					java.util.Date val = (java.util.Date) info.get(columna);
					atributo.set(nuevo, convertJavaDateToTimesStamp(val));
				} else if (valor instanceof java.sql.Date) {
					java.sql.Date val = (java.sql.Date) info.get(columna);
					atributo.set(nuevo, convertSqlDateToTimesStamp(val));
				} else if (valor instanceof java.sql.Timestamp) {
					java.sql.Timestamp val = (java.sql.Timestamp) info.get(columna);
					atributo.set(nuevo, convertTimeStampToTimesStamp(val));
				}
			} else if (tipo.equals(java.math.BigDecimal.class)){
				Object valor = info.get(columna);
				if (valor instanceof java.lang.Byte){
					Byte va = (Byte) valor;
					atributo.set(nuevo, new BigDecimal(va));
				} else if (valor instanceof java.lang.Short){
					Short va = (Short) valor;
					atributo.set(nuevo, new BigDecimal(va));
				} else if (valor instanceof java.lang.Integer) {
					Integer va = (Integer) valor;
					atributo.set(nuevo, new BigDecimal(va));
				} else if (valor instanceof java.lang.Long) {
					Long va = (Long) valor;
					atributo.set(nuevo, new BigDecimal(va));
				} else if (valor instanceof java.lang.Float) {
					Float va = (Float) valor;
					atributo.set(nuevo, new BigDecimal(va));
				} else if (valor instanceof java.lang.Double) {
					Double va = (Double) valor;
					atributo.set(nuevo, new BigDecimal(va));
				} else if (valor instanceof java.math.BigDecimal) {
					BigDecimal va = (BigDecimal) valor;
					atributo.set(nuevo, va);
				} else if (valor instanceof java.math.BigInteger) {
					BigInteger va = (BigInteger) valor;
					atributo.set(nuevo, new BigDecimal(va));
				}
			} else if (tipo.equals(java.lang.Byte.class)){
				Object valor = info.get(columna);
				if (valor instanceof java.lang.Byte){
					Byte va = (Byte) valor;
					atributo.set(nuevo, va);
				} else if (valor instanceof java.lang.Short){
					Short va = (Short) valor;
					atributo.set(nuevo, va.byteValue());
				} else if (valor instanceof java.lang.Integer) {
					Integer va = (Integer) valor;
					atributo.set(nuevo, va.byteValue());
				} else if (valor instanceof java.lang.Long) {
					Long va = (Long) valor;
					atributo.set(nuevo, va.byteValue());
				} else if (valor instanceof java.lang.Float) {
					Float va = (Float) valor;
					atributo.set(nuevo, va.byteValue());
				} else if (valor instanceof java.lang.Double) {
					Double va = (Double) valor;
					atributo.set(nuevo, va.byteValue());
				} else if (valor instanceof java.math.BigDecimal) {
					BigDecimal va = (BigDecimal) valor;
					atributo.set(nuevo, va.byteValue());
				} else if (valor instanceof java.math.BigInteger) {
					BigInteger va = (BigInteger) valor;
					atributo.set(nuevo, va.byteValue());
				}
			} else if (tipo.equals(java.math.BigInteger.class)){
				Object valor = info.get(columna);
				if (valor instanceof java.lang.Byte){
					Byte va = (Byte) valor;
					atributo.set(nuevo, new BigInteger(va.toString()));
				} else if (valor instanceof java.lang.Short){
					Short va = (Short) valor;
					atributo.set(nuevo, new BigInteger(va.toString()));
				} else if (valor instanceof java.lang.Integer) {
					Integer va = (Integer) valor;
					atributo.set(nuevo, new BigInteger(va.toString()));
				} else if (valor instanceof java.lang.Long) {
					Long va = (Long) valor;
					atributo.set(nuevo, new BigInteger(va.toString()));
				} else if (valor instanceof java.lang.Float) {
					Float va = (Float) valor;
					atributo.set(nuevo, new BigInteger(va.toString()));
				} else if (valor instanceof java.lang.Double) {
					Double va = (Double) valor;
					atributo.set(nuevo, new BigInteger(va.toString()));
				} else if (valor instanceof java.math.BigDecimal) {
					BigDecimal va = (BigDecimal) valor;
					atributo.set(nuevo, new BigInteger(va.toString()));
				} else if (valor instanceof java.math.BigInteger) {
					BigInteger va = (BigInteger) valor;
					atributo.set(nuevo, new BigInteger(va.toString()));
				}
			}
		} else {
			atributo.set(nuevo, null);
		}
	}
	
	
	 
	 
	 
	/**
	 * Consulta la informacion realacionada con la tabla y sus campos
	 * @param object Entidad relacionada con una tabla
	 * @return
	 * @throws SQLDBException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private  TableFields getTableAndFields (T object) throws SQLDBException{
		TableFields regreso = new TableFields();
		List<AnnotationTableField> fieldTags =  null;
		List<Field> field = null;
		Class clase  = object.getClass();
		String className = clase.getName();
		AnnotationTable classAnotacion = (AnnotationTable) clase.getAnnotation(AnnotationTable.class);
		//Validamos este etiquetada la clase y que este el nombre de la TABLA
		if (classAnotacion != null &&  classAnotacion.name() != null && classAnotacion.name().length() > 0 ){
			
			//Obtener atributos
			Field[] atributos = clase.getDeclaredFields();
			if (atributos != null && atributos.length > 0){
				fieldTags = new ArrayList<AnnotationTableField>();
				field = new ArrayList<Field>();
				int contadorLlavePrimaria = 0;
				for (Field atributo : atributos){
					
					//Obtener anotacion de atributo
					AnnotationTableField anotacion = atributo.getAnnotation(AnnotationTableField.class);
					//Solo agregamos los atributos etiquetados
					if (anotacion != null && anotacion.name() != null && anotacion.name().length() > 0 ){
						fieldTags.add(anotacion);
						field.add(atributo);
						if (anotacion.id()){
							regreso.setIdAnotation(anotacion);
							regreso.setId(atributo);
							contadorLlavePrimaria++;
						}
					}
				}
				//Validar que solo se tenga una a lo mucho una llave primaria
				if (contadorLlavePrimaria > 1){
					throw new SQLDBException(SQLDBException.DEFALT_CODE, "La clase " + className +" tiene asignado mas de un ID");
				}
			} else {
				throw new SQLDBException(SQLDBException.DEFALT_CODE, "La clase " + className +" no tiene nungun atributo");
			}
		} else {
			throw new SQLDBException(SQLDBException.DEFALT_CODE, "La clase " + className +" debe de estar etiquetada y se debe especificar el nombre de la tabla");
		}
		
		//Validar que por lo menos tenga un campo etiquetado
		if (fieldTags == null || fieldTags.isEmpty()){
			throw new SQLDBException(SQLDBException.DEFALT_CODE, "La clase " + className +" debe tener por lo menos un atributo etiquetado");
		} 
		
		regreso.setFieldTag(fieldTags);
		regreso.setClase(clase);
		regreso.setClassName(className);
		regreso.setTableName(classAnotacion.name());
		regreso.setSchema(classAnotacion.schema());
		regreso.setField(field);
		return regreso;
	}
	
	
	
	
	
	/**
	 * Obtiene la informacion de para las operciones de select genericas
	 * @param object Clase donde cada atributo representa un campo del query select
	 * @return
	 * @throws SQLDBException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private  SelectFields getQueryAndFields (T object) throws SQLDBException{
		SelectFields regreso = new SelectFields();
		Class clase  = object.getClass();
		String className = clase.getName();
		Map<String, Field> campos = null;
		String query = null;
		
		AnnotationSelectQuery queryAnotacion = (AnnotationSelectQuery) clase.getAnnotation(AnnotationSelectQuery.class);
		if (queryAnotacion != null && 
				queryAnotacion.sentense() != null && 
				queryAnotacion.sentense().length > 0){
			//Por el momento es el primero
			query = queryAnotacion.sentense()[0];
			//Obtener atributos
			Field[] atributos = clase.getFields();
			if (atributos != null && atributos.length > 0){
				campos = new HashMap<String, Field>();
				for (Field atributo : atributos){
					//Obtener anotacion de atributo
					AnnotationSelectField anotacion = atributo.getAnnotation(AnnotationSelectField.class);
					//Solo agregamos los atributos etiquetados
					if (anotacion != null && anotacion.nameColumn() != null && anotacion.nameColumn().length() > 0){
						campos.put(anotacion.nameColumn(), atributo);
					}
				}
				
			} else {
				throw new SQLDBException(SQLDBException.DEFALT_CODE, "La clase " + className +" no tiene nungun campo en el select");
			}
		} else {
			throw new SQLDBException(SQLDBException.DEFALT_CODE, "La clase " + className +" debe de estar etiquetada con minimo un query");
		}
		
		regreso.setQuery(query);
		regreso.setClase(clase);
		regreso.setClassName(className);
		regreso.setFields(campos);
		return regreso;
	}
	
	
	/**
	 * Metodo de utileria que convierte un arreglo de objetos a su representacion T
	 * @param resultado List<T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<T> llenarLista (List<Object> resultado){
		List<T> regreso = null;
		if (resultado != null && !resultado.isEmpty()){
			regreso = new ArrayList<T>();
			for (Object objeto : resultado){
				if (objeto != null)
					regreso.add((T) objeto);
			}
		}
		return regreso;
	}
	
	
	/**
	 * Metodo de ulileria que convierte un array de valores numericos a un array de su representacion en String
	 * @param array Arreglo de valores numericos
	 * @return
	 * @throws SQLDBException
	 */
	private String[] numberArrayToStringArray (Number[] array) throws SQLDBException{
		String [] regreso = null;
		if (array != null && array.length > 0){
			regreso = new String[array.length];
			int contador = 0;
			for (Number n : array){
				if (n instanceof Long || 
						n instanceof Integer || 
						n instanceof Short){
					regreso[contador] = n.toString();
				} else {
					throw new SQLDBException(SQLDBException.DEFALT_CODE, "Las restriciones en una sentencia IN, deben ser ids de tipo Long,Integer o Short");
				}
				contador++;
			}
		}
		return regreso;
	}
	
	
	
	
	
	/**
	 * Asigna el valor de una cadena a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar de tipo Boolean.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 * 
	 */
	private void setBoolean (PreparedStatement ps, int indice, Boolean valor )  throws SQLException {
		if (valor == null )
			ps.setNull (indice, java.sql.Types.VARCHAR);
		else
			ps.setBoolean (indice, valor.booleanValue());
	}

	

	/**
	 * Asigna el valor de una cadena a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 */
	private void setString (PreparedStatement ps, int indice, String valor)  throws SQLException {
		if (valor == null )
			ps.setNull (indice, java.sql.Types.VARCHAR);
		else
			ps.setString (indice, valor);
	}

	

	/**
	 * Asigna el valor de un objeto tipo Byte a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 */
	private void setByte (PreparedStatement ps, int indice, Byte valor)  throws SQLException {
		if (valor == null ) 
			ps.setNull (indice, java.sql.Types.BIT);
		else 
			ps.setByte (indice, valor.byteValue());
	}

	
	

	/**
	 * Asigna el valor de un objeto tipo BigDecimal a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 */
	private void setBigDecimal (PreparedStatement ps, int indice, BigDecimal valor)  throws SQLException {
		if (valor == null ) 
			ps.setNull (indice, java.sql.Types.DECIMAL);
		else
			ps.setBigDecimal (indice, valor);
	}
	
	
	private void setBigInteger (PreparedStatement ps, int indice, BigInteger valor)  throws SQLException {
		if (valor == null ) 
			ps.setNull (indice, java.sql.Types.BIGINT);
		else
			ps.setInt (indice, valor.intValueExact());
	}

	

	/**
	 * Asigna el valor de un objeto tipo Double a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 */
	private void setDouble (PreparedStatement ps, int indice, Double valor)  throws SQLException {
		if (valor == null ) 
			ps.setNull (indice, java.sql.Types.DOUBLE);
		else
			ps.setDouble (indice, valor.doubleValue());
	}

	

	/**
	 * Asigna el valor de un objeto tipo Integer a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 */
	private void setInteger (PreparedStatement ps, int indice, Integer valor)  throws SQLException {
		if (valor == null )
			ps.setNull (indice, java.sql.Types.INTEGER);
		else
			ps.setInt  (indice, valor.intValue());
	}

	

	/**
	 * Asigna el valor de un objeto tipo Short a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 */
	private void setShort (PreparedStatement ps, int indice, Short valor)  throws SQLException {
		if (valor == null )
			ps.setNull (indice, java.sql.Types.SMALLINT);
		else
			ps.setShort (indice, valor);
	}

	

	/**
	 * Asigna el valor de un objeto tipo Long a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 */
	private void setLong (PreparedStatement ps, int indice, Long valor)  throws SQLException {
		if ( valor == null )
			ps.setNull (indice, java.sql.Types.NUMERIC);
		else
			ps.setLong (indice, valor.longValue());
	}

	

	/**
	 * Asigna el valor de un objeto tipo Float a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 */
	private void setFloat ( PreparedStatement ps, int indice, Float valor)  throws SQLException {
		if (valor == null)
			ps.setNull (indice, java.sql.Types.FLOAT);
		else
			ps.setFloat (indice, valor.floatValue());
	}

	

	/**
	 * Asigna el valor de un objeto tipo Float a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 */
	private void setTimestamp ( PreparedStatement ps, int indice, Timestamp valor)  throws SQLException {
		if (valor == null)
			ps.setNull (indice, java.sql.Types.TIMESTAMP);
		else
			ps.setTimestamp (indice, valor);
	}
	
	
	/**
	 * Asigna el valor de un objeto tipo java.util.Date a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 */
	@SuppressWarnings("unused")
	private void setDate (PreparedStatement ps, int indice, java.util.Date valor)  throws SQLException {
		if (valor == null ) 
			ps.setNull (indice, java.sql.Types.DATE);
		else
			ps.setDate (indice, new java.sql.Date (valor.getTime()));
	}
	
	/**
	 * Asigna el valor de un objeto tipo java.sql.Date a una columna al PreparedStatement.
	 * @param ps Es el PreparedStatement de la conexion de la base de datos.
	 * @param indice Es la posicion del campo en que se almacenara el registro.
	 * @param valor Es el valor a almacenar.
	 * @throws SQLException Es la excepcion de tipo SQLException.
	 */
	@SuppressWarnings("unused")
	private void setDate (PreparedStatement ps, int indice, java.sql.Date valor)  throws SQLException {
		if (valor == null ) 
			ps.setNull (indice, java.sql.Types.DATE);
		else
			ps.setDate (indice, valor);
	}
	
	
	
	
	private java.util.Date convertTimestampToJavaDate(java.sql.Timestamp time) {
        java.util.Date javaDate = null;
        if (time != null) 
            javaDate = new java.util.Date(time.getTime());
        return javaDate;
	 }

	
	 private java.sql.Date convertTimestampToSqlDate(java.sql.Timestamp time) {
	 	java.sql.Date sqlDate = null;
        if (time != null) 
        	sqlDate = new java.sql.Date(time.getTime());
        return sqlDate;
	 }
	 
	 private java.sql.Timestamp convertJavaDateToTimesStamp(java.util.Date date) {
		 java.sql.Timestamp  timestamp = null;
         if (date != null) 
        	 timestamp = new java.sql.Timestamp(date.getTime());
         return timestamp;
	 }
	 
	 private java.sql.Timestamp convertSqlDateToTimesStamp(java.sql.Date date) {
		 java.sql.Timestamp  timestamp = null;
         if (date != null) 
        	 timestamp = new java.sql.Timestamp(date.getTime());
         return timestamp;
	 }
	 
	 private java.util.Date convertSqlDateToJavaDate(java.sql.Date date) {
		 java.util.Date javaDate = null;
         if (date != null) 
        	 javaDate = new java.util.Date(date.getTime());
         return javaDate;
	 }
	 
	 private java.sql.Date convertJavaDateToSQLDate(java.util.Date date) {
		 java.sql.Date sqlDate = null;
         if (date != null) 
        	 sqlDate = new java.sql.Date(date.getTime());
         return sqlDate;
	 }
	 
	 
	 private java.util.Date convertJavaDateToJavaDate(java.util.Date date) {
		 java.util.Date utilDate = null;
         if (date != null) 
        	 utilDate = new java.util.Date(date.getTime());
         return utilDate;
	 }

	 private java.sql.Timestamp convertTimeStampToTimesStamp(java.sql.Timestamp time) {
		 java.sql.Timestamp  timestamp = null;
         if (time != null) 
        	 timestamp = new java.sql.Timestamp(time.getTime());
         return timestamp;
	 }
	 
	 
	 private java.sql.Date convertSqlDateToSQLDate(java.sql.Date date) {
		 java.sql.Date sqlDate = null;
         if (date != null) 
        	 sqlDate = new java.sql.Date(date.getTime());
         return sqlDate;
	 }
	

}
