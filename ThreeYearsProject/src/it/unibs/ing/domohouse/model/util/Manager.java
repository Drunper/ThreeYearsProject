package it.unibs.ing.domohouse.model.util;

import java.util.concurrent.ConcurrentHashMap;

import it.unibs.ing.domohouse.model.components.elements.Manageable;
import it.unibs.ing.domohouse.model.ModelStrings;

import java.util.Map;
import java.util.Set;

public class Manager {

	private Map<String, Manageable> elementMap;

	/*
	 * invariante elementMap != null;
	 */
	public Manager() {
		elementMap = new ConcurrentHashMap<>();
	}

	public int size() {
		assert managerInvariant() : ModelStrings.WRONG_INVARIANT;
		return elementMap.size();
	}

	public Manageable getElement(String name) {
		assert name != null;
		assert elementMap.containsKey(name) : ModelStrings.ELEMENT_MAP_PRECONDITION + name;
		assert managerInvariant() : ModelStrings.WRONG_INVARIANT;

		Manageable elem = elementMap.get(name);

		assert elem != null;
		assert managerInvariant() : ModelStrings.WRONG_INVARIANT;
		return elem;
	}

	public void addElement(Manageable element) {
		assert managerInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = elementMap.size();

		if(element != null)
			elementMap.put(element.getName(), element);

		assert elementMap.size() >= pre_size;
		assert managerInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasElement(String name) {
		assert name != null;
		assert managerInvariant() : ModelStrings.WRONG_INVARIANT;

		return elementMap.containsKey(name);
	}

	public Set<String> getSetOfElements() {
		assert managerInvariant() : ModelStrings.WRONG_INVARIANT;
		Set<String> namesSet = elementMap.keySet();

		assert namesSet != null;
		return namesSet;
	}

	public boolean isEmpty() {
		return elementMap.isEmpty();
	}

	private boolean managerInvariant() {
		return elementMap != null;
	}

	public void removeElement(String element) {
		elementMap.get(element).delete();
		elementMap.remove(element);
	}
}
