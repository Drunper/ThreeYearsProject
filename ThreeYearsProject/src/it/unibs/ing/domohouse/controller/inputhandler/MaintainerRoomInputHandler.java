package it.unibs.ing.domohouse.controller.inputhandler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MaintainerRoomInputHandler extends UserRoomInputHandler {

	public MaintainerRoomInputHandler(DataFacade dataFacade, PrintWriter output, RawInputHandler input) {
		super(dataFacade, output, input);
	}

	public void changeRoomDescription(String user, String selectedHouse, String selectedRoom) {
		assert selectedHouse != null && selectedRoom != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String descr = input.readNotVoidString(ControllerStrings.ROOM_INPUT_DESCRIPTION);
		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_SAVING))
			dataFacade.changeRoomDescription(user, selectedHouse, selectedRoom, descr);

		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public void readSensorFromUser(String user, String selectedHouse, String location) {
		assert selectedHouse != null && location != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		if (dataFacade.doesSensorCategoryExist()) {
			String category;
			boolean remain = true;

			do {
				category = input.readNotVoidString(ControllerStrings.INSERT_CATEGORY);
				if (dataFacade.hasSensorCategory(category))
					remain = false;
				else
					output.println(ControllerStrings.CATEGORY_NON_EXISTENT);
			}
			while (remain);

			String name;
			String toCheck;
			remain = true;
			do {
				name = input.readNotVoidString(ControllerStrings.SENSOR_INPUT_NAME);
				toCheck = name + ControllerStrings.UNDERSCORE + category;
				if (!dataFacade.hasSensor(user, selectedHouse, toCheck)) {
					remain = false;
					name = toCheck;
				}
				else
					output.println(ControllerStrings.SENSOR_NAME_ASSIGNED);
			}
			while (remain);

			boolean roomOrArtifact = input.yesOrNo(ControllerStrings.SENSOR_ARTIFACT_OR_ROOM_ASSOCIATION);
			if (checkCategoryAssociations(user, selectedHouse, category, roomOrArtifact)) {
				List<String> objectList = readDeviceAssociationObjects(user, selectedHouse, category, roomOrArtifact);
				if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
					dataFacade.addSensor(user, selectedHouse, location, name, category, roomOrArtifact, objectList);
			}
		}
		else
			output.println(ControllerStrings.NO_SENSOR_CATEGORY_ERROR);
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	private boolean checkCategoryAssociations(String user, String selectedHouse, String category,
			boolean roomOrArtifact) {
		boolean ok = false;
		if (roomOrArtifact) {
			if (!dataFacade.doesRoomExist(user, selectedHouse))
				output.println(ControllerStrings.NO_ASSOCIABLE_ELEMENT);
			else if (!dataFacade.hasAssociableRooms(user, selectedHouse, category))
				output.println(ControllerStrings.NO_ROOM_TO_ASSOC);
			else
				ok = true;
		}
		else {
			if (!dataFacade.doesArtifactExist(user, selectedHouse))
				output.println(ControllerStrings.NO_ASSOCIABLE_ELEMENT);
			else if (!dataFacade.hasAssociableArtifacts(user, selectedHouse, category))
				output.println(ControllerStrings.NO_ARTIFACT_TO_ASSOC);
			else
				ok = true;
		}
		return ok;
	}

	public void readArtifactFromUser(String user, String selectedHouse, String location, MenuManager view)
			throws Exception {
		assert selectedHouse != null;
		assert location != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String name;
		do {
			name = input.readNotVoidString(ControllerStrings.ARTIFACT_INPUT_NAME);
			if (dataFacade.hasArtifact(user, selectedHouse, name))
				output.println(ControllerStrings.ARTIFACT_ROOM_NAME_ASSIGNED);
		}
		while (dataFacade.hasArtifact(user, selectedHouse, name));

		String descr = input.readNotVoidString(ControllerStrings.ARTIFACT_INPUT_DESCRIPTION);

		output.println(ControllerStrings.LIST_OF_PROPERTIES);
		view.printCollectionOfString(dataFacade.getPropertiesSet());

		Map<String, String> propertyMap = new HashMap<>();

		boolean remain = true;
		String propertyName;
		do {
			propertyName = input.readStringWithMaximumLength(ControllerStrings.PROPERTY_INPUT_NAME, 30);
			if (!propertyName.equals(ControllerStrings.BACK_CHARACTER) && !propertyMap.containsKey(propertyName)) {
				String propertyValue;
				if (dataFacade.hasProperty(propertyName)) {
					propertyValue = (input.yesOrNo(ControllerStrings.PROPERTY_INPUT_VALUE_QUESTION))
							? input.readStringWithMaximumLength(ControllerStrings.PROPERTY_INPUT_VALUE, 30)
							: dataFacade.getPropertyDefaultValue(propertyName);
				}
				else {
					propertyValue = input.readStringWithMaximumLength(ControllerStrings.PROPERTY_INPUT_VALUE, 30);
					dataFacade.addProperty(propertyName, propertyValue);
				}
				propertyMap.put(propertyName, propertyValue);
			}
			else if (propertyName.equals(ControllerStrings.BACK_CHARACTER))
				remain = false;
		}
		while (remain);

		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
			dataFacade.addArtifact(user, selectedHouse, name, descr, location, propertyMap);

		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public void readActuatorFromUser(String user, String selectedHouse, String location) {
		assert selectedHouse != null;
		assert location != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		if (dataFacade.doesActuatorCategoryExist()) {

			String category;
			do {
				category = input.readNotVoidString(ControllerStrings.ACTUATOR_CATEGORY_INPUT_NAME);
				if (!dataFacade.hasActuatorCategory(category))
					output.println(ControllerStrings.CATEGORY_NON_EXISTENT);
			}
			while (!dataFacade.hasActuatorCategory(category));

			String name;
			do {
				name = input.readNotVoidString(ControllerStrings.ACTUATOR_INPUT_NAME);
				if (dataFacade.hasActuator(user, selectedHouse, name + ControllerStrings.UNDERSCORE + category))
					output.println(ControllerStrings.ACTUATOR_NAME_ASSIGNED);
				else
					name = name + ControllerStrings.UNDERSCORE + category;
			}
			while (dataFacade.hasActuator(user, selectedHouse, name + ControllerStrings.UNDERSCORE + category));

			boolean roomOrArtifact = input.yesOrNo(ControllerStrings.SENSOR_ARTIFACT_OR_ROOM_ASSOCIATION);
			if (checkCategoryAssociations(user, selectedHouse, category, roomOrArtifact)) {
				List<String> objectList = readDeviceAssociationObjects(user, selectedHouse, category, roomOrArtifact);
				if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
					dataFacade.addActuator(user, selectedHouse, location, name, category, roomOrArtifact, objectList);
			}
		}
		else
			output.println(ControllerStrings.NO_ACTUATOR_CATEGORY_ERROR);
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	private List<String> readDeviceAssociationObjects(String user, String selectedHouse, String category,
			boolean roomOrArtifact) {
		List<String> objectList = new ArrayList<>();
		do {
			boolean remain = true;
			String toAssoc;
			do {
				if (roomOrArtifact) {
					toAssoc = input.readNotVoidString(ControllerStrings.SENSOR_ROOM_ASSOCIATION);
					if (!objectList.contains(toAssoc)) {
						if (!dataFacade.hasRoom(user, selectedHouse, toAssoc))
							output.println(ControllerStrings.ERROR_NON_EXISTENT_ROOM);
						else if (dataFacade.isRoomAssociated(user, selectedHouse, toAssoc, category))
							output.println(ControllerStrings.SENSOR_WRONG_ASSOCIATION_CATEGORY);
						else
							remain = false;
					}
					else
						output.println(ControllerStrings.INPUT_ERROR_ALREADY_INSERTED);
				}
				else {
					toAssoc = input.readNotVoidString(ControllerStrings.SENSOR_ARTIFACT_ASSOCIATION);
					if (!objectList.contains(toAssoc)) {
						if (!dataFacade.hasArtifact(user, selectedHouse, toAssoc))
							output.println(ControllerStrings.ERROR_NON_EXISTENT_ARTIFACT);
						else if (dataFacade.isArtifactAssociated(user, selectedHouse, toAssoc, category))
							output.println(ControllerStrings.SENSOR_WRONG_ASSOCIATION_CATEGORY);
						else
							remain = false;
					}
					else
						output.println(ControllerStrings.INPUT_ERROR_ALREADY_INSERTED);
				}

			}
			while (remain);
			objectList.add(toAssoc);
		}
		while (input.yesOrNo(ControllerStrings.SENSOR_ANOTHER_ASSOCIATION));
		return objectList;
	}
}
