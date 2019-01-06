package com.analyzer.services;

import com.google.appengine.api.utils.SystemProperty;

public class Environment {
	
	public static boolean isDevEnv()
	{
		return SystemProperty.environment.value() == SystemProperty.Environment.Value.Development;
	}

}
