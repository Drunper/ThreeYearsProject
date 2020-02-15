package it.unibs.ing.domohouse.components;

import java.io.Serializable;

import it.unibs.ing.domohouse.util.Association;
import it.unibs.ing.domohouse.util.AssociationHandler;
import it.unibs.ing.domohouse.util.Manager;

public class HousingUnit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4272512019548783815L;
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
		assert !roomManager.hasEntry(toAdd.getName()) : "E' già presente una stanza con il nome "+toAdd.getName()+" in questa casa";
		roomManager.addEntry(toAdd);
		Association assoc = new Association(toAdd.getName());
		assoc.setIsElementARoom(true);
		associationManager.addAssociation(assoc);
	}
	
	public String [] roomList() {
		assert roomManager.namesList() != null : "roomManager è null";
		return roomManager.namesList();
	}
	
	public Room getRoom(String name) {
		assert roomManager.hasEntry(name) : "Non esiste una stanza chiamata " + name + " in questa casa";
		return (Room)roomManager.getElementByName(name);
	}
	
	public boolean hasRoom(String name) {
		assert name.length() > 0 : "Violated name.length() > 0";
		return roomManager.hasEntry(name);
	}

	public String getRoomString(String selectedRoom) {
		assert roomManager.hasEntry(selectedRoom) : "Non esiste una stanza chiamata " + selectedRoom + " in questa casa";
		return roomManager.getElementString(selectedRoom);
	}
	
	public String toString() {
		String roomNames = String.join(":", roomList());
		return name+':'+descr+':'+roomNames;
	}

	public String getSensorString(String selectedSensor) {
		assert sensorManager.hasEntry(selectedSensor) : "Non esiste un sensore chiamato "+selectedSensor+" in questa casa";
		return sensorManager.getElementString(selectedSensor);
	}

	public String getActuatorString(String selectedActuator) {
		assert actuatorManager.hasEntry(selectedActuator) : "Non esiste un attuatore chiamato "+selectedActuator+" in questa casa";
		return actuatorManager.getElementString(selectedActuator);
	}

	public String getArtifactString(String selectedArtifact) {
		assert artifactManager.hasEntry(selectedArtifact) : "Non esiste un artefatto chiamato " + selectedArtifact + "in questa casa";
		return artifactManager.getElementString(selectedArtifact);
	}

	public void setRoomDescription(String selectedRoom, String descr) {
		assert roomManager.hasEntry(selectedRoom) : "Non esiste una stanza chiamata " +selectedRoom+" in questa casa";
		Room room = (Room)roomManager.getElementByName(selectedRoom);
		room.setDescr(descr);
	}

	public void addSensor(String location, Sensor toAdd) {
		assert roomManager.hasEntry(location) : "Non esiste una stanza chiamata " +location+" in questa casa";
		Room room = (Room)roomManager.getElementByName(location);
		room.addSensor(toAdd);
		sensorManager.addEntry(toAdd);
	}
	
	public boolean hasRoomOrArtifact(String name) {
		assert name.length() > 0 : "Violated hasRoomOrArtifact name.length() > 0";
		return associationManager.hasEntry(name);
	}

	public boolean hasSensor(String name) {
		assert name.length() > 0 : "Violated hasSensor name.length() > 0";
		return sensorManager.hasEntry(name);
	}

	public boolean hasActuator(String name) {
		assert name.length() > 0 : "Violated hasActuator name.length() > 0";
		return actuatorManager.hasEntry(name);
	}

	public boolean hasArtifact(String name) {
		assert name.length() > 0 : "Violated hasArtifact name.length() > 0";
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
		assert artifactManager.hasEntry(name) : "Non esiste un artefatto chiamato " +name+" in questa casa";
		return (Artifact)artifactManager.getElementByName(name);
	}

	public void addArtifact(Artifact toAdd, String location) {
		assert roomManager.hasEntry(location) : "Non esista una stanza chiamata "+ location + "in questa casa";
		Room room = (Room)roomManager.getElementByName(location);
		room.addArtifact(toAdd);
		artifactManager.addEntry(toAdd);
		associationManager.addAssociation(new Association(toAdd.getName()));
	}

	public void addActuator(Actuator toAdd, String location) {
		assert roomManager.hasEntry(location) : "Non esista una stanza chiamata "+ location + "in questa casa";
		Room room = (Room)roomManager.getElementByName(location);
		room.addActuator(toAdd);
		actuatorManager.addEntry(toAdd);
	}
}
