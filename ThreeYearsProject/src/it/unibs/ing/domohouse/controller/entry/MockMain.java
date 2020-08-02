package it.unibs.ing.domohouse.controller.entry;

import java.awt.EventQueue;
import java.io.PrintWriter;

import javax.swing.JFrame;

import it.unibs.ing.domohouse.controller.inputhandler2.MaintainerInputHandler;
import it.unibs.ing.domohouse.controller.modules2.MaintainerLoginController;
import it.unibs.ing.domohouse.controller.modules2.MaintainerMainController;
import it.unibs.ing.domohouse.controller.modules2.OptionController;
import it.unibs.ing.domohouse.controller.modules2.UserFormController;
import it.unibs.ing.domohouse.model.db.Connector;
import it.unibs.ing.domohouse.model.db.DatabaseAuthenticator;
import it.unibs.ing.domohouse.model.util.Authenticator;
import it.unibs.ing.domohouse.model.util.ConfigFileManager;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.HashCalculator;
import it.unibs.ing.domohouse.model.util.ObjectRemover;

public class MockMain {

	private JFrame frame;
	private static ConfigFileManager c;
	private static Connector connector;
	private static DataFacade dataFacade;
	private static MaintainerInputHandler mih;
	private static Authenticator a;
	private static ObjectRemover ob;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		c = new ConfigFileManager(new PrintWriter(System.out));
		c.loadConfigFile();
		connector = new Connector(c.getDBURL(), c.getDBUserName(), c.getDBpassword());
		connector.openConnection();
		dataFacade = new DataFacade(connector);
		ob = new ObjectRemover(dataFacade);
		mih = new MaintainerInputHandler(dataFacade);
		a = new DatabaseAuthenticator(new HashCalculator(), connector);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MockMain window = new MockMain();
					window.frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MockMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 450);
		OptionController op = new OptionController(frame, c);
		UserFormController uf = new UserFormController(frame, mih, ob);
		MaintainerMainController ma = new MaintainerMainController(dataFacade, frame, op, uf);
		
		MaintainerLoginController m = new MaintainerLoginController(a, frame, ma, op);
		m.drawMaintainerLoginPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
