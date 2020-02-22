package it.unibs.ing.domohouse.util;

import java.io.Serializable;

import it.unibs.ing.domohouse.components.*;

public class DataHandler implements Serializable {

	private static final long serialVersionUID = 830399600665259268L;
	private Manager sensorCategoryManager;
	private Manager actuatorCategoryManager;
	private HousingUnit housingUnit;
	/*
	 * invariante sensorCategoryManager != null, actuatorCategoryManager != null
	 */
	public DataHandler () {
		sensorCategoryManager = new Manager();
		actuatorCategoryManager = new Manager();
	}


	public void addHouse(HousingUnit toAdd) {
		assert toAdd != null;
		housingUnit = toAdd;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public String getHousingUnitString() {
		assert housingUnit != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return housingUnit.toString();
	}

	public String[] getRoomList() {
		assert housingUnit != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return housingUnit.roomList();
	}

	public String[] getSensorCategoryList() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return sensorCategoryManager.namesList();
	}

	public String getSensorCategoryString(String selectedSensCategory) {
		assert selectedSensCategory != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert sensorCategoryManager.hasEntry(selectedSensCategory) : "sensorCategoryManager di dataHandler non contiene " + selectedSensCategory;
		
		String result = sensorCategoryManager.getElementString(selectedSensCategory);
		
		assert result != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result;
	}

	public String[] getActuatorCategoryList() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return actuatorCategoryManager.namesList();
	}

	public String getActuatorCategoryString(String selectedActuCategory) {
		assert selectedActuCategory != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert actuatorCategoryManager.hasEntry(selectedActuCategory) : "actuatorCategoryManager non contiene " + selectedActuCategory;
		
		String result =  actuatorCategoryManager.getElementString(selectedActuCategory);
		
		assert result != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result;
	}

	public boolean hasSensorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return sensorCategoryManager.hasEntry(category);
	}
	
	public boolean hasActuatorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return actuatorCategoryManager.hasEntry(category);
	}

	public void addSensorCategory(SensorCategory cat) {
		assert cat != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		int pre_size = sensorCategoryManager.size();
		
		sensorCategoryManager.addEntry(cat);
		
		assert sensorCategoryManager.size() >= pre_size;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public void addActuatorCategory(ActuatorCategory cat) {
		assert cat != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		int pre_size = actuatorCategoryManager.size();
		
		actuatorCategoryManager.addEntry(cat);
		
		assert actuatorCategoryManager.size() >= pre_size;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public SensorCategory getSensorCategory(String category) {
		assert category != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert sensorCategoryManager.hasEntry(category) : "sensorCategoryManager non contiene " + category;
		
		SensorCategory s = (SensorCategory)sensorCategoryManager.getElementByName(category);
		
		assert s != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return s;
	}

	public ActuatorCategory getActuatorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert actuatorCategoryManager.hasEntry(category) : "actuatorCategoryManager non contiene " + category;
		
		ActuatorCategory act = (ActuatorCategory)actuatorCategoryManager.getElementByName(category);
		
		assert act != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return act;
	}

	public String getRoomString(String selectedRoom) {
		assert selectedRoom != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		assert housingUnit.hasRoom(selectedRoom) : "La casa " + housingUnit.getName() + " non contiene la stanza " + selectedRoom;
		
		String result =  housingUnit.getRoomString(selectedRoom);
		
		assert result != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result;
	}

	public String[] getSensorNames(String selectedRoom) {

		assert selectedRoom != null && housingUnit != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit.hasRoom(selectedRoom) : "La casa + " + housingUnit.getName() + " non contiene la stanza " + selectedRoom;
		
		Room room = housingUnit.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return room.getSensorsNames();
	}

	public String getSensorString(String selectedSensor) {
		assert selectedSensor != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		
		String result = housingUnit.getSensorString(selectedSensor);
		
		assert result != null; 
		return result;
	}

	public String[] getActuatorNames(String selectedRoom) {

		assert selectedRoom != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		assert housingUnit.hasRoom(selectedRoom) : "La casa " + housingUnit.getName() + " non contiene la stanza " + selectedRoom;
		
		Room room = housingUnit.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return room.getActuatorsNames();
	}

	public String getActuatorString(String selectedActuator) {
		assert selectedActuator != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		
		String result =  housingUnit.getActuatorString(selectedActuator);
		
		assert result != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result; 
	}
	

	public String[] getArtifactNames(String selectedRoom) {
		assert selectedRoom != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		assert housingUnit.hasRoom(selectedRoom) : "La casa " + housingUnit.getName() + " non contiente la stanza " + selectedRoom + " e dunque non può restituire i nomi degli artefatti";
		
		Room room = housingUnit.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return room.getArtifactsNames();
	}

	public String getArtifactString(String selectedArtifact) {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		String s = housingUnit.getArtifactString(selectedArtifact);
		
		assert s != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return s; 
	}

	public void changeHouseDescription(String descr) {
		assert descr != null;
		assert housingUnit != null;
		
		housingUnit.setDescr(descr);
		
		assert housingUnit.getDescr() != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public boolean hasRoom(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.hasRoom(name);
	}

	public void addRoom(Room toAdd) {
		assert toAdd != null;
		assert housingUnit != null;
		
		housingUnit.addRoom(toAdd);
		
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public void changeRoomDescription(String selectedRoom, String descr) {
		assert selectedRoom != null && descr != null; 
		assert housingUnit != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit.hasRoom(selectedRoom) : "La casa " + housingUnit.getName() + " non contiente la stanza " + selectedRoom;
		
		housingUnit.setRoomDescription(selectedRoom, descr);

		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public void addSensor(String location, Sensor sensor) {
		assert location != null && sensor != null && sensor.getName() != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		assert housingUnit.hasRoom(location);
		
		
		housingUnit.addSensor(location, sensor);
		
		assert housingUnit.getRoom(location).getSensorByName(sensor.getName()) != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public boolean hasRoomOrArtifact(String name) {
		assert name != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.hasRoomOrArtifact(name);
	}

	public boolean hasSensor(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.hasSensor(name);
	}
	
	public boolean hasActuator(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.hasActuator(name);
	}

	public boolean hasArtifact(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.hasArtifact(name);
	}

	public boolean isElementARoom(String toAssoc) {
		assert toAssoc != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.isElementARoom(toAssoc);
	}

	public boolean isAssociated(String toAssoc, String category) {
		assert toAssoc != null && category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.isAssociated(toAssoc, category);
	}

	public void addAssociation(String object, String category) {
		assert object != null && category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		housingUnit.addAssociation(object, category);
	
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public Room getRoom(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		assert housingUnit.hasRoom(name) : "La casa " + housingUnit.getName() + " non contiene la stanza " + name;
		
		Room room = housingUnit.getRoom(name);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return room;
	}

	public Artifact getArtifact(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		Artifact art = housingUnit.getArtifact(name);
		
		assert art != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return art;
	}

	public void addArtifact(String location, Artifact toAdd) {
		assert location != null && toAdd != null && toAdd.getName() != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		assert housingUnit.hasRoom(location);
		
		housingUnit.addArtifact(toAdd, location);
		
		assert housingUnit.hasArtifact(toAdd.getName());
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public void addActuator(String location, Actuator toAdd) {
		assert location != null && toAdd != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		assert housingUnit.hasRoom(location);
		
		housingUnit.addActuator(toAdd, location);
		
		assert housingUnit.getRoom(location).getActuatorByName(toAdd.getName()) != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
	}

	public boolean hasOperatingMode(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return OperatingModesHandler.hasOperatingMode(name);
	}
	
	public boolean isThereRoomOrArtifact() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.isThereRoomOrArtifact();
	}
	
	public boolean isThereRoom() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.isThereRoom();
	}
	
	public boolean isThereArtifact() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.isThereArtifact();
	}
	public boolean dataHandlerInvariant() {
		boolean checkManagers = sensorCategoryManager != null && actuatorCategoryManager != null;
		return checkManagers; 
	}
}