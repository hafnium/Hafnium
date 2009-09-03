package com.sumerit.hafnium.devices;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that a climate device uses to actually adjust the temperature. This class creates a new 
 * thread when it's method is called that will actually adjust the temperature.
 * @author Sean Arietta
 *
 */
public class InternalTemperatureDevice implements Runnable 
{
	public static final int TEMPERATURE_SET = 0x01;
	public static final int TEMPERATURE_CHANGE = 0x02;
	
	private final float lagFactor = 0.1f;
	private float currentTemperature;
	private float targetTemperature;
	private Thread localThread;
	private ActionListener listener;
	
	private boolean init = false;
	
	/**
	 * This function creates the thread that actually adjusts the temperature.
	 * @param currentTemperature
	 * @param targetTemperature
	 */
	public void adjustTemperature(float currentTemperature, float targetTemperature)
	{
		this.currentTemperature = currentTemperature;
		this.targetTemperature = targetTemperature;	
		
		init = true;
		
		localThread = new Thread(this);
		localThread.start();
	}

	public void run() 
	{
		if (this.init == false)
			return;
		
		float localTemperature = currentTemperature;
		float decrement = 0.0625f;
		
		try 
		{
			float sign = Math.signum(targetTemperature - currentTemperature);
			
			if (sign < 0)
			{
				while (localTemperature > targetTemperature)	
				{				
					localTemperature -= decrement;
					System.out.println(localTemperature);
					this.listener.actionPerformed(new ActionEvent(this, InternalTemperatureDevice.TEMPERATURE_CHANGE, String.valueOf(localTemperature)));
					Thread.sleep((long) ( lagFactor * 1000));
				}
			} else
			{
				while (localTemperature < targetTemperature)	
				{				
					localTemperature += decrement;
					System.out.println(localTemperature);
					this.listener.actionPerformed(new ActionEvent(this, InternalTemperatureDevice.TEMPERATURE_CHANGE, String.valueOf(localTemperature)));
					Thread.sleep((long) ( lagFactor * 1000));
				}
			}
		} catch (InterruptedException e)
		{
			
		}
		
		currentTemperature = localTemperature;
		this.listener.actionPerformed(new ActionEvent(this, InternalTemperatureDevice.TEMPERATURE_SET, String.valueOf(currentTemperature)));
	}	
	
	/**
	 * Add a listener to this object to receive information about temperature changes.
	 * @param listener
	 */
	public void addActionListener(ActionListener listener)
	{
		this.listener = listener;
	}
}
