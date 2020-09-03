package com.microc.bankmanagement.userservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperConversion {
	public static String jsonToString(Object obj) {
		String result;

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			result = "JSON processing error";
		}
		return result;
	}
}
