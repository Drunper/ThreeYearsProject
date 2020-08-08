package it.unibs.ing.domohouse.model.util;

public class ObjectRemover {
	private DataFacade dataFacade;

	public ObjectRemover(DataFacade dataFacade) {
		this.dataFacade = dataFacade;
	}
	
	public void removeRule(String user, String selectedHouse, String selectedRule) {
		if(dataFacade.hasRule(user, selectedHouse, selectedRule))
			dataFacade.removeRule(user, selectedHouse, selectedRule);
	}

	public void removeSensor(String user, String selectedHouse, String selectedRoom, String selectedSensor) {
		if(dataFacade.hasSensor(user, selectedHouse, selectedSensor)) {
			for(String selectedRule : dataFacade.getRulesNames(user, selectedHouse)) {
				if(dataFacade.ruleContainsSensor(user, selectedHouse, selectedRule, selectedSensor))
					removeRule(user, selectedHouse, selectedRule);
			}
			String category = dataFacade.getCategoryOfASensor(user, selectedHouse, selectedSensor);
			boolean isMeasuringRoom = dataFacade.isMeasuringRoom(user, selectedHouse, selectedSensor);
			if(isMeasuringRoom)
				for(String object : dataFacade.getMeasuredObjectSet(user, selectedHouse, selectedSensor))
					dataFacade.removeRoomAssociationWithCategory(user, selectedHouse, object, category);
			else
				for(String object : dataFacade.getMeasuredObjectSet(user, selectedHouse, selectedSensor))
					dataFacade.removeArtifactAssociationWithCategory(user, selectedHouse, object, category);
			dataFacade.removeSensor(user, selectedHouse, selectedRoom, selectedSensor);
		}
	}

	public void removeSensorCategory(String selectedCategory) {
		if(dataFacade.hasSensorCategory(selectedCategory)) {
			for(String user : dataFacade.getUserSet())
				for(String selectedHouse : dataFacade.getHousingUnitSet(user)) {
					for(String selectedSensor : dataFacade.getSensorSet(user, selectedHouse)) {
						if(dataFacade.isSensorInstanceOf(user, selectedHouse, selectedSensor, selectedCategory)) {
							String selectedRoom = dataFacade.getRoomOfSensor(user, selectedHouse, selectedSensor);
							removeSensor(user, selectedHouse, selectedRoom, selectedSensor);
						}
					}
				}
			dataFacade.removeSensorCategory(selectedCategory);
		}
	}

	public void removeActuator(String user, String selectedHouse, String selectedRoom, String selectedActuator) {
		if(dataFacade.hasActuator(user, selectedHouse, selectedActuator)) {
			for(String selectedRule : dataFacade.getRulesNames(user, selectedHouse)) {
				if(dataFacade.ruleContainsActuator(user, selectedHouse, selectedRule, selectedActuator))
					removeRule(user, selectedHouse, selectedRule);
			}
			String category = dataFacade.getCategoryOfAnActuator(user, selectedHouse, selectedActuator);
			boolean isControllingRoom = dataFacade.isControllingRoom(user, selectedHouse, selectedActuator);
			if(isControllingRoom)
				for(String object : dataFacade.getControlledObjectSet(user, selectedHouse, selectedActuator))
					dataFacade.removeRoomAssociationWithCategory(user, selectedHouse, object, category);
			else
				for(String object : dataFacade.getControlledObjectSet(user, selectedHouse, selectedActuator))
					dataFacade.removeArtifactAssociationWithCategory(user, selectedHouse, object, category);
			dataFacade.removeActuator(user, selectedHouse, selectedRoom, selectedActuator);
		}
	}

	public void removeActuatorCategory(String selectedCategory) {
		if(dataFacade.hasActuatorCategory(selectedCategory)) {
			for(String user : dataFacade.getUserSet())
				for(String selectedHouse : dataFacade.getHousingUnitSet(user)) {
					for(String selectedActuator : dataFacade.getActuatorSet(user, selectedHouse)) {
						if(dataFacade.isActuatorInstanceOf(user, selectedHouse, selectedActuator, selectedCategory)) {
							String selectedRoom = dataFacade.getRoomOfActuator(user, selectedHouse, selectedActuator);
							removeActuator(user, selectedHouse, selectedRoom, selectedActuator);
						}
					}
				}
			dataFacade.removeActuatorCategory(selectedCategory);
		}
	}

	public void removeRoom(String user, String selectedHouse, String selectedRoom) {
		if(dataFacade.hasRoom(user, selectedHouse, selectedRoom)) {
			
			for(String selectedArtifact : dataFacade.getArtifactNames(user, selectedHouse, selectedRoom))
				removeArtifact(user, selectedHouse, selectedRoom, selectedArtifact);
			for(String selectedSensor : dataFacade.getSensorNames(user, selectedHouse, selectedRoom))
				removeSensor(user, selectedHouse, selectedRoom, selectedSensor);
			for(String selectedActuator : dataFacade.getActuatorNames(user, selectedHouse, selectedRoom))
				removeActuator(user, selectedHouse, selectedRoom, selectedActuator);
			
			for(String selectedSensor :  dataFacade.getSensorSet(user, selectedHouse)) {
				if(dataFacade.isSensorAssociatedWith(user, selectedHouse, selectedSensor, selectedRoom))
					dataFacade.removeSensorAssociation(user, selectedHouse, selectedSensor, selectedRoom);
				if(dataFacade.isSensorNotAssociated(user, selectedHouse, selectedSensor)) {
					String location = dataFacade.getRoomOfSensor(user, selectedHouse, selectedSensor);
					removeSensor(user, selectedHouse, location, selectedSensor);
				}
			}
			
			for(String selectedActuator : dataFacade.getActuatorSet(user, selectedHouse)) {
				if(dataFacade.isActuatorAssociatedWith(user, selectedHouse, selectedActuator, selectedRoom))
					dataFacade.removeActuatorAssociation(user, selectedHouse, selectedActuator, selectedRoom);
				if(dataFacade.isActuatorNotAssociated(user, selectedHouse, selectedActuator)) {
					String location = dataFacade.getRoomOfActuator(user, selectedHouse, selectedActuator);
					removeActuator(user, selectedHouse, location, selectedActuator);
				}
			}
			dataFacade.removeRoomAssociation(user, selectedHouse, selectedRoom);
			dataFacade.removeRoom(user, selectedHouse, selectedRoom);
		}
	}

	public void removeArtifact(String user, String selectedHouse, String selectedRoom, String selectedArtifact) {
		if(dataFacade.hasArtifact(user, selectedHouse, selectedArtifact)) {
			for(String selectedSensor : dataFacade.getSensorSet(user, selectedHouse)) {
				if(dataFacade.isSensorAssociatedWith(user, selectedHouse, selectedSensor, selectedArtifact))
					dataFacade.removeSensorAssociation(user, selectedHouse, selectedSensor, selectedArtifact);
				if(dataFacade.isSensorNotAssociated(user, selectedHouse, selectedSensor)) {
					String location = dataFacade.getRoomOfSensor(user, selectedHouse, selectedSensor);
					removeSensor(user, selectedHouse, location, selectedSensor);
				}
			}
			for(String selectedActuator : dataFacade.getActuatorSet(user, selectedHouse)) {
				if(dataFacade.isActuatorAssociatedWith(user, selectedHouse, selectedActuator, selectedArtifact))
					dataFacade.removeActuatorAssociation(user, selectedHouse, selectedActuator, selectedArtifact);
				if(dataFacade.isActuatorNotAssociated(user, selectedHouse, selectedActuator)) {
					String location = dataFacade.getRoomOfActuator(user, selectedHouse, selectedActuator);
					removeActuator(user, selectedHouse, location, selectedActuator);
				}
			}
			dataFacade.removeArtifactAssociation(user, selectedHouse, selectedArtifact);
			dataFacade.removeArtifact(user, selectedHouse, selectedRoom, selectedArtifact);
		}
	}
	
	public void removeHousingUnit(String user, String selectedHouse) {
		if(dataFacade.hasHousingUnit(user, selectedHouse)) {
			for(String room : dataFacade.getRoomsSet(user, selectedHouse))
				removeRoom(user, selectedHouse, room);
			dataFacade.removeHousingUnit(user, selectedHouse);
		}
	}

	public void removeUser(String user) throws Exception {
		if(dataFacade.hasUser(user)) {
			for(String housingUnit : dataFacade.getHousingUnitSet(user))
				removeHousingUnit(user, housingUnit);
			dataFacade.removeUser(user);
		}
	}
}
