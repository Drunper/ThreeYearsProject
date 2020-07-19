package it.unibs.ing.domohouse.model.db;

public class NewObjectState implements ObjectState {

	@Override
	public Query getUpdateQuery(Saveable context) {
		return context.getInsertionQuery();
	}

}
