package com.sumerit.hafnium;

import java.util.Vector;

import com.sumerit.hafnium.components.HomeComponent;

public class Home 
{	
	private Vector<HomeComponent> components;
	private Owner owner;
		
	private Location location;
	
	public Home(){}
	
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
