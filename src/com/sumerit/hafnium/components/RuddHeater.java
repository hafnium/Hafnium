package com.sumerit.hafnium.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.sumerit.hafnium.util.ImageLoader;

public class RuddHeater extends Heater 
{
	public RuddHeater(int serialNumber, String model) 
	{
		super(new Shell(Display.getDefault( ), SWT.NO_TRIM), SWT.NULL, serialNumber, "Rudd", model);
	}
	
	public void powerOff() 
	{
		// Make call to actual hardware
		this.setStatusMessage(this.getMake() + " " + this.getModel() + " Heater powering off", HomeComponent.LogLevel.INFO);
		this.isPoweredOn = false;
	}

	public void powerOn() 
	{
		// Make call to actual hardware
		this.setStatusMessage(this.getMake() + " " + this.getModel() + " Heater powering on", HomeComponent.LogLevel.INFO);
		this.isPoweredOn = true;
	}

	@Override
	public void setIcon() 
	{
		iconLabel = new Label(mainContentContainer, SWT.NONE);
		iconLabel.setImage(ImageLoader.load(this.getDisplay(), "resources/componentIcons/YorkES354.png"));
	}	

}
