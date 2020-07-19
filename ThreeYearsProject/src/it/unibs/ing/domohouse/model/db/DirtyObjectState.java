package it.unibs.ing.domohouse.model.db;

public class DirtyObjectState implements ObjectState {

	@Override
	public Query getUpdateQuery(Saveable context) {
		return context.getUpdateQuery();
	}

}
