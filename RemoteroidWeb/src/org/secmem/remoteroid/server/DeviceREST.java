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
import java.util.UUID;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.secmem.remoteroid.server.database.Account;
import org.secmem.remoteroid.server.database.Device;
import org.secmem.remoteroid.server.database.WakeupMessage;
import org.secmem.remoteroid.server.exception.DeviceNotFoundException;
import org.secmem.remoteroid.server.response.BaseErrorResponse;
import org.secmem.remoteroid.server.response.BaseResponse;
import org.secmem.remoteroid.server.response.Codes;
import org.secmem.remoteroid.server.response.ObjectResponse;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@Path("/device")
public class DeviceREST extends DBUtils{
	
	private static Logger log = Logger.getLogger("DeviceREST");
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/register")
	public BaseResponse registerDevice(Device device){
		// Check user credential first
		if(!AccountREST.isUserCredentialMatches(device.getOwnerAccount())){
			// Failed to authenticate.
			return new BaseErrorResponse(Codes.Error.Account.AUTH_FAILED);
		}
		
		// Check duplication in devices which current user has registered
		if(isDuplicateDeviceExists(device.getOwnerAccount().getEmail(), device.getNickname())){
			return new BaseErrorResponse(Codes.Error.Device.DUPLICATE_NAME);
		}
		
		try{
			// Generate device uuid
			device.setDeviceUUID(UUID.randomUUID().toString());
			
			// If no duplication exists, save registration info into Datastore
			// Create entity for this
			Entity entity = getDeviceEntity();
			entity.setProperty(Device.OWNER_EMAIL, device.getOwnerAccount().getEmail());
			entity.setProperty(Device.NICKNAME, device.getNickname());
			entity.setProperty(Device.REGISTRATION_KEY, device.getRegistrationKey());
			entity.setProperty(Device.DEVICE_UUID, device.getDeviceUUID());
			
			// Put into datastore
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(entity);
			
			return new ObjectResponse<Device>(device);
		}catch(Exception e){
			e.printStackTrace();
			return new BaseErrorResponse();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update")
	public BaseResponse updateDevice(Device device){
		// Check user credential first
		if(!AccountREST.isUserCredentialMatches(device.getOwnerAccount())){
			// Failed to authenticate.
			return new BaseErrorResponse(Codes.Error.Account.AUTH_FAILED);
		}
		
		try{
			// Get Entity object from data using user's email and uuid
			Entity deviceEntity = getDeviceEntity(device.getOwnerAccount().getEmail(), device.getDeviceUUID());
			
			// Modify each entity's value to new one
			String oldNickname = (String)deviceEntity.getProperty(Device.NICKNAME);
			
			// Check nickname duplicates or not
			if(!oldNickname.equals(device.getNickname())){
				// If nickname has changed
				if(isDuplicateDeviceExists(device.getOwnerAccount().getEmail(), device.getNickname())){
					return new BaseErrorResponse(Codes.Error.Device.DUPLICATE_NAME);
				}
			}
			
			deviceEntity.setProperty(Device.NICKNAME, device.getNickname());
			deviceEntity.setProperty(Device.REGISTRATION_KEY, device.getRegistrationKey());
			
			// Put entity into datastore to apply changes
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(deviceEntity);
			return new BaseResponse();
		}catch(Exception e){
			e.printStackTrace();
			return new BaseErrorResponse();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public BaseResponse deleteDevice(Device device){
		// Check user credential first
		if(!AccountREST.isUserCredentialMatches(device.getOwnerAccount())){
			// Failed to authenticate.
			return new BaseErrorResponse(Codes.Error.Account.AUTH_FAILED);
		}
		
		try{
			// If entity exists in datastore, delete it.
			Entity deviceEntity = getDeviceEntity(device.getOwnerAccount().getEmail(), device.getDeviceUUID());
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.delete(deviceEntity.getKey());
			
			return new BaseResponse();
		}catch(Exception e){
			e.printStackTrace();
			return new BaseErrorResponse();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteAll")
	public static BaseResponse deleteAllDevicesOfUser(Account account){
		// Check user credential first
		if(!AccountREST.isUserCredentialMatches(account)){
			// Failed to authenticate.
			return new BaseErrorResponse(Codes.Error.Account.AUTH_FAILED);
		}
		
		try{
			// Delete all of the device data which is linked to given user's account
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query q = new Query(Device._NAME);
			q.setFilter(new FilterPredicate(Device.OWNER_EMAIL, FilterOperator.EQUAL, account.getEmail()));
			Iterable<Entity> result = datastore.prepare(q).asIterable();
			for(Entity entity : result){
				datastore.delete(entity.getKey());
			}
			return new BaseResponse();
		}catch(Exception e){
			e.printStackTrace();
			return new BaseErrorResponse();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/wakeup")
	public BaseResponse sendConnectionMessage(WakeupMessage wakeupMessage){
		// Check user credential first
		if(!AccountREST.isUserCredentialMatches(wakeupMessage.getDevice().getOwnerAccount())){
			// Failed to authenticate.
			return new BaseErrorResponse(Codes.Error.Account.AUTH_FAILED);
		}
		
		try{
			String gcmApiKey = getGcmAPIKey();
			Sender sender = new Sender(gcmApiKey);
			Message message = new Message.Builder().addData(WakeupMessage.IP_ADDRESS, wakeupMessage.getServerIpAddress()).build();
			
			Result result = sender.send(message, wakeupMessage.getDevice().getRegistrationKey(), 5);
			
			if (result.getMessageId() != null) {
				 String canonicalRegId = result.getCanonicalRegistrationId();
				 if (canonicalRegId != null) {
					 // same device has more than on registration ID: update database
					 log.warning("Updating database with new registration id..");
					 Device device = wakeupMessage.getDevice();
					 device.setRegistrationKey(canonicalRegId);
					 updateDevice(device);
				 }
			} else {
				 String error = result.getErrorCodeName();
				 if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
					 // application has been removed from device - unregister database
					 log.warning("Application has been removed from device. Deleting device data..");
					 deleteDevice(wakeupMessage.getDevice());
				 }
			}
			return new BaseResponse();
		}catch(Exception e){
			return new BaseErrorResponse();
		}
	}
	
	private boolean isDuplicateDeviceExists(String email, String nickname){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(Device._NAME);
		q.setFilter(CompositeFilterOperator.and(
				new FilterPredicate(Device.OWNER_EMAIL, FilterOperator.EQUAL, email),
				new FilterPredicate(Device.NICKNAME, FilterOperator.EQUAL, nickname)));
		return datastore.prepare(q).asList(FetchOptions.Builder.withDefaults()).size() > 0 ? true : false;
	}
	
	static Entity getDeviceEntity(String email, String deviceUUID) throws DeviceNotFoundException{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(Device._NAME);
		q.setFilter(CompositeFilterOperator.and(
				new FilterPredicate(Device.OWNER_EMAIL, FilterOperator.EQUAL, email),
				new FilterPredicate(Device.DEVICE_UUID, FilterOperator.EQUAL, deviceUUID)));
		List<Entity> result = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		if(result.size()==0)
			throw new DeviceNotFoundException();
		else if(result.size() > 1)
			throw new IllegalStateException();
		else
			return result.get(0);
	}
	
}
