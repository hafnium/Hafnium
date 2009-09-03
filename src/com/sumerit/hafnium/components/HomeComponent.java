package com.sumerit.hafnium.components;

import java.util.logging.Logger;

/**
 * Main class that all home automation components should extend. Includes barebones 
 * functionality such as the ability to turn the device on and off and retrieve the make
 * and model of the device. Also includes a logging function that all subclasses should use
 * to communicate with users.
 * 
 * @author Sean Arietta
 * 
 */
public abstract class HomeComponent 
{
	public static enum LogLevel {SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST };
	
	private static Logger statusLogger = Logger.getLogger(HomeComponent.class.getName());	
	
	protected boolean isPoweredOn;
	protected int inputVoltage;
	protected enum inputType {AC, DC};
	protected boolean isGrounded;
	private String statusMessage;
	
	private int serialNumber;
	private String make;
	private String model;	
	
	public HomeComponent(){};
	
	/**
	 * 
	 * @param serialNumber
	 * @param make
	 * @param model
	 */
	public HomeComponent(int serialNumber, String make, String model)
	{
		this.serialNumber = serialNumber;
		this.make = make;
		this.model = model;
	}
	
	public void initialize(int serialNumber, String make, String model)
	{
		this.serialNumber = serialNumber;
		this.make = make;
		this.model = model;
	}
	
	/**
	 * Check to see if device is powered on
	 * @return
	 */
	public boolean isPoweredOn()
	{
		return isPoweredOn;
	}
	
	/**
	 * Get the make of device
	 * @return
	 */
	public String getMake()
	{
		return make;
	}
	
	/**
	 * Get the model of the device
	 * @return
	 */
	public String getModel()
	{
		return model;
	}
	
	/**
	 * Get the serial number of the device
	 * @return
	 */
	public int getSerialNumber()
	{
		return serialNumber;
	}
	
	/**
	 * Get the last status message that was set
	 * @return
	 */
	public String getStatusMessage()
	{
		return this.statusMessage;
	}
	
	/**
	 * Function to set the status message of this device. Makes a call to the internal {@link Logger}
	 * @param message a {@link String} containing the message
	 * @param level the level of the message. 
	 * @see Logger
	 */
	public void setStatusMessage(String message, LogLevel level)
	{
		String pre = "";
		switch(level)
		{
			case SEVERE:
				pre = "SEVERE";
				statusLogger.severe(message);
				break;
			case WARNING:
				pre = "WARNING";
				statusLogger.warning(message);
				break;
			case INFO:
				pre = "INFO";
				statusLogger.info(message);
				break;
			case FINE:
				pre = "FINE";
				statusLogger.fine(message);
				break;
			case FINER:
				pre = "FINER";
				statusLogger.finer(message);
				break;
			case FINEST:
				pre = "FINEST";
				statusLogger.finest(message);
				break;
		}
		this.statusMessage = pre + ": " + message;
	}
	
	/**
	 * Turn the device on. This should be overridden by the leaf classes in the hierarchy as 
	 * they will need to make calls to the actual hardware.
	 */
	public abstract void powerOn();
	
	/**
	 * Turn the device off. This should be overridden by the leaf classes in the hierarchy as 
	 * they will need to make calls to the actual hardware.
	 */
	public abstract void powerOff();	
	
}
