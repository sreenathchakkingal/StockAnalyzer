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

public class DatastoreOperations<E> {

	private static final int GOOGLE_APP_ENGINE_CHUNK_SIZE = 450;

	private static final Logger LOGGER = Logger.getLogger(DatastoreOperations.class.getName());

	private final Class<E> entityClass;

	public DatastoreOperations(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public List<E> fetchEntities() {
		
		init();

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
		Iterable<List<E>> partitionedIterables = Iterables.partition(entities, GOOGLE_APP_ENGINE_CHUNK_SIZE);
		partitionedIterables.forEach(entitiesInChunk -> saveEntities(entitiesInChunk));
	}
	
	public void  deleteEntities(Iterable<E> entities)
	{
		init();
		ofy().delete().entities(entities).now();  
	}

	private Map<Key<E>, E> saveEntities(Iterable<E> entities) 
	{
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		init();
		return ofy().save().entities(entities).now();	
	}

	private void init() 
	{
		//todo:move these to propertyfiles
		if(Environment.isDevEnv())
		{
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
