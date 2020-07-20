package it.unibs.ing.domohouse.model.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import it.unibs.ing.domohouse.model.components.elements.*;
import it.unibs.ing.domohouse.model.components.properties.*;
import it.unibs.ing.domohouse.model.components.rule.Rule;
import it.unibs.ing.domohouse.model.components.rule.RuleParser;
import it.unibs.ing.domohouse.model.ModelStrings;

public class ObjectFactory {

	private RuleParser ruleParser;

	public ObjectFactory(RuleParser ruleParser) {
		this.ruleParser = ruleParser;
	}

	public Sensor createSensor(String name, SensorCategory category, boolean roomOrArtifact,
			List<Gettable> objectList) {
		assert name != null && name.length() > 0;
		assert category != null && objectList != null;
		assert objectFactoryInvariant() : ModelStrings.WRONG_INVARIANT;

		Sensor sensor = new Sensor(name, category);
		sensor.setMeasuringRoom(roomOrArtifact);
		for (Gettable object : objectList)
			sensor.addEntry(object);

		return sensor;
	}

	public Actuator createActuator(String name, ActuatorCategory category, boolean roomOrArtifact,
			List<Gettable> objectList) {
		assert name != null && name.length() > 0;
		assert category != null && objectList != null;
		assert objectFactoryInvariant() : ModelStrings.WRONG_INVARIANT;

		Actuator actuator = new Actuator(name, category);
		actuator.setControllingRoom(roomOrArtifact);
		for (Gettable object : objectList)
			actuator.addEntry(object);

		return actuator;
	}

	public SensorCategory createSensorCategory(String name, String abbreviation, String manufacturer,
			Map<String, InfoStrategy> infoDomainMap, Map<String, String> measurementUnitMap) {
		assert name != null && name.length() > 0;
		assert abbreviation != null && manufacturer != null && infoDomainMap != null && measurementUnitMap != null;
		assert objectFactoryInvariant() : ModelStrings.WRONG_INVARIANT;

		String descr = abbreviation + ModelStrings.SEPARATOR + manufacturer;
		for (Entry<String, InfoStrategy> entry : infoDomainMap.entrySet()) {
			descr = descr + ModelStrings.SEPARATOR + entry.getValue();
			if (measurementUnitMap.containsKey(entry.getKey()))
				descr = descr + ModelStrings.SPACE + ModelStrings.OPEN_BRACKET + measurementUnitMap.get(entry.getKey())
						+ ModelStrings.CLOSED_BRACKET;
		}
		return new SensorCategory(name, descr, infoDomainMap, measurementUnitMap);
	}

	public ActuatorCategory createActuatorCategory(String name, String abbreviation, String manufacturer,
			List<String> listOfModes, String defaultMode) {
		assert name != null && name.length() > 0;
		assert abbreviation != null && manufacturer != null && listOfModes != null && defaultMode != null;
		assert objectFactoryInvariant() : ModelStrings.WRONG_INVARIANT;

		String descr = abbreviation + ModelStrings.SEPARATOR + manufacturer + ModelStrings.SEPARATOR + defaultMode;
		for (String toConcat : listOfModes)
			descr = descr + ModelStrings.SEPARATOR + toConcat;
		ActuatorCategory cat = new ActuatorCategory(name, descr);
		for (String toAdd : listOfModes)
			cat.putOperatingMode(toAdd, OperatingModesManager.getOperatingMode(toAdd));

		return cat;
	}

	public void createRule(String name, String antString, String consString, List<Sensor> involvedSensors,
			List<Actuator> involvedActuators) {
		assert name != null && name.length() > 0;
		State state = new ActiveState();
		for (Sensor sensor : involvedSensors)
			if (!sensor.getState().isActive())
				state = new InactiveState();
		for (Actuator actuator : involvedActuators)
			if (!actuator.getState().isActive())
				state = new InactiveState();
		createRule(name, antString, consString, involvedSensors, involvedActuators, state);
	}

	public Rule createRule(String name, String antString, String consString, List<Sensor> involvedSensors,
			List<Actuator> involvedActuators, State state) {
		Rule rule = null;
		try {
			rule = new Rule(name, ruleParser.parseAnt(antString, involvedSensors),
					ruleParser.parseCons(consString, involvedActuators),
					involvedSensors.stream().map(s -> s.getName()).collect(Collectors.toList()),
					involvedActuators.stream().map(a -> a.getName()).collect(Collectors.toList()), state);
			if (consString.contains("start"))
				rule.setTime(ruleParser.getTime(consString));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return rule;
	}

	private boolean objectFactoryInvariant() {
		return ruleParser != null;
	}
}
