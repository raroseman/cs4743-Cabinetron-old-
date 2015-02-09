package assignment2;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PartView extends JFrame {
	private JPanel partFrame;
	private JButton cancel, ok, edit, save;
	private JLabel partName, partNumber, partVendor, partQuantity, partID, errorMessage;
	private JTextField nameField, numberField, vendorField, quantityField, idField;
	
	public PartView(String title) {
		super(title);

			this.setSize(340, 270);
			this.setVisible(true);
			this.setLocation(900, 250);
			
			partFrame = new JPanel();
			partFrame.setBackground(Color.LIGHT_GRAY);
			partFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(partFrame);
			partFrame.setLayout(null);
			
			partName = new JLabel("Name");
			partName.setBounds(15, 15, 70, 30);
			partFrame.add(partName);
			
			partID = new JLabel("ID");
			partID.setBounds(15, 45, 70, 30);
			partFrame.add(partID);
			
			partNumber = new JLabel("#");
			partNumber.setBounds(15, 75, 70, 30);
			partFrame.add(partNumber);
			
			partVendor = new JLabel("Vendor");
			partVendor.setBounds(15, 105, 70, 30);
			partFrame.add(partVendor);
			
			partQuantity = new JLabel("Quantity");
			partQuantity.setBounds(15, 135, 70, 30);
			partFrame.add(partQuantity);
			
			errorMessage = new JLabel("");
			errorMessage.setForeground(Color.red);
			errorMessage.setBounds(15, 200, 280, 30);
			partFrame.add(errorMessage);
			
			cancel = new JButton("Cancel");
			cancel.setBounds(225, 180, 75, 25);
			partFrame.add(cancel);
			
			ok = new JButton("OK");
			ok.setBounds(155, 180, 70, 25);
			partFrame.add(ok);
			
			edit = new JButton("Edit");
			edit.setBounds(155, 180, 70, 25);
			partFrame.add(edit);
			
			save = new JButton("Save");
			save.setBounds(155, 180, 70, 25);
			partFrame.add(save);
			
			nameField = new JTextField();
			nameField.setBounds(100, 20, 200, 20);
			partFrame.add(nameField);
			
			idField = new JTextField();
			idField.setBounds(100, 50, 200, 20);
			partFrame.add(idField);
			
			numberField = new JTextField();
			numberField.setBounds(100, 80, 200, 20);
			partFrame.add(numberField);
			
			vendorField = new JTextField();
			vendorField.setBounds(100, 110, 200, 20);
			partFrame.add(vendorField);
			
			quantityField = new JTextField();
			quantityField.setBounds(100, 140, 200, 20);
			partFrame.add(quantityField);
	}
	
	public void register(PartsInventoryController controller) {
		ok.addActionListener(controller);
		cancel.addActionListener(controller);
		edit.addActionListener(controller);
		save.addActionListener(controller);
	}
	
	public String getName() {
		return nameField.getText();
	}
	
	public Integer getID() throws NumberFormatException {
		Integer i = 0;
		try {
			i = Integer.parseInt(idField.getText().trim());
			return i;
		}
		catch (NumberFormatException nfe) {
			throw new NumberFormatException("Error: id must be in the form of an integer.");
		}
	}
	public String getNumber() {
		return numberField.getText();
	}
	
	public String getVendor() {
		return vendorField.getText();
	}
	
	public Integer getQuantity() throws NumberFormatException {
		Integer i = 0;
		try {
			i = Integer.parseInt(quantityField.getText().trim());
			return i;
		}
		catch (NumberFormatException nfe) {
			throw new NumberFormatException("Error: quantity must be in the form of an integer.");
		}
	}
	
	public void setErrorMessage(String error) {
		errorMessage.setText(error);
	}
	
	public void setName(String name) {
		nameField.setText(name);
	}
	
	public void setID(Integer id) {
		idField.setText(String.valueOf(id));
	}
	
	public void setNumber(String number) {
		numberField.setText(number);
	}
	
	public void setVendor(String vendor) {
		vendorField.setText(vendor);
	}
	
	public void setQuantity(Integer quantity) {
		quantityField.setText(String.valueOf(quantity));
	}
	
	public void hideEditButton() {
		edit.setVisible(false);
	}
	
	public void hideSaveButton() {
		save.setVisible(false);
	}
	
	public void disableIDEdit() {
		idField.setEnabled(false);
	}
	
	public void disableEditable() {
		ok.setVisible(false);
		save.setVisible(false);
		nameField.setEnabled(false);
		idField.setEnabled(false);
		numberField.setEnabled(false);
		vendorField.setEnabled(false);
		quantityField.setEnabled(false);
	}
	
	public void enableEditable() {
		save.setVisible(true);
		nameField.setEnabled(true);
		numberField.setEnabled(true);
		vendorField.setEnabled(true);
		quantityField.setEnabled(true);
	}
}
