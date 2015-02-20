package com.excilys.cdb.ui;

import java.sql.SQLException;

/**
 * 
 * @author sclaudet
 *
 */
public class Launcher {

	public static void main(String[] args) throws SQLException {
		CLI.run();

		System.out.println("End of programm");
		System.exit(0);
	}

}
