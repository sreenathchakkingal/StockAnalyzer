package com.analyzer.endpoints;

import com.analyzer.domain.HelloWorld;
import com.analyzer.domain.dbo.ProfitAndLossDbObject;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Api(
	    name = "analyzer",
	    version = "v1",
	    namespace =
	    @ApiNamespace(
	        ownerDomain = "analyzer.com",
	        ownerName = "analyzer.com",
	        packagePath = ""
	    )
	)

public class RealizedUnrealizedEndPoints {
	
	@ApiMethod(name = "helloWorld", httpMethod=HttpMethod.GET)
	public HelloWorld helloWorld()
	{
		HelloWorld hw = new HelloWorld();
		hw.setMessage("ente ammo");
		return hw ; 
	}
	
	@ApiMethod(name = "getProfitAndLoss")
	public HelloWorld getProfitAndLoss()
	{
//		return ofy().load().type(ProfitAndLossDbObject.class).first().now();
		HelloWorld hw = new HelloWorld();
		hw.setMessage("this works");
		return hw ; 
	}

}
