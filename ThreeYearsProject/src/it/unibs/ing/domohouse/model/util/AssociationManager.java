package it.unibs.ing.domohouse.model.util;

import java.io.Serializable;
import java.util.ArrayList;

import it.unibs.ing.domohouse.model.ModelStrings;

public class AssociationManager implements Serializable {

	private static final long serialVersionUID = 1194017378304880890L;
	private ArrayList<Association> associationList;

	public AssociationManager() {
		associationList = new ArrayList<>();
	}

	public int size() {
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		return associationList.size();
	}

	public void addAssociation(Association association) {
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = associationList.size();

		associationList.add(association);

		assert associationList.size() >= pre_size;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Association getAssociation(String toGet) {
		assert toGet != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		for (Association assoc : associationList) {
			if (assoc.getElementName().equalsIgnoreCase(toGet))
				return assoc;
		}
		return null;
	}

	public boolean hasAssociation(String name) {
		assert name != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;

		for (Association assoc : associationList) {
			if (assoc.getElementName().equalsIgnoreCase(name))
				return true;
		}
		return false;
	}

	public String[] associatedElementsList() {
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;

		String[] nameList = new String[associationList.size()];
		for (int i = 0; i < nameList.length; i++) {
			nameList[i] = associationList.get(i).getElementName();
		}
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		return nameList;
	}

	public boolean isElementARoom(String name) {
		assert name != null;

		Association element = getAssociation(name);

		assert element != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;

		return element.isElementARoom();
	}

	public boolean isElementAssociatedWith(String name, String category) {
		assert name != null && category != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;

		Association element = getAssociation(name);

		assert element != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		return element.isAssociatedWith(category);
	}

	public void addAssociationBetween(String name, String category) {
		assert name != null && category != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;

		Association element = getAssociation(name);
		assert element != null;
		element.addAssociationWith(category);

		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	private boolean associationHandlerInvariant() {
		boolean check = associationList != null;
		return check;
	}
}
