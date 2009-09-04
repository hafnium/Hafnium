package com.sumerit.hafnium.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public class ImageLoader 
{
	public static Image load(Device device, String path)
	{
		File file = new File(path);
		FileInputStream fis = null;
		ImageData imageData = null;
		Image image = null;
		try {
			fis = new FileInputStream(file);
			imageData = new ImageData(fis);
			image = new Image(device, imageData);
		} catch (FileNotFoundException e) {
			System.err.println("Could not open file " + path);
		}
		return image;
	}
}
