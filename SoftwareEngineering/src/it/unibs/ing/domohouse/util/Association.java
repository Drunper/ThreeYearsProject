package it.unibs.ing.domohouse.util;

import java.io.Serializable;
import java.util.ArrayList;

public class Association implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 844718073354532891L;
	private String element;
	private boolean elementIsARoom;
	private ArrayList<String> associatedCategory;
	/*
	 * invariante element != null e size > 0 e associatedCategory != null;
	 */
	public Association(String element) {
		this.element = element;
		associatedCategory = new ArrayList<>();
	}
	
	public boolean isAssociated(String category) {
		assert category != null;
		assert associationInvariant() : "Invariante di classe non soddisfatto";
		return associatedCategory.contains(category);
	}
	
	public void addAssociation(String category) {
		assert category != null; 
		assert associationInvariant() : "Invariante di classe non soddisfatto";
		int pre_size = associatedCategory.size();
		
		associatedCategory.add(category);
		
		assert associatedCategory.size() >= pre_size;
		assert associationInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public void removeAssociation(String category) {
		assert category != null;
		assert associationInvariant() : "Invariante di classe non soddisfatto";
		assert associatedCategory.contains(category) : "associatedCategory non contiente " + category + " e dunque non può rimuoverlo";
		int pre_size = associatedCategory.size();
		
		associatedCategory.remove(category);
		
		assert associatedCategory.size() <= pre_size;
		assert associationInvariant() : "Invariante di classe non soddisfatto";		
	}
	
	public String getElementName() {
		assert associationInvariant() : "Invariante di classe non soddisfatto";
		return element;
	}
	
	public void setElementName(String element) {
		assert element != null;
		assert associationInvariant() : "Invariante di classe non soddisfatto";
		
		this.element = element;
		
		assert associationInvariant() : "Invariante di classe non soddisfatto";
	}

	public boolean isElementARoom() {
		assert associationInvariant() : "Invariante di classe non soddisfatto";
		return elementIsARoom;
	}

	public void setIsElementARoom(boolean elementIsARoom) {
		assert associationInvariant() : "Invariante di classe non soddisfatto";
		this.elementIsARoom = elementIsARoom;
		assert associationInvariant() : "Invariante di classe non soddisfatto";
	}
	
	private boolean associationInvariant() {
		boolean checkElement = element != null;
		boolean checkAssociatedCategory = associatedCategory != null;
		
		if(checkElement && checkAssociatedCategory) return true;
		return false;
	}
}
