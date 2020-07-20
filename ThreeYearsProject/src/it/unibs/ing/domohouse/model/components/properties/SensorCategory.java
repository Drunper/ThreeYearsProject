package it.unibs.ing.domohouse.model.components.properties;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibs.ing.domohouse.model.components.elements.Manageable;
import it.unibs.ing.domohouse.model.db.Saveable;
import it.unibs.ing.domohouse.model.ModelStrings;

public class SensorCategory implements Manageable, Serializable {

	private static final long serialVersionUID = 3198680539714650299L;
	private String name;
	private String descr;
	private Map<String, InfoStrategy> infoDomainMap;
	private Map<String, String> measurementUnitMap;
	private Saveable saveable;

	public SensorCategory(String name, String descr, Map<String, InfoStrategy> infoDomainMap,
			Map<String, String> measurementUnitMap) {
		this.name = name;
		this.descr = descr;
		this.infoDomainMap = infoDomainMap;
		this.measurementUnitMap = measurementUnitMap;
	}
	
	public void setSaveable(Saveable saveable) {
		this.saveable = saveable;
	}
	
	public void modify() {
		saveable.modify();
	}
	
	public void delete() {
		saveable.delete();
	}

	public String getName() {
		assert sensorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.name;
	}

	public String getDescr() {
		assert sensorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.descr;
	}

	public void setName(String name) {
		assert name != null;
		assert sensorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;
		this.name = name;
	}

	public void setDescr(String descr) {
		assert descr != null;
		assert sensorCategoryInvariant() : ModelStrings.WRONG_INVARIANT;
		this.descr = descr;
	}

	public boolean hasInfo(String info) {
		return infoDomainMap.containsKey(info);
	}

	public String getSingleValueOf(String variableName, List<String> values) {
		InfoStrategy info = infoDomainMap.get(variableName);
		return info.getSingleValue(values);
	}

	public Set<String> getInfoStrategySet() {
		return infoDomainMap.keySet();
	}
	
	public int getInfoID(String property) {
		return infoDomainMap.get(property).getID();
	}

	public String getMeasurementUnit(String info) {
		return measurementUnitMap.get(info);
	}

	public boolean hasMeasurementUnit(String info) {
		return measurementUnitMap.containsKey(info);
	}

	public String getDomainString(String info) {
		return infoDomainMap.get(info).toString();
	}

	public String getAbbreviation() {
		return descr.split(ModelStrings.SEPARATOR)[ModelStrings.FIRST_TOKEN];
	}

	public String getManufacturer() {
		return descr.split(ModelStrings.SEPARATOR)[ModelStrings.SECOND_TOKEN];
	}

	private boolean sensorCategoryInvariant() {
		boolean checkName = name != null;
		boolean checkText = descr != null;

		return checkName && checkText;
	}
}
