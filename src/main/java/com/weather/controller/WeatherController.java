package com.weather.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.weather.model.WeatherDetail;
import com.weather.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {
	
	private static final Logger logger=LoggerFactory.getLogger(WeatherController.class);
	
	@Autowired
	private WeatherService service;
	
	/**
	 * Resource method to fetch the weather information based on the city in the incoming request
	 * @param city
	 * @return 
	 */
	@GetMapping("/getData/{city}")
	public WeatherDetail getWeatherData(@PathVariable("city") final String city) {
		logger.info("Entering getWeatherData in WeatherController with city: {}",city);
		return service.getWeatherInfo(city);
	}

}
