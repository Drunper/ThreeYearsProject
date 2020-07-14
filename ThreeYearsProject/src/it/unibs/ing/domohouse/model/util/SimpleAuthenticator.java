package it.unibs.ing.domohouse.model.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import it.unibs.ing.domohouse.model.ModelStrings;

public class SimpleAuthenticator implements Authenticator {

	private HashMap<String, String> maintainers;
	private MessageDigest md;
	private byte[] hash;
	private String hexHash;

	/*
	 * invariante: maintainers != null
	 */
	public SimpleAuthenticator() {
		maintainers = new HashMap<>();
		try {
			md = MessageDigest.getInstance(ModelStrings.SHA_256);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.err.println(ModelStrings.ALGORITHM_ERROR);
		}
	}

	private void hash(String toHash) {
		assert toHash != null;
		hash = md.digest(toHash.getBytes(StandardCharsets.UTF_8));
		hexHash = bytesToHex(hash);
		assert homeLoginInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addEntry(String maintainerID, String passwordHash) {
		assert homeLoginInvariant() : ModelStrings.WRONG_INVARIANT;
		assert maintainerID != null && passwordHash != null;
		int pre_size = maintainers.size();

		maintainers.put(maintainerID, passwordHash);

		assert maintainers.size() >= pre_size;
		assert homeLoginInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean checkPassword(String maintainerID, String password) {
		assert maintainerID != null && password != null;
		assert homeLoginInvariant() : ModelStrings.WRONG_INVARIANT;

		if (!maintainers.containsKey(maintainerID))
			return false;
		else {
			hash(password);
			return maintainers.get(maintainerID).equalsIgnoreCase(hexHash);
		}
	}

	private static String bytesToHex(byte[] hash) {
		assert hash != null;

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}

		String result = hexString.toString();

		assert result != null;
		return result;
	}

	private boolean homeLoginInvariant() {
		boolean checkMap = maintainers != null;
		return checkMap;
	}
}