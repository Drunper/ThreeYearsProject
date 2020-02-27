package it.unibs.ing.domohouse.components;

import java.io.Serializable;

import it.unibs.ing.domohouse.interfaces.Manageable;

public class SensorCategory implements Manageable, Serializable{
	private String name;
	private String text;
	private boolean isNumeric;
	
	public SensorCategory(String name, String text) {
		this.name = name;
		this.text = text;
	}
	public String getName() {
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return this.name;
	}
	
	public String getDescr() {
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return this.text;
	}
	
	public void setName(String name) {
		assert name != null;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		this.name = name;
	}
	
	public void setDescr(String text) {
		assert text != null;
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		this.text = text;
	}
	
	public void setIsNumeric(boolean flag) {
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		this.isNumeric = flag;
	}
	
	public boolean getIsNumeric() {
		assert sensorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return this.isNumeric;
	}
	
	
	private boolean sensorCategoryInvariant() {
		boolean checkName = name != null;
		boolean checkText = text != null;
		
		if(checkName && checkText) return true;
		return false;
	}
}
