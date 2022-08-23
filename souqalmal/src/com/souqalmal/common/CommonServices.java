package com.souqalmal.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonServices {
	public static final synchronized String convertJavaObjectToJSONstring(Object javaObject) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonString = mapper.writeValueAsString(javaObject);
			return jsonString;
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	public static final synchronized boolean isNullString(String propertyValue) {
		if (propertyValue != null && !propertyValue.trim().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public static final synchronized boolean isInteger(String propertyValue) {
		if (isNullString(propertyValue)) {
			return false;
		} else {
			try {
				Integer.parseInt(propertyValue);
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
	}
}
