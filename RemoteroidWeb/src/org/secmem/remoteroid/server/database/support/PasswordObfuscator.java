package org.secmem.remoteroid.server.database.support;

public interface PasswordObfuscator {
	public String generate(String password);
	public boolean matches(String obfuscatedPassword, String rawPassword);
}
