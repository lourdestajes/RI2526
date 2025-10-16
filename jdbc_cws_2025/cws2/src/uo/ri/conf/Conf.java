package uo.ri.conf;

import java.io.IOException;
import java.util.Properties;

public class Conf {
	private static Properties props;
	private static final String FILE_CONF = "connection.properties";

	static {
		props = new Properties();
		try {
			props.load(Conf.class.getClassLoader().getResourceAsStream(FILE_CONF));
		} catch (IOException e) {
			throw new RuntimeException("File properties cannot be loaded",e);
		}
		
	}
	
	public static String getProperty(String key) {
		String value = props.getProperty(key);
		if (value == null) {
			throw new RuntimeException("Property not found in config file");
		}
		return value;
	}
	

}
