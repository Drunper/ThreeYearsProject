package it.unibs.ing.domohouse.controller.inputhandler;

import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.ObjectFabricator;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MaintainerUnitInputHandler extends UserUnitInputHandler {

	public MaintainerUnitInputHandler(DataFacade dataFacade, ObjectFabricator objectFabricator, PrintWriter output,
			RawInputHandler input) {
		super(dataFacade, objectFabricator, output, input);
	}

	public void changeHouseDescription(String user, String selectedHouse) {
		assert selectedHouse != null;
		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String descr = input.readNotVoidString(ControllerStrings.HOUSE_INPUT_DESCRIPTION);
		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_SAVING))
			dataFacade.changeHouseDescription(user, selectedHouse, descr);

		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}

	public void readRoomFromUser(String user, String selectedHouse) {
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
		Map<String, String> propertiesMap = new TreeMap<>();

		double temp = input.readDouble(ControllerStrings.ROOM_INPUT_TEMPERATURE);
		double umidita = input.readDouble(ControllerStrings.ROOM_INPUT_HUMIDITY);
		double pressione = input.readDouble(ControllerStrings.ROOM_INPUT_PRESSURE);
		double vento = input.readDouble(ControllerStrings.ROOM_INPUT_WIND);
		propertiesMap.put("temperatura", String.valueOf(temp));
		propertiesMap.put("umidità", String.valueOf(umidita));
		propertiesMap.put("pressione", String.valueOf(pressione));
		propertiesMap.put("vento", String.valueOf(vento));

		boolean presenza = input.yesOrNo(ControllerStrings.ROOM_PRESENCE);
		String presenza_persone;
		if (presenza)
			presenza_persone = "presenza di persone";
		else
			presenza_persone = "assenza di persone";

		propertiesMap.put("presenza_persone", String.valueOf(presenza_persone));

		if (input.yesOrNo(ControllerStrings.PROCEED_WITH_CREATION))
			objectFabricator.createRoom(user, selectedHouse, name, descr, propertiesMap);

		assert inputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
	}
}
