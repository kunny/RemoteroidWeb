package org.secmem.remoteroid.server.response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.secmem.remoteroid.server.database.Account;
import org.secmem.remoteroid.server.database.Device;

@XmlRootElement
@XmlSeeAlso({Account.class, Device.class})
public class ObjectResponse<T> extends BaseResponse {
	
	private T data;
	
	public ObjectResponse(){
		super();
	}
	
	public ObjectResponse(T data){
		this();
		this.data = data;
	}
	
	public void setData(T data){
		this.data = data;
	}
	
	public T getData(){
		return this.data;
	}
}
