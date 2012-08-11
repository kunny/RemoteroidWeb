package org.secmem.remoteroid.server;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.secmem.remoteroid.server.database.Md5PasswordObfuscator;
import org.secmem.remoteroid.server.database.Remoteroid.Account;
import org.secmem.remoteroid.server.response.AccountResponse;
import org.secmem.remoteroid.server.response.BaseErrorResponse;
import org.secmem.remoteroid.server.response.BaseResponse;
import org.secmem.remoteroid.server.response.Codes;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@Path("/account")
public class AccountREST extends DBUtils{
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/register")
	public BaseResponse addAccount(Account account){
		if(account==null){
			return new BaseErrorResponse();
		}
		
		// Email duplicate check
		if(isEmailDuplicates(account.getEmail())){
			return new BaseErrorResponse(Codes.Error.Account.DUPLICATE_EMAIL);
		}
		
		// Obfuscate password
		String rawPassword = account.getPassword();
		String obfuscatedPassword = new Md5PasswordObfuscator().generate(rawPassword);
		account.setPassword(obfuscatedPassword);
		
		// Generate token
		account.setToken(UUID.randomUUID().toString());
		
		// Store data into DataStore
		Entity newAccount = new Entity(Account._NAME, getRemoteroidKey(Account._NAME));
		newAccount.setProperty(Account.EMAIL, account.getEmail());
		newAccount.setProperty(Account.PASSWORD, account.getPassword());
		newAccount.setProperty(Account.TOKEN, account.getToken());
		
		// Insert entity to Datastore
		this.insertOrUpdate(Account._NAME, newAccount);
		
		return new AccountResponse(account);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unregister")
	public BaseResponse deleteAccount(Account account){
		
		return null;
	}

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
	
	private boolean isEmailDuplicates(String email){
		Query q = new Query(Account._NAME);
		q.setFilter(new FilterPredicate(Account.EMAIL, FilterOperator.EQUAL, email));
		
		List<Entity> result = query(q);
		
		if(result.size()==0){
			// No duplicated email.
			return false;
		}else
			// Email duplicates.
			return true;
	}
}
