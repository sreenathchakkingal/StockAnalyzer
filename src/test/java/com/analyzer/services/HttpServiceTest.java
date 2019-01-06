package com.analyzer.services;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.analyzer.domain.MessageHolder;

public class HttpServiceTest {

	private final static HttpService<MessageHolder> HTTP_SERVICE = new HttpService<>(MessageHolder.class);
	 
	@Test
	public void convertJsonStringToEntity()
	{
		String datastoreEntityAsJsonResponse = "{\r\n" + 
				"  \"message\": \"message11\"\r\n" + 
				"}";
		
		MessageHolder convertJsonReponseToEntityList = HTTP_SERVICE.convertJsonStringToEntity(datastoreEntityAsJsonResponse);
		Assert.assertEquals("message11", convertJsonReponseToEntityList.getMessage());
	}
	
	@Test
	public void convertJsonStringToEntityList()
	{
		String datastoreEntitesAsJsonResponse = "{\r\n" + 
				"  \"items\": [\r\n" + 
				"    {\r\n" + 
				"      \"message\": \"message1\"\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"message\": \"message2\"\r\n" + 
				"    }\r\n" + 
				"  ]\r\n" + 
				"}";
		
		List<MessageHolder> convertJsonReponseToEntityList2 = HTTP_SERVICE.convertJsonStringToEntityList(datastoreEntitesAsJsonResponse);
		List<String> expectedMessages = Arrays.asList("message1", "message2");
		List<String> actualMessages = Arrays.asList(convertJsonReponseToEntityList2.get(0).getMessage(),convertJsonReponseToEntityList2.get(1).getMessage());
		Assert.assertEquals(expectedMessages, actualMessages);
	}
}
