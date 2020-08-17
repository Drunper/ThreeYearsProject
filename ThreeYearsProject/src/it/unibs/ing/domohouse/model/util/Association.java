package it.unibs.ing.domohouse.model.util;

import java.util.ArrayList;
import java.util.List;

import it.unibs.ing.domohouse.model.ModelStrings;

public class Association {

	private String element; // nome della stanza o dell'artefatto
	private List<String> associatedCategories;

	/*
	 * invariante element != null e size > 0 e associatedCategory != null;
	 */
	public Association(String element) {
		this.element = element;
		associatedCategories = new ArrayList<>();
	}

	public boolean isAssociatedWith(String category) {
		assert category != null;
		assert associationInvariant() : ModelStrings.WRONG_INVARIANT;
		return associatedCategories.contains(category);
	}

	public void addAssociationWith(String category) {
		assert category != null;
		assert associationInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = associatedCategories.size();

		associatedCategories.add(category);

		assert associatedCategories.size() >= pre_size;
		assert associationInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public String getElementName() {
		assert associationInvariant() : ModelStrings.WRONG_INVARIANT;
		return element;
	}

	public void setElementName(String element) {
		assert element != null;
		assert associationInvariant() : ModelStrings.WRONG_INVARIANT;

		this.element = element;

		assert associationInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void removeAssociationWithCategory(String category) {
		associatedCategories.remove(category);
	}
	
	private boolean associationInvariant() {
		boolean checkElement = element != null;
		boolean checkAssociatedCategory = associatedCategories != null;

		return checkElement && checkAssociatedCategory;
	}
}
