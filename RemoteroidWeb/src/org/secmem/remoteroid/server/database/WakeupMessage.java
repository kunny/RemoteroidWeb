package org.secmem.remoteroid.server.database;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Contains data regarding Wake-up message(Remote connection) from server.
 * @author Taeho Kim
 *
 */
@XmlRootElement
@XmlSeeAlso(Device.class)
public class WakeupMessage {
	
	public static final String IP_ADDRESS = "ip_address";
	/**
	 * A device will be waked up for
	 */
	private Device device;
	
	/**
	 * Server's IP Address, where device should connect to
	 */
	private String serverIpAddress;
	
	/**
	 * Default constructor for <code>WakeupMessage</code>
	 */
	public WakeupMessage(){
		
	}
	
	/**
	 * Constructs <code>WakeupMessage</code> with given server's IP Address.
	 * @param ipAddress Server's IP Address where remote device should connect to
	 */
	public WakeupMessage(String ipAddress){
		this();
		this.serverIpAddress = ipAddress;
	}
	
	/**
	 * Get device information.
	 * @return a device information
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * Set message device to be waked up.
	 * @param device a device
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * Get server's IP Address.
	 * @return a server's IP address
	 */
	public String getServerIpAddress() {
		return serverIpAddress;
	}

	/**
	 * Set server's IP Address.
	 * @param serverIpAddress a server's IP Address
	 */
	public void setServerIpAddress(String serverIpAddress) {
		this.serverIpAddress = serverIpAddress;
	}
	
}
