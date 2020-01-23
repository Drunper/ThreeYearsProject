package it.unibs.ing.softwareengineering;

import java.util.*;

public class Room {
	private String roomName;
	private String text;
	private ArrayList<Sensor> sensorList = new ArrayList<>();
	private ArrayList<Actuator> actuatorList = new ArrayList<>();
	private ArrayList<Artifact> artifactList = new ArrayList<>();
	
	public Room (String roomName, String text, ArrayList<Sensor> sensorList, ArrayList<Actuator> actuatorList, ArrayList<Artifact> artifactList) {
		this.roomName = roomName;
		this.text = text;
		this.sensorList = sensorList;
		this.actuatorList = actuatorList;
		this.artifactList = artifactList;
	}

	public double getMeasure(String measuredVariable) {
		return 0;
	}
	
	public String getRoomName() {
		return this.roomName;
	}
	
	public String getDescr() {
		return this.text;
	}

}
