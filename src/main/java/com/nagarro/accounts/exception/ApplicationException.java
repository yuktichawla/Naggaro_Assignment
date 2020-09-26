package com.nagarro.accounts.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApplicationException extends RuntimeException {

	private String errorCode;
	private String errorMessage;
	private int statusCode = HttpStatus.BAD_REQUEST.value();
	
	public ApplicationException(String errorCode, String errorMessage, int statusCode) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.statusCode = statusCode;
	}
	
	public ApplicationException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	
}
