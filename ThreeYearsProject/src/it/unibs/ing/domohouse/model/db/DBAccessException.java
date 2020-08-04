package it.unibs.ing.domohouse.model.db;

public class DBAccessException extends Exception {

	private static final long serialVersionUID = -4603321572205539797L;
	
	public DBAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
