package com.weather.util;

import java.text.DecimalFormat;

/**
 * 
 * @author Amit.Sharma88
 * Utility class comprising of all commonly used functionalities
 */
public class Utility {
	/**
	 * Used to convert Celsius to Fahrenheit
	 * @param cels
	 * @return
	 */
	public static double convertCeltoFahr(double cels) {
		double fahr=(cels*9/5)+32;
		DecimalFormat numberFormat = new DecimalFormat(WeatherConstants.DECIMAL_LIMIT);
		return Double.valueOf(numberFormat.format(fahr));
	}

}
