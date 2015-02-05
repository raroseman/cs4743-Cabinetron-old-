package assignment2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PartsInventoryController implements ActionListener, ListSelectionListener {
	private PartsInventoryModel partsInventoryModel;
	private PartsInventoryView inventoryView;
	private PartView partView;
	private JButton lastButtonClicked;
	private Color lastButtonOriginalColor;
	private Color highlightedColor = new Color(255, 255, 153);
	private List<Part> selectedParts = null;
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
					partView = new PartView("Add New Part");
					partView.register(this);
					partView.hideSaveButton();
					partView.hideEditButton();
					hasPartViewOpen = true;
					break;
				case "Delete":
					if (selectedParts != null) {
						if (hasPartViewOpen) {
							partView.dispose();
							hasPartViewOpen = false;
						}
						for (Part p : selectedParts) {
							partsInventoryModel.deletePart(p);
						}
						ClearSelection();
						inventoryView.updatePanel();
						inventoryView.repaint();
					}
					break;
				case "View":
					if (hasPartViewOpen) {
						partView.dispose();
					}
					if (selectedParts != null) {
						inventoryView.disableDelete();
						inventoryView.disableView();
						for (Part p : selectedParts) {
							partView = new PartView("View/Edit Part: " + p.getPartName());
							partView.register(this);
							partView.disableEditable();
							partView.setName(p.getPartName());
							partView.setNumber(p.getPartNumber());
							partView.setVendor(p.getVendor());
							partView.setQuantity(p.getQuantity());
						}
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
					if (selectedParts != null) {
						for (Part p : selectedParts) {
							try {
								Part newPart = new Part(partView.getQuantity(), partView.getName(), partView.getNumber(), partView.getVendor());
								partsInventoryModel.editPart(p, newPart);
								partView.dispose();
								inventoryView.updatePanel();
								inventoryView.repaint();
								ClearSelection();
							} catch (NumberFormatException noint) {
								partView.setErrorMessage(noint.getMessage());
							} catch (IOException ex) {
								partView.setErrorMessage(ex.getMessage());
							} catch (Exception e1) {
								partView.setErrorMessage(e1.getMessage());
							} 
						}					
					}	
					break;
				case "OK":
					try {
						Part part = new Part(partView.getQuantity(), partView.getName(), partView.getNumber(), partView.getVendor());		
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
					selectedParts = null;
					break;
				case "Part Name":
					ChangeButtonColors(e);
					partsInventoryModel.sortByPartName();
					ClearSelection();
					inventoryView.updatePanel();
					inventoryView.repaint();
					break;
				case "Part #":
					ChangeButtonColors(e);
					partsInventoryModel.sortByPartNumber();
					ClearSelection();
					inventoryView.updatePanel();
					inventoryView.repaint();
					break;
				case "Vendor":
					ChangeButtonColors(e);
					partsInventoryModel.sortByVendor();
					ClearSelection();
					inventoryView.updatePanel();
					inventoryView.repaint();
					break;
				case "Quantity":
					ChangeButtonColors(e);
					partsInventoryModel.sortByQuantity();
					ClearSelection();
					inventoryView.updatePanel();
					inventoryView.repaint();
					break;
					
			}
		}
		
		private void ClearSelection() {
			selectedParts = null;
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
			JList<Part> j;
			if (e.getSource() instanceof JList<?>) {
				j = (JList<Part>) e.getSource();
				if (j.getSelectedValue() != null) {
					selectedParts = j.getSelectedValuesList();
					inventoryView.enableDelete();
					inventoryView.enableView();
				}		
			}
		}
}
