package com.excilys.cdb.utilsdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.servlets.Dashboard;

public enum DatabaseLoader {
	INSTANCE;
	
	private static final Logger logger = LoggerFactory
			.getLogger(Dashboard.class);
	
	public void load() {
		String line;
		try {
			Process p = Runtime.getRuntime().exec(
					"./src/test/resources/script.sh");
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
			input.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
