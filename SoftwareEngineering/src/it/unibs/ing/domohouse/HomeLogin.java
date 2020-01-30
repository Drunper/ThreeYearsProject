package it.unibs.ing.domohouse;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class HomeLogin {

	HashMap<String, String> maintainers;
	MessageDigest md;
	byte[] hash;
	String hexHash;
	
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
		hash = md.digest(toHash.getBytes(StandardCharsets.UTF_8));
		hexHash = bytesToHex(hash);
	}
	
	public void addEntry (String maintainerID, String passwordHash) {
		maintainers.put(maintainerID, passwordHash);
	}
	
	public boolean checkPassword (String maintainerID, String password) {
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
}
