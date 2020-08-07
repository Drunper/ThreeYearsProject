package it.unibs.ing.domohouse.model.db.persistent;

import java.util.Collections;

import it.unibs.ing.domohouse.model.components.elements.Sensor;
import it.unibs.ing.domohouse.model.db.Query;
import it.unibs.ing.domohouse.model.db.QueryStrings;

public class PersistentSensor extends PersistentObject {

	private String user;
	private String housingUnit;
	private String location;
	private Sensor sensor;
	
	public PersistentSensor(String user, String housingUnit, String location, Sensor sensor,
			PersistentObjectState persistentObjectState) {
		super(persistentObjectState);
		this.user = user;
		this.housingUnit = housingUnit;
		this.location = location;
		this.sensor = sensor;
	}

	@Override
	public Query getModifyQuery() {
		Query query = new Query("");
		int pos = 1;
		String queryString = sensor.isMeasuringRoom() ? QueryStrings.DELETE_MEASURED_ROOM : QueryStrings.DELETE_MEASURED_ARTIFACT;
		for(String object : sensor.getNotAssociatedObjects()) {
			query.setStringParameter(pos++, sensor.getName());
			query.setStringParameter(pos++, housingUnit);
			query.setStringParameter(pos++, user);
			query.setStringParameter(pos++, object);
		}
		queryString = String.join(" ", Collections.nCopies(sensor.getNotAssociatedObjects().size(), queryString));
		
		queryString += " " + QueryStrings.UPDATE_SENSOR_STATE;
		query.setIntegerParameter(pos++, sensor.getState().isActive() ? 1 : 0);
		query.setStringParameter(pos++, sensor.getName());
		query.setStringParameter(pos++, housingUnit);
		query.setStringParameter(pos++, user);
		query.setQuery(queryString);
		return query;
	}
	
	@Override
	public Query getInsertionQuery() {
		String queryString = QueryStrings.INSERT_SENSOR;
		if(sensor.isMeasuringRoom())
			queryString += QueryStrings.INSERT_SENSOR_MEASURE_ROOM;
		else
			queryString += QueryStrings.INSERT_SENSOR_MEASURE_ARTIFACT;
		
		Query query = new Query("");
		query.setStringParameter(1, sensor.getName());
		query.setStringParameter(2, housingUnit);
		query.setStringParameter(3, user);
		query.setIntegerParameter(4, sensor.getState().isActive() ? 1 : 0);
		query.setIntegerParameter(5, sensor.isMeasuringRoom() ? 1 : 0);
		query.setStringParameter(6, location);
		query.setStringParameter(7, sensor.getCategory());
		
		int pos = 8;
		for(String measuredObject : sensor.getMeasuredObjectSet()) {
			query.setStringParameter(pos++, sensor.getName());
			query.setStringParameter(pos++, housingUnit);
			query.setStringParameter(pos++, user);
			query.setStringParameter(pos++, measuredObject);
		}
		queryString += String.join(", ", Collections.nCopies(sensor.getMeasuredObjectSet().size(), QueryStrings.FOUR_VALUES)) + ";";
		query.setQuery(queryString);
		return query;
	}

	@Override
	public Query getDeleteQuery() {
		Query query = new Query(QueryStrings.DELETE_SENSOR);
		query.setStringParameter(1, sensor.getName());
		query.setStringParameter(2, housingUnit);
		query.setStringParameter(3, user);
		return query;
	}
}
