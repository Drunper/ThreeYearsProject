package it.unibs.ing.domohouse.view2;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

public class SensorCategoryFormPanel extends JPanel {
	private JTextField nameTextField;
	private JTextField abbreviationTextField;
	private JTextField manufacturerTextField;
	private JScrollPane scrollPane;
	private JButton btnAddInfo;
	private JButton btnOptions;
	private JButton btnBack;
	private JLabel lblNewLabel_3;
	private JList infoList;

	/**
	 * Create the panel.
	 */
	public SensorCategoryFormPanel() {
		setLayout(new MigLayout("", "[][grow][grow]", "[][][grow][][][]"));
		
		JLabel lblNewLabel = new JLabel("Nome");
		add(lblNewLabel, "cell 0 0,alignx trailing");
		
		nameTextField = new JTextField();
		add(nameTextField, "cell 1 0,growx");
		nameTextField.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Selezione informazioni");
		add(lblNewLabel_3, "cell 2 0");
		
		JLabel lblNewLabel_1 = new JLabel("Sigla");
		add(lblNewLabel_1, "cell 0 2,alignx trailing");
		
		abbreviationTextField = new JTextField();
		add(abbreviationTextField, "cell 1 2,growx");
		abbreviationTextField.setColumns(10);
		
		scrollPane = new JScrollPane();
		add(scrollPane, "cell 2 1 1 2,grow");
		
		JLabel lblNewLabel_2 = new JLabel("Costruttore");
		add(lblNewLabel_2, "cell 0 3,alignx trailing");
		
		manufacturerTextField = new JTextField();
		add(manufacturerTextField, "cell 1 3,growx");
		manufacturerTextField.setColumns(10);
		
		btnAddInfo = new JButton("Aggiungi informazione");
		add(btnAddInfo, "cell 2 3");
		
		btnOptions = new JButton("Opzioni");
		add(btnOptions, "cell 2 4");
		
		btnBack = new JButton("Indietro");
		add(btnBack, "cell 2 5");
	}

	public void setInfoList(List<String> infos) {
		infoList = new JList();
		DefaultListModel dm = new DefaultListModel();
		for (String info : infos) {
			dm.addElement(info);
		}
		infoList.setModel(dm);
		infoList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// list.setBackground(backgroundColor);
		// list.setFont(panelFont);
		// list.setForeground(foregroundColor);
		infoList.setFixedCellWidth(infoList.getWidth());
		infoList.setFixedCellHeight(50);
		
		scrollPane.setViewportView(infoList);
	}
}
