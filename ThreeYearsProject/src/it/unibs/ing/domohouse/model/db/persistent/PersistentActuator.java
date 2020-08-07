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
		Query query = new Query("");
		int pos = 1;
		String queryString = actuator.isControllingRoom() ? QueryStrings.DELETE_CONTROLLED_ROOM : QueryStrings.DELETE_CONTROLLED_ARTIFACT;
		for(String object : actuator.getNotAssociatedObjects()) {
			query.setStringParameter(pos++, actuator.getName());
			query.setStringParameter(pos++, housingUnit);
			query.setStringParameter(pos++, user);
			query.setStringParameter(pos++, object);
		}
		queryString = String.join(" ", Collections.nCopies(actuator.getNotAssociatedObjects().size(), queryString));
		
		queryString += " " + QueryStrings.UPDATE_SENSOR_STATE;
		query.setIntegerParameter(pos++, actuator.getState().isActive() ? 1 : 0);
		query.setStringParameter(pos++, actuator.getName());
		query.setStringParameter(pos++, housingUnit);
		query.setStringParameter(pos++, user);
		query.setQuery(queryString);
		return query;
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
		query.setStringParameter(7, actuator.getCategoryName());
		
		int pos = 8;
		for(String controlledObject : actuator.getControlledObjectSet()) {
			query.setStringParameter(pos++, actuator.getName());
			query.setStringParameter(pos++, housingUnit);
			query.setStringParameter(pos++, user);
			query.setStringParameter(pos++, controlledObject);
		}
		queryString += String.join(", ", Collections.nCopies(actuator.getControlledObjectSet().size(), QueryStrings.FOUR_VALUES))  + ";";
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
