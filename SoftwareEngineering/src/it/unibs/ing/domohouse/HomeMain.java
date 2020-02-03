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
	
	private static ImplementedMenu allMenu;
	private static HousingUnit home; 
	public static Manager sensorCategoryManager; 
	public static Manager actuatorCategoryManager; 
	
	public static void main(String[] args) {
		allMenu = new ImplementedMenu();
		sensorCategoryManager = new Manager();
		actuatorCategoryManager = new Manager();
		
		SensorCategory temperatura = new SensorCategory("sensori di temperatura", "misurano la temperatura"); 
		ActuatorCategory interruttori = new ActuatorCategory("interruttori di accensione", "sigla:costruttore:modalità di default"); 
		home = new HousingUnit("Casa di Ivan", "Boh"); 
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
		
		//CATEGORY STRUCTURES (from files after this)
			
		allMenu.show(home);
	}
}
