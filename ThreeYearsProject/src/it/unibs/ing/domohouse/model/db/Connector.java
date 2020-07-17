package it.unibs.ing.domohouse.model.db;

import java.sql.*;

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

	public void execute() {
		try {
			preparedStatement.execute();
		}
		catch (Exception e) {
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
