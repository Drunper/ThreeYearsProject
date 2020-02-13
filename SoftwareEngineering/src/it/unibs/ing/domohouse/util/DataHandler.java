package it.unibs.ing.domohouse.util;

import java.io.Serializable;

import it.unibs.ing.domohouse.components.*;

public class DataHandler implements Serializable {

	private Manager sensorCategoryManager;
	private Manager actuatorCategoryManager;
	private HousingUnit housingUnit;
	
	public DataHandler () {
		sensorCategoryManager = new Manager();
		actuatorCategoryManager = new Manager();
		/*
		housingUnit = new HousingUnit("Casa di Ivan", "Boh"); 
		
		SensorCategory temperatura = new SensorCategory("sensori di temperatura", "TEMP:Tommaso Ebhardt:idle:endD");
		SensorCategory antifiamma = new SensorCategory("sensori antifiamma", "ANTFLM:Herbert Asbury:idle:endD");
		SensorCategory antifumo = new SensorCategory("sensori di rilevamento fumo", "ANTFM:Douglas Adams:idle:endD");
		SensorCategory movimento = new SensorCategory("sensori di movimento", "MVM:Sun Tzu:idle:endD");
		
		ActuatorCategory sprinkler = new ActuatorCategory("sprinkler", "Sprink:Sprinkler spa:idle:endD");
		ActuatorCategory interruttori = new ActuatorCategory("interruttori di accensione", "INTACC:Edward Snowden:idle:endD"); 
		ActuatorCategory allarme_acustico = new ActuatorCategory("allarme acustico", "ALLRM:Jordan Belfort:idle:endD");
		
		Room soggiorno = new Room("Soggiorno", "Sala in cui si vive penso");
		Room cucina = new Room("Cucina", "Sala in cui di solito si cucina ma ognuno può vivere come gli pare, se si mette a dormire li dentro a me non frega niente");
		Room camera = new Room("Camera", "Si dorme");
		soggiorno.setNumericProperty("temperatura", 20);
		soggiorno.setNumericProperty("umidità", 53);
		soggiorno.setNumericProperty("pressione", 872);
		soggiorno.setNumericProperty("vento", 0);
		cucina.setNumericProperty("temperatura", 20);
		cucina.setNumericProperty("umidità", 53);
		cucina.setNumericProperty("pressione", 872);
		cucina.setNumericProperty("vento", 0);
		camera.setNumericProperty("temperatura", 20);
		camera.setNumericProperty("umidità", 53);
		camera.setNumericProperty("pressione", 872);
		camera.setNumericProperty("vento", 0);
		
		housingUnit.addRoom(soggiorno);
		housingUnit.addRoom(cucina);
		housingUnit.addRoom(camera);
		
		Artifact lampadario_camera = new Artifact("lampadarioCamera", "sono un bel lampadario ammuffito");
		Artifact lampadario_cucina = new Artifact("lampadarioCucina", "sono un lampadario abbronzato");
		Artifact forno = new Artifact("Forno da cucina", "Proprio un bel forno di quelli costosi e luccicanti");
		Artifact lampadario_soggiorno = new Artifact("lampadarioMurano", "Sono un lampa-dario");
		Artifact portaOmbrelli = new Artifact("portaOmbrelli", "servo solo per far numero e non ho molto senso");
		
		
		Sensor temperino = new NumericSensor("t1_temperatura", temperatura);
		Sensor temperino2 = new NumericSensor("t2_temperatura", temperatura);
		Sensor sensore_antifiamma = new NumericSensor("ant1_antifiamma", antifiamma); //soggiorno
		Sensor sensore_antifumo = new NumericSensor("antfum1_antifumo", antifumo);//soggiorno
		Sensor sensore_antifiamma1 = new NumericSensor("ant2_antifiamma", antifiamma); //cucina
		Sensor sensore_antifumo1 = new NumericSensor("antifum2_antifumo", antifumo); //cucina
		Sensor sensore_movimento = new NumericSensor("mov1_movimento", movimento); //soggiorno
		
		Actuator attuatorino1 = new Actuator("a1_interruttori", interruttori);
		Actuator attuatorino2 = new Actuator("a2_interruttori", interruttori);
		Actuator sprinkler1 = new Actuator("s1_sprinkler", sprinkler);
		Actuator sprinkler2 = new Actuator("s2_sprinkler", sprinkler);
		Actuator allarme1 = new Actuator("all1_allarme", allarme_acustico);
		Actuator allarme2 = new Actuator("all2_allarme", allarme_acustico);
		Actuator allarme3 = new Actuator("all3_allarme", allarme_acustico);
		
		
		sensorCategoryManager.addEntry(temperatura);
		sensorCategoryManager.addEntry(antifiamma);
		sensorCategoryManager.addEntry(antifumo);
		sensorCategoryManager.addEntry(movimento);
		actuatorCategoryManager.addEntry(interruttori);
		actuatorCategoryManager.addEntry(sprinkler);
		actuatorCategoryManager.addEntry(allarme_acustico);
		
		soggiorno.addActuator(attuatorino1);
		soggiorno.addActuator(attuatorino2);
		soggiorno.addArtifact(portaOmbrelli);
		soggiorno.addArtifact(lampadario_soggiorno);
		soggiorno.addSensor(temperino);
		soggiorno.addSensor(temperino2);
		soggiorno.addActuator(allarme1);
		soggiorno.addSensor(sensore_movimento);
		
		cucina.addArtifact(forno);
		cucina.addArtifact(lampadario_cucina);
		cucina.addSensor(sensore_antifiamma);
		cucina.addSensor(sensore_antifumo);
		cucina.addActuator(sprinkler1);
		cucina.addActuator(allarme2);
		
		camera.addSensor(sensore_antifiamma1);
		camera.addSensor(sensore_antifumo1);
		camera.addActuator(sprinkler2);
		camera.addActuator(allarme3);
		camera.addArtifact(lampadario_camera);

		housingUnit.addSensor("Soggiorno", temperino);
		housingUnit.addSensor("Soggiorno", temperino2);
		housingUnit.addSensor("Soggiorno", sensore_movimento);
		housingUnit.addActuator(attuatorino1, "Soggiorno");
		housingUnit.addActuator(attuatorino2, "Soggiorno");
		housingUnit.addActuator(allarme1, "Soggiorno");
		housingUnit.addArtifact(lampadario_soggiorno, "Soggiorno");
		housingUnit.addArtifact(portaOmbrelli, "Soggiorno");
		housingUnit.addSensor("Camera", sensore_antifiamma1);
		housingUnit.addSensor("Camera", sensore_antifumo1);
		housingUnit.addActuator(sprinkler2, "Camera");
		housingUnit.addActuator(allarme3, "Camera");
		housingUnit.addArtifact(lampadario_camera, "Camera");
		housingUnit.addSensor("Cucina", sensore_antifiamma);
		housingUnit.addSensor("Cucina", sensore_antifumo);
		housingUnit.addActuator(sprinkler1, "Cucina");
		housingUnit.addActuator(allarme2, "Cucina");
		housingUnit.addArtifact(forno, "Cucina");
		housingUnit.addArtifact(lampadario_cucina, "Cucina");
		
		temperino.addEntry(soggiorno);
		temperino2.addEntry(soggiorno);
		attuatorino1.addEntry(soggiorno);
		attuatorino2.addEntry(soggiorno);
		allarme1.addEntry(soggiorno);
		sensore_movimento.addEntry(soggiorno);
		sensore_antifiamma1.addEntry(camera);
		sensore_antifumo1.addEntry(camera);
		sprinkler2.addEntry(camera);
		allarme3.addEntry(camera);
		sensore_antifiamma.addEntry(cucina);
		sensore_antifumo.addEntry(cucina);
		sprinkler1.addEntry(cucina);
		allarme2.addEntry(cucina);
*/
	}


	public void addHouse(HousingUnit toAdd) {
		housingUnit = toAdd;
	}
	
	public String getHousingUnitString() {
		return housingUnit.toString();
	}

	public String[] getRoomList() {
		return housingUnit.roomList();
	}

	public String[] getSensorCategoryList() {
		return sensorCategoryManager.namesList();
	}

	public String getSensorCategoryString(String selectedSensCategory) {
		return sensorCategoryManager.getElementString(selectedSensCategory);
	}

	public String[] getActuatorCategoryList() {
		return actuatorCategoryManager.namesList();
	}

	public String getActuatorCategoryString(String selectedActuCategory) {
		return actuatorCategoryManager.getElementString(selectedActuCategory);
	}

	public boolean hasSensorCategory(String category) {
		return sensorCategoryManager.hasEntry(category);
	}
	
	public boolean hasActuatorCategory(String category) {
		return actuatorCategoryManager.hasEntry(category);
	}

	public void addSensorCategory(SensorCategory cat) {
		sensorCategoryManager.addEntry(cat);
	}

	public void addActuatorCategory(ActuatorCategory cat) {
		actuatorCategoryManager.addEntry(cat);
	}

	public SensorCategory getSensorCategory(String category) {
		return (SensorCategory)sensorCategoryManager.getElementByName(category);
	}

	public ActuatorCategory getActuatorCategory(String category) {
		return (ActuatorCategory)actuatorCategoryManager.getElementByName(category);
	}

	public String getRoomString(String selectedRoom) {
		return housingUnit.getRoomString(selectedRoom);
	}

	public String[] getSensorNames(String selectedRoom) {
		Room room = housingUnit.getRoom(selectedRoom);
		return room.getSensorsNames();
	}

	public String getSensorString(String selectedSensor) {
		return housingUnit.getSensorString(selectedSensor);
	}

	public String[] getActuatorNames(String selectedRoom) {
		Room room = housingUnit.getRoom(selectedRoom);
		return room.getActuatorsNames();
	}

	public String getActuatorString(String selectedActuator) {
		return housingUnit.getActuatorString(selectedActuator);
	}

	public String[] getArtifactNames(String selectedRoom) {
		Room room = housingUnit.getRoom(selectedRoom);
		return room.getArtifactsNames();
	}

	public String getArtifactString(String selectedArtifact) {
		return housingUnit.getArtifactString(selectedArtifact);
	}

	public void changeHouseDescription(String descr) {
		housingUnit.setDescr(descr);
	}

	public boolean hasRoom(String name) {
		return housingUnit.hasRoom(name);
	}

	public void addRoom(Room toAdd) {
		housingUnit.addRoom(toAdd);
	}

	public void changeRoomDescription(String selectedRoom, String descr) {
		housingUnit.setRoomDescription(selectedRoom, descr);
	}

	public void addSensor(String location, Sensor sensor) {
		housingUnit.addSensor(location, sensor);
	}
	
	public boolean hasRoomOrArtifact(String name) {
		return housingUnit.hasRoomOrArtifact(name);
	}

	public boolean hasSensor(String name) {
		return housingUnit.hasSensor(name);
	}
	
	public boolean hasActuator(String name) {
		return housingUnit.hasActuator(name);
	}

	public boolean hasArtifact(String name) {
		return housingUnit.hasArtifact(name);
	}

	public boolean isElementARoom(String toAssoc) {
		return housingUnit.isElementARoom(toAssoc);
	}

	public boolean isAssociated(String toAssoc, String category) {
		return housingUnit.isAssociated(toAssoc, category);
	}

	public void addAssociation(String object, String category) {
		housingUnit.addAssociation(object, category);
	}

	public Room getRoom(String name) {
		return housingUnit.getRoom(name);
	}

	public Artifact getArtifact(String name) {
		return housingUnit.getArtifact(name);
	}

	public void addArtifact(String location, Artifact toAdd) {
		housingUnit.addArtifact(toAdd, location);
	}
	
	public void addActuator(String location, Actuator toAdd) {
		housingUnit.addActuator(toAdd, location);
	}

	public boolean hasOperatingMode(String name) {
		return OperatingModesHandler.hasOperatingMode(name);
	}
}
