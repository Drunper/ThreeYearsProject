package it.unibs.ing.domohouse.util;

import java.util.TreeMap;

import it.unibs.ing.domohouse.interfaces.Manageable;

import java.io.Serializable;
import java.util.Set;

public class Manager implements Serializable{

	private TreeMap<String, Manageable> elementMap;
	
	public Manager() {
		elementMap = new TreeMap<>();
	}
	
	public Manageable getElementByName(String name) {
		return elementMap.get(name);
	}
	
	public void addEntry(Manageable element) {
		elementMap.put(element.getName(), element);
	}
	
	public void remove(String toRemove) {
		elementMap.remove(toRemove);
	}
	
	public void changeElementName(String oldName, String newName) {
		Manageable element = elementMap.get(oldName);
		elementMap.remove(oldName);
		element.setName(newName);
		addEntry(element);
	}
	
	public boolean hasEntry(String name) {
		return elementMap.containsKey(name);
	}
	
	public String [] namesList () {
		Set<String> namesSet = elementMap.keySet();
		return namesSet.toArray(new String[0]);
	}
}