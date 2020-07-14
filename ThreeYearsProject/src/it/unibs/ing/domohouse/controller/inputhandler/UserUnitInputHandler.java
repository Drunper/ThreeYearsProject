package it.unibs.ing.domohouse.controller.inputhandler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.properties.SensorCategory;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.ObjectFabricator;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.controller.ControllerStrings;

public class UserUnitInputHandler {

	protected DataFacade dataFacade;
	protected PrintWriter output;
	protected RawInputHandler input;
	protected ObjectFabricator objectFabricator;

	public UserUnitInputHandler(DataFacade dataFacade, ObjectFabricator objectFabricator, PrintWriter output,
			RawInputHandler input) {
		this.dataFacade = dataFacade;
		this.output = output;
		this.input = input;
		this.objectFabricator = objectFabricator;
	}

	public String safeInsertRoom(String selectedHouse) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedRoom = input.readNotVoidString(ControllerStrings.INSERT_ROOM);
		if (dataFacade.hasRoom(selectedHouse, selectedRoom))
			return selectedRoom;
		else
			do {
				selectedRoom = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_ROOM
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_ROOM);
			}
			while (!dataFacade.hasRoom(selectedHouse, selectedRoom));

		assert selectedRoom != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedRoom;
	}

	public void readRuleFromUser(String selectedHouse, MenuManager view) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String name;
		do {
			name = input.readNotVoidString(ControllerStrings.INPUT_RULE_NAME);
		}
		while (dataFacade.hasRule(selectedHouse, name));

		String antString = ControllerStrings.NULL_CHARACTER;
		String consString = ControllerStrings.NULL_CHARACTER;
		boolean cont = false;

		List<String> sensors = new ArrayList<>();
		List<String> actuators = new ArrayList<>();

		do {
			String superOp;

			if (input.yesOrNo(ControllerStrings.INPUT_RULE_CONDITION)) {
				String sensor;
				String info;

				view.printListOfString(dataFacade.getHousingUnit(selectedHouse).getSensorNames());
				sensor = safeInsertSensor(selectedHouse);
				sensors.add(sensor);

				SensorCategory category = dataFacade
						.getSensorCategory(dataFacade.getCategoryOfASensor(selectedHouse, sensor));
				Set<String> infos = category.getDetectableInfoList();

				view.printListOfString(infos);

				do {
					info = input.readNotVoidString(ControllerStrings.INPUT_INFO_TO_DETECT);
					if (!infos.contains(info))
						output.println(ControllerStrings.ERROR_INFO_NAME);
				}
				while (!infos.contains(info));

				String op;
				do {
					op = input.readNotVoidString(ControllerStrings.INPUT_OPERATOR);
					if (!(op.equals(">=") || op.equals("<=") || op.equals("<") || op.equals(">") || op.equals("!=")
							|| op.equals("==")))
						output.println(ControllerStrings.ERROR_OPERATOR);
				}
				while (!(op.equals(">=") || op.equals("<=") || op.equals("<") || op.equals(">") || op.equals("!=")
						|| op.equals("==")));

				double value = input.readDouble(ControllerStrings.INPUT_DESIRED_VALUE);
				antString = antString + sensor + "." + info + ControllerStrings.SPACE + op + ControllerStrings.SPACE
						+ value;
			}
			else { // in teoria di condizione temporale dovrebbe essercene solo una per il momento
					// lascio così
					// condizione temporale
				String op;
				do {
					op = input.readNotVoidString(ControllerStrings.INPUT_OPERATOR);
					if (!(op.equals(">=") || op.equals("<=") || op.equals("<") || op.equals(">") || op.equals("!=")
							|| op.equals("==")))
						output.println(ControllerStrings.ERROR_OPERATOR);
				}
				while (!(op.equals(">=") || op.equals("<=") || op.equals("<") || op.equals(">") || op.equals("!=")
						|| op.equals("==")));

				String time = readTime();

				antString = antString + "time" + ControllerStrings.SPACE + op + ControllerStrings.SPACE + time;
			}
			cont = input.yesOrNo(ControllerStrings.INPUT_NEW_COST);
			if (cont) {
				do {
					superOp = input.readNotVoidString(ControllerStrings.INPUT_COST_OPERATOR);
					if (!(superOp.equals("&&") || superOp.equals("||")))
						output.println(ControllerStrings.ERROR_OPERATOR);
				}
				while (!(superOp.equals("&&") || superOp.equals("||")));

				antString = antString + ControllerStrings.SPACE + superOp + ControllerStrings.SPACE;
			}
		}
		while (cont);

		// costruzione consString
		// b1_attCancelloBattente := apertura
		cont = false;

		do {
			view.printListOfString(dataFacade.getHousingUnit(selectedHouse).getActuatorNames());
			String actuator = safeInsertActuator(selectedHouse);

			view.printListOfString(dataFacade.getActuatorOperatingModes(selectedHouse, actuator));

			Set<String> modOp = dataFacade.getActuatorOperatingModes(selectedHouse, actuator);

			String operatingMode;
			do {
				operatingMode = input.readNotVoidString(ControllerStrings.INPUT_OPERATING_MODE);
				if (!modOp.contains(operatingMode))
					output.println(ControllerStrings.ERROR_OPERATING_MODE);
			}
			while (!modOp.contains(operatingMode));

			if (OperatingModesManager.getOperatingMode(operatingMode).isParametric()) {
				List<String> params = new ArrayList<>();
				for (String paramName : OperatingModesManager.getOperatingMode(operatingMode).getParametersList())
					params.add(input.readNotVoidString(ControllerStrings.INPUT_PARAMETER_VALUE + paramName));

				consString = actuator + " := " + operatingMode + ControllerStrings.OPEN_BRACKET
						+ String.join(",", params) + ControllerStrings.CLOSED_BRACKET;
			}
			else
				consString = actuator + " := " + operatingMode;

			cont = input.yesOrNo(ControllerStrings.INPUT_NEW_COST);
			if (cont)
				consString = consString + ", ";
		}
		while (cont);

		if (input.yesOrNo(ControllerStrings.ENABLE_ACTUATORS_SPECIFIC_TIME)) {
			String time = readTime();
			consString = "start :=" + time + "," + consString;
		}

		objectFabricator.createRule(selectedHouse, name, antString, consString, sensors, actuators);

		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public void readRuleStateFromUser(String selectedHouse, MenuManager view) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		if (!dataFacade.doesRuleExist(selectedHouse))
			output.println(ControllerStrings.NO_SUCH_RULES_IN_HOUSE);
		else {
			Set<String> rules = dataFacade.getRulesNames(selectedHouse);
			view.printListOfString(rules);
			String rule;

			do {
				rule = input.readNotVoidString(ControllerStrings.INPUT_DISABLE_RULE);
				if (!rule.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER)) {
					if (!rules.contains(rule))
						output.println(ControllerStrings.NO_SUCH_RULE);
				}
				if (rule.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER))
					break;
			}
			while (!rules.contains(rule));

			dataFacade.changeRuleState(selectedHouse, rule);
		}
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public String safeInsertSensor(String selectedHouse) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedSensor = input.readNotVoidString(ControllerStrings.INSERT_SENSOR);
		if (dataFacade.hasSensor(selectedHouse, selectedSensor))
			return selectedSensor;
		else
			do {
				selectedSensor = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_SENSOR
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_SENSOR);
			}
			while (!dataFacade.hasSensor(selectedHouse, selectedSensor));

		assert selectedSensor != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedSensor;
	}

	public String safeInsertActuator(String selectedHouse) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedActuator = input.readNotVoidString(ControllerStrings.INSERT_ACTUATOR);
		if (dataFacade.hasActuator(selectedHouse, selectedActuator))
			return selectedActuator;
		else
			do {
				selectedActuator = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_ACTUATOR
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_ACTUATOR);
			}
			while (!dataFacade.hasActuator(selectedHouse, selectedActuator));

		assert selectedActuator != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedActuator;
	}

	private String readTime() {
		String time;
		int hour = -1;
		int minute = -1;
		do {
			time = input.readNotVoidString(ControllerStrings.INPUT_TIME);
			if (time.contains(".")) {
				try {
					hour = Integer.parseInt(time.split("\\.")[0]);
					minute = Integer.parseInt(time.split("\\.")[1]);
				}
				catch (Exception ex) {
					hour = -1;
					minute = -1;
				}
			}
			if (!time.contains(".") || hour > 23 || hour < 0 || minute > 59 || minute < 0)
				output.println(ControllerStrings.INVALID_TIME);
		}
		while (!time.contains(".") || hour > 23 || hour < 0 || minute > 59 || minute < 0);
		return time;
	}

	protected boolean inputHandlerInvariant() {
		if (dataFacade != null)
			return true;
		return false;
	}
}
