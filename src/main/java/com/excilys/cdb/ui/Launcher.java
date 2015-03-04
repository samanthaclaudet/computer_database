package com.excilys.cdb.ui;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author sclaudet
 *
 */
public class Launcher {

	public static void main(String[] args) {
		
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/application-context.xml");
		CLI cli = context.getBean(CLI.class);
		
		cli.run();
		System.out.println("End of programm");
		context.close();
		System.exit(0);
	}

}
