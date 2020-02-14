package it.unibs.ing.domohouse.components;

import java.io.Serializable;

import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.interfaces.Manageable;
import it.unibs.ing.domohouse.util.Manager;

public class Sensor implements Manageable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3804289994000782961L;
	private String name;
	protected SensorCategory category;
	private Manager measuredObjects;
	private boolean state;
	private boolean measuringRoom;

	public Sensor(String name, SensorCategory category) {
		super();
		this.name = name;
		this.category = category;
		measuredObjects = new Manager();
		state = true;
	}
	
	public String getName() {
		return name.split("_")[0];
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addEntry(Room toAdd) {
		measuredObjects.addEntry(toAdd);
	}
	
	public void addEntry(Artifact toAdd) {
		measuredObjects.addEntry(toAdd);
	}
	
	public String [] measuredObjectsList() {
		return measuredObjects.namesList();
	}
	
	public Gettable getElementByName(String name) {
		return (Gettable)measuredObjects.getElementByName(name);
	}

	public boolean isMeasuringRoom() {
		return measuringRoom;
	}

	public void setMeasuringRoom(boolean measuringRoom) {
		this.measuringRoom = measuringRoom;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}
