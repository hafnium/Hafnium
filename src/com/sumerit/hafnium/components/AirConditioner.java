package com.sumerit.hafnium.components;

import org.eclipse.swt.widgets.Composite;

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
	public void setTemperature(float targetTemperature) throws InterruptedException, RuntimeException 
	{
		if (targetTemperature >= this.getAmbientTemperature())
		{
			this.setStatusMessage("Target temperature is greater than or equal to current temperature", HomeComponent.LogLevel.WARNING);
			return;
		}
		
		if (condensorState == CondensorState.FROZEN)
		{
			this.setStatusMessage("Condensor coil is frozen", HomeComponent.LogLevel.SEVERE);
			throw new RuntimeException("Condensor coil is frozen");
		}
		
		if (condensorState == CondensorState.PARTIALLY_FROZEN)
			this.setStatusMessage("Condensor coil is partially frozen", HomeComponent.LogLevel.WARNING);
		
		super.setTemperature(targetTemperature);			
	}	

	public CondensorState getCondensorState()
	{
		return condensorState;
	}
}
