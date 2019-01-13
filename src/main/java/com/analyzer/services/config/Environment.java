package com.analyzer.services.config;

import java.util.logging.Logger;

import com.google.appengine.api.utils.SystemProperty;

public class Environment {
	
	public static final String PROD = "prod";
	private static final Logger LOGGER = Logger.getLogger(Environment.class.getName());
	
	public static boolean isDevEnv()
	{
		
		boolean isDev = SystemProperty.environment.value() == SystemProperty.Environment.Value.Development;
		
		LOGGER.info("isDev: "+isDev);
		
		return isDev;
	}
	
	public static boolean isProdEnv()
	{
		boolean isProd = SystemProperty.environment.value() == SystemProperty.Environment.Value.Production;
		
		LOGGER.info("isProd: "+isProd);

		return isProd;
	}
	
	public static boolean isNoEnv()
	{
		boolean isNo = SystemProperty.environment.value() == null;
		
		LOGGER.info("isNoEnv: "+isNo);
		
		return isNo;
	}
}
