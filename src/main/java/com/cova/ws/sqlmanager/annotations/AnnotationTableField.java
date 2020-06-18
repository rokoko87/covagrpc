package com.cova.ws.sqlmanager.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AnnotationTableField {
	
	boolean id() default false;
	boolean autogenetaratedKey() default false;
	String name();
	int maxLenght() default 0;
	boolean nullable() default true;
	

}
