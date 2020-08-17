package it.unibs.ing.domohouse.model.components.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.unibs.ing.domohouse.model.components.elements.Manageable;
import it.unibs.ing.domohouse.model.db.persistent.PersistentObject;
import it.unibs.ing.domohouse.model.ModelStrings;

public class ActuatorCategory implements Manageable {

	private String name;
	private String descr;
	private Map<String, OperatingMode> operatingModesMap;
	private PersistentObject saveable;

	/*
	 * invariante name > 0, text > 0 name != null, text != null
	 */
	public ActuatorCategory(String name, String descr) {
		this.name = name;
		this.descr = descr;
		operatingModesMap = new HashMap<>();
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

	public String getName() {
		assert actuatorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.name;
	}

	public String getDescr() {
		assert actuatorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.descr;
	}

	public void setName(String newName) {
		assert newName.length() > 0 && newName != null;
		this.name = newName;
		assert actuatorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void setDescr(String descr) {
		assert descr.length() > 0 && descr != null;
		this.descr = descr;
		assert actuatorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Set<String> getOperatingModesSet() {
		assert operatingModesMap != null;
		assert actuatorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;
		return operatingModesMap.keySet();
	}

	public void putOperatingMode(String name, OperatingMode operatingMode) {
		assert name.length() > 0;
		int pre_size = operatingModesMap.size();

		operatingModesMap.put(name, operatingMode);

		assert operatingModesMap.size() >= pre_size;
		assert actuatorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public OperatingMode getOperatingMode(String name) {
		assert operatingModesMap.containsKey(name) : ModelStrings.OPERATING_MODE_PRECONDITION;
		assert actuatorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;

		return operatingModesMap.get(name);
	}

	public boolean hasOperatingMode(String operating_mode) {
		assert operatingModesMap != null;

		return operatingModesMap.containsKey(operating_mode);
	}

	public String getDefaultMode() {
		assert descr.contains(ModelStrings.SEPARATOR);
		assert actuatorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;
		return descr.split(ModelStrings.SEPARATOR)[2];
	}

	public String getAbbreviation() {
		return descr.split(ModelStrings.SEPARATOR)[ModelStrings.FIRST_TOKEN];
	}

	public String getManufacturer() {
		return descr.split(ModelStrings.SEPARATOR)[ModelStrings.SECOND_TOKEN];
	}

	private boolean actuatorCategoryInvariant() {
		return name.length() > 0 && name != null && descr.length() > 0 && descr != null && operatingModesMap != null;
	}
}
