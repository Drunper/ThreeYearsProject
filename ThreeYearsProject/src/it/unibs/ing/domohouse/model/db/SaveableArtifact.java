package it.unibs.ing.domohouse.model.db;

import java.util.Collections;

import it.unibs.ing.domohouse.model.components.elements.Artifact;

public class SaveableArtifact implements Saveable {

	private String user;
	private String housingUnit;
	private String location;
	private Artifact artifact;
	private ObjectState objectState;
	
	public SaveableArtifact(String user, String housingUnit, String location, Artifact artifact,
			ObjectState objectState) {
		this.user = user;
		this.housingUnit = housingUnit;
		this.location = location;
		this.artifact = artifact;
		this.objectState = objectState;
	}

	@Override
	public Query getModifyQuery() {
		Query query = new Query(QueryStrings.UPDATE_ARTIFACT);
		query.setStringParameter(1, artifact.getDescr());
		query.setStringParameter(2, artifact.getName());
		query.setStringParameter(3, housingUnit);
		query.setStringParameter(4, user);
		return query;
	}
	
	@Override
	public Query getInsertionQuery() {
		String queryString = QueryStrings.INSERT_ARTIFACT;
		Query query = new Query("");
		query.setStringParameter(1, artifact.getName());
		query.setStringParameter(2, housingUnit);
		query.setStringParameter(3, user);
		query.setStringParameter(4, artifact.getDescr());
		query.setStringParameter(5, location);
		
		if (artifact.doesPropertyExist()) {
			int pos = 6;
			for (String property : artifact.getPropertiesNameSet()) {
				query.setStringParameter(pos++, artifact.getName());
				query.setStringParameter(pos++, housingUnit);
				query.setStringParameter(pos++, user);
				query.setStringParameter(pos++, property);
			}
			queryString += QueryStrings.INSERT_ARTIFACT_PROPERTY + String.join(", ",
					Collections.nCopies(artifact.getPropertiesNameSet().size(), QueryStrings.FOUR_VALUES)) + ";";
		}
		
		query.setQuery(queryString);
		return query;
	}

	@Override
	public Query getDeleteQuery() {
		Query query = new Query(QueryStrings.DELETE_ARTIFACT);
		query.setStringParameter(1, artifact.getName());
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
