package it.unibs.ing.domohouse.model.db;

import java.sql.*;
import java.util.Map.Entry;

import it.unibs.ing.domohouse.model.ModelStrings;

public class Connector {

	private String url;
	private String user;
	private String password;
	private Connection connection;
	private PreparedStatement preparedStatement;

	public Connector(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public void openConnection() throws Exception {
		try {
			connection = DriverManager.getConnection(url, user, password);
		}
		catch (Exception e) {
			throw new Exception(ModelStrings.DB_CONNECTION_EXCEPTION, e);
		}
	}

	public void closeConnection() {
		try {
			connection.close();
		}
		catch (Exception e) {
			//Non dovrebbe accadere
		}
	}

	public ResultSet executeQuery(String query) throws Exception {
		ResultSet res = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			res = preparedStatement.executeQuery();
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_EXCEPTION, e);
		}
		return res;
	}
	
	public ResultSet executeQuery(Query query) throws DBAccessException, DBParametersException {
		ResultSet set = null;
		try {
			setQuery(query);
			set = preparedStatement.executeQuery();
		}
		catch (SQLException e) {
			throw new DBAccessException(ModelStrings.DB_QUERY_EXCEPTION, e);
		}
		catch (DBParametersException e) {
			throw e;
		}
		return set;
	}

	public void executeQueryWithoutResult(Query query) throws DBAccessException, DBParametersException {
		if (query != null)
			try {
				setQuery(query);
				preparedStatement.execute();
				preparedStatement.close();
			}
		catch (SQLException e) {
			throw new DBAccessException(ModelStrings.DB_QUERY_EXCEPTION, e);
		}
		catch (DBParametersException e) {
			throw e;
		}
	}

	private void setQuery(Query query) throws DBParametersException {
		try {
			preparedStatement = connection.prepareStatement(query.getQuery());
			for (Entry<Integer, Integer> param : query.getIntegerParameters().entrySet())
				preparedStatement.setInt(param.getKey(), param.getValue());
			for (Entry<Integer, String> param : query.getStringParameters().entrySet())
				preparedStatement.setString(param.getKey(), param.getValue());
			for (Entry<Integer, Double> param : query.getDoubleParameters().entrySet())
				preparedStatement.setDouble(param.getKey(), param.getValue());
		}
		catch (SQLException e) {
			throw new DBParametersException(ModelStrings.DB_QUERY_PARAMETERS_EXCEPTION, e);
		}
	}
}
