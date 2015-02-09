package assignment2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PartsInventoryController implements ActionListener, ListSelectionListener {
	private PartsInventoryModel partsInventoryModel;
	private PartsInventoryView inventoryView;
	private PartView partView;
	private JButton lastButtonClicked;
	private Color lastButtonOriginalColor;
	private Color highlightedColor = new Color(255, 255, 153);
	private Part selectedPart = null;
	private boolean hasPartViewOpen;
	
	public PartsInventoryController(PartsInventoryModel inventoryModel, PartsInventoryView inventoryView) {
		lastButtonClicked = null;
		this.partsInventoryModel = inventoryModel;
		this.inventoryView = inventoryView;
		hasPartViewOpen = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) throws NumberFormatException {
		String command = e.getActionCommand();
		
		e.paramString();
		
		switch(command) {
			case "Add": 
				if (hasPartViewOpen) {
					partView.dispose();
				}
				ClearSelection();
				partView = new PartView(partsInventoryModel, "Add New Part");
				partView.register(this);
				partView.hideSaveButton();
				partView.hideEditButton();
				hasPartViewOpen = true;
				break;
			case "Delete":
				if (selectedPart != null) {
					if (hasPartViewOpen) {
						partView.dispose();
						hasPartViewOpen = false;
					}
					partsInventoryModel.deletePart(selectedPart);
					ClearSelection();
					inventoryView.updatePanel();
					inventoryView.repaint();
				}
				break;
			case "View":
				if (hasPartViewOpen) {
					partView.dispose();
				}
				if (selectedPart != null) {
					inventoryView.disableDelete();
					inventoryView.disableView();
					partView = new PartView(partsInventoryModel, "View/Edit Part: " + selectedPart.getPartName());
					partView.register(this);
					partView.disableEditable();
					partView.setName(selectedPart.getPartName());
					partView.setNumber(selectedPart.getPartNumber());
					partView.setVendor(selectedPart.getVendor());
					partView.setQuantity(selectedPart.getQuantity());
					partView.setQuantityUnitType(selectedPart.getQuantityUnitType());
					inventoryView.updatePanel();
					inventoryView.repaint();
					hasPartViewOpen = true;
				}
				break;
			case "Edit":
				partView.enableEditable();
				partView.hideEditButton();
				partView.repaint();
				break;
			case "Save":
				if (selectedPart != null) {
					try {
						Part newPart = new Part(partView.getQuantity(), partView.getQuantityUnitType(), partView.getName(), partView.getNumber(), partView.getVendor());
						partsInventoryModel.editPart(selectedPart, newPart);
						partView.dispose();
						inventoryView.updatePanel();
						inventoryView.repaint();
					} catch (NumberFormatException noint) {
						partView.setErrorMessage(noint.getMessage());
					} catch (IOException ex) {
						partView.setErrorMessage(ex.getMessage());
					} catch (Exception e1) {
						partView.setErrorMessage(e1.getMessage());
					} 				
				}	
				break;
			case "OK":
				try {
					Part part = new Part(partView.getQuantity(), partView.getQuantityUnitType(), partView.getName(), partView.getNumber(), partView.getVendor());		
					partsInventoryModel.addPart(part);
					partView.dispose();
					inventoryView.updatePanel();
					inventoryView.repaint();
				}
				catch (NumberFormatException noint) {
					partView.setErrorMessage(noint.getMessage());
				}
				catch (IOException ioex) {
					partView.setErrorMessage(ioex.getMessage());
				}
				catch (Exception ex) {
					partView.setErrorMessage(ex.getMessage());
				}
				break;
			case "Cancel":
				partView.dispose();
				break;
			case "Part Name":
				ChangeButtonColors(e);
				partsInventoryModel.sortByPartName();
				inventoryView.updatePanel();
				inventoryView.repaint();
				break;
			case "Part #":
				ChangeButtonColors(e);
				partsInventoryModel.sortByPartNumber();
				inventoryView.updatePanel();
				inventoryView.repaint();
				break;
			case "Vendor":
				ChangeButtonColors(e);
				partsInventoryModel.sortByVendor();
				inventoryView.updatePanel();
				inventoryView.repaint();
				break;
			case "Quantity":
				ChangeButtonColors(e);
				partsInventoryModel.sortByQuantity();
				inventoryView.updatePanel();
				inventoryView.repaint();
				break;
				
		}
	}
	
	private void ClearSelection() {
		selectedPart = null;
		inventoryView.disableDelete();
		inventoryView.disableView();
	}
	
	// restores previous button clicked to its original color; changes color of current button clicked 
	private void ChangeButtonColors(ActionEvent e) {
		if (lastButtonClicked != null) {
			lastButtonClicked.setBackground(lastButtonOriginalColor);
		}
		lastButtonClicked = (JButton) e.getSource();
		lastButtonOriginalColor = lastButtonClicked.getBackground();
		lastButtonClicked.setBackground(highlightedColor); // indicates the most recently clicked button
	}

	// When the user clicks on an element in the inventory list, event is triggered: gets Part from index of list element
	@Override
	public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();
		if (lsm.isSelectionEmpty()) {
			return;
		}
		else {
			int selectedRow = lsm.getMinSelectionIndex();	
			selectedPart = inventoryView.getObjectInRow(selectedRow);
			inventoryView.enableDelete();
			inventoryView.enableView();
		}
	}
}