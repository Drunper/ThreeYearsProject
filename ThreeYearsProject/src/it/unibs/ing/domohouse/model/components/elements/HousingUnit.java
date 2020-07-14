package it.unibs.ing.domohouse.model.components.elements;

import java.io.Serializable;
import java.util.*;

import it.unibs.ing.domohouse.model.components.properties.ActiveState;
import it.unibs.ing.domohouse.model.components.properties.InactiveState;
import it.unibs.ing.domohouse.model.components.properties.State;
import it.unibs.ing.domohouse.model.components.rule.Rule;
import it.unibs.ing.domohouse.model.util.Association;
import it.unibs.ing.domohouse.model.util.AssociationManager;
import it.unibs.ing.domohouse.model.util.Manager;
import it.unibs.ing.domohouse.model.ModelStrings;

public class HousingUnit implements Serializable, Manageable {

	private static final long serialVersionUID = -4272512019548783815L;
	private String name;
	private String descr;
	private Manager roomManager;
	private Manager rulesManager;
	private Manager sensorManager; // tutti i sensori della casa
	private Manager actuatorManager; // tutti gli attuatori della casa
	private Manager artifactManager; // tutti gli artefatti della casa
	private AssociationManager associationManager; // per il controllo delle associazioni

	/*
	 * invariante name > 0, descr > 0, diversi da null. Manager != null
	 */
	public HousingUnit(String name, String descr) {
		this.name = name;
		this.descr = descr;
		roomManager = new Manager();
		sensorManager = new Manager();
		actuatorManager = new Manager();
		artifactManager = new Manager();
		associationManager = new AssociationManager();
		rulesManager = new Manager();
	}

	public String getName() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return name;
	}

	public void setName(String name) {
		assert name != null && name.length() > 0;
		this.name = name;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public String getDescr() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return descr;
	}

	public void setDescr(String descr) {
		assert descr != null && descr.length() > 0;
		this.descr = descr;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addRoom(Room toAdd) {
		assert toAdd != null && toAdd.getName() != null && toAdd.getName().length() > 0;
		int pre_size_roomManager = roomManager.size();
		int pre_size_associationManager = associationManager.size();

		roomManager.addElement(toAdd);
		Association assoc = new Association(toAdd.getName());
		assoc.setRoomOrArtifact(true);
		associationManager.addAssociation(assoc);

		assert roomManager.size() >= pre_size_roomManager;
		assert associationManager.size() >= pre_size_associationManager;
		assert assoc.isElementARoom() == true && associationManager.hasAssociation(assoc.getElementName());
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Set<String> getRoomList() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return roomManager.getListOfElements();
	}

	public Room getRoom(String name) {
		assert name != null && (Room) roomManager.getElementByName(name) != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return (Room) roomManager.getElementByName(name);
	}

	public boolean hasRoom(String name) {
		assert name != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return roomManager.hasElement(name);
	}

	public Sensor getSensor(String selectedSensor) {
		assert selectedSensor != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		Sensor sens = (Sensor) sensorManager.getElementByName(selectedSensor);

		assert sens != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return sens;
	}

	public Actuator getActuator(String selectedActuator) {
		assert selectedActuator != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		Actuator act = (Actuator) actuatorManager.getElementByName(selectedActuator);

		assert act != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return act;
	}

	public Artifact getArtifact(String name) {
		assert name != null && name.length() > 0;
		Artifact art = (Artifact) artifactManager.getElementByName(name);
		assert art != null : ModelStrings.ARTIFACT_PRECONDITION;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return art;
	}

	public void setRoomDescription(String selectedRoom, String descr) {
		assert selectedRoom != null && selectedRoom.length() > 0 && descr != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		Room room = (Room) roomManager.getElementByName(selectedRoom);
		room.setDescr(descr);
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addSensor(String location, Sensor toAdd) {
		assert location != null && location.length() > 0;
		assert toAdd != null;
		int pre_size = sensorManager.size();
		Room room = (Room) roomManager.getElementByName(location);
		room.addSensor(toAdd);
		sensorManager.addElement(toAdd);
		assert sensorManager.size() >= pre_size;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasRoomOrArtifact(String name) {
		assert name != null && name.length() > 0;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return associationManager.hasAssociation(name);
	}

	public boolean hasSensor(String name) {
		assert name != null && name.length() > 0;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return sensorManager.hasElement(name);
	}

	public boolean hasActuator(String name) {
		assert name != null && name.length() > 0;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return actuatorManager.hasElement(name);
	}

	public boolean hasArtifact(String name) {
		assert name != null && name.length() > 0;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return artifactManager.hasElement(name);
	}

	public boolean isElementARoom(String toAssoc) {
		assert toAssoc != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return associationManager.isElementARoom(toAssoc);
	}

	public boolean isAssociatedWith(String toAssoc, String category) {
		assert toAssoc != null && category != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return associationManager.isElementAssociatedWith(toAssoc, category);
	}

	public void addAssociationWith(String object, String category) {
		assert object != null && category != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		associationManager.addAssociationBetween(object, category);
	}

	public void addArtifact(Artifact toAdd, String location) {
		assert toAdd != null && toAdd.getName() != null && toAdd.getName().length() > 0;
		assert location != null && location.length() > 0;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size_artifactManager = artifactManager.size();
		int pre_size_associationManager = artifactManager.size();

		Room room = (Room) roomManager.getElementByName(location);
		room.addArtifact(toAdd);
		artifactManager.addElement(toAdd);
		associationManager.addAssociation(new Association(toAdd.getName()));

		assert artifactManager.size() >= pre_size_artifactManager;
		assert associationManager.size() >= pre_size_associationManager;
		assert room.hasArtifact(toAdd.getName());
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addActuator(Actuator toAdd, String location) {
		assert toAdd != null && toAdd.getName() != null && toAdd.getName().length() > 0;
		assert location != null && location.length() > 0;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = actuatorManager.size();

		Room room = (Room) roomManager.getElementByName(location);
		room.addActuator(toAdd);
		actuatorManager.addElement(toAdd);

		assert actuatorManager.size() >= pre_size;
		assert room.hasActuator(toAdd.getName());
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}
	
	public Set<String> getActuatorOperatingModes(String selectedActuator) {
		return getActuator(selectedActuator).getOperatingModes();
	}

	public boolean doesRoomOrArtifactExist() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		return !roomManager.isEmpty() || !artifactManager.isEmpty();
	}

	public boolean doesRoomExist() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		return !roomManager.isEmpty();
	}

	public boolean doesArtifactExist() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		return !artifactManager.isEmpty();
	}

	public boolean doesArtifactExist(String selectedRoom) {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		Room room = (Room) roomManager.getElementByName(selectedRoom);
		return room.doesArtifactExist();
	}

	public boolean doesSensorExist(String selectedRoom) {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		Room room = (Room) roomManager.getElementByName(selectedRoom);
		return room.doesSensorExist();
	}

	public Set<String> getSensorNames() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return sensorManager.getListOfElements();
	}

	public Set<String> getActuatorNames() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return actuatorManager.getListOfElements();
	}

	public boolean doesActuatorExist(String selectedRoom) {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		Room room = (Room) roomManager.getElementByName(selectedRoom);
		return room.doesActuatorExist();
	}

	public String getCategoryOfASensor(String selectedSensor) {
		assert selectedSensor != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		Sensor sens = (Sensor) sensorManager.getElementByName(selectedSensor);

		assert sens != null;
		return sens.getCategory();
	}

	public void updateRulesState() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		State rule_state = new ActiveState();
		for (String ruleName : rulesManager.getListOfElements()) {
			Rule rule = (Rule) rulesManager.getElementByName(ruleName);
			for (String involved_sensor : rule.getInvolvedSensors()) {
				Sensor sens = getSensor(involved_sensor);
				if (!sens.getState().isActive())
					rule_state = new InactiveState();
			}

			for (String involved_actuator : rule.getInvolvedActuators()) {
				Actuator act = (Actuator) actuatorManager.getElementByName(involved_actuator);
				if (!act.getState().isActive())
					rule_state = new InactiveState();
			}
			rule.setState(rule_state);
		}
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasRule(String selectedRule) {
		return rulesManager.hasElement(selectedRule);
	}

	public void addRule(Rule toAdd) {
		rulesManager.addElement(toAdd);
	}

	public boolean doesRuleExist() {
		return !rulesManager.isEmpty();
	}

	public List<Rule> getEnabledRulesList() {
		List<Rule> enabledRulesList = new ArrayList<>();
		for (String ruleName : rulesManager.getListOfElements()) {
			Rule rule = (Rule) rulesManager.getElementByName(ruleName);
			if (rule.getState().isActive())
				enabledRulesList.add(rule);
		}
		return enabledRulesList;
	}

	public Set<String> getRulesNames() {
		return rulesManager.getListOfElements();
	}

	public List<String> getEnabledRulesNamesList() {
		List<String> enabledRulesNamesList = new ArrayList<>();
		for (String ruleName : rulesManager.getListOfElements()) {
			if (((Rule) rulesManager.getElementByName(ruleName)).getState().isActive())
				enabledRulesNamesList.add(ruleName);
		}
		return enabledRulesNamesList;
	}

	public List<String> getEnabledRulesStrings() {
		List<String> enabledRulesString = new ArrayList<>();
		for (String ruleName : rulesManager.getListOfElements()) {
			if (((Rule) rulesManager.getElementByName(ruleName)).getState().isActive())
				enabledRulesString.add(((Rule) rulesManager.getElementByName(ruleName)).toString());
		}
		return enabledRulesString;
	}

	public List<String> getRulesStrings() {
		List<String> enabledRulesString = new ArrayList<>();
		for (String ruleName : rulesManager.getListOfElements())
			enabledRulesString.add(((Rule) rulesManager.getElementByName(ruleName)).toString());
		return enabledRulesString;
	}

	public void changeRuleState(String selectedRule) {
		Rule rule = (Rule) rulesManager.getElementByName(selectedRule);
		rule.trigger();
	}

	private boolean housingUnitInvariant() {
		boolean checkName = name != null && name.length() > 0;
		boolean checkDescr = descr != null;
		boolean checkManagers = roomManager != null && sensorManager != null && actuatorManager != null
				&& artifactManager != null && associationManager != null;
		return checkName && checkDescr && checkManagers;
	}
}
