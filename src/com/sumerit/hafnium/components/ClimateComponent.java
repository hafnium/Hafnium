package com.sumerit.hafnium.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sumerit.hafnium.devices.InternalTemperatureDevice;

/**
 * Superclass to all climate controlling devices. 
 * @author Sean Arietta
 *
 */
public abstract class ClimateComponent extends HomeComponent implements ActionListener
{		
	private float temperatureSetting;
	private float ambientTemperature;
	private ActionListener temperatureListener = null;
	InternalTemperatureDevice temperatureController;
	
	public ClimateComponent(){};
	
	/**
	 * Creates a climate device with a given serial number, make, and model.
	 * @param serialNumber
	 * @param make
	 * @param model
	 */
	public ClimateComponent(int serialNumber, String make, String model) 
	{
		super(serialNumber, make, model);	
		
		ambientTemperature = 70.0f;
		temperatureController = new InternalTemperatureDevice();
		temperatureController.addActionListener(this);
	}
	
	/**
	 * Method to adjust the temperature of this climate device. Makes a call to a local {@link InternalTemperatureDevice} 
	 * to actually adjust the temperature. 
	 * @param targetTemperature the temperature to set this device to
	 * @throws InterruptedException
	 * @throws RuntimeException if the device is not powered on
	 */
	public void setTemperature(float targetTemperature) throws InterruptedException, RuntimeException
	{	
		if (!this.isPoweredOn())
		{
			this.setStatusMessage("Attempted to adjust temperature while device was off", HomeComponent.LogLevel.WARNING);
			throw new RuntimeException("Device has been turned off");
		}
		
		temperatureController.adjustTemperature(ambientTemperature, targetTemperature);
	}
	
	public float getTemperatureSetting()
	{
		return this.temperatureSetting;
	}
	
	public float getAmbientTemperature()
	{
		return this.ambientTemperature;
	}
	
	/**
	 * Receives events that the {@link InternalTemperatureDevice} sends to it. Will react to a temperature 
	 * change and a temperature set event.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getID() == InternalTemperatureDevice.TEMPERATURE_SET)
		{
			this.temperatureSetting = Float.parseFloat(e.getActionCommand());
			this.setStatusMessage("Temperature set to " + this.getTemperatureSetting(), HomeComponent.LogLevel.INFO);
			
			if (temperatureListener != null)
				this.temperatureListener.actionPerformed(e);
		} else if (e.getID() == InternalTemperatureDevice.TEMPERATURE_CHANGE)
		{
			this.ambientTemperature = Float.parseFloat(e.getActionCommand());
			this.setStatusMessage("Temperature changed to " + this.getAmbientTemperature(), HomeComponent.LogLevel.INFO);
		}
	}
	
	/**
	 * Add a listener that wants to be notified when the temperature is finally set to the desired temperature
	 * @param listener
	 */
	public void addChangeTemperatureListener(ActionListener listener)
	{
		this.temperatureListener = listener;
	}
}
