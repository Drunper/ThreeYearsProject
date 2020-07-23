package it.unibs.ing.domohouse.model.db.persistent;

import it.unibs.ing.domohouse.model.components.rule.Rule;
import it.unibs.ing.domohouse.model.db.Query;
import it.unibs.ing.domohouse.model.db.QueryStrings;

public class PersistentRule implements PersistentObject {

	private String user;
	private String housingUnit;
	private Rule rule;
	private PersistentObjectState objectState;
	
	public PersistentRule(String user, String housingUnit, Rule rule,
			PersistentObjectState objectState) {
		this.user = user;
		this.housingUnit = housingUnit;
		this.rule = rule;
		this.objectState = objectState;
	}

	@Override
	public Query getModifyQuery() {
		return null;
	}
	
	@Override
	public Query getInsertionQuery() {
		Query query = new Query(QueryStrings.INSERT_RULE);
		query.setStringParameter(1, rule.getName());
		query.setStringParameter(2, housingUnit);
		query.setStringParameter(3, user);
		query.setIntegerParameter(4, rule.getState().isActive() ? 1 : 0);
		query.setStringParameter(5, rule.getAntecedentText());
		query.setStringParameter(6, rule.getConsequentText());
		return query;
	}

	@Override
	public Query getDeleteQuery() {
		Query query = new Query(QueryStrings.DELETE_RULE);
		query.setStringParameter(1, rule.getName());
		query.setStringParameter(2, housingUnit);
		query.setStringParameter(3, user);
		return query;
	}
	
	@Override
	public Query getUpdateQuery() {
		return objectState.getUpdateQuery(this);
	}

	@Override
	public void setObjectState(PersistentObjectState objectState) {
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
