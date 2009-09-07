package com.sumerit.hafnium.simulator;

import java.sql.Time;

public class Environment 
{
	private static float ambientTemperature = 70.0f;
	private static Time timeofDay;
	private static float soundLevel;
	
	public static float getAmbientTemperature()
	{
		return ambientTemperature;
	}
	
	public static void raiseTemperature()
	{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ambientTemperature += 1.0f;
	}
	
	public static void lowerTemperature()
	{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ambientTemperature -= 1.0f;
	}
}
