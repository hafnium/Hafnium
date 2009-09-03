package com.sumerit.hafnium.util;

import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LocalWeather 
{
	private String zipcode;
	private String queryURL;
	private int precipitationPercentage;
	private Document dom;
	
	private int temperature;
	private int iconPointer = -1;
	
	private final String queryURLPrefix = "http://xoap.weather.com/weather/local/";
	private final String queryURLSuffix = "?cc=*&dayf=1&link=xoap&prod=xoap&par=1136215722&key=071fe28af7d57072";
	
	
	public LocalWeather(String zipcode)
	{
		this.zipcode = zipcode;	
		this.queryURL = queryURLPrefix + this.zipcode + queryURLSuffix;
		
		parseWeatherXML();
	}
	
	public Image getCurrentImage(Device device)
	{
		if (dom == null)
			this.parseWeatherXML();
		
		Image weatherImage = null;
		
		if (iconPointer == -1)
			return weatherImage;
		
		String url = "http://image.weather.com/web/common/wxicons/93/" + iconPointer + ".png";
		
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) (new URL(url)).openConnection();
			con.setRequestMethod("GET");
			con.connect();
			weatherImage = new Image(device, con.getInputStream());	
		} catch (Exception e) {
			System.err.println("Could not load weather icon");
			e.printStackTrace();
			return null;
		} 			
		
		return weatherImage;
	}	
	
	public int getTemperature()
	{
		return this.temperature;
	}
	
	public int getPrecipitation()
	{
		return this.precipitationPercentage;
	}
	
	public void parseWeatherXML()
	{
		dom = XMLParser.parseXmlFile(queryURL);
		
		//get the root elememt
		Element doc = dom.getDocumentElement();
			
		//get a nodelist of <cc> elements
		NodeList cc = (doc.getElementsByTagName("cc")).item(0).getChildNodes();
		if(cc != null && cc.getLength() > 0) 
		{		
			for (int i = 0; i < cc.getLength(); i++)
			{
				if (cc.item(i).getNodeName() == "tmp")
					this.temperature = Integer.parseInt(cc.item(i).getTextContent());
				if (cc.item(i).getNodeName() == "icon")
					this.iconPointer = Integer.parseInt(cc.item(i).getTextContent());
			}
		}
		
		Element dayf = (Element) (doc.getElementsByTagName("dayf")).item(0);
		NodeList day = (dayf.getElementsByTagName("day")).item(0).getChildNodes();
		if(day != null && day.getLength() > 0) 
		{		
			boolean useDay = true;
			for (int i = 0; i < day.getLength(); i++)
			{
				if (day.item(i).getNodeName() == "hi")
				{
					useDay = day.item(i).getTextContent() == "N/A" ? false : true;
					break;
				}
			}
			
			NodeList part = ((Element) (dayf.getElementsByTagName("day")).item(0)).getElementsByTagName("part");
			if(part != null && part.getLength() > 0) 
			{		
				for (int i = 0; i < part.getLength(); i++)
				{
					if (((Element) part.item(i)).getAttribute("p") == "d" && useDay)
					{
						precipitationPercentage = Integer.parseInt(((Element) part.item(i)).getElementsByTagName("ppcp").item(0).getTextContent());
					} else if (((Element) part.item(i)).getAttribute("p") == "n")
					{
						precipitationPercentage = Integer.parseInt(((Element) part.item(i)).getElementsByTagName("ppcp").item(0).getTextContent());
					}
				}
			}
		}
	}
}
