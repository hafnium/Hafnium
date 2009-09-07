package com.sumerit.hafnium;

import java.util.Vector;

import com.sumerit.hafnium.components.ClimateComponent;
import com.sumerit.hafnium.components.HomeComponent;
import com.sumerit.hafnium.util.HafniumFileLoader;

public class Home 
{	
	private Vector<HomeComponent> components;
	private Owner owner;
	private ClimateComponent temperatureComponent;
		
	private Location location;
	
	public Home(){}
	
	public Home(String path)
	{
		this.owner = HafniumFileLoader.loadOwner(path);
		this.location = HafniumFileLoader.loadLocation(path);
		this.components = HafniumFileLoader.loadComponents(path);
		
		for (int i = 0; i < components.size(); i++)
		{
			if (ClimateComponent.class.isAssignableFrom(components.get(i).getClass()))
				this.temperatureComponent = (ClimateComponent) components.get(i);
		}
	}
	
	public ClimateComponent getTemperatureComponent()
	{
		return this.temperatureComponent;
	}
	
	public void setComponents(Vector<HomeComponent> components)
	{
		this.components = components;
	}
	
	public Vector<HomeComponent> getComponents()
	{
		return this.components;
	}
	
	public Location getLocation()
	{
		return this.location;
	}

	public Owner getOwner() 
	{
		return owner;
	}
	
	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
