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
		//momentaneamente
		housingUnit = new HousingUnit("Casa di Ivan", "Boh"); 
		SensorCategory temperatura = new SensorCategory("sensori di temperatura", "misurano la temperatura"); 
		ActuatorCategory interruttori = new ActuatorCategory("interruttori di accensione", "sigla:costruttore:modalità di default"); 
		 
		Room soggiorno = new Room("Soggiorno", "Sala in cui si vive penso");
		housingUnit.addRoom(soggiorno);
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
	
	public void addActuator(Actuator actuator, String location) {
		housingUnit.addActuator(actuator, location);
	}

	public boolean hasOperatingMode(String name) {
		return OperatingModesHandler.hasOperatingMode(name);
	}
}
