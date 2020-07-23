package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.MaintainerInputHandler;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.ConfigFileManager;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.LibImporter;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.view.ManageableRenderer;

import java.io.PrintWriter;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MaintainerController {

	// View
	private MenuManager menuManager;
	private PrintWriter output;
	private RawInputHandler input;

	// Controller collegati
	private MaintainerUnitController maintainerUnitController;
	private MaintainerInputHandler maintainerInputHandler;

	// Model
	private DataFacade dataFacade;
	private LogWriter log;
	private ManageableRenderer renderer;
	private LibImporter libImporter;
	private ClockStrategy clock;
	private ConfigFileManager configFileManager;

	public MaintainerController(DataFacade dataFacade, LogWriter log, ConfigFileManager configFileManager, ManageableRenderer renderer,
			MaintainerInputHandler maintainerInputHandler, LibImporter libImporter, ClockStrategy clock,
			PrintWriter output, RawInputHandler input) {
		menuManager = new MenuManager(ControllerStrings.MAINTAINER_MAIN_MENU_TITLE,
				ControllerStrings.MAINTAINER_MAIN_MENU_VOICES, output, input);
		this.dataFacade = dataFacade;
		this.configFileManager = configFileManager;
		this.log = log;
		this.input = input;
		this.renderer = renderer;
		this.maintainerInputHandler = maintainerInputHandler;
		this.libImporter = libImporter;
		this.clock = clock;
		this.output = output;
	}

	public void show() {
		int selection;
		do {
			output.println(clock.getCurrentTime());
			output.println();
			selection = menuManager.doChoice();
			String user;
			switch (selection) {
				case 0:
					dataFacade.saveData();
					log.write(ControllerStrings.LOG_EXIT_MENU);
					break;
				case 1:
					maintainerInputHandler.readUser();
					break;
				case 2:
					menuManager.clearOutput();
					user = input.readNotVoidString(ControllerStrings.INSERT_USER_DB);
					if(dataFacade.hasUser(user)) {
						if (dataFacade.doesHousingUnitExist(user)) {
							menuManager.printCollectionOfString(dataFacade.getHousingUnitsList(user));
							String selectedHouse = maintainerInputHandler.safeInsertHouse(user);
							maintainerUnitController.show(user, selectedHouse);
						}
						else
							output.println(ControllerStrings.NO_HOUSE);
					}
					else
						output.println(ControllerStrings.ERROR_NON_EXISTENT_USER);
					break;
				case 3:
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_INSERT_HOUSE);
					maintainerInputHandler.readHouseFromUser();
					log.write(ControllerStrings.LOG_INSERT_HOUSE_SUCCESS);
					break;
				case 4:
					// visualizza categorie di sensori
					menuManager.clearOutput();
					if (dataFacade.doesSensorCategoryExist()) {
						menuManager.printCollectionOfString(dataFacade.getSensorCategoryList());
						output.println();
						output.println();

						String selectedSensCategory = maintainerInputHandler.safeInsertSensorCategory(); 
						log.write(ControllerStrings.LOG_SHOW_SENSOR_CATEGORY + selectedSensCategory);
						output.println(renderer.render(dataFacade.getSensorCategory(selectedSensCategory)));
					}
					else
						output.println(ControllerStrings.NO_SENSOR_CATEGORY);
					break;
				case 5:
					// visualizza categoria di attuatore
					menuManager.clearOutput();
					if (dataFacade.doesActuatorCategoryExist()) {
						menuManager.printCollectionOfString(dataFacade.getActuatorCategoryList());
						output.println(); 
						output.println();

						String selectedActuCategory = maintainerInputHandler.safeInsertActuatorCategory(); 
						log.write(ControllerStrings.LOG_SHOW_ACTUATOR_CATEGORY + selectedActuCategory);
						output.println(renderer.render(dataFacade.getActuatorCategory(selectedActuCategory)));
					}
					else
						output.println(ControllerStrings.NO_ACTUATOR_CATEGORY);
					break;
				case 6:
					// crea sensor category
					log.write(ControllerStrings.LOG_INSERT_SENSOR_CATEGORY);
					maintainerInputHandler.readSensorCategoryFromUser(menuManager);
					log.write(ControllerStrings.LOG_INSERT_SENSOR_CATEGORY_SUCCESS);
					break;
				case 7:
					// crea actuator category
					log.write(ControllerStrings.LOG_INSERT_ACTUATOR_CATEGORY);
					maintainerInputHandler.readActuatorCategoryFromUser();
					log.write(ControllerStrings.LOG_INSERT_ACTUATOR_CATEGORY_SUCCESS);
					break;
				case 8:
					// importa file
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_IMPORTING_FILE);
					user = input.readNotVoidString(ControllerStrings.INSERT_USER_DB);
					if(dataFacade.hasUser(user)) {
						dataFacade.loadHousingUnits(user);
						if (libImporter.importFile(user)) {
							log.write(ControllerStrings.SUCCESS_IMPORT_FILE);
							output.println(ControllerStrings.SUCCESS_IMPORT_FILE);
						}
						else {
							String error = libImporter.getErrorsString();
							output.println(error);
							log.write(ControllerStrings.LOG_ERROR_IMPORT + error);
						}
					}
					else
						output.println(ControllerStrings.ERROR_NON_EXISTENT_USER);
						
					break;
				case 9:
					// mostra file di help
					configFileManager.runFileFromSource(ControllerStrings.HELP_PATH + ControllerStrings.MAINTAINER_HELP_FILE_NAME);
					break;
				case 10:
					// visualizza log
					configFileManager.runFileFromSource(ControllerStrings.LOG_PATH + ControllerStrings.LOG_NAME_FILE);
					break;
				case 11:
					// aggiorna ora
					log.write(ControllerStrings.LOG_REFRESH_HOUR);
					menuManager.clearOutput();
					break;
			}
		}
		while (selection != 0);
	}

	public void setMaintainerUnitController(MaintainerUnitController maintainerUnitController) {
		this.maintainerUnitController = maintainerUnitController;
	}
}
