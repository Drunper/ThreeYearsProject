package it.unibs.ing.domohouse.model.db.persistent;

import it.unibs.ing.domohouse.model.db.Query;

public class NewObjectState implements PersistentObjectState {

	@Override
	public Query getUpdateQuery(PersistentObject context) {
		return context.getInsertionQuery();
	}

	@Override
	public void modify(PersistentObject context) {		
	}

	@Override
	public void delete(PersistentObject context) {
		context.setObjectState(new PersistentObjectState() { //come se non avessi mai inserito l'oggetto, non devo fare niente

			@Override
			public Query getUpdateQuery(PersistentObject context) {
				return null;
			}

			@Override
			public void modify(PersistentObject context) {				
			}

			@Override
			public void delete(PersistentObject context) {				
			}});
	}

}
