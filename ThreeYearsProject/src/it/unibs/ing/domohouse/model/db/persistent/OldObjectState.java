package it.unibs.ing.domohouse.model.db.persistent;

import it.unibs.ing.domohouse.model.db.Query;

public class OldObjectState implements PersistentObjectState {

	@Override
	public Query getUpdateQuery(PersistentObject context) {
		return null;
	}

	@Override
	public void modify(PersistentObject context) {
		context.setObjectState(new DirtyObjectState());
	}

	@Override
	public void delete(PersistentObject context) {
		context.setObjectState(new DeletedObjectState());
	}

}
