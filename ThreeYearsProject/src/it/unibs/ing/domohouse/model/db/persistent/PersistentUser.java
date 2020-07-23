package it.unibs.ing.domohouse.model.db.persistent;

import it.unibs.ing.domohouse.model.components.elements.User;
import it.unibs.ing.domohouse.model.db.Query;
import it.unibs.ing.domohouse.model.db.QueryStrings;

public class PersistentUser extends PersistentObject {

	private User user;

	public PersistentUser(User user, PersistentObjectState persistentObjectState) {
		super(persistentObjectState);
		this.user = user;
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
}
