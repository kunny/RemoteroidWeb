package org.secmem.remoteroid.server.database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5PasswordObfuscator implements PasswordObfuscator {

	@Override
	public String generate(String password) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			return new String(digest.digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean matches(String obfuscatedPassword, String rawPassword) {
		return obfuscatedPassword.equals(generate(rawPassword));
	}

}
