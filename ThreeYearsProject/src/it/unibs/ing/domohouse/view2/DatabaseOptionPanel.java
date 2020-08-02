package it.unibs.ing.domohouse.view2;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class DatabaseOptionPanel extends JPanel {
	private JPasswordField passwordTextField;
	private JTextField databaseNameTextField;
	private JTextField userTextField;
	private JTextField portTextField;
	private JTextField ipTextField;
	private JButton btnCancel;
	private JButton btnSave;

	/**
	 * Create the panel.
	 */
	public DatabaseOptionPanel() {
		setLayout(new MigLayout("", "[][grow,center][grow,right]", "[][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Indirizzo IP:");
		add(lblNewLabel, "cell 0 0,alignx trailing");
		
		ipTextField = new JTextField();
		add(ipTextField, "cell 1 0 2 1,growx");
		ipTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Porta:");
		add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		portTextField = new JTextField();
		add(portTextField, "cell 1 1 2 1,growx");
		portTextField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Nome utente:");
		add(lblNewLabel_2, "cell 0 2,alignx trailing");
		
		userTextField = new JTextField();
		add(userTextField, "cell 1 2 2 1,growx");
		userTextField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Password:");
		add(lblNewLabel_3, "cell 0 3,alignx trailing");
		
		passwordTextField = new JPasswordField();
		add(passwordTextField, "cell 1 3 2 1,growx");
		
		JLabel lblNewLabel_4 = new JLabel("Nome database:");
		add(lblNewLabel_4, "cell 0 4,alignx trailing");
		
		databaseNameTextField = new JTextField();
		add(databaseNameTextField, "cell 1 4 2 1,growx");
		databaseNameTextField.setColumns(10);
		
		btnCancel = new JButton("Annulla");
		add(btnCancel, "flowx,cell 1 5 2 1");
		
		btnSave = new JButton("Salva");
		add(btnSave, "cell 2 5");
	}

	public void addCancelListener(ActionListener l) {
		btnCancel.addActionListener(l);
	}
	
	public void addSaveListener(ActionListener l) {
		btnSave.addActionListener(l);
	}
	
	public String getIP() {
		return ipTextField.getText();
	}
	
	public String getPort() {
		return portTextField.getText();
	}
	
	public String getUserName() {
		return userTextField.getText();
	}
	
	public String getPassword() {
		return new String(passwordTextField.getPassword());
	}
	
	public String getDatabaseName() {
		return databaseNameTextField.getText();
	}
	
	public void setIP(String ip) {
		ipTextField.setText(ip);
	}
	
	public void setPort(String port) {
		portTextField.setText(port);
	}
	
	public void setUserName(String userName) {
		userTextField.setText(userName);
	}
	
	public void setPassword(String password) {
		passwordTextField.setText(password);
	}
	
	public void setDatabaseName(String dbName) {
		databaseNameTextField.setText(dbName);
	}
	
	public void showErrorPopup(String errorText) {
		JOptionPane.showMessageDialog(this, errorText, "Errore", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showInfoPopup(String errorText) {
		JOptionPane.showMessageDialog(this, errorText, "Avviso", JOptionPane.INFORMATION_MESSAGE);
	}
}
