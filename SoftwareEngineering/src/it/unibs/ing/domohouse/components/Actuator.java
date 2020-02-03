package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.function.Consumer;

import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.interfaces.Manageable;
import it.unibs.ing.domohouse.util.Manager;

public class Actuator implements Manageable, Serializable {

	private String name;
	private Manager controlledObjects;
	private ActuatorCategory category;
	private String operatingMode;
	private String defaultMode;
	private boolean state;

	public Actuator(String name, ActuatorCategory category) {
		super();
		this.name = name;
		this.category = category;
		defaultMode = category.getDefaultMode();
		operatingMode = defaultMode;
		controlledObjects = new Manager();
		state = true;
	}
	
	public String getName() {
		return name.split("_")[0];
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addEntry(Manageable toAdd) {
		controlledObjects.addEntry(toAdd);
	}

	public void setOperatingMode(String operatingMode) {
		this.operatingMode = operatingMode;
		Consumer<Gettable> mode = category.getOperatingMode(operatingMode);
		for(String object : controlledObjects.namesList())
		{
			mode.accept((Gettable)controlledObjects.getElementByName(object));
		}
		operatingMode = defaultMode;
	}
	
	public String toString() {
		String unformattedText;
		String status;
		if (state) 
			status = "acceso";
		else
			status = "spento";
		unformattedText = name+':'+category.getName()+':'+String.join(":", controlledObjects.namesList())+':'+operatingMode+':'+status;
		return unformattedText;
	}
}
