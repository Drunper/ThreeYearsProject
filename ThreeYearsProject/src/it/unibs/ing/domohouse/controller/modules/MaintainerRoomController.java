package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.MaintainerRoomInputHandler;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.model.util.ObjectRemover;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.view.ManageableRenderer;

import java.io.PrintWriter;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MaintainerRoomController {

	private MenuManager menuManager;
	private ManageableRenderer renderer;

	private DataFacade dataFacade;
	private ObjectRemover objectRemover;
	private LogWriter log;
	private ClockStrategy clock;
	private PrintWriter output;

	private MaintainerRoomInputHandler maintainerRoomInputHandler;

	public MaintainerRoomController(DataFacade dataFacade, ObjectRemover objectRemover,
			MaintainerRoomInputHandler maintainerRoomInputHandler, LogWriter log, ManageableRenderer renderer,
			ClockStrategy clock, PrintWriter output, RawInputHandler input) {
		menuManager = new MenuManager(ControllerStrings.MAINTAINER_ROOM_MENU_TITLE,
				ControllerStrings.MAINTAINER_ROOM_VOICES, output, input);
		this.dataFacade = dataFacade;
		this.objectRemover = objectRemover;
		this.log = log;
		this.renderer = renderer;
		this.maintainerRoomInputHandler = maintainerRoomInputHandler;
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
						menuManager
								.printCollectionOfString(dataFacade.getSensorNames(user, selectedHouse, selectedRoom));
						output.println();
						output.println();
						String selectedSensor = maintainerRoomInputHandler.safeInsertSensor(user, selectedHouse,
								selectedRoom);
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
						menuManager.printCollectionOfString(
								dataFacade.getActuatorNames(user, selectedHouse, selectedRoom));
						output.println();
						output.println();
						String selectedActuator = maintainerRoomInputHandler.safeInsertActuator(user, selectedHouse,
								selectedRoom);
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
						menuManager.printCollectionOfString(
								dataFacade.getActuatorNames(user, selectedHouse, selectedRoom));
						String selectedAct = maintainerRoomInputHandler.safeInsertActuator(user, selectedHouse,
								selectedRoom);
						maintainerRoomInputHandler.setOperatingMode(user, selectedHouse, selectedRoom, selectedAct,
								menuManager);
						log.write(ControllerStrings.LOG_ACTUATOR_ACTION + selectedAct);
						dataFacade.updateRulesState(user, selectedHouse);
					}
					else
						output.println(ControllerStrings.NO_ACTUATOR);
					break;
				case 5:
					// visualizza artefatto
					menuManager.clearOutput();
					if (dataFacade.doesArtifactExist(user, selectedHouse, selectedRoom)) {
						menuManager.printCollectionOfString(
								dataFacade.getArtifactNames(user, selectedHouse, selectedRoom));
						output.println();
						output.println();
						String selectedArtifact = maintainerRoomInputHandler.safeInsertArtifact(user, selectedHouse,
								selectedRoom);
						log.write(ControllerStrings.LOG_SHOW_ARTIFACT);
						output.println(renderer.render(dataFacade.getArtifact(user, selectedHouse, selectedArtifact)));
					}
					else
						output.println(ControllerStrings.NO_ARTIFACT);
					break;
				case 6:
					// modifica descrizione stanza
					log.write(ControllerStrings.LOG_CHANGE_ROOM_DESCR);
					maintainerRoomInputHandler.changeRoomDescription(user, selectedHouse, selectedRoom);
					break;
				case 7:
					// inserisci sensore
					log.write(ControllerStrings.LOG_INSERT_SENSOR);
					maintainerRoomInputHandler.readSensorFromUser(user, selectedHouse, selectedRoom);
					log.write(ControllerStrings.LOG_INSERT_SENSOR_SUCCESS);
					break;
				case 8:
					// inserisci attuatore
					log.write(ControllerStrings.LOG_INSERT_ACTUATOR);
					maintainerRoomInputHandler.readActuatorFromUser(user, selectedHouse, selectedRoom);
					log.write(ControllerStrings.LOG_INSERT_ACTUATOR_SUCCESS);
					break;
				case 9:
					// inserisci artefatto
					log.write(ControllerStrings.LOG_INSERT_ARTIFACT);
					maintainerRoomInputHandler.readArtifactFromUser(user, selectedHouse, selectedRoom, menuManager);
					log.write(ControllerStrings.LOG_INSERT_ARTIFACT_SUCCESS);
					break;
				case 10:
					// rimozione sensore
					menuManager.clearOutput();
					if (dataFacade.doesSensorExist(user, selectedHouse, selectedRoom)) {
						menuManager
								.printCollectionOfString(dataFacade.getSensorNames(user, selectedHouse, selectedRoom));
						output.println();
						output.println();
						String selectedSensor = maintainerRoomInputHandler.safeInsertSensor(user, selectedHouse,
								selectedRoom);
						objectRemover.removeSensor(user, selectedHouse, selectedRoom, selectedSensor);
					}
					else
						output.println(ControllerStrings.NO_SENSOR);
					break;
				case 11:
					// rimozione attuatore
					menuManager.clearOutput();
					if (dataFacade.doesActuatorExist(user, selectedHouse, selectedRoom)) {
						menuManager.printCollectionOfString(
								dataFacade.getActuatorNames(user, selectedHouse, selectedRoom));
						output.println();
						output.println();
						String selectedActuator = maintainerRoomInputHandler.safeInsertActuator(user, selectedHouse,
								selectedRoom);
						objectRemover.removeActuator(user, selectedHouse, selectedRoom, selectedActuator);
					}
					else
						output.println(ControllerStrings.NO_ACTUATOR);
					break;
				case 12:
					// rimozione artefatto
					menuManager.clearOutput();
					if (dataFacade.doesArtifactExist(user, selectedHouse, selectedRoom)) {
						menuManager.printCollectionOfString(
								dataFacade.getArtifactNames(user, selectedHouse, selectedRoom));
						output.println();
						output.println();
						String selectedArtifact = maintainerRoomInputHandler.safeInsertArtifact(user, selectedHouse,
								selectedRoom);
						objectRemover.removeArtifact(user, selectedHouse, selectedRoom, selectedArtifact);
					}
					else
						output.println(ControllerStrings.NO_ARTIFACT);
					break;
				case 13:
					// attiva/dis disp
					maintainerRoomInputHandler.readDeviceStateFromUser(user, selectedHouse, selectedRoom, menuManager);
					log.write(ControllerStrings.LOG_ENABLE_DISABLE_DISP);
					dataFacade.updateRulesState(user, selectedHouse);
					break;
				case 14:
					// aggiorna ora
					log.write(ControllerStrings.LOG_REFRESH_HOUR);
					menuManager.clearOutput();
					break;
			}
		}
		while (selection != 0);
	}
}
