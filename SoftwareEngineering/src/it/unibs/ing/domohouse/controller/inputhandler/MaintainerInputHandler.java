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
import it.unibs.ing.domohouse.model.util.ObjectFabricator;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MaintainerInputHandler extends UserInputHandler {

	private ObjectFabricator objectFabricator;

	public MaintainerInputHandler(DataFacade dataFacade, ObjectFabricator objectFabricator, PrintWriter output,
			RawInputHandler input) {
		super(dataFacade, output, input);
		this.objectFabricator = objectFabricator;
	}

	public void readHouseFromUser() {
		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String name;
		do {
			name = input.readNotVoidString(ControllerStrings.HOUSE_INPUT_NAME);
			if (dataFacade.hasHousingUnit(name))
				output.println(ControllerStrings.NAME_ALREADY_EXISTENT);
		}
		while (dataFacade.hasHousingUnit(name));

		String descr = input.readNotVoidString(ControllerStrings.HOUSE_INPUT_DESCRIPTION);
		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
			objectFabricator.createHouse(name, descr);

		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public void readSensorCategoryFromUser() {
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

		remain = true;
		if (input.yesOrNo(ControllerStrings.INSERT_NUMERIC_INFO)) {
			do {
				String detectableInfo = input.readNotVoidString(ControllerStrings.SENSOR_CATEGORY_INPUT_INFO);
				if (!infoDomainMap.containsKey(detectableInfo)) {
					double min = input.readDouble(ControllerStrings.INSERT_SENSOR_CATEGORY_MIN_VALUE);
					double max = input.readDouble(ControllerStrings.INSERT_SENSOR_CATEGORY_MAX_VALUE);
					String measurementUnit = input.readNotVoidString(ControllerStrings.SENSOR_CATEGORY_DETECTABLE_INFO);

					InfoStrategy domainInfo = new DoubleInfoStrategy(min, max);
					infoDomainMap.put(detectableInfo, domainInfo);
					measurementUnitMap.put(detectableInfo, measurementUnit);
					if (!input.yesOrNo(ControllerStrings.INSERT_ANOTHER_INFO))
						remain = false;
				}
				else
					output.println(ControllerStrings.ERROR_INFO_ALREADY_INSERTED);
			}
			while (remain);
		}
		else {
			do {
				String detectableInfo = input.readNotVoidString(ControllerStrings.SENSOR_CATEGORY_INPUT_INFO);
				if (!infoDomainMap.containsKey(detectableInfo)) {
					List<String> domain = new ArrayList<>();
					String s;
					boolean remainTwo = true;
					do {
						s = input.readNotVoidString(ControllerStrings.INPUT_NON_NUMERIC_DOMAIN);
						if (s.equals(ControllerStrings.BACK_CHARACTER))
							if (!domain.isEmpty())
								remainTwo = false;
							else
								output.println(ControllerStrings.DOMAIN_VALUE_REQUIRED);
						else if (domain.contains(s))
							output.println(ControllerStrings.ERROR_DOMAIN_VALUE_ALREADY_INSERTED);
						else
							domain.add(s);
					}
					while (remainTwo);
					InfoStrategy domainInfo = new StringInfoStrategy(domain);
					infoDomainMap.put(detectableInfo, domainInfo);
					if (!input.yesOrNo(ControllerStrings.INSERT_ANOTHER_INFO))
						remain = false;
				}
				else
					output.println(ControllerStrings.ERROR_INFO_ALREADY_INSERTED);
			}
			while (remain);
		}

		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
			objectFabricator.createSensorCategory(name, abbreviation, manufacturer, infoDomainMap, measurementUnitMap);

		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
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
			objectFabricator.createActuatorCategory(name, abbreviation, constructor, listOfModes, defaultMode);

		assert maintainerInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	private boolean maintainerInputHandlerInvariant() {
		return userInputHandlerInvariant() && objectFabricator != null;
	}
}
