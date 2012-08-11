package org.secmem.remoteroid.server.response;

import javax.xml.bind.annotation.XmlRootElement;

import org.secmem.remoteroid.server.database.Remoteroid.Account;

@XmlRootElement
public class AccountResponse extends BaseResponse {
	
	private Account account;
	
	public AccountResponse(){
		super();
	}
	
	public AccountResponse(Account account){
		this();
		this.account = account;
	}
	
	public Account getAccount(){
		return this.account;
	}
}
