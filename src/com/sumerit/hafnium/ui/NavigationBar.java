package com.sumerit.hafnium.ui;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.sumerit.hafnium.util.ImageLoader;

public class NavigationBar implements SelectionListener
{	
	private Vector<NavigationBarElement> elements;	
	private Device display;
	private NavigationBarElement activeElement;
	
	public NavigationBar(Device display)
	{
		this.display = display;
		this.elements = new Vector<NavigationBarElement>();
	}
	
	public void addNavigationElement(NavigationBarElement e)
	{
		this.elements.add(e);
	}
	
	public void createNavigationElement(ToolBar holder, String activeFile, String inactiveFile)
	{		
		ToolItem element = new ToolItem(holder, SWT.NONE);
		elements.add(new NavigationBarElement(element, ImageLoader.load(display, activeFile), ImageLoader.load(display, inactiveFile)));
		
		if (elements.size() == 1) {
			elements.get(0).setActive();
			this.activeElement = elements.get(0); 
		} else {
			elements.get(elements.size()-1).setInactive();
		}
		
		elements.get(elements.size()-1).getElement().addSelectionListener(this);
	}

	public void widgetSelected(SelectionEvent event) 
	{
		ToolItem clickedElement = (ToolItem) event.widget;
		NavigationBarElement element = (NavigationBarElement) clickedElement.getData("owner");
		
		activeElement.setInactive();	
		
		/** @TODO: Remove all of the components from the active element **/
		
		element.setActive();
		activeElement = element;
		
		/** @TODO: Now add all of the components from this nav bar element to the display **/
		// Vector<HomeComponent> components = activeElement.getComponents();
	}

	public void widgetDefaultSelected(SelectionEvent e) {this.widgetSelected(e);}
}
