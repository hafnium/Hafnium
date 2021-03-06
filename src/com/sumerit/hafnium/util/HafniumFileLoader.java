package com.sumerit.hafnium.util;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sumerit.hafnium.Location;
import com.sumerit.hafnium.Owner;
import com.sumerit.hafnium.components.HomeComponent;

public class HafniumFileLoader 
{

	public static HashMap<String, Vector<HomeComponent>> loadComponents(String path)
	{
		File file = new File(path);
		
		return getHomeComponents(XMLParser.parseXmlFile(file));
	}
	
	public static Owner loadOwner(String path)
	{
		File file = new File(path);
		
		return getOwner(XMLParser.parseXmlFile(file));
	}
	
	public static Location loadLocation(String path)
	{
		File file = new File(path);
		
		return getLocation(XMLParser.parseXmlFile(file));
	}	
	
	private static Owner getOwner(Document dom)
	{
		//get the root elememt
		Element docEle = dom.getDocumentElement();
		
		//get a nodelist of <HomeComponent> elements
		NodeList nl = docEle.getElementsByTagName("Owner");
		if(nl != null && nl.getLength() > 0) 
		{		
			//get the employee element
			Element el = (Element) nl.item(0);
				
			return new Owner(el.getAttribute("name"), el.getAttribute("customerID"));
		} else
		{
			return null;
		}
	}
	
	private static Location getLocation(Document dom)
	{
		//get the root elememt
		Element docEle = dom.getDocumentElement();
		
		//get a nodelist of <HomeComponent> elements
		NodeList nl = docEle.getElementsByTagName("Location");
		if(nl != null && nl.getLength() > 0) 
		{		
			//get the employee element
			Element el = (Element) nl.item(0);
				
			return new Location(el.getAttribute("city"), el.getAttribute("state"), el.getAttribute("zipcode"));
		} else
		{
			return null;
		}
	}
	
	private static HashMap<String, Vector<HomeComponent>> getHomeComponents(Document dom)
	{
		HashMap<String, Vector<HomeComponent>> components = new HashMap<String, Vector<HomeComponent>>();
		
		//get the root elememt
		Element docEle = dom.getDocumentElement();
		
		//get a nodelist of <HomeComponent> elements
		NodeList nl = docEle.getElementsByTagName("HomeComponent");
		if(nl != null && nl.getLength() > 0) 
		{
			for(int i = 0 ; i < nl.getLength();i++) 
			{			
				//get the employee element
				Element el = (Element)nl.item(i);
				
				//get the Employee object
				getHomeComponent(el, components);
			}
		}
		
		return components;
	}
	
	private static void getHomeComponent(Element element, HashMap<String, Vector<HomeComponent>> components) 
	{
		
		//for each <employee> element get text or int values of 
		//name ,id, age and name
		int serial = XMLParser.getIntValue(element,"SerialNumber");

		String type = element.getAttribute("class");
		String category = element.getAttribute("category");
		
		//Create a new Employee with the value read from the xml nodes

		HomeComponent component = null;
		try {
			component = (HomeComponent) Class.forName(type).newInstance();
			component.initialize(serial);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Could not load object of type " + type + " from XML file\nERROR: " + e.getMessage());
		}
		
		if(!components.containsKey(category))
			components.put(category, new Vector<HomeComponent>());
		
		components.get(category).add(component);
			
	}		
}
