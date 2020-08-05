package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.MaintainerInputHandler;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.util.ConfigFileManager;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.LibImporter;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.model.util.ObjectRemover;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.view.ManageableRenderer;

import java.io.PrintWriter;
import java.util.logging.Level;

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
	private ObjectRemover objectRemover;
	private LogWriter log;
	private ManageableRenderer renderer;
	private LibImporter libImporter;
	private ClockStrategy clock;
	private ConfigFileManager configFileManager;

	public MaintainerController(DataFacade dataFacade, ObjectRemover objectRemover, LogWriter log,
			ConfigFileManager configFileManager, ManageableRenderer renderer,
			MaintainerInputHandler maintainerInputHandler, LibImporter libImporter, ClockStrategy clock,
			PrintWriter output, RawInputHandler input) {
		menuManager = new MenuManager(ControllerStrings.MAINTAINER_MAIN_MENU_TITLE,
				ControllerStrings.MAINTAINER_MAIN_MENU_VOICES, output, input);
		this.dataFacade = dataFacade;
		this.objectRemover = objectRemover;
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
			String selectedHouse = null;
			switch (selection) {
				case 0:
					try {
						dataFacade.saveData();
					}
					catch (Exception e) {
						log.log(Level.SEVERE, ControllerStrings.DB_SAVE_ERROR, e);
						output.println(ControllerStrings.DB_SAVE_ERROR);
					}
					log.log(Level.FINE, ControllerStrings.LOG_EXIT_MENU);
					break;
				case 1:
					try {
						maintainerInputHandler.readUser();
					}
					catch (Exception e) {
						log.log(Level.SEVERE, "Errore durante l'inserimento di un utente", e);
						output.println("Errore durante l'inserimento di un utente, non è possibile verificare la presenza di altri utenti nel database");
					}
					break;
				case 2:
					user = input.readNotVoidString(ControllerStrings.INSERT_USER_DB);
					try {
						if (dataFacade.hasUser(user)) {
							objectRemover.removeUser(user);
							dataFacade.saveData();
						}
						else
							output.println(ControllerStrings.ERROR_NON_EXISTENT_USER);
					}
					catch (Exception e) {
						log.log(Level.SEVERE, "Errore durante la rimozione dell'utente " + user , e);
						output.println("Errore durante la rimozione dell'utente, non è possibile verificare la presenza di altri utenti nel database");
					}
					break;
				case 3:
					menuManager.clearOutput();
					user = input.readNotVoidString(ControllerStrings.INSERT_USER_DB);
					try {
						if (dataFacade.hasUser(user)) {
							if (dataFacade.doesHousingUnitExist(user)) {
								menuManager.printCollectionOfString(dataFacade.getHousingUnitSet(user));
								selectedHouse = maintainerInputHandler.safeInsertHouse(user);
								maintainerUnitController.show(user, selectedHouse);
							}
							else
								output.println(ControllerStrings.NO_HOUSE);
						}
						else
							output.println(ControllerStrings.ERROR_NON_EXISTENT_USER);
					}
					catch (Exception e) {
						log.log(Level.SEVERE, "Errore durante l'ottenimento della casa " + selectedHouse, e);
						output.println("Errore durante l'ottenimento della casa selezionata dal database");
					}
					break;
				case 4:
					menuManager.clearOutput();
					log.log(Level.FINE, ControllerStrings.LOG_INSERT_HOUSE);
					try {
						maintainerInputHandler.readHouseFromUser();
					}
					catch (Exception e) {
						log.log(Level.SEVERE, "Errore durante l'inserimento dell'unità immobiliare", e);
						output.println("Errore durante l'inserimento dell'unità immobiliare, verificare la connessione al database");
					}
					log.log(Level.FINE, ControllerStrings.LOG_INSERT_HOUSE_SUCCESS);
					break;
				case 5:
					user = input.readNotVoidString(ControllerStrings.INSERT_USER_DB);
					try {
						if (dataFacade.hasUser(user)) {
							if (dataFacade.doesHousingUnitExist(user)) {
								menuManager.printCollectionOfString(dataFacade.getHousingUnitSet(user));
								selectedHouse = maintainerInputHandler.safeInsertHouse(user);
								objectRemover.removeHousingUnit(user, selectedHouse);
							}
							else
								output.println(ControllerStrings.NO_HOUSE);
						}
						else
							output.println(ControllerStrings.ERROR_NON_EXISTENT_USER);
					}
					catch (Exception e) {
						log.log(Level.SEVERE, "Errore durante la rimozione dell'unità immobiliare " + selectedHouse, e);
						output.println("Errore durante la rimozione dell'unità immobiliare, verificare la connessione al database");
					}
					break;
				case 6:
					// visualizza categorie di sensori
					menuManager.clearOutput();
					if (dataFacade.doesSensorCategoryExist()) {
						menuManager.printCollectionOfString(dataFacade.getSensorCategoryList());
						output.println();
						output.println();

						String selectedSensCategory = maintainerInputHandler.safeInsertSensorCategory();
						log.log(Level.FINE, ControllerStrings.LOG_SHOW_SENSOR_CATEGORY + selectedSensCategory);
						output.println(renderer.render(dataFacade.getSensorCategory(selectedSensCategory)));
					}
					else
						output.println(ControllerStrings.NO_SENSOR_CATEGORY);
					break;
				case 7:
					// visualizza categoria di attuatore
					menuManager.clearOutput();
					if (dataFacade.doesActuatorCategoryExist()) {
						menuManager.printCollectionOfString(dataFacade.getActuatorCategoryList());
						output.println();
						output.println();

						String selectedActuCategory = maintainerInputHandler.safeInsertActuatorCategory();
						log.log(Level.FINE, ControllerStrings.LOG_SHOW_ACTUATOR_CATEGORY + selectedActuCategory);
						output.println(renderer.render(dataFacade.getActuatorCategory(selectedActuCategory)));
					}
					else
						output.println(ControllerStrings.NO_ACTUATOR_CATEGORY);
					break;
				case 8:
					// crea sensor category
					log.log(Level.FINE, ControllerStrings.LOG_INSERT_SENSOR_CATEGORY);
					try {
						maintainerInputHandler.readSensorCategoryFromUser(menuManager);
					}
					catch (Exception e) {
						log.log(Level.SEVERE, "Errore durante l'inserimento della categoria di sensori", e);
						output.println("Errore durante l'inserimento della categoria di sensori, verificare la connessione al database");
					}
					log.log(Level.FINE, ControllerStrings.LOG_INSERT_SENSOR_CATEGORY_SUCCESS);
					break;
				case 9:
					// crea actuator category
					log.log(Level.FINE, ControllerStrings.LOG_INSERT_ACTUATOR_CATEGORY);
					maintainerInputHandler.readActuatorCategoryFromUser();
					log.log(Level.FINE, ControllerStrings.LOG_INSERT_ACTUATOR_CATEGORY_SUCCESS);
					break;
				case 10:
					menuManager.clearOutput();
					if (dataFacade.doesSensorCategoryExist()) {
						menuManager.printCollectionOfString(dataFacade.getSensorCategoryList());
						output.println();
						output.println();

						String selectedSensCategory = maintainerInputHandler.safeInsertSensorCategory();
						objectRemover.removeSensorCategory(selectedSensCategory);
						try {
							dataFacade.saveData();
						}
						catch (Exception e) {
							log.log(Level.SEVERE, "Errore durante la rimozione della categoria di sensori " + selectedSensCategory, e);
							output.println(ControllerStrings.DB_SAVE_ERROR);
						}
					}
					else
						output.println(ControllerStrings.NO_SENSOR_CATEGORY);
					break;
				case 11:
					menuManager.clearOutput();
					if (dataFacade.doesActuatorCategoryExist()) {
						menuManager.printCollectionOfString(dataFacade.getActuatorCategoryList());
						output.println();
						output.println();

						String selectedActuCategory = maintainerInputHandler.safeInsertActuatorCategory();
						objectRemover.removeActuatorCategory(selectedActuCategory);
						try {
							dataFacade.saveData();
						}
						catch (Exception e) {
							log.log(Level.SEVERE, "Errore durante la rimozione della categoria di attuatori " + selectedActuCategory, e);
							output.println(ControllerStrings.DB_SAVE_ERROR);
						}
					}
					else
						output.println(ControllerStrings.NO_ACTUATOR_CATEGORY);
					break;
				case 12:
					// importa file
					menuManager.clearOutput();
					log.log(Level.FINE, ControllerStrings.LOG_IMPORTING_FILE);
					user = input.readNotVoidString(ControllerStrings.INSERT_USER_DB);
					try {
						if (dataFacade.hasUser(user)) {
							dataFacade.loadHousingUnits(user);
							if (libImporter.importFile(user)) {
								log.log(Level.FINE, ControllerStrings.SUCCESS_IMPORT_FILE);
								output.println(ControllerStrings.SUCCESS_IMPORT_FILE);
							}
							else {
								String error = libImporter.getErrorsString();
								output.println(error);
								log.log(Level.FINE, ControllerStrings.LOG_ERROR_IMPORT + error);
							}
						}
						else
							output.println(ControllerStrings.ERROR_NON_EXISTENT_USER);
					}
					catch (Exception e) {
						log.log(Level.SEVERE, "Errore durante l'importazione del file di libreria", e);
						output.println("Errore durante l'importazione del file di libreria");
					}

					break;
				case 13:
					// mostra file di help
					try {
						configFileManager
								.runFileFromSource(ControllerStrings.HELP_PATH + ControllerStrings.USER_HELP_FILE_NAME);
					}
					catch (Exception e) {
						log.log(Level.SEVERE, "Impossibile aprire il file di help", e);
						output.println("Impossibile aprire il file di help");
					};
					break;
				case 14:
					// visualizza log
					try {
						configFileManager.runFileFromSource(ControllerStrings.LOG_PATH + ControllerStrings.LOG_NAME_FILE);
					}
					catch (Exception e) {
						log.log(Level.SEVERE, "Impossibile aprire il file di log", e);
						output.println("Impossibile aprire il file di log");
					}
					break;
				case 15:
					// aggiorna ora
					log.log(Level.FINE, ControllerStrings.LOG_REFRESH_HOUR);
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
