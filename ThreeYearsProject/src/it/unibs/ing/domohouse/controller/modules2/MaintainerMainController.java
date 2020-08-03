package it.unibs.ing.domohouse.controller.modules2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.view2.MaintainerMainPanel;

public class MaintainerMainController {

	private DataFacade dataFacade;

	private JFrame frame;
	private MaintainerMainPanel maintainerMainPanel;
	private JPanel previousPanel;

	private OptionController optionController;
	private UserFormController userFormController;
	private SensorCategoriesController sensorCategoriesController;

	private MaintainerHousingUnitsController maintainerHousingUnitsController;

	public MaintainerMainController(DataFacade dataFacade, JFrame frame, OptionController optionController,
			UserFormController userFormController, SensorCategoriesController sensorCategoriesController) {
		this.dataFacade = dataFacade;
		this.frame = frame;
		this.sensorCategoriesController = sensorCategoriesController;
		this.optionController = optionController;
		this.userFormController = userFormController;
	}

	public void setPreviousPanel(JPanel previousPanel) {
		this.previousPanel = previousPanel;
	}

	public void drawMaintainerMainPanel() {
		maintainerMainPanel = new MaintainerMainPanel();

		maintainerMainPanel.addBackListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				frame.getContentPane().add(previousPanel);
				frame.revalidate();
				frame.repaint();
			}
		});

		maintainerMainPanel.addOptionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				optionController.setPreviousPanel(maintainerMainPanel);
				optionController.drawOptionPanel(false);
			}
		});

		maintainerMainPanel.addAddUserListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				userFormController.setPreviousPanel(maintainerMainPanel);
				userFormController.drawInsertUserPanel();
			}
		});

		maintainerMainPanel.addRemoveUserListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				userFormController.setPreviousPanel(maintainerMainPanel);
				userFormController.drawRemoveUserPanel();
			}
		});

		maintainerMainPanel.addSearchListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				maintainerMainPanel.setUserList(dataFacade.getUsersFromDB(maintainerMainPanel.getSearchBoxText()));
			}
		});

		maintainerMainPanel.addUsersListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String user = maintainerMainPanel.getListSelectedItem();
					if (dataFacade.hasUser(user)) {
						frame.getContentPane().removeAll();
						frame.invalidate();
						maintainerHousingUnitsController.drawMaintainerHousingUnitsPanel(user);
					}
				}
			}
		});

		maintainerMainPanel.addSensorCategoryListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				sensorCategoriesController.setPreviousPanel(maintainerMainPanel);
				sensorCategoriesController.drawSensorCategoriesPanel();
			}
		});

		maintainerMainPanel.addActuatorCategoryListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				actuatorCategoriesController.setPreviousPanel(maintainerMainPanel);
				actuatorCategoriesController.drawActuatorCategoriesPanel();
			}
		});

		frame.getContentPane().add(maintainerMainPanel);
		frame.revalidate();
		frame.repaint();
	}
}
