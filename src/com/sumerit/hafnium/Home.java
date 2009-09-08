package com.sumerit.hafnium;

import java.util.HashMap;
import java.util.Vector;

import com.sumerit.hafnium.components.ClimateComponent;
import com.sumerit.hafnium.components.HomeComponent;
import com.sumerit.hafnium.util.HafniumFileLoader;

public class Home 
{	
	//private Vector<HomeComponent> components;
	private HashMap<String, Vector<HomeComponent>> components;
	private Owner owner;
		
	private Location location;
	
	public Home(){}
	
	public Home(String path)
	{
		this.owner = HafniumFileLoader.loadOwner(path);
		this.location = HafniumFileLoader.loadLocation(path);
		this.components = HafniumFileLoader.loadComponents(path);		
	}
	
	public HashMap<String, Vector<HomeComponent>> getComponents()
	{
		return this.components;
	}
	
	public Vector<HomeComponent> getComponents(String key)
	{
		return this.components.get(key);
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
