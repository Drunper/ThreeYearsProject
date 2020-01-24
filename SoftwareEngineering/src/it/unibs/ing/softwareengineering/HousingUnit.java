package it.unibs.ing.softwareengineering;

import java.util.ArrayList;

public class HousingUnit {
	
	private String name;
	private String text;
	private ArrayList<Room> roomList;
	private ArrayList<Sensor> allSensorList;
	private ArrayList<Actuator> allActuatorList;
	private ArrayList<Artifact> allArtifactList;
	
	public HousingUnit(String name, String text, ArrayList<Room> rooms) {
		this.name = name;
		this.text = text;
		this.roomList = rooms;
		gatherAllSensors();
		gatherAllActuators();
		gatherAllArtifacts();
	}
	
	public String[] getRoomNames() {
		String[] roomNames = new String[roomList.size()];
		for(int i=0; i<roomList.size();i++) {
			roomNames[i] = roomList.get(i).getRoomName();
		}
		return roomNames;
	}
	
	public String[] getSensorNames() {
		String[] sensorNames = new String[allSensorList.size()];
		for(int i=0; i<allSensorList.size();i++) {
			sensorNames[i] = allSensorList.get(i).getName();
		}
		return sensorNames;
	}
	
	public String[] getActuatorNames() {
		String[] actuatorNames = new String[allActuatorList.size()];
		for(int i=0; i<allActuatorList.size();i++) {
			actuatorNames[i] = allActuatorList.get(i).getName();
		}
		return actuatorNames;
	}
	
	public String[] getArtifactNames() {
		String[] artifactNames = new String[allArtifactList.size()];
		for(int i=0; i<allArtifactList.size();i++) {
			artifactNames[i] = allArtifactList.get(i).getName();
		}
		return artifactNames;
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
	
	public int getAllSensorListSize() {
		return this.allSensorList.size();
	}
	
	public int getAllActuatorListSize() {
		return this.allActuatorList.size();
	}
	
	public int getAllArtifactListSize() {
		return this.allArtifactList.size();
	}
	
	private void gatherAllSensors() {
		for(int i=0; i<roomList.size(); i++) {
			for(int j=0; j<roomList.get(i).getSensorListSize();j++) {
				allSensorList.add(roomList.get(i).getSensorFromIndex(j));
			}
		}
	}
	
	private void gatherAllActuators() {
		for(int i=0; i<roomList.size(); i++) {
			for(int j=0; j<roomList.get(i).getActuatorListSize();j++) {
				allActuatorList.add(roomList.get(i).getActuatorFromIndex(j));
			}
		}
	}
	
	private void gatherAllArtifacts() {
		for(int i=0; i<roomList.size(); i++) {
			for(int j=0; j<roomList.get(i).getArtifactListSize();j++) {
				allArtifactList.add(roomList.get(i).getArtifactFromIndex(j));
			}
		}
	}
	
	public Sensor getSensorFromHomeSensorList(int i) {
		return this.allSensorList.get(i);
	}
	
	public Actuator getActuatorFromHomeActuatorList(int i) {
		return this.allActuatorList.get(i);
	}
	
	public Artifact getArtifactFromHomeArtifactList(int i) {
		return this.allArtifactList.get(i);
	}
	
	
}
