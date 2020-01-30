package it.unibs.ing.softwareengineering;

public class ImplementedMenu {
	
	public static final MyMenu menu = new MyMenu("Login", Strings.LOGIN_VOICES);
	public static final MyMenu userMenu = new MyMenu("Scegli cosa fare", Strings.USER_VOICES);
	public static final MyMenu roomMenu  = new MyMenu("Scegli un'opzione", Strings.ROOM_VOICES);
	public static final MyMenu maintainerMenu = new MyMenu("Scegli un'opzione", Strings.MAINTAINER_VOICES);
	public static final MyMenu maintainerRoomMenu = new MyMenu("Scegli un'opzione", Strings.MAINTAINER_ROOM_VOICES);
	
	
	
	public static void show() {
		String user;
		int scelta;
		do
		{
			scelta = ImplementedMenu.menu.scegli();
			switch(scelta)
			{
				case 1: 
					user = RawDataInput.readNotVoidString("Inserisci il nome utente");
					System.out.println("Benvenuto " + user);
					showUserMenu();
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
							ok = HomeMain.login.checkPassword(user, password);
							if(!ok)
								System.out.println("Nome utente o password errati");
						}
					}
					while (!user.equalsIgnoreCase("^") && !ok);
					if (ok)
						showMaintainerMenu();
					break;
			}
		}
		while (scelta != 0);
	}
	
	private static void showMaintainerMenu() {
		boolean exitFlag = false;
		do
		{
			int choice = ImplementedMenu.maintainerMenu.scegli();
			switch(choice) {
				case 0:
					exitFlag = true;
					break;
				case 1:
					DataOutput.printHousingUnit(HomeMain.home.toString());
					break;
				case 2:
					//CookedDataInput.changeHouseDescription(home);
					break;
				case 3:
					DataOutput.printListOfString(HomeMain.home.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedRoom = RawDataInput.readNotVoidString("Inserisci il nome della stanza su cui vuoi operare");
					showMaintainerRoomMenu(selectedRoom);
					break;
				case 4:
					//CookedDataInput.readRoomFromUser(home);
					break;
				case 5:
					DataOutput.printListOfString(HomeMain.sensorCategoryManager.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedSensCategory = RawDataInput.readNotVoidString("Inserisci la categoria di sensori che vuoi visualizzare");
					DataOutput.printSensorCategory(HomeMain.sensorCategoryManager.getElementByName(selectedSensCategory).toString());
					// Da valutare se castare o meno
					break;
				case 6:
					DataOutput.printListOfString(HomeMain.sensorCategoryManager.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedActuCategory = RawDataInput.readNotVoidString("Inserisci la categoria di attuatori che vuoi visualizzare");
					DataOutput.printSensorCategory(HomeMain.actuatorCategoryManager.getElementByName(selectedActuCategory).toString());
					// Da valutare se castare o meno
					break;
				case 7:
					//CookedDataInput.readSensorCategoryFromUser();
					break;
				case 8:
					//CookedDataInput.readActuatorCategoryFromUser();
					break;
			}
		}
		while(exitFlag != true);
	}

	private static void showUserMenu() {
		boolean exitFlag = false;
		do {
			int choice = ImplementedMenu.userMenu.scegli();
			switch(choice) {
				case 0:
					exitFlag = true;
					break;
				case 1:
					DataOutput.printHousingUnit(HomeMain.home.toString());
					break;
				case 2:
					DataOutput.printListOfString(HomeMain.home.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedRoom = RawDataInput.readNotVoidString("Inserisci il nome della stanza su cui vuoi operare");
					showRoomMenu(selectedRoom);
					break;
				case 3:
					DataOutput.printListOfString(HomeMain.sensorCategoryManager.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedSensCategory = RawDataInput.readNotVoidString("Inserisci la categoria di sensori che vuoi visualizzare");
					DataOutput.printSensorCategory(HomeMain.sensorCategoryManager.getElementByName(selectedSensCategory).toString());
					// Da valutare se castare o meno
					break;
				case 4:
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

	private static void showRoomMenu(String selectedRoom) {
		Room toWorkOn = (Room)HomeMain.home.getElementByName(selectedRoom);
		boolean exitFlag = false;
		do {
			int choice = ImplementedMenu.roomMenu.scegli();
			switch(choice) {
			case 0:
				exitFlag = true;
				break;
			case 1:
				DataOutput.printRoom(toWorkOn.toString());
				break;
			case 2:
				DataOutput.printListOfString(toWorkOn.getSensorsNames());
				System.out.println();
				System.out.println("---------DEVO FORMATTARE MEGLIO---------");
				System.out.println();
				
				String selectedSensor = RawDataInput.readNotVoidString("Inserisci il nome del sensore che vuoi visualizzare");
				DataOutput.printSensor(toWorkOn.getSensorByName(selectedSensor).toString());
				break;
			case 3:
				DataOutput.printListOfString(toWorkOn.getActuatorsNames());
				System.out.println();
				System.out.println("---------DEVO FORMATTARE MEGLIO---------");
				System.out.println();
				
				String selectedActuator = RawDataInput.readNotVoidString("Inserisci il nome dell'attuatore che vuoi visualizzare");
				DataOutput.printSensor(toWorkOn.getActuatorByName(selectedActuator).toString());
				break;
			case 4:
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
	
	private static void showMaintainerRoomMenu(String selectedRoom) {
		Room toWorkOn = (Room)HomeMain.home.getElementByName(selectedRoom);
		boolean exitFlag = false;
		do {
			int choice = ImplementedMenu.roomMenu.scegli();
			switch(choice) {
				case 0:
					exitFlag = true;
					break;
				case 1:
					DataOutput.printRoom(toWorkOn.toString());
					break;
				case 2:
					DataOutput.printListOfString(toWorkOn.getSensorsNames());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedSensor = RawDataInput.readNotVoidString("Inserisci il nome del sensore che vuoi visualizzare");
					DataOutput.printSensor(toWorkOn.getSensorByName(selectedSensor).toString());
					break;
				case 3:
					DataOutput.printListOfString(toWorkOn.getActuatorsNames());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedActuator = RawDataInput.readNotVoidString("Inserisci il nome dell'attuatore che vuoi visualizzare");
					DataOutput.printSensor(toWorkOn.getActuatorByName(selectedActuator).toString());
					break;
				case 4:
					DataOutput.printListOfString(toWorkOn.getArtifactsNames());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedArtifact = RawDataInput.readNotVoidString("Inserisci il nome dell'artefatto che vuoi visualizzare");
					DataOutput.printSensor(toWorkOn.getArtifactByName(selectedArtifact).toString());
					break;	
				case 5:
					//CookedDataInput.changeRoomDescription(toWorkOn);
					break;
				case 6:
					//CookedDataInput.readSensorFromUser(toWorkOn);
					break;
				case 7:
					//CookedDataInput.readActuatorFromUser(toWorkOn);
					break;
				case 8:
					//CookedDataInput.readArtifactFromUser(toWorkOn);
					break;
			}
		}
		while(exitFlag!=true);	
	}
		
	
}

