package org.secmem.remoteroid.server.database;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Device{
	public static final String _NAME = "Devices";
	
	public static final String OWNER_EMAIL = "owner_email";
	public static final String NICKNAME = "nickname";
	public static final String REGISTRATION_KEY = "reg_key";
	public static final String DEVICE_UUID = "device_uuid";
	
	private Account ownerAccount;
	private String nickname;
	private String registrationKey;
	private String deviceUUID;
	
	public Device(){
		
	}

	public Account getOwnerAccount() {
		return ownerAccount;
	}

	public void setOwnerAccount(Account ownerAccount) {
		this.ownerAccount = ownerAccount;
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

	public String getUUID() {
		return deviceUUID;
	}

	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}
	
}
