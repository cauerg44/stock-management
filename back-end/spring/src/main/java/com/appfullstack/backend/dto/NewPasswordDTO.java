package com.appfullstack.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewPasswordDTO {

	@NotBlank(message = "Required field")
	private String token;
	
	@NotBlank(message = "Required field")
	@Size(min = 8, message = "New password must have more than 8 characters")
	private String password;

	public NewPasswordDTO() {
	}
	
	public NewPasswordDTO(String token, String password) {
		this.token = token;
		this.password = password;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
