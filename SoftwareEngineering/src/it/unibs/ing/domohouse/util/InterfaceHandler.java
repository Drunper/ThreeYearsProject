package it.unibs.ing.domohouse.util;

import it.unibs.ing.domohouse.HomeLogin;

public class InterfaceHandler {
	
	private HomeLogin login;
	private InputHandler inputHandler;
	private DataHandler dataHandler;
	private FileLoader loader;
	private FileSaver saver;
	private LogWriter logWriter;
	private boolean firstStart;

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
		logWriter = new LogWriter();
		checkExistenceDataHandler();
		OperatingModesHandler.fillOperatingModes();
		saver.createDirs();
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
					if(firstStart) {System.out.println(Strings.USER_FIRST_ACCESS_PROHIBITED);}
					else {
						user = RawInputHandler.readNotVoidString(Strings.INSERT_USER);
						if(!user.equalsIgnoreCase(Strings.BACK_CHARACTER))
						{
							System.out.println(Strings.WELCOME + user);
							logWriter.write("L'utente " + user + " ha effettuato il login\n");
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
						if(firstStart)	System.out.println(Strings.BASIC_FILE_CREATION);
						logWriter.write("Il manutentore " + user + " ha effettuato il login\n");
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
		
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do
		{
			int choice = userMenu.select();
			switch(choice) {
				case 0: 
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
					OutputHandler.printActuatorCategory(dataHandler.getActuatorCategoryString(selectedActuCategory));
					break;
			}
		}while(exitFlag != true);
		
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
					inputHandler.readHouseFromUser();
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
					OutputHandler.printActuatorCategory(dataHandler.getActuatorCategoryString(selectedActuCategory));
					break;
				case 5:
					inputHandler.readSensorCategoryFromUser();
					break;
				case 6:
					inputHandler.readActuatorCategoryFromUser();
					break;		
				case 7:
					firstStart = false;
					saver.writeDataHandlerToFile(dataHandler);
					System.out.println(Strings.DATA_SAVED);
					break;
			}
		}while(exitFlag != true);
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
	}
	
	
	private void showMaintainerUnitMenu(String selectedHouse) {
		assert maintainerUnitMenu != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do
		{
			int choice = maintainerUnitMenu.select();
			switch(choice) {
				case 0:
					exitFlag = true;
					break;
				case 1:
					OutputHandler.clearOutput();
					OutputHandler.printHousingUnit(dataHandler.getHousingUnitString(selectedHouse));
					break;
				case 2:
					inputHandler.changeHouseDescription(selectedHouse);
					break;
				case 3:
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
					inputHandler.readRoomFromUser(selectedHouse);
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
			}
		}
		while(exitFlag != true);
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	private void showUserUnitMenu(String selectedHouse) {
		assert userUnitMenu != null;
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		OutputHandler.clearOutput();
		boolean exitFlag = false;
		do {
			int choice = userUnitMenu.select();
			switch(choice) {
				case 0:
					exitFlag = true;
					break;
				case 1:
					OutputHandler.clearOutput();
					OutputHandler.printHousingUnit(dataHandler.getHousingUnitString(selectedHouse));
					break;
				case 2:
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
					OutputHandler.printActuatorCategory(dataHandler.getActuatorCategoryString(selectedActuCategory));
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
				OutputHandler.printRoom(dataHandler.getRoomString(selectedHouse, selectedRoom));
				break;
			case 2:
				OutputHandler.clearOutput();
				if(dataHandler.getSensorNames(selectedHouse, selectedRoom).length == 0) {
					System.out.println(Strings.NO_SENSOR);
					break;
				}
				OutputHandler.printListOfString(dataHandler.getSensorNames(selectedHouse, selectedRoom));
				System.out.println();
				System.out.println();
				String selectedSensor = inputHandler.safeInsertSensor(selectedHouse);
				OutputHandler.printSensor(dataHandler.getSensorString(selectedHouse, selectedSensor));
				break;
			case 3:
				OutputHandler.clearOutput();
				if(dataHandler.getActuatorNames(selectedHouse, selectedRoom).length == 0) {
					System.out.println(Strings.NO_ACTUATOR);
					break;
				}
				OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedHouse, selectedRoom));
				System.out.println();
				System.out.println();
				String selectedActuator = inputHandler.safeInsertActuator(selectedHouse);
				OutputHandler.printActuator(dataHandler.getActuatorString(selectedHouse, selectedActuator));
				break;
			case 4:
				OutputHandler.clearOutput();
				if(dataHandler.getActuatorNames(selectedHouse, selectedRoom).length == 0) {
					System.out.println(Strings.NO_ACTUATOR);
					break;
				}
				OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedHouse, selectedRoom));
				String selectedAct = inputHandler.safeInsertActuator(selectedHouse);
				inputHandler.setOperatingMode(selectedHouse, selectedRoom, selectedAct);
				break;
			case 5:
				OutputHandler.clearOutput();
				if(dataHandler.getArtifactNames(selectedHouse, selectedRoom).length == 0) {
					System.out.println(Strings.NO_ARTIFACT);
					break;
				}
				OutputHandler.printListOfString(dataHandler.getArtifactNames(selectedHouse, selectedRoom));
				System.out.println();
				System.out.println();			
				String selectedArtifact = inputHandler.safeInsertArtifact(selectedHouse);
				OutputHandler.printArtifact(dataHandler.getArtifactString(selectedHouse, selectedArtifact));
				break;	
			}
		}
		while(exitFlag!=true);		
		
		assert interfaceHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	private void showMaintainerRoomMenu(String selectedHouse, String selectedRoom) {
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
					OutputHandler.printRoom(dataHandler.getRoomString(selectedHouse, selectedRoom));
					break;
				case 2:
					OutputHandler.clearOutput();
					if(dataHandler.getSensorNames(selectedHouse, selectedRoom).length == 0) {
						System.out.println(Strings.NO_SENSOR);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getSensorNames(selectedHouse, selectedRoom));
					System.out.println();
					System.out.println();			
					String selectedSensor = inputHandler.safeInsertSensor(selectedHouse);
					OutputHandler.printSensor(dataHandler.getSensorString(selectedHouse, selectedSensor));
					break;
				case 3:
					OutputHandler.clearOutput();
					if(dataHandler.getActuatorNames(selectedHouse, selectedRoom).length == 0) {
						System.out.println(Strings.NO_ACTUATOR);
					}
					OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedHouse, selectedRoom));
					System.out.println();
					System.out.println();
					String selectedActuator = inputHandler.safeInsertActuator(selectedHouse);
					OutputHandler.printActuator(dataHandler.getActuatorString(selectedHouse, selectedActuator));
					break;
				case 4:
					OutputHandler.clearOutput();
					if(dataHandler.getActuatorNames(selectedHouse, selectedRoom).length == 0) {
						System.out.println(Strings.NO_ACTUATOR);
						break;
					}
					OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedHouse, selectedRoom));
					String selectedAct = inputHandler.safeInsertActuator(selectedHouse);
					inputHandler.setOperatingMode(selectedHouse, selectedRoom, selectedAct);
					break;
				case 5:
					OutputHandler.clearOutput();
					if(dataHandler.getArtifactNames(selectedHouse, selectedRoom).length == 0) {
						System.out.println(Strings.NO_ARTIFACT);
					}
					OutputHandler.printListOfString(dataHandler.getArtifactNames(selectedHouse, selectedRoom));
					System.out.println();
					System.out.println();
					String selectedArtifact = inputHandler.safeInsertArtifact(selectedHouse);
					OutputHandler.printArtifact(dataHandler.getArtifactString(selectedHouse, selectedArtifact));
					break;	
				case 6:
					inputHandler.changeRoomDescription(selectedHouse, selectedRoom);
					break;
				case 7:		
					inputHandler.readSensorFromUser(selectedHouse, selectedRoom);
					break;
				case 8:
					inputHandler.readActuatorFromUser(selectedHouse, selectedRoom);
					break;
				case 9:
					inputHandler.readArtifactFromUser(selectedHouse, selectedRoom);
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

