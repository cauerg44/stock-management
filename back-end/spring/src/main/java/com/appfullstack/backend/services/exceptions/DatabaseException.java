package com.appfullstack.backend.services.exceptions;

@SuppressWarnings("serial")
public class DatabaseException extends RuntimeException {

	public DatabaseException(String errorMsg) {
		super(errorMsg);
	}
}
