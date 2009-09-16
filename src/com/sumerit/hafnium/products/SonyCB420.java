package com.sumerit.hafnium.products;

import com.sumerit.hafnium.components.SecurityCamera;
import com.sumerit.hafnium.devices.SecurityCameraController;

public class SonyCB420 extends SecurityCamera
{
	public class SonyCB420Controller implements SecurityCameraController
	{	
		public void powerOff() {}
		public void powerOn() {}
		
		public void powerOnNightVision( ) { }
		public void powerOffNightVision( ) { }
	}
	
	protected void initializeController()
	{
		if (this.controller == null)
			this.controller = new SonyCB420Controller();
	}
	
	public SonyCB420() 
	{
		this(0);
	}
	
	public SonyCB420(int serialNumber)
	{
		super(serialNumber, "Sony", "CB420");
	}
	
	public void initialize(int serialNumber)
	{
		this.serialNumber = serialNumber;
		this.make = "Sony";
		this.model = "CB420";
	}
}
