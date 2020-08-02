package it.unibs.ing.domohouse.view2;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UserFormPanel extends JPanel {
	private JTextField textField;
	private JButton btnInsert;
	private JButton btnCancel;

	/**
	 * Create the panel.
	 */
	public UserFormPanel() {
		setLayout(new MigLayout("", "[][grow, center]", "[][]"));
		
		JLabel lblNewLabel = new JLabel("Nome:");
		add(lblNewLabel, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		add(textField, "cell 1 0,growx");
		textField.setColumns(10);
		
		btnCancel = new JButton("Annulla");
		add(btnCancel, "cell 0 1");
		
		btnInsert = new JButton("Inserisci");
		add(btnInsert, "cell 1 1");

	}
	
	public String getUserName() {
		return textField.getText();
	}
	
	public void addCancelListener(ActionListener l) {
		btnCancel.addActionListener(l);
	}
	
	public void addInsertListener(ActionListener l) {
		btnInsert.addActionListener(l);
	}
	
	public void showErrorPopup(String errorText) {
		JOptionPane.showMessageDialog(this, errorText, "Errore", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showInfoPopup(String errorText) {
		JOptionPane.showMessageDialog(this, errorText, "Avviso", JOptionPane.INFORMATION_MESSAGE);
	}
}
