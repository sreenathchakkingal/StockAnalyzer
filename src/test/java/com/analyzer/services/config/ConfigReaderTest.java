package com.analyzer.services.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigReaderTest {
	
	@Test
	public void testConfig()
	{
		assertEquals("stockanalyzer-225803", ConfigReader.getProjectSetting().getAppId());
		assertEquals(ConfigReader.getProjectSetting(), ConfigReader.getProjectSetting()); //verifies that its not loaded everytime
		
		String endPoint = ConfigReader.getProjectSetting().getHostName().get(Environment.PROD)+ConfigReader.getProjectSetting().getEndPointPrefix();
		assertEquals("https://stockanalyzer-225803.appspot.com/_ah/api/", endPoint);
	}
}
