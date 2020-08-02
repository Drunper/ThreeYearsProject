package it.unibs.ing.domohouse.controller.modules2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import it.unibs.ing.domohouse.model.util.Authenticator;
import it.unibs.ing.domohouse.view2.MaintainerLoginPanel;

public class MaintainerLoginController {

	private Authenticator authenticator;
	
	private JFrame frame;
	private MaintainerLoginPanel maintainerLoginPanel;
	
	private MaintainerMainController maintainerMainController;
	private OptionController optionController;

	public MaintainerLoginController(Authenticator authenticator, JFrame frame, MaintainerMainController maintainerMainController, OptionController optionController) {
		this.frame = frame;
		this.authenticator = authenticator;
		this.maintainerMainController = maintainerMainController;
		this.optionController = optionController;
	}
	
	public void drawMaintainerLoginPanel() {
		maintainerLoginPanel = new MaintainerLoginPanel();
		frame.getContentPane().add(maintainerLoginPanel);
		
		maintainerLoginPanel.addLoginListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!authenticator.checkMaintainerPassword(maintainerLoginPanel.getUserFieldText(), maintainerLoginPanel.getPasswordFieldText()))
					maintainerLoginPanel.showErrorPopup("Errore, l'utente o la password inserita sono errati!");
				else {
					frame.getContentPane().removeAll();
					frame.invalidate();
					maintainerMainController.setPreviousPanel(maintainerLoginPanel);
					maintainerMainController.drawMaintainerMainPanel();
				}
			}
		});
		
		maintainerLoginPanel.addExitListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		maintainerLoginPanel.addOptionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				optionController.setPreviousPanel(maintainerLoginPanel);
				optionController.drawOptionPanel(true);
			}
		});
	}
}
