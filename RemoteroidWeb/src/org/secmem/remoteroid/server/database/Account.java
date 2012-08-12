package org.secmem.remoteroid.server.database;

import javax.xml.bind.annotation.XmlRootElement;

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
	
	@Override
	public String toString(){
		return "Account [email="+email+", password="+password+"]";
	}
	
}
