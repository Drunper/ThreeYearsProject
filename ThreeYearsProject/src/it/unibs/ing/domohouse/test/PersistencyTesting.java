package it.unibs.ing.domohouse.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibs.ing.domohouse.model.db.Query;
import it.unibs.ing.domohouse.model.db.persistent.DeletedObjectState;
import it.unibs.ing.domohouse.model.db.persistent.DirtyObjectState;
import it.unibs.ing.domohouse.model.db.persistent.NewObjectState;
import it.unibs.ing.domohouse.model.db.persistent.OldObjectState;
import it.unibs.ing.domohouse.model.db.persistent.PersistentObject;
import it.unibs.ing.domohouse.model.db.persistent.PersistentObjectState;

public class PersistencyTesting {

	@Test
	public void testNewToNewTransition() {
		PersistentObjectState state = new NewObjectState();
		PersistentObject object = new PersistentObject(state) {

			@Override
			public Query getModifyQuery() {
				return null;
			}

			@Override
			public Query getInsertionQuery() {
				return null;
			}

			@Override
			public Query getDeleteQuery() {
				return null;
			}
		};
		object.modify();
		assertTrue(object.getObjectState() instanceof NewObjectState);
	}
	
	@Test
	public void testNewToNullTransition() {
		PersistentObjectState state = new NewObjectState();
		PersistentObject object = new PersistentObject(state) {

			@Override
			public Query getModifyQuery() {
				return null;
			}

			@Override
			public Query getInsertionQuery() {
				return null;
			}

			@Override
			public Query getDeleteQuery() {
				return null;
			}
		};
		object.delete();
		assertTrue(!(object.getObjectState() instanceof NewObjectState || object.getObjectState() instanceof OldObjectState
						|| object.getObjectState() instanceof DirtyObjectState
						|| object.getObjectState() instanceof DeletedObjectState));
	}
	
	@Test
	public void testOldToDirtyTransition() {
		PersistentObjectState state = new OldObjectState();
		PersistentObject object = new PersistentObject(state) {

			@Override
			public Query getModifyQuery() {
				return null;
			}

			@Override
			public Query getInsertionQuery() {
				return null;
			}

			@Override
			public Query getDeleteQuery() {
				return null;
			}
		};
		object.modify();
		assertTrue(object.getObjectState() instanceof DirtyObjectState);
	}
	
	@Test
	public void testOldToDeletedTransition() {
		PersistentObjectState state = new OldObjectState();
		PersistentObject object = new PersistentObject(state) {

			@Override
			public Query getModifyQuery() {
				return null;
			}

			@Override
			public Query getInsertionQuery() {
				return null;
			}

			@Override
			public Query getDeleteQuery() {
				return null;
			}
		};
		object.delete();
		assertTrue(object.getObjectState() instanceof DeletedObjectState);
	}
	
	@Test
	public void testDirtyToDirtyTransition() {
		PersistentObjectState state = new DirtyObjectState();
		PersistentObject object = new PersistentObject(state) {

			@Override
			public Query getModifyQuery() {
				return null;
			}

			@Override
			public Query getInsertionQuery() {
				return null;
			}

			@Override
			public Query getDeleteQuery() {
				return null;
			}
		};
		object.modify();
		assertTrue(object.getObjectState() instanceof DirtyObjectState);
	}
	
	@Test
	public void testDirtyToDeletedTransition() {
		PersistentObjectState state = new DirtyObjectState();
		PersistentObject object = new PersistentObject(state) {

			@Override
			public Query getModifyQuery() {
				return null;
			}

			@Override
			public Query getInsertionQuery() {
				return null;
			}

			@Override
			public Query getDeleteQuery() {
				return null;
			}
		};
		object.delete();
		assertTrue(object.getObjectState() instanceof DeletedObjectState);
	}
}
