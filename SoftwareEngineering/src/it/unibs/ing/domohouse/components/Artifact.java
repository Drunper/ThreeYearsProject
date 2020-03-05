package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

import it.unibs.ing.domohouse.interfaces.Gettable;

public class Artifact implements Gettable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 643544508558962880L;
	private String name;
	private String text;
	private ArrayList<Actuator> controllerActuators;
	private TreeMap<String, Double> numericPropertiesMap; //treemap per contenere i valori numerici tipo temperatura
	private TreeMap<String, String> nonNumericPropertiesMap; //treemap per contenere i valori string tipo "presenza persone"
	
	public Artifact(String name, String text) {
		this.name = name;
		this.text = text;
		this.controllerActuators = new ArrayList<>();
		numericPropertiesMap = new TreeMap<>();
		nonNumericPropertiesMap = new TreeMap<>();
	}

	public String getName() {
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
		return this.name;
	}
	
	public String getDescr() {
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
		return this.text;
	}
	
	public void addControllerActuator(Actuator actuator) {
		controllerActuators.add(actuator);
	}
	
	public String [] getControllerActuatorsNames() {
		String [] result = new String[controllerActuators.size()];
		
		for(int i = 0; i < controllerActuators.size(); i++) {
			result[i] = controllerActuators.get(i).getName();
		}
		
		return result;
	}
	
	public Actuator getActuatorByName(String selectedActuator) {
		Actuator result = null;
		for(int i = 0; i < controllerActuators.size(); i++) {
			if(controllerActuators.get(i).getName().equals(selectedActuator)) result = controllerActuators.get(i);
		}
		return result;
	}
	
	public void setName(String newName) {
		assert newName.length() > 0 && newName != null;
		this.name = newName;
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void setDescr(String text) {
		assert text != null;
		this.text = text;
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public double getNumericProperty(String variableName) {
		assert variableName != null;
		assert numericPropertiesMap != null;
		assert numericPropertiesMap.containsKey(variableName) : "numericPropertiesMap non contiene " + variableName;
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
		return numericPropertiesMap.get(variableName);
	}
	
	public String getNonNumericProperty(String variableName) {
		assert variableName.length() > 0 && variableName != null;
		assert nonNumericPropertiesMap != null;
		assert nonNumericPropertiesMap.containsKey(variableName) : "nonNumericPropertiesMap non contiene " + variableName;
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
		return nonNumericPropertiesMap.get(variableName);
	}
	
	public void setNumericProperty (String variableName, double newValue) {
		assert variableName.length() > 0 && variableName != null;
		assert numericPropertiesMap != null;
		int pre_size = numericPropertiesMap.size();
		
		numericPropertiesMap.put(variableName, newValue);
		
		assert numericPropertiesMap.size() >= pre_size;
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
		
	}
	
	public void setNonNumericProperty (String variableName, String newValue) {
		assert variableName.length() > 0 && variableName != null;
		assert nonNumericPropertiesMap != null;
		int pre_size = nonNumericPropertiesMap.size();
		
		nonNumericPropertiesMap.put(variableName, newValue);
		
		assert nonNumericPropertiesMap.size() >= pre_size;
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public boolean hasNumericProperty(String variableName) {
		assert variableName != null;
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
		return numericPropertiesMap.containsKey(variableName);
	}
	
	public boolean hasNonNumericProperty(String variableName) {
		assert variableName != null;
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
		return nonNumericPropertiesMap.containsKey(variableName);
	}
	
	public String toString() {
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
		
		String unformattedText;
		unformattedText = name+':'+text;
		
		assert unformattedText.length() > 0;
		assert artifactInvariant() : "Invariante della classe non soddisfatto";
		return unformattedText;
	}
	
	private boolean artifactInvariant() {
		boolean checkName = false;
		boolean checkTest = false;
		boolean checkMap = false;
		if(name.length() > 0 && name != null) checkName = true;
		if(text != null) checkTest = true;
		if(numericPropertiesMap != null && nonNumericPropertiesMap != null) checkMap = true; 
		
		if(checkName && checkTest && checkMap) return true;
		return false;
	}
}
