/*
 * Remoteroid Web Service
 * Copyright(c) 2012 Taeho Kim (jyte82@gmail.com)
 * 
 * This project aims to support 'Remote-connect' feature, 
 * which user can connect to the phone from PC, without any control on the phone.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.secmem.remoteroid.server.database.support;

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
