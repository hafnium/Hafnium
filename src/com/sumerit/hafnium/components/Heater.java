package com.sumerit.hafnium.components;

public abstract class Heater extends ClimateComponent 
{
	private enum ExchangerState {OPERATIONAL, OVERHEATED};
	private ExchangerState exchangerState;
	
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
	public void setTemperature(float targetTemperature) throws InterruptedException, RuntimeException 
	{
		if (targetTemperature <= this.getAmbientTemperature())
		{
			this.setStatusMessage("Target temperature is less than or equal to current temperature", HomeComponent.LogLevel.WARNING);
			return;
		}
		
		if (exchangerState == ExchangerState.OVERHEATED)
		{
			this.setStatusMessage("Heat exchanger is overheated", HomeComponent.LogLevel.SEVERE);
			throw new RuntimeException("Heat exchanger is overheated");
		}
		
		super.setTemperature(targetTemperature);			
	}	

}