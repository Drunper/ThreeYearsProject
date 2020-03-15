package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.util.Strings;

public class Artifact implements Gettable, Serializable {

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
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		return this.name;
	}
	
	public String getDescr() {
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		return this.text;
	}
	
	public void addControllerActuator(Actuator actuator) {
		assert actuator != null;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = controllerActuators.size();
		
		controllerActuators.add(actuator);
		
		assert controllerActuators.size() >= pre_size;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public String [] getControllerActuatorsNames() {
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		
		String [] result = new String[controllerActuators.size()];
		
		for(int i = 0; i < controllerActuators.size(); i++) {
			result[i] = controllerActuators.get(i).getName();
		}
		
		assert result != null;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}
	
	public Actuator getActuatorByName(String selectedActuator) {
		assert selectedActuator != null;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		
		Actuator result = null;
		for(int i = 0; i < controllerActuators.size(); i++) {
			if(controllerActuators.get(i).getName().equals(selectedActuator)) result = controllerActuators.get(i);
		}
		
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}
	
	public void setName(String newName) {
		assert newName.length() > 0 && newName != null;
		this.name = newName;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void setDescr(String text) {
		assert text != null;
		this.text = text;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public double getNumericProperty(String variableName) {
		assert variableName != null;
		assert numericPropertiesMap != null;
		assert numericPropertiesMap.containsKey(variableName) : Strings.NUMERIC_PROPERTIES_PRECONDITION + variableName;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		return numericPropertiesMap.get(variableName);
	}
	
	public String getNonNumericProperty(String variableName) {
		assert variableName.length() > 0 && variableName != null;
		assert nonNumericPropertiesMap != null;
		assert nonNumericPropertiesMap.containsKey(variableName) : Strings.NON_NUMERIC_PROPERTIES_PRECONDITION + variableName;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		return nonNumericPropertiesMap.get(variableName);
	}
	
	public void setNumericProperty (String variableName, double newValue) {
		assert variableName.length() > 0 && variableName != null;
		assert numericPropertiesMap != null;
		int pre_size = numericPropertiesMap.size();
		
		numericPropertiesMap.put(variableName, newValue);
		
		assert numericPropertiesMap.size() >= pre_size;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		
	}
	
	public void setNonNumericProperty (String variableName, String newValue) {
		assert variableName.length() > 0 && variableName != null;
		assert nonNumericPropertiesMap != null;
		int pre_size = nonNumericPropertiesMap.size();
		
		nonNumericPropertiesMap.put(variableName, newValue);
		
		assert nonNumericPropertiesMap.size() >= pre_size;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public boolean hasNumericProperty(String variableName) {
		assert variableName != null;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		return numericPropertiesMap.containsKey(variableName);
	}
	
	public boolean hasNonNumericProperty(String variableName) {
		assert variableName != null;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		return nonNumericPropertiesMap.containsKey(variableName);
	}
	
	public String toString() {
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		
		String unformattedText;
		unformattedText = name+Strings.SEPARATOR+text;
		
		assert unformattedText.length() > 0;
		assert artifactInvariant() : Strings.WRONG_INVARIANT;
		return unformattedText;
	}
	
	private boolean artifactInvariant() {
		boolean checkName = false;
		boolean checkText = false;
		boolean checkMap = false;
		if(name.length() > 0 && name != null) checkName = true;
		if(text != null) checkText = true;
		if(numericPropertiesMap != null && nonNumericPropertiesMap != null && controllerActuators != null) checkMap = true; 
		
		return checkName && checkText && checkMap;
	}
}
