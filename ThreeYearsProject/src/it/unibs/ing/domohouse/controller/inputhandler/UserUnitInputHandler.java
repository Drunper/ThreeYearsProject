package it.unibs.ing.domohouse.controller.inputhandler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.properties.SensorCategory;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.controller.ControllerStrings;

public class UserUnitInputHandler {

	protected DataFacade dataFacade;
	protected PrintWriter output;
	protected RawInputHandler input;
	private List<String> operators;
	
	public UserUnitInputHandler(DataFacade dataFacade, PrintWriter output, RawInputHandler input) {
		this.dataFacade = dataFacade;
		this.output = output;
		this.input = input;
		operators = new ArrayList<>(Arrays.asList(ControllerStrings.REL_OPERATORS));
	}

	public String safeInsertRoom(String user, String selectedHouse) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedRoom = input.readNotVoidString(ControllerStrings.INSERT_ROOM);
		if (dataFacade.hasRoom(user, selectedHouse, selectedRoom))
			return selectedRoom;
		else
			do {
				selectedRoom = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_ROOM
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_ROOM);
			}
			while (!dataFacade.hasRoom(user, selectedHouse, selectedRoom));

		assert selectedRoom != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedRoom;
	}

	public String safeInsertRule(String user, String selectedHouse) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedRule = input.readNotVoidString(ControllerStrings.INSERT_RULE);
		if (dataFacade.hasRule(user, selectedHouse, selectedRule))
			return selectedRule;
		else
			do {
				selectedRule = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_RULE
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_RULE);
			}
			while (!dataFacade.hasRule(user, selectedHouse, selectedRule));

		assert selectedRule != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedRule;
	}

	public void readRuleFromUser(String user, String selectedHouse, MenuManager view) throws Exception {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String name;
		do {
			name = input.readNotVoidString(ControllerStrings.INPUT_RULE_NAME);
		}
		while (dataFacade.hasRule(user, selectedHouse, name));

		Set<String> sensors = new HashSet<>();
		Set<String> actuators = new HashSet<>();

		try {
			dataFacade.addRule(user, selectedHouse, name, readAntecedent(user, selectedHouse, view, sensors),
					readConsequent(user, selectedHouse, view, actuators), sensors, actuators);
		}
		catch (Exception e) {
			throw new Exception("Errore durante l'aggiunta della regola", e);
		}

		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	private String readConsequent(String user, String selectedHouse, MenuManager view, Set<String> actuators) {
		String consString = ControllerStrings.NULL_CHARACTER;
		boolean cont;
		do {
			view.printCollectionOfString(dataFacade.getHousingUnit(user, selectedHouse).getActuatorNames());
			String actuator = safeInsertActuator(user, selectedHouse);
			actuators.add(actuator);

			view.printCollectionOfString(dataFacade.getActuatorOperatingModes(user, selectedHouse, actuator));

			Set<String> modOp = dataFacade.getActuatorOperatingModes(user, selectedHouse, actuator);

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
			consString = "start :=" + readTime() + "," + consString;
		}
		return consString;
	}

	public String readAntecedent(String user, String selectedHouse, MenuManager view,
			Set<String> sensors) {
		boolean cont;
		boolean temporalCondition = false;
		String antString = ControllerStrings.NULL_CHARACTER;
		do {
			String superOp;

			if (input.yesOrNo(ControllerStrings.INPUT_RULE_CONDITION)) {
				antString = readNonTemporalCondition(user, selectedHouse, view, sensors, antString);
			}
			else if(!temporalCondition) { 
				String op;
				do {
					op = input.readNotVoidString(ControllerStrings.INPUT_OPERATOR);
					if (!operators.contains(op))
						output.println(ControllerStrings.ERROR_OPERATOR);
				}
				while (!operators.contains(op));

				antString = antString + "time" + ControllerStrings.SPACE + op + ControllerStrings.SPACE + readTime();
			}
			else {
				output.println(ControllerStrings.ERROR_CONDITION_ALREADY_INSERTED);
				antString = antString.substring(0, antString.length()-4);
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
			temporalCondition = true;
		}
		while (cont);
		return antString;
	}

	private String readNonTemporalCondition(String user, String selectedHouse, MenuManager view, Set<String> sensors,
			String antString) {
		String sensor;
		String info;
		view.printCollectionOfString(dataFacade.getHousingUnit(user, selectedHouse).getSensorNames());
		sensor = safeInsertSensor(user, selectedHouse);
		sensors.add(sensor);
		SensorCategory category = dataFacade
				.getSensorCategory(dataFacade.getCategoryOfASensor(user, selectedHouse, sensor));
		Set<String> infos = category.getInfoStrategySet();
		view.printCollectionOfString(infos);

		do {
			info = input.readNotVoidString(ControllerStrings.INPUT_INFO_TO_DETECT);
			if (!infos.contains(info))
				output.println(ControllerStrings.ERROR_INFO_NAME);
		}
		while (!infos.contains(info));

		String op;
		do {
			op = input.readNotVoidString(ControllerStrings.INPUT_OPERATOR);
			if (!operators.contains(op))
				output.println(ControllerStrings.ERROR_OPERATOR);
		}
		while (!operators.contains(op));

		String result;
		
		if(input.yesOrNo(ControllerStrings.INPUT_ANOTHER_SENSORY_VARIABLE)) {
			String sensor2;
			String info2;
			view.printCollectionOfString(dataFacade.getHousingUnit(user, selectedHouse).getSensorNames());
			sensor2 = safeInsertSensor(user, selectedHouse);
			sensors.add(sensor2);
			SensorCategory category2 = dataFacade
					.getSensorCategory(dataFacade.getCategoryOfASensor(user, selectedHouse, sensor2));
			Set<String> infos2 = category2.getInfoStrategySet();
			view.printCollectionOfString(infos2);

			do {
				info2 = input.readNotVoidString(ControllerStrings.INPUT_INFO_TO_DETECT);
				if (!infos2.contains(info2))
					output.println(ControllerStrings.ERROR_INFO_NAME);
			}
			while (!infos2.contains(info2));
			result = antString + sensor + "." + info + ControllerStrings.SPACE + op + ControllerStrings.SPACE
					+ sensor2 + "." + info2;
		}
		else {
			String value;
			value = input.readNotVoidString(ControllerStrings.INPUT_DESIRED_VALUE);
			result = antString + sensor + "." + info + ControllerStrings.SPACE + op + ControllerStrings.SPACE
					+ value;
		}
		return result;
	}

	public void readRuleStateFromUser(String user, String selectedHouse, MenuManager view) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		if (!dataFacade.doesRuleExist(user, selectedHouse))
			output.println(ControllerStrings.NO_SUCH_RULES_IN_HOUSE);
		else {
			Set<String> rules = dataFacade.getRulesNames(user, selectedHouse);
			view.printCollectionOfString(rules);
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

			dataFacade.changeRuleState(user, selectedHouse, rule);
		}
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public String safeInsertSensor(String user, String selectedHouse) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedSensor = input.readNotVoidString(ControllerStrings.INSERT_SENSOR);
		if (dataFacade.hasSensor(user, selectedHouse, selectedSensor))
			return selectedSensor;
		else
			do {
				selectedSensor = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_SENSOR
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_SENSOR);
			}
			while (!dataFacade.hasSensor(user, selectedHouse, selectedSensor));

		assert selectedSensor != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedSensor;
	}

	public String safeInsertActuator(String user, String selectedHouse) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedActuator = input.readNotVoidString(ControllerStrings.INSERT_ACTUATOR);
		if (dataFacade.hasActuator(user, selectedHouse, selectedActuator))
			return selectedActuator;
		else
			do {
				selectedActuator = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_ACTUATOR
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_ACTUATOR);
			}
			while (!dataFacade.hasActuator(user, selectedHouse, selectedActuator));

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
				catch (NumberFormatException e) {
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
