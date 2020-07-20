package it.unibs.ing.domohouse.model.db;

public class NewObjectState implements ObjectState {

	@Override
	public Query getUpdateQuery(Saveable context) {
		return context.getInsertionQuery();
	}

	@Override
	public void modify(Saveable context) {		
	}

	@Override
	public void delete(Saveable context) {
		context.setObjectState(new ObjectState() { //come se non avessi mai inserito l'oggetto, non devo fare niente

			@Override
			public Query getUpdateQuery(Saveable context) {
				return null;
			}

			@Override
			public void modify(Saveable context) {				
			}

			@Override
			public void delete(Saveable context) {				
			}});
	}

}
