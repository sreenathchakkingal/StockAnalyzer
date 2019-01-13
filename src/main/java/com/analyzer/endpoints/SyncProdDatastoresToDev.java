package com.analyzer.endpoints;

import java.util.List;
import java.util.logging.Logger;

import com.analyzer.domain.MessageHolder;
import com.analyzer.domain.dbo.legacy.AllScripsDbObject;
import com.analyzer.services.HttpService;
import com.analyzer.services.config.ConfigReader;
import com.analyzer.services.config.Environment;
import com.analyzer.services.datastore.DatastoreOperations;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.utils.SystemProperty;


@Api(
	    name = "syncProdDatastoresToDev",
	    version = "v1"
	)

public class SyncProdDatastoresToDev {

	private static final String PROD_END_POINT_PREFIX = ConfigReader.getProjectSetting().getHostName().get(Environment.PROD)+
						ConfigReader.getProjectSetting().getEndPointPrefix()+"syncProdDatastoresToDev/v1/";
	private static final Logger LOGGER = Logger.getLogger(SyncProdDatastoresToDev.class.getName());
	
	@ApiMethod(name = "allscripsdbobjectcollection", httpMethod=HttpMethod.GET)
	public List<AllScripsDbObject> getAllScripsDbObjectsFromProd()
	{
		LOGGER.info("in getAllScripsDbObjectsFromProd");
		List<AllScripsDbObject> entities = DatastoreOperations.fetchEntities(AllScripsDbObject.class);
		
		LOGGER.info("number of entities: "+entities.size());
		return entities;
	}	

	@ApiMethod(name = "syncAllScripsDbObjectToDev", httpMethod=HttpMethod.GET)
	public MessageHolder syncAllScripsDbObjectToDev()
	{
		MessageHolder hw = new MessageHolder();
		LOGGER.info("initialized MessageHolder: "+SystemProperty.environment.value());
		if(Environment.isDevEnv())
		{
			String apiUrl = PROD_END_POINT_PREFIX+"allscripsdbobjectcollection";
					
			List<AllScripsDbObject> allScripDbObjects = new HttpService<>(AllScripsDbObject.class).invokeGetReponseAsEntityList(apiUrl);
			
			String result ="";
			for (int i=0;i<10;i++) 
			{
				result = result + allScripDbObjects.get(i).getNseId() + " , ";
			}
			LOGGER.info("initialized resposne: "+result);
			
			DatastoreOperations.saveEntitiesInChunks(AllScripsDbObject.class, allScripDbObjects);
			
			hw.setMessage("synced " +allScripDbObjects.size() +" AllScripsDbObject objects");
			
			return hw;
		}
		else
		{
			throw new RuntimeException("this api should be invoked only from the dev");
		}
	}	
	
}
