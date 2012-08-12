package org.secmem.remoteroid.server.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.secmem.remoteroid.server.database.SHAPasswordObfuscator;
import org.secmem.remoteroid.server.database.PasswordObfuscator;

public class PasswordObfuscatorTest {

	@Test
	public void test() {
		PasswordObfuscator obfuscator = new SHAPasswordObfuscator();
		String password = "remoteroid";
		String obfuscated = obfuscator.generate(password);
		
		assertTrue(obfuscator.matches(obfuscated, password));
	}

}
