package it.unibs.ing.domohouse.model.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import it.unibs.ing.domohouse.model.ModelStrings;

public class HashCalculator {

	private MessageDigest md;
	
	public HashCalculator() {
		try {
			md = MessageDigest.getInstance(ModelStrings.SHA_256);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.err.println(ModelStrings.ALGORITHM_ERROR);
		}
	}

	public String hash(String toHash) {
		assert toHash != null;
		return bytesToHex(md.digest(toHash.getBytes(StandardCharsets.UTF_8)));
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
}
