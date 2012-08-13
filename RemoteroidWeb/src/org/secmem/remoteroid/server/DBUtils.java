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
package org.secmem.remoteroid.server;

import java.util.List;

import org.secmem.remoteroid.server.database.Account;
import org.secmem.remoteroid.server.database.Device;
import org.secmem.remoteroid.server.database.Gcm;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class DBUtils {
	
	protected Key insertOrUpdate(String kind, Entity entity){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		return datastore.put(entity);
	}
	
	protected static List<Entity> query(Query query){
		return query(query, FetchOptions.Builder.withDefaults());
	}
	
	protected static List<Entity> query(Query query, FetchOptions options){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		return datastore.prepare(query).asList(options);
	}
	
	protected Key getRemoteroidKey(String kind){
		return KeyFactory.createKey("Remoteroid", kind);
	}
	
	protected Entity getAccountEntity(){
		return new Entity(Account._NAME, getRemoteroidKey(Account._NAME));
	}
	
	protected Entity getDeviceEntity(){
		return new Entity(Device._NAME, getRemoteroidKey(Device._NAME));
	}
	
	protected String getGcmAPIKey(){
		Query q = new Query(Gcm._NAME);
		List<Entity> result = query(q);
		if(result.size()==0){
			throw new IllegalStateException("There are no entity for GCM API key. Please create Entity for GCM API key by accessing http://[your appengine domain]/apis/admin/init.");
		}else{
			return (String)result.get(0).getProperty(Gcm.API_KEY);
		}
	}
	
}
