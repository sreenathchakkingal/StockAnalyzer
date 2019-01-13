package com.analyzer.services.datastore;

import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.analyzer.services.config.ConfigReader;
import com.analyzer.services.config.Environment;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class DatastoreOperations2<E> {

	private static final int GOOGLE_APP_ENGINE_CHUNK_SIZE = 450;

	private static final Logger LOGGER = Logger.getLogger(DatastoreOperations.class.getName());

	private final Class<E> entityClass;

	public DatastoreOperations2(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public List<E> fetchEntities() {

		init();
		LOGGER.info("Initialized.fetching entities of type: "+this.entityClass.getName());
		List<E> entities = ofy().load().type(this.entityClass).list();
		LOGGER.info("returning: "+entities.size()+": of type "+this.entityClass.getName());
		return entities;
	}
	
	public void deleteAndSaveEntitiesInChunks(Iterable<E> entities) 
	{
		deleteEntities(entities);
		saveEntitiesInChunks(entities);
	}
	
	public void saveEntitiesInChunks(Iterable<E> entities) 
	{
		LOGGER.info("entities to be saved : "+Iterables.size(entities));
		
		Iterable<List<E>> partitionedIterables = Iterables.partition(entities, GOOGLE_APP_ENGINE_CHUNK_SIZE);
		partitionedIterables.forEach(entitiesInChunk -> 
		{
			Map<Key<E>, E> savedEntities = saveEntities(entitiesInChunk);
			LOGGER.info("saved : "+savedEntities.size());
		});
	}
	
	public void  deleteEntities(Iterable<E> entities)
	{
		init();
		ofy().delete().entities(entities).now();  
	}

	private Map<Key<E>, E> saveEntities(Iterable<E> entities) 
	{
		LOGGER.info("sleep");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info("active");
		init();
		LOGGER.info("going to save");
		return ofy().save().entities(entities).now();	
	}

	private void init() 
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

		factory().register(this.entityClass);
	}

}
