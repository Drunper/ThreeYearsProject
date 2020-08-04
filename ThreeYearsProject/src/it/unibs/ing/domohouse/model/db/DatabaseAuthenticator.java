package it.unibs.ing.domohouse.model.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.unibs.ing.domohouse.model.ModelStrings;
import it.unibs.ing.domohouse.model.util.Authenticator;
import it.unibs.ing.domohouse.model.util.HashCalculator;

public class DatabaseAuthenticator implements Authenticator {

	private HashCalculator hashCalculator;
	private Connector connector;

	public DatabaseAuthenticator(HashCalculator hashCalculator, Connector connector) throws Exception {
		this.hashCalculator = hashCalculator;
		this.connector = connector;
		connector.openConnection();
	}

	@Override
	public boolean checkMaintainerPassword(String user, String password) throws Exception {
		boolean result = false;
		Query query = new Query(QueryStrings.GET_MAINTAINER_PASSWORD_AND_SALT);
		query.setStringParameter(1, user);
		try (ResultSet set = connector.executeQuery(query)) {
			if (set.next()) {
				result = set.getString("password").equalsIgnoreCase(
						hashCalculator.hash(password, hashCalculator.hexToBytes(set.getString("sale"))));
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return result;
	}

	@Override
	public void addMaintainer(String user, String password) throws Exception {
		Query query = new Query(QueryStrings.INSERT_MAINTAINER);
		byte[] salt = hashCalculator.getSalt();
		query.setStringParameter(1, user);
		query.setStringParameter(2, hashCalculator.hash(password, salt));
		query.setStringParameter(3, hashCalculator.bytesToHex(salt));
		connector.executeQueryWithoutResult(query);
	}

	@Override
	public boolean checkUser(String user) throws Exception {
		boolean result = false;
		Query query = new Query(QueryStrings.CHECK_USER);
		query.setStringParameter(1, user);
		try (ResultSet set = connector.executeQuery(query);) {
			result = set.next();
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_EXCEPTION, e);
		}
		return result;
	}

}
