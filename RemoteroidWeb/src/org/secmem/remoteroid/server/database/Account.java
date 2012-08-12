package org.secmem.remoteroid.server.database;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account{
	public static final String _NAME = "Account";
	
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	
	private String email;
	private String password;
	
	public Account(){
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString(){
		return "Account [email="+email+", password="+password+"]";
	}
	
}
