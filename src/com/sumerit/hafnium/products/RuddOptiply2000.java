package com.sumerit.hafnium.products;

import com.sumerit.hafnium.components.Heater;
import com.sumerit.hafnium.components.HomeComponent;
import com.sumerit.hafnium.devices.ClimateController;
import com.sumerit.hafnium.devices.TemperatureSampler;
import com.sumerit.hafnium.simulator.Environment;

public class RuddOptiply2000 extends Heater 
{
	public class RuddOptiply2000Sampler implements TemperatureSampler
	{
		/**
		 * This method should make a call directly to hardware
		 */
		public float sampleAmbientTemperature() 
		{
			return Environment.getAmbientTemperature();
		}
		
	}
	
	public class RuddOptiply2000Controller implements ClimateController
	{
		public void lowerTemperature(float targetTemperature){}

		public void raiseTemperature(float targetTemperature) 
		{
			while (temperatureSampler.sampleAmbientTemperature() != targetTemperature)
				Environment.raiseTemperature();
		}

		public void powerOff() {}

		public void powerOn() {}
		
	}
	
	public RuddOptiply2000() 
	{
		this.temperatureSampler = new RuddOptiply2000Sampler();
	}
	
	public RuddOptiply2000(int serialNumber)
	{
		super(serialNumber, "Rudd Optiply", "2000");
		this.temperatureSampler = new RuddOptiply2000Sampler();
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

	public void initialize(int serialNumber) 
	{
		this.serialNumber = serialNumber;
		this.make = "Rudd Optiply";
		this.model = "2000";
	}
}
