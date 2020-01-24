package it.unibs.ing.softwareengineering;

public class Artifact {
	
	private String name;
	private String text;
	
	public Artifact(String name, String text) {
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
	
	public void setDesc(String text) {
		this.text = text;
	}
	
	public double getMeasure(String measuredVariable) {
		return 0;
	}

}
