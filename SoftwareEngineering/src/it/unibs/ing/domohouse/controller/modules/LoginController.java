package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.Authenticator;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;

import java.io.PrintWriter;

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
					if (dataFacade.getFirstStart()) {
						log.write(ControllerStrings.LOG_FIRST_ACCESS);
						output.println(ControllerStrings.USER_FIRST_ACCESS_PROHIBITED);
					}
					else {
						String user = input.readNotVoidString(ControllerStrings.INSERT_USER);
						if (!user.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER)) {
							output.println(ControllerStrings.WELCOME + user);
							log.write(user + ControllerStrings.LOG_SYSTEM_ACCESS);
							userController.show();
						}
					}
					break;
				case 2: // login manutentore
					String user;
					String password;
					boolean ok;
					do {
						ok = false;
						user = input.readNotVoidString(ControllerStrings.INSERT_USER);
						if (!user.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER)) {
							password = input.readNotVoidString(ControllerStrings.INSERT_PASSWORD);
							ok = authenticator.checkPassword(user, password);
							if (!ok)
								output.println(ControllerStrings.USER_OR_PASSWORD_ERROR);
						}
						log.write(ControllerStrings.MAINTAINER + user + ControllerStrings.LOG_SYSTEM_ACCESS);
					}
					while (!user.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER) && !ok);
					if (ok) {
						if (dataFacade.getFirstStart()) {
							output.println(ControllerStrings.BASIC_FILE_CREATION);
							log.write(ControllerStrings.LOG_BASIC_FILE_CREATION);
						}
						maintainerController.show();
					}
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
