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
	
	public void setName(String roomName) {
		this.roomName = roomName;
	}

	public void setDescr(String text) {
		this.text = text;
	}
	
	public Sensor getSensorFromIndex(int i) {
		return this.sensorList.get(i);
	}
	
	public int getSensorListSize() {
		return this.sensorList.size();
	}
	
	public String[] getSensorNames() {
		String[] sensorNames = new String[sensorList.size()];
		for(int i=0; i<sensorList.size();i++) {
			sensorNames[i] = sensorList.get(i).getName();
		}
		return sensorNames;
	}
	
	public Actuator getActuatorFromIndex(int i) {
		return this.actuatorList.get(i);
	}
	
	public int getActuatorListSize() {
		return this.actuatorList.size();
	}
	
	public String[] getActuatorNames() {
		String[] actuatorNames = new String[actuatorList.size()];
		for(int i=0; i<actuatorList.size();i++) {
			actuatorNames[i] = actuatorList.get(i).getName();
		}
		return actuatorNames;
	}
	
	public Artifact getArtifactFromIndex(int i) {
		return this.artifactList.get(i);
	}
	
	public int getArtifactListSize() {
		return this.artifactList.size();
	}
	
	public String[] getArtifactNames() {
		String[] artifactNames = new String[artifactList.size()];
		for(int i=0; i<artifactList.size();i++) {
			artifactNames[i] = artifactList.get(i).getName();
		}
		return artifactNames;
	}
}
