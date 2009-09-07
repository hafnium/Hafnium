package com.sumerit.hafnium.products;

import com.sumerit.hafnium.components.HomeComponent;

import junit.framework.TestCase;

public class YorkSakaiES3457Test extends TestCase {

	/*public void testInitialize() {
		fail("Not yet implemented");
	}

	public void testYorkSakaiES3457() {
		fail("Not yet implemented");
	}

	public void testYorkSakaiES3457Int() {
		fail("Not yet implemented");
	}*/

	/*public void testAdjustTemperature() {
		fail("Not yet implemented");
	}*/

	/*public void testAirConditioner() {
		fail("Not yet implemented");
	}

	public void testAirConditionerIntStringString() {
		fail("Not yet implemented");
	}*/

	/*public void testGetCondensorState() {
		YorkSakaiES3457 toTest = new YorkSakaiES3457(1234);
		toTest.adjustTemperature(30);
		assertEquals(toTest.getCondensorState( ), AirConditioner.CondensorState.FROZEN);
	}*/

	/*public void testAdd() {
		fail("Not yet implemented");
	}*/

	/*public void testClimateComponent() {
		fail("Not yet implemented");
	}

	public void testClimateComponentIntStringString() {
		fail("Not yet implemented");
	}*/

	public void testGetTemperatureSetting() {
		YorkSakaiES3457 toTest = new YorkSakaiES3457(1234);
		assertEquals(toTest.getAmbientTemperature(), 70.0);
	}

	public void testGetAmbientTemperature() {
		YorkSakaiES3457 toTest = new YorkSakaiES3457(1234);
		assertEquals(toTest.getAmbientTemperature(), 70.0);
	}

	/*public void testHomeComponent() {
		fail("Not yet implemented");
	}

	public void testHomeComponentIntStringString() {
		fail("Not yet implemented");
	}*/

	public void testIsPoweredOn() {
		YorkSakaiES3457 toTest = new YorkSakaiES3457(1234);
		assertEquals(toTest.isPoweredOn( ), false);
		toTest.powerOn( );
		assertEquals(toTest.isPoweredOn( ), true);
		toTest.powerOff( );
		assertEquals(toTest.isPoweredOn( ), false);
	}

	public void testGetMake() {
		YorkSakaiES3457 toTest = new YorkSakaiES3457(1234);
		assertEquals(toTest.getMake( ), "York Sakai");
	}

	public void testGetModel() {
		YorkSakaiES3457 toTest = new YorkSakaiES3457(1234);
		assertEquals(toTest.getModel( ), "ES3457");
	}

	public void testGetSerialNumber() {
		YorkSakaiES3457 toTest = new YorkSakaiES3457(1234);
		assertEquals(toTest.getSerialNumber( ), 1234);
	}

	public void testGetStatusMessage() {
		YorkSakaiES3457 toTest = new YorkSakaiES3457(1234);
		toTest.setStatusMessage("Test", HomeComponent.LogLevel.SEVERE);
		assertEquals(toTest.getStatusMessage(), "SEVERE: Test");
	}

	public void testSetStatusMessage() {
		YorkSakaiES3457 toTest = new YorkSakaiES3457(1234);
		toTest.setStatusMessage("Test", HomeComponent.LogLevel.SEVERE);
		assertEquals(toTest.getStatusMessage(), "SEVERE: Test");
	}

	public void testPowerOn() {
		YorkSakaiES3457 toTest = new YorkSakaiES3457(1234);
		assertEquals(toTest.isPoweredOn( ), false);
		toTest.powerOn( );
		assertEquals(toTest.isPoweredOn( ), true);
		toTest.powerOff( );
		assertEquals(toTest.isPoweredOn( ), false);
	}

	public void testPowerOff() {
		YorkSakaiES3457 toTest = new YorkSakaiES3457(1234);
		assertEquals(toTest.isPoweredOn( ), false);
		toTest.powerOn( );
		assertEquals(toTest.isPoweredOn( ), true);
		toTest.powerOff( );
		assertEquals(toTest.isPoweredOn( ), false);
	}

	/*public void testSetIcon() {
		fail("Not yet implemented");
	}*/

}
