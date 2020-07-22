package it.unibs.ing.domohouse.model.db;

import java.util.Collections;

import it.unibs.ing.domohouse.model.components.elements.Room;

public class SaveableRoom implements Saveable {

	private String user;
	private String housingUnit;
	private Room room;
	private ObjectState objectState;

	public SaveableRoom(String user, String housingUnit, Room room, ObjectState objectState) {
		this.user = user;
		this.housingUnit = housingUnit;
		this.room = room;
		this.objectState = objectState;
	}

	@Override
	public Query getModifyQuery() {
		Query query = new Query(QueryStrings.UPDATE_ROOM);
		query.setStringParameter(1, room.getDescr());
		query.setStringParameter(2, room.getName());
		query.setStringParameter(3, housingUnit);
		query.setStringParameter(4, user);
		return query;
	}

	@Override
	public Query getInsertionQuery() {
		String queryString = QueryStrings.INSERT_ROOM;
		Query query = new Query("");
		query.setStringParameter(1, room.getName());
		query.setStringParameter(2, housingUnit);
		query.setStringParameter(3, user);
		query.setStringParameter(4, room.getDescr());

		if (room.doesPropertyExist()) {
			int pos = 5;
			for (String property : room.getPropertiesNameSet()) {
				query.setStringParameter(pos++, room.getName());
				query.setStringParameter(pos++, housingUnit);
				query.setStringParameter(pos++, user);
				query.setStringParameter(pos++, property);
			}
			queryString += " " + QueryStrings.INSERT_ROOM_PROPERTY + String.join(", ",
					Collections.nCopies(room.getPropertiesNameSet().size(), QueryStrings.FOUR_VALUES)) + ";";
		}
		query.setQuery(queryString);
		return query;
	}

	@Override
	public Query getDeleteQuery() {
		Query query = new Query(QueryStrings.DELETE_ROOM);
		query.setStringParameter(1, room.getName());
		query.setStringParameter(2, housingUnit);
		query.setStringParameter(3, user);
		return query;
	}

	@Override
	public Query getUpdateQuery() {
		return objectState.getUpdateQuery(this);
	}

	@Override
	public void setObjectState(ObjectState objectState) {
		this.objectState = objectState;
	}

	@Override
	public void modify() {
		objectState.modify(this);
	}

	@Override
	public void delete() {
		objectState.delete(this);
	}
}
