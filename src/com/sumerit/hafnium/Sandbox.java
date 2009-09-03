package com.sumerit.hafnium;

import java.util.Vector;

import com.sumerit.hafnium.components.HomeComponent;
import com.sumerit.hafnium.util.HafniumFileLoader;

public class Sandbox 
{
	public static void main(String[] args)
	{
		Owner owner = HafniumFileLoader.loadOwner("myHome.xml");
		Location location = HafniumFileLoader.loadLocation("myHome.xml");
		
		System.out.println("This home is owned by " + owner.getName() + " (" + owner.getId() + ")");
		System.out.println("This home is located in " + location.getCity() + ", " + location.getState() + " " + location.getZipcode());
		
		Vector<HomeComponent> components = HafniumFileLoader.loadComponents("myHome.xml");
		
		for (int i = 0; i < components.size(); i++)
		{
			HomeComponent h = components.get(i);
			System.out.println(h.getClass().toString() + ":: " + h.getMake() + " " + h.getModel() + "(" + h.getSerialNumber() + ")");
		}
	}
}
