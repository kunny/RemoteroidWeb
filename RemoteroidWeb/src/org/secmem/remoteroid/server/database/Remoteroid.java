package org.secmem.remoteroid.server.database;

import javax.xml.bind.annotation.XmlRootElement;

public class Remoteroid {
	
	@XmlRootElement
	public static class Account{
		public static final String _NAME = "Account";
		
		public static final String EMAIL = "email";
		public static final String PASSWORD = "password";
		public static final String TOKEN = "token";
		
		private String email;
		private String password;
		private String token;
		
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

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
		
	}
	
	@XmlRootElement
	public static class Devices{
		public static final String _NAME = "Devices";
		
		public static final String OWNER_EMAIL = "owner_email";
		public static final String NICKNAME = "nickname";
		public static final String REGISTRATION_KEY = "reg_key";
		
		private String ownerEmail;
		private String nickname;
		private String registrationKey;
		
		public Devices(){
			
		}

		public String getOwnerEmail() {
			return ownerEmail;
		}

		public void setOwnerEmail(String ownerEmail) {
			this.ownerEmail = ownerEmail;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getRegistrationKey() {
			return registrationKey;
		}

		public void setRegistrationKey(String registrationKey) {
			this.registrationKey = registrationKey;
		}
		
	}

}
