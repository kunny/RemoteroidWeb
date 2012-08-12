package org.secmem.remoteroid.server;

import java.util.List;

import org.secmem.remoteroid.server.database.Account;
import org.secmem.remoteroid.server.database.Device;

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
}
