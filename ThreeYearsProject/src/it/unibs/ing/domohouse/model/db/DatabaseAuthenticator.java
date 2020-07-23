package it.unibs.ing.domohouse.model.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.unibs.ing.domohouse.model.util.Authenticator;
import it.unibs.ing.domohouse.model.util.HashCalculator;

public class DatabaseAuthenticator implements Authenticator {

	private HashCalculator hashCalculator;
	private Connector connector;
	
	public DatabaseAuthenticator(HashCalculator hashCalculator, Connector connector) {
		this.hashCalculator = hashCalculator;
		this.connector = connector;
		connector.openConnection();
	}
	
	@Override
	public boolean checkMaintainerPassword(String user, String password) {
		boolean result = false;
		Query query = new Query(QueryStrings.GET_MAINTAINER_PASSWORD_AND_SALT);
		query.setStringParameter(1, user);
		try (ResultSet set = connector.executeQuery(query)) {
			if(set.next()) {
				result = set.getString("password").equalsIgnoreCase(hashCalculator.hash(password, hashCalculator.hexToBytes(set.getString("sale"))));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void addMaintainer(String user, String password) {
		Query query = new Query(QueryStrings.INSERT_MAINTAINER);
		byte[] salt = hashCalculator.getSalt();
		query.setStringParameter(1, user);
		query.setStringParameter(2, hashCalculator.hash(password, salt));
		connector.executeQueryWithoutResult(query);
		//gestire bene le eccezioni
	}
	
	@Override
	public boolean checkUser(String user) {
		boolean result = false;
		Query query = new Query(QueryStrings.CHECK_USER);
		query.setStringParameter(1, user);
		try (ResultSet set = connector.executeQuery(query);) {
			result = set.next();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
