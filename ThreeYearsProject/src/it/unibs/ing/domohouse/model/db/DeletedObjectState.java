package it.unibs.ing.domohouse.model.db;

public class DeletedObjectState implements ObjectState {

	@Override
	public Query getUpdateQuery(Saveable context) {
		return context.getDeleteQuery();
	}

}
