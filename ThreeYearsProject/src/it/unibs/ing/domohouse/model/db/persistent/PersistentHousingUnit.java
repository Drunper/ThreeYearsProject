package it.unibs.ing.domohouse.model.db.persistent;

import it.unibs.ing.domohouse.model.components.elements.HousingUnit;
import it.unibs.ing.domohouse.model.db.Query;
import it.unibs.ing.domohouse.model.db.QueryStrings;

public class PersistentHousingUnit extends PersistentObject {

	private HousingUnit housingUnit;
	
	public PersistentHousingUnit(HousingUnit housingUnit, PersistentObjectState persistentObjectState) {
		super(persistentObjectState);
		this.housingUnit = housingUnit;
	}

	@Override
	public Query getModifyQuery() {
		Query query = new Query(QueryStrings.UPDATE_HOUSING_UNIT);
		query.setStringParameter(1, housingUnit.getDescr());
		query.setStringParameter(2, housingUnit.getName());
		query.setStringParameter(3, housingUnit.getUser());
		return query;
	}
	
	@Override
	public Query getInsertionQuery() {
		Query query = new Query(QueryStrings.INSERT_HOUSING_UNIT);
		query.setStringParameter(1, housingUnit.getName());
		query.setStringParameter(2, housingUnit.getUser());
		query.setStringParameter(3, housingUnit.getType());
		query.setStringParameter(4, housingUnit.getDescr());
		return query;
	}

	@Override
	public Query getDeleteQuery() {
		Query query = new Query(QueryStrings.DELETE_HOUSING_UNIT);
		query.setStringParameter(1, housingUnit.getName());
		query.setStringParameter(2, housingUnit.getUser());
		return query;
	}
}
