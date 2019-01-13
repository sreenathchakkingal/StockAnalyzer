package com.analyzer.endpoints;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.analyzer.domain.MessageHolder;
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
	public MessageHolder populateWatchListFromAllScripsDbObject()
	{
		
		LOGGER.info("entered populateWatchListFromAllScripsDbObject");
		
		List<AllScripsDbObject> watchListedScripsDbObject = DatastoreOperations.fetchEntities(AllScripsDbObject.class, "isWatchListed", "true");
		LOGGER.info("filtered  AllScripsDbObject count: "+watchListedScripsDbObject.size());
		
		List<WatchList> watchListedStocks = watchListedScripsDbObject.stream()
                .map(eachScripDbObject -> { 
                	{
                	  WatchList watchList = new WatchList();
                	  watchList.setNseId(eachScripDbObject.getNseId());
                	  return watchList;
                	}
                })
                .collect(Collectors.toList());
	    
	    LOGGER.info("watchListedStocks count: "+watchListedStocks.size());
	    DatastoreOperations.saveEntitiesInChunks(WatchList.class, watchListedStocks);
		MessageHolder messageHolder = new MessageHolder("sucessfully saved! ");
		return messageHolder;
	}
	
}
