package it.unibs.ing.domohouse;

import it.unibs.ing.domohouse.components.Actuator;
import it.unibs.ing.domohouse.components.ActuatorCategory;
import it.unibs.ing.domohouse.components.Artifact;
import it.unibs.ing.domohouse.components.HousingUnit;
import it.unibs.ing.domohouse.components.Room;
import it.unibs.ing.domohouse.components.Sensor;
import it.unibs.ing.domohouse.components.SensorCategory;
import it.unibs.ing.domohouse.util.FileLoader;
import it.unibs.ing.domohouse.util.FileSaver;
import it.unibs.ing.domohouse.util.ImplementedMenu;
import it.unibs.ing.domohouse.util.Manager;

public class HomeMain {
	//
	public static HomeLogin login;
	public static HousingUnit home; //change home to public 
	public static Manager sensorCategoryManager; //change to public
	public static Manager actuatorCategoryManager; //change to public
	
	public static void main(String[] args) {
		sensorCategoryManager = new Manager();
		actuatorCategoryManager = new Manager();
		
		SensorCategory temperatura = new SensorCategory("sensori di temperatura", "misurano la temperatura"); //saved
		ActuatorCategory interruttori = new ActuatorCategory("interruttori di accensione", "accendono qualcosa"); //saved
		home = new HousingUnit("Casa di Ivan", "Boh"); //saved
		Room soggiorno = new Room("Soggiorno", "Sala in cui si vive penso");
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
		
		/* **ESEMPIO DI CARICAMENTE DA FILE ATTRAVERSO FILE LOADER**
		FileLoader fl = new FileLoader();
		SensorCategory temperatura = (SensorCategory) fl.ReadObjectFromFile("data\\categories\\sensor\\categoria_temperatura.dat");
		*/ 
		
		
		System.out.println("PROVIAMO A SALVARE");
		FileSaver fs = new FileSaver();
		fs.writeObjectToFile(FileSaver.houseFilepath, "casa_di_ivan", home);
		fs.writeObjectToFile(FileSaver.sensorCategoryFilepath, "categoria_temperatura", temperatura);
		fs.writeObjectToFile(FileSaver.actuatorCategoryFilepath, "categoria_interruttori", interruttori);
		fs.writeObjectToFile(FileSaver.roomFilepath, "soggiorno", soggiorno);
		fs.writeObjectToFile(FileSaver.actuatorFilepath,"lampadario" , lampadario);
		fs.writeObjectToFile(FileSaver.actuatorFilepath,"attuatorino1" , attuatorino1);
		fs.writeObjectToFile(FileSaver.actuatorFilepath,"attuatorino2" , attuatorino2);
		fs.writeObjectToFile(FileSaver.actuatorFilepath,"portaOmbrelli" , portaOmbrelli);
		fs.writeObjectToFile(FileSaver.sensorFilepath, "temperino", temperino);
		fs.writeObjectToFile(FileSaver.sensorFilepath, "temperino2", temperino);
		System.out.println("SALVATAGGIO EFFETTUATO");
		
		//CATEGORY STRUCTURES (from files after this)
		
		login = new HomeLogin();
		login.addEntry("paolino", "6fcb473c563dc49628a187d2a590ff2c000da215d8cd914f7901df3bc2a2c626"); //pippo123456
		
		ImplementedMenu.show();
	}
}
