package it.unibs.ing.softwareengineering;

public class ActuatorCategory {

	private String name;
	private String text;
	private String [] operatingModes;
	
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
}
