package com.sumerit.hafnium.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;

import com.cloudgarden.resource.SWTResourceManager;
import com.sumerit.hafnium.devices.TemperatureSampler;


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
public abstract class ClimateComponent extends HomeComponent
{		
	private float temperatureSetting;
	protected TemperatureSampler temperatureSampler;
	
	protected Composite mainContentContainer;
	private Label temperatureLabel;
	protected Label temperatureValue;
	private Label sliderLabel;
	protected Slider temperatureSlider;
	private Label adjustTemperatureLabel;
	
	public static final String degreeSymbol = new String(Character.toChars(176));

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
	}
	
	public void add(Composite mainContentContainer, Point position)
	{
		super.add(mainContentContainer, position);
		{
			temperatureSlider = new Slider(componentContainer, SWT.NONE);
			temperatureSlider.setBounds(488, 58, 192, 24);
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
			sliderLabel = new Label(componentContainer, SWT.NONE);
			sliderLabel.setText("30    40    50    60    70    80");
			sliderLabel.setBounds(498, 38, 170, 16);
			sliderLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 10, 1, false, false));
		}
		{
			adjustTemperatureLabel = new Label(componentContainer, SWT.NONE);
			adjustTemperatureLabel.setText("Adjust Temperature");
			adjustTemperatureLabel.setBounds(510, 88, 155, 25);
			adjustTemperatureLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
		{
			temperatureLabel = new Label(componentContainer, SWT.NONE);
			temperatureLabel.setText("Current Temperature Setting:");
			temperatureLabel.setBounds(80, 107, 231, 27);
			temperatureLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
		{
			temperatureValue = new Label(componentContainer, SWT.NONE);
			temperatureValue.setText("70" + ClimateComponent.degreeSymbol);
			temperatureValue.setBounds(316, 109, 64, 23);
			temperatureValue.setForeground(SWTResourceManager.getColor(128, 128, 128));
			temperatureValue.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
	}
	
	/**
	 * Method to adjust the temperature of this climate device. Makes a call to a local {@link InternalTemperatureDevice} 
	 * to actually adjust the temperature. 
	 * @param targetTemperature the temperature to set this device to
	 * @throws InterruptedException
	 * @throws RuntimeException if the device is not powered on
	 */
	public abstract void adjustTemperature(float targetTemperature);
	
	public float getTemperatureSetting()
	{
		return this.temperatureSetting;
	}
	
	public float getAmbientTemperature()
	{
		return temperatureSampler.sampleAmbientTemperature();
	}
	
	private void temperatureSliderMouseUp(MouseEvent evt) 
	{
		this.temperatureSetting = Math.round(((Slider) evt.getSource()).getSelection());
		try
		{
			this.temperatureValue.setText(this.temperatureSetting + ClimateComponent.degreeSymbol + "F");
			adjustTemperature(this.temperatureSetting);
		} catch(Exception e) { 
			System.out.println(e.getMessage());
		}
		
	}
	
	protected void resetTemperatureSetting()
	{
		this.temperatureSlider.setSelection((int) this.temperatureSampler.sampleAmbientTemperature());
		this.temperatureValue.setText("" + (int) this.temperatureSampler.sampleAmbientTemperature() + ClimateComponent.degreeSymbol); 		
	}
}
