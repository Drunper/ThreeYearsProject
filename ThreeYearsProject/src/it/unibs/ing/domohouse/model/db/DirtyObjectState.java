package it.unibs.ing.domohouse.model.db;

public class DirtyObjectState implements ObjectState {

	@Override
	public Query getUpdateQuery(Saveable context) {
		return context.getUpdateQuery();
	}

	@Override
	public void modify(Saveable context) {
	}

	@Override
	public void delete(Saveable context) {
		context.setObjectState(new DeletedObjectState());
	}

}
