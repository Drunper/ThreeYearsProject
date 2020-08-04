package it.unibs.ing.domohouse.controller.inputhandler;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MaintainerUnitInputHandler extends UserUnitInputHandler {

	public MaintainerUnitInputHandler(DataFacade dataFacade, PrintWriter output, RawInputHandler input) {
		super(dataFacade, output, input);
	}

	public void changeHouseDescription(String user, String selectedHouse) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String descr = input.readNotVoidString(ControllerStrings.HOUSE_INPUT_DESCRIPTION);
		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_SAVING))
			dataFacade.changeHouseDescription(user, selectedHouse, descr);

		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public void readRoomFromUser(String user, String selectedHouse, MenuManager view) throws Exception {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String name;
		do {
			name = input.readNotVoidString(ControllerStrings.ROOM_INPUT_NAME);
			if (dataFacade.hasRoomOrArtifact(user, selectedHouse, name))
				output.println(ControllerStrings.NAME_ALREADY_EXISTENT);
		}
		while (dataFacade.hasRoomOrArtifact(user, selectedHouse, name));
		String descr = input.readNotVoidString(ControllerStrings.ROOM_INPUT_DESCRIPTION);
		output.println(ControllerStrings.LIST_OF_PROPERTIES);
		view.printCollectionOfString(dataFacade.getPropertiesSet());

		Map<String, String> propertiesMap = new HashMap<>();

		boolean remain = true;
		String propertyName;
		do {
			propertyName = input.readStringWithMaximumLength(ControllerStrings.PROPERTY_INPUT_NAME, 20);
			if (!propertyName.equals(ControllerStrings.BACK_CHARACTER) && !propertiesMap.containsKey(propertyName)) {
				String propertyValue;
				if (dataFacade.hasProperty(propertyName)) {
					propertyValue = (input.yesOrNo(ControllerStrings.PROPERTY_INPUT_VALUE_QUESTION))
							? input.readStringWithMaximumLength(ControllerStrings.PROPERTY_INPUT_VALUE, 20)
							: dataFacade.getPropertyDefaultValue(propertyName);
				}
				else {
					propertyValue = input.readStringWithMaximumLength(ControllerStrings.PROPERTY_INPUT_VALUE, 20);
					dataFacade.addProperty(propertyName, propertyValue);
				}
				propertiesMap.put(propertyName, propertyValue);
			}
			else if (propertyName.equals(ControllerStrings.BACK_CHARACTER))
				remain = false;
		}
		while (remain);

		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
			dataFacade.addRoom(user, selectedHouse, name, descr, propertiesMap);

		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}
}
