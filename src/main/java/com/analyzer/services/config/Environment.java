package com.analyzer.services.config;

import com.google.appengine.api.utils.SystemProperty;

public class Environment {
	
	public static final String PROD = "prod";
	
	public static boolean isDevEnv()
	{
		return SystemProperty.environment.value() == SystemProperty.Environment.Value.Development;
	}

}
