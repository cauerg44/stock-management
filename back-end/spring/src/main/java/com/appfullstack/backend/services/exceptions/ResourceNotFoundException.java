package com.appfullstack.backend.services.exceptions;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String errorMsg) {
		super(errorMsg);
	}
}
