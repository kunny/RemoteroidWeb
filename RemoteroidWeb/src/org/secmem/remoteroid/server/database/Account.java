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
package org.secmem.remoteroid.server.database;

import javax.xml.bind.annotation.XmlRootElement;

import org.secmem.remoteroid.server.database.support.PasswordObfuscator;

/**
 * Contains data represents each account.
 * @author Taeho Kim
 *
 */
@XmlRootElement
public class Account{
	public static final String _NAME = "Account";
	
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	
	/**
	 * User's E-mail address.<br/>
	 * Used to identify each user, hence this should not be duplicated.
	 */
	private String email;
	
	/**
	 * User's password.<br/>
	 * May contain raw password or hashed one for security.
	 */
	private String password;
	
	/**
	 * Default constructor for <code>Account</code>
	 */
	public Account(){
		
	}

	/**
	 * Returns user's E-mail address.
	 * @return an E-mail address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set user's E-mail address.
	 * @param email an E-mail address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Return user's password
	 * @return raw password if this object is made in Login procedure, hashed password otherwise.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set user's password
	 * @param password a password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Obfuscate raw password.
	 * @param obfuscator a PasswordObfuscator
	 */
	public void obfuscatePassword(PasswordObfuscator obfuscator){
		this.password = obfuscator.generate(this.password);
	}
	
	@Override
	public String toString(){
		return "Account [email="+email+", password="+password+"]";
	}
	
}
