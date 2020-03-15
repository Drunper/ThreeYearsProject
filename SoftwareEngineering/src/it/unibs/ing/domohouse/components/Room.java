package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.*;

import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.util.Manager;
import it.unibs.ing.domohouse.util.Strings;

public class Room implements Gettable, Serializable {

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
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return this.name;
	}
	
	public String getDescr() {
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return this.text;
	}
	
	public void setName(String roomName) {
		assert roomName != null && roomName.length() > 0;
		
		this.name = roomName;
		
		assert this.name != null && this.name.length() > 0;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}

	public void setDescr(String text) {
		assert text != null;
		
		this.text = text;
		
		assert this.text != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void addActuator(Actuator toAdd) {
		assert toAdd != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = actuatorManager.size();
		
		actuatorManager.addElement(toAdd);
		
		assert actuatorManager.size() >= pre_size;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void removeActuator(String toRemove) {
		assert toRemove != null && toRemove.length() > 0;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		boolean prec = actuatorManager.hasElement(toRemove);
		assert prec : "actuatorManger non contiene " + toRemove + " e dunque non può rimuoverlo";
		int pre_size = actuatorManager.size();
		
		actuatorManager.removeElement(toRemove);
		
		assert actuatorManager.size() <= pre_size;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public Actuator getActuatorByName(String toGet) {
		assert toGet != null && toGet.length() > 0;
		boolean prec = actuatorManager.hasElement(toGet);
		assert prec : "actuatorManager non contiente " + toGet;
		int pre_size = actuatorManager.size();
		
		Actuator act = (Actuator)actuatorManager.getElementByName(toGet);
	
		assert act != null;
		assert actuatorManager.size() == pre_size;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return act;
	}
	
	public void changeActuatorName(String oldName, String newName) {
		assert oldName != null && oldName.length() > 0;
		assert newName != null && newName.length() > 0;
		assert actuatorManager.hasElement(oldName) : "actuatorManager non contiene " + oldName;
		int pre_size = actuatorManager.size();
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		
		actuatorManager.changeElementName(oldName, newName);
		
		assert actuatorManager.size() == pre_size;
		assert actuatorManager.hasElement(newName) : "actuatorManager non contiene " + newName;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public boolean hasActuator(String name) {
		assert name != null && name.length() > 0; 
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return actuatorManager.hasElement(name);
	}
	
	public String [] getActuatorsNames() {
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return actuatorManager.getListOfElements();
	}
	
	public void addSensor(Sensor toAdd) {
		assert toAdd != null; 
		int pre_size = sensorManager.size();
		
		sensorManager.addElement(toAdd);
		
		assert sensorManager.size() >= pre_size;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void removeSensor(String toRemove) {
		assert toRemove != null && toRemove.length() > 0;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = sensorManager.size();
		
		sensorManager.removeElement(toRemove);
		
		assert sensorManager.size() <= pre_size;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public Sensor getSensorByName(String toGet) {
		assert toGet != null && toGet.length() > 0; 
		assert sensorManager.hasElement(toGet) : "sensorManager non contiene " + toGet;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		
		Sensor s = (Sensor)sensorManager.getElementByName(toGet);
		
		assert s != null; 
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return s;
	}
	
	public void changeSensorName(String oldName, String newName) {
		assert oldName != null && oldName.length() > 0;
		assert newName != null && newName.length() > 0;
		assert sensorManager.hasElement(oldName) : "sensorManager non contiene " + oldName;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = sensorManager.size();
		
		sensorManager.changeElementName(oldName, newName);
		
		assert sensorManager.size() == pre_size;
		assert sensorManager.hasElement(newName) : "sensorManager non contiene " + newName;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public boolean hasSensor(String name) {
		assert name != null && name.length() > 0;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return sensorManager.hasElement(name);
	}
	
	public String [] getSensorsNames() {
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return sensorManager.getListOfElements();
	}
	
	public void addArtifact(Artifact toAdd) {
		assert toAdd != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = artifactManager.size();
		
		artifactManager.addElement(toAdd);
		
		assert artifactManager.size() >= pre_size;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void removeArtifact(String toRemove) {
		assert toRemove != null && toRemove.length() > 0;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		assert artifactManager.hasElement(toRemove) : "artifactManager non contiene " + toRemove +" e dunque non può rimuoverlo";
		int pre_size = artifactManager.size();
		
		artifactManager.removeElement(toRemove);
		
		assert artifactManager.size() <= pre_size;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public Artifact getArtifactByName(String toGet) {
		assert toGet != null && toGet.length() > 0;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		
		Artifact art = (Artifact)artifactManager.getElementByName(toGet);
		
		assert art != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return art;
	}
	
	public void changeArtifactName(String oldName, String newName) {
		assert oldName != null && oldName.length() > 0;
		assert newName != null && newName.length() > 0;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		assert artifactManager.hasElement(oldName) : "artifactManager non contiene "+ oldName;
		int pre_size = artifactManager.size();
		
		artifactManager.changeElementName(oldName, newName);
		
		assert artifactManager.size() == pre_size;
		assert artifactManager.hasElement(newName) : "artifactManager non contiene "+ newName;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public boolean hasArtifact(String name) {
		assert name != null && name.length() > 0;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return artifactManager.hasElement(name);
	}
	
	public String [] getArtifactsNames() {
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return artifactManager.getListOfElements();
	}
	
	public boolean hasNumericProperty(String variableName) {
		assert variableName != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return numericPropertiesMap.containsKey(variableName);
	}
	
	public double getNumericProperty(String variableName) {
		assert variableName != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return numericPropertiesMap.get(variableName);
	}
	
	public boolean hasNonNumericProperty(String variableName) {
		assert variableName != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return nonNumericPropertiesMap.containsKey(variableName);
	}
	
	public String getNonNumericProperty(String variableName) {
		assert variableName != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return nonNumericPropertiesMap.get(variableName);
	}
	
	public void setNumericProperty (String variableName, double newValue) {
		assert variableName != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = numericPropertiesMap.size();
		
		numericPropertiesMap.put(variableName, newValue);
		
		assert numericPropertiesMap.size() >= pre_size;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void setNonNumericProperty (String variableName, String newValue) {
		assert variableName != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = nonNumericPropertiesMap.size();
		
		nonNumericPropertiesMap.put(variableName, newValue);
		
		assert nonNumericPropertiesMap.size() >= pre_size;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public String toString() {
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		String [] sensorNames = getSensorsNames();
		String [] actuatorNames = getActuatorsNames();
		String [] artifactNames = getArtifactsNames();
		String [] elementNames = new String[sensorNames.length+actuatorNames.length+artifactNames.length];
		int i;
		for(i = 0; i < sensorNames.length; i++)
			elementNames[i] = sensorNames[i] + " - Sensore";
		
		int j = i + actuatorNames.length;
		for(i = sensorNames.length; i < j; i++)
			elementNames[i] = actuatorNames[i-sensorNames.length] + " - Attuatore";
		
		int k = j + artifactNames.length;
		for(i = j; i < k; i++)
			elementNames[i] = artifactNames[i-j] + " - Artefatto";
		String compactNames = String.join(Strings.SEPARATOR, elementNames);
		String result =  name+Strings.SEPARATOR+text+Strings.SEPARATOR+compactNames;
		
		assert result.length() > 0 && result != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}
	
	public String [] getEnabledSensors() {
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		
		ArrayList<String> enabledSens = new ArrayList<>();
		for(String s : sensorManager.getListOfElements()) {
			Sensor sens = (Sensor) sensorManager.getElementByName(s);
			if(sens.isState()) 
				enabledSens.add(s);
		}
		
		String [] result = new String [enabledSens.size()];
		
		for(int i = 0; i < enabledSens.size(); i++)
			result[i] = enabledSens.get(i);
		
		assert result != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}
	
	public String [] getDisabledSensors() {
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		
		ArrayList<String> disabledSens = new ArrayList<>();
		for(String s : sensorManager.getListOfElements()) {
			Sensor sens = (Sensor) sensorManager.getElementByName(s);
			if(!sens.isState()) 
				disabledSens.add(s);
		}
		
		String [] result = new String [disabledSens.size()];
		
		for(int i = 0; i < disabledSens.size(); i++)
			result[i] = disabledSens.get(i);
		
		assert result != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}
	
	public String [] getEnabledActuators() {
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		
		ArrayList<String> enabledActuator = new ArrayList<>();
		for(String s : actuatorManager.getListOfElements()) {
			Actuator act = (Actuator) actuatorManager.getElementByName(s);
			if(act.isState()) 
				enabledActuator.add(s);
		}
		
		String [] result = new String [enabledActuator.size()];
		
		for(int i = 0; i < enabledActuator.size(); i++)
			result[i] = enabledActuator.get(i);
		
		assert result != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}
	
	public String [] getDisabledActuators() {
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		
		ArrayList<String> disabledActuators = new ArrayList<>();
		for(String s : actuatorManager.getListOfElements()) {
			Actuator act = (Actuator) actuatorManager.getElementByName(s);
			if(!act.isState()) 
				disabledActuators.add(s);
		}
		
		String [] result = new String [disabledActuators.size()];
		
		for(int i = 0; i < disabledActuators.size(); i++)
			result[i] = disabledActuators.get(i);
		
		assert result != null;
		assert roomInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}
	
	private boolean roomInvariant() {
		boolean checkName = name != null && name.length() > 0;
		boolean checkText = text != null;
		boolean checkManagers = sensorManager != null && actuatorManager != null && artifactManager != null;
		boolean checkMaps = numericPropertiesMap != null && nonNumericPropertiesMap != null;
		
		return checkName && checkText && checkManagers && checkMaps;
	}
}
