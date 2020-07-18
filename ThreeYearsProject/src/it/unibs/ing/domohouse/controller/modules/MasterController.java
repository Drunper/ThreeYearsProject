package it.unibs.ing.domohouse.controller.modules;

import it.unibs.ing.domohouse.controller.inputhandler.MaintainerInputHandler;
import it.unibs.ing.domohouse.controller.inputhandler.MaintainerRoomInputHandler;
import it.unibs.ing.domohouse.controller.inputhandler.MaintainerUnitInputHandler;
import it.unibs.ing.domohouse.controller.inputhandler.UserInputHandler;
import it.unibs.ing.domohouse.controller.inputhandler.UserRoomInputHandler;
import it.unibs.ing.domohouse.controller.inputhandler.UserUnitInputHandler;
import it.unibs.ing.domohouse.model.components.clock.ClockStrategy;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.rule.RuleParser;
import it.unibs.ing.domohouse.model.components.rule.RulesWorker;
import it.unibs.ing.domohouse.model.db.Connector;
import it.unibs.ing.domohouse.model.file.FileLoader;
import it.unibs.ing.domohouse.model.file.FileSaver;
import it.unibs.ing.domohouse.model.util.Authenticator;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.LibImporter;
import it.unibs.ing.domohouse.model.util.Loader;
import it.unibs.ing.domohouse.model.util.LogWriter;
import it.unibs.ing.domohouse.model.db.DatabaseAuthenticator;
import it.unibs.ing.domohouse.model.db.DatabaseLoader;
import it.unibs.ing.domohouse.model.util.HashCalculator;
import it.unibs.ing.domohouse.model.util.ObjectFabricator;
import it.unibs.ing.domohouse.model.util.Saver;
import it.unibs.ing.domohouse.view.ActuatorCategoryRenderer;
import it.unibs.ing.domohouse.view.ActuatorRenderer;
import it.unibs.ing.domohouse.view.ArtifactRenderer;
import it.unibs.ing.domohouse.view.HousingUnitRenderer;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.view.ManageableRenderer;
import it.unibs.ing.domohouse.view.RoomRenderer;
import it.unibs.ing.domohouse.view.SensorCategoryRenderer;
import it.unibs.ing.domohouse.view.SensorRenderer;

import java.io.PrintWriter;
import java.util.Scanner;

import it.unibs.ing.domohouse.controller.ControllerStrings;

public class MasterController {

	// Model
	private DataFacade dataFacade;
	private Authenticator authenticator;
	private ClockStrategy clock;
	private ObjectFabricator objectFabricator;
	private LibImporter libImporter;
	private Loader loader;
	private Saver saver;
	private LogWriter log;
	private RuleParser ruleParser;
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
		dataFacade = new DataFacade();
		ruleParser = new RuleParser(dataFacade);
		checkConfigFileExistence(output);
		rulesWorker = new RulesWorker(dataFacade, clock);
		objectFabricator = new ObjectFabricator(dataFacade, ruleParser);
		libImporter = new LibImporter(dataFacade, objectFabricator);
		log = new LogWriter();
		renderer = buildChain();

		connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		authenticator = new DatabaseAuthenticator(new HashCalculator(), connector);
		loader = new DatabaseLoader(connector, objectFabricator);
		saver = new FileSaver();
		loadFromDB();

		
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
		userUnitInputHandler = new UserUnitInputHandler(dataFacade, objectFabricator, output, input);
		userRoomInputHandler = new UserRoomInputHandler(dataFacade, output, input);
		maintainerInputHandler = new MaintainerInputHandler(dataFacade, objectFabricator, output, input);
		maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, objectFabricator, output, input);
		maintainerRoomInputHandler = new MaintainerRoomInputHandler(dataFacade, objectFabricator, output, input);
	}

	private void setControllers(PrintWriter output, RawInputHandler input) {
		maintainerRoomController = new MaintainerRoomController(dataFacade, maintainerRoomInputHandler, log, renderer,
				clock, output, input);
		maintainerUnitController = new MaintainerUnitController(dataFacade, log, renderer, maintainerUnitInputHandler,
				clock, output, input);
		maintainerController = new MaintainerController(dataFacade, log, renderer, loader, maintainerInputHandler,
				saver, libImporter, clock, output, input);
		userRoomController = new UserRoomController(dataFacade, log, renderer, userRoomInputHandler, clock, output,
				input);
		userUnitController = new UserUnitController(dataFacade, log, renderer, userUnitInputHandler, clock, output,
				input);
		userController = new UserController(dataFacade, log, renderer, userInputHandler, clock, output, input);
		loginController = new LoginController(dataFacade, log, authenticator, loader, clock, output, input);

		loginController.setMaintainerController(maintainerController);
		loginController.setUserController(userController);
		userController.setUserUnitController(userUnitController);
		userUnitController.setUserRoomController(userRoomController);
		maintainerController.setMaintainerUnitController(maintainerUnitController);
		maintainerUnitController.setMaintainerRoomController(maintainerRoomController);
	}
	
	private void loadFromDB() {
		assert loader != null;

		loader.loadSensorCategories();
		loader.loadActuatorCategories();

		assert controllerInvariant() : ControllerStrings.WRONG_INVARIANT;
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

	private boolean controllerInvariant() {
		return true;
	}
}
