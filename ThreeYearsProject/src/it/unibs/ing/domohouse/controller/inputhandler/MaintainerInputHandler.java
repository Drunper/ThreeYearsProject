package it.unibs.ing.domohouse.controller.inputhandler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibs.ing.domohouse.model.components.properties.InfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.DoubleInfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.StringInfoStrategy;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MaintainerInputHandler extends UserInputHandler {

	public MaintainerInputHandler(DataFacade dataFacade, PrintWriter output, RawInputHandler input) {
		super(dataFacade, output, input);
	}

	public void readUser() throws Exception {
		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String user;
		boolean remain = true;
		do {
			user = input.readNotVoidString(ControllerStrings.USER_INPUT_NAME);
			if (dataFacade.hasUser(user))
				output.println(ControllerStrings.ERROR_USER_ALREADY_EXISTENT);
			else
				remain = false;
		}
		while (remain);

		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
			dataFacade.addUser(user);
	}

	public void readHouseFromUser() throws Exception {
		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String user;
		boolean remain = true;
		do {
			user = input.readNotVoidString(ControllerStrings.HOUSE_INPUT_USER);
			if (!dataFacade.hasUser(user))
				output.println(ControllerStrings.ERROR_NON_EXISTENT_USER);
			else
				remain = false;
		}
		while (remain);

		dataFacade.loadHousingUnits(user);

		String name;
		do {
			name = input.readStringWithMaximumLength(ControllerStrings.HOUSE_INPUT_NAME, 30);
			if (dataFacade.hasHousingUnit(user, name))
				output.println(ControllerStrings.NAME_ALREADY_EXISTENT);
		}
		while (dataFacade.hasHousingUnit(user, name));

		String descr = input.readNotVoidString(ControllerStrings.HOUSE_INPUT_DESCRIPTION);
		String type = input.readNotVoidString(ControllerStrings.HOUSE_INPUT_TYPE);
		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
			dataFacade.addHousingUnit(user, name, descr, type);

		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public void readSensorCategoryFromUser(MenuManager view) throws Exception {
		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String name;
		boolean remain = true;
		do {
			name = input.readNotVoidString(ControllerStrings.SENSOR_CATEGORY_INPUT_NAME);
			if (!dataFacade.hasSensorCategory(name))
				remain = false;
			else
				output.println(ControllerStrings.NAME_ALREADY_EXISTENT);
		}
		while (remain);
		name = name.replace(ControllerStrings.UNDERSCORE, ControllerStrings.NULL_CHARACTER); // il nome non può
																								// contenere underscore
		String abbreviation = input.readNotVoidString(ControllerStrings.INPUT_CATEGORY_ABBREVIATION);
		String manufacturer = input.readNotVoidString(ControllerStrings.INPUT_CATEGORY_MANUFACTURER);

		Map<String, InfoStrategy> infoDomainMap = new HashMap<>();
		Map<String, String> measurementUnitMap = new HashMap<>();

		readInfoStrategies(infoDomainMap, measurementUnitMap, view);

		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
			dataFacade.addSensorCategory(name, abbreviation, manufacturer, infoDomainMap, measurementUnitMap);

		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	private void readInfoStrategies(Map<String, InfoStrategy> infoDomainMap, Map<String, String> measurementUnitMap,
			MenuManager view) throws Exception {
		boolean remain = true;

		do {
			if (input.yesOrNo(ControllerStrings.INSERT_NUMERIC_INFO)) {
				view.printCollectionOfString(dataFacade.getNumericInfoStrategySet());
				if (input.yesOrNo(ControllerStrings.INSERT_INFO_IN_LIST))
					remain = readNumericInfoFromList(infoDomainMap, measurementUnitMap);
				else 
					remain = readNumericInfo(infoDomainMap, measurementUnitMap);
			}
			else {
				view.printCollectionOfString(dataFacade.getNonNumericInfoStrategySet());
				if (input.yesOrNo(ControllerStrings.INSERT_INFO_IN_LIST))
					remain = readNonNumericInfoFromList(infoDomainMap);
				else
					remain = readNonNumericInfo(infoDomainMap);
			}
		}
		while (remain);
	}

	private boolean readNonNumericInfo(Map<String, InfoStrategy> infoDomainMap) throws Exception {
		String detectableInfo = input.readNotVoidString(ControllerStrings.SENSOR_CATEGORY_INPUT_INFO);
		if (!dataFacade.hasProperty(detectableInfo)) {
			String defaultValue = input
					.readStringWithMaximumLength(ControllerStrings.NOT_IN_DB_PROPERTY_INPUT_VALUE, 20);
			dataFacade.addProperty(detectableInfo, defaultValue);
		}
		if (!infoDomainMap.containsKey(detectableInfo)) {
			List<String> domain = new ArrayList<>();
			String domainValue;
			boolean remain = true;
			do {
				domainValue = input.readNotVoidString(ControllerStrings.INPUT_NON_NUMERIC_DOMAIN);
				if (domainValue.equals(ControllerStrings.BACK_CHARACTER))
					if (!domain.isEmpty())
						remain = false;
					else
						output.println(ControllerStrings.DOMAIN_VALUE_REQUIRED);
				else if (domain.contains(domainValue))
					output.println(ControllerStrings.ERROR_DOMAIN_VALUE_ALREADY_INSERTED);
				else
					domain.add(domainValue);
			}
			while (remain);
			
			int dbID = dataFacade.getCurrentMaxID();
			StringInfoStrategy domainInfo = new StringInfoStrategy(domain, ++dbID, detectableInfo);
			dataFacade.addNonNumericInfoStrategy(dbID, domainInfo);
			infoDomainMap.put(detectableInfo, domainInfo);
			if (!input.yesOrNo(ControllerStrings.INSERT_ANOTHER_INFO))
				return false;
		}
		else
			output.println(ControllerStrings.ERROR_INFO_ALREADY_INSERTED);
		return true;
	}

	private boolean readNumericInfo(Map<String, InfoStrategy> infoDomainMap, Map<String, String> measurementUnitMap) throws Exception {
		String detectableInfo = input.readNotVoidString(ControllerStrings.SENSOR_CATEGORY_INPUT_INFO);
		if (!dataFacade.hasProperty(detectableInfo)) {
			String defaultValue = input
					.readStringWithMaximumLength(ControllerStrings.NOT_IN_DB_PROPERTY_INPUT_VALUE, 20);
			dataFacade.addProperty(detectableInfo, defaultValue);
		}
		if (!infoDomainMap.containsKey(detectableInfo)) {
			double min = input.readDouble(ControllerStrings.INSERT_SENSOR_CATEGORY_MIN_VALUE);
			double max = input.readDouble(ControllerStrings.INSERT_SENSOR_CATEGORY_MAX_VALUE);
			String measurementUnit = input
					.readNotVoidString(ControllerStrings.SENSOR_CATEGORY_DETECTABLE_INFO);

			int dbID = dataFacade.getCurrentMaxID();
			DoubleInfoStrategy domainInfo = new DoubleInfoStrategy(min, max, ++dbID, detectableInfo);
			dataFacade.addNumericInfoStrategy(dbID, domainInfo);
			infoDomainMap.put(detectableInfo, domainInfo);
			measurementUnitMap.put(detectableInfo, measurementUnit);
			if (!input.yesOrNo(ControllerStrings.INSERT_ANOTHER_INFO))
				return false;
		}
		else
			output.println(ControllerStrings.ERROR_INFO_ALREADY_INSERTED);
		return true;
	}

	private boolean readNonNumericInfoFromList(Map<String, InfoStrategy> infoDomainMap) {
		int ID = input.readInt(ControllerStrings.INPUT_INFO_ID);
		if (dataFacade.hasNonNumericInfoStrategy(ID)) {
			StringInfoStrategy infoStrategy = dataFacade.getNonNumericInfoStrategy(ID);
			if (!infoDomainMap.containsKey(infoStrategy.getMeasuredProperty())) {
				infoDomainMap.put(infoStrategy.getMeasuredProperty(), infoStrategy);
				if (!input.yesOrNo(ControllerStrings.INSERT_ANOTHER_INFO))
					return false;
			}
			else
				output.println(ControllerStrings.ERROR_INFO_ALREADY_INSERTED);
		}
		else
			output.println(ControllerStrings.ERROR_INFO_NOT_IN_DB);
		return true;
	}

	private boolean readNumericInfoFromList(Map<String, InfoStrategy> infoDomainMap,
			Map<String, String> measurementUnitMap) {
		int ID = input.readInt(ControllerStrings.INPUT_INFO_ID);
		if (dataFacade.hasNumericInfoStrategy(ID)) {
			DoubleInfoStrategy infoStrategy = dataFacade.getNumericInfoStrategy(ID);
			if (!infoDomainMap.containsKey(infoStrategy.getMeasuredProperty())) {
				infoDomainMap.put(infoStrategy.getMeasuredProperty(), infoStrategy);
				measurementUnitMap.put(infoStrategy.getMeasuredProperty(),
						input.readNotVoidString(ControllerStrings.SENSOR_CATEGORY_DETECTABLE_INFO));
				if (!input.yesOrNo(ControllerStrings.INSERT_ANOTHER_INFO))
					return false;
			}
			else
				output.println(ControllerStrings.ERROR_INFO_ALREADY_INSERTED);
		}
		else
			output.println(ControllerStrings.ERROR_INFO_NOT_IN_DB);
		return true;
	}

	public void readActuatorCategoryFromUser() {
		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String name;
		do {
			name = input.readNotVoidString(ControllerStrings.ACTUATOR_CATEGORY_INPUT_NAME);
			if (dataFacade.hasActuatorCategory(name))
				output.println(ControllerStrings.NAME_ALREADY_EXISTENT);
		}
		while (dataFacade.hasActuatorCategory(name));
		name = name.replace(ControllerStrings.UNDERSCORE, ControllerStrings.NULL_CHARACTER); // il nome non può
																								// contenere underscore

		String abbreviation = input.readNotVoidString(ControllerStrings.INPUT_CATEGORY_ABBREVIATION);
		String constructor = input.readNotVoidString(ControllerStrings.INPUT_CATEGORY_MANUFACTURER);
		ArrayList<String> listOfModes = new ArrayList<>();
		String temp;
		do {
			temp = input.readNotVoidString(ControllerStrings.ACTUATOR_CATEGORY_INPUT_OPERATING_MODE);
			if (!temp.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER)) {
				if (dataFacade.hasOperatingMode(temp))
					listOfModes.add(temp);
				else
					output.println(ControllerStrings.OPERATING_MODE_NOT_SUPPORTED);
			}
		}
		while (!temp.equalsIgnoreCase(ControllerStrings.BACK_CHARACTER));
		String defaultMode = input.readNotVoidString(ControllerStrings.ACTUATOR_CATEGORY_INPUT_DEFAULT_MODE);

		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
			dataFacade.addActuatorCategory(name, abbreviation, constructor, listOfModes, defaultMode);

		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public String safeInsertUser() throws Exception {
		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedUser = input.readNotVoidString(ControllerStrings.INSERT_USER_DB);
		if (dataFacade.hasUser(selectedUser))
			return selectedUser;
		else
			do {
				selectedUser = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_USER
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_USER_DB);
			}
			while (!dataFacade.hasUser(selectedUser));

		assert selectedUser != null;
		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedUser;
	}

	private boolean maintainerInputHandlerInvariant() {
		return userInputHandlerInvariant();
	}
}
