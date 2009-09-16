package com.sumerit.hafnium.products;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;

import com.sumerit.hafnium.components.DimmerComponent;
import com.sumerit.hafnium.devices.LightingController;
import com.sumerit.hafnium.util.ImageLoader;

public class LutronLumea600 extends DimmerComponent
{
	public class LutronLumea600Controller implements LightingController
	{
		public void powerOff() 
		{
			dimmerSlider.setSelection(0);
			Event fakeDimmer = new Event();
			fakeDimmer.widget = dimmerSlider;
			dimmerSliderMouseUp(new MouseEvent(fakeDimmer));
			icon.setImage(ImageLoader.load(componentContainer.getDisplay(), "resources/componentIcons/"+(make+model).replace(" ", "")+".png"));
		}

		public void powerOn() 
		{
			dimmerSlider.setSelection(100);
			Event fakeDimmer = new Event();
			fakeDimmer.widget = dimmerSlider;
			dimmerSliderMouseUp(new MouseEvent(fakeDimmer));
			
			icon.setImage(ImageLoader.load(componentContainer.getDisplay(), "resources/componentIcons/"+(make+model).replace(" ", "")+".on.png"));
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
