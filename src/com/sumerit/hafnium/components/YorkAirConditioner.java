package com.sumerit.hafnium.components;


public class YorkAirConditioner extends AirConditioner {

	public YorkAirConditioner(){}
	
	public YorkAirConditioner(int serialNumber, String model) 
	{
		super(serialNumber, "York", model);
	}

	public void powerOff() 
	{
		// Make call to actual hardware
		this.setStatusMessage(this.getMake() + " " + this.getModel() + " AC powering off", HomeComponent.LogLevel.INFO);
		this.isPoweredOn = false;
	}

	public void powerOn() 
	{
		// Make call to actual hardware
		this.setStatusMessage(this.getMake() + " " + this.getModel() + " AC powering on", HomeComponent.LogLevel.INFO);
		this.isPoweredOn = true;
	}	

}
