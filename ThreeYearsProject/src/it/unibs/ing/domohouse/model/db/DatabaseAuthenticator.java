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
		connector.submitParametrizedQuery("SELECT *" + 
				" FROM manutentore" + 
				" WHERE nome_manutentore =? AND password =?");
		connector.setStringParameter(1, user);
		connector.setStringParameter(2, hashCalculator.hash(password));
		ResultSet set = connector.executeQuery();
		try {
			result = set.next();
		}
		catch (SQLException e) {
			// log eccezione
		}
		return result;
	}

	@Override
	public void addMaintainer(String user, String password) {
		connector.submitParametrizedQuery("INSERT INTO manutentore (nome_manutentore, password) VALUES (?, ?)");
		connector.setStringParameter(1, user);
		connector.setStringParameter(2, hashCalculator.hash(password));
		connector.execute();
		connector.closeStatement();
		//gestire bene le eccezioni
	}
	
	@Override
	public boolean checkUser(String user) {
		boolean result = false;
		connector.submitParametrizedQuery("SELECT *" + 
				" FROM utente" + 
				" WHERE nome_utente =?");
		connector.setStringParameter(1, user);
		ResultSet set = connector.executeQuery();
		try {
			result = set.next();
		}
		catch (SQLException e) {
			// log eccezione
		}
		return result;
	}

}
