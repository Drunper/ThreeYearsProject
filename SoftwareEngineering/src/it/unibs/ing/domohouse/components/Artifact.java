package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.TreeMap;

import it.unibs.ing.domohouse.interfaces.Gettable;

public class Artifact implements Gettable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 643544508558962880L;
	private String name;
	private String text;
	private TreeMap<String, Double> numericPropertiesMap; //treemap per contenere i valori numerici tipo temperatura
	private TreeMap<String, String> nonNumericPropertiesMap; //treemap per contenere i valori string tipo "presenza persone"
	
	public Artifact(String name, String text) {
		this.name = name;
		this.text = text;
		numericPropertiesMap = new TreeMap<>();
		nonNumericPropertiesMap = new TreeMap<>();
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
	
	public boolean hasNumericProperty(String variableName) {
		return numericPropertiesMap.containsKey(variableName);
	}
	
	public boolean hasNonNumericProperty(String variableName) {
		return nonNumericPropertiesMap.containsKey(variableName);
	}
	
	public String toString() {
		String unformattedText;
		unformattedText = name+':'+text;
		return unformattedText;
	}
}
