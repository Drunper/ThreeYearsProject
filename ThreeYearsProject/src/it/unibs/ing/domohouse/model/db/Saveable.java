package it.unibs.ing.domohouse.model.db;

public interface Saveable {

	Query getModifyQuery();
	Query getInsertionQuery();
	Query getDeleteQuery();
	Query getUpdateQuery();
	void setObjectState(ObjectState objectState);
	void modify();
	void delete();
}
