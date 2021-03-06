package it.unibs.ing.domohouse.model.db.persistent;

import it.unibs.ing.domohouse.model.db.Query;

public class DeletedObjectState implements PersistentObjectState {

	@Override
	public Query getUpdateQuery(PersistentObject context) {
		return context.getDeleteQuery();
	}

	@Override
	public void modify(PersistentObject context) {
	}

	@Override
	public void delete(PersistentObject context) {
	}

}
