package com.sumerit.hafnium.components;

import com.sumerit.hafnium.devices.ClimateController;

public abstract class Heater extends ClimateComponent 
{
	private enum ExchangerState {OPERATIONAL, OVERHEATED};
	private ExchangerState exchangerState;
	
	public Heater()
	{
		super();
	}
	
	/**
	 * Create a heater device with the provided serial number, make, and model
	 * @param serialNumber
	 * @param make
	 * @param model
	 */
	public Heater(int serialNumber, String make, String model) 
	{
		super(serialNumber, make, model);
	}
	
	/**
	 * Set the temperature. This makes a call to the superclass {@link ClimateComponent} only after 
	 * checking this device for malfunction.
	 * 
	 *  @throws RuntimeException if the heater's exchanger is frozen
	 */
	public void adjustTemperature(float targetTemperature)
	{
		if (targetTemperature >= this.getAmbientTemperature())
		{
			this.setStatusMessage("Target temperature is greater than or equal to current temperature", HomeComponent.LogLevel.WARNING);
			return;
		}
		
		if (exchangerState == ExchangerState.OVERHEATED)
		{
			this.setStatusMessage("Condensor coil is frozen", HomeComponent.LogLevel.SEVERE);
			throw new RuntimeException("Condensor coil is frozen");
		}
		
		((ClimateController) this.controller).raiseTemperature(targetTemperature);		
	}
}