package com.sumerit.hafnium;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

import com.sumerit.hafnium.components.RuddHeater;
import com.sumerit.hafnium.components.YorkAirConditioner;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class TestDriver implements Runnable, ActionListener 
{
	private YorkAirConditioner myAC;
	private RuddHeater myHeater;
	
	private Text heatMeter;
	private Text acMeter;
	
	private Shell mainWindow;
	private Display mainDisplay;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{		
		TestDriver driver = new TestDriver();
		driver.run();			
	}
	
	public void initWindow()
	{
		mainDisplay = new Display ();
		mainWindow = new Shell (mainDisplay);
		mainWindow.setText("Shell");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 5;
		mainWindow.setLayout(gridLayout);
	}
	
	public void setupHeader()
	{
		ImageData imageData = new ImageData("resources\\header.png");
		
		Image headerImage = new Image(mainDisplay, imageData);
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 5;	
		
	    Label label = new Label(mainWindow, SWT.NONE);
	    label.setImage(headerImage);
	    label.setLayoutData(gridData);
	}
	
	public void setupSideBar()
	{
		ImageData imageData = new ImageData("resources\\sidebar.png");		
		Image sidebarImage = new Image(mainDisplay, imageData);	
		
	    Label label = new Label(mainWindow, SWT.NONE);
	    label.setImage(sidebarImage);
	}
	
	public void update()
	{
		heatMeter.setText("" + myHeater.getAmbientTemperature() + "F");
		acMeter.setText("" + myAC.getAmbientTemperature() + "F");
	}

	@SuppressWarnings("unchecked")
	private void readLocalWeatherRSS()
	{
		try {
            URL feedUrl = new URL("http://www.rssweather.com/wx/us/va/charlottesvle/rss.php");

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            List<SyndEntryImpl> elements = feed.getEntries();
            
            for (int i = 0; i < elements.size(); i++)
            	System.out.println("" + i + ": " + elements.get(i).getDescription().getValue());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: "+ex.getMessage());
        }
	}
	
	public void run() 
	{		
		initWindow();		
		setupHeader();
		setupSideBar();
		readLocalWeatherRSS();
		
		if (true)
		{
			myAC = new YorkAirConditioner(128, "ES354");		
			myAC.addChangeTemperatureListener(this);
			
			myHeater = new RuddHeater(128, "Optiply 2000");
			myHeater.addChangeTemperatureListener(this);
			
			myAC.powerOn();
			myHeater.powerOn();						
			
			heatMeter = new Text(mainWindow, SWT.READ_ONLY | SWT.SINGLE);
			heatMeter.setText("" + myHeater.getAmbientTemperature() + "F");
			
			acMeter = new Text(mainWindow, SWT.READ_ONLY | SWT.SINGLE);
			acMeter.setText("" + myHeater.getAmbientTemperature() + "F");
			
			Button acOff = new Button (mainWindow, SWT.PUSH);
			acOff.setText ("Turn off AC");
			acOff.addSelectionListener (new SelectionAdapter () {
				public void widgetSelected (SelectionEvent e) {
					myAC.powerOff();
				}
			});
			
			Button heatOff = new Button (mainWindow, SWT.PUSH);
			heatOff.setText ("Turn off Heater");
			heatOff.addSelectionListener (new SelectionAdapter () {
				public void widgetSelected (SelectionEvent e) {
					myHeater.powerOff();
				}
			});
			
			GridData gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.horizontalSpan =3;		
			
			Text heaterControlText = new Text(mainWindow, SWT.READ_ONLY | SWT.SINGLE);
			heaterControlText.setText("Heat Adjustment");			
			
			Slider heaterControl = new Slider(mainWindow, SWT.HORIZONTAL);
			heaterControl.setLayoutData(gridData);
			heaterControl.setValues((int) myHeater.getAmbientTemperature(), 50, 95, 1, 1, 5);
			heaterControl.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {				
				}
				public void widgetSelected(SelectionEvent e) 
				{
					try {
						myHeater.setTemperature(((Slider) e.getSource()).getSelection());
					} catch (Exception e1) {
						System.out.println("ERROR: " + e1.getMessage());
					} 
				}
				
			});
			
			Text acControlText = new Text(mainWindow, SWT.READ_ONLY | SWT.SINGLE);
			acControlText.setText("AC Adjustment");			
			
			Slider acControl = new Slider(mainWindow, SWT.HORIZONTAL);
			acControl.setLayoutData(gridData);
			acControl.setValues((int) myAC.getAmbientTemperature(), 50, 95, 1, 1, 5);
			acControl.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {				
				}
				public void widgetSelected(SelectionEvent e) 
				{
					try {
						myAC.setTemperature(((Slider) e.getSource()).getSelection());
					} catch (Exception e1) {
						System.out.println("ERROR: " + e1.getMessage());
					} 
				}
				
			});
		}
		
		mainWindow.pack ();
		mainWindow.open ();
				
		while (!mainWindow.isDisposed ()) 
		{			
			if (!mainDisplay.readAndDispatch ())
			{				
				mainDisplay.sleep();
				update();				
			}
		}
		mainDisplay.dispose ();
	}

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("\nTemperature has changed...");
		System.out.println(myAC.getStatusMessage());
	}

}
