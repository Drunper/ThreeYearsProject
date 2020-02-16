package it.unibs.ing.domohouse;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class HomeLogin {

	private HashMap<String, String> maintainers;
	private MessageDigest md;
	private byte[] hash;
	private String hexHash;
	/*
	 * invariante: maintainers != null
	 */
	public HomeLogin () {
		maintainers = new HashMap<>();
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.err.println("Algorithm not found");
		}
	}
	
	private void hash (String toHash) {
		assert toHash != null;
		hash = md.digest(toHash.getBytes(StandardCharsets.UTF_8));
		hexHash = bytesToHex(hash);
		assert homeLoginInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public void addEntry (String maintainerID, String passwordHash) {
		assert homeLoginInvariant() : "Invariante di classe non soddisfatto";
		assert maintainerID != null && passwordHash != null;
		int pre_size = maintainers.size();
		
		maintainers.put(maintainerID, passwordHash);
		
		assert maintainers.size() >= pre_size;
		assert homeLoginInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public boolean checkPassword (String maintainerID, String password) {
		assert maintainerID != null && password != null;
		assert homeLoginInvariant() : "Invariante di classe non soddisfatto";
		
		if (!maintainers.containsKey(maintainerID))
			return false;
		else
		{
			hash(password);
			return maintainers.get(maintainerID).equalsIgnoreCase(hexHash);
		}
	}
	
	private static String bytesToHex (byte[] hash) {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    	String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	private boolean homeLoginInvariant() {
		boolean checkMap = maintainers != null;
		return checkMap;
	}
}
