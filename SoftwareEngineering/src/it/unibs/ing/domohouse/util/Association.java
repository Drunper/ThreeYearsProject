package it.unibs.ing.domohouse.util;

import java.util.ArrayList;

public class Association {

	private String element;
	private boolean elementIsARoom;
	private ArrayList<String> associatedCategory;
	
	public Association(String element) {
		this.element = element;
		associatedCategory = new ArrayList<>();
	}
	
	public boolean isAssociated(String category) {
		return associatedCategory.contains(category);
	}
	
	public void addAssociation(String category) {
		associatedCategory.add(category);
	}
	
	public void removeAssociation(String category) {
		associatedCategory.remove(category);
	}
	
	public String getElementName() {
		return element;
	}
	
	public void setElementName(String element) {
		this.element = element;
	}

	public boolean isElementARoom() {
		return elementIsARoom;
	}

	public void setIsElementARoom(boolean elementIsARoom) {
		this.elementIsARoom = elementIsARoom;
	}
}
