package com.excilys.cdb.ui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.CompanyDAOImpl;
import com.excilys.cdb.persistence.ComputerDAOImpl;

/**
 * CLI is the user interface of the computer-database program
 * <ul>
 * <li> List all computers (result can be pageable) </li>
 * <li> List all companies </li>
 * <li> Show the details of a specific computer </li>
 * <li> Add a new computer to the database </li>
 * <li> Update a computer in the database </li>
 * <li> Delete a computer from the database </li>
 * </ul>
 * 
 * @author sclaudet
 *
 */
public class CLI {
	
	/**
	 * runProgram becomes false when the user wants to exit the program 
	 */
	private static boolean runProgram = true;
	
	public static Scanner sc= new Scanner(System.in);

	public static void run() {
		do {
			showMenu();
			requestTreatment();
		} while(runProgram);
		
		sc.close();
	}

	public static void showMenu() {
		System.out.println("\n What do you want to do ?");
		System.out.println("Press 1 -> List computers");
		System.out.println("Press 2 -> List companies");
		System.out.println("Press 3 -> Show computer details ");
		System.out.println("Press 4 -> Create a computer");
		System.out.println("Press 5 -> Update a computer");
		System.out.println("Press 6 -> Delete a computer");
		System.out.println("Press 7 -> Exit \n");
	}
	
	/**
	 * requestTreatment retrieves the user input in a loop until the entry is valid
	 * @see CLI#getUserChoice(String)
	 */
	public static void requestTreatment() {
		boolean validEntry;
		do {
			System.out.print("Your choice : ");
			String choice = sc.nextLine();
			validEntry = getUserChoice(choice);
		} while (!validEntry);
	}
	
	/**
	 * 
	 * @param choice
	 * 			the user input
	 * @return true id the input is valid, false otherwise
	 */
	public static boolean getUserChoice(String choice) {
		switch (choice) {
			// list of all computers
			case "1" : 
				System.out.println("Do you want the result to be pageable ? Y/N");
				String answer = sc.nextLine();
				if (answer.toUpperCase().matches("Y")) {
					// displays result in a page
					Page p = new Page(100,  ComputerDAOImpl.INSTANCE.getNbComputers()); // 100 computers per page
					p.menuPage(sc);			
				}
				else {
					listComputers();
				}
				return true;
			// list of all companies
			case "2" : 
				listCompanies();
				return true;
			// shows the details of one computer
			case "3" :
				showComputer();
				return true;
			// creates a new computer
			case "4" : 
				createComputer();
				return true;
			// updates a computer
			case "5" : 
				updateComputer();
				return true;
			// deletes a computer
			case "6" : 
				deleteComputer();
				return true;
			// exit the program
			case "7" :
				runProgram = false;
				return true;
			// user input is not valid
			default : 
				System.out.println("Non valid entry, please try again");
				return false;
		}		
	}
	
	/**
	 * Displays the list of all computers
	 * @see ComputerDAO#getAll()
	 */
	public static void listComputers() {
		List<Computer> CL;
		CL = ComputerDAOImpl.INSTANCE.getAll();
		for(Computer c : CL ) {
			System.out.println(c);
		}
	}
	
	/**
	 * Displays the list of all companies
	 * @see CompanyDAO#getAll()
	 */
	public static void listCompanies() {
		List<Company> CL;
		CL = CompanyDAOImpl.INSTANCE.getAll();
		for(Company c : CL ) {
			System.out.println(c);
		}
	}
	
	/**
	 * Displays the details of one computer
	 * @see ComputerDAO#getById(int)
	 */
	public static void showComputer() {
		System.out.println("What is the id of the computer you want to display ?");
		// check validity of input
		int id = Util.checkId(sc);

		Computer c = ComputerDAOImpl.INSTANCE.getById(id);
		if (c != null) {
			System.out.println(c);
		} else {
			System.out.println("Wrong id, no computer found");
		}
	}
	
	/**
	 * Adds a computer to the database
	 * @see ComputerDAO#set(Computer)
	 */
	public static void createComputer() {
		// put the user input into a Computer object
		Computer c = setComputerData();
		System.out.println("Computer to add: " + c);
		ComputerDAOImpl.INSTANCE.set(c);
	}
	
	/**
	 * Updates a computer in the database
	 * @see ComputerDAO#update(int, Computer)
	 */
	public static void updateComputer() {
		System.out.println("What is the id of the computer you want to modify ?");
		// check validity of input
		int id = Util.checkId(sc);
		Computer newc = null;
		// put the current entry into a Computer object
		Computer c = ComputerDAOImpl.INSTANCE.getById(id);
		if (c != null) {
			// put the user entry into a new Computer object
			newc = updateComputerData(c);
			ComputerDAOImpl.INSTANCE.update(id, newc);
		} else {
			System.out.println("Wrong id, no computer found");
		}
	}
	
	/**
	 * Deletes a computer from the database
	 * @see ComputerDAO#delete(int)
	 */
	public static void deleteComputer(){
		System.out.println("What is the id of the computer you want to delete ?");
		// check validity of input
		int id = Util.checkId(sc);
		ComputerDAOImpl.INSTANCE.delete(id);
	}
	
	/**
	 * Creates a computer based on the user inputs
	 * 
	 * @return a computer based on the user inputs
	 */
	public static Computer setComputerData() {
		System.out.println("Please enter the name of the new computer");
		String name = sc.nextLine();
		System.out.println("Please enter the date of introduction, format yyyy-MM-dd HH:mm (null if unknown)");
		// check validity of input
		LocalDateTime ldti = Util.checkDate(sc);
		System.out.println("Please enter the date of discontinuation, format yyyy-MM-dd HH:mm (null if unknown)");
		// check validity of input
		LocalDateTime ldtd = Util.checkDate(sc);
		System.out.println("Please enter the company id from the list (0 if unknown)");
		// check validity of input
		Company cy = Util.checkCompany(sc);
		
		Computer c = new Computer(name, ldti, ldtd, cy);
		return c;
	}
	
	/**
	 * Updates values of a computer
	 * 
	 * @param c
	 * 			Current Computer values
	 * @return new Computer
	 */
	public static Computer updateComputerData(Computer c) {
		// update the name of the computer
		System.out.println("Do you want to change the name of the computer ? Y/N");
		String answer = sc.nextLine();
		if (answer.toUpperCase().matches("Y")) {
			System.out.println("Please enter the new name");
			String name = sc.nextLine();
			c.setName(name);
		}
		// update the date of introduction
		System.out.println("Do you want to change the date of introduction ? Y/N");
		answer = sc.nextLine();
		if (answer.toUpperCase().matches("Y")) {
			System.out.println("Please enter the new date, format yyyy-MM-dd HH:mm (null if unknown)");
			// check validity of input
			LocalDateTime ldti = Util.checkDate(sc);
			c.setDate_introduced(ldti);
		}
		// update the date of discontinuation
		System.out.println("Do you want to change the date of discontinuation ? Y/N");
		answer = sc.nextLine();
		if (answer.toUpperCase().matches("Y")) {
			System.out.println("Please enter the new date, format yyyy-MM-dd HH:mm (null if unknown)");
			// check validity of input
			LocalDateTime ldtd = Util.checkDate(sc);
			c.setDate_discontinued(ldtd);
		}
		// update the company
		System.out.println("Do you want to change the company ? Y/N");
		answer = sc.nextLine();
		if (answer.toUpperCase().matches("Y")) {
			System.out.println("Please enter the id of the new company (0 if unknown)");
			// check validity of input
			Company cy = Util.checkCompany(sc);
			c.setManufacturer(cy);
		}
		return c;
	}
	
}