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
