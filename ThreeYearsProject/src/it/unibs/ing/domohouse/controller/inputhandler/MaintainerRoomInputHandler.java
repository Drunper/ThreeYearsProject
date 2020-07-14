package it.unibs.ing.domohouse.controller.inputhandler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.ObjectFabricator;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MaintainerRoomInputHandler extends UserRoomInputHandler {

	private ObjectFabricator objectFabricator;

	public MaintainerRoomInputHandler(DataFacade dataFacade, ObjectFabricator objectFabricator, PrintWriter output,
			RawInputHandler input) {
		super(dataFacade, output, input);
		this.objectFabricator = objectFabricator;
	}

	public void changeRoomDescription(String selectedHouse, String selectedRoom) {
		assert selectedHouse != null && selectedRoom != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String descr = input.readNotVoidString(ControllerStrings.ROOM_INPUT_DESCRIPTION);
		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_SAVING))
			dataFacade.changeRoomDescription(selectedHouse, selectedRoom, descr);

		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public void readSensorFromUser(String selectedHouse, String location) {
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
				if (!dataFacade.hasSensor(selectedHouse, toCheck))
					remain = false;
				else
					output.println(ControllerStrings.SENSOR_NAME_ASSIGNED);
			}
			while (remain);

			if (dataFacade.doesRoomOrArtifactExist(selectedHouse)) {

				boolean isThereRoom = dataFacade.doesRoomExist(selectedHouse);
				boolean isThereArtifact = dataFacade.doesArtifactExist(selectedHouse);
				boolean roomOrArtifact;

				roomOrArtifact = input.yesOrNo(ControllerStrings.SENSOR_ARTIFACT_OR_ROOM_ASSOCIATION);

				if ((roomOrArtifact && !isThereRoom)) {
					output.println(ControllerStrings.NO_ASSOCIABLE_ELEMENT);
					roomOrArtifact = false;
				}
				else if ((!roomOrArtifact && !isThereArtifact)) {
					output.println(ControllerStrings.NO_ASSOCIABLE_ELEMENT);
					roomOrArtifact = true;
				}

				if (roomOrArtifact) {
					boolean canAssociate = false;
					for (String room : dataFacade.getRoomsList(selectedHouse)) {
						if (!dataFacade.isAssociated(selectedHouse, room, category))
							canAssociate = true;
					}

					if (!canAssociate) {
						output.println(ControllerStrings.NO_ROOM_TO_ASSOC);
						return;
					}
				}
				else {
					boolean canAssociate = false;
					for (String room : dataFacade.getRoomsList(selectedHouse)) {
						for (String artifact : dataFacade.getArtifactNames(selectedHouse, room)) {
							if (!dataFacade.isAssociated(selectedHouse, artifact, category))
								canAssociate = true;
						}
					}
					if (!canAssociate) {
						output.println(ControllerStrings.NO_ARTIFACT_TO_ASSOC);
						return;
					}
				}

				List<String> objectList = new ArrayList<>();
				do {
					String toAssoc;
					do {
						if (roomOrArtifact)
							toAssoc = input.readNotVoidString(ControllerStrings.SENSOR_ROOM_ASSOCIATION);
						else
							toAssoc = input.readNotVoidString(ControllerStrings.SENSOR_ARTIFACT_ASSOCIATION);

						if (!objectList.contains(toAssoc)) {
							if (!dataFacade.hasRoomOrArtifact(selectedHouse, toAssoc))
								output.println(ControllerStrings.ROOM_OR_ARTIFACT_NON_EXISTENT);
							else {
								if (roomOrArtifact && !dataFacade.isElementARoom(selectedHouse, toAssoc))
									output.println(ControllerStrings.SENSOR_WRONG_ASSOCIATION_ROOM);
								else if (!roomOrArtifact && dataFacade.isElementARoom(selectedHouse, toAssoc))
									output.println(ControllerStrings.SENSOR_WRONG_ASSOCIATION_ARTIFACT);
								else if (dataFacade.isAssociated(selectedHouse, toAssoc, category))
									output.println(ControllerStrings.SENSOR_WRONG_ASSOCIATION_CATEGORY);
							}
						}
						else {
							output.println(ControllerStrings.INPUT_ERROR_ALREADY_INSERTED);
						}
					}

					while (!dataFacade.hasRoomOrArtifact(selectedHouse, toAssoc)
							|| (roomOrArtifact && !dataFacade.isElementARoom(selectedHouse, toAssoc))
							|| (!roomOrArtifact && dataFacade.isElementARoom(selectedHouse, toAssoc))
							|| dataFacade.isAssociated(selectedHouse, toAssoc, category)
							|| objectList.contains(toAssoc));
					objectList.add(toAssoc);
				}
				while (input.yesOrNo(ControllerStrings.SENSOR_ANOTHER_ASSOCIATION));
				if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
					objectFabricator.createSensor(selectedHouse, name, category, roomOrArtifact, objectList, location);
			}
			else
				output.println(ControllerStrings.NO_SENSOR_ROOM_OR_ARTIFACT_ERROR);
		}
		else
			output.println(ControllerStrings.NO_SENSOR_CATEGORY_ERROR);
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public void readArtifactFromUser(String selectedHouse, String location) {
		assert selectedHouse != null;
		assert location != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String name;
		do {
			name = input.readNotVoidString(ControllerStrings.ARTIFACT_INPUT_NAME);
			if (dataFacade.hasRoomOrArtifact(selectedHouse, name))
				output.println(ControllerStrings.ARTIFACT_ROOM_NAME_ASSIGNED);
		}
		while (dataFacade.hasRoomOrArtifact(selectedHouse, name));

		String descr = input.readNotVoidString(ControllerStrings.ARTIFACT_INPUT_DESCRIPTION);
		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
			objectFabricator.createArtifact(selectedHouse, name, descr, location);

		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public void readActuatorFromUser(String selectedHouse, String location) {
		assert selectedHouse != null;
		assert location != null;
		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		if (!dataFacade.getActuatorCategoryList().isEmpty()) {

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
				if (dataFacade.hasActuator(selectedHouse, name + ControllerStrings.UNDERSCORE + category))
					output.println(ControllerStrings.ACTUATOR_NAME_ASSIGNED);
			}
			while (dataFacade.hasActuator(selectedHouse, name + ControllerStrings.UNDERSCORE + category));
			name = name.replace(ControllerStrings.UNDERSCORE, ControllerStrings.NULL_CHARACTER); // il nome non può
																									// contenere
																									// underscore

			if (dataFacade.doesRoomOrArtifactExist(selectedHouse)) {
				boolean isThereRoom = dataFacade.doesRoomExist(selectedHouse);
				boolean isThereArtifact = dataFacade.doesArtifactExist(selectedHouse);
				boolean roomOrArtifact;

				do {
					roomOrArtifact = input.yesOrNo(ControllerStrings.ACTUATOR_ARTIFACT_OR_ROOM_ASSOCIATION);

					if ((roomOrArtifact && !isThereRoom))
						output.println(ControllerStrings.NO_ASSOCIABLE_ELEMENT);
					else if ((!roomOrArtifact && !isThereArtifact))
						output.println(ControllerStrings.NO_ASSOCIABLE_ELEMENT);
				}
				while ((roomOrArtifact && !isThereRoom) || (!roomOrArtifact && !isThereArtifact));

				if (roomOrArtifact) {
					boolean canAssociate = false;
					for (String room : dataFacade.getRoomsList(selectedHouse)) {
						if (!dataFacade.isAssociated(selectedHouse, room, category))
							canAssociate = true;
					}

					if (!canAssociate) {
						output.println(ControllerStrings.NO_ROOM_TO_ASSOC);
						return;
					}
				}
				else {
					boolean canAssociate = false;
					for (String room : dataFacade.getRoomsList(selectedHouse)) {
						for (String artifact : dataFacade.getArtifactNames(selectedHouse, room)) {
							if (!dataFacade.isAssociated(selectedHouse, artifact, category))
								canAssociate = true;
						}
					}

					if (!canAssociate) {
						output.println(ControllerStrings.NO_ARTIFACT_TO_ASSOC);
						return;
					}
				}

				List<String> objectList = new ArrayList<>();
				do {
					String toAssoc;
					do {
						if (roomOrArtifact)
							toAssoc = input.readNotVoidString(ControllerStrings.ACTUATOR_ROOM_ASSOCIATION);
						else
							toAssoc = input.readNotVoidString(ControllerStrings.ACTUATOR_ARTIFACT_ASSOCIATION);

						if (!objectList.contains(toAssoc)) {
							if (!dataFacade.hasRoomOrArtifact(selectedHouse, toAssoc))
								output.println(ControllerStrings.ROOM_OR_ARTIFACT_NON_EXISTENT);
							else {
								if (roomOrArtifact && !dataFacade.isElementARoom(selectedHouse, toAssoc))
									output.println(ControllerStrings.ACTUATOR_WRONG_ASSOCIATION_ROOM);
								else if (!roomOrArtifact && dataFacade.isElementARoom(selectedHouse, toAssoc))
									output.println(ControllerStrings.ACTUATOR_WRONG_ASSOCIATION_ARTIFACT);
								else if (dataFacade.isAssociated(selectedHouse, toAssoc, category))
									output.println(ControllerStrings.ACTUATOR_WRONG_ASSOCIATION_CATEGORY);
							}
						}
						else {
							output.println(ControllerStrings.INPUT_ERROR_ALREADY_INSERTED);
						}
					}
					while (!dataFacade.hasRoomOrArtifact(selectedHouse, toAssoc)
							|| (roomOrArtifact && !dataFacade.isElementARoom(selectedHouse, toAssoc))
							|| (!roomOrArtifact && dataFacade.isElementARoom(selectedHouse, toAssoc))
							|| dataFacade.isAssociated(selectedHouse, toAssoc, category)
							|| objectList.contains(toAssoc));

					objectList.add(toAssoc);
				}
				while (input.yesOrNo(ControllerStrings.ACTUATOR_ANOTHER_ASSOCIATION));
				if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
					objectFabricator.createActuator(selectedHouse, name, category, roomOrArtifact, objectList,
							location);
			}
			else
				output.println(ControllerStrings.NO_ACTUATOR_ROOM_OR_ARTIFACT_ERROR);
		}
		else
			output.println(ControllerStrings.NO_ACTUATOR_CATEGORY_ERROR);

		assert userRoomInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}
}
