package it.unibs.ing.domohouse.controller.inputhandler;

import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.view.RawInputHandler;

import java.io.PrintWriter;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class UserInputHandler {

	protected DataFacade dataFacade;
	protected PrintWriter output;
	protected RawInputHandler input;

	public UserInputHandler(DataFacade dataFacade, PrintWriter output, RawInputHandler input) {
		this.dataFacade = dataFacade;
		this.output = output;
		this.input = input;
	}

	public String safeInsertSensorCategory() {
		assert userInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedSensCategory = input.readNotVoidString(ControllerStrings.INSERT_SENSOR_CATEGORY);
		if (dataFacade.hasSensorCategory(selectedSensCategory))
			return selectedSensCategory;
		else
			do {
				selectedSensCategory = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_SENSOR_CATEGORY
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_SENSOR_CATEGORY);
			}
			while (!dataFacade.hasSensorCategory(selectedSensCategory));

		assert selectedSensCategory != null;
		assert userInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedSensCategory;
	}

	public String safeInsertActuatorCategory() {
		assert userInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedActuCategory = input.readNotVoidString(ControllerStrings.INSERT_ACTUATOR_CATEGORY);
		if (dataFacade.hasActuatorCategory(selectedActuCategory))
			return selectedActuCategory;
		else
			do {
				selectedActuCategory = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_ACTUATOR_CATEGORY
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_ACTUATOR_CATEGORY);
			}
			while (!dataFacade.hasActuatorCategory(selectedActuCategory));

		assert selectedActuCategory != null;
		assert userInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;
		return selectedActuCategory;
	}

	public String safeInsertHouse(String user) {
		assert userInputHandlerInvariant() : ControllerStrings.WRONG_INVARIANT;

		String selectedHouse = input.readNotVoidString(ControllerStrings.INSERT_HOUSE);
		if (dataFacade.hasHousingUnit(user, selectedHouse))
			return selectedHouse;
		else
			do {
				selectedHouse = input.readNotVoidString(ControllerStrings.ERROR_NON_EXISTENT_HOUSE
						+ ControllerStrings.SPACE + ControllerStrings.INSERT_HOUSE);
			}
			while (!dataFacade.hasHousingUnit(user, selectedHouse));
		return selectedHouse;
	}

	protected boolean userInputHandlerInvariant() {
		return dataFacade != null;
	}
}
