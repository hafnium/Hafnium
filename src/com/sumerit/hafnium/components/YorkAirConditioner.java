package com.sumerit.hafnium.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.sumerit.hafnium.TestUI;
import com.sumerit.hafnium.util.ImageLoader;


public class YorkAirConditioner extends AirConditioner {
	
	public YorkAirConditioner()
	{
		super();
	}
	
	public YorkAirConditioner(int serialNumber, String make, String model) 
	{
		super(serialNumber, "York " + make, model);
	}

	public void powerOff() 
	{
		// Make call to actual hardware
		this.setStatusMessage(this.getMake() + " " + this.getModel() + " AC powering off", HomeComponent.LogLevel.INFO);
		this.isPoweredOn = false;
	}

	public void powerOn() 
	{
		// Make call to actual hardware
		this.setStatusMessage(this.getMake() + " " + this.getModel() + " AC powering on", HomeComponent.LogLevel.INFO);
		this.isPoweredOn = true;
	}

	@Override
	public void setIcon() 
	{
		iconLabel = new Label(mainContentContainer, SWT.NONE);
		iconLabel.setImage(ImageLoader.load(HomeComponent.display, "resources/componentIcons/YorkES354.png"));
	}	

}
