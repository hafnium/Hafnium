package com.sumerit.hafnium;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;

import com.sumerit.hafnium.components.ClimateComponent;
import com.sumerit.hafnium.components.HomeComponent;
import com.sumerit.hafnium.ui.NavigationBar;
import com.sumerit.hafnium.util.ImageLoader;
import com.sumerit.hafnium.util.LocalWeather;

import com.cloudgarden.resource.SWTResourceManager;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class UI extends org.eclipse.swt.widgets.Composite implements ActionListener 
{
	private Composite sideBarContainer;
	private Composite headerContainer;
	private Label currentWeatherLabel;
	private Label ambientTemperatureLabel;
	private ToolBar mainNavigation;
	private Label currentWeatherAdditional;
	private Label currentWeatherTemperature;
	private Label currentWeatherIcon;
	private Label closeButton;
	private Composite mainContentContainer;
	private NavigationBar navigation;
	
	private static Display display;
	private Home home;
	
	private String degreeSymbol = new String(Character.toChars(176));
	
	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	public static Device getGlobalDisplay()
	{
		return UI.display;
	}

	public UI(Composite parent, int style) 
	{
		super(parent, style);
		home = new Home("myHome.xml");				
		
		// Add main components of the UI
		initGUI();
		
		// Add this home's comHomeComponente UI
		
		{
			Vector<HomeComponent> hvacComponents = home.getComponents("hvac");
			for (int i = 0; i < hvacComponents.size(); i++)
				hvacComponents.get(i).add(this.mainContentContainer, new Point(44, 179 + 220*i));
		}
		
		{
			Vector<HomeComponent> lightingComponents = home.getComponents("lighting");
			for (int i = 0; i < lightingComponents.size(); i++)
				lightingComponents.get(i).add(this.mainContentContainer, new Point(44, 179 + 220*i));
		}
		
		{
			Vector<HomeComponent> electronicComponents = home.getComponents("electronic");
			for (int i = 0; i < electronicComponents.size(); i++)
				electronicComponents.get(i).add(this.mainContentContainer, new Point(44, 179 + 220*i));
		}
		
		{
			Vector<HomeComponent> securityComponents = home.getComponents("security");
			for (int i = 0; i < securityComponents.size(); i++)
				securityComponents.get(i).add(this.mainContentContainer, new Point(44, 179 + 220*i));
		}
		
		this.pack();
		
		Event hvacSelection = new Event();
		hvacSelection.widget = this.mainNavigation.getItem(0);
		this.navigation.widgetDefaultSelected(new SelectionEvent(hvacSelection));
	}
	
	public void update()
	{
		float ambientTemperature = ((ClimateComponent) home.getComponents("hvac").get(0)).getAmbientTemperature();
		this.ambientTemperatureLabel.setText("Ambient Temperature: " + ambientTemperature);
	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI() 
	{
		try 
		{
			this.setBackground(SWTResourceManager.getColor(192, 192, 192));
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.layout();
			this.setSize(1024, 768);
			
			// Set up the header and the close button that goes on the header
			{
				FormData composite1LData = new FormData();
				composite1LData.width = 1024;
				composite1LData.height = 120;
				composite1LData.left =  new FormAttachment(0, 1000, 0);
				composite1LData.top =  new FormAttachment(0, 1000, 0);
				headerContainer = new Composite(this, SWT.NONE);
				headerContainer.setLayout(null);
				headerContainer.setLayoutData(composite1LData);
				headerContainer.setBackgroundImage(ImageLoader.load(this.getDisplay(), "resources/header.png"));
				{
					closeButton = new Label(headerContainer, SWT.PUSH | SWT.CENTER);
					closeButton.setLocation(new org.eclipse.swt.graphics.Point(100, 0));
					closeButton.setBounds(958, -2, 66, 33);
					closeButton.setImage(ImageLoader.load(this.getDisplay(), "resources/closeIcon.png"));
					closeButton.addMouseListener(new MouseAdapter() {
						public void mouseDown(MouseEvent evt) {
							closeButtonMouseDown(evt);
						}
					});
				}
			}
			{
				FormData mainContentContainerLData = new FormData();
				mainContentContainerLData.width = 834;
				mainContentContainerLData.height = 648;
				mainContentContainerLData.left =  new FormAttachment(0, 1000, 190);
				mainContentContainerLData.top =  new FormAttachment(0, 1000, 120);
				mainContentContainer = new Composite(this, SWT.NONE);
				mainContentContainer.setLayout(null);
				mainContentContainer.setLayoutData(mainContentContainerLData);
				mainContentContainer.setBackground(SWTResourceManager.getColor(255, 255, 255));
				{
					mainNavigation = new ToolBar(mainContentContainer, SWT.FLAT | SWT.WRAP);
					mainNavigation.setBounds(0, 0, 835, 85);
					mainNavigation.setLayoutData(new FillLayout());
					mainNavigation.setBackground(SWTResourceManager.getColor(239, 239, 239));
					// Create the navigation tab bar
					{
						navigation = new NavigationBar(this.getDisplay(), this.mainContentContainer);
						navigation.createNavigationElement(home.getComponents("hvac"), mainNavigation, "resources/navigationIcons/hvac_active.png", "resources/navigationIcons/hvac_inactive.png");
						navigation.createNavigationElement(home.getComponents("lighting"), mainNavigation, "resources/navigationIcons/lighting_active.png", "resources/navigationIcons/lighting_inactive.png");
						navigation.createNavigationElement(home.getComponents("entertainment"), mainNavigation, "resources/navigationIcons/entertainment_active.png", "resources/navigationIcons/entertainment_inactive.png");
						navigation.createNavigationElement(home.getComponents("security"), mainNavigation, "resources/navigationIcons/security_active.png", "resources/navigationIcons/security_inactive.png");
						navigation.createNavigationElement(home.getComponents("other"), mainNavigation, "resources/navigationIcons/appliances_active.png", "resources/navigationIcons/appliances_inactive.png");
					}					
					mainNavigation.pack();
				}

				{
					ambientTemperatureLabel = new Label(mainContentContainer, SWT.NONE);
					ambientTemperatureLabel.setText("Ambient Temperature: 0" + degreeSymbol);
					ambientTemperatureLabel.setBounds(176, 117, 385, 39);
					ambientTemperatureLabel.setBackground(SWTResourceManager.getColor(32, 32, 32));
					ambientTemperatureLabel.setForeground(SWTResourceManager.getColor(255, 255, 255));
					ambientTemperatureLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 18, 1, false, false));
					ambientTemperatureLabel.setAlignment(SWT.CENTER);
				}
			}
			
			// Set up the sidebar
			{
				// Make it look pretty
				FormData sideBarContainerLData = new FormData();
				sideBarContainerLData.width = 190;
				sideBarContainerLData.height = 647;
				sideBarContainerLData.left =  new FormAttachment(0, 1000, 0);
				sideBarContainerLData.top =  new FormAttachment(0, 1000, 120);
				sideBarContainer = new Composite(this, SWT.NONE);
				sideBarContainer.setLayout(null);
				sideBarContainer.setLayoutData(sideBarContainerLData);
				sideBarContainer.setBackgroundImage(ImageLoader.load(this.getDisplay(), "resources/sidebar.png"));
				
				// Current weather section
				{
					currentWeatherLabel = new Label(sideBarContainer, SWT.NONE);
					currentWeatherLabel.setText("Current Weather");
					currentWeatherLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 13, 1, false, false));
					currentWeatherLabel.setBounds(13, 24, 161, 25);
					currentWeatherLabel.setForeground(SWTResourceManager.getColor(51, 51, 51));
					currentWeatherLabel.setAlignment(SWT.CENTER);
				}
				{
					currentWeatherIcon = new Label(sideBarContainer, SWT.NONE);
					currentWeatherIcon.setBounds(43, 61, 100, 100);
					currentWeatherIcon.setSize(93, 93);
				}
				{
					currentWeatherTemperature = new Label(sideBarContainer, SWT.NONE);
					currentWeatherTemperature.setBounds(17, 160, 160, 43);
					currentWeatherTemperature.setFont(SWTResourceManager.getFont("Gill Sans MT", 24, 1, false, false));
					currentWeatherTemperature.setForeground(SWTResourceManager.getColor(51, 51, 51));
					currentWeatherTemperature.setAlignment(SWT.CENTER);
				}
				{
					currentWeatherAdditional = new Label(sideBarContainer, SWT.NONE);
					currentWeatherAdditional.setFont(SWTResourceManager.getFont("Gill Sans MT", 8, 1, false, false));
					currentWeatherAdditional.setForeground(SWTResourceManager.getColor(51, 51, 51));
					currentWeatherAdditional.setBounds(11, 202, 166, 47);
					currentWeatherAdditional.setAlignment(SWT.CENTER);
				}
				
				LocalWeather localWeather = new LocalWeather(home.getLocation().getZipcode());		
				
				currentWeatherTemperature.setText("" + localWeather.getTemperature() + degreeSymbol + "F");    
				currentWeatherIcon.setImage(localWeather.getCurrentImage(this.getDisplay()));
				currentWeatherAdditional.setText("Today's High: " + localWeather.getHighTemperature() + "\nPrecipitation: " + localWeather.getPrecipitation() + "%");
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) 
	{
		display = Display.getDefault();
		Shell shell = new Shell(display, SWT.NO_TRIM);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		UI inst = new UI(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) 
		{
			inst.pack();
			shell.pack();
		} else 
		{
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		
		shell.open();
		while (!shell.isDisposed()) 
		{
			if (!display.readAndDispatch())
			{
				//display.sleep();
				inst.update();
			}
		}
	}
	
	private void closeButtonMouseDown(MouseEvent evt) {
		this.getShell().close();
	}

	//@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

}
