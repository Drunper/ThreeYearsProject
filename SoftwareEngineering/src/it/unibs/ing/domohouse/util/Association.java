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
		assert associationInvariant() : Strings.WRONG_INVARIANT;
		return associatedCategories.contains(category);
	}
	
	public void addAssociationWith(String category) {
		assert category != null; 
		assert associationInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = associatedCategories.size();
		
		associatedCategories.add(category);
		
		assert associatedCategories.size() >= pre_size;
		assert associationInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void removeAssociationWith(String category) {
		assert category != null;
		assert associationInvariant() : Strings.WRONG_INVARIANT;
		assert associatedCategories.contains(category) : "associatedCategory non contiente " + category + " e dunque non può rimuoverlo";
		int pre_size = associatedCategories.size();
		
		associatedCategories.remove(category);
		
		assert associatedCategories.size() <= pre_size;
		assert associationInvariant() : Strings.WRONG_INVARIANT;		
	}
	
	public String getElementName() {
		assert associationInvariant() : Strings.WRONG_INVARIANT;
		return element;
	}
	
	public void setElementName(String element) {
		assert element != null;
		assert associationInvariant() : Strings.WRONG_INVARIANT;
		
		this.element = element;
		
		assert associationInvariant() : Strings.WRONG_INVARIANT;
	}

	public boolean isElementARoom() {
		assert associationInvariant() : Strings.WRONG_INVARIANT;
		return roomOrArtifact;
	}

	public void setRoomOrArtifact(boolean roomOrArtifact) {
		assert associationInvariant() : Strings.WRONG_INVARIANT;
		this.roomOrArtifact = roomOrArtifact;
		assert associationInvariant() : Strings.WRONG_INVARIANT;
	}
	
	private boolean associationInvariant() {
		boolean checkElement = element != null;
		boolean checkAssociatedCategory = associatedCategories != null;
		
		return checkElement && checkAssociatedCategory;
	}
}
