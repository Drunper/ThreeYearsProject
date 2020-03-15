package it.unibs.ing.domohouse.util;

import java.io.Serializable;
import java.util.ArrayList;

public class AssociationHandler implements Serializable{

	private static final long serialVersionUID = 1194017378304880890L;
	private ArrayList<Association> associationList;
	
	public AssociationHandler() {
		associationList = new ArrayList<>();
	}
	
	public int size() {
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
		return associationList.size();
	}
	
	public void addAssociation(Association association) {
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = associationList.size();
		
		associationList.add(association);
		
		assert associationList.size() >= pre_size;
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void removeAssociation(Association toRemove) {
		assert toRemove != null;
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
		assert associationList.contains(toRemove) : "associationList non contiente " + toRemove + " e dunque non pu� rimuoverlo";
		int pre_size = associationList.size();
		
		associationList.remove(toRemove);
		
		assert associationList.size() <= pre_size;
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public Association getAssociation(String toGet) {
		assert toGet != null;
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
		for(Association assoc : associationList) {
			if (assoc.getElementName().equalsIgnoreCase(toGet))
				return assoc;
		}
		return null;
	}
	
	public boolean hasAssociation(String name) {
		assert name != null;
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		for(Association assoc : associationList) {
			if (assoc.getElementName().equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
	
	public String [] associatedElementsList() {
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		String [] nameList = new String[associationList.size()];
		for(int i = 0; i < nameList.length; i++) {
			nameList[i] = associationList.get(i).getElementName();
		}
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
		return nameList;
	}

	public boolean isElementARoom(String name) {
		assert name != null;
		
		Association element = getAssociation(name);
		
		assert element != null; 
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		return element.isElementARoom();
	}

	public boolean isElementAssociatedWith(String name, String category) {
		assert name != null && category != null;
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		Association element = getAssociation(name);
		
		assert element != null;
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
		return element.isAssociatedWith(category);
	}

	public void addAssociationBetween(String name, String category) {
		assert name != null && category != null;
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		Association element = getAssociation(name);
		assert element != null;
		element.addAssociationWith(category);
		
		assert associationHandlerInvariant() : Strings.WRONG_INVARIANT;
	}
	
	private boolean associationHandlerInvariant() {
		boolean check = associationList != null; 
		return check;
	}	
}
