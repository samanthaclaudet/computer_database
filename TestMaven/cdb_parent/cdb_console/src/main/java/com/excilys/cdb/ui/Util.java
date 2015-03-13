package com.excilys.cdb.ui;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.impl.CompanyServiceImpl;

/**
 * The Util class is used to check the validity of the user input The input is
 * retrieved as a String and returned as the valid format
 * <ul>
 * <li>a valid id (int)</li>
 * <li>a valid LocalDateTime</li>
 * <li>a valid Company</li>
 * </ul>
 * It also checks if a query has succeeded or not
 * 
 * @author sclaudet
 *
 */
@Component
public class Util {
	
	@Autowired
	private CompanyServiceImpl companyServiceImpl;
	
	/**
	 * LocalDateTime must follow the pattern "yyyy-mm-dd HH:mm"
	 */
	private static final String DATE_REGEX = "^(19|20)[0-9][0-9](-)((0[1-9])|(1[0-2]))(-)((0[1-9])|([1-2][0-9])|(3[0-1]))(T|\\s)(([0-1][0-9])|(2[0-3])):([0-5][0-9])";
	private static final Pattern P = Pattern.compile(DATE_REGEX);
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm");


	
	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	/**
	 * Check if the user input is a valid int
	 * 
	 * @param sc
	 *            the scanner
	 * @return a valid int
	 */
	public static int checkId(Scanner sc) {
		int id = 0;

		while (!sc.hasNextInt()) {
			System.out.println("Non valid, please enter an integer");
			sc.nextLine();
		}
		String idx = sc.nextLine();
		try {
			id = Integer.parseInt(idx);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
		return id;
	}

	/**
	 * Check if the user input is a valid date or null
	 * 
	 * @param sc
	 *            the scanner
	 * @return a valid LocalDateTime or null
	 */
	public static LocalDateTime checkDate(Scanner sc) {

		LocalDateTime ldt = null;

		String date = sc.nextLine();
		if (date.equals("null")) {
			return null;
		}

		while (!P.matcher(date).find()) {
			System.out
					.println("Non valid format, please enter a date with format yyyy-MM-dd HH:mm or null");
			date = sc.nextLine();
			if (date.equals("null")) {
				return null;
			}
		}
		try {
			ldt = LocalDateTime.parse(date, FORMATTER);
		} catch (DateTimeException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
		return ldt;
	}

	/**
	 * Check if the user input is a valid date or null
	 * 
	 * @param date
	 *            a String
	 * @return a valid LocalDateTime or null
	 */
	public static LocalDateTime checkDate(String date) {
		LocalDateTime ldt = null;
		date = date.replace('T', ' ');
		if (P.matcher(date).find()) {
			ldt = LocalDateTime.parse(date, FORMATTER);
		}
		return ldt;
	}

	/**
	 * Check if the user input matches a valid Company
	 * 
	 * @param cli
	 * @param sc
	 *            the scanner
	 * @param companyService
	 * @see CompanyService#getById(int)
	 * @return a valid Company or null
	 */
	public static Company checkCompany(CLI cli, Scanner sc, CompanyServiceImpl companyService) {
		boolean validEntry = true;
		int manufacture = 0;
		Company cy = null;
		do {
			// you can only choose an id from the list
			cli.listCompanies();
			manufacture = checkId(sc);

			if (manufacture == 0) {
				return null;
			} else if (manufacture > 0) {
				cy = companyService.getById(manufacture);
			}
			if (cy == null || manufacture < 0) {
				System.out
						.println("Wrong id, no company found ! Please enter a new value \n");
				validEntry = false;
			} else {
				validEntry = true;
			}
		} while (!validEntry);
		return cy;
	}

	/**
	 * Displays "success" or "failure" depending of the output of the query
	 * 
	 * @param queryExecuted
	 *            the value returned by the method executeUpdate() of the
	 *            PreparedStatement
	 */
	public static boolean checkSuccess(int queryExecuted) {
		System.out.print("Query executed ? ");
		if (queryExecuted == 0) {
			System.out.println("Failed");
			return false;
		} else {
			System.out.println("Success");
			return true;
		}
	}

}
