package it.unibs.ing.domohouse.model.db.persistent;

import it.unibs.ing.domohouse.model.db.Query;

public interface PersistentObjectState {

	Query getUpdateQuery(PersistentObject context);
	void modify(PersistentObject context);
	void delete(PersistentObject context);
}
