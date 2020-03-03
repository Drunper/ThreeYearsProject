package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.*;

import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.util.Manager;

public class Room implements Gettable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2234559747971441506L;
	private String name;
	private String text;
	private Manager sensorManager;
	private Manager actuatorManager;
	private Manager artifactManager;
	private TreeMap<String, Double> numericPropertiesMap; //treemap per contenere i valori numerici tipo temperatura
	private TreeMap<String, String> nonNumericPropertiesMap; //treemap per contenere i valori string tipo "presenza persone"
	
	/*
	 * Room ha 2 costruttori, uno semplice senza numericProperties per la creazione veloce per effettuare test
	 * e un costruttore dove bisogna inserire anche le proprietà numeriche della stanza come temperatura, umidita, 
	 * pressione, vento. Il manutentore per inserire una nuova stanza dovrà fornire tutte le informazioni numeriche
	 * necessarie.
	 */
	public Room(String name, String text) {
		this.name = name;
		this.text = text;
		sensorManager = new Manager();
		actuatorManager = new Manager();
		artifactManager = new Manager();
		numericPropertiesMap = new TreeMap<>();
		nonNumericPropertiesMap = new TreeMap<>();
	}
	public Room (String name, String text, double temp, double umidita, double pressione, double vento, String presenza_persone) {
		this.name = name;
		this.text = text;
		sensorManager = new Manager();
		actuatorManager = new Manager();
		artifactManager = new Manager();
		numericPropertiesMap = new TreeMap<>();
		numericPropertiesMap.put("temperatura", temp);
		numericPropertiesMap.put("umidità", umidita);
		numericPropertiesMap.put("pressione", pressione);
		numericPropertiesMap.put("vento", vento);
		nonNumericPropertiesMap = new TreeMap<>();
		nonNumericPropertiesMap.put("presenza", presenza_persone);
		nonNumericPropertiesMap.put("coloreLuce", "bianco");
	}

	public String getName() {
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return this.name;
	}
	
	public String getDescr() {
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return this.text;
	}
	
	public void setName(String roomName) {
		assert roomName != null && roomName.length() > 0;
		
		this.name = roomName;
		
		assert this.name != null && this.name.length() > 0;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}

	public void setDescr(String text) {
		assert text != null;
		
		this.text = text;
		
		assert this.text != null;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void addActuator(Actuator toAdd) {
		assert toAdd != null;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		int pre_size = actuatorManager.size();
		
		actuatorManager.addEntry(toAdd);
		
		assert actuatorManager.size() >= pre_size;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void removeActuator(String toRemove) {
		assert toRemove != null && toRemove.length() > 0;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		boolean prec = actuatorManager.hasEntry(toRemove);
		assert prec : "actuatorManger non contiene " + toRemove + " e dunque non può rimuoverlo";
		int pre_size = actuatorManager.size();
		
		actuatorManager.remove(toRemove);
		
		assert actuatorManager.size() <= pre_size;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public Actuator getActuatorByName(String toGet) {
		assert toGet != null && toGet.length() > 0;
		boolean prec = actuatorManager.hasEntry(toGet);
		assert prec : "actuatorManager non contiente " + toGet;
		int pre_size = actuatorManager.size();
		
		Actuator act = (Actuator)actuatorManager.getElementByName(toGet);
	
		assert act != null;
		assert actuatorManager.size() == pre_size;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return act;
	}
	
	public void changeActuatorName(String oldName, String newName) {
		assert oldName != null && oldName.length() > 0;
		assert newName != null && newName.length() > 0;
		assert actuatorManager.hasEntry(oldName) : "actuatorManager non contiene " + oldName;
		int pre_size = actuatorManager.size();
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		
		actuatorManager.changeElementName(oldName, newName);
		
		assert actuatorManager.size() == pre_size;
		assert actuatorManager.hasEntry(newName) : "actuatorManager non contiene " + newName;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public boolean hasActuator(String name) {
		assert name != null && name.length() > 0; 
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return actuatorManager.hasEntry(name);
	}
	
	public String [] getActuatorsNames() {
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return actuatorManager.namesList();
	}
	
	public void addSensor(Sensor toAdd) {
		assert toAdd != null; 
		int pre_size = sensorManager.size();
		
		sensorManager.addEntry(toAdd);
		
		assert sensorManager.size() >= pre_size;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void removeSensor(String toRemove) {
		assert toRemove != null && toRemove.length() > 0;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		int pre_size = sensorManager.size();
		
		sensorManager.remove(toRemove);
		
		assert sensorManager.size() <= pre_size;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public Sensor getSensorByName(String toGet) {
		assert toGet != null && toGet.length() > 0; 
		assert sensorManager.hasEntry(toGet) : "sensorManager non contiene " + toGet;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		
		Sensor s = (Sensor)sensorManager.getElementByName(toGet);
		
		assert s != null; 
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return s;
	}
	
	public void changeSensorName(String oldName, String newName) {
		assert oldName != null && oldName.length() > 0;
		assert newName != null && newName.length() > 0;
		assert sensorManager.hasEntry(oldName) : "sensorManager non contiene " + oldName;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		int pre_size = sensorManager.size();
		
		sensorManager.changeElementName(oldName, newName);
		
		assert sensorManager.size() == pre_size;
		assert sensorManager.hasEntry(newName) : "sensorManager non contiene " + newName;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public boolean hasSensor(String name) {
		assert name != null && name.length() > 0;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return sensorManager.hasEntry(name);
	}
	
	public String [] getSensorsNames() {
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return sensorManager.namesList();
	}
	
	public void addArtifact(Artifact toAdd) {
		assert toAdd != null;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		int pre_size = artifactManager.size();
		
		artifactManager.addEntry(toAdd);
		
		assert artifactManager.size() >= pre_size;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void removeArtifact(String toRemove) {
		assert toRemove != null && toRemove.length() > 0;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		assert artifactManager.hasEntry(toRemove) : "artifactManager non contiene " + toRemove +" e dunque non può rimuoverlo";
		int pre_size = artifactManager.size();
		
		artifactManager.remove(toRemove);
		
		assert artifactManager.size() <= pre_size;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public Artifact getArtifactByName(String toGet) {
		assert toGet != null && toGet.length() > 0;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		
		Artifact art = (Artifact)artifactManager.getElementByName(toGet);
		
		assert art != null;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		
		return art;
	}
	
	public void changeArtifactName(String oldName, String newName) {
		assert oldName != null && oldName.length() > 0;
		assert newName != null && newName.length() > 0;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		assert artifactManager.hasEntry(oldName) : "artifactManager non contiene "+ oldName;
		int pre_size = artifactManager.size();
		
		artifactManager.changeElementName(oldName, newName);
		
		assert artifactManager.size() == pre_size;
		assert artifactManager.hasEntry(newName) : "artifactManager non contiene "+ newName;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public boolean hasArtifact(String name) {
		assert name != null && name.length() > 0;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return artifactManager.hasEntry(name);
	}
	
	public String [] getArtifactsNames() {
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return artifactManager.namesList();
	}
	
	public boolean hasNumericProperty(String variableName) {
		assert variableName != null;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return numericPropertiesMap.containsKey(variableName);
	}
	
	public double getNumericProperty(String variableName) {
		assert variableName != null;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return numericPropertiesMap.get(variableName);
	}
	
	public boolean hasNonNumericProperty(String variableName) {
		assert variableName != null;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return nonNumericPropertiesMap.containsKey(variableName);
	}
	
	public String getNonNumericProperty(String variableName) {
		assert variableName != null;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		return nonNumericPropertiesMap.get(variableName);
	}
	
	public void setNumericProperty (String variableName, double newValue) {
		assert variableName != null;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		int pre_size = numericPropertiesMap.size();
		
		numericPropertiesMap.put(variableName, newValue);
		
		assert numericPropertiesMap.size() >= pre_size;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void setNonNumericProperty (String variableName, String newValue) {
		assert variableName != null;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		int pre_size = nonNumericPropertiesMap.size();
		
		nonNumericPropertiesMap.put(variableName, newValue);
		
		assert nonNumericPropertiesMap.size() >= pre_size;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public String toString() {
		assert roomInvariant() : "Invariante della classe non soddisfatto";
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
		String result =  name+':'+text+':'+compactNames;
		
		assert result.length() > 0 && result != null;
		assert roomInvariant() : "Invariante della classe non soddisfatto";
		
		return result;
		
	}
	
	private boolean roomInvariant() {
		boolean checkName = name != null && name.length() > 0;
		boolean checkText = text != null;
		boolean checkManagers = sensorManager != null && actuatorManager != null && artifactManager != null;
		boolean checkMaps = numericPropertiesMap != null && nonNumericPropertiesMap != null;
		
		if(checkName && checkText && checkManagers && checkMaps) return true;
		return false;
	}
}
