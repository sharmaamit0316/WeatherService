package com.weather.model;

import java.time.LocalDate;

public class WeatherDetail {

	private String city;
	private String weatherDesc;
	private double tempFahr;
	private double tempCel;
	private LocalDate today;
	private String sunrise;
	private String sunset;

	public double getTempFahr() {
		return tempFahr;
	}

	public void setTempFahr(double tempFahr) {
		this.tempFahr = tempFahr;
	}

	public double getTempCel() {
		return tempCel;
	}

	public void setTempCel(double tempCel) {
		this.tempCel = tempCel;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getWeatherDesc() {
		return weatherDesc;
	}

	public void setWeatherDesc(String weatherDesc) {
		this.weatherDesc = weatherDesc;
	}
	
	public void setToday(LocalDate today) {
		this.today = today;
	}


	public LocalDate getToday() {
		return today;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}
	
	@Override
	public String toString() {
		return "WeatherDetail [city=" + city + ", weatherDesc=" + weatherDesc + ", tempFahr=" + tempFahr + ", tempCel="
				+ tempCel + ", today=" + today + ", sunrise=" + sunrise + ", sunset=" + sunset + "]";
	}

}
