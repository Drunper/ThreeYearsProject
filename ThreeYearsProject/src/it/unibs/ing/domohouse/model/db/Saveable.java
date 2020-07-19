package it.unibs.ing.domohouse.model.db;

public interface Saveable {

	Query getUpdateQuery();
	Query getInsertionQuery();
	Query getDeleteQuery();
	void setObjectState(ObjectState objectState);
}
