package com.analyzer.endpoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.analyzer.domain.MessageHolder;
import com.analyzer.domain.dbo.legacy.AllScripsDbObject;
import com.analyzer.services.Environment;
import com.analyzer.services.HttpService;
import com.analyzer.services.datastore.DatastoreOperations;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.utils.SystemProperty;


@Api(
	    name = "syncProdDatastoresToLocal",
	    version = "v1"
	)

public class RestoreFromBackUpEndPoints {

	private static final Logger LOGGER = Logger.getLogger(RestoreFromBackUpEndPoints.class.getName());
	
	@ApiMethod(name = "myApiMethodName", httpMethod=HttpMethod.GET)
	public List<MessageHolder> myBlah()
	{
		MessageHolder hw = new MessageHolder();
		hw.setMessage("my blah");
		
		MessageHolder hw2 = new MessageHolder();
		hw2.setMessage("ente");

		List<MessageHolder> list = new ArrayList<>();
		list.add(hw);
		list.add(hw2);
		LOGGER.info("hello world log info");
		return list ; 
	}
	
	@ApiMethod(name = "getAllScripsDbObjectsFromProd", httpMethod=HttpMethod.GET)
	public List<AllScripsDbObject> getAllScripsDbObjectsFromProd()
	{
		LOGGER.info("in getAllScripsDbObjectsFromProd");
		DatastoreOperations<AllScripsDbObject> datastoreOperations = new DatastoreOperations<>(AllScripsDbObject.class);
		List<AllScripsDbObject> entities = datastoreOperations.fetchEntities();
		LOGGER.info("number of entities: "+entities.size());
		return entities;
	}	

	@ApiMethod(name = "syncAllDataStoresFromProdToLocal", httpMethod=HttpMethod.PUT)
	public MessageHolder syncAllDataStoresFromProdToLocal()
	{
		Map<Class, String> classApiMap = new HashMap<>();
//		classApiMap.put(AllScripsDbObject.class, value)
		return null;
	}
	
	@ApiMethod(name = "syncAllScripsDbObjectToLocal", httpMethod=HttpMethod.GET)
	public MessageHolder syncAllScripsDbObjectToLocal()
	{
		MessageHolder hw = new MessageHolder();
		LOGGER.info("initialized MessageHolder: "+SystemProperty.environment.value());
		if(Environment.isDevEnv())
		{
			String apiUrl = "https://stockanalyzer-225803.appspot.com/_ah/api/syncProdDatastoresToLocal/v1/allscripsdbobjectcollection";
			LOGGER.info("apiUrl: "+apiUrl);
			List<AllScripsDbObject> allScripDbObjects = new HttpService<>(AllScripsDbObject.class).invokeGetReponseAsEntityList(apiUrl);
			
			String result ="";
			for (int i=0;i<10;i++) 
			{
				result = result + allScripDbObjects.get(i).getNseId() + " , ";
			}
			LOGGER.info("initialized resposne: "+result);
			hw.setMessage("my blah1: "+result);
			
			DatastoreOperations<AllScripsDbObject> datastoreOperations = new DatastoreOperations<>(AllScripsDbObject.class);
			datastoreOperations.saveEntitiesInChunks(allScripDbObjects);
			
			return hw;
		}
		else
		{
			throw new RuntimeException("this api should be invoked only from the dev");
		}
				
		//delete the existing in local?
		//save to local
		//return the response
	}	
	

}
