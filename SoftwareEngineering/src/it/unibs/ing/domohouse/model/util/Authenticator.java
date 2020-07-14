package it.unibs.ing.domohouse.model.util;

public interface Authenticator {
	boolean checkPassword(String user, String password);
	void addEntry(String user, String password);
}
