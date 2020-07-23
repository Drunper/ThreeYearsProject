package it.unibs.ing.domohouse.model.db.persistent;

import it.unibs.ing.domohouse.model.db.Query;

public abstract class PersistentObject {

	private PersistentObjectState persistentObjectState;

	public PersistentObject(PersistentObjectState persistentObjectState) {
		this.persistentObjectState = persistentObjectState;
	}
	
	public abstract Query getModifyQuery();
	
	public abstract Query getInsertionQuery();
	
	public abstract Query getDeleteQuery();
	
	public Query getUpdateQuery() {
		return persistentObjectState.getUpdateQuery(this);
	}
	
	public void setObjectState(PersistentObjectState persistentObjectState) {
		this.persistentObjectState = persistentObjectState;
	}
	
	public void modify() {
		persistentObjectState.modify(this);
	}
	
	public void delete() {
		persistentObjectState.delete(this);
	}
}
