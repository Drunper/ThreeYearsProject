package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.Authenticator;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;

import java.io.PrintWriter;
import java.util.logging.Level;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class LoginController {

	// View
	private MenuManager menu;
	private PrintWriter output;
	private RawInputHandler input;

	// Controller dei menu collegati
	private MaintainerController maintainerController;
	private UserController userController;

	// Model
	private ClockStrategy clock;
	private DataFacade dataFacade;
	private LogWriter log;
	private Authenticator authenticator;

	public LoginController(DataFacade dataFacade, LogWriter log, Authenticator authenticator, ClockStrategy clock,
			PrintWriter output, RawInputHandler input) {
		menu = new MenuManager(ControllerStrings.LOGIN_MENU_TITLE, ControllerStrings.LOGIN_MENU_VOICES, output, input);
		this.dataFacade = dataFacade;
		this.log = log;
		this.authenticator = authenticator;
		this.clock = clock;
		this.output = output;
		this.input = input;
	}

	public void show() {
		int selection;
		do {
			output.println(clock.getCurrentTime());
			output.println();
			selection = menu.doChoice();
			switch (selection) {
				case 0:
					clock.stopClock();
					break;
				case 1: // login fruitore
					String user = input.readNotVoidString(ControllerStrings.INSERT_USER);
					try {
						if (dataFacade.hasUser(user)) {
							if (dataFacade.doesHousingUnitExist(user))
								userController.show(user);
						}
						else
							output.println(ControllerStrings.ERROR_NON_EXISTENT_USER);
					}
					catch (Exception e) {
						//TOLOG
						output.println(ControllerStrings.DB_LOAD_USER_ERROR);
					}
					break;
				case 2: // login manutentore
					String maintainer;
					String password;
					boolean ok;
					do {
						ok = false;
						maintainer = input.readNotVoidString(ControllerStrings.INSERT_USER);
						if (!maintainer.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER)) {
							password = input.readNotVoidString(ControllerStrings.INSERT_PASSWORD);
							try {
								ok = authenticator.checkMaintainerPassword(maintainer, password);
								if (!ok)
									output.println(ControllerStrings.USER_OR_PASSWORD_ERROR);
							}
							catch (Exception e) {
								//TOLOG
								output.println(ControllerStrings.DB_AUTHENTICATION_ERROR);
							}
						}
						log.write(Level.FINE, ControllerStrings.MAINTAINER + maintainer + ControllerStrings.LOG_SYSTEM_ACCESS);
					}
					while (!maintainer.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER) && !ok);
					if (ok)
						maintainerController.show();
					break;
			}
		}
		while (selection != 0);
	}

	public void setMaintainerController(MaintainerController maintainerController) {
		this.maintainerController = maintainerController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
	}
}
