package it.unibs.ing.softwareengineering;

public class Actuator implements Manageable {

	private String name;
	private Artifact controlledArtif;
	private ActuatorCategory category;
	private String operatingMode;
	private boolean state;

	public Actuator(String name, ActuatorCategory category) {
		super();
		this.name = name;
		this.category = category;
		operatingMode = "idle";
		state = true;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		String unformattedText;
		String status;
		if (state) 
			status = "acceso";
		else
			status = "spento";
		unformattedText = name+':'+category.getName()+':'+controlledArtif.getName()+':'+operatingMode+':'+status;
		return unformattedText;
	}
}
