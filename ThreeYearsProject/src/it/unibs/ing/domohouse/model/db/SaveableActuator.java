package it.unibs.ing.domohouse.model.db;

import it.unibs.ing.domohouse.model.components.elements.Actuator;

public class SaveableActuator implements Saveable {

	private String user;
	private String housingUnit;
	private String location;
	private Actuator actuator;
	private ObjectState objectState;
	
	public SaveableActuator(String user, String housingUnit, String location, Actuator actuator,
			ObjectState objectState) {
		this.user = user;
		this.housingUnit = housingUnit;
		this.location = location;
		this.actuator = actuator;
		this.objectState = objectState;
	}

	@Override
	public Query getUpdateQuery() {
		return null;
	}

	@Override
	public Query getInsertionQuery() {
		Query query = new Query(QueryStrings.INSERT_ACTUATOR);
		query.setStringParameter(1, actuator.getName());
		query.setStringParameter(2, housingUnit);
		query.setStringParameter(3, user);
		query.setIntegerParameter(4, actuator.getState().isActive() ? 1 : 0);
		query.setIntegerParameter(5, actuator.isControllingRoom() ? 1 : 0);
		query.setStringParameter(6, location);
		query.setStringParameter(7, actuator.getCategory().getName());
		
		return query;
	}

	@Override
	public Query getDeleteQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setObjectState(ObjectState objectState) {
		this.objectState = objectState;
	}
}
