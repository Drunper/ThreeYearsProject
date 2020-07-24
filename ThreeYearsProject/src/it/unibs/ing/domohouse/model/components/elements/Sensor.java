package it.unibs.ing.domohouse.model.components.elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unibs.ing.domohouse.model.components.properties.ActiveState;
import it.unibs.ing.domohouse.model.components.properties.SensorCategory;
import it.unibs.ing.domohouse.model.components.properties.State;
import it.unibs.ing.domohouse.model.db.persistent.PersistentObject;
import it.unibs.ing.domohouse.model.util.Manager;
import it.unibs.ing.domohouse.model.ModelStrings;

public class Sensor implements Manageable, Serializable, Stateable {

	private static final long serialVersionUID = -3804289994000782961L;

	private String name;
	private SensorCategory category;
	private Manager measuredObjects;
	private List<String> notAssociatedObjects;
	private State state;
	private boolean measuringRoom;
	private PersistentObject saveable;

	public Sensor(String name, SensorCategory category) {
		this.name = name;
		this.category = category;
		measuredObjects = new Manager();
		notAssociatedObjects = new ArrayList<>();
		state = new ActiveState();
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
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
		return name;
	}

	public void setName(String name) {
		assert name != null && name.length() > 0;
		this.name = name;
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addEntry(Gettable toAdd) {
		assert toAdd != null;
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = measuredObjects.size();

		measuredObjects.addElement(toAdd);

		assert measuredObjects.size() >= pre_size;
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Set<String> getMeasuredObjectSet() {
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
		return measuredObjects.getSetOfElements();
	}

	public Gettable getElementByName(String name) {
		assert name != null && name.length() > 0;
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;

		Gettable g = (Gettable) measuredObjects.getElement(name);

		assert g != null;
		return g;
	}

	public boolean isMeasuringRoom() {
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
		return measuringRoom;
	}

	public void setMeasuringRoom(boolean measuringRoom) {
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
		this.measuringRoom = measuringRoom;
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public State getState() {
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
		return state;
	}

	public void setState(State state) {
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
		this.state = state;
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void trigger() {
		state.trigger(this);
	}

	public String getCategory() {
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
		return category.getName();
	}

	public void removeMeasuredObject(String toRemove) {
		modify();
		measuredObjects.removeElement(toRemove);
		notAssociatedObjects.add(toRemove);
	}

	public List<String> getNotAssociatedObjects() {
		return notAssociatedObjects;
	}

	public boolean isMeasuringObject(String element) {
		return measuredObjects.hasElement(element);
	}

	public boolean isNotAssociated() {
		return measuredObjects.isEmpty();
	}

	public String getMeasurementOf(String info) {
		List<String> valuesFromObjects = new ArrayList<>();
		Set<String> objectNames = getMeasuredObjectSet();
		for (String name : objectNames) {
			Gettable element = getElementByName(name);
			if (element.hasProperty(info)) { // Se è possibile rilevare informazioni allora le rileva
				valuesFromObjects.add(element.getProperty(info));
			}
		}
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;
		return category.getSingleValueOf(info, valuesFromObjects);
	}

	public List<String> getMeasurements() {
		assert sensorInvariant() : ModelStrings.WRONG_INVARIANT;

		List<String> result = new ArrayList<>();
		for (String info : category.getInfoStrategySet()) {
			String measurement = getMeasurementOf(info);
			if (category.hasMeasurementUnit(info))
				measurement = measurement + ModelStrings.SPACE + category.getMeasurementUnit(info);
			result.add(measurement);
		}

		return result;
	}

	private boolean sensorInvariant() {
		boolean checkName = name != null && name.length() > 0;
		boolean checkCategory = category != null;
		boolean checkManager = measuredObjects != null;

		return checkName && checkCategory && checkManager;
	}

	public boolean isInstanceOf(String selectedCategory) {
		return category.getName().equalsIgnoreCase(selectedCategory);
	}
}
