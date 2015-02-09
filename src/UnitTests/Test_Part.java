package UnitTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import assignment2.Part;

public class Test_Part {
	Integer id;
	Integer quantity;
	String unitOfQuantity;
	String partName;
	String partNumber;
	String vendor;
	String location;
	Part p;
	
	@Before 
	public void setUp() {	
		id = 1;
		quantity = 2;
		unitOfQuantity = "Pieces";
		partName = "The Part Name v1.0";
		partNumber = "18J-2015A1";
		vendor = "The_Vendor @ 1 UTSA Cir";
		location = "Facility 2";
	}
	
	@Test
	public void testPartCreation_NoVendor() {
		try {
			p = new Part(id, quantity, unitOfQuantity, partName, partNumber, location);
			assertTrue(p.getQuantity() == quantity);
			assertTrue(p.getQuantityUnitType().equals(unitOfQuantity));
			assertTrue(p.getPartName().equals(partName));
			assertTrue(p.getPartNumber().equals(partNumber));
			assertTrue(p.getVendor().equals(""));
			assertTrue(p.getLocation().equals(location));
		}
		catch (IOException e) {
			fail("Exception thrown during unexceptional part creation: \n\t" + e);
		}	
	}

	@Test
	public void testPartCreation_WithVendor() {
		try {
			p = new Part(id, quantity, unitOfQuantity, partName, partNumber, vendor, location);
			assertTrue(p.getQuantity() == quantity);
			assertTrue(p.getQuantityUnitType().equals(unitOfQuantity));
			assertTrue(p.getPartName().equals(partName));
			assertTrue(p.getPartNumber().equals(partNumber));
			assertTrue(p.getVendor().equals(vendor));
			assertTrue(p.getLocation().equals(location));
		}
		catch (IOException e) {
			fail("Exception thrown during unexceptional part creation.");
		}	
	}
	
	@Test (expected = IOException.class)
	public void testPartCreation_QuantityError() throws IOException {
		Integer badQuantity = -1;
		try {
			p = new Part(id, badQuantity, unitOfQuantity, partName, partNumber, location);
			fail("Should have thrown an exception: quantity is: " + 
					badQuantity + "and was set as " + p.getQuantity());
		}
		catch (IOException e) {
			throw new IOException(e); // expected behavior - should fail if no exception caught
		}
	}
	
	@Test (expected = IOException.class)
	public void testPartCreation_UnitOfQuantityError_Unrecognized() throws IOException {
		String badUnit = "Feet";
		try {
			p = new Part(id, quantity, badUnit, partName, partNumber, location);
			fail("Should have thrown an exception: unit type of quantity is: " + 
					badUnit + "and was set as " + p.getQuantityUnitType());
		}
		catch (IOException e) {
			throw new IOException(e); // expected behavior - should fail if no exception caught
		}
	}
	
	@Test (expected = IOException.class)
	public void testPartCreation_UnitOfQuantityError_Unknown() throws IOException {
		String badUnit = "Unknown";
		try {
			p = new Part(id, quantity, badUnit, partName, partNumber, location);
			fail("Should have thrown an exception: unit type of quantity is: " + 
					badUnit + "and was set as " + p.getQuantityUnitType());
		}
		catch (IOException e) {
			throw new IOException(e); // expected behavior - should fail if no exception caught
		}
	}
	
	@Test (expected = IOException.class)
	public void testPartCreation_UnitOfQuantityError_Empty() throws IOException {
		String badUnit = "";
		try {
			p = new Part(id, quantity, badUnit, partName, partNumber, location);
			fail("Should have thrown an exception: unit type of quantity is: " + 
					badUnit + "and was set as " + p.getQuantityUnitType());
		}
		catch (IOException e) {
			throw new IOException(e); // expected behavior - should fail if no exception caught
		}
	}
	
	@Test
	public void testPartCreation_PartNameLengthMax() {
		String longPartName = "";
		for (int i = 0; i < Part.getMaxPartNameLength(); i++) {
			longPartName = longPartName + "A"; // add one letter to the string
		}
		try {
			p = new Part(id, quantity, unitOfQuantity, longPartName, partNumber, location);
		}
		catch (IOException e) {
			fail("Exception thrown during unexceptional part creation: partName length is: " + 
					longPartName.length() + " and limit is: " + Part.getMaxPartNameLength());
		}
	}
	
	@Test (expected = IOException.class)
	public void testPartCreation_PartNameLengthExceeded() throws IOException {
		String longPartName = "";
		for (int i = 0; i < Part.getMaxPartNameLength() + 1; i++) {
			longPartName = longPartName + "A"; // add one letter to the string
		}
		try {
			p = new Part(id, quantity, unitOfQuantity, longPartName, partNumber, location);
			fail("Should have thrown an exception: partName length is: " + 
					p.getPartName().length() + " and limit is: " + Part.getMaxPartNameLength());
		}
		catch (IOException e) {
			throw new IOException(e); // expected behavior - should fail if no exception caught
		}
	}
	
	@Test
	public void testPartCreation_PartNumberLengthMax() {
		String longPartNumber = "";
		for (int i = 0; i < Part.getMaxPartNumberLength(); i++) {
			longPartNumber = longPartNumber + "A"; // add one letter to the string
		}
		try {
			p = new Part(id, quantity, unitOfQuantity, partName, longPartNumber, location);
		}
		catch (IOException e) {
			fail("Exception thrown during unexceptional part creation: partName length is: " + 
					longPartNumber.length() + " and limit is: " + Part.getMaxPartNumberLength());
		}
	}
	
	@Test (expected = IOException.class)
	public void testPartCreation_PartNumberLengthExceeded() throws IOException {
		String longPartNumber = "";
		for (int i = 0; i < Part.getMaxPartNumberLength() + 1; i++) {
			longPartNumber = longPartNumber + "A"; // add one letter to the string
		}
		try {
			p = new Part(id, quantity, unitOfQuantity, partName, longPartNumber, location);
			fail("Should have thrown an exception: partName length is: " + 
					p.getPartName().length() + " and limit is: " + Part.getMaxPartNumberLength());
		}
		catch (IOException e) {
			throw new IOException(e); // expected behavior - should fail if no exception caught
		}
	}
	
	@Test
	public void testPartCreation_VendorLengthMax() {
		String longVendor = "";
		for (int i = 0; i < Part.getMaxVendorLength(); i++) {
			longVendor = longVendor + "A"; // add one letter to the string
		}
		try {
			p = new Part(id, quantity, unitOfQuantity, partName, partNumber, longVendor, location);
		}
		catch (IOException e) {
			fail("Exception thrown during unexceptional part creation: vendor length is: " + 
					longVendor.length() + " and limit is: " + Part.getMaxVendorLength());
		}
	}
	
	@Test (expected = IOException.class)
	public void testPartCreation_VendorLengthExceeded() throws IOException {
		String longVendor = "";
		for (int i = 0; i < Part.getMaxVendorLength() + 1; i++) {
			longVendor = longVendor + "A"; // add one letter to the string
		}
		try {
			p = new Part(id, quantity, unitOfQuantity, partName, partNumber, longVendor, location);
			fail("Should have thrown an exception: vendor length is: " + 
					p.getVendor().length() + " and limit is: " + Part.getMaxVendorLength());
		}
		catch (IOException e) {
			throw new IOException(e); // expected behavior - should fail if no exception caught
		}
	}
	
	@Test (expected = IOException.class)
	public void testPartCreation_LocationError_Unrecognized() throws IOException {
		String badLocation = "Route 66";
		try {
			p = new Part(id, quantity, unitOfQuantity, partName, partNumber, badLocation);
			fail("Should have thrown an exception: location is unrecognized: " + 
					badLocation + "and was set as " + p.getLocation());
		}
		catch (IOException e) {
			throw new IOException(e); // expected behavior - should fail if no exception caught
		}
	}
	
	@Test (expected = IOException.class)
	public void testPartCreation_LocationError_Unknown() throws IOException {
		String badLocation = "Unknown";
		try {
			p = new Part(id, quantity, unitOfQuantity, partName, partNumber, badLocation);
			fail("Should have thrown an exception: location is unknown: " + 
					badLocation + "and was set as " + p.getLocation());
		}
		catch (IOException e) {
			throw new IOException(e); // expected behavior - should fail if no exception caught
		}
	}
	
	@Test (expected = IOException.class)
	public void testPartCreation_LocationError_Empty() throws IOException {
		String badLocation = "";
		try {
			p = new Part(id, quantity, unitOfQuantity, partName, partNumber, badLocation);
			fail("Should have thrown an exception: location is required: " + 
					badLocation + "and was set as " + p.getLocation());
		}
		catch (IOException e) {
			throw new IOException(e); // expected behavior - should fail if no exception caught
		}
	}
}
