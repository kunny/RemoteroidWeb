package org.secmem.remoteroid.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.secmem.remoteroid.server.database.Account;
import org.secmem.remoteroid.server.database.SHAPasswordObfuscator;
import org.secmem.remoteroid.server.response.BaseErrorResponse;
import org.secmem.remoteroid.server.response.BaseResponse;
import org.secmem.remoteroid.server.response.Codes;
import org.secmem.remoteroid.server.response.ObjectResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
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
		
		try{
			// Obfuscate password
			String rawPassword = account.getPassword();
			String obfuscatedPassword = new SHAPasswordObfuscator().generate(rawPassword);
			account.setPassword(obfuscatedPassword);
			
			// Store data into DataStore
			Entity newAccount = new Entity(Account._NAME, getRemoteroidKey(Account._NAME));
			newAccount.setProperty(Account.EMAIL, account.getEmail());
			newAccount.setProperty(Account.PASSWORD, account.getPassword());
			
			// Insert entity to Datastore
			this.insertOrUpdate(Account._NAME, newAccount);
			
			return new ObjectResponse<Account>(account);
		}catch(Exception e){
			e.printStackTrace();
			return new BaseErrorResponse();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/login")
	public BaseResponse login(Account account){
		// Check user credentials
		if(!isUserCredentialMatches(account)){
			// Failed to authenticate user.
			return new BaseErrorResponse(Codes.Error.Account.AUTH_FAILED);
		}
		
		try{
			// Get user data from database
			Entity entity = getAccountEntity(account.getEmail());
			// Fetch obfuscated password from database
			account.setPassword((String)entity.getProperty(Account.PASSWORD));
			// Return data to user with account info
			return new ObjectResponse<Account>(account);
		}catch(Exception e){
			e.printStackTrace();
			return new BaseResponse();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/unregister")
	public BaseResponse deleteAccount(Account account){
		// Check user credentials
		if(!isUserCredentialMatches(account)){
			// Failed to authenticate user.
			return new BaseErrorResponse(Codes.Error.Account.AUTH_FAILED);
		}
		
		try{
			// Delete devices linked to this account
			if(DeviceREST.deleteAllDevicesOfUser(account) instanceof BaseErrorResponse){
				throw new IllegalStateException("Cannot delete devices linked to following account : "+account.getEmail());
			}
			
			// Delete account from datastore
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			Entity entity = getAccountEntity(account.getEmail());
			datastore.delete(entity.getKey());
			
			return new BaseResponse();
		}catch(Exception e){
			e.printStackTrace();
			return new BaseErrorResponse();
		}
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
	
	private Entity getAccountEntity(String email){
		Query q = new Query(Account._NAME);
		q.setFilter(new FilterPredicate(Account.EMAIL, FilterOperator.EQUAL, email));
		
		List<Entity> result = query(q);
		
		if(result.size() > 1){
			throw new IllegalStateException("There are more than one account in the database.");
		}else if(result.size() <1) {
			throw new IllegalStateException("Failed to find matching account with following email : "+email);
		}else{
			return result.get(0);
		}
	}
	
	static boolean isUserCredentialMatches(Account account){
		Query q = new Query(Account._NAME);
		q.setFilter(CompositeFilterOperator.and(
				new FilterPredicate(Account.EMAIL, FilterOperator.EQUAL, account.getEmail()),
				new FilterPredicate(Account.PASSWORD, FilterOperator.EQUAL, account.getPassword())));
		List<Entity> result = query(q);
		if(result.size()==0){
			return false;
		}else{
			return true;
		}
	}
}
