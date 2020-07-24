package it.unibs.ing.domohouse.model.components.elements;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import it.unibs.ing.domohouse.model.ModelStrings;
import it.unibs.ing.domohouse.model.db.persistent.PersistentObject;

public class Artifact implements Gettable, Serializable {

	private static final long serialVersionUID = 643544508558962880L;
	private String name;
	private String text;
	private Map<String, String> propertiesMap;
	public PersistentObject saveable;
	
	public Artifact(String name, String text, Map<String, String> propertiesMap) {
		this.name = name;
		this.text = text;
		this.propertiesMap = propertiesMap;
	}

	@Override
	public String getName() {
		assert artifactInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.name;
	}

	public String getDescr() {
		assert artifactInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.text;
	}
	
	@Override
	public void setName(String newName) {
		assert newName.length() > 0 && newName != null;
		this.name = newName;
		assert artifactInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void setDescr(String text) {
		assert text != null;
		this.text = text;
		modify();
		assert artifactInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	@Override
	public String getProperty(String variableName) {
		return propertiesMap.get(variableName);
	}
	
	@Override
	public void setProperty(String variableName, String newValue) {
		propertiesMap.put(variableName, newValue);
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
	public Set<String> getPropertiesNameSet() {
		return propertiesMap.keySet();
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

	private boolean artifactInvariant() {
		boolean checkName = false;
		boolean checkText = false;
		boolean checkMap = false;
		if (name.length() > 0 && name != null)
			checkName = true;
		if (text != null)
			checkText = true;
		if (propertiesMap != null)
			checkMap = true;

		return checkName && checkText && checkMap;
	}

}
