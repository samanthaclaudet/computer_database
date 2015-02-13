package com.excilys.cdb.utilsdb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum DatabaseName {
	INSTANCE;

	public String getDatabaseName(){
		Properties prop = new Properties();
		String propFileName = "db.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	
		try{
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return prop.getProperty("DB_NAME");
	}

}
