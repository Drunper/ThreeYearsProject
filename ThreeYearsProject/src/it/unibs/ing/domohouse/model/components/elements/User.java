package it.unibs.ing.domohouse.model.components.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unibs.ing.domohouse.model.ModelStrings;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.rule.Rule;
import it.unibs.ing.domohouse.model.db.persistent.PersistentObject;
import it.unibs.ing.domohouse.model.util.Manager;

public class User implements Manageable {

	private String name;
	private Manager housingUnitManager;
	private PersistentObject saveable;
	
	public User(String name) {
		this.name = name;
		housingUnitManager = new Manager();
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
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean hasHousingUnit(String selectedHouse) {
		assert selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		return housingUnitManager.hasElement(selectedHouse);
	}

	public Set<String> getHousingUnitsList() {
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		return housingUnitManager.getSetOfElements();
	}

	public boolean doesHousingUnitExist() {
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		return !housingUnitManager.isEmpty();
	}

	public HousingUnit getHousingUnit(String selectedHouse) {
		assert selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		return (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
	}

	public void addHousingUnit(HousingUnit toAdd) {
		assert toAdd != null;
		housingUnitManager.addElement(toAdd);
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Set<String> getRoomsList(String selectedHouse) {
		assert selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		assert (HousingUnit) housingUnitManager.getElementByName(selectedHouse) != null;

		HousingUnit selected = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);

		return selected.getRoomList();
	}
	
	public String getCategoryOfASensor(String selectedHouse, String selectedSensor) {
		assert selectedHouse != null && selectedSensor != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit house = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return house.getCategoryOfASensor(selectedSensor);
	}

	public Sensor getSensor(String selectedHouse, String selectedSensor) {
		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.getSensor(selectedSensor);
	}

	public Set<String> getSensorNames(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);

		assert room != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		return room.getSensorsNames();
	}

	public boolean doesSensorExist(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit house = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return house.doesSensorExist(selectedRoom);
	}

	public Actuator getActuator(String selectedHouse, String selectedActuator) {
		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.getActuator(selectedActuator);
	}

	public Set<String> getActuatorNames(String selectedHouse, String selectedRoom) {
		assert selectedHouse != null;
		assert selectedRoom != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);

		assert room != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		return room.getActuatorsNames();
	}

	public boolean doesActuatorExist(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit house = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return house.doesActuatorExist(selectedRoom);
	}
	
	public Set<String> getActuatorOperatingModes(String selectedHouse, String selectedActuator) {
		return getHousingUnit(selectedHouse).getActuatorOperatingModes(selectedActuator);
	}

	public boolean doesArtifactExist(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit house = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return house.doesArtifactExist(selectedRoom);
	}

	public Set<String> getArtifactNames(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);

		assert room != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		return room.getArtifactsNames();
	}

	public void changeHouseDescription(String selectedHouse, String descr) {
		assert descr != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.setDescr(descr);

		assert _selectedHouse.getDescr() != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasRoom(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasRoom(name);
	}

	public void addRoom(String selectedHouse, Room toAdd) {
		assert toAdd != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addRoom(toAdd);

		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void changeRoomDescription(String selectedHouse, String selectedRoom, String descr) {
		assert selectedHouse != null;
		assert selectedRoom != null && descr != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.setRoomDescription(selectedRoom, descr);

		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addSensor(String selectedHouse, String location, Sensor sensor) {
		assert selectedHouse != null;
		assert location != null && sensor != null && sensor.getName() != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addSensor(location, sensor);

		assert _selectedHouse.getRoom(location).getSensorByName(sensor.getName()) != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasRoomOrArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasRoomOrArtifact(name);
	}

	public boolean hasSensor(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasSensor(name);
	}

	public boolean hasActuator(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasActuator(name);
	}

	public boolean hasArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasArtifact(name);
	}

	public boolean hasRule(String selectedHouse, String rule) {
		assert selectedHouse != null && rule != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);

		assert _selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		return _selectedHouse.hasRule(rule);
	}

	public boolean isElementARoom(String selectedHouse, String toAssoc) {
		assert toAssoc != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.isElementARoom(toAssoc);
	}

	public boolean isAssociated(String selectedHouse, String toAssoc, String category) {
		assert toAssoc != null && category != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.isAssociatedWith(toAssoc, category);
	}

	public void addAssociation(String selectedHouse, String object, String category) {
		assert object != null && category != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		assert housingUnitManager.getElementByName(selectedHouse) != null;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addAssociationWith(object, category);

		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Room getRoom(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		assert housingUnitManager.getElementByName(selectedHouse) != null;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(name);

		assert room != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		return room;
	}

	public Artifact getArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Artifact art = _selectedHouse.getArtifact(name);

		assert art != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		return art;
	}

	public void addArtifact(String selectedHouse, String location, Artifact toAdd) {
		assert location != null && toAdd != null && toAdd.getName() != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addArtifact(toAdd, location);

		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addActuator(String selectedHouse, String location, Actuator toAdd) {
		assert location != null && toAdd != null && selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addActuator(toAdd, location);

		assert _selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasOperatingMode(String name) {
		assert name != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		return OperatingModesManager.hasOperatingMode(name);
	}

	public boolean doesRoomOrArtifactExist(String selectedHouse) {
		assert selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);

		assert _selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		return _selectedHouse.doesRoomOrArtifactExist();
	}

	public boolean doesRoomExist(String selectedHouse) {
		assert selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);

		assert _selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		return _selectedHouse.doesRoomExist();
	}

	public boolean doesArtifactExist(String selectedHouse) {
		assert selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);

		assert _selectedHouse != null;
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;
		return _selectedHouse.doesArtifactExist();
	}

	public void updateRulesState(String selectedHouse) {
		assert userInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit house = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		house.updateRulesState();
	}

	public List<Rule> getEnabledRulesList() {
		List<Rule> rules = new ArrayList<>();
		for (String house : getHousingUnitsList())
			rules.addAll(getHousingUnit(house).getEnabledRulesList());
		return rules;
	}

	public List<String> getEnabledRulesStrings(String selectedHouse) {
		return getHousingUnit(selectedHouse).getEnabledRulesStrings();
	}

	public List<String> getRulesStrings(String selectedHouse) {
		return getHousingUnit(selectedHouse).getRulesStrings();
	}

	public void addRule(String selectedHouse, Rule toAdd) {
		HousingUnit house = getHousingUnit(selectedHouse);
		house.addRule(toAdd);
	}

	public Set<String> getRulesNames(String selectedHouse) {
		return getHousingUnit(selectedHouse).getRulesNames();
	}

	public void changeRuleState(String selectedHouse, String rule) {
		getHousingUnit(selectedHouse).changeRuleState(rule);
	}

	public boolean doesRuleExist(String selectedHouse) {
		return getHousingUnit(selectedHouse).doesRuleExist();
	}
	
	private boolean userInvariant() {
		return housingUnitManager != null;
	}

	public Set<String> getArtifactSet(String selectedHouse) {
		return getHousingUnit(selectedHouse).getArtifactSet();
	}
}
