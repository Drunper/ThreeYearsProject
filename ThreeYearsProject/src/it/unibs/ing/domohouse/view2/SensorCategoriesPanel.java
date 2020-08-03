package it.unibs.ing.domohouse.view2;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;

public class SensorCategoriesPanel extends JPanel {
	private JButton btnAddCategory;
	private JScrollPane infoScrollPane;
	private JButton btnOptions;
	private JButton btnBack;
	private JScrollPane categoriesScrollPane;
	private JLabel lblNewLabel;
	private JList categoriesList;
	private JList infoList;
	private JTextArea textArea;
	private JButton btnRemoveCategory;

	/**
	 * Create the panel.
	 */
	public SensorCategoriesPanel() {
		setLayout(new MigLayout("", "[][grow][grow][grow]", "[][grow][grow][][]"));
		
		lblNewLabel = new JLabel("Lista categorie");
		add(lblNewLabel, "cell 0 0,alignx trailing");
		
		btnAddCategory = new JButton("Aggiungi");
		add(btnAddCategory, "cell 2 0");
		
		btnRemoveCategory = new JButton("Rimuovi");
		add(btnRemoveCategory, "cell 3 0");
		
		categoriesScrollPane = new JScrollPane();
		add(categoriesScrollPane, "cell 0 1 2 2,grow");
		
		textArea = new JTextArea();
		add(textArea, "cell 2 1 2 1,grow");
		
		infoScrollPane = new JScrollPane();
		add(infoScrollPane, "cell 2 2 2 1,grow");
		
		btnOptions = new JButton("Opzioni");
		add(btnOptions, "cell 3 3");
		
		btnBack = new JButton("Indietro");
		add(btnBack, "cell 3 4");
	}
	
	public void setTextAreaText(String text) {
		textArea.setText(text);
	}

	public void addAddCategoryListener(ActionListener l) {
		btnAddCategory.addActionListener(l);
	}

	public void addRemoveCategoryListener(ActionListener l) {
		btnRemoveCategory.addActionListener(l);
	}

	public void addOptionsListener(ActionListener l) {
		btnOptions.addActionListener(l);
	}

	public void addBackListener(ActionListener l) {
		btnBack.addActionListener(l);
	}
	
	public void addCategoriesListListener(ListSelectionListener l) {
		if (categoriesList != null)
			categoriesList.addListSelectionListener(l);
	}
	
	public String getListSelectedItem() {
		return (String) categoriesList.getSelectedValue();
	}

	public void setCategoriesList(Set<String> categories) {
		categoriesList = new JList();
		DefaultListModel dm = new DefaultListModel();
		for (String category : categories) {
			dm.addElement(category);
		}
		categoriesList.setModel(dm);
		categoriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// list.setBackground(backgroundColor);
		// list.setFont(panelFont);
		// list.setForeground(foregroundColor);
		categoriesList.setFixedCellWidth(categoriesList.getWidth());
		categoriesList.setFixedCellHeight(50);
		
		categoriesScrollPane.setViewportView(categoriesList);
	}
	
	public void setInfoList(Set<String> infos) {
		infoList = new JList();
		DefaultListModel dm = new DefaultListModel();
		for (String info : infos) {
			dm.addElement(info);
		}
		infoList.setModel(dm);
		infoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// list.setBackground(backgroundColor);
		// list.setFont(panelFont);
		// list.setForeground(foregroundColor);
		infoList.setFixedCellWidth(infoList.getWidth());
		infoList.setFixedCellHeight(50);
		
		infoScrollPane.setViewportView(infoList);
	}
}
