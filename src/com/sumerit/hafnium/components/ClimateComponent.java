package com.sumerit.hafnium.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;

import com.cloudgarden.resource.SWTResourceManager;
import com.sumerit.hafnium.devices.InternalTemperatureDevice;


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
 * Superclass to all climate controlling devices. 
 * @author Sean Arietta
 *
 */
public abstract class ClimateComponent extends HomeComponent implements ActionListener
{		
	private float temperatureSetting;
	private float ambientTemperature;
	private ActionListener temperatureListener = null;
	InternalTemperatureDevice temperatureController;
	
	protected Composite mainContentContainer;
	private Label temperatureLabel;
	private Label temperatureValue;
	private Label sliderLabel;
	private Slider temperatureSlider;
	private Label adjustTemperatureLabel;

	public ClimateComponent()
	{
		super();
	}
	
	/**
	 * Creates a climate device with a given serial number, make, and model.
	 * @param serialNumber
	 * @param make
	 * @param model
	 */
	public ClimateComponent(int serialNumber, String make, String model) 
	{
		super(serialNumber, make, model);
		ambientTemperature = 70.0f;
		temperatureController = new InternalTemperatureDevice();
		temperatureController.addActionListener(this);
	}
	
	public void initGUI( )
	{
		FormData mainContentContainerLData = new FormData();
		mainContentContainerLData.width = 640;
		mainContentContainerLData.height = 180;
		mainContentContainerLData.left =  new FormAttachment(0, 1000, 190);
		mainContentContainerLData.top =  new FormAttachment(0, 1000, 120);
		mainContentContainer = new Composite(HomeComponent.mainComposite, SWT.NONE);
		mainContentContainer.setLayout(null);
		mainContentContainer.setLayoutData(mainContentContainerLData);
		mainContentContainer.setBackground(SWTResourceManager.getColor(255, 255, 255));
		{
			temperatureLabel = new Label(mainContentContainer, SWT.NONE);
			temperatureLabel.setText("Current Temperature Setting:");
			temperatureLabel.setBounds(98, 74, 231, 24);
			temperatureLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
		{
			temperatureValue = new Label(mainContentContainer, SWT.NONE);
			temperatureValue.setText("70�");
			temperatureValue.setBounds(335, 74, 64, 24);
			temperatureValue.setForeground(SWTResourceManager.getColor(128, 128, 128));
			temperatureValue.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
		{
			temperatureSlider = new Slider(mainContentContainer, SWT.NONE);
			temperatureSlider.setBounds(447, 74, 193, 24);
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
			sliderLabel.setBounds(461, 51, 170, 17);
			sliderLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 10, 1, false, false));
		}
		{
			adjustTemperatureLabel = new Label(mainContentContainer, SWT.NONE);
			adjustTemperatureLabel.setText("Adjust Temperature");
			adjustTemperatureLabel.setBounds(465, 104, 155, 25);
			adjustTemperatureLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
	}
	
	/**
	 * Method to adjust the temperature of this climate device. Makes a call to a local {@link InternalTemperatureDevice} 
	 * to actually adjust the temperature. 
	 * @param targetTemperature the temperature to set this device to
	 * @throws InterruptedException
	 * @throws RuntimeException if the device is not powered on
	 */
	public void setTemperature(float targetTemperature) throws InterruptedException, RuntimeException
	{	
		if (!this.isPoweredOn())
		{
			this.setStatusMessage("Attempted to adjust temperature while device was off", HomeComponent.LogLevel.WARNING);
			throw new RuntimeException("Device has been turned off");
		}
		
		temperatureController.adjustTemperature(ambientTemperature, targetTemperature);
	}
	
	public float getTemperatureSetting()
	{
		return this.temperatureSetting;
	}
	
	public float getAmbientTemperature()
	{
		return this.ambientTemperature;
	}
	
	/**
	 * Receives events that the {@link InternalTemperatureDevice} sends to it. Will react to a temperature 
	 * change and a temperature set event.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getID() == InternalTemperatureDevice.TEMPERATURE_SET)
		{
			this.temperatureSetting = Float.parseFloat(e.getActionCommand());
			this.setStatusMessage("Temperature set to " + this.getTemperatureSetting(), HomeComponent.LogLevel.INFO);
			
			if (temperatureListener != null)
				this.temperatureListener.actionPerformed(e);
		} else if (e.getID() == InternalTemperatureDevice.TEMPERATURE_CHANGE)
		{
			this.ambientTemperature = Float.parseFloat(e.getActionCommand());
			this.setStatusMessage("Temperature changed to " + this.getAmbientTemperature(), HomeComponent.LogLevel.INFO);
		}
	}
	
	/**
	 * Add a listener that wants to be notified when the temperature is finally set to the desired temperature
	 * @param listener
	 */
	public void addChangeTemperatureListener(ActionListener listener)
	{
		this.temperatureListener = listener;
	}
	
	private void temperatureSliderMouseUp(MouseEvent evt) {
		try
		{
			setTemperature(Math.round(((Slider) evt.getSource()).getSelection()));
		} catch(Exception e) { 
			System.out.println(e.getMessage());
		}
		
	}
}
