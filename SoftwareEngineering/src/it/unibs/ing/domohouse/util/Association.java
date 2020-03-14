package it.unibs.ing.domohouse.util;

import java.io.Serializable;
import java.util.ArrayList;

public class Association implements Serializable {


	private static final long serialVersionUID = 844718073354532891L;
	private String element; // nome della stanza o dell'artefatto
	private boolean roomOrArtifact; // boolean per indicare se l'elemento è una stanza (true) o un artefatto (false)
	private ArrayList<String> associatedCategories;
	
	/*
	 * invariante element != null e size > 0 e associatedCategory != null;
	 */
	public Association(String element) {
		this.element = element;
		associatedCategories = new ArrayList<>();
	}
	
	public boolean isAssociatedWith(String category) {
		assert category != null;
		assert associationInvariant() : "Invariante di classe non soddisfatto";
		return associatedCategories.contains(category);
	}
	
	public void addAssociationWith(String category) {
		assert category != null; 
		assert associationInvariant() : "Invariante di classe non soddisfatto";
		int pre_size = associatedCategories.size();
		
		associatedCategories.add(category);
		
		assert associatedCategories.size() >= pre_size;
		assert associationInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public void removeAssociationWith(String category) {
		assert category != null;
		assert associationInvariant() : "Invariante di classe non soddisfatto";
		assert associatedCategories.contains(category) : "associatedCategory non contiente " + category + " e dunque non può rimuoverlo";
		int pre_size = associatedCategories.size();
		
		associatedCategories.remove(category);
		
		assert associatedCategories.size() <= pre_size;
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
		return roomOrArtifact;
	}

	public void setRoomOrArtifact(boolean roomOrArtifact) {
		assert associationInvariant() : "Invariante di classe non soddisfatto";
		this.roomOrArtifact = roomOrArtifact;
		assert associationInvariant() : "Invariante di classe non soddisfatto";
	}
	
	private boolean associationInvariant() {
		boolean checkElement = element != null;
		boolean checkAssociatedCategory = associatedCategories != null;
		
		if(checkElement && checkAssociatedCategory) return true;
		return false;
	}
}
