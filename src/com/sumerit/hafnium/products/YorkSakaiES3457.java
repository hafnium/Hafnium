package com.sumerit.hafnium.products;

import com.sumerit.hafnium.components.AirConditioner;
import com.sumerit.hafnium.devices.ClimateController;
import com.sumerit.hafnium.devices.TemperatureSampler;
import com.sumerit.hafnium.simulator.Environment;

public class YorkSakaiES3457 extends AirConditioner 
{
	public class YorkSakaiES3457Sampler implements TemperatureSampler
	{
		/**
		 * This method should make a call directly to hardware
		 */
		public float sampleAmbientTemperature() 
		{
			return Environment.getAmbientTemperature();
		}
		
	}
	
	public class YorkSakaiES3457Controller implements ClimateController
	{	
		
		public void lowerTemperature(final float targetTemperature) 
		{			
			while (temperatureSampler.sampleAmbientTemperature() > targetTemperature)
			{
				Environment.lowerTemperature();
			}
		}

		public void raiseTemperature(float targetTemperature) {}

		public void powerOff() {}

		public void powerOn() {}

		public void interruptController() {}
		
	}
	
	protected void initializeController()
	{
		if (this.controller == null)
			this.controller = new YorkSakaiES3457Controller();
	}
	
	protected void initializeSampler()
	{
		if (this.temperatureSampler == null)
			this.temperatureSampler = new YorkSakaiES3457Sampler();	
	}
	
	public YorkSakaiES3457() 
	{
		this(0);
	}
	
	public YorkSakaiES3457(int serialNumber)
	{
		super(serialNumber, "York Sakai", "ES3457");
	}
	
	public void initialize(int serialNumber)
	{
		this.serialNumber = serialNumber;
		this.make = "York Sakai";
		this.model = "ES3457";
	}
}
