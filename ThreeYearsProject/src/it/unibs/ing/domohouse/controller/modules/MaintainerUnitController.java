package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.MaintainerUnitInputHandler;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.model.util.ObjectRemover;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.view.ManageableRenderer;

import java.io.PrintWriter;
import java.util.logging.Level;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MaintainerUnitController {

	private MenuManager menuManager;

	private MaintainerRoomController maintainerRoomController;
	private DataFacade dataFacade;
	private ObjectRemover objectRemover;
	private LogWriter log;
	private ManageableRenderer renderer;
	private ClockStrategy clock;
	private PrintWriter output;

	private MaintainerUnitInputHandler maintainedUnitInputHandler;

	public MaintainerUnitController(DataFacade dataFacade, ObjectRemover objectRemover, LogWriter log,
			ManageableRenderer renderer, MaintainerUnitInputHandler maintainerUnitInputHandler, ClockStrategy clock,
			PrintWriter output, RawInputHandler input) {
		menuManager = new MenuManager(ControllerStrings.MAINTAINER_UNIT_MENU_TITLE,
				ControllerStrings.MAINTAINER_UNIT_MENU_VOICES, output, input);
		this.dataFacade = dataFacade;
		this.objectRemover = objectRemover;
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
					log.write(Level.FINE, ControllerStrings.LOG_EXIT_MENU);
					break;
				case 1:
					// visualizza descrizione unità immobiliare
					menuManager.clearOutput();
					log.write(Level.FINE, ControllerStrings.LOG_DESCR_HOUSE);
					output.println(renderer.render(dataFacade.getHousingUnit(user, selectedHouse)));
					break;
				case 2:
					// cambia descrizione casa
					log.write(Level.FINE, ControllerStrings.LOG_CHANGE_DESCR_HOUSE);
					maintainedUnitInputHandler.changeHouseDescription(user, selectedHouse);
					break;
				case 3:
					// visualizza stanza
					menuManager.clearOutput();
					if (dataFacade.doesRoomExist(user, selectedHouse)) {
						menuManager.printCollectionOfString(dataFacade.getRoomsSet(user, selectedHouse));
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
					log.write(Level.FINE, ControllerStrings.LOG_INSERT_ROOM);
					try {
						maintainedUnitInputHandler.readRoomFromUser(user, selectedHouse, menuManager);
					}
					catch (Exception e) {
						// TOLOG
						output.println("Errore durante l'inserimento della stanza, verificare la connessione al database");
					}
					log.write(Level.FINE, ControllerStrings.LOG_INSERT_ROOM_SUCCESS);
					break;
				case 5:
					// Rimozione stanza
					menuManager.clearOutput();
					if (dataFacade.doesRoomExist(user, selectedHouse)) {
						menuManager.printCollectionOfString(dataFacade.getRoomsSet(user, selectedHouse));
						output.println();
						output.println();
						String selectedRoom = maintainedUnitInputHandler.safeInsertRoom(user, selectedHouse);
						objectRemover.removeRoom(user, selectedHouse, selectedRoom);
					}
					else
						output.println(ControllerStrings.NO_ROOM);
					break;
				case 6:
					// aggiungi regola
					menuManager.clearOutput();
					log.write(Level.FINE, ControllerStrings.LOG_INSERT_NEW_RULE);
					maintainedUnitInputHandler.readRuleFromUser(user, selectedHouse, menuManager);
					log.write(Level.FINE, ControllerStrings.LOG_INSERT_NEW_RULE_SUCCESS);
					break;
				case 7:
					// visualizza regole attive
					menuManager.clearOutput();
					log.write(Level.FINE, ControllerStrings.LOG_SHOW_ENABLED_RULES);
					menuManager.printCollectionOfString(dataFacade.getEnabledRulesStrings(user, selectedHouse));
					break;
				case 8:
					// visualizza tutte le regole
					menuManager.clearOutput();
					log.write(Level.FINE, ControllerStrings.LOG_SHOW_ALL_RULES);
					menuManager.printCollectionOfString(dataFacade.getRulesStrings(user, selectedHouse));
					break;
				case 9:
					// attiva/disattiva regola
					menuManager.clearOutput();
					log.write(Level.FINE, ControllerStrings.LOG_ENABLE_DISABLE_RULE);
					maintainedUnitInputHandler.readRuleStateFromUser(user, selectedHouse, menuManager);
					break;
				case 10:
					// rimuovi regola
					menuManager.clearOutput();
					if (dataFacade.doesRuleExist(user, selectedHouse)) {
						String selectedRule = maintainedUnitInputHandler.safeInsertRule(user, selectedHouse);
						objectRemover.removeRule(user, selectedHouse, selectedRule);
					}
					else
						output.println(ControllerStrings.NO_RULE);
					break;
				case 11:
					// aggiorna ora
					log.write(Level.FINE, ControllerStrings.LOG_REFRESH_HOUR);
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
