package it.unibs.ing.softwareengineering;

public class NumericActuator extends Actuator {
	
	private String name;
	private String text;
	private ActuatorCategory category;
	private String currentState;
	private Artifact controlledArtif; 
	private boolean state;
	
	public NumericActuator(String name, String text, ActuatorCategory category, Artifact artif) {
		super();
		this.name = name;
		this.text = text;
		this.category = category;
		this.controlledArtif = artif;
		state = true;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDescr() {
		return this.text;
	}
	
	public void setDescr(String text) {
		this.text = text;
	}
	
	@Override
	public ActuatorCategory getCategory() {
		return this.category;
	}
	
	public Artifact getControlledArtifact() {
		return this.controlledArtif;
	}
	
	public String getState() {
		return this.currentState;
	}
	
	public boolean isOn() {
		return this.state;
	}
	
}
