package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.UserInputHandler;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.Loader;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.view.ManageableRenderer;

import java.io.PrintWriter;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class UserController {

	private MenuManager menuManager;

	private UserUnitController userUnitController;
	private DataFacade dataFacade;
	private LogWriter log;
	private ManageableRenderer renderer;
	private Loader loader;
	private UserInputHandler userInputHandler;
	private ClockStrategy clock;
	private PrintWriter output;

	public UserController(DataFacade dataFacade, LogWriter log, ManageableRenderer renderer, Loader loader,
			UserInputHandler userInputHandler, ClockStrategy clock, PrintWriter output, RawInputHandler input) {
		menuManager = new MenuManager(ControllerStrings.USER_MAIN_MENU_TILE, ControllerStrings.USER_MAIN_MENU_VOICES,
				output, input);
		this.dataFacade = dataFacade;
		this.log = log;
		this.renderer = renderer;
		this.loader = loader;
		this.userInputHandler = userInputHandler;
		this.clock = clock;
		this.output = output;
	}

	public void show() {
		int selection;
		do {
			output.println(clock.getCurrentTime());
			output.println();
			selection = menuManager.doChoice();
			switch (selection) {
				case 0:
					log.write(ControllerStrings.LOG_EXIT_MENU);
					break;
				case 1:
					menuManager.clearOutput();
					if (dataFacade.doesHousingUnitExist()) {
						menuManager.printListOfString(dataFacade.getHousingUnitsList());
						String selectedHouse = userInputHandler.safeInsertHouse();
						userUnitController.show(selectedHouse);
					}
					else
						output.println(ControllerStrings.NO_HOUSE);
					break;
				case 2:
					menuManager.clearOutput();
					if (dataFacade.doesSensorCategoryExist()) {
						menuManager.printListOfString(dataFacade.getSensorCategoryList());
						output.println();
						output.println();

						String selectedSensCategory = userInputHandler.safeInsertSensorCategory();
						log.write(ControllerStrings.LOG_SHOW_SENSOR_CATEGORY + selectedSensCategory);
						output.println(renderer.render(dataFacade.getSensorCategory(selectedSensCategory)));
					}
					else
						output.println(ControllerStrings.NO_SENSOR_CATEGORY);
					break;
				case 3:
					menuManager.clearOutput();
					if (dataFacade.doesActuatorCategoryExist()) {
						menuManager.printListOfString(dataFacade.getActuatorCategoryList());
						output.println();
						output.println();
						String selectedActuCategory = userInputHandler.safeInsertActuatorCategory();
						log.write(ControllerStrings.LOG_SHOW_ACTUATOR_CATEGORY + selectedActuCategory);
						output.println(renderer.render(dataFacade.getActuatorCategory(selectedActuCategory)));
					}
					else
						output.println(ControllerStrings.NO_ACTUATOR_CATEGORY);
					break;
				case 4:
					// mostra file di help
					menuManager.clearOutput();
					loader.runFileFromSource(ControllerStrings.HELP_PATH + ControllerStrings.USER_HELP_FILE_NAME);
					break;
				case 5:
					// aggiorna ora
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_REFRESH_HOUR);
					break;
			}
		}
		while (selection != 0);
	}

	public void setUserUnitController(UserUnitController userUnitController) {
		this.userUnitController = userUnitController;
	}
}