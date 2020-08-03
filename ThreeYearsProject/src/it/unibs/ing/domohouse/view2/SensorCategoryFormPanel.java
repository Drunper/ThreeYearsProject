package it.unibs.ing.domohouse.view2;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

public class SensorCategoryFormPanel extends JPanel {
	private JTextField nameTextField;
	private JTextField abbreviationTextField;
	private JTextField manufacturerTextField;
	private JScrollPane scrollPane;
	private JButton btnAddInfo;
	private JButton btnBack;
	private JLabel lblNewLabel_3;
	private JList<String> nonNumericInfoList;
	private JList<String> numericInfoList;
	private JButton btnInsert;
	private JScrollPane scrollPane_1;
	private JLabel lblNewLabel_4;

	/**
	 * Create the panel.
	 */
	public SensorCategoryFormPanel() {
		setLayout(new MigLayout("", "[][grow][grow]", "[][grow][][grow][][][]"));
		
		JLabel lblNewLabel = new JLabel("Nome");
		add(lblNewLabel, "cell 0 0,alignx trailing");
		
		nameTextField = new JTextField();
		add(nameTextField, "cell 1 0,growx");
		nameTextField.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Selezione informazioni numeriche");
		add(lblNewLabel_3, "cell 2 0");
		
		scrollPane_1 = new JScrollPane();
		add(scrollPane_1, "cell 2 1,grow");
		
		JLabel lblNewLabel_1 = new JLabel("Sigla");
		add(lblNewLabel_1, "cell 0 2,alignx trailing");
		
		abbreviationTextField = new JTextField();
		add(abbreviationTextField, "cell 1 2,growx");
		abbreviationTextField.setColumns(10);
		
		lblNewLabel_4 = new JLabel("Selezione informazioni non numeriche");
		add(lblNewLabel_4, "cell 2 2");
		
		scrollPane = new JScrollPane();
		add(scrollPane, "cell 2 3,grow");
		
		JLabel lblNewLabel_2 = new JLabel("Costruttore");
		add(lblNewLabel_2, "cell 0 4,alignx trailing");
		
		manufacturerTextField = new JTextField();
		add(manufacturerTextField, "cell 1 4,growx");
		manufacturerTextField.setColumns(10);
		
		btnAddInfo = new JButton("Aggiungi informazione");
		add(btnAddInfo, "cell 2 4");
		
		btnInsert = new JButton("Inserisci");
		add(btnInsert, "cell 2 5");
		
		btnBack = new JButton("Indietro");
		add(btnBack, "cell 2 6");
	}
	
	public void addInsertListener(ActionListener l) {
		btnInsert.addActionListener(l);
	}

	public void addBackListener(ActionListener l) {
		btnBack.addActionListener(l);
	}
	
	public void addInsertInfoListener(ActionListener l) {
		btnAddInfo.addActionListener(l);
	}
	
	public void addNonNumericInfoListListener(ListSelectionListener l) {
		if (nonNumericInfoList != null)
			nonNumericInfoList.addListSelectionListener(l);
	}
	
	public List<String> getNonNumericListSelectedItems() {
		return nonNumericInfoList.getSelectedValuesList();
	}
	
	public void addNumericInfoListListener(ListSelectionListener l) {
		if (numericInfoList != null)
			numericInfoList.addListSelectionListener(l);
	}
	
	public List<String> getNumericListSelectedItems() {
		return numericInfoList.getSelectedValuesList();
	}
	
	public void showErrorPopup(String errorText) {
		JOptionPane.showMessageDialog(this, errorText, "Errore", JOptionPane.ERROR_MESSAGE);
	}
	
	public String getNameText() {
		return nameTextField.getText();
	}
	
	public String getAbbreviationText() {
		return abbreviationTextField.getText();
	}
	
	public String getManufacturerText() {
		return manufacturerTextField.getText();
	}

	public void setNumericInfoList(List<String> infos) {
		numericInfoList = new JList<>();
		DefaultListModel<String> dm = new DefaultListModel<>();
		for (String info : infos) {
			dm.addElement(info);
		}
		numericInfoList.setModel(dm);
		numericInfoList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// list.setBackground(backgroundColor);
		// list.setFont(panelFont);
		// list.setForeground(foregroundColor);
		numericInfoList.setFixedCellWidth(numericInfoList.getWidth());
		numericInfoList.setFixedCellHeight(50);
		
		scrollPane_1.setViewportView(numericInfoList);
	}
	
	public void setNonNumericInfoList(List<String> infos) {
		nonNumericInfoList = new JList<>();
		DefaultListModel<String> dm = new DefaultListModel<>();
		for (String info : infos) {
			dm.addElement(info);
		}
		nonNumericInfoList.setModel(dm);
		nonNumericInfoList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// list.setBackground(backgroundColor);
		// list.setFont(panelFont);
		// list.setForeground(foregroundColor);
		nonNumericInfoList.setFixedCellWidth(nonNumericInfoList.getWidth());
		nonNumericInfoList.setFixedCellHeight(50);
		
		scrollPane.setViewportView(nonNumericInfoList);
	}
}
