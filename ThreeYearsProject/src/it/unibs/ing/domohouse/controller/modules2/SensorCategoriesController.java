package it.unibs.ing.domohouse.controller.modules2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.view.ManageableRenderer;
import it.unibs.ing.domohouse.view2.SensorCategoriesPanel;

public class SensorCategoriesController {

	private DataFacade dataFacade;

	private JFrame frame;
	private JPanel previousPanel;
	private SensorCategoriesPanel sensorCategoriesPanel;
	private ManageableRenderer renderer;

	private OptionController optionController;
	private SensorCategoryFormController sensorCategoryFormController;

	public SensorCategoriesController(DataFacade dataFacade, JFrame frame, ManageableRenderer renderer,
			OptionController optionController, SensorCategoryFormController sensorCategoryFormController) {
		this.dataFacade = dataFacade;
		this.frame = frame;
		this.optionController = optionController;
		this.sensorCategoryFormController = sensorCategoryFormController;
		this.renderer = renderer;
	}

	public void setPreviousPanel(JPanel previousPanel) {
		this.previousPanel = previousPanel;
	}

	public void drawSensorCategoriesPanel() {
		sensorCategoriesPanel = new SensorCategoriesPanel();

		sensorCategoriesPanel.setCategoriesList(dataFacade.getSensorCategorySet());

		sensorCategoriesPanel.addBackListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				frame.getContentPane().add(previousPanel);
				frame.revalidate();
				frame.repaint();
			}
		});

		sensorCategoriesPanel.addOptionsListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				optionController.setPreviousPanel(sensorCategoriesPanel);
				optionController.drawOptionPanel(false);
			}
		});

		sensorCategoriesPanel.addAddCategoryListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				sensorCategoryFormController.setPreviousPanel(sensorCategoriesPanel);
				sensorCategoryFormController.drawInsertSensorCategoryPanel();
			}
		});

		sensorCategoriesPanel.addRemoveCategoryListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				sensorCategoryFormController.setPreviousPanel(sensorCategoriesPanel);
				sensorCategoryFormController.drawRemoveSensorCategoryPanel();
			}
		});

		sensorCategoriesPanel.addCategoriesListListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String category = sensorCategoriesPanel.getListSelectedItem();
					sensorCategoriesPanel.setTextAreaText(renderer.render(dataFacade.getSensorCategory(category)));
					sensorCategoriesPanel.setInfoList(dataFacade.getSensorCategory(category).getInfoStrategySet());
				}
			}
		});

		frame.getContentPane().add(sensorCategoriesPanel);
		frame.revalidate();
		frame.repaint();
	}
}
