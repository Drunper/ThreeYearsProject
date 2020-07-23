package it.unibs.ing.domohouse.model.db.persistent;

import java.util.Collections;

import it.unibs.ing.domohouse.model.components.properties.ActuatorCategory;
import it.unibs.ing.domohouse.model.db.Query;
import it.unibs.ing.domohouse.model.db.QueryStrings;

public class PersistentActuatorCategory extends PersistentObject {

	private ActuatorCategory actuatorCategory;

	public PersistentActuatorCategory(ActuatorCategory actuatorCategory, PersistentObjectState persistentObjectState) {
		super(persistentObjectState);
		this.actuatorCategory = actuatorCategory;
	}

	@Override
	public Query getModifyQuery() {
		return null;
	}

	@Override
	public Query getInsertionQuery() {
		String queryString = QueryStrings.INSERT_ACTUATOR_CATEGORY;
		Query query = new Query("");
		query.setStringParameter(1, actuatorCategory.getName());
		query.setStringParameter(2, actuatorCategory.getDescr());
		query.setStringParameter(3, actuatorCategory.getAbbreviation());
		query.setStringParameter(4, actuatorCategory.getManufacturer());
		query.setStringParameter(5, actuatorCategory.getDefaultMode());

		int pos = 6;
		for (String opMode : actuatorCategory.getOperatingModesSet()) {
			query.setStringParameter(pos++, actuatorCategory.getName());
			query.setStringParameter(pos++, opMode);
		}
		queryString += QueryStrings.INSERT_CATEGORY_OPERATING_MODE
				+ String.join(", ",
						Collections.nCopies(actuatorCategory.getOperatingModesSet().size(), QueryStrings.TWO_VALUES))
				+ ";";
		query.setQuery(queryString);
		return query;
	}

	@Override
	public Query getDeleteQuery() {
		Query query = new Query(QueryStrings.DELETE_ACTUATOR_CATEGORY);
		query.setStringParameter(1, actuatorCategory.getName());
		return query;
	}
}
