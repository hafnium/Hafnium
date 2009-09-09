package com.sumerit.hafnium.products;

import com.sumerit.hafnium.components.LightingComponent;
import com.sumerit.hafnium.devices.LightingController;

public class GEMaxLight2000 extends LightingComponent 
{

	public class GEMaxLight2000Controller implements LightingController
	{
		public void powerOff() 
		{
			
		}

		public void powerOn() 
		{
			
		}
		
	}
	
	public GEMaxLight2000()
	{
		this(0);
	}
	
	public GEMaxLight2000(int serialNumber)
	{
		super(serialNumber, "GE MaxLight", "2000");
	}

	public void initialize(int serialNumber) 
	{		
		this.serialNumber = serialNumber;
		this.make = "GE MaxLight";
		this.model = "2000";
	}

	protected void initializeController() 
	{
		if (this.controller == null)
			this.controller = new GEMaxLight2000Controller();
	}
	
}
