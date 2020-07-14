package it.unibs.ing.domohouse.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unibs.ing.domohouse.model.components.elements.Actuator;
import it.unibs.ing.domohouse.model.components.elements.Artifact;
import it.unibs.ing.domohouse.model.components.elements.HousingUnit;
import it.unibs.ing.domohouse.model.components.elements.Room;
import it.unibs.ing.domohouse.model.components.elements.Sensor;
import it.unibs.ing.domohouse.model.components.properties.ActuatorCategory;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.properties.SensorCategory;
import it.unibs.ing.domohouse.model.components.rule.Rule;
import it.unibs.ing.domohouse.model.ModelStrings;

public class DataFacade implements Serializable {

	private static final long serialVersionUID = 830399600665259268L;
	private Manager sensorCategoryManager;
	private Manager actuatorCategoryManager;
	private Manager housingUnitManager;
	private boolean firstStart;

	/*
	 * invariante sensorCategoryManager != null, actuatorCategoryManager != null,
	 * housingUnitManager != null
	 */
	public DataFacade() {
		sensorCategoryManager = new Manager();
		actuatorCategoryManager = new Manager();
		housingUnitManager = new Manager();
		this.firstStart = true;
	}

	public void setFirstStart(boolean flag) {
		this.firstStart = flag;
	}

	public boolean getFirstStart() {
		return this.firstStart;
	}

	public boolean hasHousingUnit(String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return housingUnitManager.hasElement(selectedHouse);
	}

	public Set<String> getHousingUnitsList() {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return housingUnitManager.getListOfElements();
	}

	public boolean doesHousingUnitExist() {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return !housingUnitManager.isEmpty();
	}

	public HousingUnit getHousingUnit(String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
	}

	public void addHousingUnit(HousingUnit toAdd) {
		assert toAdd != null;
		housingUnitManager.addElement(toAdd);
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Set<String> getRoomsList(String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		assert (HousingUnit) housingUnitManager.getElementByName(selectedHouse) != null;

		HousingUnit selected = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);

		return selected.getRoomList();
	}

	public Set<String> getSensorCategoryList() {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return sensorCategoryManager.getListOfElements();
	}

	public String getCategoryOfASensor(String selectedHouse, String selectedSensor) {
		assert selectedHouse != null && selectedSensor != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit house = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return house.getCategoryOfASensor(selectedSensor);
	}

	public boolean doesSensorCategoryExist() {
		return !sensorCategoryManager.isEmpty();
	}

	public SensorCategory getSensorCategoryByInfo(String info) {
		assert info != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		for (String cat : sensorCategoryManager.getListOfElements()) {
			SensorCategory sensCat = getSensorCategory(cat);

			Set<String> detectableInfoList = sensCat.getDetectableInfoList();
			for (String inf : detectableInfoList) {
				if (info.equalsIgnoreCase(inf)) {
					assert sensCat != null;
					return sensCat;
				}
			}
		}
		return null;
	}

	public Set<String> getActuatorCategoryList() {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return actuatorCategoryManager.getListOfElements();
	}

	public boolean doesActuatorCategoryExist() {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return !actuatorCategoryManager.isEmpty();
	}

	public boolean hasSensorCategory(String category) {
		assert category != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return sensorCategoryManager.hasElement(category);
	}

	public boolean hasActuatorCategory(String category) {
		assert category != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return actuatorCategoryManager.hasElement(category);
	}

	public void addSensorCategory(SensorCategory cat) {
		assert cat != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = sensorCategoryManager.size();

		sensorCategoryManager.addElement(cat);

		assert sensorCategoryManager.size() >= pre_size;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addActuatorCategory(ActuatorCategory cat) {
		assert cat != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = actuatorCategoryManager.size();

		actuatorCategoryManager.addElement(cat);

		assert actuatorCategoryManager.size() >= pre_size;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public SensorCategory getSensorCategory(String category) {
		assert category != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		assert sensorCategoryManager.hasElement(category) : "sensorCategoryManager non contiene " + category;

		SensorCategory s = (SensorCategory) sensorCategoryManager.getElementByName(category);

		assert s != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return s;
	}

	public ActuatorCategory getActuatorCategory(String category) {
		assert category != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		assert actuatorCategoryManager.hasElement(category) : "actuatorCategoryManager non contiene " + category;

		ActuatorCategory act = (ActuatorCategory) actuatorCategoryManager.getElementByName(category);

		assert act != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return act;
	}

	public Sensor getSensor(String selectedHouse, String selectedSensor) {
		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.getSensor(selectedSensor);
	}

	public Set<String> getSensorNames(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);

		assert room != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return room.getSensorsNames();
	}

	public boolean doesSensorExist(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

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
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);

		assert room != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return room.getActuatorsNames();
	}

	public boolean doesActuatorExist(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit house = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return house.doesActuatorExist(selectedRoom);
	}
	
	public Set<String> getActuatorOperatingModes(String selectedHouse, String selectedActuator) {
		return getHousingUnit(selectedHouse).getActuatorOperatingModes(selectedActuator);
	}

	public boolean doesArtifactExist(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit house = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return house.doesArtifactExist(selectedRoom);
	}

	public Set<String> getArtifactNames(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);

		assert room != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return room.getArtifactsNames();
	}

	public void changeHouseDescription(String selectedHouse, String descr) {
		assert descr != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.setDescr(descr);

		assert _selectedHouse.getDescr() != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasRoom(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasRoom(name);
	}

	public void addRoom(String selectedHouse, Room toAdd) {
		assert toAdd != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addRoom(toAdd);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void changeRoomDescription(String selectedHouse, String selectedRoom, String descr) {
		assert selectedHouse != null;
		assert selectedRoom != null && descr != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.setRoomDescription(selectedRoom, descr);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addSensor(String selectedHouse, String location, Sensor sensor) {
		assert selectedHouse != null;
		assert location != null && sensor != null && sensor.getName() != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addSensor(location, sensor);

		assert _selectedHouse.getRoom(location).getSensorByName(sensor.getName()) != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasRoomOrArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasRoomOrArtifact(name);
	}

	public boolean hasSensor(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasSensor(name);
	}

	public boolean hasActuator(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasActuator(name);
	}

	public boolean hasArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasArtifact(name);
	}

	public boolean hasRule(String selectedHouse, String rule) {
		assert selectedHouse != null && rule != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);

		assert _selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return _selectedHouse.hasRule(rule);
	}

	public boolean isElementARoom(String selectedHouse, String toAssoc) {
		assert toAssoc != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.isElementARoom(toAssoc);
	}

	public boolean isAssociated(String selectedHouse, String toAssoc, String category) {
		assert toAssoc != null && category != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.isAssociatedWith(toAssoc, category);
	}

	public void addAssociation(String selectedHouse, String object, String category) {
		assert object != null && category != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		assert housingUnitManager.getElementByName(selectedHouse) != null;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addAssociationWith(object, category);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Room getRoom(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		assert housingUnitManager.getElementByName(selectedHouse) != null;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(name);

		assert room != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return room;
	}

	public Artifact getArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Artifact art = _selectedHouse.getArtifact(name);

		assert art != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return art;
	}

	public void addArtifact(String selectedHouse, String location, Artifact toAdd) {
		assert location != null && toAdd != null && toAdd.getName() != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addArtifact(toAdd, location);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addActuator(String selectedHouse, String location, Actuator toAdd) {
		assert location != null && toAdd != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addActuator(toAdd, location);

		assert _selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasOperatingMode(String name) {
		assert name != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return OperatingModesManager.hasOperatingMode(name);
	}

	public boolean doesRoomOrArtifactExist(String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);

		assert _selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return _selectedHouse.doesRoomOrArtifactExist();
	}

	public boolean doesRoomExist(String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);

		assert _selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return _selectedHouse.doesRoomExist();
	}

	public boolean doesArtifactExist(String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit _selectedHouse = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);

		assert _selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return _selectedHouse.doesArtifactExist();
	}

	public void updateRulesState(String selectedHouse) {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

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

	private boolean dataFacadeInvariant() {
		return sensorCategoryManager != null && actuatorCategoryManager != null && housingUnitManager != null;
	}
}