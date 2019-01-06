package com.analyzer.endpoints;

import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.analyzer.domain.MessageHolder;
import com.analyzer.domain.dbo.RatingDbObject;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;


@Api(
	    name = "analyzer",
	    version = "v1"
//	    namespace =
//	    @ApiNamespace(
//	        ownerDomain = "analyzer.com",
//	        ownerName = "analyzer.com",
//	        packagePath = "/test/mypackagePath"
//	    )
	)

public class RealizedUnrealizedEndPoints {
	private static final Logger LOGGER = Logger.getLogger(RealizedUnrealizedEndPoints.class.getName());
	
	@ApiMethod(name = "helloWorld", httpMethod=HttpMethod.GET)
	public List<MessageHolder> helloWorld()
	{
		MessageHolder hw = new MessageHolder();
		hw.setMessage("ente ellam alle1");
		
		MessageHolder hw2 = new MessageHolder();
		hw2.setMessage("ente");

		List<MessageHolder> list = new ArrayList<>();
		list.add(hw);
		list.add(hw2);
		LOGGER.info("hello world log info");
		return list ; 
	}
	
	@ApiMethod(name = "getHelloWorlds", httpMethod=HttpMethod.GET)
	public List<MessageHolder> getHelloWorlds()
	{
		ObjectifyService.init(new ObjectifyFactory());
		
		ObjectifyService.begin();
		
		factory().register(RatingDbObject.class);
		
		List<RatingDbObject> list = ofy().load().type(RatingDbObject.class).list();
		List<MessageHolder> al  = new ArrayList<>();
		Iterator<RatingDbObject> iterator = list.iterator();
		MessageHolder hw = null;
		hw = new MessageHolder();
		hw.setMessage("size: "+String.valueOf(list.size()));
		LOGGER.info("size: "+String.valueOf(list.size()));
		al.add(hw);
		int counter=0;
		while(iterator.hasNext())
		{
			hw = new MessageHolder();
			String ratingName = iterator.next().getRatingName();
			hw.setMessage(ratingName+" : "+String.valueOf(counter++));
			LOGGER.info("count this~ "+ratingName);
			al.add(hw);
		}
		
		return al;
	}
	
	@ApiMethod(name = "hitAndTrial")
	public MessageHolder hitAndTrialMethodName()
	{
		ObjectifyService.init(new ObjectifyFactory());
		ObjectifyService.begin();
		MessageHolder hw = new MessageHolder();
		hw.setMessage("hitAndTrial");
		return hw ; 
	}

}
