package com.sumerit.hafnium;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Event;

import com.sumerit.hafnium.components.HomeComponent;
import com.sumerit.hafnium.components.RuddHeater;
import com.sumerit.hafnium.components.YorkAirConditioner;
import com.sumerit.hafnium.ui.NavigationBar;
import com.sumerit.hafnium.util.HafniumFileLoader;
import com.sumerit.hafnium.util.ImageLoader;
import com.sumerit.hafnium.util.LocalWeather;
import com.sumerit.hafnium.util.TemperatureFormatter;
import com.sumerit.hafnium.util.TemperatureFormatter.TemperatureScale;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

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
public class TestUI extends org.eclipse.swt.widgets.Composite {
	private Composite sideBarContainer;
	private Composite headerContainer;
	private Label currentWeatherLabel;
	private Label energyLabel;
	private Label temperatureLabel;
	private Label powerLabel;
	private Label ambientTemperatureLabel;
	private Button[] powerButtons;
	private Label adjustTemperatureLabel;
	private Label sliderLabel;
	private Slider temperatureSlider;
	private Label[] energyValues;
	private Label[] temperatureValues;
	private Label[] powerValues;
	private Label climateLabels;
	private Label[] climateIcons;
	private ToolBar mainNavigation;
	private Label[] quickViewIcons;
	private Label[] quickViewTexts;
	private Label quickViewLabel;
	private Label currentWeatherAdditional;
	private Label currentWeatherTemperature;
	private Label currentWeatherIcon;
	private Label closeButton;
	private Composite mainContentContainer;
	private NavigationBar navigation;
	
	private static Display display;
	
	private Home home;
	
	private YorkAirConditioner myAC;
	private RuddHeater myHeater;
	private String degreeSymbol = new String(Character.toChars(176));
	
	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	public static Device getGlobalDisplay()
	{
		return TestUI.display;
	}

	public TestUI(Composite parent, int style) 
	{
		super(parent, style);
		
		HomeComponent.display = this.getDisplay();
		HomeComponent.mainComposite = this;
		
		home = new Home();
		home.setComponents(HafniumFileLoader.loadComponents("myHome.xml"));
		home.setOwner(HafniumFileLoader.loadOwner("myHome.xml"));
		home.setLocation(HafniumFileLoader.loadLocation("myHome.xml"));
		initGUI();
	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI() {
		try {
			this.setBackground(SWTResourceManager.getColor(192, 192, 192));
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.layout();
			pack();
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
						navigation = new NavigationBar(this.getDisplay());
						navigation.createNavigationElement(mainNavigation, "resources/navigationIcons/hvac_active.png", "resources/navigationIcons/hvac_inactive.png");
						navigation.createNavigationElement(mainNavigation, "resources/navigationIcons/lighting_active.png", "resources/navigationIcons/lighting_inactive.png");
						navigation.createNavigationElement(mainNavigation, "resources/navigationIcons/entertainment_active.png", "resources/navigationIcons/entertainment_inactive.png");
						navigation.createNavigationElement(mainNavigation, "resources/navigationIcons/security_active.png", "resources/navigationIcons/security_inactive.png");
						navigation.createNavigationElement(mainNavigation, "resources/navigationIcons/appliances_active.png", "resources/navigationIcons/appliances_inactive.png");
					}					
					mainNavigation.pack();
				}
				
				
				/*{
					climateIcons = new Label[1];
					climateIcons[0] = new Label(mainContentContainer, SWT.NONE);
					climateIcons[0].setBounds(49, 185, 58, 178);
					climateIcons[0].setImage(ImageLoader.load(this.getDisplay(), "resources/componentIcons/YorkES354.png"));
				}
				{
					climateLabels = new Label(mainContentContainer, SWT.NONE);
					climateLabels.setText("Sakai Standing Air Conditioner");
					climateLabels.setBounds(134, 185, 313, 32);
					climateLabels.setFont(SWTResourceManager.getFont("Gill Sans MT", 16, 1, false, false));
				}
				{
					powerLabel = new Label(mainContentContainer, SWT.NONE);
					powerLabel.setText("Power:");
					powerLabel.setBounds(146, 229, 61, 23);
					powerLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
				}
				{
					temperatureLabel = new Label(mainContentContainer, SWT.NONE);
					temperatureLabel.setText("Current Temperature Setting:");
					temperatureLabel.setBounds(146, 258, 231, 24);
					temperatureLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
				}
				{
					energyLabel = new Label(mainContentContainer, SWT.NONE);
					energyLabel.setText("Current Energy Consumption:");
					energyLabel.setBounds(146, 288, 231, 25);
					energyLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
				}
				{
					powerValues = new Label[1];
					powerValues[0] = new Label(mainContentContainer, SWT.NONE);
					powerValues[0].setText("Off");
					powerValues[0].setBounds(219, 229, 29, 23);
					powerValues[0].setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
					powerValues[0].setForeground(SWTResourceManager.getColor(255, 0, 0));
				}
				{
					temperatureValues = new Label[1];
					temperatureValues[0] = new Label(mainContentContainer, SWT.NONE);
					temperatureValues[0].setText("70�");
					temperatureValues[0].setBounds(383, 258, 64, 24);
					temperatureValues[0].setForeground(SWTResourceManager.getColor(128, 128, 128));
					temperatureValues[0].setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
				}
				{
					energyValues = new Label[1];
					energyValues[0] = new Label(mainContentContainer, SWT.NONE);
					energyValues[0].setText("0 W/hr");
					energyValues[0].setBounds(383, 288, 64, 25);
					energyValues[0].setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
					energyValues[0].setForeground(SWTResourceManager.getColor(128, 128, 128));
				}
				{
					temperatureSlider = new Slider(mainContentContainer, SWT.NONE);
					temperatureSlider.setBounds(495, 258, 192, 24);
					temperatureSlider.setMinimum(30);
					temperatureSlider.setMaximum(80);
					temperatureSlider.setIncrement(1);
					temperatureSlider.setSelection(70);
					temperatureSlider.setThumb(1);
					temperatureSlider.addMouseListener(new MouseAdapter() {
						public void mouseUp(MouseEvent evt) {
							temperatureSliderMouseUp(evt);
						}
					});
				}
				{
					sliderLabel = new Label(mainContentContainer, SWT.NONE);
					sliderLabel.setText("30    40    50    60    70    80");
					sliderLabel.setBounds(509, 235, 170, 17);
					sliderLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 10, 1, false, false));
				}
				{
					adjustTemperatureLabel = new Label(mainContentContainer, SWT.NONE);
					adjustTemperatureLabel.setText("Adjust Temperature");
					adjustTemperatureLabel.setBounds(513, 288, 155, 25);
					adjustTemperatureLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
				}
				{
					powerButtons = new Button[1];
					powerButtons[0] = new Button(mainContentContainer, SWT.PUSH | SWT.CENTER);
					powerButtons[0].setText("Turn On");
					powerButtons[0].setBounds(535, 333, 112, 30);
					powerButtons[0].setFont(SWTResourceManager.getFont("Gill Sans MT", 16, 1, false, false));
					powerButtons[0].addMouseListener(new MouseAdapter() {
						public void mouseDown(MouseEvent evt) {
							powerButtonsMouseDown(evt, 0);
						}
					});
				}*/
				{
					ambientTemperatureLabel = new Label(mainContentContainer, SWT.NONE);
					ambientTemperatureLabel.setText("Ambient Temperature: 0�");
					ambientTemperatureLabel.setBounds(176, 117, 385, 39);
					ambientTemperatureLabel.setBackground(SWTResourceManager.getColor(32, 32, 32));
					ambientTemperatureLabel.setForeground(SWTResourceManager.getColor(255, 255, 255));
					ambientTemperatureLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 18, 1, false, false));
					ambientTemperatureLabel.setAlignment(SWT.CENTER);
				}
				{
					myAC = new YorkAirConditioner(128, "Sakai", "ES354");
					//myAC.setBounds(60, 200, 640, 180);
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
					currentWeatherIcon.setImage(ImageLoader.load(this.getDisplay(), "resources/weatherIcons/sunny.png"));
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
				
				// Quick view section
				{
					quickViewLabel = new Label(sideBarContainer, SWT.NONE);
					quickViewLabel.setText("Quick View");
					quickViewLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 13, 1, false, false));
					quickViewLabel.setForeground(SWTResourceManager.getColor(51, 51, 51));
					quickViewLabel.setBounds(0, 286, 190, 30);
					quickViewLabel.setAlignment(SWT.CENTER);
				}
				{
					quickViewIcons = new Label[1];
					quickViewIcons[0] = new Label(sideBarContainer, SWT.NONE);
					Image icon = ImageLoader.load(this.getDisplay(), "resources/componentIcons/greenLight.png");
					quickViewIcons[0].setImage(icon);					
					quickViewIcons[0].setBounds(18, 328, icon.getBounds().width, icon.getBounds().height);				
				}
				{
					quickViewTexts = new Label[1];
					quickViewTexts[0] = new Label(sideBarContainer, SWT.NONE);
					quickViewTexts[0].setText("Front Light - ON\nLiving Room Light - ON\nKitchen Light - ON");
					quickViewTexts[0].setFont(SWTResourceManager.getFont("Gill Sans MT", 6, 1, false, false));
					quickViewTexts[0].setBounds(68, 328, 125, 40);				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the local weather from an RSS feed and attempts to set all of the display elements in the current weather section
	 * accordingly. Very sensitive to non-standard strings in the weather report... obviously.
	 */
	private void readLocalWeatherRSS()
	{
		LocalWeather localWeather = new LocalWeather(home.getLocation().getZipcode());		
		
		currentWeatherTemperature.setText("" + localWeather.getTemperature() + degreeSymbol + "F");    
		currentWeatherIcon.setImage(localWeather.getCurrentImage(this.getDisplay()));
		currentWeatherAdditional.setText("Today's High: " + localWeather.getHighTemperature() + "\nPrecipitation: " + localWeather.getPrecipitation() + "%");
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
		TestUI inst = new TestUI(shell, SWT.NULL);
		inst.readLocalWeatherRSS();
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
	
	public void update( )
	{
		ambientTemperatureLabel.setText("Ambient Temperature: " + TemperatureFormatter.format(myAC.getAmbientTemperature(), TemperatureScale.Fahrenheit));
	}
	
	private void closeButtonMouseDown(MouseEvent evt) {
		this.getShell().close();
	}
	
	private void powerButtonsMouseDown(MouseEvent evt, int index) {
		//System.out.println("powerButtons.mouseDown, event="+evt);
		//TODO add your code for powerButtons.mouseDown
		if(powerButtons[index].getText( ).equals("Turn On"))
		{
			myAC.powerOn( );
			powerButtons[index].setText("Turn Off");
			powerValues[index].setText("On");
			powerValues[0].setForeground(SWTResourceManager.getColor(0, 255, 0));
		}
		else if(powerButtons[index].getText( ).equals("Turn Off"))
		{
			myAC.powerOff( );
			powerButtons[index].setText("Turn On");
			powerValues[index].setText("Off");
			powerValues[0].setForeground(SWTResourceManager.getColor(255, 0, 0));
		}
	}
	
	private void temperatureSliderMouseUp(MouseEvent evt) {
		//System.out.println("temperatureSlider.mouseUp, event="+evt);
		//TODO add your code for temperatureSlider.mouseUp
		try
		{
			myAC.setTemperature(Math.round(((Slider) evt.getSource()).getSelection()));
		} catch(Exception e) { 
			System.out.println(e.getMessage());
		}
		
	}

}
