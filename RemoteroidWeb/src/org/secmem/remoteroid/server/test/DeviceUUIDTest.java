package org.secmem.remoteroid.server.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.secmem.remoteroid.server.database.support.SHAObfuscator;

public class DeviceUUIDTest {
	
	@Test
	public void testDeviceUUID(){
		SHAObfuscator obfuscator = new SHAObfuscator();
		String uuid = "12:12:23:34:45:56";
		String obfuscated = obfuscator.generate(uuid);
		assertTrue(obfuscator.matches(obfuscated, uuid));
	}

}
