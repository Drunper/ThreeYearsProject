package it.unibs.ing.domohouse.model.db;

public class OldObjectState implements ObjectState {

	@Override
	public Query getUpdateQuery(Saveable context) {
		return null;
	}

	@Override
	public void modify(Saveable context) {
		context.setObjectState(new DirtyObjectState());
	}

	@Override
	public void delete(Saveable context) {
		context.setObjectState(new DeletedObjectState());
	}

}
