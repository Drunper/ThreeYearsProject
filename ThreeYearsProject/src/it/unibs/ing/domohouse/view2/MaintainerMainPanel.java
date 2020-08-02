package it.unibs.ing.domohouse.view2;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;

public class MaintainerMainPanel extends JPanel {
	private JTextField userSearchBox;
	private JButton btnActuatorCategory;
	private JButton btnSensorCategory;
	private JButton btnOption;
	private JButton btnBack;
	private JButton btnRemoveUser;
	private JButton btnAddUser;
	private JButton btnSearch;
	private JList list;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public MaintainerMainPanel() {
		setLayout(new MigLayout("", "[grow][grow][][][]", "[][grow][][]"));

		JLabel lblNewLabel = new JLabel("Lista utenti");
		add(lblNewLabel, "cell 0 0,alignx trailing");

		userSearchBox = new JTextField();
		userSearchBox.setToolTipText("Cerca un utente all'interno del database");
		add(userSearchBox, "cell 1 0,growx");
		userSearchBox.setColumns(10);

		btnSearch = new JButton("Cerca");
		add(btnSearch, "cell 2 0");

		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "cell 0 1 2 1,grow");

		btnAddUser = new JButton("Aggiungi utente");
		add(btnAddUser, "cell 3 0");

		btnRemoveUser = new JButton("Rimuovi utente");
		add(btnRemoveUser, "cell 4 0");

		btnSensorCategory = new JButton("Categorie sensori");
		add(btnSensorCategory, "flowy,cell 4 1");

		btnActuatorCategory = new JButton("Categorie attuatori");
		add(btnActuatorCategory, "cell 4 1");

		btnOption = new JButton("Opzioni");
		add(btnOption, "cell 4 2");

		btnBack = new JButton("Indietro");
		add(btnBack, "cell 4 3");
	}

	public String getSearchBoxText() {
		return userSearchBox.getText();
	}

	public void addSearchListener(ActionListener l) {
		btnSearch.addActionListener(l);
	}

	public void addAddUserListener(ActionListener l) {
		btnAddUser.addActionListener(l);
	}

	public void addRemoveUserListener(ActionListener l) {
		btnRemoveUser.addActionListener(l);
	}

	public void addSensorCategoryListener(ActionListener l) {
		btnSensorCategory.addActionListener(l);
	}

	public void addActuatorCategoryListener(ActionListener l) {
		btnActuatorCategory.addActionListener(l);
	}

	public void addOptionListener(ActionListener l) {
		btnOption.addActionListener(l);
	}

	public void addBackListener(ActionListener l) {
		btnBack.addActionListener(l);
	}
	
	public void addUsersListener(ListSelectionListener l) {
		if (list != null)
			list.addListSelectionListener(l);
	}
	
	public String getListSelectedItem() {
		return (String) list.getSelectedValue();
	}

	public void setUserList(List<String> users) {
		list = new JList();
		DefaultListModel dm = new DefaultListModel();
		for (String user : users) {
			dm.addElement(user);
		}
		list.setModel(dm);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// list.setBackground(backgroundColor);
		// list.setFont(panelFont);
		// list.setForeground(foregroundColor);
		list.setFixedCellWidth(list.getWidth());
		list.setFixedCellHeight(50);
		
		scrollPane.setViewportView(list);
	}
}
