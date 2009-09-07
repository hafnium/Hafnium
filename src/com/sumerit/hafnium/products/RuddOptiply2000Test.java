package com.sumerit.hafnium.products;

import com.sumerit.hafnium.components.HomeComponent;

import junit.framework.TestCase;

public class RuddOptiply2000Test extends TestCase {

	/*public void testInitialize() {
		fail("Not yet implemented");
	}*/

	public void testPowerOn() {
		RuddOptiply2000 toTest = new RuddOptiply2000(1234);
		assertEquals(toTest.isPoweredOn( ), false);
		toTest.powerOn( );
		assertEquals(toTest.isPoweredOn( ), true);
		toTest.powerOff( );
		assertEquals(toTest.isPoweredOn( ), false);
	}

	public void testPowerOff() {
		RuddOptiply2000 toTest = new RuddOptiply2000(1234);
		assertEquals(toTest.isPoweredOn( ), false);
		toTest.powerOn( );
		assertEquals(toTest.isPoweredOn( ), true);
		toTest.powerOff( );
		assertEquals(toTest.isPoweredOn( ), false);
	}

	/*public void testRuddOptiply2000() {
		fail("Not yet implemented");
	}

	public void testRuddOptiply2000Int() {
		fail("Not yet implemented");
	}*/

	/*public void testAdjustTemperature() {
		fail("Not yet implemented");
	}

	public void testHeater() {
		fail("Not yet implemented");
	}*/

	/*public void testHeaterIntStringString() {
		fail("Not yet implemented");
	}

	public void testAdd() {
		fail("Not yet implemented");
	}

	public void testClimateComponent() {
		fail("Not yet implemented");
	}

	public void testClimateComponentIntStringString() {
		fail("Not yet implemented");
	}*/

	public void testGetTemperatureSetting() {
		RuddOptiply2000 toTest = new RuddOptiply2000(1234);
		assertEquals(toTest.getAmbientTemperature(), 70.0);
	}

	public void testGetAmbientTemperature() {
		RuddOptiply2000 toTest = new RuddOptiply2000(1234);
		assertEquals(toTest.getAmbientTemperature(), 70.0);
	}

	/*public void testHomeComponent() {
		fail("Not yet implemented");
	}

	public void testHomeComponentIntStringString() {
		fail("Not yet implemented");
	}*/

	public void testIsPoweredOn() {
		RuddOptiply2000 toTest = new RuddOptiply2000(1234);
		assertEquals(toTest.isPoweredOn( ), false);
		toTest.powerOn( );
		assertEquals(toTest.isPoweredOn( ), true);
		toTest.powerOff( );
		assertEquals(toTest.isPoweredOn( ), false);
	}

	public void testGetMake() {
		RuddOptiply2000 toTest = new RuddOptiply2000(1234);
		assertEquals(toTest.getMake( ), "Rudd Optiply");
	}

	public void testGetModel() {
		RuddOptiply2000 toTest = new RuddOptiply2000(1234);
		assertEquals(toTest.getModel( ), "2000");
	}

	public void testGetSerialNumber() {
		RuddOptiply2000 toTest = new RuddOptiply2000(1234);
		assertEquals(toTest.getSerialNumber( ), 1234);
	}

	public void testGetStatusMessage() {
		RuddOptiply2000 toTest = new RuddOptiply2000(1234);
		toTest.setStatusMessage("Test", HomeComponent.LogLevel.SEVERE);
		assertEquals(toTest.getStatusMessage(), "SEVERE: Test");
	}

	public void testSetStatusMessage() {
		RuddOptiply2000 toTest = new RuddOptiply2000(1234);
		toTest.setStatusMessage("Test", HomeComponent.LogLevel.SEVERE);
		assertEquals(toTest.getStatusMessage(), "SEVERE: Test");
	}

	/*public void testSetIcon() {
		fail("Not yet implemented");
	}*/

}
