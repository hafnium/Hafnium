package com.sumerit.hafnium.components;

public class RuddHeater extends Heater 
{
	public RuddHeater(int serialNumber, String model) 
	{
		super(serialNumber, "Rudd", model);
	}
	
	public void powerOff() 
	{
		// Make call to actual hardware
		this.setStatusMessage(this.getMake() + " " + this.getModel() + " Heater powering off", HomeComponent.LogLevel.INFO);
		this.isPoweredOn = false;
	}

	public void powerOn() 
	{
		// Make call to actual hardware
		this.setStatusMessage(this.getMake() + " " + this.getModel() + " Heater powering on", HomeComponent.LogLevel.INFO);
		this.isPoweredOn = true;
	}	

}
