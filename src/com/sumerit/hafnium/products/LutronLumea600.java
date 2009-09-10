package com.sumerit.hafnium.products;

import com.sumerit.hafnium.components.DimmerComponent;
import com.sumerit.hafnium.devices.LightingController;

public class LutronLumea600 extends DimmerComponent
{
	public class LutronLumea600Controller implements LightingController
	{
		public void powerOff() 
		{
			
		}

		public void powerOn() 
		{
			
		}

		public void interruptController() {
		}
	}
	
	public LutronLumea600()
	{
		this(0);
	}
	
	public LutronLumea600(int serialNumber)
	{
		super(serialNumber, "Lutron", "Lumea 600");
	}
	
	public void initialize(int serialNumber) 
	{		
		this.serialNumber = serialNumber;
		this.make = "Lutron";
		this.model = "Lumea 600";
	}
	
	protected void initializeController() 
	{
		if (this.controller == null)
			this.controller = new LutronLumea600Controller();
	}
}
