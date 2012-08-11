package org.secmem.remoteroid.server.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseResponse {
	
	protected int result = Codes.Result.OK;
	
	public BaseResponse(){
		
	}
	
	protected BaseResponse(int result){
		this.result = result;
	}
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public boolean isSucceed(){
		return result==Codes.Result.OK ? true : false;
	}
}
