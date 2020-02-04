package it.unibs.ing.domohouse.util;

import it.unibs.ing.domohouse.HomeLogin;
import it.unibs.ing.domohouse.HomeMain;
import it.unibs.ing.domohouse.components.HousingUnit;
import it.unibs.ing.domohouse.components.Room;

public class ImplementedMenu {
	
	private HomeLogin login;
	private InputHandler inputHandler;

	private final MyMenu menu = new MyMenu(Strings.LOGIN_MENU_TITLE, Strings.LOGIN_VOICES);
	private final MyMenu userMenu = new MyMenu(Strings.USER_MENU_TITLE, Strings.USER_VOICES);
	private final MyMenu roomMenu  = new MyMenu(Strings.USER_ROOM_MENU_TITLE, Strings.ROOM_VOICES);
	private final MyMenu maintainerMenu = new MyMenu(Strings.MAINTAINER_MENU_TITLE, Strings.MAINTAINER_VOICES);
	private final MyMenu maintainerRoomMenu = new MyMenu(Strings.MAINTAINER_ROOM_MENU_TITLE, Strings.MAINTAINER_ROOM_VOICES);	
	
	public ImplementedMenu() {
		login = new HomeLogin();
		inputHandler = new InputHandler();
		login.addEntry(Strings.MAINTAINER_USER, Strings.PASSWORD); 
	}
	
	public void show(HousingUnit home) {
		DataOutput.clearOutput();
		String user;
		int scelta;
		do
		{
			scelta = menu.select();
			switch(scelta)
			{
				case 1: 
					user = RawDataInput.readNotVoidString(Strings.INSERT_USER);
					if(!user.equalsIgnoreCase(Strings.BACK_CHARACTER))
					{
						System.out.println(Strings.WELCOME + user);
						showUserMenu(home);
					}
					break;
					
				case 2:
					String password;
					boolean ok;
					do
					{
						ok = false;
						user = RawDataInput.readNotVoidString(Strings.INSERT_USER);
						if(!user.equalsIgnoreCase(Strings.BACK_CHARACTER))
						{	
							password = RawDataInput.readNotVoidString(Strings.INSERT_PASSWORD);
							ok = login.checkPassword(user, password);
							if(!ok)
								System.out.println(Strings.USER_OR_PASSWORD_ERROR);
						}
					}
					while (!user.equalsIgnoreCase(Strings.BACK_CHARACTER) && !ok);
					if (ok)
						showMaintainerMenu(home);
					break;
			}
		}
		while (scelta != 0);
	}
	
	private void showMaintainerMenu(HousingUnit home) {
		DataOutput.clearOutput();
		boolean exitFlag = false;
		do
		{
			int choice = maintainerMenu.select();
			switch(choice) {
				case 0:
					exitFlag = true;
					break;
				case 1:
					DataOutput.clearOutput();
					DataOutput.printHousingUnit(home.toString());
					break;
				case 2:
					inputHandler.changeHouseDescription(home);
					break;
				case 3:
					DataOutput.clearOutput();
					DataOutput.printListOfString(home.roomList());
					System.out.println();
					System.out.println();
					
					String selectedRoom = RawDataInput.readNotVoidString(Strings.INSERT_ROOM);
					showMaintainerRoomMenu(selectedRoom, home);
					break;
				case 4:
					inputHandler.readRoomFromUser(home);
					break;
				case 5:
					DataOutput.clearOutput();
					DataOutput.printListOfString(HomeMain.sensorCategoryManager.namesList());
					System.out.println();
					System.out.println();
					
					String selectedSensCategory = RawDataInput.readNotVoidString(Strings.INSERT_SENSOR_CATEGORY);
					DataOutput.printSensorCategory(HomeMain.sensorCategoryManager.getElementByName(selectedSensCategory).toString());
					// Da valutare se castare o meno
					break;
				case 6:
					DataOutput.clearOutput();
					DataOutput.printListOfString(HomeMain.sensorCategoryManager.namesList());
					System.out.println();
					System.out.println();
					
					String selectedActuCategory = RawDataInput.readNotVoidString(Strings.INSERT_ACTUATOR_CATEGORY);
					DataOutput.printSensorCategory(HomeMain.actuatorCategoryManager.getElementByName(selectedActuCategory).toString());
					// Da valutare se castare o meno
					break;
				case 7:
					inputHandler.readSensorCategoryFromUser();
					break;
				case 8:
					inputHandler.readActuatorCategoryFromUser();
					break;
			}
		}
		while(exitFlag != true);
	}

	private void showUserMenu(HousingUnit home) {
		DataOutput.clearOutput();
		boolean exitFlag = false;
		do {
			int choice = userMenu.select();
			switch(choice) {
				case 0:
					exitFlag = true;
					break;
				case 1:
					DataOutput.clearOutput();
					DataOutput.printHousingUnit(home.toString());
					break;
				case 2:
					DataOutput.clearOutput();
					DataOutput.printListOfString(home.roomList());
					System.out.println();
					System.out.println();
					
					String selectedRoom = RawDataInput.readNotVoidString(Strings.INSERT_ROOM);
					showUserRoomMenu(selectedRoom, home);
					break;
				case 3:
					DataOutput.clearOutput();
					DataOutput.printListOfString(HomeMain.sensorCategoryManager.namesList());
					System.out.println();
					System.out.println();
					
					String selectedSensCategory = RawDataInput.readNotVoidString(Strings.INSERT_SENSOR_CATEGORY);
					DataOutput.printSensorCategory(HomeMain.sensorCategoryManager.getElementByName(selectedSensCategory).toString());
					// Da valutare se castare o meno
					break;
				case 4:
					DataOutput.clearOutput();
					DataOutput.printListOfString(HomeMain.sensorCategoryManager.namesList());
					System.out.println();
					System.out.println();
					
					String selectedActuCategory = RawDataInput.readNotVoidString(Strings.INSERT_ACTUATOR_CATEGORY);
					DataOutput.printSensorCategory(HomeMain.actuatorCategoryManager.getElementByName(selectedActuCategory).toString());
					// Da valutare se castare o meno
					break;
			}
		}
		while(exitFlag!=true);
	}

	private void showUserRoomMenu(String selectedRoom, HousingUnit home) {
		DataOutput.clearOutput();
		Room toWorkOn = home.getRoomByName(selectedRoom);
		boolean exitFlag = false;
		do {
			int choice = roomMenu.select();
			switch(choice) {
			case 0:
				exitFlag = true;
				break;
			case 1:
				DataOutput.clearOutput();
				DataOutput.printRoom(toWorkOn.toString());
				break;
			case 2:
				DataOutput.clearOutput();
				DataOutput.printListOfString(toWorkOn.getSensorsNames());
				System.out.println();
				System.out.println();
				
				String selectedSensor = RawDataInput.readNotVoidString(Strings.INSERT_SENSOR);
				DataOutput.printSensor(toWorkOn.getSensorByName(selectedSensor).toString());
				break;
			case 3:
				DataOutput.clearOutput();
				DataOutput.printListOfString(toWorkOn.getActuatorsNames());
				System.out.println();
				System.out.println();
				
				String selectedActuator = RawDataInput.readNotVoidString(Strings.INSERT_ACTUATOR);
				DataOutput.printSensor(toWorkOn.getActuatorByName(selectedActuator).toString());
				break;
			case 4:
				DataOutput.clearOutput();
				DataOutput.printListOfString(toWorkOn.getArtifactsNames());
				System.out.println();
				System.out.println();
				
				String selectedArtifact = RawDataInput.readNotVoidString(Strings.INSERT_ARTIFACT);
				DataOutput.printSensor(toWorkOn.getArtifactByName(selectedArtifact).toString());
				break;	
			}
		}
		while(exitFlag!=true);		
	}
	
	private void showMaintainerRoomMenu(String selectedRoom, HousingUnit home) {
		DataOutput.clearOutput();
		Room toWorkOn = home.getRoomByName(selectedRoom);
		boolean exitFlag = false;
		do {
			int choice = maintainerRoomMenu.select();
			switch(choice) {
				case 0:
					exitFlag = true;
					break;
				case 1:
					DataOutput.clearOutput();
					DataOutput.printRoom(toWorkOn.toString());
					break;
				case 2:
					DataOutput.clearOutput();
					DataOutput.printListOfString(toWorkOn.getSensorsNames());
					System.out.println();
					System.out.println();
					
					String selectedSensor = RawDataInput.readNotVoidString(Strings.INSERT_SENSOR);
					DataOutput.printSensor(toWorkOn.getSensorByName(selectedSensor).toString());
					break;
				case 3:
					DataOutput.clearOutput();
					DataOutput.printListOfString(toWorkOn.getActuatorsNames());
					System.out.println();
					System.out.println();
					
					String selectedActuator = RawDataInput.readNotVoidString(Strings.INSERT_ACTUATOR);
					DataOutput.printSensor(toWorkOn.getActuatorByName(selectedActuator).toString());
					break;
				case 4:
					DataOutput.clearOutput();
					DataOutput.printListOfString(toWorkOn.getArtifactsNames());
					System.out.println();
					System.out.println();
					
					String selectedArtifact = RawDataInput.readNotVoidString(Strings.INSERT_ARTIFACT);
					DataOutput.printSensor(toWorkOn.getArtifactByName(selectedArtifact).toString());
					break;	
				case 5:
					inputHandler.changeRoomDescription(toWorkOn);
					break;
				case 6:
					inputHandler.readNumericSensorFromUser(toWorkOn);
					break;
				case 7:
					inputHandler.readActuatorFromUser(toWorkOn);
					break;
				case 8:
					inputHandler.readArtifactFromUser(toWorkOn);
					break;
			}
		}
		while(exitFlag!=true);	
	}	
}

