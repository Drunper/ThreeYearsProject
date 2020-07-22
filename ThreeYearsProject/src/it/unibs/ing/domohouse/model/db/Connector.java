package it.unibs.ing.domohouse.model.db;

import java.sql.*;
import java.util.Map.Entry;

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

	public void openConnection() {
		try {
			connection = DriverManager.getConnection(url, user, password);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setIntParameter(int position, int parameter) {
		try {
			preparedStatement.setInt(position, parameter);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setStringParameter(int position, String parameter) {
		try {
			preparedStatement.setString(position, parameter);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void submitParametrizedQuery(String query) {
		try {
			preparedStatement = connection.prepareStatement(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery() {
		ResultSet res = null;
		try {
			res = preparedStatement.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public ResultSet executeQuery(String query) {
		ResultSet res = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			res = preparedStatement.executeQuery();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public void execute() {
		try {
			preparedStatement.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void executeQueryWithoutResult(Query query) {
		if(query != null)
			try {
				setQuery(query);
				preparedStatement.execute();
				preparedStatement.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public ResultSet executeQuery(Query query) {
		ResultSet set = null;
		try {
			setQuery(query);
			set = preparedStatement.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return set;
	}
	
	private void setQuery(Query query) {
		try {
			preparedStatement = connection.prepareStatement(query.getQuery());
			for(Entry<Integer, Integer> param : query.getIntegerParameters().entrySet())
				preparedStatement.setInt(param.getKey(), param.getValue());
			for(Entry<Integer, String> param : query.getStringParameters().entrySet())
				preparedStatement.setString(param.getKey(), param.getValue());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeStatement() {
		if (preparedStatement != null)
			try {
				preparedStatement.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
