package it.unibs.ing.domohouse.model.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import it.unibs.ing.domohouse.model.ModelStrings;

public class HashCalculator {

	private MessageDigest md;
	SecureRandom sr;
	
	public HashCalculator() {
		try {
			md = MessageDigest.getInstance(ModelStrings.SHA_256);
			sr = SecureRandom.getInstance(ModelStrings.SHA1PRNG);
		}
		catch (NoSuchAlgorithmException e) {
			//TOLOG
			//throware
			e.printStackTrace();
		}
	}

	public String hash(String toHash, byte[] salt) {
		assert toHash != null;
		md.update(salt);
		return bytesToHex(md.digest(toHash.getBytes(StandardCharsets.UTF_8)));
	}
	
	public byte[] getSalt() {
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
	}
	
	public String bytesToHex(byte[] hash) {
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
	
	public byte[] hexToBytes(String s) {
	    int length = s.length();
	    byte[] result = new byte[length / 2];
	    for (int i = 0; i < length; i += 2) {
	        result[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return result;
	}
}
