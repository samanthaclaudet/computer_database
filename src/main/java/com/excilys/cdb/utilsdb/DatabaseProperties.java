package com.excilys.cdb.utilsdb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DatabaseProperties {
	INSTANCE;

	private static final Logger logger = LoggerFactory
			.getLogger(DatabaseProperties.class);

	private Properties prop;

	public Properties getProperties() {
		if (prop == null) {
			prop = new Properties();
			String propFileName = "db.properties";
			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream(propFileName);

			try {
				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new FileNotFoundException("property file '"
							+ propFileName + "' not found in the classpath");
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		return prop;
	}

	public String getDatabaseUser() {
		Properties prop = getProperties();
		return prop.getProperty("DB_USER");
	}

	public String getDatabaseUrl() {
		Properties prop = getProperties();
		return prop.getProperty("DB_URL");
	}
	
	public String getDatabasePassword() {
		Properties prop = getProperties();
		return prop.getProperty("DB_PASSWD");
	}
	
	public int getBoneCPMin() {
		Properties prop = getProperties();
		String s = prop.getProperty("MIN_CONNECTIONS_PER_PARTITION");
		return Integer.parseInt(s);
	}
	
	public int getBoneCPMax() {
		Properties prop = getProperties();
		String s = prop.getProperty("MAX_CONNECTIONS_PER_PARTITION");
		return Integer.parseInt(s);
	}

	public int getBoneCPPartitions() {
		Properties prop = getProperties();
		String s = prop.getProperty("NB_PARTITIONS");
		return Integer.parseInt(s);
	}

}
