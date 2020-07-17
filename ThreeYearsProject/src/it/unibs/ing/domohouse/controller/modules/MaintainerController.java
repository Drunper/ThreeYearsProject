package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.MaintainerInputHandler;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.LibImporter;
import it.unibs.ing.domohouse.model.util.Loader;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.model.util.Saver;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.view.ManageableRenderer;

import java.io.PrintWriter;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MaintainerController {

	// View
	private MenuManager menuManager;
	private PrintWriter output;

	// Controller collegati
	private MaintainerUnitController maintainerUnitController;
	private MaintainerInputHandler maintainerInputHandler;

	// Model
	private DataFacade dataFacade;
	private LogWriter log;
	private ManageableRenderer renderer;
	private Loader loader;
	private Saver saver;
	private LibImporter libImporter;
	private ClockStrategy clock;

	public MaintainerController(DataFacade dataFacade, LogWriter log, ManageableRenderer renderer, Loader loader,
			MaintainerInputHandler maintainerInputHandler, Saver saver, LibImporter libImporter, ClockStrategy clock,
			PrintWriter output, RawInputHandler input) {
		menuManager = new MenuManager(ControllerStrings.MAINTAINER_MAIN_MENU_TITLE,
				ControllerStrings.MAINTAINER_MAIN_MENU_VOICES, output, input);
		this.dataFacade = dataFacade;
		this.log = log;
		this.renderer = renderer;
		this.loader = loader;
		this.maintainerInputHandler = maintainerInputHandler;
		this.saver = saver;
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
			switch (selection) {
				case 0:
					log.write(ControllerStrings.LOG_EXIT_MENU);
					break;
				case 1:
					menuManager.clearOutput();
					if (dataFacade.doesHousingUnitExist()) {
						menuManager.printListOfString(dataFacade.getHousingUnitsList());
						String selectedHouse = maintainerInputHandler.safeInsertHouse();
						maintainerUnitController.show(selectedHouse);
					}
					else
						output.println(ControllerStrings.NO_HOUSE);
					break;
				case 2:
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_INSERT_HOUSE);
					maintainerInputHandler.readHouseFromUser();
					log.write(ControllerStrings.LOG_INSERT_HOUSE_SUCCESS);
					break;
				case 3:
					// visualizza categorie di sensori
					menuManager.clearOutput();
					if (dataFacade.doesSensorCategoryExist()) {
						menuManager.printListOfString(dataFacade.getSensorCategoryList());
						output.println();
						output.println();

						String selectedSensCategory = maintainerInputHandler.safeInsertSensorCategory(); 
						log.write(ControllerStrings.LOG_SHOW_SENSOR_CATEGORY + selectedSensCategory);
						output.println(renderer.render(dataFacade.getSensorCategory(selectedSensCategory)));
					}
					else
						output.println(ControllerStrings.NO_SENSOR_CATEGORY);
					break;
				case 4:
					// visualizza categoria di attuatore
					menuManager.clearOutput();
					if (!dataFacade.doesActuatorCategoryExist()) {
						menuManager.printListOfString(dataFacade.getActuatorCategoryList());
						output.println(); 
						output.println();

						String selectedActuCategory = maintainerInputHandler.safeInsertActuatorCategory(); 
						log.write(ControllerStrings.LOG_SHOW_ACTUATOR_CATEGORY + selectedActuCategory);
						output.println(renderer.render(dataFacade.getActuatorCategory(selectedActuCategory)));
					}
					else
						output.println(ControllerStrings.NO_ACTUATOR_CATEGORY);
					break;
				case 5:
					// crea sensor category
					log.write(ControllerStrings.LOG_INSERT_SENSOR_CATEGORY);
					maintainerInputHandler.readSensorCategoryFromUser();
					log.write(ControllerStrings.LOG_INSERT_SENSOR_CATEGORY_SUCCESS);
					break;
				case 6:
					// crea actuator category
					log.write(ControllerStrings.LOG_INSERT_ACTUATOR_CATEGORY);
					maintainerInputHandler.readActuatorCategoryFromUser();
					log.write(ControllerStrings.LOG_INSERT_ACTUATOR_CATEGORY_SUCCESS);
					break;
				case 7:
					// salva file
					dataFacade.setFirstStart(false);
					log.write(ControllerStrings.LOG_SAVING_DATA);
					saver.saveDataFacade(dataFacade);
					output.println(ControllerStrings.DATA_SAVED);
					log.write(ControllerStrings.DATA_SAVED);
					break;
				case 8:
					// importa file
					menuManager.clearOutput();
					log.write(ControllerStrings.LOG_IMPORTING_FILE);
					if (libImporter.importFile()) {
						log.write(ControllerStrings.SUCCESS_IMPORT_FILE);
						output.println(ControllerStrings.SUCCESS_IMPORT_FILE);
					}
					else {
						String error = libImporter.getErrorsString();
						output.println(error);
						log.write(ControllerStrings.LOG_ERROR_IMPORT + error);
					}
					break;
				case 9:
					/*
					// mostra file di help
					loader.runFileFromSource(ControllerStrings.HELP_PATH + ControllerStrings.MAINTAINER_HELP_FILE_NAME);
					break;
					*/
				case 10:
					/*
					// visualizza log
					loader.runFileFromSource(ControllerStrings.LOG_PATH + ControllerStrings.LOG_NAME_FILE);
					break;
					*/
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
