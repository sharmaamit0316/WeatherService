package com.weather.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.weather.controller.WeatherController;
import com.weather.model.WeatherDetail;
import com.weather.service.WeatherService;


@RunWith(MockitoJUnitRunner.class)
public class WeatherControllerTest {
	
	@Mock
    private WeatherService service;
	
	@InjectMocks
	private WeatherController controller=new WeatherController();
	
	@Test
	public void getWeatherDataTest() {
		Mockito.when(service.getWeatherInfo(Mockito.anyString())).thenReturn(new WeatherDetail());
		WeatherDetail detail=controller.getWeatherData("LONDON");
		Assert.assertNotNull(detail);
	}
	
	@Test
	public void getWeatherDataTest_Empty() {
		Mockito.when(service.getWeatherInfo(Mockito.anyString())).thenReturn(null);
		WeatherDetail detail=controller.getWeatherData("LONDON");
		Assert.assertNull(detail);
	}

}
