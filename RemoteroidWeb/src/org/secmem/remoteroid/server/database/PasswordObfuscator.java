package org.secmem.remoteroid.server.database;

public interface PasswordObfuscator {
	public String generate(String password);
	public boolean matches(String obfuscatedPassword, String rawPassword);
}
