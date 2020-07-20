package it.unibs.ing.domohouse.model.db;

import java.util.Collections;

import it.unibs.ing.domohouse.model.components.elements.Sensor;

public class SaveableSensor implements Saveable {

	private String user;
	private String housingUnit;
	private String location;
	private Sensor sensor;
	private ObjectState objectState;
	
	public SaveableSensor(String user, String housingUnit, String location, Sensor sensor,
			ObjectState objectState) {
		this.user = user;
		this.housingUnit = housingUnit;
		this.location = location;
		this.sensor = sensor;
		this.objectState = objectState;
	}

	@Override
	public Query getModifyQuery() {
		return null;
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
		for(String measuredObject : sensor.getMeasuredObjectsSet()) {
			query.setStringParameter(pos++, sensor.getName());
			query.setStringParameter(pos++, housingUnit);
			query.setStringParameter(pos++, user);
			query.setStringParameter(pos++, measuredObject);
		}
		queryString += String.join(", ", Collections.nCopies(sensor.getMeasuredObjectsSet().size(), QueryStrings.FOUR_VALUES)) + ";";
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
	
	@Override
	public Query getUpdateQuery() {
		return objectState.getUpdateQuery(this);
	}

	@Override
	public void setObjectState(ObjectState objectState) {
		this.objectState = objectState;
	}

	@Override
	public void modify() {
		objectState.modify(this);
	}

	@Override
	public void delete() {
		objectState.delete(this);
	}
}
