package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.UserRoomInputHandler;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.view.ManageableRenderer;

import java.io.PrintWriter;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class UserRoomController {

	private MenuManager menuManager;

	private DataFacade dataFacade;
	private LogWriter log;
	private ManageableRenderer renderer;
	private UserRoomInputHandler userRoomInputHandler;
	private ClockStrategy clock;
	private PrintWriter output;

	public UserRoomController(DataFacade dataFacade, LogWriter log, ManageableRenderer renderer,
			UserRoomInputHandler userRoomInputHandler, ClockStrategy clock, PrintWriter output, RawInputHandler input) {
		menuManager = new MenuManager(ControllerStrings.USER_ROOM_MENU_TITLE, ControllerStrings.USER_ROOM_MENU_VOICES,
				output, input);
		this.dataFacade = dataFacade;
		this.log = log;
		this.renderer = renderer;
		this.userRoomInputHandler = userRoomInputHandler;
		this.clock = clock;
		this.output = output;
	}

	public void show(String user, String selectedHouse, String selectedRoom) {
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
					// visualizza descrizione stanza
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_SHOW_DESCR_ROOM + selectedRoom);
					output.println(renderer.render(dataFacade.getRoom(user, selectedHouse, selectedRoom)));
					break;
				case 2:
					// visualizza sensore
					menuManager.clearOutput();
					if (dataFacade.doesSensorExist(user, selectedHouse, selectedRoom)) {
						menuManager.printCollectionOfString(dataFacade.getSensorNames(user, selectedHouse, selectedRoom));
						output.println();
						output.println();
						String selectedSensor = userRoomInputHandler.safeInsertSensor(user, selectedHouse, selectedRoom);
						log.write(ControllerStrings.LOG_SHOW_SENSOR + selectedSensor);
						output.println(renderer.render(dataFacade.getSensor(user, selectedHouse, selectedSensor)));
					}
					else
						output.println(ControllerStrings.NO_SENSOR);
					break;
				case 3:
					// visualizza attuatore
					menuManager.clearOutput();
					if (dataFacade.doesActuatorExist(user, selectedHouse, selectedRoom)) {
						menuManager.printCollectionOfString(dataFacade.getActuatorNames(user, selectedHouse, selectedRoom));
						output.println();
						output.println();
						String selectedActuator = userRoomInputHandler.safeInsertActuator(user, selectedHouse, selectedRoom);
						log.write(ControllerStrings.LOG_SHOW_ACTUATOR + selectedActuator);
						output.println(renderer.render(dataFacade.getActuator(user, selectedHouse, selectedActuator)));
					}
					else
						output.println(ControllerStrings.NO_ACTUATOR);
					break;
				case 4:
					// aziona attuatore
					menuManager.clearOutput();
					if (dataFacade.doesActuatorExist(user, selectedHouse, selectedRoom)) {
						menuManager.printCollectionOfString(dataFacade.getActuatorNames(user, selectedHouse, selectedRoom));
						String selectedAct = userRoomInputHandler.safeInsertActuator(user, selectedHouse, selectedRoom);
						userRoomInputHandler.setOperatingMode(user, selectedHouse, selectedRoom, selectedAct, menuManager);
						log.write(ControllerStrings.LOG_ACTUATOR_ACTION + selectedAct);
					}
					else
						output.println(ControllerStrings.NO_ACTUATOR);
					break;
				case 5:
					// visualizza artefatto
					menuManager.clearOutput();
					if (dataFacade.doesArtifactExist(user, selectedHouse, selectedRoom)) {
						menuManager.printCollectionOfString(dataFacade.getArtifactNames(user, selectedHouse, selectedRoom));
						output.println();
						output.println();
						String selectedArtifact = userRoomInputHandler.safeInsertArtifact(user, selectedHouse, selectedRoom);
						log.write(ControllerStrings.LOG_SHOW_ARTIFACT);
						output.println(renderer.render(dataFacade.getArtifact(user, selectedHouse, selectedArtifact)));
					}
					else
						output.println(ControllerStrings.NO_ARTIFACT);
					break;
				case 6:
					// attiva/disattiva dispositivo
					userRoomInputHandler.readDeviceStateFromUser(user, selectedHouse, selectedRoom, menuManager);
					log.write(ControllerStrings.LOG_ENABLE_DISABLE_DISP);
					dataFacade.updateRulesState(user, selectedHouse);
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

}
