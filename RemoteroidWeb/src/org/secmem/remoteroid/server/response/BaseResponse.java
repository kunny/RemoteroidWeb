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
