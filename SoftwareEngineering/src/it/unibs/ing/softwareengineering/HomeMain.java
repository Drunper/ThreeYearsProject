package it.unibs.ing.softwareengineering;

import java.util.*;

public class HomeMain {
	
	private static String[] loginChoices = {Strings.USER, Strings.MAINTAINER};
	private static String[] userChoices = {Strings.HOUSE_VIEW};
	private static String[] maintainerChoices = {Strings.USER_SEC, Strings.HOUSE_EDIT};
	private static String[] roomChoices;
	private static String[] elementChoices = {Strings.SENSOR, Strings.ACTUATOR, Strings.ARTIFACT};
	private static String[] sensorChoices;
	private static String[] actuatorChoices;
	private static String[] artifactChoices;
	private static String[] editChoices = {Strings.HOUSE_EDIT_NAMEDESCR, Strings.ROOM_EDIT, Strings.SENSOR_EDIT, 
			Strings.ACTUATOR_EDIT, Strings.ARTIFACT_EDIT};
	private static String[] nameDescrChoices = {Strings.EDIT_NAME, Strings.EDIT_DESCR};
	private static String[] categoryElementChoices = {Strings.EDIT_ELEMENT_CATEGORY, Strings.EDIT_SINGLE_ELEMENT};
	private static Scanner s = new Scanner(System.in);
	private static HousingUnit home;
	private static MyMenu loginMenu = new MyMenu(Strings.LOGINTITLE, loginChoices);
	private static MyMenu userMenu = new MyMenu(Strings.USER_MENU, userChoices);
	private static MyMenu maintainerMenu = new MyMenu(Strings.MAINTAINER_MENU, maintainerChoices);
	private static MyMenu roomMenu;
	private static MyMenu roomElementMenu = new MyMenu(Strings.ELEMENT_CHOICE, elementChoices);
	private static MyMenu sensorMenu;
	private static MyMenu actuatorMenu;
	private static MyMenu artifactMenu;
	private static MyMenu editMenu = new MyMenu(Strings.EDIT_MENU, editChoices);
	private static MyMenu roomEditMenu;
	private static MyMenu nameDescrMenu = new MyMenu(Strings.EDIT_NAME_DESCR_MENU, nameDescrChoices);
	private static MyMenu elementCategoryEditMenu = new MyMenu(Strings.EDIT_ELEMENT_MENU, categoryElementChoices);
	

	
	//THE BIG MAIN (it's fu***ng huge)
	public static void main(String[] args) {
		//REMOVING ZONE
		
		//TEST OBJECTS (will be deleted and loaded from files)
		SensorCategory temperatura = new SensorCategory("sensori di temperatura", "misurano la temperatura", null);
		ActuatorCategory interruttori = new ActuatorCategory("interruttori di accensione", "accendono qualcosa");
		ArrayList<Sensor> sensorListSoggiorno = new ArrayList<>();
		ArrayList<Actuator> actuatorListSoggiorno = new ArrayList<>();
		Artifact lampadario = new Artifact("lampadario", "un bel lampa-dario");
		ArrayList<Artifact> artiList = new ArrayList<>();
		artiList.add(lampadario);
		Room soggiorno = new Room("soggiorno", "il salotto", sensorListSoggiorno, actuatorListSoggiorno, artiList);
		ArrayList<Room> testRooms = new ArrayList<>();
		testRooms.add(soggiorno);
		home = new HousingUnit("casetta", "una casetta", testRooms);
		NumericActuator interruttore = new NumericActuator("i1_interruttore", "accende la luce", interruttori, lampadario);
		NumericSensor termometro = new NumericSensor("t1_temperatura", "il termometro del soggiorno", temperatura, soggiorno);
		sensorListSoggiorno.add(termometro);
		actuatorListSoggiorno.add(interruttore);
		//END OF REMOVING ZONE
		
		
		
		roomChoices = home.getRoomNames();
		roomMenu = new MyMenu(Strings.ROOM_MENU, roomChoices);
		roomEditMenu = new MyMenu(Strings.ROOM_EDIT_MENU, roomChoices);
		
		do {
			int choice = loginMenu.scegli();
			switch(choice) {
				case 1:
					System.out.println(Strings.INSERT_USER_NAME);
					HomeLogin user = new HomeLogin(s.nextLine());
					System.out.println("OK");
					showUserMenu();
					break;
				case 2:
					System.out.println(Strings.INSERT_USER_NAME);
					System.out.println(Strings.INSERT_MANPASS);
					HomeLogin maintainer = new HomeLogin(s.nextLine(), s.nextLine());
					System.out.println("OK");
					showMaintainerMenu();
					break;
				}
		}
		while(true);		
	}
	
	private static void showUserMenu() {
		boolean exitFlag = false;
		do {
			int choice = userMenu.scegli();
			switch(choice) {
			case 0:
				exitFlag = true;
				break;
			case 1:
				System.out.println(home.getName() + ": " + home.getDescr());
				showRoomMenu();
			}
		}
		while(exitFlag!=true);
	}
	
	private static void showMaintainerMenu() {
		boolean exitFlag = false;
		do {
			int choice = maintainerMenu.scegli();
			switch(choice) {
			case 0:
				exitFlag = true;
				break;
			case 1:
				showUserMenu();
				break;
			case 2:
				showEditMenu();
				break;
			}
		}
		while(exitFlag!=true);
	}
	
	private static void showRoomMenu() {
		boolean exitFlag = false;
		do {
			int choice = roomMenu.scegli();
			if(choice == 0)
				exitFlag= true;
			else if (choice <= home.getRoomListSize()){
				System.out.println(Strings.CHOOSE);
				Room selected = home.getRoomFromIndex(choice-1);
				System.out.println(selected.getRoomName() + ": " + selected.getDescr());
				boolean exitSubFlag = false;
				do {
					int subChoice = roomElementMenu.scegli();
					switch(subChoice) {
					case 0:
						exitSubFlag = true;
						break;
					case 1:
						sensorChoices = selected.getSensorNames();
						sensorMenu = new MyMenu(Strings.SENSOR_MENU, sensorChoices);
						int sensorChoice = sensorMenu.scegli();
						if(sensorChoice == 0) break;
						else if (sensorChoice <= selected.getSensorListSize()){
							System.out.println(Strings.CHOOSE);
							Sensor selectedSensor = selected.getSensorFromIndex(choice-1);
							System.out.println(selectedSensor.getCategory().getName() + ": " + selectedSensor.getCategory().getDescr());
							System.out.println(selectedSensor.getName() + ": " + selectedSensor.getDescr());
						}
						break;
					case 2:
						actuatorChoices = selected.getActuatorNames();
						actuatorMenu = new MyMenu(Strings.ACTUATOR_MENU, actuatorChoices);
						int actuatorChoice = actuatorMenu.scegli();
						if(actuatorChoice == 0) break;
						else if (actuatorChoice <= selected.getActuatorListSize()){
							System.out.println(Strings.CHOOSE);
							Actuator selectedActuator = selected.getActuatorFromIndex(choice-1);
							System.out.println(selectedActuator.getCategory().getName() + ": " + selectedActuator.getCategory().getDescr());
							System.out.println(selectedActuator.getName() + ": " + selectedActuator.getDescr());
						}
						break;
					case 3:
						artifactChoices = selected.getArtifactNames();
						artifactMenu = new MyMenu(Strings.ARTIFACT_MENU, artifactChoices);
						int artifactChoice = artifactMenu.scegli();
						if(artifactChoice == 0) break;
						else if (artifactChoice <= selected.getArtifactListSize()){
							System.out.println(Strings.CHOOSE);
							Artifact selectedArtifact = selected.getArtifactFromIndex(choice-1);
							System.out.println(selectedArtifact.getName() + ": " + selectedArtifact.getDescr());
						}
						break;
					}
				}
				while(exitSubFlag!=true);
			}
		}
		while(exitFlag!=true);
		}
	
	private static void showEditMenu() {
		boolean exitFlag = false;
		do {
			int choice = editMenu.scegli();
			switch(choice) {
			case 0:
				exitFlag = true;
				break;
			case 1:
				showHouseEditMenu();
				break;
			case 2:
				showRoomEditMenu();
				break;
			case 3:
				showSensorEditMenu();
				break;
			case 4:
				break;
			case 5:
				break;
			}
		}
		while(exitFlag!=true);
	}
	
	private static void showHouseEditMenu() {
		boolean exitSubFlag = false;
		do {
			int subChoice = nameDescrMenu.scegli();
			switch(subChoice) {
			case 0:
				exitSubFlag = true;
				break;
			case 1:
				System.out.println(Strings.INSERT_NAME);
				String newName = s.nextLine();
				home.setName(newName);
				break;
			case 2:
				System.out.println(Strings.INSERT_DESCR);
				String newDescr = s.nextLine();
				home.setDescr(newDescr);
				break;
			}
		}
		while(exitSubFlag!=true);
	}
	
	private static void showRoomEditMenu() {
		boolean exitFlag = false;
		do {
			int choice = roomEditMenu.scegli();
			if(choice == 0)
				exitFlag= true;
			else if(choice <= home.getRoomListSize()){
				System.out.println(Strings.CHOOSE);
				Room selected = home.getRoomFromIndex(choice-1);
				boolean exitSubFlag = false;
				do {
					int subChoice = nameDescrMenu.scegli();
					switch(subChoice) {
					case 0:
						exitSubFlag = true;
						break;
					case 1:
						System.out.println(Strings.INSERT_NAME);
						String newName = s.nextLine();
						selected.setName(newName);
						break;
					case 2:
						System.out.println(Strings.INSERT_DESCR);
						String newDescr = s.nextLine();
						selected.setDescr(newDescr);
						break;
					}
				}
				while(exitSubFlag!=true);
				
			}	
		}
		while(exitFlag!=true);
	}
	
	private static void showSensorEditMenu() {
		boolean exitFlag = false;
		do {
			int choice = elementCategoryEditMenu.scegli();
			switch(choice) {
			case 0:
				exitFlag=true;
				break;
			case 1:
				break;
			case 2:
				break;
			}
		}
		while(exitFlag!=true);
	}
}
