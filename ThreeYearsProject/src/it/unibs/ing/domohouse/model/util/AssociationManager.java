package it.unibs.ing.domohouse.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unibs.ing.domohouse.model.ModelStrings;

public class AssociationManager implements Serializable {

	private static final long serialVersionUID = 1194017378304880890L;
	private List<Association> artifactAssociationList;
	private List<Association> roomAssociationList;
	
	public AssociationManager() {
		artifactAssociationList = new ArrayList<>();
		roomAssociationList = new ArrayList<>();
	}

	public int artifactsSize() {
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		return artifactAssociationList.size();
	}
	
	public int roomsSize() {
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		return roomAssociationList.size();
	}
	
	public void addArtifactAssociation(Association toAdd) {
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = artifactAssociationList.size();
		
		artifactAssociationList.add(toAdd);
		
		assert artifactAssociationList.size() >= pre_size;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
	}
	
	public void addRoomAssociation(Association toAdd) {
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = artifactAssociationList.size();
		
		roomAssociationList.add(toAdd);
		
		assert artifactAssociationList.size() >= pre_size;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Association getArtifactAssociation(String selectedAssociation) {
		assert selectedAssociation != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		for(Association assoc : artifactAssociationList) {
			if (assoc.getElementName().equalsIgnoreCase(selectedAssociation))
				return assoc;
		}
		return null;
	}
	
	public Association getRoomAssociation(String selectedAssociation) {
		assert selectedAssociation != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		for(Association assoc : roomAssociationList) {
			if(assoc.getElementName().equalsIgnoreCase(selectedAssociation))
				return assoc;
		}
		return null;
	}
	
	public void removeArtifactAssociation(String selectedArtifact) {
		artifactAssociationList.remove(getArtifactAssociation(selectedArtifact));
	}

	public void removeRoomAssociation(String selectedRoom) {
		roomAssociationList.remove(getRoomAssociation(selectedRoom));
	}

	public void removeArtifactAssociationWithCategory(String artifact, String category) {
		getArtifactAssociation(artifact).removeAssociationWithCategory(category);
	}

	public void removeRoomAssociationWithCategory(String room, String category) {
		getRoomAssociation(room).removeAssociationWithCategory(category);
	}

	public int getNumberOfAssociableRooms(String category) {
		int counter = 0;
		for(Association assoc : roomAssociationList) {
			if(!assoc.isAssociatedWith(category))
				counter++;
		}
		return counter;
	}

	public int getNumberOfAssociableArtifacts(String category) {
		int counter = 0;
		for(Association assoc : artifactAssociationList) {
			if(!assoc.isAssociatedWith(category))
				counter++;
		}
		return counter;
	}
	
	public boolean hasArtifactAssociation(String selectedAssociation) {
		assert selectedAssociation != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		
		for(Association assoc : artifactAssociationList) {
			if(assoc.getElementName().equalsIgnoreCase(selectedAssociation))
				return true;
		}
		return false;
	}
	
	public boolean hasRoomAssociation(String selectedAssociation) {
		assert selectedAssociation != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		
		for(Association assoc : roomAssociationList) {
			if(assoc.getElementName().equalsIgnoreCase(selectedAssociation))
				return true;
		}
		return false;
	}

	public boolean isArtifactAssociated(String name, String category) {
		assert name != null && category != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		
		Association element = getArtifactAssociation(name);
		
		assert element != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		return element.isAssociatedWith(category);
	}
	
	public boolean isRoomAssociated(String name, String category) {
		assert name != null && category != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		
		Association element = getRoomAssociation(name);
		
		assert element != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		return element.isAssociatedWith(category);
	}

	public void addArtifactAssociationWith(String name, String category) {
		assert name != null && category != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		
		Association assoc = getArtifactAssociation(name);
		assert assoc != null;
		assoc.addAssociationWith(category);
		
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
	}
	
	public void addRoomAssociationWith(String name, String category) {
		assert name != null && category != null;
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		
		Association assoc = getRoomAssociation(name);
		assert assoc != null;
		assoc.addAssociationWith(category);
		
		assert associationHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
	}
	
	public boolean hasAssociableArtifacts(String category) {
		for(Association assoc : artifactAssociationList)
			if(!assoc.isAssociatedWith(category))
				return true;
		return false;
	}
	
	public boolean hasAssociableRooms(String category) {
		for(Association assoc : roomAssociationList)
			if(!assoc.isAssociatedWith(category))
				return true;
		return false;
	}

	private boolean associationHandlerInvariant() {
		return artifactAssociationList != null && roomAssociationList != null;
	}
}
