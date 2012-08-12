package org.secmem.remoteroid.server.database;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso(Device.class)
public class WakeupMessage {
	public static final String IP_ADDRESS = "ip_address";
	
	private Device device;
	private String serverIpAddress;
	
	public WakeupMessage(){
		
	}
	
	public WakeupMessage(String ipAddress){
		this();
		this.serverIpAddress = ipAddress;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getServerIpAddress() {
		return serverIpAddress;
	}

	public void setServerIpAddress(String serverIpAddress) {
		this.serverIpAddress = serverIpAddress;
	}
	
}
