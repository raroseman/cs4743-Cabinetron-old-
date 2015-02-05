package assignment2;

import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PartsInventoryPanel extends JPanel {
	
	PartsInventoryModel model;
	PartsInventoryView view;
	
	public PartsInventoryPanel(PartsInventoryModel model, PartsInventoryView view) {
		super();
		this.setSize(view.getWidth(), view.getHeight());
		this.setBackground(Color.white);
		this.setVisible(true);
		this.setFocusable(true);
	}
	
	public PartsInventoryPanel(PartsInventoryModel model, PartView view) {
		super();
		this.setSize(view.getWidth(), view.getHeight());
		this.setBackground(Color.white);
		this.setVisible(true);
		this.setFocusable(true);
	}
}
