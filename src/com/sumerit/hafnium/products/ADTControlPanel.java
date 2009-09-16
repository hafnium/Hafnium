package com.sumerit.hafnium.products;

import com.sumerit.hafnium.components.SecurityComponent;
import com.sumerit.hafnium.devices.SecurityAlarmController;

public class ADTControlPanel extends SecurityComponent
{
	public class ADTControlPanelController implements SecurityAlarmController
	{	
		public void powerOff() {}
		public void powerOn() {}
		
		public void reset() 
		{
			
		}
		
		public void trip() 
		{
			
		}
	}
	
	protected void initializeController()
	{
		if (this.controller == null)
			this.controller = new ADTControlPanelController();
	}
	
	public ADTControlPanel() 
	{
		this(0);
	}
	
	public ADTControlPanel(int serialNumber)
	{
		super(serialNumber, "ADT", "Control Panel");
	}
	
	public void initialize(int serialNumber)
	{
		this.serialNumber = serialNumber;
		this.make = "ADT";
		this.model = "Control Panel";
	}
}
