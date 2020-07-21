package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.MaintainerUnitInputHandler;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.view.ManageableRenderer;

import java.io.PrintWriter;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MaintainerUnitController {

	private MenuManager menuManager;

	private MaintainerRoomController maintainerRoomController;
	private DataFacade dataFacade;
	private LogWriter log;
	private ManageableRenderer renderer;
	private ClockStrategy clock;
	private PrintWriter output;

	private MaintainerUnitInputHandler maintainedUnitInputHandler;

	public MaintainerUnitController(DataFacade dataFacade, LogWriter log, ManageableRenderer renderer,
			MaintainerUnitInputHandler maintainerUnitInputHandler, ClockStrategy clock, PrintWriter output,
			RawInputHandler input) {
		menuManager = new MenuManager(ControllerStrings.MAINTAINER_UNIT_MENU_TITLE,
				ControllerStrings.MAINTAINER_UNIT_MENU_VOICES, output, input);
		this.dataFacade = dataFacade;
		this.log = log;
		this.renderer = renderer;
		this.maintainedUnitInputHandler = maintainerUnitInputHandler;
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
					// uscita menu
					log.write(ControllerStrings.LOG_EXIT_MENU);
					break;
				case 1:
					// visualizza descrizione unità immobiliare
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_DESCR_HOUSE);
					output.println(renderer.render(dataFacade.getHousingUnit(user, selectedHouse)));
					break;
				case 2:
					// cambia descrizione casa
					log.write(ControllerStrings.LOG_CHANGE_DESCR_HOUSE);
					maintainedUnitInputHandler.changeHouseDescription(user, selectedHouse);
					break;
				case 3:
					// visualizza stanza
					menuManager.clearOutput();
					if (dataFacade.doesRoomExist(user, selectedHouse)) {
						menuManager.printCollectionOfString(dataFacade.getRoomsList(user, selectedHouse));
						output.println();
						output.println();
						String selectedRoom = maintainedUnitInputHandler.safeInsertRoom(user, selectedHouse);
						maintainerRoomController.show(user, selectedHouse, selectedRoom);
					}
					else
						output.println(ControllerStrings.NO_ROOM);
					break;
				case 4:
					// Inserisci stanza
					log.write(ControllerStrings.LOG_INSERT_ROOM);
					maintainedUnitInputHandler.readRoomFromUser(user, selectedHouse, menuManager);
					log.write(ControllerStrings.LOG_INSERT_ROOM_SUCCESS);
					break;
				case 5:
					// aggiungi regola
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_INSERT_NEW_RULE);
					maintainedUnitInputHandler.readRuleFromUser(user, selectedHouse, menuManager);
					log.write(ControllerStrings.LOG_INSERT_NEW_RULE_SUCCESS);
					break;
				case 6:
					// visualizza regole attive
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_SHOW_ENABLED_RULES);
					menuManager.printCollectionOfString(dataFacade.getEnabledRulesStrings(user, selectedHouse));
					break;
				case 7:
					// visualizza tutte le regole
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_SHOW_ALL_RULES);
					menuManager.printCollectionOfString(dataFacade.getRulesStrings(user, selectedHouse));
					break;
				case 8:
					// attiva/disattiva regola
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_ENABLE_DISABLE_RULE);
					maintainedUnitInputHandler.readRuleStateFromUser(user, selectedHouse, menuManager);
					break;
				case 9:
					// aggiorna ora
					log.write(ControllerStrings.LOG_REFRESH_HOUR);
					menuManager.clearOutput();
					break;
			}
		}
		while (selection != 0);
	}

	public void setMaintainerRoomController(MaintainerRoomController maintainerRoomController) {
		this.maintainerRoomController = maintainerRoomController;
	}
}
