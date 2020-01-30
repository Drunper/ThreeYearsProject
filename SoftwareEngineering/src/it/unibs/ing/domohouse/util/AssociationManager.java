package it.unibs.ing.domohouse.util;

import java.util.ArrayList;

public class AssociationManager {

	//Forse si può usare la classe Manager, valuterò
	private ArrayList<Association> associationList;
	
	public AssociationManager() {
		associationList = new ArrayList<>();
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
}
