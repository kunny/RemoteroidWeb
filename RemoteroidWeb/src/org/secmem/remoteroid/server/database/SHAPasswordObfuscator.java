package org.secmem.remoteroid.server.database;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAPasswordObfuscator implements PasswordObfuscator {

	@Override
	public String generate(String password) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA");
			digest.update(password.getBytes("UTF-8"));
			
			byte[] raw = digest.digest();
			int rawByteLength = raw.length;
			
			StringBuilder builder = new StringBuilder();
			for(int i=0; i<rawByteLength; ++i){
				builder.append(Integer.toHexString(0xFF & raw[i]));
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean matches(String obfuscatedPassword, String rawPassword) {
		return obfuscatedPassword.equals(generate(rawPassword));
	}

}
