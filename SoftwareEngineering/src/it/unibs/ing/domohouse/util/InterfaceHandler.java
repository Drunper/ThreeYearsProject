package it.unibs.ing.domohouse.util;

import it.unibs.ing.domohouse.HomeLogin;
import it.unibs.ing.domohouse.components.HousingUnit;

public class InterfaceHandler {
	
	private HomeLogin login;
	private InputHandler inputHandler;
	private DataHandler dataHandler;
	private FileLoader loader;
	private FileSaver saver;
	private boolean firstStart;

	//MENU
	private final Menu menu = new Menu(Strings.LOGIN_MENU_TITLE, Strings.LOGIN_VOICES);
	private final Menu userMenu = new Menu(Strings.USER_MENU_TITLE, Strings.USER_VOICES);
	private final Menu roomMenu  = new Menu(Strings.USER_ROOM_MENU_TITLE, Strings.ROOM_VOICES);
	private final Menu maintainerMenu = new Menu(Strings.MAINTAINER_MENU_TITLE, Strings.MAINTAINER_VOICES);
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
		
		OutputHandler.clearOutput();
		String user;
		int scelta;
		do
		{
			scelta = menu.select();
			switch(scelta)
			{
				case 1: 
					if(firstStart) {System.out.println("Accesso vietato! Per il primo avvio chiamare un manutentore per configurare il sistema");}
					else {
						user = RawInputHandler.readNotVoidString(Strings.INSERT_USER);
						if(!user.equalsIgnoreCase(Strings.BACK_CHARACTER))
						{
							System.out.println(Strings.WELCOME + user);
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
					}
					while (!user.equalsIgnoreCase(Strings.BACK_CHARACTER) && !ok);
					if (ok) {
						if(firstStart) {
							System.out.println("Creazione dati di base per la prima configurazione...");
							dataHandler.addHouse(new HousingUnit("Casa","Inserire una descrizione.."));
						}
						showMaintainerMenu();
					}
					break;
			}
		}
		while (scelta != 0);
		
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	private void showMaintainerMenu() {
		assert maintainerMenu != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do
		{
			int choice = maintainerMenu.select();
			switch(choice) {
				case 0:
					exitFlag = true;
					break;
				case 1:
					OutputHandler.clearOutput();
					OutputHandler.printHousingUnit(dataHandler.getHousingUnitString());
					break;
				case 2:
					inputHandler.changeHouseDescription();
					break;
				case 3:
					OutputHandler.clearOutput();
					if(dataHandler.getRoomList().length == 0) {
						System.out.println(Strings.NO_ROOM);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getRoomList());
					System.out.println();
					System.out.println();
					
					String selectedRoom = inputHandler.safeInsertRoom();
					
					showMaintainerRoomMenu(selectedRoom);
					break;
				case 4:
					inputHandler.readRoomFromUser();
					break;
				case 5:
					OutputHandler.clearOutput();
					if(dataHandler.getSensorCategoryList().length == 0) {
						System.out.println(Strings.NO_SENSOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getSensorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedSensCategory = inputHandler.safeInsertSensorCategory();
					
					OutputHandler.printSensorCategory(dataHandler.getSensorCategoryString(selectedSensCategory));
					break;
				case 6:
					OutputHandler.clearOutput();
					if(dataHandler.getActuatorCategoryList().length == 0) {
						System.out.println(Strings.NO_ACTUATOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getActuatorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedActuCategory = inputHandler.safeInsertActuatorCategory();
					OutputHandler.printActuatorCategory(dataHandler.getActuatorCategoryString(selectedActuCategory));
					break;
				case 7:
					inputHandler.readSensorCategoryFromUser();
					break;
				case 8:
					inputHandler.readActuatorCategoryFromUser();
					break;		
				case 9:
					saver.createDirs();
					saver.writeDataHandlerToFile(dataHandler);
					break;
			}
		}
		while(exitFlag != true);
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	private void showUserMenu() {
		assert userMenu != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do {
			int choice = userMenu.select();
			switch(choice) {
				case 0:
					exitFlag = true;
					break;
				case 1:
					OutputHandler.clearOutput();
					OutputHandler.printHousingUnit(dataHandler.getHousingUnitString());
					break;
				case 2:
					OutputHandler.clearOutput();
					if(dataHandler.getRoomList().length == 0) {
						System.out.println(Strings.NO_ROOM);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getRoomList());
					System.out.println();
					System.out.println();
					String selectedRoom = inputHandler.safeInsertRoom();
					showUserRoomMenu(selectedRoom);
					break;
				case 3:
					OutputHandler.clearOutput();
					if(dataHandler.getSensorCategoryList().length == 0) {
						System.out.println(Strings.NO_SENSOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getSensorCategoryList());
					System.out.println();
					System.out.println();
					String selectedSensCategory = inputHandler.safeInsertSensorCategory();			
					OutputHandler.printSensorCategory(dataHandler.getSensorCategoryString(selectedSensCategory));
					break;
				case 4:
					OutputHandler.clearOutput();
					if(dataHandler.getActuatorCategoryList().length == 0) {
						System.out.println(Strings.NO_ACTUATOR_CATEGORY);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getActuatorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedActuCategory = inputHandler.safeInsertActuatorCategory();
					OutputHandler.printSensorCategory(dataHandler.getActuatorCategoryString(selectedActuCategory));
					break;
			}
		}
		while(exitFlag!=true);
		
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	private void showUserRoomMenu(String selectedRoom) {
		assert selectedRoom != null;
		assert roomMenu != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do {
			int choice = roomMenu.select();
			switch(choice) {
			case 0:
				exitFlag = true;
				break;
			case 1:
				OutputHandler.clearOutput();
				OutputHandler.printRoom(dataHandler.getRoomString(selectedRoom));
				break;
			case 2:
				OutputHandler.clearOutput();
				if(dataHandler.getSensorNames(selectedRoom).length == 0) {
					System.out.println(Strings.NO_SENSOR);
					break;
				}
				OutputHandler.printListOfString(dataHandler.getSensorNames(selectedRoom));
				System.out.println();
				System.out.println();
				String selectedSensor = inputHandler.safeInsertSensor();
				OutputHandler.printSensor(dataHandler.getSensorString((selectedSensor)));
				break;
			case 3:
				OutputHandler.clearOutput();
				if(dataHandler.getActuatorNames(selectedRoom).length == 0) {
					System.out.println(Strings.NO_ACTUATOR);
					break;
				}
				OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedRoom));
				System.out.println();
				System.out.println();
				String selectedActuator = inputHandler.safeInsertActuator();
				OutputHandler.printActuator(dataHandler.getActuatorString(selectedActuator));
				break;
			case 4:
				OutputHandler.clearOutput();
				if(dataHandler.getArtifactNames(selectedRoom).length == 0) {
					System.out.println(Strings.NO_ARTIFACT);
					break;
				}
				OutputHandler.printListOfString(dataHandler.getArtifactNames(selectedRoom));
				System.out.println();
				System.out.println();			
				String selectedArtifact = inputHandler.safeInsertArtifact();
				OutputHandler.printArtifact(dataHandler.getArtifactString(selectedArtifact));
				break;	
			}
		}
		while(exitFlag!=true);		
		
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	private void showMaintainerRoomMenu(String selectedRoom) {
		assert selectedRoom != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do {
			int choice = maintainerRoomMenu.select();
			switch(choice) {
				case 0:
					exitFlag = true;
					break;
				case 1:
					OutputHandler.clearOutput();
					OutputHandler.printRoom(dataHandler.getRoomString(selectedRoom));
					break;
				case 2:
					OutputHandler.clearOutput();
					if(dataHandler.getSensorNames(selectedRoom).length == 0) {
						System.out.println(Strings.NO_SENSOR);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getSensorNames(selectedRoom));
					System.out.println();
					System.out.println();			
					String selectedSensor = inputHandler.safeInsertSensor();
					OutputHandler.printSensor(dataHandler.getSensorString(selectedSensor));
					break;
				case 3:
					OutputHandler.clearOutput();
					if(dataHandler.getActuatorNames(selectedRoom).length == 0) {
						System.out.println(Strings.NO_ACTUATOR);
					}
					OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedRoom));
					System.out.println();
					System.out.println();
					String selectedActuator = inputHandler.safeInsertActuator();
					OutputHandler.printActuator(dataHandler.getActuatorString(selectedActuator));
					break;
				case 4:
					OutputHandler.clearOutput();
					if(dataHandler.getArtifactNames(selectedRoom).length == 0) {
						System.out.println(Strings.NO_ARTIFACT);
					}
					OutputHandler.printListOfString(dataHandler.getArtifactNames(selectedRoom));
					System.out.println();
					System.out.println();
					String selectedArtifact = inputHandler.safeInsertArtifact();
					OutputHandler.printArtifact(dataHandler.getArtifactString(selectedArtifact));
					break;	
				case 5:
					inputHandler.changeRoomDescription(selectedRoom);
					break;
				case 6:
					inputHandler.readNumericSensorFromUser(selectedRoom);
					break;
				case 7:
					inputHandler.readActuatorFromUser(selectedRoom);
					break;
				case 8:
					inputHandler.readArtifactFromUser(selectedRoom);
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

