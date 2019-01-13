package com.analyzer.endpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.analyzer.domain.MessageHolder;
import com.analyzer.domain.WatchList;
import com.analyzer.domain.dbo.legacy.AllScripsDbObject;
import com.analyzer.endpoints.helper.SyncProdDatastoresToDevHelper;
import com.analyzer.services.config.ConfigReader;
import com.analyzer.services.config.Environment;
import com.analyzer.services.datastore.DatastoreOperations;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;


@Api(
	    name = "syncProdDatastoresToDev",
	    version = "v1"
	)

public class SyncProdDatastoresToDev {

	private static final String PROD_END_POINT_PREFIX = ConfigReader.getProjectSetting().getHostName().get(Environment.PROD)+
						ConfigReader.getProjectSetting().getEndPointPrefix()+"syncProdDatastoresToDev/v1/";
	private static final Logger LOGGER = Logger.getLogger(SyncProdDatastoresToDev.class.getName());
	
	@ApiMethod(name = "getAllScripsDbObjects", httpMethod=HttpMethod.GET)
	public List<AllScripsDbObject> getAllScripsDbObjects()
	{
		LOGGER.info("in getAllScripsDbObjects");
		List<AllScripsDbObject> entities = DatastoreOperations.fetchEntities(AllScripsDbObject.class);
		
		LOGGER.info("number of entities: "+entities.size());
		return entities;
	}	

	@ApiMethod(name = "getAllWatchListedStockNames", httpMethod=HttpMethod.GET)
	public List<WatchList> getAllWatchListedStockNames()
	{
		LOGGER.info("in getAllWatchListedStockNames");
		List<WatchList> entities = DatastoreOperations.fetchEntities(WatchList.class);
		
		LOGGER.info("number of entities: "+entities.size());
		return entities;
	}	

	@ApiMethod(name = "syncAllRegisteredDatastoresFromProdToDev", httpMethod=HttpMethod.GET)
	public List<MessageHolder>  syncAllRegisteredDatastoresFromProdToDev()
	{
		List<MessageHolder> messages = new ArrayList<>();
		messages.add(SyncProdDatastoresToDevHelper.syncDatastoresFromProdToDev(AllScripsDbObject.class, PROD_END_POINT_PREFIX+"allscripsdbobjectcollection"));
		messages.add(SyncProdDatastoresToDevHelper.syncDatastoresFromProdToDev(WatchList.class, PROD_END_POINT_PREFIX+"watchlistcollection"));
		return messages;
	}
}
