package it.unibs.ing.domohouse.model.db;

import java.util.HashMap;
import java.util.Map;

public class Query {

	private String query;
	private Map<Integer, Integer> integerParameters;
	private Map<Integer, String> stringParameters;
	
	public Query(String query) {
		this.query = query;
		integerParameters = new HashMap<>();
		stringParameters = new HashMap<>();
	}
	
	public Query(String query, Map<Integer, Integer> integerParameters, Map<Integer, String> stringParameters) {
		this.query = query;
		this.integerParameters = integerParameters;
		this.stringParameters = stringParameters;
	}
	
	public void setStringParameter(int position, String value) {
		stringParameters.put(position, value);
	}
	
	public void setIntegerParameter(int position, int value) {
		integerParameters.put(position, value);
	}
	
	public void setStringParameter(String value, int...positions) {
		for(int position : positions)
			stringParameters.put(position, value);
	}
	
	public Map<Integer, Integer> getIntegerParameters() {
		return integerParameters;
	}
	
	public Map<Integer, String> getStringParameters() {
		return stringParameters;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
}
