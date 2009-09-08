package com.sumerit.hafnium.ui;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.sumerit.hafnium.components.HomeComponent;
import com.sumerit.hafnium.util.ImageLoader;

public class NavigationBar implements SelectionListener
{
	private Vector<NavigationBarElement> elements;
	private Device display;
	private NavigationBarElement activeElement;
	private Composite mainContainer;

	public NavigationBar(Device display, Composite mainContainer)
	{
		this.display = display;
		this.elements = new Vector<NavigationBarElement>();
		
		this.mainContainer = mainContainer;
	}

	public void addNavigationElement(NavigationBarElement e)
	{
		this.elements.add(e);
	}

	public void createNavigationElement(Vector<HomeComponent> components, ToolBar holder, String activeFile, String inactiveFile)
	{
		ToolItem element = new ToolItem(holder, SWT.NONE);
		NavigationBarElement navigationBarElement = new NavigationBarElement(element, ImageLoader.load(display, activeFile), ImageLoader.load(display, inactiveFile));
		navigationBarElement.setComponents(components);
		elements.add(navigationBarElement);

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
		Vector<HomeComponent> oldComponents = activeElement.getComponents();
		for (int i = 0; i < oldComponents.size(); i++)
			oldComponents.get(i).setVisible(false);

		/** @TODO: Remove all of the components from the active element **/

		element.setActive();
		activeElement = element;
		Vector<HomeComponent> newComponents = activeElement.getComponents();
		for (int i = 0; i < newComponents.size(); i++)
			newComponents.get(i).setVisible(true);

		/** @TODO: Now add all of the components from this nav bar element to the display **/
		
	}

	public void widgetDefaultSelected(SelectionEvent e) {this.widgetSelected(e);}
}
