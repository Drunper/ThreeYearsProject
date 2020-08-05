package it.unibs.ing.domohouse.model.components.elements;

import java.io.Serializable;
import java.util.*;

import it.unibs.ing.domohouse.model.components.properties.ActiveState;
import it.unibs.ing.domohouse.model.components.properties.InactiveState;
import it.unibs.ing.domohouse.model.components.properties.State;
import it.unibs.ing.domohouse.model.components.rule.Rule;
import it.unibs.ing.domohouse.model.db.persistent.PersistentObject;
import it.unibs.ing.domohouse.model.util.Association;
import it.unibs.ing.domohouse.model.util.AssociationManager;
import it.unibs.ing.domohouse.model.util.Manager;
import it.unibs.ing.domohouse.model.ModelStrings;

public class HousingUnit implements Serializable, Manageable {

	private static final long serialVersionUID = -4272512019548783815L;
	private String name;
	private String descr;
	private String type;
	private String user;
	private Manager roomManager;
	private Manager rulesManager;
	private Manager sensorManager; // tutti i sensori della casa
	private Manager actuatorManager; // tutti gli attuatori della casa
	private Manager artifactManager; // tutti gli artefatti della casa
	private AssociationManager associationManager; // per il controllo delle associazioni
	private PersistentObject saveable;

	/*
	 * invariante name > 0, descr > 0, diversi da null. Manager != null
	 */
	public HousingUnit(String name, String descr, String type, String user) {
		this.name = name;
		this.descr = descr;
		this.type = type;
		this.user = user;
		roomManager = new Manager();
		sensorManager = new Manager();
		actuatorManager = new Manager();
		artifactManager = new Manager();
		associationManager = new AssociationManager();
		rulesManager = new Manager();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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
		modify();
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addRoom(Room toAdd) {
		assert toAdd != null && toAdd.getName() != null && toAdd.getName().length() > 0;
		int pre_size_roomManager = roomManager.size();
		int pre_size_associationManager = associationManager.roomsSize();

		roomManager.addElement(toAdd);
		Association assoc = new Association(toAdd.getName());
		associationManager.addRoomAssociation(assoc);

		assert roomManager.size() >= pre_size_roomManager;
		assert associationManager.roomsSize() >= pre_size_associationManager;
		assert associationManager.hasRoomAssociation(assoc.getElementName());
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Set<String> getRoomSet() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return roomManager.getSetOfElements();
	}

	public Room getRoom(String name) {
		assert name != null && (Room) roomManager.getElement(name) != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return (Room) roomManager.getElement(name);
	}

	public boolean hasRoom(String name) {
		assert name != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return roomManager.hasElement(name);
	}

	public Sensor getSensor(String selectedSensor) {
		assert selectedSensor != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		Sensor sens = (Sensor) sensorManager.getElement(selectedSensor);

		assert sens != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return sens;
	}

	public Actuator getActuator(String selectedActuator) {
		assert selectedActuator != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		Actuator act = (Actuator) actuatorManager.getElement(selectedActuator);

		assert act != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return act;
	}

	public Artifact getArtifact(String name) {
		assert name != null && name.length() > 0;
		Artifact art = (Artifact) artifactManager.getElement(name);
		assert art != null : ModelStrings.ARTIFACT_PRECONDITION;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return art;
	}

	public void setRoomDescription(String selectedRoom, String descr) {
		assert selectedRoom != null && selectedRoom.length() > 0 && descr != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		Room room = (Room) roomManager.getElement(selectedRoom);
		room.setDescr(descr);
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addSensor(String location, Sensor toAdd) {
		assert location != null && location.length() > 0;
		assert toAdd != null;
		int pre_size = sensorManager.size();
		Room room = (Room) roomManager.getElement(location);
		room.addSensor(toAdd);
		sensorManager.addElement(toAdd);
		assert sensorManager.size() >= pre_size;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
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

	public boolean isArtifactAssociatedWith(String artifact, String category) {
		assert artifact != null && category != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return associationManager.isArtifactAssociated(artifact, category);
	}
	
	public boolean isRoomAssociatedWith(String room, String category) {
		assert room != null && category != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return associationManager.isRoomAssociated(room, category);
	}

	public void addArtifactAssociationWith(String artifact, String category) {
		assert artifact != null && category != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		associationManager.addArtifactAssociationWith(artifact, category);
	}
	
	public void addRoomAssociationWith(String room, String category) {
		assert room != null && category != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		associationManager.addRoomAssociationWith(room, category);
	}

	public void addArtifact(Artifact toAdd, String location) {
		assert toAdd != null && toAdd.getName() != null && toAdd.getName().length() > 0;
		assert location != null && location.length() > 0;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size_artifactManager = artifactManager.size();
		int pre_size_associationManager = associationManager.artifactsSize();

		Room room = (Room) roomManager.getElement(location);
		room.addArtifact(toAdd);
		artifactManager.addElement(toAdd);
		associationManager.addArtifactAssociation(new Association(toAdd.getName()));

		assert artifactManager.size() >= pre_size_artifactManager;
		assert associationManager.artifactsSize() >= pre_size_associationManager;
		assert room.hasArtifact(toAdd.getName());
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addActuator(Actuator toAdd, String location) {
		assert toAdd != null && toAdd.getName() != null && toAdd.getName().length() > 0;
		assert location != null && location.length() > 0;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = actuatorManager.size();

		Room room = (Room) roomManager.getElement(location);
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

		Room room = (Room) roomManager.getElement(selectedRoom);
		return room.doesArtifactExist();
	}

	public boolean doesSensorExist(String selectedRoom) {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		Room room = (Room) roomManager.getElement(selectedRoom);
		return room.doesSensorExist();
	}

	public Set<String> getSensorNames() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return sensorManager.getSetOfElements();
	}

	public Set<String> getActuatorNames() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;
		return actuatorManager.getSetOfElements();
	}

	public boolean doesActuatorExist(String selectedRoom) {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		Room room = (Room) roomManager.getElement(selectedRoom);
		return room.doesActuatorExist();
	}

	public String getCategoryOfASensor(String selectedSensor) {
		assert selectedSensor != null;
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		return getSensor(selectedSensor).getCategory();
	}

	public void updateRulesState() {
		assert housingUnitInvariant() : ModelStrings.WRONG_INVARIANT;

		State rule_state = new ActiveState();
		for (String ruleName : rulesManager.getSetOfElements()) {
			Rule rule = (Rule) rulesManager.getElement(ruleName);
			for (String involved_sensor : rule.getInvolvedSensors()) {
				Sensor sens = getSensor(involved_sensor);
				if (!sens.getState().isActive())
					rule_state = new InactiveState();
			}

			for (String involved_actuator : rule.getInvolvedActuators()) {
				Actuator act = (Actuator) actuatorManager.getElement(involved_actuator);
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
		for (String ruleName : rulesManager.getSetOfElements()) {
			Rule rule = (Rule) rulesManager.getElement(ruleName);
			if (rule.getState().isActive())
				enabledRulesList.add(rule);
		}
		return enabledRulesList;
	}

	public Set<String> getRulesNames() {
		return rulesManager.getSetOfElements();
	}

	public List<String> getEnabledRulesNamesList() {
		List<String> enabledRulesNamesList = new ArrayList<>();
		for (String ruleName : rulesManager.getSetOfElements()) {
			if (((Rule) rulesManager.getElement(ruleName)).getState().isActive())
				enabledRulesNamesList.add(ruleName);
		}
		return enabledRulesNamesList;
	}

	public List<String> getEnabledRulesStrings() {
		List<String> enabledRulesString = new ArrayList<>();
		for (String ruleName : rulesManager.getSetOfElements()) {
			if (((Rule) rulesManager.getElement(ruleName)).getState().isActive())
				enabledRulesString.add(((Rule) rulesManager.getElement(ruleName)).toString());
		}
		return enabledRulesString;
	}

	public List<String> getRulesStrings() {
		List<String> enabledRulesString = new ArrayList<>();
		for (String ruleName : rulesManager.getSetOfElements())
			enabledRulesString.add(((Rule) rulesManager.getElement(ruleName)).toString());
		return enabledRulesString;
	}

	public void changeRuleState(String selectedRule) {
		Rule rule = (Rule) rulesManager.getElement(selectedRule);
		rule.trigger();
	}

	private boolean housingUnitInvariant() {
		boolean checkName = name != null && name.length() > 0;
		boolean checkDescr = descr != null;
		boolean checkManagers = roomManager != null && sensorManager != null && actuatorManager != null
				&& artifactManager != null && associationManager != null;
		return checkName && checkDescr && checkManagers;
	}

	public Set<String> getArtifactSet() {
		return artifactManager.getSetOfElements();
	}

	public Set<String> getActuatorSet() {
		return actuatorManager.getSetOfElements();
	}

	public Set<String> getSensorSet() {
		return sensorManager.getSetOfElements();
	}

	public void removeRule(String selectedRule) {
		rulesManager.removeElement(selectedRule);
	}

	public Rule getRule(String selectedRule) {
		return (Rule) rulesManager.getElement(selectedRule);
	}

	public boolean ruleContainsSensor(String selectedRule, String selectedSensor) {
		return getRule(selectedRule).containsSensor(selectedSensor);
	}

	public boolean ruleContainsActuator(String selectedRule, String selectedActuator) {
		return getRule(selectedRule).containsActuator(selectedActuator);
	}

	public boolean isMeasuringRoom(String selectedSensor) {
		return getSensor(selectedSensor).isMeasuringRoom();
	}

	public boolean isControllingRoom(String selectedActuator) {
		return getActuator(selectedActuator).isControllingRoom();
	}

	public Set<String> getMeasuredObjectSet(String selectedSensor) {
		return getSensor(selectedSensor).getMeasuredObjectSet();
	}

	public void removeRoomAssociationWithCategory(String room, String category) {
		associationManager.removeRoomAssociationWithCategory(room, category);
	}

	public void removeArtifactAssociationWithCategory(String artifact, String category) {
		associationManager.removeArtifactAssociationWithCategory(artifact, category);
	}

	public void removeSensor(String selectedRoom, String selectedSensor) {
		getRoom(selectedRoom).removeSensor(selectedSensor);
		sensorManager.removeElement(selectedSensor);
	}

	public boolean isSensorInstanceOf(String selectedSensor, String selectedCategory) {
		return getSensor(selectedSensor).isInstanceOf(selectedCategory);
	}

	public String getRoomOfSensor(String selectedSensor) {
		for (String room : getRoomSet())
			if (getRoom(room).hasSensor(selectedSensor))
				return room;
		return null;
	}

	public String getCategoryOfAnActuator(String selectedActuator) {
		return getActuator(selectedActuator).getCategoryName();
	}

	public Set<String> getControlledObjectSet(String selectedActuator) {
		return getActuator(selectedActuator).getControlledObjectSet();
	}

	public void removeActuator(String selectedRoom, String selectedActuator) {
		getRoom(selectedRoom).removeActuator(selectedActuator);
		actuatorManager.removeElement(selectedActuator);
	}

	public boolean isActuatorInstanceOf(String selectedActuator, String selectedCategory) {
		return getActuator(selectedActuator).isInstanceOf(selectedCategory);
	}

	public String getRoomOfActuator(String selectedActuator) {
		for (String room : getRoomSet())
			if (getRoom(room).hasActuator(selectedActuator))
				return room;
		return null;
	}

	public boolean isSensorAssociatedWith(String selectedSensor, String object) {
		return getSensor(selectedSensor).isMeasuringObject(object);
	}

	public void removeSensorAssociation(String selectedSensor, String object) {
		getSensor(selectedSensor).removeMeasuredObject(object);
	}

	public boolean isSensorNotAssociated(String selectedSensor) {
		return getSensor(selectedSensor).isNotAssociated();
	}

	public boolean isActuatorAssociatedWith(String selectedActuator, String object) {
		return getActuator(selectedActuator).isControllingObject(object);
	}

	public void removeActuatorAssociation(String selectedActuator, String object) {
		getActuator(selectedActuator).removeControlledObject(object);
	}

	public boolean isActuatorNotAssociated(String selectedActuator) {
		return getActuator(selectedActuator).isNotAssociated();
	}

	public void removeRoomAssociation(String selectedRoom) {
		associationManager.removeRoomAssociation(selectedRoom);
	}

	public void removeRoom(String selectedRoom) {
		roomManager.removeElement(selectedRoom);
	}

	public void removeArtifactAssociation(String selectedArtifact) {
		associationManager.removeArtifactAssociation(selectedArtifact);
	}

	public void removeArtifact(String selectedRoom, String selectedArtifact) {
		getRoom(selectedRoom).removeArtifact(selectedArtifact);
		artifactManager.removeElement(selectedArtifact);
	}
	
	public boolean hasAssociableArtifacts(String category) {
		return associationManager.hasAssociableArtifacts(category);
	}
	
	public boolean hasAssociableRooms(String category) {
		return associationManager.hasAssociableRooms(category);
	}
}
