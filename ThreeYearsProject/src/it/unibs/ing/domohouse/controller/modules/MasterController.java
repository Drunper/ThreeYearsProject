package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.*;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.rule.RulesWorker;
import it.unibs.ing.domohouse.model.db.Connector;
import it.unibs.ing.domohouse.model.file.FileLoader;
import it.unibs.ing.domohouse.model.file.FileSaver;
import it.unibs.ing.domohouse.model.util.*;
import it.unibs.ing.domohouse.model.db.DatabaseAuthenticator;
import it.unibs.ing.domohouse.view.*;

import java.io.PrintWriter;
import java.util.Scanner;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MasterController {

	// Model
	private DataFacade dataFacade;
	private Authenticator authenticator;
	private ClockStrategy clock;
	private LibImporter libImporter;
	private Saver saver;
	private LogWriter log;
	private RulesWorker rulesWorker;
	private Connector connector;

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
		checkConfigFileExistence(output);
		log = new LogWriter();
		renderer = buildChain();

		connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		dataFacade = new DataFacade(connector);
		authenticator = new DatabaseAuthenticator(new HashCalculator(), connector);
		rulesWorker = new RulesWorker(dataFacade, clock);
		libImporter = new LibImporter(dataFacade);
		saver = new FileSaver();
		dataFacade.loadCategories();
		
		RawInputHandler input = new RawInputHandler(in, output);
		buildInputHandlers(output, input);
		setControllers(output, input);
	}

	public void start() {
		loginController.show();
		rulesWorker.stopCheckRules();
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
		maintainerRoomController = new MaintainerRoomController(dataFacade, maintainerRoomInputHandler, log, renderer,
				clock, output, input);
		maintainerUnitController = new MaintainerUnitController(dataFacade, log, renderer, maintainerUnitInputHandler,
				clock, output, input);
		maintainerController = new MaintainerController(dataFacade, log, renderer, maintainerInputHandler,
				libImporter, clock, output, input);
		userRoomController = new UserRoomController(dataFacade, log, renderer, userRoomInputHandler, clock, output,
				input);
		userUnitController = new UserUnitController(dataFacade, log, renderer, userUnitInputHandler, clock, output,
				input);
		userController = new UserController(dataFacade, log, renderer, userInputHandler, clock, output, input);
		loginController = new LoginController(dataFacade, log, authenticator, clock, output, input);

		loginController.setMaintainerController(maintainerController);
		loginController.setUserController(userController);
		userController.setUserUnitController(userUnitController);
		userUnitController.setUserRoomController(userRoomController);
		maintainerController.setMaintainerUnitController(maintainerUnitController);
		maintainerUnitController.setMaintainerRoomController(maintainerRoomController);
	}
	
	private void checkConfigFileExistence(PrintWriter output) {
		FileLoader fileLoader = new FileLoader();
		if (!fileLoader.loadConfigFile()) {
			output.println(ControllerStrings.CONFIG_FILE_LOADING_FAILED);
			saver.createConfigFile();
			output.println(ControllerStrings.CONFIG_FILE_CREATED);
			fileLoader.loadConfigFile();
		}
		loadClockType();
		output.println(ControllerStrings.LOADED_CLOCK_STRATEGY);
	}

	private void loadClockType() {
		String className = System.getProperty("clock.type.class.name");
		try {
			clock = (ClockStrategy) Class.forName(className).getDeclaredConstructor().newInstance();
			clock.startClock();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ManageableRenderer buildChain() {
		return new HousingUnitRenderer(new RoomRenderer(new ArtifactRenderer(
				new ActuatorRenderer(new SensorRenderer(new SensorCategoryRenderer(new ActuatorCategoryRenderer()))))));
	}
}
