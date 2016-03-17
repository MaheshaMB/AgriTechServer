package com.shavika.agritech.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class PropertyReader {

	private static final Logger LOGGER = Logger.getLogger(PropertyReader.class);
	private static final Map<String, ResourceBundle> RESOURCE_MAP = new HashMap();

	public static String getPropertyValue(String fileName, String key) {
		String value = null;
		ResourceBundle resBundle = (ResourceBundle) RESOURCE_MAP.get(fileName);
		if (resBundle == null) {
			try {
				resBundle = ResourceBundle.getBundle(fileName);
				RESOURCE_MAP.put(fileName, resBundle);
			} catch (MissingResourceException ex) {
				resBundle = null;
				LOGGER.error("Fail to get Bundal from the File : " + fileName, ex);
			}
		}

		if (resBundle != null) {
			try {
				value = resBundle.getString(key);
			} catch (MissingResourceException ex) {
				value = null;
				LOGGER.error("Fail to get value from the KEY : " + key, ex);
			}
		}
		return value;
	}
}
