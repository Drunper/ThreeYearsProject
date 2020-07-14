package it.unibs.ing.domohouse.model.components.elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.unibs.ing.domohouse.model.ModelStrings;

public class Artifact implements Gettable, Serializable {

	private static final long serialVersionUID = 643544508558962880L;
	private String name;
	private String text;
	private List<Actuator> controllerActuators;
	private Map<String, String> propertiesMap;

	public Artifact(String name, String text) {
		this.name = name;
		this.text = text;
		this.controllerActuators = new ArrayList<>();
		propertiesMap = new TreeMap<>();
	}

	public String getName() {
		assert artifactInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.name;
	}

	public String getDescr() {
		assert artifactInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.text;
	}

	public Actuator getActuatorByName(String selectedActuator) {
		assert selectedActuator != null;
		assert artifactInvariant() : ModelStrings.WRONG_INVARIANT;

		Actuator result = null;
		for (int i = 0; i < controllerActuators.size(); i++) {
			if (controllerActuators.get(i).getName().equals(selectedActuator))
				result = controllerActuators.get(i);
		}

		assert artifactInvariant() : ModelStrings.WRONG_INVARIANT;
		return result;
	}

	public void setName(String newName) {
		assert newName.length() > 0 && newName != null;
		this.name = newName;
		assert artifactInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void setDescr(String text) {
		assert text != null;
		this.text = text;
		assert artifactInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	@Override
	public String getProperty(String variableName) {
		return propertiesMap.get(variableName);
	}

	public void setProperty(String variableName, String newValue) {
		propertiesMap.put(variableName, newValue);
	}

	public boolean hasProperty(String variableName) {
		return propertiesMap.containsKey(variableName);
	}

	private boolean artifactInvariant() {
		boolean checkName = false;
		boolean checkText = false;
		boolean checkMap = false;
		if (name.length() > 0 && name != null)
			checkName = true;
		if (text != null)
			checkText = true;
		if (propertiesMap != null && controllerActuators != null)
			checkMap = true;

		return checkName && checkText && checkMap;
	}

}
