package it.unibs.ing.domohouse.model.db;

public class OldObjectState implements ObjectState {

	@Override
	public Query getUpdateQuery(Saveable context) {
		return null;
	}

}
