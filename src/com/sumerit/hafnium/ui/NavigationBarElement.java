package com.sumerit.hafnium.ui;

import java.util.Vector;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.ToolItem;

import com.sumerit.hafnium.components.HomeComponent;

public class NavigationBarElement
{
	private Image activeImage;
	private Image inactiveImage;
	private ToolItem element;

	private Vector<HomeComponent> components;

	public NavigationBarElement(ToolItem element, Image activeImage, Image inactiveImage)
	{
		this.element = element;
		this.element.setData("owner", this);
		this.activeImage = activeImage;
		this.inactiveImage = inactiveImage;
		
		this.components = new Vector<HomeComponent>();
	}

	public void setActive()
	{
		element.setImage(activeImage);
	}

	public void setInactive()
	{
		element.setImage(inactiveImage);
	}

	public ToolItem getElement()
	{
		return this.element;
	}

	public void setComponents(Vector<HomeComponent> components)
	{
		this.components = components;
	}

	public Vector<HomeComponent> getComponents()
	{
		return this.components;
	}
}