package it.unibs.ing.domohouse.model.db;

public interface ObjectState {

	Query getUpdateQuery(Saveable context);
}
