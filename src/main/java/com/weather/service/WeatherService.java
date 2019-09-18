package com.weather.service;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.weather.exception.WeatherException;
import com.weather.model.WeatherDetail;
import com.weather.util.Utility;
import com.weather.util.WeatherConstants;

@Service
public class WeatherService {

	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Service method to fetch all weather related information from OpenWeatherMap
	 * API and populating the model class with appropriate fields
	 * 
	 * @param city
	 * @return
	 */
	public WeatherDetail getWeatherInfo(String city) {
		logger.info("Entering getWeatherInfo Service with city: {}", city);
		WeatherDetail weatherDetail = new WeatherDetail();
		ResponseEntity<String> weatherResponse;
		HttpHeaders headers = new HttpHeaders();
		headers.set(WeatherConstants.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		URI uri = createUriBuilder(city);
		logger.debug("Generated URI: {}", uri);
		try {
			logger.info("Invoking OpenWeatherMap API with city: {}", city);
			weatherResponse = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);
			logger.info("OpenWeatherMap API returned with status : {}", weatherResponse.getStatusCode());
			populateWeatherDetails(weatherDetail, weatherResponse);
		} catch (RestClientException e) {
			logger.error(WeatherConstants.EXTERNAL_ERROR_MSG, e);
			throw new WeatherException(WeatherConstants.EXTERNAL_ERROR_MSG, HttpStatus.SERVICE_UNAVAILABLE);
		}catch(JSONException e) {
			logger.error(WeatherConstants.JSON_PARSE_MSG, e);
			throw new WeatherException(WeatherConstants.JSON_PARSE_MSG);
		}
		catch (Exception e) {
			logger.error(WeatherConstants.ERROR_MSG, e);
			throw new WeatherException(WeatherConstants.ERROR_MSG);
		}
		logger.info("Exiting getWeatherInfo Service with weatherDetail: {}", weatherDetail);
		return weatherDetail;
	}

	/**
	 * Generate URI to invoke OpenWeatherMap API
	 * @param city
	 * @return
	 */
	private URI createUriBuilder(String city) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(WeatherConstants.WEATHER_OPEN_API);
		uriBuilder.queryParam(WeatherConstants.CITY, city);
		uriBuilder.queryParam(WeatherConstants.UNITS, WeatherConstants.METRIC);
		uriBuilder.queryParam(WeatherConstants.APPID, WeatherConstants.KEY);
		uriBuilder.queryParam(WeatherConstants.CNT, WeatherConstants.ONE);
		return uriBuilder.build().encode().toUri();
	}

	/**
	 * Populate WeatherDetail based on OpenWeatherMap API response
	 * 
	 * @param weatherDetail
	 * @param weatherResponse
	 */
	private void populateWeatherDetails(WeatherDetail weatherDetail, ResponseEntity<String> weatherResponse) {
		JSONObject jsonObject = new JSONObject(weatherResponse.getBody());
		JSONObject city = jsonObject.getJSONObject("city");
		JSONObject list = jsonObject.getJSONArray("list").getJSONObject(0);
		weatherDetail.setCity(city.getString("name"));
		weatherDetail.setWeatherDesc(list.getJSONArray("weather").getJSONObject(0).getString("description"));
		weatherDetail.setTempCel(list.getJSONObject("main").getDouble("temp"));
		weatherDetail.setTempFahr(Utility.convertCeltoFahr(weatherDetail.getTempCel()));
		long offset = city.getLong("timezone");
		long sunrise = city.getLong("sunrise");
		long sunset = city.getLong("sunset");
		Instant sunriseInstant = Instant.ofEpochSecond(sunrise + offset);
		Instant sunsetInstant = Instant.ofEpochSecond(sunset + offset);
		LocalDateTime date = sunriseInstant.atZone(ZoneOffset.UTC).toLocalDateTime();
		weatherDetail.setToday(date.toLocalDate());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(WeatherConstants.TIMEFORMAT);
		weatherDetail.setSunrise(date.toLocalTime().format(formatter));
		String sunsetTime = sunsetInstant.atZone(ZoneOffset.UTC).toLocalTime().format(formatter);
		weatherDetail.setSunset(sunsetTime);
	}

}
