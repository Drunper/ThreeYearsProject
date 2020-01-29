package it.unibs.ing.softwareengineering;

import java.util.ArrayList;

public class ActuatorCategory implements Manageable {

	private String name;
	private String text;
	private ArrayList<String> operatingModes;
	
	public ActuatorCategory(String name, String text) {
		this.name = name;
		this.text = text;
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
	
	public String toString() {
		return name+':'+text+':'+String.join(":", operatingModes.toArray(new String[0]));
	}
}
