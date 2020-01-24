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
	private static String[] singleSensorMenuChoices;
	private static String[] singleActuatorMenuChoices;
	private static String[] artifactEditMenuChoices;
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
	private static MyMenu sensorCategoryMenu;
	private static MyMenu actuatorCategoryMenu;
	
	private static MyMenu singleSensorMenu;
	private static MyMenu singleActuatorMenu;
	private static MyMenu artifactEditMenu;
	
	
	//test categories
	private static ArrayList<SensorCategory> sc = new ArrayList<>();
	private static ArrayList<ActuatorCategory> ac = new ArrayList<>();
	

	
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
		NumericActuator interruttore = new NumericActuator("i1_interruttore", "accende la luce", interruttori, lampadario);
		NumericSensor termometro = new NumericSensor("t1_temperatura", "il termometro del soggiorno", temperatura, soggiorno);
		sensorListSoggiorno.add(termometro);
		actuatorListSoggiorno.add(interruttore);
		ArrayList<Room> testRooms = new ArrayList<>();
		testRooms.add(soggiorno);
		
		home = new HousingUnit("casetta", "una casetta", testRooms);
		
		//CATEGORY STRUCTURES (from files after this)
		
		sc.add(temperatura);
		
		ac.add(interruttori);
		
		
		//END OF REMOVING ZONE
		
		refreshMenu();//GO TO LINE 501
				
		do {
			int choice = loginMenu.scegli();
			switch(choice) {
				case 1:
					System.out.println(Strings.INSERT_USER_NAME);
					HomeLogin user = new HomeLogin(s.nextLine());
					System.out.println(Strings.WELCOME + user.getUserName());
					showUserMenu();
					break;
				case 2:
					System.out.println(Strings.INSERT_USER_NAME);
					String name = s.nextLine();
					System.out.println(Strings.INSERT_MANPASS);
					String pass = s.nextLine();
					HomeLogin maintainer = new HomeLogin(name, pass);
					System.out.println(Strings.WELCOME + maintainer.getUserName());
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
		refreshMenu();
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
				showActuatorEditMenu();
				break;
			case 5:
				showArtifactEditMenu();
				break;
			}
		}
		while(exitFlag!=true);
	}
	
	private static void showHouseEditMenu() {
		refreshMenu();
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
			refreshMenu();
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
			refreshMenu();
			int choice = elementCategoryEditMenu.scegli();
			switch(choice) {
			case 0:
				exitFlag=true;
				break;
			case 1:
				showSensorCategoryMenu();
				break;
			case 2:
				showSingleSensorMenu();
				break;
			}
		}
		while(exitFlag!=true);
	}
	
	private static void showSingleSensorMenu() {
		boolean exitFlag = false;
		do {
			refreshMenu();
			int choice = singleSensorMenu.scegli();
			if(choice==0) {
				exitFlag = true;
			}else if(choice <= home.getAllSensorListSize()) {
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
						home.getSensorFromHomeSensorList(choice-1).setName(newName);
						System.out.println("OK");
						break;
					case 2:
						System.out.println(Strings.INSERT_DESCR);
						String newDescr = s.nextLine();
						home.getSensorFromHomeSensorList(choice-1).setDescr(newDescr);
						System.out.println("OK");
						break;
					}
				}
				while(exitSubFlag!=true);
			}
				
			}while(exitFlag!=true);		
	}
	
	private static void showSingleActuatorMenu() {
		boolean exitFlag = false;
		do {
			refreshMenu();
			int choice = singleActuatorMenu.scegli();
			if(choice==0) {
				exitFlag = true;
			}else if(choice <= home.getAllActuatorListSize()) {
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
						home.getActuatorFromHomeActuatorList(choice-1).setName(newName);
						System.out.println("OK");
						break;
					case 2:
						System.out.println(Strings.INSERT_DESCR);
						String newDescr = s.nextLine();
						home.getActuatorFromHomeActuatorList(choice-1).setDescr(newDescr);
						System.out.println("OK");
						break;
					}
				}
				while(exitSubFlag!=true);
			}
				
			}while(exitFlag!=true);		
	}

	private static void showActuatorEditMenu() {
		boolean exitFlag = false;
		do {
			refreshMenu();
			int choice = elementCategoryEditMenu.scegli();
			switch(choice) {
			case 0:
				exitFlag=true;
				break;
			case 1:
				showActuatorCategoryMenu();
				break;
			case 2:
				showSingleActuatorMenu();
				break;
			}
		}
		while(exitFlag!=true);
	}
	
	private static void showSensorCategoryMenu() {
		boolean exitFlag = false;
		do {
			refreshMenu();
			int choice = sensorCategoryMenu.scegli();
			if(choice==0) exitFlag=true;
			else if (choice <= sc.size()) {
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
						sc.get(choice-1).setName(newName);
						System.out.println("OK");
						break;
					case 2:
						System.out.println(Strings.INSERT_DESCR);
						String newDescr = s.nextLine();
						sc.get(choice-1).setDescr(newDescr);
						System.out.println("OK");
						break;
					}
				}
				while(exitSubFlag!=true);
			}
		}
		while(exitFlag!=true);
	}
	
	private static void showActuatorCategoryMenu() {
		boolean exitFlag = false;
		do {
			refreshMenu();
			int choice = actuatorCategoryMenu.scegli();
			if(choice==0) exitFlag=true;
			else if (choice <= ac.size()) {
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
						ac.get(choice-1).setName(newName);
						System.out.println("OK");
						break;
					case 2:
						System.out.println(Strings.INSERT_DESCR);
						String newDescr = s.nextLine();
						ac.get(choice-1).setDescr(newDescr);
						System.out.println("OK");
						break;
					}
				}
				while(exitSubFlag!=true);
			}
		}
		while(exitFlag!=true);
	}
	
	private static void showArtifactEditMenu() {
		boolean exitFlag = false;
		do {
			refreshMenu();
			int choice = artifactEditMenu.scegli();
			if(choice == 0) {
				exitFlag = true;
			}else if(choice <= home.getAllArtifactListSize()) {
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
						home.getArtifactFromHomeArtifactList(choice-1).setName(newName);
						System.out.println("OK");
						break;
					case 2:
						System.out.println(Strings.INSERT_DESCR);
						String newDescr = s.nextLine();
						home.getArtifactFromHomeArtifactList(choice-1).setDescr(newDescr);
						System.out.println("OK");
						break;
					}
				}while(exitSubFlag!=true);
			}	
		}while(exitFlag!=true);
	}
	
	/*
	 * Questo metodo si occupa di modificare runtime le voci dei menu in caso di modifica
	 * da parte del manutentore.
	 * L'errore era dovuto al fatto che non c'è un collegamento diretto tra i menu e i vari elementi della casa (stanze, sensori..)
	 * Forse non la soluzione migliore ma per ora la più efficiente!
	 * Good Job Patrick! 
	 */
	private static void refreshMenu() {
		roomChoices = home.getRoomNames();
		singleSensorMenuChoices = home.getSensorNames();
		singleActuatorMenuChoices = home.getActuatorNames();
		artifactEditMenuChoices = home.getArtifactNames();
		roomMenu = new MyMenu(Strings.ROOM_MENU, roomChoices);
		roomEditMenu = new MyMenu(Strings.ROOM_EDIT_MENU, roomChoices);
		sensorCategoryMenu = new MyMenu(Strings.EDIT_CATEGORY, getSensorCategoryNames());
		actuatorCategoryMenu = new MyMenu(Strings.EDIT_CATEGORY, getActuatorCategoryNames());
		singleSensorMenu = new MyMenu(Strings.SENSOR_EDIT, singleSensorMenuChoices);
		singleSensorMenu = new MyMenu(Strings.ACTUATOR_EDIT, singleSensorMenuChoices);
		artifactEditMenu = new MyMenu(Strings.ARTIFACT_EDIT, artifactEditMenuChoices);
	}
	
	private static String[] getSensorCategoryNames() {
		String[] categoryNames = new String[sc.size()];
		for(int i=0; i<sc.size();i++) {
			categoryNames[i] = sc.get(i).getName();
		}
		return categoryNames;
	}
	
	private static String[] getActuatorCategoryNames() {
		String[] categoryNames = new String[ac.size()];
		for(int i=0; i<ac.size();i++) {
			categoryNames[i] = ac.get(i).getName();
		}
		return categoryNames;
	}
}

