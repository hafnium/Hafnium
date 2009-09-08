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
	
	/**
	 * Set the temperature. This makes a call to the superclass {@link ClimateComponent} only after 
	 * checking this device for malfunction.
	 * 
	 *  @throws RuntimeException if the AC's condensor is frozen
	 */
	public void adjustTemperature(float targetTemperature)
	{
		if (!this.isPoweredOn)
		{
			this.resetTemperatureSetting();	
			throw new RuntimeException("Device is off");
		}
		
		if (targetTemperature >= this.getAmbientTemperature())
		{
			this.resetTemperatureSetting();
			this.setStatusMessage("Target temperature is greater than or equal to current temperature", HomeComponent.LogLevel.WARNING);
			return;
		}
		
		if (condensorState == CondensorState.FROZEN)
		{
			this.resetTemperatureSetting();
			this.setStatusMessage("Condensor coil is frozen", HomeComponent.LogLevel.SEVERE);
			throw new RuntimeException("Condensor coil is frozen");
		}
		
		if (condensorState == CondensorState.PARTIALLY_FROZEN)
			this.setStatusMessage("Condensor coil is partially frozen", HomeComponent.LogLevel.WARNING);
		
		((ClimateController) this.controller).lowerTemperature(targetTemperature);		
	}	

	public CondensorState getCondensorState()
	{
		return condensorState;
	}
}
