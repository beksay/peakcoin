package org.infosystema.peakcoin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/***
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class Configuration {

	private static Configuration configration;
	private final Properties properties = new Properties();
	private static final String SETTING_FILE = "/opt12/repository/setting.properties";
	
	private Configuration() {
		init();
	}
	
	public static synchronized Configuration getInstance() {
		if(configration == null){
			configration = new Configuration();
		}
		return configration;
	}
	
	private void init(){
		try {
			File file = new File(SETTING_FILE);
			InputStream stream = file.exists() && file.isFile() ? new FileInputStream(file) 
				: getClass().getClassLoader().getResourceAsStream("setting.properties");
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
}
