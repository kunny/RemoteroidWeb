package org.secmem.remoteroid.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@Path("/account")
public class AccountREST {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/hello={name}")
	public String hello(
			@PathParam("name")final String name){
		Key accountKey  = KeyFactory.createKey("Remoteroid", "Account"); // Table?
		
		Entity newAccount = new Entity("Account", accountKey); // Tuple
		newAccount.setProperty("name", name); // Columns
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key inskey = datastore.put(newAccount);
		
		return "Hello, "+name+", key="+inskey.toString();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/retrieve")
	public String getResult(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query("Account");
		
		PreparedQuery pq = datastore.prepare(q);
		
		return pq.asList(FetchOptions.Builder.withDefaults()).toString();
		
	}
}
