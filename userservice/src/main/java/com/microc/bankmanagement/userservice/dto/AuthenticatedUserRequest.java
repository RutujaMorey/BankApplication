package com.microc.bankmanagement.userservice.dto;

import java.io.Serializable;

public class AuthenticatedUserRequest implements Serializable {

	private static final long serialVersionUID = -3406654087724803954L;

	public AuthenticatedUserRequest(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
