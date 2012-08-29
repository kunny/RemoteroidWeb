package org.secmem.remoteroid.server.response;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.secmem.remoteroid.server.database.Account;
import org.secmem.remoteroid.server.database.Device;

@XmlRootElement
@XmlSeeAlso({Device.class,Account.class})
public class DeviceListResponse extends BaseResponse {
	private ArrayList<Device> data;
	
	public DeviceListResponse(){
		
	}
	
	public DeviceListResponse(ArrayList<Device> list){
		this.data = list;
	}
	
	public ArrayList<Device> getData(){
		return data;
	}
	
	public void setData(ArrayList<Device> data){
		this.data = data;
	}
	
}
