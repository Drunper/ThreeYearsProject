package it.unibs.ing.domohouse.model.db;

import java.util.HashMap;
import java.util.Map;

public class Query {

	private String query;
	private Map<Integer, Integer> integerParameters;
	private Map<Integer, String> stringParameters;
	private Map<Integer, Double> doubleParameters;
	
	public Query(String query) {
		this.query = query;
		integerParameters = new HashMap<>();
		stringParameters = new HashMap<>();
		doubleParameters = new HashMap<>();
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
	
	public void setDoubleParameter(int position, double value) {
		doubleParameters.put(position, value);
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
	
	public Map<Integer, Double> getDoubleParameters() {
		return doubleParameters;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
}
