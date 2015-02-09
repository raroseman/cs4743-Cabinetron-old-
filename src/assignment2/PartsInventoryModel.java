package assignment2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PartsInventoryModel {
	private List<Part> partsInventory;
	private Comparator<Part> sortingMode = Part.PartNameDescending; // default sort
	
	public PartsInventoryModel() {
		partsInventory = new ArrayList<Part>();
	}
	
	public void addPart(Part p) throws Exception {
		try {
			addPart(p.getID(), p.getQuantity(), p.getQuantityUnitType(), p.getPartName(), p.getPartNumber(), p.getVendor());
		}
		catch (IOException e) {
			throw new IOException(e.getMessage());
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void addPart(Integer id, Integer quantity, String unitOfQuantity, String partName, String partNumber) throws Exception {
		try {
			addPart(id, quantity, unitOfQuantity, partName, partNumber, "");
		}
		catch (IOException e) {
			throw new IOException(e.getMessage());
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void addPart(Integer id, Integer quantity, String unitOfQuantity, String partName, String partNumber, String vendor) throws Exception, IOException {
		if (quantity <= 0) {
			throw new IOException("A new item requires quantity greater than zero.");
		}
		try {
			Part p = new Part(id, quantity, unitOfQuantity, partName, partNumber, vendor);
			if (findPartName(p.getPartName()) != null) {
				throw new Exception("Part name \"" + p.getPartName() + "\" is already listed in inventory.");
			}
			partsInventory.add(p);
		}
		catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	}
	
	public void deletePart(Part p) {
		partsInventory.remove(p); // if it exists, first instance (unique, only one entry) is removed. otherwise does nothing
	}
	
	public void deletePart(String partName) {
		Part p = findPartName(partName);
		if (p != null) {
			partsInventory.remove(p); // if it exists, first instance (unique, only one entry) is removed. otherwise does nothing
		}
	}
	
	public void editPart(Part partOld, Part partNew) throws Exception {
		int index = partsInventory.indexOf(partOld);
		//if (index == -1) {
		//	throw new Exception("Error: the old part, " + partOld.getPartName() + " cannot be edited as it is not listed in inventory.");
		//}
		
		// If the item being edited did not originally have the new part name AND the new part name is already taken, throw an error
		// Otherwise, the name remains the same, and it should be OK to keep
		if (!partOld.getPartName().equals(partNew.getPartName()) && findPartName(partNew.getPartName()) != null) {
			//throw new Exception("Part name \"" + partNew.getPartName() + "\" is already listed in inventory.");
			throw new Exception("Error: part name already exists in the inventory.");
		} else {
			partsInventory.set(index, partNew);
		}
	}
	
	public void editPart(Part partOld, int newID, int newQuantity, String newQuantityUnitType, String newName, String newPartNumber, String newVendor) throws Exception {
		int index = partsInventory.indexOf(partOld);
	//	if (index == -1) {
	//		throw new Exception("Error: the old part, " + partOld.getPartName() + " cannot be edited as it is not listed in inventory.");
	//	}
		
		if (!partOld.getPartName().equals(newName) && findPartName(newName) != null) {
			//throw new Exception("Part name \"" + newName + "\" is already listed in inventory.");
			throw new Exception("Error: part name already exists in the inventory.");
		} else {
			Part newPart = new Part(newID, newQuantity, newQuantityUnitType, newName, newPartNumber, newVendor);
			partsInventory.set(index, newPart);
		}
	}
	
	public Part findPartName(String partName) {
		if (partName.length() > Part.getMaxPartNameLength()) {
			partName = partName.substring(0, Part.getMaxPartNameLength()); // maybe just throw length exceeded exception...
		}
		for (Part part : partsInventory) { // this is O(n)
			if (part.getPartName().equals(partName)) {
				return part;
			}
		}
		return null;
	}
	
	public Part findPartNumber(String partNumber) {
		if (partNumber.length() > Part.getMaxPartNumberLength()) {
			partNumber = partNumber.substring(0, Part.getMaxPartNumberLength());
		}
		for (Part part : partsInventory) { // this is O(n)
			if (part.getPartNumber().equals(partNumber)) {
				return part;
			}
		}
		return null;
	}
	
	public void printInventory() { // for debugging, console, or file output
		String horizontalSeparator = "";
		int recordNum = 1;
		for (int i = 0; i < 80; i++) {
			horizontalSeparator += '-';
		}
		System.out.printf("%8s   %17s   %17s   %8s   %17s\n",
				"Record #", "Part #", "Part Name", "Quantity", "Quantity Unit Type", "Vendor");
		System.out.println(horizontalSeparator);
		for (Part part : partsInventory) {
			System.out.printf("%8s | %17s | %17s | %8s | %17s\n",
					recordNum++, part.getPartNumber(), part.getPartName(), part.getQuantity(), part.getQuantityUnitType(), part.getVendor());
			System.out.println(horizontalSeparator);
		}
	}
	
	public int getSize() {
		return partsInventory.size();
	}
	
	public List<Part> getInventory() { // for GUI output
		return partsInventory;
	}
	
	public String[] getValidQuantityUnitTypes() {
		return Part.getValidQuantityUnitTypes();
	}
	
	public void sortByQuantity() {
		if (sortingMode == Part.QuantityDescending) {
			sortingMode = Part.QuantityAscending;
		}
		else {
			sortingMode = Part.QuantityDescending;
		}
		partsInventory.sort(sortingMode);
	}
	
	public void sortByQuantityUnitType() {
		if (sortingMode == Part.QuantityUnitTypeDescending) {
			sortingMode = Part.QuantityUnitTypeAscending;
		}
		else {
			sortingMode = Part.QuantityUnitTypeDescending;
		}
		partsInventory.sort(sortingMode);
	}
	
	public void sortByPartName() {
		if (sortingMode == Part.PartNameDescending) {
			sortingMode = Part.PartNameAscending;
		}
		else {
			sortingMode = Part.PartNameDescending;
		}
		partsInventory.sort(sortingMode);
	}
	
	public void sortByPartNumber() {
		if (sortingMode == Part.PartNumberDescending) {
			sortingMode = Part.PartNumberAscending;
		}
		else {
			sortingMode = Part.PartNumberDescending;
		}
		partsInventory.sort(sortingMode);
	}
	
	public void sortByVendor() {
		if (sortingMode == Part.VendorDescending) {
			sortingMode = Part.VendorAscending;
		}
		else {
			sortingMode = Part.VendorDescending;
		}
		partsInventory.sort(sortingMode);
	}
	
}
