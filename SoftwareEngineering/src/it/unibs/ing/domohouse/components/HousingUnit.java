package it.unibs.ing.domohouse.components;

import java.io.Serializable;

import it.unibs.ing.domohouse.util.Manager;

public class HousingUnit implements Serializable {
	
	private String name;
	private String descr;
	private Manager roomManager;
	
	public HousingUnit(String name, String descr) {
		this.name = name;
		this.descr = descr;
		roomManager = new Manager();
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
	}
	
	public String [] roomList() {
		return roomManager.namesList();
	}
	
	public Room getRoomByName(String name) {
		return (Room)roomManager.getElementByName(name);
	}
	
	public boolean hasRoom(String name) {
		return roomManager.hasEntry(name);
	}

	public String toString() {
		String roomNames = String.join(":", roomList());
		return name+':'+descr+':'+roomNames;
	}
}
