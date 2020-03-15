package it.unibs.ing.domohouse.components;

import java.io.Serializable;

import it.unibs.ing.domohouse.interfaces.Manageable;
import it.unibs.ing.domohouse.util.Strings;

public class SensorCategory implements Manageable, Serializable{
	private static final long serialVersionUID = 3198680539714650299L;
	private String name;
	private String text;
	private boolean isNumeric;
	
	public SensorCategory(String name, String text) {
		this.name = name;
		this.text = text;
	}
	
	public String getName() {
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		return this.name;
	}
	
	public String getDescr() {
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		return this.text;
	}
	
	public void setName(String name) {
		assert name != null;
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		this.name = name;
	}
	
	public void setDescr(String text) {
		assert text != null;
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		this.text = text;
	}
	
	public void setIsNumeric(boolean flag) {
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		this.isNumeric = flag;
	}
	
	public boolean getIsNumeric() {
		assert sensorCategoryInvariant() : Strings.WRONG_INVARIANT;
		return this.isNumeric;
	}
	
	private boolean sensorCategoryInvariant() {
		boolean checkName = name != null;
		boolean checkText = text != null;
		
		return checkName && checkText;
	}
}
