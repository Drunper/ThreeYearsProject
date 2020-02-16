package it.unibs.ing.domohouse.util;

import java.util.TreeMap;

import it.unibs.ing.domohouse.interfaces.Manageable;

import java.io.Serializable;
import java.util.Set;

public class Manager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5803882506409735012L;
	private TreeMap<String, Manageable> elementMap;
	/*
	 * invariante elementMap != null;
	 */
	public Manager() {
		elementMap = new TreeMap<>();
	}
	
	public int size() {
		assert managerInvariant() : "Invariante di classe non soddisfatto";
		return elementMap.size();
	}
	
	public Manageable getElementByName(String name) {
		assert name != null;
		assert elementMap.containsKey(name) : "elementMap non contiene " + name;
		assert managerInvariant() : "Invariante di classe non soddisfatto";
		
		Manageable elem = elementMap.get(name);
		
		assert elem != null;
		assert managerInvariant() : "Invariante di classe non soddisfatto";
		return elem;
	}
	
	public void addEntry(Manageable element) {
		assert element != null;
		assert managerInvariant() : "Invariante di classe non soddisfatto";
		int pre_size = elementMap.size();
		
		elementMap.put(element.getName(), element);
		
		assert elementMap.size() >= pre_size;
		assert managerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public void remove(String toRemove) {
		assert toRemove != null;
		assert managerInvariant() : "Invariante di classe non soddisfatto";
		assert elementMap.containsKey(toRemove);
		int pre_size = elementMap.size();
		
		elementMap.remove(toRemove);
		
		assert elementMap.size() <= pre_size;
		assert managerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public void changeElementName(String oldName, String newName) {
		assert oldName != null && newName != null;
		assert managerInvariant() : "Invariante di classe non soddisfatto";
		assert elementMap.containsKey(oldName) : "elementMap non contiene " + oldName; 
		int pre_size = elementMap.size();
		
		Manageable element = elementMap.get(oldName);
		elementMap.remove(oldName);
		element.setName(newName);
		addEntry(element);
		
		assert elementMap.size() == pre_size;
		assert element != null;
		assert managerInvariant() : "Invariante di classe non soddisfatto";
		
		
	}
	
	public boolean hasEntry(String name) {
		assert name != null;
		assert managerInvariant() : "Invariante di classe non soddisfatto";
		
		return elementMap.containsKey(name);
	}
	
	public String [] namesList () {
		assert managerInvariant() : "Invariante di classe non soddisfatto";
		Set<String> namesSet = elementMap.keySet();
		
		assert namesSet != null; 
		
		return namesSet.toArray(new String[0]);
	}
	
	public String getElementString(String name) {
		assert name != null;
		assert managerInvariant() : "Invariante di classe non soddisfatto";
		
		Manageable element = elementMap.get(name);
		String result = element.toString();
		
		assert result != null;
		assert managerInvariant() : "Invariante di classe non soddisfatto";
		return result; 
	}
	
	private boolean managerInvariant() {
		if(elementMap != null ) return true;
		return false;
	}
}
