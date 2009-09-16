package com.sumerit.hafnium.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.cloudgarden.resource.SWTResourceManager;

public abstract class SecurityCamera extends SecurityComponent 
{
	private Label nightVisionLabel;
	//private Label nightVisionValue;
	protected Button nightVisionButton;
	protected boolean nightVisionOn = false;
	
	public SecurityCamera()
	{
		super();
	}
	
	public SecurityCamera(int serialNumber, String make, String model) 
	{
		super(serialNumber, make, model);
	}
	
	public void add(Composite mainContentContainer, Point position)
	{
		super.add(mainContentContainer, position);
		{
			nightVisionLabel = new Label(componentContainer, SWT.NONE);
			nightVisionLabel.setText("Night Vision Setting:");
			nightVisionLabel.setBounds(80, 107, 180, 27);
			nightVisionLabel.setFont(SWTResourceManager.getFont("Gill Sans MT", 12, 1, false, false));
		}
		/*{
			nightVisionValue = new Label(componentContainer, SWT.NONE);
			nightVisionValue.setText("Off");
			nightVisionValue.setBounds(270, 109, 64, 23);
			nightVisionValue.setFont(SWTResourceManager.getFont("Gill Sans MT",12,1,false,false));
			nightVisionValue.setForeground(SWTResourceManager.getColor(255,0,0));
		}*/
		
		// BUTTON:: {PowerOn | PowerOff}
		{
			nightVisionButton = new Button(componentContainer, SWT.PUSH | SWT.CENTER);
			nightVisionButton.setText("Off");
			nightVisionButton.setBounds(270, 109, 75, 25);
			nightVisionButton.setFont(SWTResourceManager.getFont("Gill Sans MT",12,1,false,false));
			nightVisionButton.setForeground(SWTResourceManager.getColor(255,0,0));
			nightVisionButton.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent evt) {
					nightVisionPowerButtonMouseDown(evt, 0);
				}
			});
		}
	}
	
	private void nightVisionPowerButtonMouseDown(MouseEvent evt, int index) 
	{
		if(nightVisionOn == false && isPoweredOn( ))
		{
			nightVisionButton.setText("On");
			nightVisionButton.setForeground(SWTResourceManager.getColor(0,255,0));
			nightVisionOn = true;
		}
		else
		{
			nightVisionButton.setText("Off");
			nightVisionButton.setForeground(SWTResourceManager.getColor(255,0,0));
			nightVisionOn = false;
		}
	}
}
