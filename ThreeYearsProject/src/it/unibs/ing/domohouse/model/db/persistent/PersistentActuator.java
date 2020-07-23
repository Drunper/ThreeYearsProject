package it.unibs.ing.domohouse.model.db.persistent;

import java.util.Collections;

import it.unibs.ing.domohouse.model.components.elements.Actuator;
import it.unibs.ing.domohouse.model.db.Query;
import it.unibs.ing.domohouse.model.db.QueryStrings;

public class PersistentActuator extends PersistentObject {

	private String user;
	private String housingUnit;
	private String location;
	private Actuator actuator;
	
	public PersistentActuator(String user, String housingUnit, String location, Actuator actuator,
			PersistentObjectState persistentObjectState) {
		super(persistentObjectState);
		this.user = user;
		this.housingUnit = housingUnit;
		this.location = location;
		this.actuator = actuator;
	}

	@Override
	public Query getModifyQuery() {
		return null;
	}
	
	@Override
	public Query getInsertionQuery() {
		String queryString = QueryStrings.INSERT_ACTUATOR;
		if(actuator.isControllingRoom())
			queryString += QueryStrings.INSERT_ACTUATOR_CONTROL_ROOM;
		else
			queryString += QueryStrings.INSERT_ACTUATOR_CONTROL_ARTIFACT;
		
		Query query = new Query("");
		query.setStringParameter(1, actuator.getName());
		query.setStringParameter(2, housingUnit);
		query.setStringParameter(3, user);
		query.setIntegerParameter(4, actuator.getState().isActive() ? 1 : 0);
		query.setIntegerParameter(5, actuator.isControllingRoom() ? 1 : 0);
		query.setStringParameter(6, location);
		query.setStringParameter(7, actuator.getCategory().getName());
		
		int pos = 8;
		for(String controlledObject : actuator.getControlledObjectsSet()) {
			query.setStringParameter(pos++, actuator.getName());
			query.setStringParameter(pos++, housingUnit);
			query.setStringParameter(pos++, user);
			query.setStringParameter(pos++, controlledObject);
		}
		queryString += String.join(", ", Collections.nCopies(actuator.getControlledObjectsSet().size(), QueryStrings.FOUR_VALUES))  + ";";
		query.setQuery(queryString);
		return query;
	}

	@Override
	public Query getDeleteQuery() {
		Query query = new Query(QueryStrings.DELETE_ACTUATOR);
		query.setStringParameter(1, actuator.getName());
		query.setStringParameter(2, housingUnit);
		query.setStringParameter(3, user);
		return query;
	}
}
