package it.unibs.ing.domohouse.controller.modules2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibs.ing.domohouse.model.util.ConfigFileManager;
import it.unibs.ing.domohouse.view2.DatabaseOptionPanel;
import it.unibs.ing.domohouse.view2.GraphicOptionPanel;
import it.unibs.ing.domohouse.view2.OptionPanel;

public class OptionController {

	private ConfigFileManager configFileManager;

	private JFrame frame;
	private OptionPanel optionPanel;
	private JPanel previousPanel;

	public OptionController(JFrame frame, ConfigFileManager configFileManager) {
		this.frame = frame;
		this.configFileManager = configFileManager;
	}

	public void setPreviousPanel(JPanel previousPanel) {
		this.previousPanel = previousPanel;
	}

	public void drawOptionPanel(boolean databaseOption) {
		optionPanel = new OptionPanel();
		GraphicOptionPanel card1 = new GraphicOptionPanel();
		optionPanel.addCard("Grafica", card1);
		ActionListener cancelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				frame.getContentPane().add(previousPanel);
				frame.revalidate();
				frame.repaint();
			}
		};
		card1.addCancelListener(cancelListener);

		if (databaseOption) {
			DatabaseOptionPanel card2 = new DatabaseOptionPanel();
			card2.setIP(configFileManager.getIP());
			card2.setPort(configFileManager.getPort());
			card2.setDatabaseName(configFileManager.getDBName());
			card2.setPassword(configFileManager.getDBpassword());
			card2.setUserName(configFileManager.getDBName());
			
			optionPanel.addCard("Database", card2);
			card2.addCancelListener(cancelListener);

			card2.addSaveListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (card2.getPassword() != null && card2.getUserName() != null && card2.getPort() != null
							&& card2.getIP() != null && card2.getDatabaseName() != null) {
						configFileManager.updateConfigFile(card2.getUserName(), card2.getPassword(), card2.getIP(),
								card2.getPort(), card2.getDatabaseName());
						card2.showInfoPopup("Le modifiche effettuate avranno effetto solo dopo il riavvio dell'applicazione");
					}
					else
						card2.showErrorPopup("E' necessario l'inserimento di tutti i valori");
				}
			});
		}
		
		frame.getContentPane().add(optionPanel);
		frame.revalidate();
		frame.repaint();
	}

}
