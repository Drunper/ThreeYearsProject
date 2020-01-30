package it.unibs.ing.domohouse.components;

import java.util.*;

import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.util.Manager;

public class Room implements Gettable {
	private String name;
	private String text;
	private Manager sensorManager;
	private Manager actuatorManager;
	private Manager artifactManager;
	private TreeMap<String, Double> numericPropertiesMap; //treemap per contenere i valori numerici tipo temperatura
	private TreeMap<String, String> nonNumericPropertiesMap; //treemap per contenere i valori string tipo "presenza persone"
	
	public Room (String name, String text) {
		this.name = name;
		this.text = text;
		sensorManager = new Manager();
		actuatorManager = new Manager();
		artifactManager = new Manager();
		numericPropertiesMap = new TreeMap<>();
		nonNumericPropertiesMap = new TreeMap<>();
	}

	public String getName() {
		return this.name;
	}
	
	public String getDescr() {
		return this.text;
	}
	
	public void setName(String roomName) {
		this.name = roomName;
	}

	public void setDescr(String text) {
		this.text = text;
	}
	
	public void addActuator(Actuator toAdd) {
		actuatorManager.addEntry(toAdd);
	}
	
	public void removeActuator(String toRemove) {
		actuatorManager.remove(toRemove);
	}
	
	public Actuator getActuatorByName(String toGet) {
		return (Actuator)actuatorManager.getElementByName(toGet);
	}
	
	public void changeActuatorName(String oldName, String newName) {
		actuatorManager.changeElementName(oldName, newName);
	}
	
	public boolean hasActuator(String name) {
		return actuatorManager.hasEntry(name);
	}
	
	public String [] getActuatorsNames() {
		return actuatorManager.namesList();
	}
	
	public void addSensor(Sensor toAdd) {
		sensorManager.addEntry(toAdd);
	}
	
	public void removeSensor(String toRemove) {
		sensorManager.remove(toRemove);
	}
	
	public Sensor getSensorByName(String toGet) {
		return (Sensor)sensorManager.getElementByName(toGet);
	}
	
	public void changeSensorName(String oldName, String newName) {
		sensorManager.changeElementName(oldName, newName);
	}
	
	public boolean hasSensor(String name) {
		return sensorManager.hasEntry(name);
	}
	
	public String [] getSensorsNames() {
		return sensorManager.namesList();
	}
	
	public void addArtifact(Artifact toAdd) {
		artifactManager.addEntry(toAdd);
	}
	
	public void removeArtifact(String toRemove) {
		artifactManager.remove(toRemove);
	}
	
	public Artifact getArtifactByName(String toGet) {
		return (Artifact)artifactManager.getElementByName(toGet);
	}
	
	public void changeArtifactName(String oldName, String newName) {
		artifactManager.changeElementName(oldName, newName);
	}
	
	public boolean hasArtifact(String name) {
		return artifactManager.hasEntry(name);
	}
	
	public String [] getArtifactsNames() {
		return artifactManager.namesList();
	}
	
	public double getNumericProperty(String variableName) {
		return numericPropertiesMap.get(variableName);
	}
	
	public String getNonNumericProperty(String variableName) {
		return nonNumericPropertiesMap.get(variableName);
	}
	
	public void setNumericProperty (String variableName, double newValue) {
		numericPropertiesMap.put(variableName, newValue);
	}
	
	public void setNonNumericProperty (String variableName, String newValue) {
		nonNumericPropertiesMap.put(variableName, newValue);
	}
	
	public String toString() {
		String [] sensorNames = getSensorsNames();
		String [] actuatorNames = getActuatorsNames();
		String [] artifactNames = getArtifactsNames();
		String [] elementNames = new String[sensorNames.length+actuatorNames.length+artifactNames.length];
		int i;
		for(i = 0; i < sensorNames.length; i++)
		{
			elementNames[i] = sensorNames[i] + " - Sensore";
		}
		int j = i + actuatorNames.length;
		for(i = sensorNames.length; i < j; i++)
		{
			elementNames[i] = actuatorNames[i-sensorNames.length] + " - Attuatore";
		}
		int k = j + artifactNames.length;
		for(i = j; i < k; i++)
		{
			elementNames[i] = artifactNames[i-j] + " - Artefatto";
		}
		String compactNames = String.join(":", elementNames);
		return name+':'+text+':'+compactNames;
	}
}
