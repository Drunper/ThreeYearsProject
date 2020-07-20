package it.unibs.ing.domohouse.model.db;

import it.unibs.ing.domohouse.model.components.elements.HousingUnit;

public class SaveableHousingUnit implements Saveable {

	private HousingUnit housingUnit;
	private ObjectState objectState;
	
	public SaveableHousingUnit(HousingUnit housingUnit, ObjectState objectState) {
		this.housingUnit = housingUnit;
		this.objectState = objectState;
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
