package it.unibs.ing.domohouse.util;

import it.unibs.ing.domohouse.HomeLogin;
import it.unibs.ing.domohouse.HomeMain;
import it.unibs.ing.domohouse.components.HousingUnit;
import it.unibs.ing.domohouse.components.Room;

public class InterfaceHandler {
	
	private HomeLogin login;
	private InputHandler inputHandler;
	private DataHandler dataHandler;
	private FileLoader loader;
	private FileSaver saver;

	//MENU
	private final Menu menu = new Menu(Strings.LOGIN_MENU_TITLE, Strings.LOGIN_VOICES);
	private final Menu userMenu = new Menu(Strings.USER_MENU_TITLE, Strings.USER_VOICES);
	private final Menu roomMenu  = new Menu(Strings.USER_ROOM_MENU_TITLE, Strings.ROOM_VOICES);
	private final Menu maintainerMenu = new Menu(Strings.MAINTAINER_MENU_TITLE, Strings.MAINTAINER_VOICES);
	private final Menu maintainerRoomMenu = new Menu(Strings.MAINTAINER_ROOM_MENU_TITLE, Strings.MAINTAINER_ROOM_VOICES);	
	
	public InterfaceHandler() {
		login = new HomeLogin();
		dataHandler = new DataHandler();
		inputHandler = new InputHandler(dataHandler);
		loader = new FileLoader(inputHandler);
		saver = new FileSaver();
		login.addEntry(Strings.MAINTAINER_USER, Strings.PASSWORD); 
	}
	
	public void show() {
		OutputHandler.clearOutput();
		String user;
		int scelta;
		do
		{
			scelta = menu.select();
			switch(scelta)
			{
				case 1: 
					user = RawInputHandler.readNotVoidString(Strings.INSERT_USER);
					if(!user.equalsIgnoreCase(Strings.BACK_CHARACTER))
					{
						System.out.println(Strings.WELCOME + user);
						showUserMenu();
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
					if (ok)
						showMaintainerMenu();
					break;
			}
		}
		while (scelta != 0);
		saver.writeDataHandlerToFile(dataHandler);
	}
	
	private void showMaintainerMenu() {
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
					OutputHandler.printListOfString(dataHandler.getRoomList());
					System.out.println();
					System.out.println();
					
					String selectedRoom = safeInsertRoom();
					
					showMaintainerRoomMenu(selectedRoom);
					break;
				case 4:
					inputHandler.readRoomFromUser();
					break;
				case 5:
					OutputHandler.clearOutput();
					OutputHandler.printListOfString(dataHandler.getSensorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedSensCategory = safeInsertSensorCategory();
					
					OutputHandler.printSensorCategory(dataHandler.getSensorCategoryString(selectedSensCategory));
					break;
				case 6:
					OutputHandler.clearOutput();
					OutputHandler.printListOfString(dataHandler.getActuatorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedActuCategory = safeInsertActuatorCategory();
					OutputHandler.printSensorCategory(dataHandler.getActuatorCategoryString(selectedActuCategory));
					break;
				case 7:
					inputHandler.readSensorCategoryFromUser();
					break;
				case 8:
					inputHandler.readActuatorCategoryFromUser();
					break;		
				case 9:
					loader.createBasicFiles();
					break;
			}
		}
		while(exitFlag != true);
	}

	private void showUserMenu() {
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
					OutputHandler.printListOfString(dataHandler.getRoomList());
					System.out.println();
					System.out.println();
					String selectedRoom = safeInsertRoom();
					showUserRoomMenu(selectedRoom);
					break;
				case 3:
					OutputHandler.clearOutput();
					OutputHandler.printListOfString(dataHandler.getSensorCategoryList());
					System.out.println();
					System.out.println();
					String selectedSensCategory = safeInsertSensorCategory();			
					OutputHandler.printSensorCategory(dataHandler.getSensorCategoryString(selectedSensCategory));
					break;
				case 4:
					OutputHandler.clearOutput();
					OutputHandler.printListOfString(dataHandler.getActuatorCategoryList());
					System.out.println();
					System.out.println();
					
					String selectedActuCategory = safeInsertActuatorCategory();
					OutputHandler.printSensorCategory(dataHandler.getActuatorCategoryString(selectedActuCategory));
					break;
				case 5:
					loader.createBasicFiles();
					break;
			}
		}
		while(exitFlag!=true);
	}

	private void showUserRoomMenu(String selectedRoom) {
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
				OutputHandler.printListOfString(dataHandler.getSensorNames(selectedRoom));
				System.out.println();
				System.out.println();
				String selectedSensor = safeInsertSensor();
				OutputHandler.printSensor(dataHandler.getSensorString((selectedSensor)));
				break;
			case 3:
				OutputHandler.clearOutput();
				OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedRoom));
				System.out.println();
				System.out.println();
				String selectedActuator = safeInsertActuator();
				OutputHandler.printActuator(dataHandler.getActuatorString(selectedActuator));
				break;
			case 4:
				OutputHandler.clearOutput();
				OutputHandler.printListOfString(dataHandler.getArtifactNames(selectedRoom));
				System.out.println();
				System.out.println();			
				String selectedArtifact = safeInsertArtifact();
				OutputHandler.printArtifact(dataHandler.getArtifactString(selectedArtifact));
				break;	
			}
		}
		while(exitFlag!=true);		
	}
	
	private void showMaintainerRoomMenu(String selectedRoom) {
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
					OutputHandler.printListOfString(dataHandler.getSensorNames(selectedRoom));
					System.out.println();
					System.out.println();			
					String selectedSensor = safeInsertSensor();
					OutputHandler.printSensor(dataHandler.getSensorString(selectedSensor));
					break;
				case 3:
					OutputHandler.clearOutput();
					OutputHandler.printListOfString(dataHandler.getActuatorNames(selectedRoom));
					System.out.println();
					System.out.println();
					String selectedActuator = safeInsertActuator();
					OutputHandler.printActuator(dataHandler.getActuatorString(selectedActuator));
					break;
				case 4:
					OutputHandler.clearOutput();
					OutputHandler.printListOfString(dataHandler.getArtifactNames(selectedRoom));
					System.out.println();
					System.out.println();
					String selectedArtifact = safeInsertArtifact();
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
	}
	
	//FORSE DA SPOSTARE IN INPUTHANDLER QUESTI METODI QUA SOTTO 
	private String safeInsertSensorCategory() {
		String selectedSensCategory = RawInputHandler.readNotVoidString(Strings.INSERT_SENSOR_CATEGORY);
		if(dataHandler.hasSensorCategory(selectedSensCategory)) return selectedSensCategory;
		else do {
				selectedSensCategory = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_SENSOR_CATEGORY + " " + Strings.INSERT_SENSOR_CATEGORY);
		}while(!dataHandler.hasSensorCategory(selectedSensCategory));
		return selectedSensCategory;
	}
	
	private String safeInsertActuatorCategory() {
		String selectedActuCategory = RawInputHandler.readNotVoidString(Strings.INSERT_ACTUATOR_CATEGORY);
		if(dataHandler.hasActuatorCategory(selectedActuCategory)) return selectedActuCategory;
		else do {	
				selectedActuCategory = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_ACTUATOR_CATEGORY + " " + Strings.INSERT_SENSOR_CATEGORY);
		}while(!dataHandler.hasActuatorCategory(selectedActuCategory));
		return selectedActuCategory;
	}
	
	private String safeInsertRoom() {
		String selectedRoom = RawInputHandler.readNotVoidString(Strings.INSERT_ROOM);
		if(dataHandler.hasRoom(selectedRoom)) return selectedRoom;
		else do{
				selectedRoom = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_ROOM + " " + Strings.INSERT_ROOM);	
		}while(!dataHandler.hasRoom(selectedRoom));
		return selectedRoom;
	}

	private String safeInsertSensor() {
		String selectedSensor = RawInputHandler.readNotVoidString(Strings.INSERT_SENSOR);
		if(dataHandler.hasSensor(selectedSensor)) return selectedSensor;
		else do {
				selectedSensor = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_SENSOR+ " " + Strings.INSERT_SENSOR);
		}while(!dataHandler.hasSensor(selectedSensor));
		return selectedSensor;
	}
	
	private String safeInsertActuator() {
		String selectedActuator = RawInputHandler.readNotVoidString(Strings.INSERT_ACTUATOR);
		if(dataHandler.hasActuator(selectedActuator)) return selectedActuator;
		else do {		
				selectedActuator = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_ACTUATOR + " " + Strings.INSERT_ACTUATOR);
		}while(!dataHandler.hasActuator(selectedActuator));
		return selectedActuator;
	}
	
	private String safeInsertArtifact() {
		String selectedArtifact = RawInputHandler.readNotVoidString(Strings.INSERT_ARTIFACT);
		if(dataHandler.hasActuator(selectedArtifact)) return selectedArtifact;
		do {
				selectedArtifact = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_ARTIFACT + " " + Strings.INSERT_ARTIFACT);
		}while(!dataHandler.hasActuator(selectedArtifact));
		return selectedArtifact;
	}
}

