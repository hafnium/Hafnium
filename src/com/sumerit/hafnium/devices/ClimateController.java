package com.sumerit.hafnium.devices;

public interface ClimateController extends Controller 
{
	public void raiseTemperature(float targetTemperature);
	public void lowerTemperature(float targetTemperature);
}
