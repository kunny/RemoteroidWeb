package org.secmem.remoteroid.server;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.secmem.remoteroid.server.database.Gcm;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.sun.jersey.api.view.Viewable;

@Path("/admin")
public class AdminREST extends DBUtils {
	private static final Logger log = Logger.getLogger("AdminREST");
	
	@GET
	@Path("/init")
	public Viewable createApiEntity(){
		
		// Check entity exists or not
		Query q = new Query(Gcm._NAME);
		List<Entity> result = query(q);
		
		// Do only there are no entity for api key
		if(result.size()==0){
			// API key entry not exists. Need to create new one.
			Entity entity = new Entity(Gcm._NAME, getRemoteroidKey(Gcm._NAME));
			
			// NOTE : You cannot change entity's value on localhost server.
			// If you intend to test your GCM on your localhost, please replace API_KEY_PLACEHOLDER into your own GCM api key.
			entity.setProperty(Gcm.API_KEY, "API_KEY_PLACEHOLDER");
			
			// Put api key entity to datastore
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(entity);
			
			log.info("Created API key entity.");
		}
		
		return new Viewable("/configuration_done.html");
	}
}
