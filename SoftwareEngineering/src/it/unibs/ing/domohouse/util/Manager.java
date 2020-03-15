package it.unibs.ing.domohouse.util;

import java.util.TreeMap;
import java.util.Set;
import java.io.Serializable;
import it.unibs.ing.domohouse.interfaces.Manageable;

public class Manager implements Serializable {

	private static final long serialVersionUID = -5803882506409735012L;
	private TreeMap<String, Manageable> elementMap;
	
	/*
	 * invariante elementMap != null;
	 */
	public Manager() {
		elementMap = new TreeMap<>();
	}
	
	public int size() {
		assert managerInvariant() : Strings.WRONG_INVARIANT;
		return elementMap.size();
	}
	
	public Manageable getElementByName(String name) {
		assert name != null;
		assert elementMap.containsKey(name) : Strings.ELEMENT_MAP_PRECONDITION + name;
		assert managerInvariant() : Strings.WRONG_INVARIANT;
		
		Manageable elem = elementMap.get(name);
		
		assert elem != null;
		assert managerInvariant() : Strings.WRONG_INVARIANT;
		return elem;
	}
	
	public void addElement(Manageable element) {
		assert element != null;
		assert managerInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = elementMap.size();
		
		elementMap.put(element.getName(), element);
		
		assert elementMap.size() >= pre_size;
		assert managerInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void removeElement(String toRemove) {
		assert toRemove != null;
		assert managerInvariant() : Strings.WRONG_INVARIANT;
		assert elementMap.containsKey(toRemove);
		int pre_size = elementMap.size();
		
		elementMap.remove(toRemove);
		
		assert elementMap.size() <= pre_size;
		assert managerInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void changeElementName(String oldName, String newName) {
		assert oldName != null && newName != null;
		assert managerInvariant() : Strings.WRONG_INVARIANT;
		assert elementMap.containsKey(oldName) : Strings.ELEMENT_MAP_PRECONDITION + oldName; 
		int pre_size = elementMap.size();
		
		Manageable element = elementMap.get(oldName);
		elementMap.remove(oldName);
		element.setName(newName);
		addElement(element);
		
		assert elementMap.size() == pre_size;
		assert element != null;
		assert managerInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public boolean hasElement(String name) {
		assert name != null;
		assert managerInvariant() : Strings.WRONG_INVARIANT;
		
		return elementMap.containsKey(name);
	}
	
	public String [] getListOfElements () {
		assert managerInvariant() : Strings.WRONG_INVARIANT;
		Set<String> namesSet = elementMap.keySet();
		
		assert namesSet != null; 
		return namesSet.toArray(new String[0]);
	}
	
	public String getElementString(String name) {
		assert name != null;
		assert managerInvariant() : Strings.WRONG_INVARIANT;
		
		Manageable element = elementMap.get(name);
		String result = element.toString();
		
		assert result != null;
		assert managerInvariant() : Strings.WRONG_INVARIANT;
		return result; 
	}
	
	private boolean managerInvariant() {
		return elementMap != null;
	}
}
