package it.unibs.ing.domohouse.model.db;

import it.unibs.ing.domohouse.model.components.elements.User;

public class SaveableUser implements Saveable {

	private User user;
	private ObjectState objectState;

	public SaveableUser(User user, ObjectState objectState) {
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
