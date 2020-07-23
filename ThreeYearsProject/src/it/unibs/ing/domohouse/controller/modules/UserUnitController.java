package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.UserUnitInputHandler;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.view.ManageableRenderer;

import java.io.PrintWriter;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class UserUnitController {

	private MenuManager menuManager;

	private UserRoomController userRoomController;
	private DataFacade dataFacade;
	private LogWriter log;
	private ClockStrategy clock;
	private PrintWriter output;

	private ManageableRenderer renderer;
	private UserUnitInputHandler userUnitInputHandler;

	public UserUnitController(DataFacade dataFacade, LogWriter log, ManageableRenderer renderer,
			UserUnitInputHandler userUnitInputHandler, ClockStrategy clock, PrintWriter output, RawInputHandler input) {
		menuManager = new MenuManager(ControllerStrings.USER_UNIT_MENU_TITLE, ControllerStrings.USER_UNIT_MENU_VOICES,
				output, input);
		this.dataFacade = dataFacade;
		this.log = log;
		this.renderer = renderer;
		this.userUnitInputHandler = userUnitInputHandler;
		this.clock = clock;
		this.output = output;
	}

	public void show(String user, String selectedHouse) {
		int selection;
		do {
			output.println(clock.getCurrentTime());
			output.println();
			selection = menuManager.doChoice();
			switch (selection) {
				case 0:
					log.write(ControllerStrings.LOG_EXIT_MENU);
					break;
				case 1:
					// visualizza descrizione unita immobiliare
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_DESCR_HOUSE);
					output.println(renderer.render(dataFacade.getHousingUnit(user, selectedHouse)));
					break;
				case 2:
					// visualizza stanza
					menuManager.clearOutput();
					if (dataFacade.doesRoomExist(user, selectedHouse)) {
						menuManager.printCollectionOfString(dataFacade.getRoomsSet(user, selectedHouse));
						output.println();
						output.println();
						String selectedRoom = userUnitInputHandler.safeInsertRoom(user, selectedHouse);
						userRoomController.show(user, selectedHouse, selectedRoom);
					}
					else
						output.println(ControllerStrings.NO_ROOM);
					break;
				case 3:
					// aggiungi regola
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_INSERT_NEW_RULE);
					userUnitInputHandler.readRuleFromUser(user, selectedHouse, menuManager);
					log.write(ControllerStrings.LOG_INSERT_NEW_RULE_SUCCESS);
					break;
				case 4:
					// visualizza regole attive
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_SHOW_ENABLED_RULES);
					menuManager.printCollectionOfString(dataFacade.getEnabledRulesStrings(user, selectedHouse));
					break;
				case 5:
					// visualizza tutte le regole
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_SHOW_ALL_RULES);
					menuManager.printCollectionOfString(dataFacade.getRulesStrings(user, selectedHouse));
					break;
				case 6:
					// attiva/disattiva regola
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_ENABLE_DISABLE_RULE);
					userUnitInputHandler.readRuleStateFromUser(user, selectedHouse, menuManager);
					break;
				case 7:
					// aggiorna ora
					log.write(ControllerStrings.LOG_REFRESH_HOUR);
					menuManager.clearOutput();
					break;
			}
		}
		while (selection != 0);
	}

	public void setUserRoomController(UserRoomController userRoomController) {
		this.userRoomController = userRoomController;
	}
}
