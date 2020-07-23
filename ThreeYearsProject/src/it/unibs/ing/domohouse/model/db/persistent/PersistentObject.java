package it.unibs.ing.domohouse.model.db.persistent;

import it.unibs.ing.domohouse.model.db.Query;

public interface PersistentObject {

	Query getModifyQuery();
	Query getInsertionQuery();
	Query getDeleteQuery();
	Query getUpdateQuery();
	void setObjectState(PersistentObjectState objectState);
	void modify();
	void delete();
}
