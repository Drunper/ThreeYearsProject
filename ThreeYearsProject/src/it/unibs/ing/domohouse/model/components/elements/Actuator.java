package it.unibs.ing.domohouse.model.components.elements;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import it.unibs.ing.domohouse.model.components.properties.ActiveState;
import it.unibs.ing.domohouse.model.components.properties.ActuatorCategory;
import it.unibs.ing.domohouse.model.components.properties.OperatingMode;
import it.unibs.ing.domohouse.model.components.properties.State;
import it.unibs.ing.domohouse.model.db.Saveable;
import it.unibs.ing.domohouse.model.util.Manager;
import it.unibs.ing.domohouse.model.ModelStrings;

public class Actuator implements Manageable, Serializable, Stateable {

	private static final long serialVersionUID = 8133324316964731994L;
	private String name;
	private Manager controlledObjects;
	private ActuatorCategory category;
	private String operatingMode;
	private String defaultMode;
	private State state;
	private Saveable saveable;
	private boolean controllingRoom;
	private boolean performing;

	/*
	 * invariante: controlledObjects size > 0, name != null
	 */
	public Actuator(String name, ActuatorCategory category) {
		super();
		this.name = name;
		this.category = category;
		defaultMode = category.getDefaultMode();
		operatingMode = defaultMode;
		controlledObjects = new Manager();
		state = new ActiveState();
		performing = false;
	}

	public String getName() {
		assert actuatorInvariant() : ModelStrings.WRONG_INVARIANT;
		return name;
	}

	// info per sapere se l'attuatore sta attuando una modalità operativa
	public boolean isPerforming() {
		assert actuatorInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.performing;
	}

	public void setName(String newName) {
		assert newName.length() > 0 : ModelStrings.ACTUATOR_NAME_PRECONDITION;
		this.name = newName;
		assert name.length() > 0 && name != null;
		assert actuatorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return this.state;
	}

	public void trigger() {
		state.trigger(this);
	}
	
	public void setSaveable(Saveable saveable) {
		this.saveable = saveable;
	}
	
	public void modify() {
		saveable.modify();
	}
	
	public void delete() {
		saveable.delete();
	}

	public ActuatorCategory getCategory() {
		assert actuatorInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.category;
	}

	public Set<String> getControlledObjectsSet() {
		return controlledObjects.getListOfElements();
	}

	// Fatto per chiarezza, solo un artefatto o una stanza può essere controllato
	// dall'attuatore
	public void addEntry(Gettable toAdd) {
		assert toAdd != null : ModelStrings.TO_ADD_PRECONDITION;
		int size = controlledObjects.size();
		controlledObjects.addElement(toAdd);
		assert controlledObjects.size() == size + 1;
		assert actuatorInvariant() : ModelStrings.WRONG_INVARIANT;
	}
	
	public boolean isControllingRoom() {
		assert actuatorInvariant() : ModelStrings.WRONG_INVARIANT;
		return controllingRoom;
	}

	public void setControllingRoom(boolean controllingRoom) {
		this.controllingRoom = controllingRoom;
		assert actuatorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public String getOperatingMode() {
		return operatingMode;
	}
	
	public Set<String> getOperatingModes() {
		return category.getOperatingModesSet();
	}

	public void setOperatingMode(String operatingMode, List<String> parameters) {
		assert category != null && category.getOperatingMode(operatingMode) != null;
		this.operatingMode = operatingMode;
		performing = true;
		OperatingMode mode = category.getOperatingMode(operatingMode);
		for (String parameter : parameters)
			mode.setParamaterValue(parameter);
		for (String object : getControlledObjectsSet())
			mode.operate((Gettable) controlledObjects.getElementByName(object));
		performing = false;
		assert actuatorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	private boolean actuatorInvariant() {
		return controlledObjects != null && name != null && name.length() > 0;
	}

}
