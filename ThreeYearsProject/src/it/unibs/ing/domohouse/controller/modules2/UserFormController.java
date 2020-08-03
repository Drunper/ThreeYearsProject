package it.unibs.ing.domohouse.controller.modules2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibs.ing.domohouse.controller.inputhandler2.MaintainerInputHandler;
import it.unibs.ing.domohouse.model.util.ObjectRemover;
import it.unibs.ing.domohouse.view2.UserFormPanel;

public class UserFormController {

	private MaintainerInputHandler maintainerInputHandler;
	private ObjectRemover objectRemover;

	private JFrame frame;
	private UserFormPanel userFormPanel;
	private JPanel previousPanel;

	public UserFormController(JFrame frame, MaintainerInputHandler maintainerInputHandler,
			ObjectRemover objectRemover) {
		this.frame = frame;
		this.maintainerInputHandler = maintainerInputHandler;
	}

	public void drawInsertUserPanel() {
		userFormPanel = new UserFormPanel();

		userFormPanel.addCancelListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				frame.getContentPane().add(previousPanel);
				frame.revalidate();
				frame.repaint();
			}
		});

		userFormPanel.addInsertListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (userFormPanel.getUserName().length() != 0)
					if (maintainerInputHandler.insertUser(userFormPanel.getUserName())) {
						userFormPanel.showInfoPopup("Inserimento effettuato con successo");
						frame.getContentPane().removeAll();
						frame.invalidate();
						frame.getContentPane().add(previousPanel);
						frame.revalidate();
						frame.repaint();
					}
					else
						userFormPanel.showErrorPopup("Errore durante l'inserimento");
				else
					userFormPanel.showErrorPopup("Per effettuare l'inserimento è necessario inserire un nome");
			}
		});

		frame.getContentPane().add(userFormPanel);
		frame.revalidate();
		frame.repaint();
	}

	public void drawRemoveUserPanel() {
		userFormPanel = new UserFormPanel();

		userFormPanel.addCancelListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.invalidate();
				frame.getContentPane().add(previousPanel);
				frame.revalidate();
				frame.repaint();
			}
		});

		userFormPanel.addInsertListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (userFormPanel.getUserName().length() != 0) {
					objectRemover.removeUser(userFormPanel.getUserName());
					userFormPanel.showInfoPopup("Rimozione effettuata con successo");
					frame.getContentPane().removeAll();
					frame.invalidate();
					frame.getContentPane().add(previousPanel);
					frame.revalidate();
					frame.repaint();
				}
				else
					userFormPanel.showErrorPopup("E' necessario inserire un nome");
			}
		});

		frame.getContentPane().add(userFormPanel);
		frame.revalidate();
		frame.repaint();
	}

	public void setPreviousPanel(JPanel previousPanel) {
		this.previousPanel = previousPanel;
	}
}
