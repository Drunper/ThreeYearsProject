package it.unibs.ing.domohouse.util;

import it.unibs.ing.domohouse.HomeLogin;
import it.unibs.ing.domohouse.HomeMain;
import it.unibs.ing.domohouse.components.HousingUnit;
import it.unibs.ing.domohouse.components.Room;

public class ImplementedMenu {
	
	private static final MyMenu menu = new MyMenu("Login", Strings.LOGIN_VOICES);
	private static final MyMenu userMenu = new MyMenu("Scegli cosa fare", Strings.USER_VOICES);
	private static final MyMenu roomMenu  = new MyMenu("Scegli un'opzione", Strings.ROOM_VOICES);
	private static final MyMenu maintainerMenu = new MyMenu("Scegli un'opzione", Strings.MAINTAINER_VOICES);
	private static final MyMenu maintainerRoomMenu = new MyMenu("Scegli un'opzione", Strings.MAINTAINER_ROOM_VOICES);
	private static CookedDataInput inputHandler = new CookedDataInput();
	
	
	public static void show(HousingUnit home, HomeLogin login) {
		DataOutput.clearOutput();
		String user;
		int scelta;
		do
		{
			scelta = menu.scegli();
			switch(scelta)
			{
				case 1: 
					user = RawDataInput.readNotVoidString("Inserisci il nome utente");
					System.out.println("Benvenuto " + user);
					showUserMenu(home);
					break;
					
				case 2:
					String password;
					boolean ok;
					do
					{
						ok = false;
						user = RawDataInput.readNotVoidString("Inserisci il nome utente (^ per tornare indietro)");
						if(!user.equalsIgnoreCase("^"))
						{	
							password = RawDataInput.readNotVoidString("Inserisci la password");
							ok = login.checkPassword(user, password);
							if(!ok)
								System.out.println("Nome utente o password errati");
						}
					}
					while (!user.equalsIgnoreCase("^") && !ok);
					if (ok)
						showMaintainerMenu(home);
					break;
			}
		}
		while (scelta != 0);
	}
	
	private static void showMaintainerMenu(HousingUnit home) {
		DataOutput.clearOutput();
		boolean exitFlag = false;
		do
		{
			int choice = ImplementedMenu.maintainerMenu.scegli();
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
					DataOutput.printListOfString(home.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedRoom = RawDataInput.readNotVoidString("Inserisci il nome della stanza su cui vuoi operare");
					showMaintainerRoomMenu(selectedRoom, home);
					break;
				case 4:
					inputHandler.readRoomFromUser(home);
					break;
				case 5:
					DataOutput.clearOutput();
					DataOutput.printListOfString(HomeMain.sensorCategoryManager.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedSensCategory = RawDataInput.readNotVoidString("Inserisci la categoria di sensori che vuoi visualizzare");
					DataOutput.printSensorCategory(HomeMain.sensorCategoryManager.getElementByName(selectedSensCategory).toString());
					// Da valutare se castare o meno
					break;
				case 6:
					DataOutput.clearOutput();
					DataOutput.printListOfString(HomeMain.sensorCategoryManager.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedActuCategory = RawDataInput.readNotVoidString("Inserisci la categoria di attuatori che vuoi visualizzare");
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

	private static void showUserMenu(HousingUnit home) {
		DataOutput.clearOutput();
		boolean exitFlag = false;
		do {
			int choice = userMenu.scegli();
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
					DataOutput.printListOfString(home.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedRoom = RawDataInput.readNotVoidString("Inserisci il nome della stanza su cui vuoi operare");
					showRoomMenu(selectedRoom, home);
					break;
				case 3:
					DataOutput.clearOutput();
					DataOutput.printListOfString(HomeMain.sensorCategoryManager.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedSensCategory = RawDataInput.readNotVoidString("Inserisci la categoria di sensori che vuoi visualizzare");
					DataOutput.printSensorCategory(HomeMain.sensorCategoryManager.getElementByName(selectedSensCategory).toString());
					// Da valutare se castare o meno
					break;
				case 4:
					DataOutput.clearOutput();
					DataOutput.printListOfString(HomeMain.sensorCategoryManager.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedActuCategory = RawDataInput.readNotVoidString("Inserisci la categoria di attuatori che vuoi visualizzare");
					DataOutput.printSensorCategory(HomeMain.actuatorCategoryManager.getElementByName(selectedActuCategory).toString());
					// Da valutare se castare o meno
					break;
			}
		}
		while(exitFlag!=true);
	}

	private static void showRoomMenu(String selectedRoom, HousingUnit home) {
		DataOutput.clearOutput();
		Room toWorkOn = (Room)home.getElementByName(selectedRoom);
		boolean exitFlag = false;
		do {
			int choice = roomMenu.scegli();
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
				System.out.println("---------DEVO FORMATTARE MEGLIO---------");
				System.out.println();
				
				String selectedSensor = RawDataInput.readNotVoidString("Inserisci il nome del sensore che vuoi visualizzare");
				DataOutput.printSensor(toWorkOn.getSensorByName(selectedSensor).toString());
				break;
			case 3:
				DataOutput.clearOutput();
				DataOutput.printListOfString(toWorkOn.getActuatorsNames());
				System.out.println();
				System.out.println("---------DEVO FORMATTARE MEGLIO---------");
				System.out.println();
				
				String selectedActuator = RawDataInput.readNotVoidString("Inserisci il nome dell'attuatore che vuoi visualizzare");
				DataOutput.printSensor(toWorkOn.getActuatorByName(selectedActuator).toString());
				break;
			case 4:
				DataOutput.clearOutput();
				DataOutput.printListOfString(toWorkOn.getArtifactsNames());
				System.out.println();
				System.out.println("---------DEVO FORMATTARE MEGLIO---------");
				System.out.println();
				
				String selectedArtifact = RawDataInput.readNotVoidString("Inserisci il nome dell'artefatto che vuoi visualizzare");
				DataOutput.printSensor(toWorkOn.getArtifactByName(selectedArtifact).toString());
				break;	
			}
		}
		while(exitFlag!=true);		
	}
	
	private static void showMaintainerRoomMenu(String selectedRoom, HousingUnit home) {
		DataOutput.clearOutput();
		Room toWorkOn = (Room)home.getElementByName(selectedRoom);
		boolean exitFlag = false;
		do {
			int choice = roomMenu.scegli();
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
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedSensor = RawDataInput.readNotVoidString("Inserisci il nome del sensore che vuoi visualizzare");
					DataOutput.printSensor(toWorkOn.getSensorByName(selectedSensor).toString());
					break;
				case 3:
					DataOutput.clearOutput();
					DataOutput.printListOfString(toWorkOn.getActuatorsNames());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedActuator = RawDataInput.readNotVoidString("Inserisci il nome dell'attuatore che vuoi visualizzare");
					DataOutput.printSensor(toWorkOn.getActuatorByName(selectedActuator).toString());
					break;
				case 4:
					DataOutput.clearOutput();
					DataOutput.printListOfString(toWorkOn.getArtifactsNames());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedArtifact = RawDataInput.readNotVoidString("Inserisci il nome dell'artefatto che vuoi visualizzare");
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

