package com.shavika.agritech.utils;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shavika.test.database.Employee;

public class CommonUtil {

	private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);

	public static long gettimesmilisec() {
		return System.currentTimeMillis();
	}

	public static JsonObject ConvertStringToJsonObject(String data) throws Exception {
		JsonObject jsonObject = null;
		String jsonStringData = null;
		try {
			jsonStringData = data;
			if (!jsonStringData.isEmpty()) {
				JsonReader reader = Json.createReader(new StringReader(jsonStringData));
				jsonObject = reader.readObject();
				reader.close();
			} else
				LOGGER.debug("JSON String was empty...");
		} catch (Exception e) {
			LOGGER.error("Fail to convert Json Object from String - " + data, e);
		}
		return jsonObject;
	}

	public static Object getDatabyJsonObject(JsonObject jsonObject, String key) {
		if (jsonObject != null && key != null) {
			try {
				String keyValue = key.toLowerCase();
				return (Object) jsonObject.get(keyValue);
			} catch (Exception e) {
				LOGGER.error("Fail to get Data from JsonObject - " + jsonObject + " with KEY - " + key, e);
			}
		} else
			LOGGER.debug(" JSONObject / KEY was null in the @method getDatabyJsonObject...");
		return null;
	}

	public static Object convertObjecttoBean(String jObj, Class object) throws Exception {
		ObjectMapper mapper = null;
		try {
			mapper = new ObjectMapper();
			LOGGER.info("Inside the convertor : " + object.getName());
			if (object.getName().equals("com.shavika.test.database.Employee"))
				return mapper.readValue(jObj, Employee.class);
		} catch (JsonGenerationException e) {
			LOGGER.error("Failed to Json Generation for clazz ", e);
		} catch (JsonMappingException e) {
			LOGGER.error("Failed to Json Mapping for clazz ", e);
		} catch (IOException e) {
			LOGGER.error("Failed with IO Exception for clazz ", e);
		}
		return null;
	}
}
