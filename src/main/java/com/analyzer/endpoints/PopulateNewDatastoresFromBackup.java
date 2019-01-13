package com.analyzer.endpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.analyzer.domain.WatchList;
import com.analyzer.domain.dbo.legacy.AllScripsDbObject;
import com.analyzer.services.datastore.DatastoreOperations;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@Api(
	    name = "populateNewDatastoresFromBackup",
	    version = "v1"
	)

public class PopulateNewDatastoresFromBackup {
	
	private static final Logger LOGGER = Logger.getLogger(PopulateNewDatastoresFromBackup.class.getName());
	
	@ApiMethod(name = "populateWatchListFromAllScripsDbObject", httpMethod=HttpMethod.PUT)
	public void populateWatchListFromAllScripsDbObject()
	{
		LOGGER.info("entered populateWatchListFromAllScripsDbObject");
		List<AllScripsDbObject> watchListedScripsDbObject = DatastoreOperations.fetchEntities(AllScripsDbObject.class,"isWatchListed", "true");

		List<WatchList> watchListedStocks = new ArrayList<>();
		for (AllScripsDbObject dbObject : watchListedScripsDbObject)
		{
			watchListedStocks.add(new WatchList(dbObject.getNseId()));
		}
		DatastoreOperations.saveEntitiesInChunks(WatchList.class, watchListedStocks);
		
		
		
		//test in local
		//test in prod
	}
	
}
