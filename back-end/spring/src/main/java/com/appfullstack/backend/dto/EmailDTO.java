package com.appfullstack.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {

	@NotBlank(message = "Email can't be empty")
	@Email(message = "Invalid email")
	private String email;
	
	public EmailDTO() {
	}

	public EmailDTO(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
	
}
