package it.unibs.ing.domohouse.controller.inputhandler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unibs.ing.domohouse.model.components.elements.Actuator;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.controller.ControllerStrings;

public class UserRoomInputHandler {

	protected DataFacade dataFacade;
	protected PrintWriter output;
	protected RawInputHandler input;

	public UserRoomInputHandler(DataFacade dataFacade, PrintWriter output, RawInputHandler input) {
		this.dataFacade = dataFacade;
		this.output = output;
		this.input = input;
	}

	public String safeInsertSensor(String user, String selectedHouse, String selectedRoom) {
		assert selectedHouse != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedSensor = input.readNotVoidString(ControllerStrings.INSERT_SENSOR);
		if (dataFacade.getRoom(user, selectedHouse, selectedRoom).hasSensor(selectedSensor))
			return selectedSensor;
		else
			do {
				selectedSensor = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_SENSOR
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_SENSOR);
			}
			while (!dataFacade.getRoom(user, selectedHouse, selectedRoom).hasSensor(selectedSensor));

		assert selectedSensor != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedSensor;
	}

	public String safeInsertActuator(String user, String selectedHouse, String selectedRoom) {
		assert selectedHouse != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedActuator = input.readNotVoidString(ControllerStrings.INSERT_ACTUATOR);
		if (dataFacade.getRoom(user, selectedHouse, selectedRoom).hasActuator(selectedActuator))
			return selectedActuator;
		else
			do {
				selectedActuator = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_ACTUATOR
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_ACTUATOR);
			}
			while (!dataFacade.getRoom(user, selectedHouse, selectedRoom).hasActuator(selectedActuator));

		assert selectedActuator != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedActuator;
	}

	public void setOperatingMode(String user, String selectedHouse, String selectedRoom, String selectedActuator,
			MenuManager view) {
		assert selectedHouse != null && selectedRoom != null && selectedActuator != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		Actuator act = dataFacade.getActuator(user, selectedHouse, selectedActuator);

		view.printCollectionOfString(act.getCategory().getOperatingModesSet());

		String op;
		do {
			op = input.readNotVoidString(ControllerStrings.INSERT_OPERATING_MODE);
			if (!act.getCategory().hasOperatingMode(op))
				output.println(ControllerStrings.ERR_OPERATING_MODE);
		}
		while (!act.getCategory().hasOperatingMode(op));

		List<String> parameters = new ArrayList<>();

		for (String paramName : OperatingModesManager.getOperatingMode(op).getParametersList())
			parameters.add(input.readNotVoidString(ControllerStrings.INPUT_PARAMETER_VALUE + paramName));

		act.setOperatingMode(op, parameters);
	}

	public String safeInsertArtifact(String user, String selectedHouse, String selectedRoom) {
		assert selectedHouse != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedArtifact = input.readNotVoidString(ControllerStrings.INSERT_ARTIFACT);
		if (dataFacade.getRoom(user, selectedHouse, selectedRoom).hasArtifact(selectedArtifact))
			return selectedArtifact;
		else
			do {
				selectedArtifact = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_ARTIFACT
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_ARTIFACT);
			}
			while (!dataFacade.getRoom(user, selectedHouse, selectedRoom).hasArtifact(selectedArtifact));

		assert selectedArtifact != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedArtifact;
	}

	public void readDeviceStateFromUser(String user, String selectedHouse, String selectedRoom, MenuManager view) {
		assert selectedHouse != null && selectedRoom != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		boolean choiceDev = input.yesOrNo(ControllerStrings.DEVICE_CHANGE_STATE);

		if (choiceDev) {
			if (!dataFacade.doesSensorExist(user, selectedHouse, selectedRoom))
				output.println(ControllerStrings.ROOM_NO_SENSORS);
			else {
				Set<String> sensors = dataFacade.getSensorNames(user, selectedHouse, selectedRoom);
				view.printCollectionOfString(sensors);
				String sensor;

				do {
					sensor = input.readNotVoidString(ControllerStrings.SENSOR_TO_ENABLE);
					if (!sensor.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER)) {
						if (!sensors.contains(sensor))
							output.println(ControllerStrings.NO_SUCH_SENSOR);
					}
					if (sensor.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER))
						break;
				}
				while (!sensors.contains(sensor));
				if (!sensor.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER))
					dataFacade.getSensor(user, selectedHouse, sensor).trigger();
			}
		}
		else {
			if (!dataFacade.doesActuatorExist(user, selectedHouse, selectedRoom))
				output.println(ControllerStrings.ROOM_NO_ACTUATORS);
			else {
				Set<String> actuators = dataFacade.getActuatorNames(user, selectedHouse, selectedRoom);
				view.printCollectionOfString(actuators);
				String actuator;
				do {
					actuator = input.readNotVoidString(ControllerStrings.ACTUATOR_TO_ENABLE);
					if (!actuator.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER)) {
						if (!actuators.contains(actuator))
							output.println(ControllerStrings.NO_SUCH_ACTUATOR);
					}
					if (actuator.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER))
						break;
				}
				while (!actuators.contains(actuator));
				if (!actuator.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER))
					dataFacade.getActuator(user, selectedHouse, actuator).trigger();
			}
		}
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	protected boolean userRoomInputHandlerInvariant() {
		return dataFacade != null;
	}
}
