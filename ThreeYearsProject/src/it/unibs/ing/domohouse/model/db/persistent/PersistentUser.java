package it.unibs.ing.domohouse.model.db.persistent;

import it.unibs.ing.domohouse.model.components.elements.User;
import it.unibs.ing.domohouse.model.db.Query;
import it.unibs.ing.domohouse.model.db.QueryStrings;

public class PersistentUser implements PersistentObject {

	private User user;
	private PersistentObjectState objectState;

	public PersistentUser(User user, PersistentObjectState objectState) {
		this.user = user;
		this.objectState = objectState;
	}

	@Override
	public Query getModifyQuery() {
		return null;
	}

	@Override
	public Query getInsertionQuery() {
		Query query = new Query(QueryStrings.INSERT_USER);
		query.setStringParameter(1, user.getName());
		return query;
	}

	@Override
	public Query getDeleteQuery() {
		Query query = new Query(QueryStrings.DELETE_USER);
		query.setStringParameter(1, user.getName());
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
