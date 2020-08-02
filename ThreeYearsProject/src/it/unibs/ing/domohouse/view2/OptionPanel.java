package it.unibs.ing.domohouse.view2;

import java.awt.Component;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTabbedPane;

public class OptionPanel extends JPanel {

	private JTabbedPane tabbedPane;

	/**
	 * Create the panel.
	 */
	public OptionPanel() {
		setLayout(new MigLayout("", "[grow]", "[grow]"));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "cell 0 0,grow");
	}
	
	public void addCard(String tabName, Component component) {
		tabbedPane.addTab(tabName, component);
	}
}
