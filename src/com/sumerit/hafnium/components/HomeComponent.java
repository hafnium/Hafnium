package com.sumerit.hafnium.components;

import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.cloudgarden.resource.SWTResourceManager;
import com.sumerit.hafnium.TestUI;


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
/**
 * Main class that all home automation components should extend. Includes barebones 
 * functionality such as the ability to turn the device on and off and retrieve the make
 * and model of the device. Also includes a logging function that all subclasses should use
 * to communicate with users.
 * 
 * @author Sean Arietta
 * 
 */
public abstract class HomeComponent
{
	
	public static enum LogLevel {SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST };
	
	private static Logger statusLogger = Logger.getLogger(HomeComponent.class.getName());	
	
	protected boolean isPoweredOn;
	protected int inputVoltage;
	protected enum inputType {AC, DC};
	protected boolean isGrounded;
	private String statusMessage;
	
	private int serialNumber;
	private String make = "";
	private String model;
	private Label climateIcons;
	private Label energyLabel;
	private Label energyValues;
	private Label nameLabel;
	protected Label iconLabel;
	private Label powerLabel;
	private Button powerButton;
	private Label powerValue;
	
	public static Composite mainComposite;
	public static Device display;
	
	public HomeComponent(){}
	
	/**
	 * 
	 * @param serialNumber
	 * @param make
	 * @param model
	 */
	public HomeComponent(int serialNumber, String make, String model)
	{
		this.serialNumber = serialNumber;
		this.make = make;
		this.model = model;
		initGUI( );
	}
	
	public void initGUI( )
	{
		FormData mainContentContainerLData = new FormData();
		mainContentContainerLData.width = 640;
		mainContentContainerLData.height = 180;
		mainContentContainerLData.left =  new FormAttachment(0, 1000, 190);
		mainContentContainerLData.top =  new FormAttachment(0, 1000, 120);

		{
			powerLabel = new Label(HomeComponent.mainComposite, SWT.NONE);
			powerLabel.setText("Power:");
			powerLabel.setBounds(98, 45, 61, 23);
			powerLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
		{
			powerValue = new Label(HomeComponent.mainComposite, SWT.NONE);
			powerValue.setText("Off");
			powerValue.setBounds(171, 45, 29, 23);
			powerValue.setFont(SWTResourceManager.getFont("Gill Sans MT",12,1,false,false));
			powerValue.setForeground(SWTResourceManager.getColor(255,0,0));
		}
		{
			nameLabel = new Label(HomeComponent.mainComposite, SWT.NONE);
			nameLabel.setText(make);
			nameLabel.setBounds(86, 1, 313, 32);
			nameLabel.setFont(SWTResourceManager.getFont("Gill Sans MT",16,1,false,false));
		}
		{
			climateIcons = new Label(HomeComponent.mainComposite, SWT.NONE);
			climateIcons.setBounds(0, 0, 60, 180);
		}
		{
			energyValues = new Label(HomeComponent.mainComposite, SWT.NONE);
			energyValues.setText("0 W/hr");
			energyValues.setBounds(335, 104, 64, 25);
			energyValues.setFont(SWTResourceManager.getFont("Gill Sans MT",12,1,false,false));
			energyValues.setForeground(SWTResourceManager.getColor(128,128,128));
		}
		{
			energyLabel = new Label(HomeComponent.mainComposite, SWT.NONE);
			energyLabel.setText("Current Energy Consumption:");
			energyLabel.setBounds(98, 104, 231, 25);
			energyLabel.setFont(SWTResourceManager.getFont("Gill Sans MT",12,1,false,false));
		}
		{
			powerButton = new Button(HomeComponent.mainComposite, SWT.PUSH | SWT.CENTER);
			powerButton.setText("Turn On");
			powerButton.setBounds(488, 150, 112, 30);
			powerButton.setFont(SWTResourceManager.getFont("Gill Sans MT",16,1,false,false));
			powerButton.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent evt) {
					powerButtonMouseDown(evt, 0);
				}
			});
		}
	}
	
	public void initialize(int serialNumber, String make, String model)
	{
		this.serialNumber = serialNumber;
		this.make = make;
		this.model = model;
	}
	
	/**
	 * Check to see if device is powered on
	 * @return
	 */
	public boolean isPoweredOn()
	{
		return isPoweredOn;
	}
	
	/**
	 * Get the make of device
	 * @return
	 */
	public String getMake()
	{
		return make;
	}
	
	/**
	 * Get the model of the device
	 * @return
	 */
	public String getModel()
	{
		return model;
	}
	
	/**
	 * Get the serial number of the device
	 * @return
	 */
	public int getSerialNumber()
	{
		return serialNumber;
	}
	
	/**
	 * Get the last status message that was set
	 * @return
	 */
	public String getStatusMessage()
	{
		return this.statusMessage;
	}
	
	/**
	 * Function to set the status message of this device. Makes a call to the internal {@link Logger}
	 * @param message a {@link String} containing the message
	 * @param level the level of the message. 
	 * @see Logger
	 */
	public void setStatusMessage(String message, LogLevel level)
	{
		String pre = "";
		switch(level)
		{
			case SEVERE:
				pre = "SEVERE";
				statusLogger.severe(message);
				break;
			case WARNING:
				pre = "WARNING";
				statusLogger.warning(message);
				break;
			case INFO:
				pre = "INFO";
				statusLogger.info(message);
				break;
			case FINE:
				pre = "FINE";
				statusLogger.fine(message);
				break;
			case FINER:
				pre = "FINER";
				statusLogger.finer(message);
				break;
			case FINEST:
				pre = "FINEST";
				statusLogger.finest(message);
				break;
		}
		this.statusMessage = pre + ": " + message;
	}
	
	/**
	 * Turn the device on. This should be overridden by the leaf classes in the hierarchy as 
	 * they will need to make calls to the actual hardware.
	 */
	public abstract void powerOn();
	
	/**
	 * Turn the device off. This should be overridden by the leaf classes in the hierarchy as 
	 * they will need to make calls to the actual hardware.
	 */
	public abstract void powerOff();	
	
	public abstract void setIcon( );
	
	private void powerButtonMouseDown(MouseEvent evt, int index) 
	{
		if(isPoweredOn)
		{
			powerOff( );
			powerButton.setText("Turn On");
			powerValue.setText("Off");
			powerValue.setForeground(SWTResourceManager.getColor(255, 0, 0));
		}
		else
		{
			powerOn( );
			powerButton.setText("Turn Off");
			powerValue.setText("On");
			powerValue.setForeground(SWTResourceManager.getColor(0, 255, 0));
		}
	}
}
