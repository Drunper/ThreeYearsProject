package it.unibs.ing.domohouse.model.db.persistent;

import java.util.Collections;

import it.unibs.ing.domohouse.model.components.properties.SensorCategory;
import it.unibs.ing.domohouse.model.db.Query;
import it.unibs.ing.domohouse.model.db.QueryStrings;

public class PersistentSensorCategory extends PersistentObject {

	private SensorCategory sensorCategory;

	public PersistentSensorCategory(SensorCategory sensorCategory, PersistentObjectState persistentObjectState) {
		super(persistentObjectState);
		this.sensorCategory = sensorCategory;
	}

	@Override
	public Query getModifyQuery() {
		return null;
	}

	@Override
	public Query getInsertionQuery() {
		String queryString = QueryStrings.INSERT_SENSOR_CATEGORY;
		Query query = new Query("");
		query.setStringParameter(1, sensorCategory.getName());
		query.setStringParameter(2, sensorCategory.getDescr());
		query.setStringParameter(3, sensorCategory.getAbbreviation());
		query.setStringParameter(4, sensorCategory.getManufacturer());

		int pos = 5;
		for (String property : sensorCategory.getInfoStrategySet()) {
			query.setStringParameter(pos++, sensorCategory.getName());
			query.setIntegerParameter(pos++, sensorCategory.getInfoID(property));
			query.setStringParameter(pos++, property);
			query.setStringParameter(pos++, sensorCategory.getMeasurementUnit(property));
		}

		queryString += QueryStrings.INSERT_MEASURED_INFO
				+ String.join(", ",
						Collections.nCopies(sensorCategory.getInfoStrategySet().size(), QueryStrings.FOUR_VALUES))
				+ ";";
		query.setQuery(queryString);
		return query;
	}

	@Override
	public Query getDeleteQuery() {
		Query query = new Query(QueryStrings.DELETE_SENSOR_CATEGORY);
		query.setStringParameter(1, sensorCategory.getName());
		return query;
	}
}
