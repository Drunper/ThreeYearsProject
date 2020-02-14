package it.unibs.ing.domohouse.util;

import java.io.Serializable;
import java.util.ArrayList;

public class AssociationHandler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1194017378304880890L;
	//Forse si può usare la classe Manager, valuterò
	private ArrayList<Association> associationList;
	
	public AssociationHandler() {
		associationList = new ArrayList<>();
	}
	
	public int size() {
		return associationList.size();
	}
	
	public void addAssociation(Association association) {
		associationList.add(association);
	}
	
	public void removeAssociation(Association assoc) {
		associationList.remove(assoc);
	}
	
	public Association getAssociation(String name) {
		for(Association assoc : associationList) {
			if (assoc.getElementName().equalsIgnoreCase(name))
				return assoc;
		}
		return null;
	}
	
	public boolean hasEntry(String name) {
		for(Association assoc : associationList) {
			if (assoc.getElementName().equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
	
	public String [] objectNameList() {
		String [] nameList = new String[associationList.size()];
		for(int i = 0; i < nameList.length; i++)
		{
			nameList[i] = associationList.get(i).getElementName();
		}
		return nameList;
	}

	public boolean isElementARoom(String name) {
		Association element = getAssociation(name);
		return element.isElementARoom();
	}

	public boolean isAssociated(String name, String category) {
		Association element = getAssociation(name);
		return element.isAssociated(category);
	}

	public void addAssociation(String name, String category) {
		Association element = getAssociation(name);
		element.addAssociation(category);
	}
	
	
}
