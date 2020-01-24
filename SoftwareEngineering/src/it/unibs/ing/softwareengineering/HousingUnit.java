package it.unibs.ing.softwareengineering;

import java.util.ArrayList;

public class HousingUnit {
	
	private String name;
	private String text;
	private ArrayList<Room> roomList;
	
	public HousingUnit(String name, String text, ArrayList<Room> rooms) {
		this.name = name;
		this.text = text;
		this.roomList = rooms;
	}
	
	public String[] getRoomNames() {
		String[] roomNames = new String[roomList.size()];
		for(int i=0; i<roomList.size();i++) {
			roomNames[i] = roomList.get(i).getRoomName();
		}
		return roomNames;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescr() {
		return this.text;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescr(String text) {
		this.text = text;
	}
	
	public Room getRoomFromIndex(int i) {
		return this.roomList.get(i);
	}
	
	public int getRoomListSize() {
		return this.roomList.size();
	}
	
}
