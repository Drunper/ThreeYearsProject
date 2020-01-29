package it.unibs.ing.softwareengineering;

public class HomeMain {

	private static MyMenu menu;
	private static MyMenu userMenu;
	private static MyMenu roomMenu;
	private static HousingUnit home;
	private static Manager sensorCategoryManager;
	private static Manager actuatorCategoryManager;
	
	public static void main(String[] args) {

		//work in progress
		sensorCategoryManager = new Manager();
		actuatorCategoryManager = new Manager();
		SensorCategory temperatura = new SensorCategory("sensori di temperatura", "misurano la temperatura");
		ActuatorCategory interruttori = new ActuatorCategory("interruttori di accensione", "accendono qualcosa");
		home = new HousingUnit("Casa di Ivan", "Boh");
		Room soggiorno = new Room("Soggiorno", "Dove le persone si drogano");
		home.addEntry(soggiorno);
		Artifact lampadario = new Artifact("lampadarioMurano", "Sono un lampa-dario");
		Artifact portaOmbrelli = new Artifact("portaOmbrelli", "servo solo per far numero e non ho molto senso");
		Sensor temperino = new Sensor("t1_temperatura", temperatura);
		Sensor temperino2 = new Sensor("t2_temperatura", temperatura);
		Actuator attuatorino1 = new Actuator("a1_interruttori", interruttori);
		Actuator attuatorino2 = new Actuator("a2_interruttori", interruttori);
		sensorCategoryManager.addEntry(temperatura);
		actuatorCategoryManager.addEntry(interruttori);
		temperino.addEntry(soggiorno);
		temperino2.addEntry(soggiorno);
		soggiorno.addActuator(attuatorino1);
		soggiorno.addActuator(attuatorino2);
		soggiorno.addArtifact(portaOmbrelli);
		soggiorno.addArtifact(lampadario);
		soggiorno.addSensor(temperino);
		soggiorno.addSensor(temperino2);
		
		
		
		//CATEGORY STRUCTURES (from files after this)
		
		
		
		HomeLogin login = new HomeLogin();
		login.addEntry("paolino", "6fcb473c563dc49628a187d2a590ff2c000da215d8cd914f7901df3bc2a2c626"); //pippo123456
		String [] voci = {"Fruitore", "Manutentore"};
		String [] userVoices = {"Visualizzare descrizione unità immobiliare", "Visualizza stanza"};
		String [] roomVoices = {"Visualizzare descrizione stanza", "Visualizza sensore", "Visualizza attuatore", "Visualizza artefatto"};
		menu = new MyMenu("Login", voci);
		userMenu = new MyMenu("Scegli cosa fare", userVoices);
		roomMenu = new MyMenu("Scegli un opzione", roomVoices);
		String user;
		int scelta;
		do
		{
			scelta = menu.scegli();
			switch(scelta)
			{
				case 1: 
					user = RawDataInput.readNotVoidString("Inserisci il nome utente");
					System.out.println("Benvenuto" + user);
					showUserMenu();
					break;
					
				case 2:
					String password;
					boolean ok;
					do
					{
						ok = false;
						user = RawDataInput.readNotVoidString("Inserisci il nome utente (0 per tornare indietro)");
						if(!user.equalsIgnoreCase("0"))
						{	
							password = RawDataInput.readNotVoidString("Inserisci la password");
							ok = login.checkPassword(user, password);
							if(!ok)
								System.out.println("Nome utente o password errati");
						}
					}
					while (!user.equalsIgnoreCase("0") && !ok);
					if (ok)
						showMaintainerMenu();
					break;
			}
		}
		while (scelta != 0);
	}

	private static void showMaintainerMenu() {
		
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
					DataOutput.printHousingUnit(home.toString());
					break;
				case 2:
					DataOutput.printListOfString(home.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedRoom = RawDataInput.readNotVoidString("Inserisci il nome della stanza su cui vuoi operare");
					showRoomMenu(selectedRoom);
					break;
				case 3:
					DataOutput.printListOfString(sensorCategoryManager.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedSensCategory = RawDataInput.readNotVoidString("Inserisci la categoria di sensori che vuoi visualizzare");
					DataOutput.printSensorCategory(sensorCategoryManager.getElementByName(selectedSensCategory).toString());
					// Da valutare se castare o meno
					break;
				case 4:
					DataOutput.printListOfString(sensorCategoryManager.namesList());
					System.out.println();
					System.out.println("---------DEVO FORMATTARE MEGLIO---------");
					System.out.println();
					
					String selectedActuCategory = RawDataInput.readNotVoidString("Inserisci la categoria di attuatori che vuoi visualizzare");
					DataOutput.printSensorCategory(actuatorCategoryManager.getElementByName(selectedActuCategory).toString());
					// Da valutare se castare o meno
					break;
					
			}
		}
		while(exitFlag!=true);
	}

	private static void showRoomMenu(String selectedRoom) {
		Room toWorkOn = (Room)home.getElementByName(selectedRoom);
		boolean exitFlag = false;
		do {
			int choice = roomMenu.scegli();
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
}
