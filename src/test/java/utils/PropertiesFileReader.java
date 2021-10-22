package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Here I am going to read the properties file
 * 
 * @author Nitish
 */
public class PropertiesFileReader {

	public Properties prop = new Properties();

	/**
	 * Method to read the properties file
	 * 
	 * @author Nitish
	 * @param fileName Name of properties file to read
	 * @return {@link Properties}
	 */
	public Properties loadPropertiesFile(String fileName) {
		try {
			prop.load(new FileInputStream("properties/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
