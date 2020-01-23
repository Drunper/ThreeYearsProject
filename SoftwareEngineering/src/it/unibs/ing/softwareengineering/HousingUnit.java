package it.unibs.ing.softwareengineering;

import java.util.ArrayList;

public class HousingUnit {
	
	private String name;
	private String text;
	private ArrayList<Room> rooms;
	
	public HousingUnit(String name, String text, ArrayList<Room> rooms) {
		this.name = name;
		this.text = text;
		this.rooms = rooms;
	}
	
	public String[] getRoomList() {
		String[] roomNames = new String[rooms.size()];
		for(int i=0; i<rooms.size();i++) {
			roomNames[i] = rooms.get(i).getRoomName();
		}
		return roomNames;
	}
}
