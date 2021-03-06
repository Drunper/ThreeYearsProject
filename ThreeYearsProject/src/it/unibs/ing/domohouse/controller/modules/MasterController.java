package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.*;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.rule.RulesWorker;
import it.unibs.ing.domohouse.model.db.Connector;
import it.unibs.ing.domohouse.model.util.*;
import it.unibs.ing.domohouse.model.db.DatabaseAuthenticator;
import it.unibs.ing.domohouse.view.*;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MasterController {

	// Model
	private DataFacade dataFacade;
	private ObjectRemover objectRemover;
	private Authenticator authenticator;
	private ClockStrategy clock;
	private LibImporter libImporter;
	private LogWriter log;
	private RulesWorker rulesWorker;
	private Connector connector;
	private ConfigFileManager configFileManager;

	// Controllers
	private LoginController loginController;
	private UserController userController;
	private UserUnitController userUnitController;
	private UserRoomController userRoomController;
	private MaintainerController maintainerController;
	private MaintainerUnitController maintainerUnitController;
	private MaintainerRoomController maintainerRoomController;

	// InputHandlers
	private UserInputHandler userInputHandler;
	private UserUnitInputHandler userUnitInputHandler;
	private UserRoomInputHandler userRoomInputHandler;
	private MaintainerInputHandler maintainerInputHandler;
	private MaintainerUnitInputHandler maintainerUnitInputHandler;
	private MaintainerRoomInputHandler maintainerRoomInputHandler;

	// View
	private ManageableRenderer renderer;

	public MasterController(Scanner in, PrintWriter output) {
		OperatingModesManager.fillOperatingModes();
		configFileManager = new ConfigFileManager(output);
		checkConfigFileExistence(output);
		renderer = buildChain();

		connector = new Connector(configFileManager.getDBURL(), configFileManager.getDBUserName(),
				configFileManager.getDBpassword());
		try {
			log = new LogWriter();
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			objectRemover = new ObjectRemover(dataFacade);
			authenticator = new DatabaseAuthenticator(new HashCalculator(), connector);
			rulesWorker = new RulesWorker(dataFacade, clock);
			libImporter = new LibImporter(dataFacade);
			dataFacade.loadCategories();
			rulesWorker.startRulesWorker();
		}
		catch (Exception e) {
			log.log(Level.SEVERE, "Errore durante l'inizializzazione del programma", e);
			output.println("Errore durante l'inizializzazione del programma, verificare lo stato della configurazione");
		}

		RawInputHandler input = new RawInputHandler(in, output);
		buildInputHandlers(output, input);
		setControllers(output, input);
	}

	public void start() {
		loginController.show();
		if(rulesWorker != null)
			rulesWorker.stopCheckRules();
		connector.closeConnection();
	}

	private void buildInputHandlers(PrintWriter output, RawInputHandler input) {
		userInputHandler = new UserInputHandler(dataFacade, output, input);
		userUnitInputHandler = new UserUnitInputHandler(dataFacade, output, input);
		userRoomInputHandler = new UserRoomInputHandler(dataFacade, output, input);
		maintainerInputHandler = new MaintainerInputHandler(dataFacade, output, input);
		maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, output, input);
		maintainerRoomInputHandler = new MaintainerRoomInputHandler(dataFacade, output, input);
	}

	private void setControllers(PrintWriter output, RawInputHandler input) {
		maintainerRoomController = new MaintainerRoomController(dataFacade, objectRemover, maintainerRoomInputHandler,
				log, renderer, clock, output, input);
		maintainerUnitController = new MaintainerUnitController(dataFacade, objectRemover, log, renderer,
				maintainerUnitInputHandler, clock, output, input);
		maintainerController = new MaintainerController(dataFacade, objectRemover, log, configFileManager, renderer,
				maintainerInputHandler, libImporter, clock, output, input);
		userRoomController = new UserRoomController(dataFacade, log, renderer, userRoomInputHandler, clock, output,
				input);
		userUnitController = new UserUnitController(dataFacade, log, renderer, userUnitInputHandler, clock, output,
				input);
		userController = new UserController(dataFacade, log, configFileManager, renderer, userInputHandler, clock,
				output, input);
		loginController = new LoginController(dataFacade, log, authenticator, clock, output, input);

		loginController.setMaintainerController(maintainerController);
		loginController.setUserController(userController);
		userController.setUserUnitController(userUnitController);
		userUnitController.setUserRoomController(userRoomController);
		maintainerController.setMaintainerUnitController(maintainerUnitController);
		maintainerUnitController.setMaintainerRoomController(maintainerRoomController);
	}

	private void checkConfigFileExistence(PrintWriter output) {
		if (!configFileManager.loadConfigFile()) {
			output.println(ControllerStrings.CONFIG_FILE_LOADING_FAILED);
			try {
				configFileManager.createConfigFile();
				output.println(ControllerStrings.CONFIG_FILE_CREATED);
				configFileManager.loadConfigFile();
			}
			catch (Exception e) {
				log.log(Level.SEVERE, "Errore durante la creazione del file di configurazione", e);
				output.println("Errore durante la creazione del file di configurazione");
			}
		}
		try {
			loadClockType();
		}
		catch (Exception e) {
			log.log(Level.SEVERE, "Errore durante la configurazione dell'orologio di sistema", e);
			output.println("Errore durante la configurazione dell'orologio di sistema");
		}
		output.println(ControllerStrings.LOADED_CLOCK_STRATEGY);
	}

	private void loadClockType() throws Exception {
		String className = System.getProperty("clock.type.class.name");
		try {
			clock = (ClockStrategy) Class.forName(className).getDeclaredConstructor().newInstance();
			clock.startClock();
		}
		catch (Exception e) {
			throw new Exception("Errore durante la configurazione dell'orologio di sistema");
		}
	}

	private ManageableRenderer buildChain() {
		return new HousingUnitRenderer(new RoomRenderer(new ArtifactRenderer(
				new ActuatorRenderer(new SensorRenderer(new SensorCategoryRenderer(new ActuatorCategoryRenderer()))))));
	}
}
