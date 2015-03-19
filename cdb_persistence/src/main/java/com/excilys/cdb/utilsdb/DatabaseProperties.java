package com.excilys.cdb.utilsdb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Gets the connections properties :
 * <ul>
 * <li>user</li>
 * <li>url</li>
 * <li>password</li>
 * </ul>
 * 
 * @author sclaudet
 *
 */
@Component
public class DatabaseProperties {

  private static final Logger logger = LoggerFactory.getLogger(DatabaseProperties.class);

  private Properties          prop;

  /**
   * Gets the connections properties from db.properties
   * 
   * @return properties
   */
  public Properties getProperties() {
    if (prop == null) {
      prop = new Properties();
      String propFileName = "db.properties";
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

      try {
        if (inputStream != null) {
          prop.load(inputStream);
        } else {
          throw new FileNotFoundException("property file '" + propFileName
              + "' not found in the classpath");
        }
      } catch (IOException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        throw new RuntimeException();
      }
    }
    return prop;
  }

  /**
   * 
   * @return the user name of the connection
   */
  public String getDatabaseUser() {
    Properties prop = getProperties();
    return prop.getProperty("DB_USER");
  }

  /**
   * 
   * @return the url of the connection
   */
  public String getDatabaseUrl() {
    Properties prop = getProperties();
    return prop.getProperty("DB_URL");
  }

  /**
   * 
   * @return the password of the connection
   */
  public String getDatabasePassword() {
    Properties prop = getProperties();
    return prop.getProperty("DB_PASSWD");
  }

}
