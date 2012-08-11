package org.secmem.remoteroid.server.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseErrorResponse extends BaseResponse {

	protected int errorCode = Codes.Error.GENERAL;
	
	public BaseErrorResponse(){
		super(Codes.Result.FAILED);
	}
	
	public BaseErrorResponse(int errorCode){
		this();
		this.errorCode = errorCode;
	}
	
	public int getErrorCode(){
		return this.errorCode;
	}
	
	public void setErrorCode(int errorCode){
		this.errorCode = errorCode;
	}
}
