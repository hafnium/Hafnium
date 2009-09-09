package com.sumerit.hafnium.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;

import com.cloudgarden.resource.SWTResourceManager;

public abstract class DimmerComponent extends LightingComponent 
{
	private Label dimmerLabel;
	protected Label dimmerValue;
	private Label sliderLabel;
	private Label adjustDimmerLabel;
	protected Slider dimmerSlider;
	protected float dimmerSetting = 0.0f;
	
	public static final String percentSymbol = new String(Character.toChars(37));
	
	public DimmerComponent()
	{
		this(0,"","");
	}
	
	/**
	 * Creates a dimmer device with a given serial number, make, and model.
	 * @param serialNumber
	 * @param make
	 * @param model
	 */
	public DimmerComponent(int serialNumber, String make, String model) 
	{		
		super(serialNumber, make, model);
	}

	public abstract void initialize(int serialNumber); 
	
	protected abstract void initializeController( ); 
	
	public void add(Composite mainContentContainer, Point position)
	{
		super.add(mainContentContainer, position);
		{
			dimmerSlider = new Slider(componentContainer, SWT.NONE);
			dimmerSlider.setBounds(488, 58, 192, 24);
			dimmerSlider.setMinimum(0);
			dimmerSlider.setMaximum(101);
			dimmerSlider.setIncrement(1);
			dimmerSlider.setSelection(0);
			dimmerSlider.setThumb(1);
			dimmerSlider.addMouseListener(new MouseAdapter() {
				public void mouseUp(MouseEvent evt) {
					dimmerSliderMouseUp(evt);
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
			adjustDimmerLabel = new Label(componentContainer, SWT.NONE);
			adjustDimmerLabel.setText("Adjust Temperature");
			adjustDimmerLabel.setBounds(510, 88, 155, 25);
			adjustDimmerLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
		{
			dimmerLabel = new Label(componentContainer, SWT.NONE);
			dimmerLabel.setText("Current Dimmer Setting:");
			dimmerLabel.setBounds(80, 107, 231, 27);
			dimmerLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
		{
			dimmerValue = new Label(componentContainer, SWT.NONE);
			dimmerValue.setText("0" + percentSymbol);
			dimmerValue.setBounds(316, 109, 64, 23);
			dimmerValue.setForeground(SWTResourceManager.getColor(128, 128, 128));
			dimmerValue.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
	}
	
	private void dimmerSliderMouseUp(MouseEvent evt) 
	{
		this.dimmerSetting = Math.round(((Slider) evt.getSource()).getSelection());
		try
		{
			this.dimmerValue.setText(this.dimmerSetting + percentSymbol);
			if(dimmerSetting > 0 && isPoweredOn( ) == false)
				powerOn( );
			else if(dimmerSetting == 0 && isPoweredOn( ) == true)
				powerOff( );
		} catch(Exception e) { 
			System.out.println(e.getMessage());
		}
	}
}
