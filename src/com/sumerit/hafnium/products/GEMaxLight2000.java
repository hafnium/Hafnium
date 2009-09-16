package com.sumerit.hafnium.products;

import com.sumerit.hafnium.components.LightingComponent;
import com.sumerit.hafnium.devices.LightingController;
import com.sumerit.hafnium.util.ImageLoader;

public class GEMaxLight2000 extends LightingComponent 
{

	public class GEMaxLight2000Controller implements LightingController
	{
		public void powerOff() 
		{
			icon.setImage(ImageLoader.load(componentContainer.getDisplay(), "resources/componentIcons/"+(make+model).replace(" ", "")+".png"));
		}

		public void powerOn() 
		{
			icon.setImage(ImageLoader.load(componentContainer.getDisplay(), "resources/componentIcons/"+(make+model).replace(" ", "")+".on.png"));
		}

		public void interruptController() {
			
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
