package com.sumerit.hafnium.components;

import com.sumerit.hafnium.devices.LightingController;

public abstract class LightingComponent extends HomeComponent
{
	
	public LightingComponent( )
	{
		super();		
	}	
	
	/**
	 * Creates a climate device with a given serial number, make, and model.
	 * @param serialNumber
	 * @param make
	 * @param model
	 */
	public LightingComponent(int serialNumber, String make, String model) 
	{
		super(serialNumber, make, model);
	}
}
