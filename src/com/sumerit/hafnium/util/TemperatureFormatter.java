package com.sumerit.hafnium.util;

public class TemperatureFormatter 
{
	public enum TemperatureScale {Fahrenheit, Celsius};
	
	public static String getScale(TemperatureScale s)
	{
		if (s == TemperatureScale.Fahrenheit)
			return "F";
		if (s == TemperatureScale.Celsius)
			return "C";
		
		return "F";
	}
	
	public static String format(float temperature, TemperatureScale scale)
	{
		return "" + Math.round(temperature) + "." + (Math.round(temperature*10)%10) + TemperatureFormatter.getScale(scale);
	}
}
