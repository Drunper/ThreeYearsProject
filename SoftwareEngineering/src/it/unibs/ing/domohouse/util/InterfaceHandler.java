package it.unibs.ing.domohouse.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.unibs.ing.domohouse.HomeLogin;
import it.unibs.ing.domohouse.components.HousingUnit;

public class InterfaceHandler {
	
	private HomeLogin login;
	private InputHandler inputHandler;
	private DataHandler dataHandler;
	private FileLoader loader;
	private FileSaver saver;
	private boolean firstStart;
	private ScheduledExecutorService checkThread;
	private LibImporter libImporter;
	private LogWriter log;

	//MENU
	private final Menu menu = new Menu(Strings.LOGIN_MENU_TITLE, Strings.LOGIN_VOICES);
	private final Menu userMenu = new Menu(Strings.USER_UNIT_MENU_TITLE, Strings.USER_UNIT_MENU);
	private final Menu userUnitMenu = new Menu(Strings.USER_MENU_TITLE, Strings.USER_VOICES);
	private final Menu roomMenu  = new Menu(Strings.USER_ROOM_MENU_TITLE, Strings.ROOM_VOICES);
	private final Menu maintainerMenu = new Menu(Strings.MAINTAINER_UNIT_MENU_TITLE, Strings.MAINTAINER_UNIT_MENU);
	private final Menu maintainerUnitMenu = new Menu(Strings.MAINTAINER_MENU_TITLE, Strings.MAINTAINER_VOICES);
	private final Menu maintainerRoomMenu = new Menu(Strings.MAINTAINER_ROOM_MENU_TITLE, Strings.MAINTAINER_ROOM_VOICES);	
	/*
	 * invariante login, dataHandler, loader, saver != null; 
	 */
	public InterfaceHandler() {
		login = new HomeLogin();
		dataHandler = new DataHandler();
		inputHandler = new InputHandler(dataHandler);
		loader = new FileLoader();
		saver = new FileSaver();
		login.addEntry(Strings.MAINTAINER_USER, Strings.PASSWORD);
		checkExistenceDataHandler();
		OperatingModesHandler.fillOperatingModes();
		saver.createDirs();
		checkThread = Executors.newSingleThreadScheduledExecutor();
		libImporter = new LibImporter(dataHandler, inputHandler);
		log = new LogWriter();
		
		checkThread.scheduleAtFixedRate(new Runnable() {
			public void run() {
				try {
					for(String houseName : dataHandler.getHouseList()) {
						HousingUnit h = (HousingUnit) dataHandler.getHousingUnit(houseName);
						h.checkRules();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 1, TimeUnit.SECONDS); //parte subito e ogni 1 secondi controlla le rules
	}
	
	private void checkExistenceDataHandler() {
		assert loader != null;
		
		if(loader.getDataHandler() != null) { //Se è presente un file dataHandler lo carico
			System.out.println(Strings.LOADING_FILE);
			dataHandler = loader.getDataHandler();
			inputHandler = new InputHandler(dataHandler);
			loader = new FileLoader();
			firstStart = false;
			System.out.println(Strings.LOADED);
		}else { //Se non è presente		
			System.out.println(Strings.NO_FILE);
			firstStart = true;
		}
		
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public void show() {
		assert menu != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		log.write(Strings.LOG_SHOW_MAIN_MENU);
		OutputHandler.clearOutput();
		String user;
		int scelta;
		do
		{
			scelta = menu.select();
			switch(scelta)
			{
				case 1: 
					if(firstStart) {
						log.write(Strings.LOG_FIRST_ACCESS);
						System.out.println(Strings.USER_FIRST_ACCESS_PROHIBITED);}
					else {
						user = RawInputHandler.readNotVoidString(Strings.INSERT_USER);
						if(!user.equalsIgnoreCase(Strings.BACK_CHARACTER))
						{
							System.out.println(Strings.WELCOME + user);
							log.write(user + Strings.LOG_SYSTEM_ACCESS);
							showUserMenu();
						}
					}
					break;
					
				case 2:
					String password;
					boolean ok;
					do
					{
						ok = false;
						user = RawInputHandler.readNotVoidString(Strings.INSERT_USER);
						if(!user.equalsIgnoreCase(Strings.BACK_CHARACTER))
						{	
							password = RawInputHandler.readNotVoidString(Strings.INSERT_PASSWORD);
							ok = login.checkPassword(user, password);
							if(!ok)
								System.out.println(Strings.USER_OR_PASSWORD_ERROR);
						}
						log.write("Il manutentore "+ user + Strings.LOG_SYSTEM_ACCESS);
					}
					while (!user.equalsIgnoreCase(Strings.BACK_CHARACTER) && !ok);
					if (ok) {
						if(firstStart) {
							System.out.println(Strings.BASIC_FILE_CREATION);
							log.write(Strings.LOG_BASIC_FILE_CREATION);
						}
						showMaintainerMenu();
					}
					break;
			}
		}
		while (scelta != 0);
		
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	private void showUserMenu() {
		assert userMenu != null; 
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		log.write(Strings.LOG_SHOW_USER_MENU);
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do
		{
			int choice = userMenu.select();
			switch(choice) {
				case 0: 
					log.write(Strings.LOG_EXIT_MENU);
					exitFlag = true;
					break;
				case 1:
					OutputHandler.clearOutput();
					if(dataHandler.getHouseList().length > 0) {
						OutputHandler.printListOfString(dataHandler.getHouseList());
						String selectedHouse = inputHandler.safeInsertHouse();
						showUserUnitMenu(selectedHouse);
					}else{
						System.out.println(Strings.NO_HOUSE);
					}
					break;
				case 2:
					OutputHandler.clearOutput();
					if(dataHandler.getSensorCategoryList().length == 0) {
						System.out.println(Strings.NO_SENSOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getSensorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedSensCategory = inputHandler.safeInsertSensorCategory();
					log.write(Strings.LOG_SHOW_SENSOR_CATEGORY + selectedSensCategory);
					OutputHandler.printSensorCategory(dataHandler.getSensorCategoryString(selectedSensCategory));
					break;
				case 3:
					OutputHandler.clearOutput();
					if(dataHandler.getActuatorCategoryList().length == 0) {
						System.out.println(Strings.NO_ACTUATOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getActuatorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedActuCategory = inputHandler.safeInsertActuatorCategory();
					log.write("Strings.LOG_SHOW_ACTUATOR_CATEGORY  " + selectedActuCategory);
					OutputHandler.printActuatorCategory(dataHandler.getActuatorCategoryString(selectedActuCategory));
					break;
				case 4:
					//aggiorna ora
					OutputHandler.clearOutput();
					log.write(Strings.LOG_REFRESH_HOUR);
					break;
			}
		}while(exitFlag != true);
		
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	private void showMaintainerMenu() {
		assert maintainerMenu != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		log.write(Strings.LOG_SHOW_MAINTAINTER_MENU);
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do 
		{
			int choice = maintainerMenu.select();
			switch(choice) {
				case 0:
					log.write(Strings.LOG_EXIT_MENU);
					exitFlag = true;
					break;
				case 1:
					OutputHandler.clearOutput();
					if(dataHandler.getHouseList().length > 0) {
						OutputHandler.printListOfString(dataHandler.getHouseList());
						String selectedHouse = inputHandler.safeInsertHouse();
						showMaintainerUnitMenu(selectedHouse);
					}else{
						System.out.println(Strings.NO_HOUSE);
					}
					break;
				case 2:
					OutputHandler.clearOutput();
					log.write(Strings.LOG_INSERT_HOUSE);
					inputHandler.readHouseFromUser();
					log.write(Strings.LOG_INSERT_HOUSE_SUCCESS);
					break; 
				case 3:
					//visualizza categorie di sensori
					OutputHandler.clearOutput();
					if(dataHandler.getSensorCategoryList().length == 0) {
						System.out.println(Strings.NO_SENSOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getSensorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedSensCategory = inputHandler.safeInsertSensorCategory();
					log.write(Strings.LOG_SHOW_SENSOR_CATEGORY + selectedSensCategory);
					
					OutputHandler.printSensorCategory(dataHandler.getSensorCategoryString(selectedSensCategory));
					break;
				case 4: 
					//visualizza categoria di attuatore
					OutputHandler.clearOutput();
					if(dataHandler.getActuatorCategoryList().length == 0) {
						System.out.println(Strings.NO_ACTUATOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getActuatorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedActuCategory = inputHandler.safeInsertActuatorCategory();
					log.write(Strings.LOG_SHOW_ACTUATOR_CATEGORY + selectedActuCategory);
					OutputHandler.printActuatorCategory(dataHandler.getActuatorCategoryString(selectedActuCategory));
					break;
				case 5:
					//crea sensor category
					log.write(Strings.LOG_INSERT_SENSOR_CATEGORY);
					inputHandler.readSensorCategoryFromUser();
					log.write(Strings.LOG_INSERT_SENSOR_CATEGORY_SUCCESS);
					break;
				case 6:
					//crea actuator category
					log.write(Strings.LOG_INSERT_ACTUATOR_CATEGORY);
					inputHandler.readActuatorCategoryFromUser();
					log.write(Strings.LOG_INSERT_ACTUATOR_CATEGORY_SUCCESS);
					break;		
				case 7:
					//salva file
					firstStart = false;
					log.write(Strings.LOG_SAVING_DATA);
					saver.writeDataHandlerToFile(dataHandler);
					System.out.println(Strings.DATA_SAVED);
					log.write(Strings.DATA_SAVED);
					break;
				case 8:
					//importa file
					OutputHandler.clearOutput();
					log.write(Strings.LOG_IMPORTING_FILE);
					if(!libImporter.importFile()) {
						String error = libImporter.getErrorsString();
						System.out.println(error);
						log.write(Strings.LOG_ERROR_IMPORT + error);
					}else {
						log.write(Strings.SUCCESS_IMPORT_FILE);
						System.out.println(Strings.SUCCESS_IMPORT_FILE);
					}
					break;
				case 9:
					//aggiorna ora
					log.write(Strings.LOG_REFRESH_HOUR);
					OutputHandler.clearOutput();
					break;
			}
		}while(exitFlag != true);
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
	}
	
	private void showMaintainerUnitMenu(String selectedHouse) {
		assert maintainerUnitMenu != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		OutputHandler.clearOutput();
		log.write(Strings.LOG_SHOW_MAINTAINTER_UNIT_MENU);
		boolean exitFlag = false;
		do
		{
			int choice = maintainerUnitMenu.select();
			switch(choice) {
				case 0:
					//uscita menu
					log.write(Strings.LOG_EXIT_MENU);
					exitFlag = true;
					break;
				case 1:
					//visualizza descrizione unità immobiliare
					OutputHandler.clearOutput();
					log.write(Strings.LOG_DESCR_HOUSE);
					OutputHandler.printHousingUnit(dataHandler.getHousingUnitString(selectedHouse));
					break;
				case 2:
					//cambia descrizione casa
					log.write(Strings.LOG_CHANGE_DESCR_HOUSE);
					inputHandler.changeHouseDescription(selectedHouse);
					break;
				case 3:
					//visualizza room menu
					OutputHandler.clearOutput();
					if(dataHandler.getRoomList(selectedHouse).length == 0) {
						System.out.println(Strings.NO_ROOM);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getRoomList(selectedHouse));
					System.out.println();
					System.out.println();
					
					String selectedRoom = inputHandler.safeInsertRoom(selectedHouse);
					showMaintainerRoomMenu(selectedHouse, selectedRoom);
					break;
				case 4:
					//Inserisci stanza
					log.write(Strings.LOG_INSERT_ROOM);
					inputHandler.readRoomFromUser(selectedHouse);
					log.write(Strings.LOG_INSERT_ROOM_SUCCESS);
					break;
				case 5:
					//visualizza sensor category
					OutputHandler.clearOutput();
					if(dataHandler.getSensorCategoryList().length == 0) {
						System.out.println(Strings.NO_SENSOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getSensorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedSensCategory = inputHandler.safeInsertSensorCategory();
					log.write(Strings.LOG_SHOW_SENSOR_CATEGORY);
					OutputHandler.printSensorCategory(dataHandler.getSensorCategoryString(selectedSensCategory));
					break;
				case 6:
					//visualizzazione actuator category
					OutputHandler.clearOutput();
					if(dataHandler.getActuatorCategoryList().length == 0) {
						System.out.println(Strings.NO_ACTUATOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getActuatorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedActuCategory = inputHandler.safeInsertActuatorCategory();
					log.write(Strings.LOG_SHOW_ACTUATOR_CATEGORY + selectedActuCategory);
					OutputHandler.printActuatorCategory(dataHandler.getActuatorCategoryString(selectedActuCategory));
					break;
				case 7:
					//aggiungi regola
					OutputHandler.clearOutput();
					log.write(Strings.LOG_INSERT_NEW_RULE);
					inputHandler.readRuleFromUser(selectedHouse);
					log.write(Strings.LOG_INSERT_NEW_RULE_SUCCESS);
					break;
				case 8:
					//visualizza regole attive
					OutputHandler.clearOutput();
					log.write(Strings.LOG_SHOW_ENABLED_RULES);
					OutputHandler.printListOfString(dataHandler.getHousingUnit(selectedHouse).getEnabledRulesList());
					break;
				case 9: 
					//visualizza tutte le regole
					OutputHandler.clearOutput();
					log.write(Strings.LOG_SHOW_ALL_RULES);
					OutputHandler.printListOfString(dataHandler.getHousingUnit(selectedHouse).getAllRulesList());
					break;
				case 10:
					//attiva/disattiva regola
					OutputHandler.clearOutput();
					log.write(Strings.LOG_ENABLE_DISABLE_RULE);
					inputHandler.setRuleState(selectedHouse);
					break;
				case 11:
					//aggiorna ora
					log.write(Strings.LOG_REFRESH_HOUR);
					OutputHandler.clearOutput();
					break;
			}
		}
		while(exitFlag != true);
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	private void showUserUnitMenu(String selectedHouse) {
		assert userUnitMenu != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		log.write(Strings.LOG_SHOW_USER_UNIT_MENU + selectedHouse);
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do {
			int choice = userUnitMenu.select();
			switch(choice) {
				case 0:
					log.write(Strings.LOG_EXIT_MENU);
					exitFlag = true;
					break;
				case 1:
					//visualizza descrizione unita immobiliare
					OutputHandler.clearOutput();
					log.write(Strings.LOG_DESCR_HOUSE);
					OutputHandler.printHousingUnit(dataHandler.getHousingUnitString(selectedHouse));
					break;
				case 2:
					//visualizza stanza
					OutputHandler.clearOutput();
					if(dataHandler.getRoomList(selectedHouse).length == 0) {
						System.out.println(Strings.NO_ROOM);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getRoomList(selectedHouse));
					System.out.println();
					System.out.println();
					String selectedRoom = inputHandler.safeInsertRoom(selectedHouse);
					showUserRoomMenu(selectedHouse, selectedRoom);
					break;
				case 3:
					//visualizza categorie di sensori
					OutputHandler.clearOutput();
					if(dataHandler.getSensorCategoryList().length == 0) {
						System.out.println(Strings.NO_SENSOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getSensorCategoryList());
					System.out.println();
					System.out.println();
					String selectedSensCategory = inputHandler.safeInsertSensorCategory();			
					log.write(Strings.LOG_SHOW_SENSOR_CATEGORY + selectedSensCategory);
					OutputHandler.printSensorCategory(dataHandler.getSensorCategoryString(selectedSensCategory));
					break;
				case 4:
					//visualizza categorie di attuatori
					OutputHandler.clearOutput();
					if(dataHandler.getActuatorCategoryList().length == 0) {
						System.out.println(Strings.NO_ACTUATOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getActuatorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedActuCategory = inputHandler.safeInsertActuatorCategory();
					log.write(Strings.LOG_SHOW_ACTUATOR_CATEGORY + selectedActuCategory);
					OutputHandler.printActuatorCategory(dataHandler.getActuatorCategoryString(selectedActuCategory));
					break;
				case 5:
					//aggiungi regola
					OutputHandler.clearOutput();
					log.write(Strings.LOG_INSERT_NEW_RULE);
					inputHandler.readRuleFromUser(selectedHouse);
					log.write(Strings.LOG_INSERT_NEW_RULE_SUCCESS);
					break;
				case 6:
					//visualizza regole attive
					OutputHandler.clearOutput();
					log.write(Strings.LOG_SHOW_ENABLED_RULES);
					OutputHandler.printListOfString(dataHandler.getHousingUnit(selectedHouse).getEnabledRulesList());
					break;
				case 7: 
					//visualizza tutte le regole
					OutputHandler.clearOutput();
					log.write(Strings.LOG_SHOW_ALL_RULES);
					OutputHandler.printListOfString(dataHandler.getHousingUnit(selectedHouse).getAllRulesList());
					break;
				case 8:
					//attiva/disattiva regola
					OutputHandler.clearOutput();
					log.write(Strings.LOG_ENABLE_DISABLE_RULE);
					inputHandler.setRuleState(selectedHouse);
					break;
				case 9:
					//aggiorna ora
					log.write(Strings.LOG_REFRESH_HOUR);
					OutputHandler.clearOutput();
					break;
			}
		}
		while(exitFlag!=true);
		
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	private void showUserRoomMenu(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null;
		assert roomMenu != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		log.write(Strings.LOG_SHOW_USER_ROOM_MENU);
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do {
			int choice = roomMenu.select();
			switch(choice) {
			case 0:
				log.write(Strings.LOG_EXIT_MENU);
				exitFlag = true;
				break;
			case 1:
				//visualizza descrizione stanza
				OutputHandler.clearOutput();
				log.write(Strings.LOG_SHOW_DESCR_ROOM + selectedRoom);
				OutputHandler.printRoom(dataHandler.getRoomString(selectedHouse, selectedRoom));
				break;
			case 2:
				//visualizza sensore
				OutputHandler.clearOutput();
				if(dataHandler.getSensorNames(selectedHouse, selectedRoom).length == 0) {
					System.out.println(Strings.NO_SENSOR);
					break;
				}
				OutputHandler.printListOfString(dataHandler.getSensorNames(selectedHouse, selectedRoom));
				System.out.println();
				System.out.println();
				String selectedSensor = inputHandler.safeInsertSensor(selectedHouse);
				log.write(Strings.LOG_SHOW_SENSOR + selectedSensor);
				OutputHandler.printSensor(dataHandler.getSensorString(selectedHouse, selectedSensor));
				break;
			case 3:
				//visualizza attuatore
				OutputHandler.clearOutput();
				if(dataHandler.getActuatorNames(selectedHouse, selectedRoom).length == 0) {
					System.out.println(Strings.NO_ACTUATOR);
					break;
				}
				OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedHouse, selectedRoom));
				System.out.println();
				System.out.println();
				String selectedActuator = inputHandler.safeInsertActuator(selectedHouse);
				log.write(Strings.LOG_SHOW_ACTUATOR + selectedActuator);
				OutputHandler.printActuator(dataHandler.getActuatorString(selectedHouse, selectedActuator));
				break;
			case 4:
				//aziona attuatore
				OutputHandler.clearOutput();
				if(dataHandler.getActuatorNames(selectedHouse, selectedRoom).length == 0) {
					System.out.println(Strings.NO_ACTUATOR);
					break;
				}
				OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedHouse, selectedRoom));
				String selectedAct = inputHandler.safeInsertActuator(selectedHouse);
				inputHandler.setOperatingMode(selectedHouse, selectedRoom, selectedAct);
				log.write(Strings.LOG_ACTUATOR_ACTION + selectedAct);
				dataHandler.updateRulesState();
				break;
			case 5:
				//visualizza artefatto
				OutputHandler.clearOutput();
				if(dataHandler.getArtifactNames(selectedHouse, selectedRoom).length == 0) {
					System.out.println(Strings.NO_ARTIFACT);
					break;
				}
				OutputHandler.printListOfString(dataHandler.getArtifactNames(selectedHouse, selectedRoom));
				System.out.println();
				System.out.println();			
				String selectedArtifact = inputHandler.safeInsertArtifact(selectedHouse);
				log.write(Strings.LOG_SHOW_ARTIFACT);
				OutputHandler.printArtifact(dataHandler.getArtifactString(selectedHouse, selectedArtifact));
				break;
			case 6:
				//attiva/disattiva dispositivo
				inputHandler.setDeviceState(selectedHouse, selectedRoom);
				log.write(Strings.LOG_ENABLE_DISABLE_DISP);
				dataHandler.updateRulesState();
				break;
			case 7:
				//aggiorna ora
				log.write(Strings.LOG_REFRESH_HOUR);
				OutputHandler.clearOutput();
				break;
			}
		}
		while(exitFlag!=true);		
		
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	private void showMaintainerRoomMenu(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		log.write(Strings.LOG_SHOW_MAINTAINTER_ROOM_MENU + selectedRoom);
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do {
			int choice = maintainerRoomMenu.select();
			switch(choice) {
				case 0:
					log.write(Strings.LOG_EXIT_MENU);
					exitFlag = true;
					break;
				case 1:
					//visualizza descrizione stanza
					OutputHandler.clearOutput();
					OutputHandler.printRoom(dataHandler.getRoomString(selectedHouse, selectedRoom));
					log.write(Strings.LOG_SHOW_DESCR_ROOM);
					break;
				case 2:
					//visualizza sensore
					OutputHandler.clearOutput();
					if(dataHandler.getSensorNames(selectedHouse, selectedRoom).length == 0) {
						System.out.println(Strings.NO_SENSOR);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getSensorNames(selectedHouse, selectedRoom));
					System.out.println();
					System.out.println();			
					String selectedSensor = inputHandler.safeInsertSensor(selectedHouse);
					log.write(Strings.LOG_SHOW_SENSOR + selectedSensor);
					OutputHandler.printSensor(dataHandler.getSensorString(selectedHouse, selectedSensor));
					break;
				case 3:
					//visualizza attuatore
					OutputHandler.clearOutput();
					if(dataHandler.getActuatorNames(selectedHouse, selectedRoom).length == 0) {
						System.out.println(Strings.NO_ACTUATOR);
					}
					OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedHouse, selectedRoom));
					System.out.println();
					System.out.println();
					String selectedActuator = inputHandler.safeInsertActuator(selectedHouse);
					log.write(Strings.LOG_SHOW_ACTUATOR + selectedActuator);
					OutputHandler.printActuator(dataHandler.getActuatorString(selectedHouse, selectedActuator));
					break;
				case 4:
					//aziona attuatore
					OutputHandler.clearOutput();
					if(dataHandler.getActuatorNames(selectedHouse, selectedRoom).length == 0) {
						System.out.println(Strings.NO_ACTUATOR);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedHouse, selectedRoom));
					String selectedAct = inputHandler.safeInsertActuator(selectedHouse);
					inputHandler.setOperatingMode(selectedHouse, selectedRoom, selectedAct);
					log.write(Strings.LOG_ACTUATOR_ACTION);
					dataHandler.updateRulesState();
					break;
				case 5:
					//visualizza artefatto
					OutputHandler.clearOutput();
					if(dataHandler.getArtifactNames(selectedHouse, selectedRoom).length == 0) {
						System.out.println(Strings.NO_ARTIFACT);
					}
					OutputHandler.printListOfString(dataHandler.getArtifactNames(selectedHouse, selectedRoom));
					System.out.println();
					System.out.println();
					String selectedArtifact = inputHandler.safeInsertArtifact(selectedHouse);
					log.write(Strings.LOG_SHOW_ACTUATOR + selectedArtifact);
					OutputHandler.printArtifact(dataHandler.getArtifactString(selectedHouse, selectedArtifact));
					break;	
				case 6:
					//modifica descrizione stanza
					log.write(Strings.LOG_CHANGE_ROOM_DESCR);
					inputHandler.changeRoomDescription(selectedHouse, selectedRoom);
					break;
				case 7:
					//inserisci sensore
					log.write(Strings.LOG_INSERT_SENSOR);
					inputHandler.readSensorFromUser(selectedHouse, selectedRoom);
					log.write(Strings.LOG_INSERT_SENSOR_SUCCESS);
					break;
				case 8:
					//inserisci attuatore
					log.write(Strings.LOG_INSERT_ACTUATOR);
					inputHandler.readActuatorFromUser(selectedHouse, selectedRoom);
					log.write(Strings.LOG_INSERT_ACTUATOR_SUCCESS);
					break;
				case 9:
					//inserisci artefatto
					log.write(Strings.LOG_INSERT_ARTIFACT);
					inputHandler.readArtifactFromUser(selectedHouse, selectedRoom);
					log.write(Strings.LOG_INSERT_ARTIFACT_SUCCESS);
					break;
				case 10:
					//attiva/dis disp
					inputHandler.setDeviceState(selectedHouse, selectedRoom);
					log.write(Strings.LOG_ENABLE_DISABLE_DISP);
					dataHandler.updateRulesState();
					break;
				case 11:
					//aggiorna ora
					log.write(Strings.LOG_REFRESH_HOUR);
					OutputHandler.clearOutput();
					break;
			}
		}
		while(exitFlag!=true);
		
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	private boolean interfaceHandlerInvariant() {
		boolean checkLogin = login != null;
		boolean checkDataHandler = dataHandler != null;
		boolean checkInputHandler = inputHandler != null;
		boolean checkLoader = loader != null;
		boolean checkSaver = saver != null;
		
		if(checkLogin && checkDataHandler && checkInputHandler && checkLoader && checkSaver) return true;
		return false;
	}

}

