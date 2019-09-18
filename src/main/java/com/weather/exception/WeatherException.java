package com.weather.exception;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Amit.Sharma88
 * Custom Exception Class
 */
public class WeatherException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	private HttpStatus status=HttpStatus.INTERNAL_SERVER_ERROR;
	
	public WeatherException() {
		
	}
	
	/**
	 * @param errorMessage
	 */
	public WeatherException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	
	/**
	 * @param errorMessage
	 * @param status
	 */
	public WeatherException(String errorMessage, HttpStatus status) {
		super();
		this.errorMessage = errorMessage;
		this.status = status;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "WeatherException [errorMessage=" + errorMessage + ", status=" + status + "]";
	}
	
}
