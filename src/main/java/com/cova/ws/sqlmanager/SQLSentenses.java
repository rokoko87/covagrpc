package com.cova.ws.sqlmanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.cova.ws.sqlmanager.annotations.AnnotationTableField;
import com.cova.ws.sqlmanager.dtos.TableFields;



/**
 * Clase que genera la santencias SQL para las distintas tipos de operacions (INSERT, UPDATE, DELETE, SELECT)
 * @author alberto.vazquez
 *
 * @param <T> Objeto Entidad (Tabla de base de datos) que contiene la metadata (informacion de la tabla) y los valores propios
 */
public class SQLSentenses <T>{
	
	public static final String SELECT = "SELECT";
	public static final String DELETE = "DELETE";
	public static final String INSERT = "INSERT INTO";
	public static final String UPDATE = "UPDATE";
	public static final String SET = "SET";
	public static final String FROM = "FROM";
	public static final String VALUES = "VALUES";
	public static final String NULL = "NULL";
	public static final String WHERE = "WHERE";
	public static final String AND = "AND";
	public static final String LIKE = "LIKE";
	public static final String IN = "IN";
	public static final String ESPACIO = " ";
	public static final String VACIO = "";
	public static final String PARENTESIS = "(";
	public static final String _PARENTESIS = ")";
	public static final String PUNTO = ".";
	public static final String IGUAL = "=";
	public static final String COMA = ",";
	public static final String COMILLA = "'";
	public static final String PORCENTAJE = "%";
	
	
	//Oracle
	public static final String DATE_ORACLE_FORMAT = "YYYY-MM-DD HH24:MI:SS";
	public static final String TO_DATE = "TO_DATE";
	
	//MySql
	public static final String DATE_MYSQL_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	
	//Configurar esta informacion con base a la base de datos que se esta utilizando
	public static final String DATE_FORMAT = DATE_MYSQL_FORMAT;
	
	
	
	/**
	 * Obtiene la sentencia DELETE basica es decir --> DELETE FROM SCHEMA.TABLA
	 * @param object Entidad relacionada con una tabla
	 * @param tableFields Informacion de la tabla, en particular se utiliza el valor del nombre de la tabla y el schema
	 * @return Sentencia generada
	 * @throws SQLDBException
	 * @throws Exception
	 */
	public String getDeleteSentence (T object, TableFields  tableFields) throws SQLDBException, Exception{
		//Armar la sentencia DELETE
		StringBuilder total = new StringBuilder ("");
		total.append(DELETE);
		total.append(ESPACIO);
		
		total.append(FROM);
		total.append(ESPACIO);
		if (tableFields.getSchema() != null && tableFields.getSchema().length() > 0){
			total.append(tableFields.getSchema());
			total.append(PUNTO);
		}
		total.append(tableFields.getTableName());
		return total.toString();
	}
	
	
	/**
	 * Obtiene la sentencia SELECT basica, es decir --> SELECT (CAMPO1, CAMPO2, CAMPO3, ... ,CAMPON) FROM SCHEMA.TABLA
	 * @param object Entidad relacionada con una tabla
	 * @param tableFields Informacion de la tabla
	 * @return
	 * @throws SQLDBException
	 * @throws Exception
	 */
	public String getSelectSentence (T object, TableFields  tableFields) throws SQLDBException, Exception{
		StringBuilder fields = new StringBuilder("");
		for (AnnotationTableField fieldTag : tableFields.getFieldTag()){
			fields.append(fieldTag.name());
			fields.append(COMA);
		}
		
		StringBuilder total = new StringBuilder ("");
		total.append(SELECT);
		total.append(ESPACIO);
		
		String fie = fields.toString().trim();
		total.append(fie.substring(0, fie.length()-1));
		
		total.append(ESPACIO);
		
		total.append(FROM);
		total.append(ESPACIO);
		if (tableFields.getSchema() != null && tableFields.getSchema().length() > 0){
			total.append(tableFields.getSchema());
			total.append(PUNTO);
		}
		total.append(tableFields.getTableName());
		return total.toString();
	}
	
	
	/**
	 * Obtiene la sentencia INSERT completa, es decir --> INSERT INTO SCHEMA.TABLA (CAMPO1, CAMPO2, CAMPO3, ... ,CAMPON) VALUES (VALOR1, VALOR2, VALOR3, ... ,VALORN)
	 * @param object Entidad relacionada con una tabla
	 * @param tableFields Informacion de la tabla
	 * @return
	 * @throws SQLDBException
	 * @throws Exception
	 */
	public String getInsertSentence (T object, TableFields  tableFields) throws SQLDBException, Exception{
		StringBuilder fields = new StringBuilder("");
		StringBuilder values = new StringBuilder ("");
		
		A: for (int i=0; i< tableFields.getFieldTag().size(); i++){
			
			if (tableFields.getFieldTag().get(i).name().equals(tableFields.getIdAnotation().name())){
				//El campo evaluando es el ID
				if(tableFields.getIdAnotation().autogenetaratedKey()){
					//Si el campo Id se generar automaticamente mediante un secuencia, tiene que venir
					//nulo aunque la etiqueta diga que es un campo no nullable, ya que se genera en 
					//automatico
					if (tableFields.getId().get(object) != null){
						throw new SQLDBException(SQLDBException.DEFALT_CODE, 
								"El campo " + tableFields.getTableName()+ "." + tableFields.getIdAnotation().name() + " es un id autogenerado, no puede tener un valor.");
					}
					
					//No se coloca en el script
					continue A;
				} else {
					if (tableFields.getId().get(object) == null){
						throw new SQLDBException(SQLDBException.DEFALT_CODE, 
								"El campo " + tableFields.getTableName()+ "." + tableFields.getIdAnotation().name() + " es un id, debe de tener un valor asignado");
					}
				}
			} else{
				//Validar que los valores not nullables vengan poblados
				if (tableFields.getField().get(i).get(object) == null && !tableFields.getFieldTag().get(i).nullable()){
					throw new SQLDBException(SQLDBException.DEFALT_CODE, 
							"El campo " + tableFields.getTableName()+ "." + tableFields.getFieldTag().get(i).name() + " no puede ingresarse como null");
				}
			}
			
			
			fields.append(tableFields.getFieldTag().get(i).name());
			fields.append(COMA);
			
			//Concatenar los valores, en base al tipo de dato (Texto, Numerico, Boolean, Fecha)
			
			values.append(valuesFormt(tableFields.getField().get(i).get(object), tableFields.getFieldTag().get(i)));
			values.append(COMA);
			
		}
		
		//Armar la sentencia INSERT
		StringBuilder total = new StringBuilder ("");
		total.append(INSERT);
		total.append(ESPACIO);
		if (tableFields.getSchema() != null && tableFields.getSchema().length() > 0){
			total.append(tableFields.getSchema());
			total.append(PUNTO);
		}
		total.append(tableFields.getTableName());
		total.append(ESPACIO);
		total.append(PARENTESIS);
		String fie = fields.toString().trim();
		total.append(fie.substring(0, fie.length()-1));
		total.append(_PARENTESIS);
		total.append(ESPACIO);
		total.append(VALUES);
		total.append(ESPACIO);
		total.append(PARENTESIS);
		String va = values.toString().trim();
		total.append(va.substring(0, va.length()-1));
		total.append(_PARENTESIS);
		
		return total.toString();
		
	
	
	}
	
	
	/**
	 * Obtiene la sentencia UPDATE basica, es decir UPDATE SCHEMA.TABLA SET CAMPO1 = VAL1 ... CAMPON = VALN 
	 * @param object Entidad relacionada con una tabla
	 * @param tableFields Informacion de la tabla
	 * @return
	 * @throws SQLDBException
	 * @throws Exception
	 */
	public String getUpdateSentence (T object, TableFields  tableFields) throws SQLDBException, Exception{
		//Armar la sentencia UPDATE
		
		StringBuilder total = new StringBuilder ("");
		total.append(UPDATE);
		total.append(ESPACIO);
		if (tableFields.getSchema() != null && tableFields.getSchema().length() > 0){
			total.append(tableFields.getSchema());
			total.append(PUNTO);
		}
		total.append(tableFields.getTableName());
		total.append(ESPACIO);
		total.append(SET);
		total.append(ESPACIO);
		
		for (int i=0; i< tableFields.getField().size(); i++){
			//Se agrega solo si no es el Id
			if (!tableFields.getFieldTag().get(i).id()){
				
				//Validar que los valores not nullables vengan poblados
				if (tableFields.getField().get(i).get(object) == null && !tableFields.getFieldTag().get(i).nullable()){
					throw new SQLDBException(SQLDBException.DEFALT_CODE, 
							"El campo " + tableFields.getTableName()+ "." + tableFields.getFieldTag().get(i).name() + " no puede ingresarse como null");
				}
				
				
				total.append (tableFields.getFieldTag().get(i).name());
				total.append(ESPACIO);
				total.append(IGUAL);
				total.append(ESPACIO);
				String valorFormat = valuesFormt(tableFields.getField().get(i).get(object), tableFields.getFieldTag().get(i));
				total.append (valorFormat);
				if (i != (tableFields.getField().size() -1)){
					total.append(COMA);
				}
			}
		}
		
		return total.toString();
	}
	
	
	/**
	 * Metodo general que obtiene la representacion SQL de un valor Java. Ejemplo
	 * Numeros --> 1
	 * String --> 'valor'
	 * Boolean --> 0,1
	 * Fechas--> '12-12-2019 HH:MM:SS' (Depende del formato de la base de datos)
	 * null --> NULL
	 * @param valor
	 * @param anotacion
	 * @return
	 * @throws SQLDBException
	 */
	private String valuesFormt (Object valor, AnnotationTableField anotacion) throws SQLDBException{
		StringBuilder value = new StringBuilder ("");
		if (valor != null){
			if (valor instanceof String){
				String v = (String) valor;
				value.append(COMILLA);
				if (v.length() > anotacion.maxLenght()){
					value.append(v.substring(0, anotacion.maxLenght()));
				} else {
					value.append(v);
				}
				value.append(COMILLA);
				
			} else if (valor instanceof Number){
				Number n = (Number) valor;
				value.append(n.toString());
				
			} else if (valor instanceof Date){
				value.append(COMILLA);
				String fechaFormat = formatDate((Date)valor, DATE_FORMAT);
				value.append(fechaFormat);
				value.append(COMILLA);
			}  else if (valor instanceof Boolean){
				Boolean b  = (Boolean) valor;
				value.append(b ? 1 : 0);
			} else {
				value.append(valor.toString());
			}
		} else {
			value.append(NULL);
			
		}
		return value.toString();
	}
	
	/**
	 * Metodo para formaterar la fecha
	 * @param a Fecha de tipo Date
	 * @param format Formato deseado
	 * @return
	 */
	public static String formatDate (Date a, String format){
		String regreso = "";
		SimpleDateFormat dt = new SimpleDateFormat(format);
		dt.setTimeZone(TimeZone.getTimeZone("UTC"));
		if (a != null){
			regreso = dt.format(a);
		}
		return regreso;
	}
	
	
	
	/**
	 * Obtiene la sentencia complementaria para para delimitar por Id --> WHERE ID = VALOR
	 * @param object Entidad relacionada con una tabla
	 * @param tableFields
	 * @return
	 * @throws SQLDBException
	 * @throws Exception
	 */
	public String byId (T object, TableFields  tableFields) throws SQLDBException, Exception{
		StringBuilder total = new StringBuilder("");
		if (tableFields.getId() == null){
			throw new SQLDBException (SQLDBException.DEFALT_CODE, "La tabla " + tableFields.getTableName() + " no tiene id" );
		} if (tableFields.getId().get(object) == null){
			throw new SQLDBException (SQLDBException.DEFALT_CODE, "Si deseas buscar/borrar/actualizar por id. Tienes que especificarlo en el objeto");
		} else {
			total.append(ESPACIO);
			total.append(WHERE);
			total.append(ESPACIO);
			total.append(tableFields.getIdAnotation().name());
			total.append(ESPACIO);
			total.append(IGUAL);
			total.append(ESPACIO);
			total.append(valuesFormt(tableFields.getId().get(object), tableFields.getIdAnotation()));
			
		}
		
		return total.toString();
	}
	
	
	/**
	 * Obtiene la sentencia complementaria para para delimitar por VARIOS IDS --> WHERE ID IN (VALOR1, VALOR2, ... VALORN)
	 * @param object Entidad relacionada con una tabla
	 * @param tableFields
	 * @param inValues Arreglos de ids
	 * @return
	 * @throws Exception
	 */
	public String idIn (T object, TableFields  tableFields, String [] inValues) throws Exception{
		StringBuilder total = new StringBuilder("");
		if (tableFields.getId() == null){
			throw new SQLDBException (SQLDBException.DEFALT_CODE, "La tabla " + tableFields.getTableName() + " no tiene id" );
		} else {
			total.append(ESPACIO);
			total.append(WHERE);
			total.append(ESPACIO);
			total.append(tableFields.getIdAnotation().name());
			total.append(ESPACIO);
			total.append(IN);
			total.append(ESPACIO);
			total.append(PARENTESIS);
			StringBuilder inV = new StringBuilder();
			for (String val : inValues){
				if (tableFields.getId().getType().equals(java.lang.String.class)){
					inV.append(COMILLA);
					inV.append(val);
					inV.append(COMILLA);
					inV.append(COMA);
				} else if (tableFields.getId().getType().equals(java.util.Date.class)){
					inV.append(COMILLA);
					SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
					String fechaFormat = df.format((Date) tableFields.getId().get(object));
					inV.append(fechaFormat);
					inV.append(COMILLA);
					inV.append(COMA);
				} else {
					inV.append(val);
					inV.append(COMA);
				}
			}
			if (inV.toString().length()> 0){
				total.append(inV.toString().substring(0, inV.toString().length()-1));
			}
			
			total.append(_PARENTESIS);
		}
		
		return total.toString();
	}
	
	

	
	/**
	 * Crea la sentencia complementaria para restringir por los dataBaseFields.
	 * Es decir --> WHERE dataBaseField1 = val1 AND dataBaseField1 = val2. 
	 * @param object Entidad relacionada con una tabla
	 * @param tableFields Informacion de la tabla
	 * @param dataBaseFields Campos a restringir (Nombre explicito del campo en base de datos). 
	 * Si el dataBaseField contiene un % al comienzo o al final se tomara con un like y se agrega el operador % para realizar la busqueda
	 * @return
	 * @throws Exception
	 */
	public String delimit (T object, TableFields  tableFields, String [] dataBaseFields) throws Exception{
		List<String> limitations = null;
		if (dataBaseFields != null && dataBaseFields.length > 0){
			limitations = new ArrayList<String>();
			for (String dbField : dataBaseFields){
				boolean encontreCampo = false;
				
				String copiadbField = dbField.toString();
				
				boolean isLikeInicio = false;
				boolean isLikeFinal = false;
				if (copiadbField.startsWith(PORCENTAJE))
					isLikeInicio = true;
				
				if (copiadbField.endsWith(PORCENTAJE))
					isLikeFinal = true;
				
				
				//Quitar los porcentajes
				copiadbField = copiadbField.replaceAll(PORCENTAJE, VACIO);
				
				//Por cada etiqueta (campo de la clase)
				for (int i=0; i< tableFields.getFieldTag().size(); i++){
					//Buscar el campo por el nombre
					if (copiadbField.toUpperCase().equals(
							tableFields.getFieldTag().get(i).name().toUpperCase())){
						
						//Valiar que sea un String o VARCHAR
						if (isLikeInicio || isLikeFinal){
							//Validar que el valor no se null
							if (tableFields.getField().get(i).get(object) == null){
								throw new SQLDBException (SQLDBException.DEFALT_CODE, "Si especificas el operador '%', el valor del campo " + tableFields.getFieldTag().get(i).name() + " puede ser null"); 
							} else {
								//Si no es null, que sea VARCHAR
								if (!(tableFields.getField().get(i).get(object) instanceof String))
									throw new SQLDBException (SQLDBException.DEFALT_CODE, "Si especificas el operador '%', el tipo del campo " + tableFields.getFieldTag().get(i).name() + " debe ser String"); 
								
							}
						}
						
						StringBuilder delimit = new StringBuilder ("");
						delimit.append(tableFields.getFieldTag().get(i).name());
						delimit.append(ESPACIO);
						
						if (isLikeInicio || isLikeFinal){
							delimit.append(LIKE);
							delimit.append(ESPACIO);
							delimit.append(COMILLA);
							if (isLikeInicio)
								delimit.append(PORCENTAJE);
							delimit.append(tableFields.getField().get(i).get(object));
							
							if (isLikeFinal)
								delimit.append(PORCENTAJE);
							
							delimit.append(COMILLA);
							
						} else {
							delimit.append(IGUAL);
							delimit.append(ESPACIO);
							delimit.append(valuesFormt(tableFields.getField().get(i).get(object), tableFields.getFieldTag().get(i)));
						}
						
						limitations.add(delimit.toString());
						encontreCampo = true;
					}
				}
				
				if (!encontreCampo)
					throw new SQLDBException (SQLDBException.DEFALT_CODE, "El campo "  + tableFields.getTableName() + "." + copiadbField + " no existe en la tabla"); 
			}
		}
		
		StringBuilder total = new StringBuilder ("");
		if (limitations != null && !limitations.isEmpty()){
			total.append(ESPACIO);
			total.append(WHERE);
			total.append(ESPACIO);
			total.append(limitations.get(0));
			
			for (int i=1; i< limitations.size(); i++){
				total.append(ESPACIO);
				total.append(AND);
				total.append(ESPACIO);
				total.append(limitations.get(i));
			}
		}
		
		return total.toString();
		
	}
	
	
	
	
	
	
}
