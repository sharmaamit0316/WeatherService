package com.weather.test;

import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.weather.exception.WeatherException;
import com.weather.model.WeatherDetail;
import com.weather.service.WeatherService;


@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {
	
	@Mock
    private RestTemplate restTemplate;
	
	@InjectMocks
	private WeatherService service=new WeatherService();
	
	@Test
	public void getWeatherInfoTestSuccess() {
		JSONObject json = dataProviderSuccess();
		ResponseEntity<String> entity=new ResponseEntity<String>(json.toString(),HttpStatus.OK);
		Mockito.when(restTemplate.exchange(Mockito.any(URI.class),Mockito.eq(HttpMethod.GET),Mockito.any(HttpEntity.class), Mockito.eq(String.class))).thenReturn(entity);
		WeatherDetail detail=service.getWeatherInfo("LONDON");
		Assert.assertEquals("LONDON", detail.getCity());
	}
	
	@Test(expected=WeatherException.class)
	public void getWeatherInfoTestFail() {
		Mockito.when(restTemplate.exchange(Mockito.any(URI.class),Mockito.eq(HttpMethod.GET),Mockito.any(HttpEntity.class), Mockito.eq(String.class))).thenThrow(RestClientException.class);
		service.getWeatherInfo("LONDON");
	}
	
	@Test(expected=WeatherException.class)
	public void getWeatherInfoTestFail1() {
		Mockito.when(restTemplate.exchange(Mockito.any(URI.class),Mockito.eq(HttpMethod.GET),Mockito.any(HttpEntity.class), Mockito.eq(String.class))).thenThrow(RuntimeException.class);
		service.getWeatherInfo("LONDON");
	}
	
	@Test(expected=WeatherException.class)
	public void getWeatherInfoTestParse() {
		JSONObject json = dataProviderParse();
		ResponseEntity<String> entity=new ResponseEntity<String>(json.toString(),HttpStatus.OK);
		Mockito.when(restTemplate.exchange(Mockito.any(URI.class),Mockito.eq(HttpMethod.GET),Mockito.any(HttpEntity.class), Mockito.eq(String.class))).thenReturn(entity);
		service.getWeatherInfo("LONDON");
	}

	/**
	 * @return JSONObject
	 */
	private JSONObject dataProviderSuccess() {
		JSONObject json=new JSONObject();
		JSONObject city=new JSONObject();
		city.put("name","LONDON");
		city.put("timezone",3600);
		city.put("sunrise",1568785175l);
		city.put("sunset",1568830212l);
		json.put("city",city);
		JSONArray array=new JSONArray();
		JSONObject list=new JSONObject();
		JSONObject main=new JSONObject();
		main.put("temp", 15.97);
		list.put("main", main);
		JSONArray weather=new JSONArray();
		JSONObject jsonWeather=new JSONObject();
		jsonWeather.put("description", "clear sky");
		weather.put(jsonWeather);
		JSONObject listJson=new JSONObject();
		listJson.put("main", main);
		listJson.put("weather", weather);
		json.put("list", array);
		array.put(listJson);
		return json;
	}
	
	/**
	 * @return JSONObject
	 */
	private JSONObject dataProviderParse() {
		JSONObject json=new JSONObject();
		JSONObject city=new JSONObject();
		city.put("name","LONDON");
		city.put("sunrise",1568785175l);
		city.put("sunset",1568830212l);
		json.put("city",city);
		JSONArray array=new JSONArray();
		JSONObject list=new JSONObject();
		JSONObject main=new JSONObject();
		main.put("temp", 15.97);
		list.put("main", main);
		JSONArray weather=new JSONArray();
		JSONObject jsonWeather=new JSONObject();
		jsonWeather.put("description", "clear sky");
		weather.put(jsonWeather);
		JSONObject listJson=new JSONObject();
		listJson.put("main", main);
		listJson.put("weather", weather);
		json.put("list", array);
		array.put(listJson);
		return json;
	}
}
