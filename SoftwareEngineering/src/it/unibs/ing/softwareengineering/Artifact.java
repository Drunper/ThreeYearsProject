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
	
	public String getText() {
		return this.text;
	}
	
	public double getMeasure(String measuredVariable) {
		return 0;
	}

}
