package com.sumerit.hafnium.components;

public abstract class SecurityComponent extends HomeComponent
{		
	public SecurityComponent()
	{
		this(0,"","");
	}
	
	public SecurityComponent(int serialNumber, String make, String model) 
	{		
		super(serialNumber, make, model);
	}
}
