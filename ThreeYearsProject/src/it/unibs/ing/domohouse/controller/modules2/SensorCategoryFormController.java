package it.unibs.ing.domohouse.controller.modules2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import it.unibs.ing.domohouse.controller.inputhandler2.MaintainerInputHandler;
import it.unibs.ing.domohouse.model.ModelStrings;
import it.unibs.ing.domohouse.view2.SensorCategoryFormPanel;

public class SensorCategoryFormController {

	private MaintainerInputHandler maintainerInputHandler;

	private Set<Integer> infos;
	private Map<Integer, String> measurementUnits;

	private JFrame frame;
	private JPanel previousPanel;
	private SensorCategoryFormPanel sensorCategoryFormPanel;

	public SensorCategoryFormController(MaintainerInputHandler maintainerInputHandler, JFrame frame) {
		this.maintainerInputHandler = maintainerInputHandler;
		this.frame = frame;
		infos = new HashSet<>();
		measurementUnits = new HashMap<>();
	}

	public void setPreviousPanel(JPanel previousPanel) {
		this.previousPanel = previousPanel;
	}

	public void drawInsertSensorCategoryPanel() {
		sensorCategoryFormPanel = new SensorCategoryFormPanel();

		sensorCategoryFormPanel.addBackListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				frame.getContentPane().add(previousPanel);
				frame.revalidate();
				frame.repaint();
			}
		});

		sensorCategoryFormPanel.addNonNumericInfoListListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					infos.addAll(sensorCategoryFormPanel.getNonNumericListSelectedItems().stream()
							.map(s -> Integer.parseInt(s.split(":")[ModelStrings.FIRST_TOKEN]))
							.collect(Collectors.toSet()));
				}
			}
		});
		
		sensorCategoryFormPanel.addNumericInfoListListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					infos.addAll(sensorCategoryFormPanel.getNumericListSelectedItems().stream()
							.map(s -> Integer.parseInt(s.split(":")[ModelStrings.FIRST_TOKEN]))
							.collect(Collectors.toSet()));
				}
				e.
			}
		});

		sensorCategoryFormPanel.addInsertListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sensorCategoryFormPanel.getAbbreviationText().length() != 0
						&& sensorCategoryFormPanel.getManufacturerText().length() != 0
						&& sensorCategoryFormPanel.getNameText().length() != 0)
					if (nonNumericInfos.size() != 0 || numericInfos.size() != 0) {
						maintainerInputHandler.insertSensorCategory(sensorCategoryFormPanel.getNameText(),
								sensorCategoryFormPanel.getAbbreviationText(),
								sensorCategoryFormPanel.getManufacturerText(), IDs, measurementUnits);
					}
					else
						sensorCategoryFormPanel
								.showErrorPopup("E' necessario selezionare almeno un'informazione rilevabile");
				else
					sensorCategoryFormPanel.showErrorPopup("E' necessario compilare tutti i campi");
			}
		});

		frame.getContentPane().add(sensorCategoryFormPanel);
		frame.revalidate();
		frame.repaint();
	}

	public void drawRemoveSensorCategoryPanel() {
		// TODO Auto-generated method stub

	}
}
