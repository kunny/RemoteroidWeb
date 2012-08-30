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
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.secmem.remoteroid.server.database.GCM;

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
		Query q = new Query(GCM._NAME);
		List<Entity> result = query(q);
		
		// Do only there are no entity for Google Cloud Messaging API Key
		if(result.size()==0){
			// API key entry not exists. Need to create new one.
			Entity entity = new Entity(GCM._NAME, getRemoteroidKey(GCM._NAME));
			
			// NOTE : You cannot change entity's value on localhost server.
			// If you intend to test GCM on your localhost, please replace API_KEY_PLACEHOLDER into your own
			// API key.
			entity.setProperty(GCM.API_KEY, "API_KEY_PLACEHOLDER");
			
			// Put api key entity to datastore
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(entity);
			
			log.info("Created API key entity.");
		}
		
		return new Viewable("/configuration_done.html");
	}
}
