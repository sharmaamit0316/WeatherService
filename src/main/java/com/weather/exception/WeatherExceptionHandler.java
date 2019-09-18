package com.weather.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom Exception Handler to handle all custom exceptions
 * @author Amit.Sharma88
 *
 */
@ControllerAdvice
public class WeatherExceptionHandler extends ResponseEntityExceptionHandler{

	/**
	 * Contains the logic for handling the {@link WeatherException} in application
	 */
	private static final Logger logger=LoggerFactory.getLogger(WeatherExceptionHandler.class);
	
	public ResponseEntity<?> handleWeatherException(WeatherException exception){
		logger.debug("Exception : {}", exception);
		ResponseEntity<?> response=new ResponseEntity<>(exception.getErrorMessage(),exception.getStatus());
		return response;
	}
}
