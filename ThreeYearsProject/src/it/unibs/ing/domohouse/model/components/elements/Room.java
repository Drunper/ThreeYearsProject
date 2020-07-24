package it.unibs.ing.domohouse.model.components.elements;

import java.io.Serializable;
import java.util.*;

import it.unibs.ing.domohouse.model.util.Manager;
import it.unibs.ing.domohouse.model.ModelStrings;
import it.unibs.ing.domohouse.model.db.persistent.PersistentObject;

public class Room implements Gettable, Serializable {

	private static final long serialVersionUID = -2234559747971441506L;
	private String name;
	private String descr;
	private Manager sensorManager;
	private Manager actuatorManager;
	private Manager artifactManager;
	private Map<String, String> propertiesMap;
	private PersistentObject saveable;

	public Room(String name, String descr, Map<String, String> propertiesMap) {
		this.name = name;
		this.descr = descr;
		sensorManager = new Manager();
		actuatorManager = new Manager();
		artifactManager = new Manager();
		this.propertiesMap = propertiesMap;

	}
	
	public void setSaveable(PersistentObject saveable) {
		this.saveable = saveable;
	}
	
	public void modify() {
		saveable.modify();
	}
	
	public void delete() {
		saveable.delete();
	}

	@Override
	public String getName() {
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.name;
	}

	public String getDescr() {
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.descr;
	}

	@Override
	public void setName(String roomName) {
		assert roomName != null && roomName.length() > 0;

		this.name = roomName;

		assert this.name != null && this.name.length() > 0;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void setDescr(String descr) {
		assert descr != null;

		this.descr = descr;

		assert this.descr != null;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addActuator(Actuator toAdd) {
		assert toAdd != null;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = actuatorManager.size();

		actuatorManager.addElement(toAdd);

		assert actuatorManager.size() >= pre_size;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Actuator getActuatorByName(String toGet) {
		assert toGet != null && toGet.length() > 0;
		boolean prec = actuatorManager.hasElement(toGet);
		assert prec : "actuatorManager non contiene " + toGet;
		int pre_size = actuatorManager.size();

		Actuator act = (Actuator) actuatorManager.getElement(toGet);

		assert act != null;
		assert actuatorManager.size() == pre_size;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		return act;
	}

	public boolean hasActuator(String name) {
		assert name != null && name.length() > 0;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		return actuatorManager.hasElement(name);
	}

	public boolean doesActuatorExist() {
		return !actuatorManager.isEmpty();
	}

	public Set<String> getActuatorsNames() {
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		return actuatorManager.getSetOfElements();
	}

	public void addSensor(Sensor toAdd) {
		assert toAdd != null;
		int pre_size = sensorManager.size();

		sensorManager.addElement(toAdd);

		assert sensorManager.size() >= pre_size;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Sensor getSensorByName(String toGet) {
		assert toGet != null && toGet.length() > 0;
		assert sensorManager.hasElement(toGet) : "sensorManager non contiene " + toGet;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;

		Sensor s = (Sensor) sensorManager.getElement(toGet);

		assert s != null;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		return s;
	}

	public boolean hasSensor(String name) {
		assert name != null && name.length() > 0;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		return sensorManager.hasElement(name);
	}

	public boolean doesSensorExist() {
		return !sensorManager.isEmpty();
	}

	public Set<String> getSensorsNames() {
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		return sensorManager.getSetOfElements();
	}

	public void addArtifact(Artifact toAdd) {
		assert toAdd != null;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = artifactManager.size();

		artifactManager.addElement(toAdd);

		assert artifactManager.size() >= pre_size;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasArtifact(String name) {
		assert name != null && name.length() > 0;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		return artifactManager.hasElement(name);
	}

	public boolean doesArtifactExist() {
		return !artifactManager.isEmpty();
	}

	public Set<String> getArtifactsNames() {
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		return artifactManager.getSetOfElements();
	}

	@Override
	public boolean hasProperty(String variableName) {
		return propertiesMap.containsKey(variableName);
	}
	
	@Override
	public boolean doesPropertyExist() {
		return !propertiesMap.isEmpty();
	}

	@Override
	public String getProperty(String variableName) {
		return propertiesMap.get(variableName);
	}

	@Override
	public void setProperty(String variableName, String newValue) {
		assert variableName != null;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = propertiesMap.size();

		propertiesMap.put(variableName, newValue);

		assert propertiesMap.size() >= pre_size;
		assert roomInvariant() : ModelStrings.WRONG_INVARIANT;
	}
	
	@Override
	public Set<String> getPropertiesNameSet() {
		return propertiesMap.keySet();
	}

	private boolean roomInvariant() {
		boolean checkName = name != null && name.length() > 0;
		boolean checkDescr = descr != null;
		boolean checkManagers = sensorManager != null && actuatorManager != null && artifactManager != null;
		boolean checkMaps = propertiesMap != null;

		return checkName && checkDescr && checkManagers && checkMaps;
	}

	public void removeArtifact(String selectedArtifact) {
		artifactManager.removeElement(selectedArtifact);
	}

	public void removeActuator(String selectedActuator) {
		actuatorManager.removeElement(selectedActuator);
	}

	public void removeSensor(String selectedSensor) {
		sensorManager.removeElement(selectedSensor);
	}
}
