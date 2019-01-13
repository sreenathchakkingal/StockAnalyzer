package com.analyzer.endpoints.helper;

import java.util.List;
import java.util.logging.Logger;

import com.analyzer.domain.MessageHolder;
import com.analyzer.endpoints.SyncProdDatastoresToDev;
import com.analyzer.services.HttpService;
import com.analyzer.services.config.Environment;
import com.analyzer.services.datastore.DatastoreOperations;
import com.google.appengine.api.utils.SystemProperty;

public class SyncProdDatastoresToDevHelper {
	
	private static final Logger LOGGER = Logger.getLogger(SyncProdDatastoresToDev.class.getName());

	
	public static <E> MessageHolder  syncDatastoresFromProdToDev(Class<E> datastoreClass, String prodUrlToFetcchEntities)
	{
		LOGGER.info("in syncDatastoresFromProdToDev");
		MessageHolder hw = new MessageHolder();
		LOGGER.info("initialized MessageHolder: "+SystemProperty.environment.value());
		if(Environment.isDevEnv())
		{
			String apiUrl = prodUrlToFetcchEntities;
			List<E> allDbObjects = new HttpService<>(datastoreClass).invokeGetReponseAsEntityList(apiUrl);
			
			DatastoreOperations.saveEntitiesInChunks(datastoreClass, allDbObjects);
			
			hw.setMessage("synced entity of type: " +datastoreClass+" count: "+ allDbObjects.size() +" allDbObjects objects");
			
			return hw;
		}
		else
		{
			throw new RuntimeException("this api should be invoked only from the dev");
		}
	}
}
