package uo.ri.cws.application.persistence.util.jdbc;

import java.io.IOException;
import java.util.Properties;

import uo.ri.util.assertion.ArgumentChecks;

public class Queries {
	private static Properties props;
	private static final String FILE_CONF = "queries.properties";

	static {
		props = new Properties();
		try {
			props.load(Queries.class.getClassLoader().getResourceAsStream(FILE_CONF));
		} catch (IOException e) {
			throw new RuntimeException("File properties cannot be loaded",e);
		}
		
	}
	
	public static String get(String key) {
		String value = props.getProperty(key);
		ArgumentChecks.isNotBlank(value, "Property not found in config file: " + key);
		return value;
	}	

}
