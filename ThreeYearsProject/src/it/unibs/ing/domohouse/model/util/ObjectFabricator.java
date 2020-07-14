package it.unibs.ing.domohouse.model.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.unibs.ing.domohouse.model.components.elements.Actuator;
import it.unibs.ing.domohouse.model.components.elements.Artifact;
import it.unibs.ing.domohouse.model.components.elements.HousingUnit;
import it.unibs.ing.domohouse.model.components.elements.Room;
import it.unibs.ing.domohouse.model.components.elements.Sensor;
import it.unibs.ing.domohouse.model.components.properties.ActiveState;
import it.unibs.ing.domohouse.model.components.properties.ActuatorCategory;
import it.unibs.ing.domohouse.model.components.properties.InfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.properties.InactiveState;
import it.unibs.ing.domohouse.model.components.properties.SensorCategory;
import it.unibs.ing.domohouse.model.components.properties.State;
import it.unibs.ing.domohouse.model.components.rule.Rule;
import it.unibs.ing.domohouse.model.components.rule.RuleParser;
import it.unibs.ing.domohouse.model.ModelStrings;

public class ObjectFabricator {

	private DataFacade dataFacade;
	private RuleParser ruleParser;

	public ObjectFabricator(DataFacade dataFacade, RuleParser ruleParser) {
		this.dataFacade = dataFacade;
		this.ruleParser = ruleParser;
	}

	public void createHouse(String name, String descr, String type, String user) {
		assert name != null && name.length() > 0;
		assert descr != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;

		HousingUnit house = new HousingUnit(name, descr, type, user);
		dataFacade.addHousingUnit(house);

		assert house != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void createArtifact(String selectedHouse, String name, String descr, String location) {
		assert selectedHouse != null;
		assert name != null && name.length() > 0;
		assert descr != null;
		assert location != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;

		Artifact art = new Artifact(name, descr);
		dataFacade.addArtifact(selectedHouse, location, art);

		assert art != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void createSensor(String selectedHouse, String name, String category, boolean roomOrArtifact,
			List<String> objectList, String location) {
		assert selectedHouse != null;
		assert name != null && name.length() > 0;
		assert category != null && objectList != null && location != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;

		String realName = name + ModelStrings.UNDERSCORE + category;

		Sensor sensor = new Sensor(realName, dataFacade.getSensorCategory(category));
		sensor.setMeasuringRoom(roomOrArtifact);
		dataFacade.addSensor(selectedHouse, location, sensor);
		for (String object : objectList) {
			dataFacade.addAssociation(selectedHouse, object, category);
			if (roomOrArtifact)
				sensor.addEntry(dataFacade.getRoom(selectedHouse, object));
			else
				sensor.addEntry(dataFacade.getArtifact(selectedHouse, object));
		}

		assert sensor != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void createActuator(String selectedHouse, String name, String category, boolean roomOrArtifact,
			List<String> objectList, String location) {
		assert selectedHouse != null;
		assert name != null && name.length() > 0;
		assert category != null && objectList != null && location != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;

		String realName = name + ModelStrings.UNDERSCORE + category;
		Actuator actuator = new Actuator(realName, dataFacade.getActuatorCategory(category));
		actuator.setControllingRoom(roomOrArtifact);
		dataFacade.addActuator(selectedHouse, location, actuator);
		for (String object : objectList) {
			dataFacade.addAssociation(selectedHouse, object, category);
			if (roomOrArtifact)
				actuator.addEntry(dataFacade.getRoom(selectedHouse, object));
			else {
				actuator.addEntry(dataFacade.getArtifact(selectedHouse, object));
			}
		}

		assert actuator != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void createRoom(String selectedHouse, String name, String descr, Map<String, String> propertiesMap) {
		assert selectedHouse != null;
		assert name != null && descr != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;

		Room room = new Room(name, descr, propertiesMap);
		dataFacade.addRoom(selectedHouse, room);

		assert room != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void createSensorCategory(String name, String abbreviation, String manufacturer,
			Map<String, InfoStrategy> infoDomainMap, Map<String, String> measurementUnitMap) {
		assert name != null && name.length() > 0;
		assert abbreviation != null && manufacturer != null && infoDomainMap != null && measurementUnitMap != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;

		String descr = abbreviation + ModelStrings.SEPARATOR + manufacturer;
		for (Entry<String, InfoStrategy> entry : infoDomainMap.entrySet()) {
			descr = descr + ModelStrings.SEPARATOR + entry.getValue();
			if (measurementUnitMap.containsKey(entry.getKey()))
				descr = descr + ModelStrings.SPACE + ModelStrings.OPEN_BRACKET + measurementUnitMap.get(entry.getKey())
						+ ModelStrings.CLOSED_BRACKET;
		}
		SensorCategory cat = new SensorCategory(name, descr, infoDomainMap, measurementUnitMap);
		dataFacade.addSensorCategory(cat);

		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void createActuatorCategory(String name, String abbreviation, String manufacturer, List<String> listOfModes,
			String defaultMode) {
		assert name != null && name.length() > 0;
		assert abbreviation != null && manufacturer != null && listOfModes != null && defaultMode != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;

		String descr = abbreviation + ModelStrings.SEPARATOR + manufacturer + ModelStrings.SEPARATOR + defaultMode;
		for (String toConcat : listOfModes)
			descr = descr + ModelStrings.SEPARATOR + toConcat;
		ActuatorCategory cat = new ActuatorCategory(name, descr);
		for (String toAdd : listOfModes)
			cat.putOperatingMode(toAdd, OperatingModesManager.getOperatingMode(toAdd));
		dataFacade.addActuatorCategory(cat);

		assert cat != null;
		assert objectFabricatorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void createRule(String selectedHouse, String name, String antString, String consString,
			List<String> involvedSensors, List<String> involvedActuators) {
		assert name != null && name.length() > 0;
		State state = new ActiveState();
		for (String sensor : involvedSensors)
			if (!dataFacade.getSensor(selectedHouse, sensor).getState().isActive())
				state = new InactiveState();
		for (String actuator : involvedActuators)
			if (!dataFacade.getActuator(selectedHouse, actuator).getState().isActive())
				state = new InactiveState();
		createRule(selectedHouse, name, antString, consString, involvedSensors, involvedActuators, state);
	}

	public void createRule(String selectedHouse, String name, String antString, String consString,
			List<String> involvedSensors, List<String> involvedActuators, State state) {
		Rule rule = null;
		try {
			rule = new Rule(name, ruleParser.parseAnt(selectedHouse, antString),
					ruleParser.parseCons(selectedHouse, consString), involvedSensors, involvedActuators, state);
			if (consString.contains("start"))
				rule.setTime(ruleParser.getTime(consString));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		dataFacade.addRule(selectedHouse, rule);
	}

	private boolean objectFabricatorInvariant() {
		return dataFacade != null;
	}
}
