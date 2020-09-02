package com.microc.bankmanagement.userservice.dto;

import java.util.List;
import java.util.Map;

public class NewUserResponse {
	private List<String> validationErrors;
	private Map<String, String> successResponse;

	public Map<String, String> getSuccessResponse() {
		return successResponse;
	}

	public void setSuccessResponse(Map<String, String> successResponse) {
		this.successResponse = successResponse;
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<String> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
