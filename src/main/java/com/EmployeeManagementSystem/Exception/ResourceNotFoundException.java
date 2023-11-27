package com.EmployeeManagementSystem.Exception;

import org.apache.catalina.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
    String fieldName;
    long fieldValue;
    SecurityProperties.User name;
    
    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) 
    {
        super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
	

}
