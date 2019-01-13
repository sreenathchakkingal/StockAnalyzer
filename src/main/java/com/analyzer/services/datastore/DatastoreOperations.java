package com.analyzer.services.datastore;

import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.analyzer.services.config.ConfigReader;
import com.analyzer.services.config.Environment;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class DatastoreOperations {

	private static final int GOOGLE_APP_ENGINE_CHUNK_SIZE = 450;

	private static final Logger LOGGER = Logger.getLogger(DatastoreOperations.class.getName());

	public static <E> List<E> fetchEntities(Class<E> type, String filterAttribute, String filterValue) {
			init(type);
			LOGGER.info("Initialized.fetching entities of type: "+type.getName());
			
			boolean isWithFilter = StringUtils.isNoneBlank(filterAttribute, filterValue);
			
			LOGGER.info("isWithFilter: "+isWithFilter+" filterAttribute: "+filterAttribute+" filterValue: "+filterValue);
			
			List<E> entities = isWithFilter ?  ofy().load().type(type).filter(filterAttribute, filterValue).list() :
												ofy().load().type(type).list();
			
			LOGGER.info("returning: "+entities.size()+": of type "+type.getName());
			return entities;
	}
	
	public static <E> List<E> fetchEntities(Class<E> type) {
		return fetchEntities(type, null, null);
	}
	
	public static <E> void saveEntitiesInChunks(Class<E> type, Iterable<E> entities) 
	{
		LOGGER.info("entities to be saved : "+Iterables.size(entities));
		
		Iterable<List<E>> partitionedIterables = Iterables.partition(entities, GOOGLE_APP_ENGINE_CHUNK_SIZE);
		partitionedIterables.forEach(entitiesInChunk -> 
		{
			Map<Key<E>, E> savedEntities = saveEntities(type, entitiesInChunk);
			LOGGER.info("saved : "+savedEntities.size());
		});
	}
	
	public static <E> void  deleteEntitiesInChunks(Class<E> type)
	{
		init(type);
		List<Key<E>> keys = ofy().load().type(type).keys().list();
		Iterable<List<Key<E>>> partitionedIterables = Iterables.partition(keys, GOOGLE_APP_ENGINE_CHUNK_SIZE);
		partitionedIterables.forEach(entitiesInChunk ->  deleteEntitiesByKeys(type, entitiesInChunk)); 
	}
	
	
	private static <E> void  deleteEntitiesByKeys(Class<E> type, List<Key<E>> keys)
	{
		sleep();
		LOGGER.info("will delete: "+keys.size()+" entries");
		ofy().delete().keys(keys).now();
	}
	
	private static <E> Map<Key<E>, E> saveEntities( Class<E> type, Iterable<E> entities) 
	{
		sleep();
		init(type);
		LOGGER.info("going to save");
		return ofy().save().entities(entities).now();	
	}

	private static void sleep() {
		LOGGER.info("sleep");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info("active");
	}

	private static <E> void init(Class<E> type) 
	{
		if(Environment.isDevEnv())
		{
			LOGGER.info("connecting to datastore in dev: "+ConfigReader.getProjectSetting().getDatastoreDevHost()+" : " + ConfigReader.getProjectSetting().getAppId());
			ObjectifyService.init(new ObjectifyFactory(
					DatastoreOptions.newBuilder()
			        .setHost(ConfigReader.getProjectSetting().getDatastoreDevHost())
			        .setProjectId(ConfigReader.getProjectSetting().getAppId())
			        .build()
			        .getService()));
			
		}
		else
		{
			ObjectifyService.init(new ObjectifyFactory());	
		}
		

		ObjectifyService.begin();

		factory().register(type);
	}

}
