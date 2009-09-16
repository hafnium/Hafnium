package com.sumerit.hafnium.components;

import com.sumerit.hafnium.devices.ClimateController;

/**
 * Class that all air conditioners should extend. Provides functionality to set the temperature.
 * @author Sean Arietta
 *
 */
public abstract class AirConditioner extends ClimateComponent 
{
	private enum CondensorState {OPERATIONAL, PARTIALLY_FROZEN, FROZEN};
	private CondensorState condensorState;
	
	public AirConditioner()
	{
		super();
	}
	
	/**
	 * Create an AC device with the provided serial number, make, and model
	 * @param serialNumber
	 * @param make
	 * @param model
	 */
	public AirConditioner(int serialNumber, String make, String model) 
	{
		super(serialNumber, make, model);
	}
	
	public boolean testAdjustTemperature(float targetTemperature)
	{			
		if (targetTemperature >= this.getAmbientTemperature())
		{
			this.resetTemperatureSetting();
			this.setStatusMessage("Target temperature is greater than or equal to current temperature", HomeComponent.LogLevel.WARNING);
			return false;
		}
		
		if (condensorState == CondensorState.FROZEN)
		{
			this.resetTemperatureSetting();
			this.setStatusMessage("Condensor coil is frozen", HomeComponent.LogLevel.SEVERE);
			return false;
		}
		
		if (condensorState == CondensorState.PARTIALLY_FROZEN)
			this.setStatusMessage("Condensor coil is partially frozen", HomeComponent.LogLevel.WARNING);
		
		return true;
	}	

	public CondensorState getCondensorState()
	{
		return condensorState;
	}
}
