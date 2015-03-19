package com.excilys.cdb.ui;

import java.net.MalformedURLException;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author sclaudet
 *
 */
public class Launcher {

  public static void main(String[] args) throws MalformedURLException {

    AbstractApplicationContext context = new ClassPathXmlApplicationContext(
        "/application-context-console.xml");
    CLI cli = context.getBean(CLI.class);

    cli.run();
    System.out.println("End of programm");
    context.close();
    System.exit(0);
  }

}
