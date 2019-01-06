package com.analyzer;

import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class HelloWorldObectifyTest {
	public static void main(String[] args)
	{
		ObjectifyService.init(new ObjectifyFactory(
				DatastoreOptions.newBuilder()
		        .setHost("http://localhost:8484")
		        .setProjectId("stockanalyzer-225803")
		        .build()
		        .getService()));
		
		ObjectifyService.begin();
		
		
		factory().register(Trivial.class);
		
		final Trivial triv1 = new Trivial("foo5", 5);
		final Trivial triv2 = new Trivial("foo6", 6);

//		ofy().save().entities(triv1, triv2).now();
		
		System.out.println("hello world");
//		Map<Key<Trivial>, Trivial> entities = ofy().load().entities(triv1, triv2);
		
		List<Trivial> trivials = ofy().load().type(Trivial.class).list();
		
		System.out.println("result: "+trivials.get(0).getSomeString()+" : "+trivials.get(1).getSomeString());
		
	}
}
