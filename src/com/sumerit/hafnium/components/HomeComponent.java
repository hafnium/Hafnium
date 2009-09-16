package com.sumerit.hafnium.components;

import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.cloudgarden.resource.SWTResourceManager;
import com.sumerit.hafnium.devices.Controller;
import com.sumerit.hafnium.util.ImageLoader;


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
	
	protected Thread controllerThread;
	
	protected int serialNumber;
	protected String make = "";
	protected String model;
	private Label energyLabel;
	private Label energyValue;
	private Label nameLabel;
	protected Label iconLabel;
	private Label powerLabel;
	private Button powerButton;
	private Label powerValue;
	private Label icon;
	
	protected Composite componentContainer;
	
	protected Controller controller;
	
	public HomeComponent()
	{
		this(0, "", "");
	}
	
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
		initializeController();
	}
	
	public void add(Composite mainContentContainer, Point position)
	{
		componentContainer = new Composite(mainContentContainer, SWT.NONE);
		componentContainer.setLayout(null);
		componentContainer.setBounds(position.x, position.y, 753, 199);

		// STATIC:: Icon
		{
			icon = new Label(componentContainer, SWT.NONE);
			icon.setBounds(5, 13, 58, 178);
			icon.setImage(ImageLoader.load(mainContentContainer.getDisplay(), "resources/componentIcons/"+(make+model).replace(" ", "")+".png"));
		}
		
		// STATIC:: Power
		{
			powerLabel = new Label(componentContainer, SWT.NONE);
			powerLabel.setText("Power:");
			powerLabel.setBounds(80, 57, 60, 23);
			powerLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
		
		// Power = {On | Off} 
		{
			powerValue = new Label(componentContainer, SWT.NONE);
			powerValue.setText("Off");
			powerValue.setBounds(144, 57, 29, 19);
			powerValue.setFont(SWTResourceManager.getFont("Gill Sans MT",12,1,false,false));
			powerValue.setForeground(SWTResourceManager.getColor(255,0,0));
		}
		
		// STATIC:: Name of this component
		{
			nameLabel = new Label(componentContainer, SWT.NONE);
			nameLabel.setText(make + " " + model);
			nameLabel.setBounds(80, 13, 313, 32);
			nameLabel.setFont(SWTResourceManager.getFont("Gill Sans MT",16,1,false,false));
		}		
		
		// STATIC:: Energy Consumption
		{
			energyLabel = new Label(componentContainer, SWT.NONE);
			energyLabel.setText("Current Energy Consumption:");
			energyLabel.setBounds(80, 82, 229, 24);
			energyLabel.setFont(SWTResourceManager.getFont("Gill Sans MT",12,1,false,false));
		}
		
		// Energy = [0,MAX] kW/hr
		{
			energyValue = new Label(componentContainer, SWT.NONE);
			energyValue.setText("0 kW/hr");
			energyValue.setBounds(313, 82, 68, 27);
			energyValue.setFont(SWTResourceManager.getFont("Gill Sans MT",12,1,false,false));
			energyValue.setForeground(SWTResourceManager.getColor(128,128,128));
		}
		
		// BUTTON:: {PowerOn | PowerOff}
		{
			powerButton = new Button(componentContainer, SWT.PUSH | SWT.CENTER);
			powerButton.setText("Turn On");
			powerButton.setBounds(535, 141, 112, 30);
			powerButton.setFont(SWTResourceManager.getFont("Gill Sans MT",16,1,false,false));
			powerButton.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent evt) {
					powerButtonMouseDown(evt, 0);
				}
			});
		}
		
		this.setVisible(false);
	}
	
	public void setVisible(boolean B)
	{
		componentContainer.setVisible(B);
	}
	
	public abstract void initialize(int serialNumber);
	protected abstract void initializeController();
	
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
	public void powerOn() 
	{
		// Make call to actual hardware
		this.setStatusMessage(this.getMake() + " " + this.getModel() + " AC powering on", HomeComponent.LogLevel.INFO);
		
		controllerThread = new Thread(){
			public void run()
			{
				controller.powerOn();
				
				isPoweredOn = true;		
				/*powerButton.setText("Turn Off");
				powerValue.setText("On");
				powerValue.setForeground(SWTResourceManager.getColor(0, 255, 0));*/
			}
		};		
		controllerThread.start();
	}
	
	/**
	 * Turn the device off. This should be overridden by the leaf classes in the hierarchy as 
	 * they will need to make calls to the actual hardware.
	 */
	public void powerOff() 
	{
		// Make call to actual hardware
		this.setStatusMessage(this.getMake() + " " + this.getModel() + " AC powering off", HomeComponent.LogLevel.INFO);
		this.isPoweredOn = false;
		this.controller.powerOff();
		powerButton.setText("Turn On");
		powerValue.setText("Off");
		powerValue.setForeground(SWTResourceManager.getColor(255, 0, 0));
	}	
	
	public void setIcon(){}
	
	private void powerButtonMouseDown(MouseEvent evt, int index) 
	{
		if(isPoweredOn)
			powerOff( );
		else
			powerOn( );
	}
}
