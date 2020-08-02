package it.unibs.ing.domohouse.view2;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MaintainerLoginPanel extends JPanel {
	private JTextField userTextField;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JButton btnExit;
	private JButton btnOption;

	/**
	 * Create the panel.
	 */
	public MaintainerLoginPanel() {
		setLayout(new MigLayout("", "[right][grow,center][left]", "[][][][][][]"));
		
		JLabel lblNewLabel_2 = new JLabel("Domohouse");
		add(lblNewLabel_2, "cell 1 0");
		
		JLabel lblNewLabel = new JLabel("Nome");
		add(lblNewLabel, "cell 0 1,alignx trailing");
		
		userTextField = new JTextField();
		add(userTextField, "cell 1 1,growx");
		userTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		add(lblNewLabel_1, "cell 0 2,alignx trailing");
		
		passwordField = new JPasswordField();
		add(passwordField, "cell 1 2,growx");
		
		btnLogin = new JButton("Login");
		add(btnLogin, "cell 1 3");
		
		btnExit = new JButton("Esci");
		add(btnExit, "cell 1 4");
		
		btnOption = new JButton("Opzioni");
		add(btnOption, "cell 2 5");
	}
	
	public void addLoginListener(ActionListener l) {
		btnLogin.addActionListener(l);
	}
	
	public void addExitListener(ActionListener l) {
		btnExit.addActionListener(l);
	}
	
	public void addOptionListener(ActionListener l) {
		btnOption.addActionListener(l);
	}

	public void showErrorPopup(String errorText) {
		JOptionPane.showMessageDialog(this, errorText, "Errore", JOptionPane.ERROR_MESSAGE);
	}

	public String getUserFieldText() {
		return userTextField.getText();
	}
	
	public String getPasswordFieldText() {
		return new String(passwordField.getPassword());
	}
}
