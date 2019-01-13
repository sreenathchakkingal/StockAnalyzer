package com.analyzer.services;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpService<E> {
	
	private static final Logger LOGGER = Logger.getLogger(HttpService.class.getName());
	
	private Class<E> entityClass;

	public HttpService(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public List<E> invokeGetReponseAsEntityList(String url) {
		LOGGER.info("accessing url: "+url);
		String jsonResponse = invokeGetReponseAsString(url);
		List<E> entitities = convertJsonStringToEntityList(jsonResponse);
		LOGGER.info("fetched "+entitities.size()+"from url");
		return entitities;
	}

	public E convertJsonStringToEntity(String jsonAsString) {
		E entity = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			entity = mapper.readValue(jsonAsString, this.entityClass);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Json is malformed: " + jsonAsString);
		}
		return entity;
	}

	public List<E> convertJsonStringToEntityList(String jsonAsString) {
		List<E> participantJsonList = null;
		ObjectMapper mapper = new ObjectMapper();
		try {

			JSONObject jsonObj = new JSONObject(jsonAsString);
			JSONArray jsonWithoutItems = (JSONArray) jsonObj.get("items");
			participantJsonList = mapper.readValue(jsonWithoutItems.toString(),
					mapper.getTypeFactory().constructCollectionType(List.class, this.entityClass));
			return participantJsonList;

		} catch (IOException | JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("Json is malformed: " + jsonAsString);
		}
	}

	private String invokeGetReponseAsString(String url) {

		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		String responseAsJsonString = null;
		try 
		{
			responseAsJsonString = client.execute(get, response -> EntityUtils.toString(response.getEntity()));
		} catch (IOException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("cannot invoke the rest api.");
		}
		
		return responseAsJsonString;

	}

}
