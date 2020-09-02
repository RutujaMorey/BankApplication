package com.microc.bankmanagement.userservice.dto;

import java.io.Serializable;

public class AuthenticatedUserResponse implements Serializable {

	private static final long serialVersionUID = 3317513310353981636L;
	private final String authenticationToken;
	private final String message;


	public String getMessage() {
		return message;
	}


	public String getAuthenticationToken() {
		return authenticationToken;
	}


	public AuthenticatedUserResponse(String token, String message) {
		this.authenticationToken = token;
		this.message = message;
	}

}
