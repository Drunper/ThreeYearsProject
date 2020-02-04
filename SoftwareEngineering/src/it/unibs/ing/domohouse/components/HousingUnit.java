package it.unibs.ing.domohouse.components;

import java.io.Serializable;

import it.unibs.ing.domohouse.util.Association;
import it.unibs.ing.domohouse.util.AssociationHandler;
import it.unibs.ing.domohouse.util.Manager;

public class HousingUnit implements Serializable {
	
	private String name;
	private String descr;
	private Manager roomManager;
	private Manager sensorManager; //tutti i sensori della casa, serve per il controllo dell'unicità dei nomi
	private Manager actuatorManager; //tutti gli attuatori della casa
	private Manager artifactManager; //tutti gli artefatti della casa
	private AssociationHandler associationManager; //per il controllo delle associazioni
	
	public HousingUnit(String name, String descr) {
		this.name = name;
		this.descr = descr;
		roomManager = new Manager();
		sensorManager = new Manager();
		actuatorManager = new Manager();
		artifactManager = new Manager();
		associationManager = new AssociationHandler();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public void addRoom(Room toAdd) {
		roomManager.addEntry(toAdd);
		associationManager.addAssociation(new Association(toAdd.getName()));
	}
	
	public String [] roomList() {
		return roomManager.namesList();
	}
	
	public Room getRoom(String name) {
		return (Room)roomManager.getElementByName(name);
	}
	
	public boolean hasRoom(String name) {
		return roomManager.hasEntry(name);
	}

	public String getRoomString(String selectedRoom) {
		return roomManager.getElementString(selectedRoom);
	}
	
	public String toString() {
		String roomNames = String.join(":", roomList());
		return name+':'+descr+':'+roomNames;
	}

	public String getSensorString(String selectedSensor) {
		return sensorManager.getElementString(selectedSensor);
	}

	public String getActuatorString(String selectedActuator) {
		return actuatorManager.getElementString(selectedActuator);
	}

	public String getArtifactString(String selectedArtifact) {
		return artifactManager.getElementString(selectedArtifact);
	}

	public void setRoomDescription(String selectedRoom, String descr) {
		Room room = (Room)roomManager.getElementByName(selectedRoom);
		room.setDescr(descr);
	}

	public void addSensor(String location, Sensor toAdd) {
		Room room = (Room)roomManager.getElementByName(location);
		room.addSensor(toAdd);
		sensorManager.addEntry(toAdd);
	}

	public boolean hasRoomOrArtifact(String name) {
		return associationManager.hasEntry(name);
	}

	public boolean hasSensor(String name) {
		return sensorManager.hasEntry(name);
	}

	public boolean hasActuator(String name) {
		return actuatorManager.hasEntry(name);
	}

	public boolean hasArtifact(String name) {
		return artifactManager.hasEntry(name);
	}

	public boolean isElementARoom(String toAssoc) {
		return associationManager.isElementARoom(toAssoc);
	}

	public boolean isAssociated(String toAssoc, String category) {
		return associationManager.isAssociated(toAssoc, category);
	}

	public void addAssociation(String object, String category) {
		associationManager.addAssociation(object, category);
	}

	public Artifact getArtifact(String name) {
		return (Artifact)artifactManager.getElementByName(name);
	}

	public void addArtifact(Artifact toAdd, String location) {
		Room room = (Room)roomManager.getElementByName(location);
		room.addArtifact(toAdd);
		artifactManager.addEntry(toAdd);
		associationManager.addAssociation(new Association(toAdd.getName()));
	}

	public void addActuator(Actuator toAdd, String location) {
		Room room = (Room)roomManager.getElementByName(location);
		room.addActuator(toAdd);
		actuatorManager.addEntry(toAdd);
	}
}
