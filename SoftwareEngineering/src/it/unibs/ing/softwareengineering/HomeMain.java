package it.unibs.ing.softwareengineering;

import java.util.*;

public class HomeMain {
	
	private static String[] loginChoices = {Strings.USER, Strings.MAINTAINER};
	private static String[] userChoices = {Strings.HOUSE_VIEW};
	private static Scanner s = new Scanner(System.in);
	

	
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
		HousingUnit casetta = new HousingUnit("casetta", "una casetta", testRooms);
		NumericActuator interruttore = new NumericActuator("i1_interruttore", "accende la luce", interruttori, lampadario);
		NumericSensor termometro = new NumericSensor("t1_temperatura", "il termometro del soggiorno", temperatura, soggiorno);
		sensorListSoggiorno.add(termometro);
		actuatorListSoggiorno.add(interruttore);
		//END OF REMOVING ZONE
		
		
		MyMenu loginMenu = new MyMenu(Strings.LOGINTITLE, loginChoices);
		MyMenu userMenu = new MyMenu(Strings.USER_MENU, userChoices);
		
		do {
			int choice = loginMenu.scegli();
			switch(choice) {
				case 1:
					System.out.println(Strings.INSERT_NAME);
					HomeLogin user = new HomeLogin(s.nextLine());
					System.out.println("OK");
					showUserMenu(userMenu);
					break;
				case 2:
					System.out.println(Strings.INSERT_NAME);
					System.out.println(Strings.INSERT_MANPASS);
					HomeLogin maintainer = new HomeLogin(s.nextLine(), s.nextLine());
					System.out.println("OK");
					break;
				}
		}
		while(true);	
		
	}
	
	private static void showUserMenu(MyMenu userMenu) {
		boolean exitFlag = false;
		do {
			int choice = userMenu.scegli();
			switch(choice) {
			case 0:
				exitFlag = true;
			}
		}
		while(exitFlag!=true);
	}
}
