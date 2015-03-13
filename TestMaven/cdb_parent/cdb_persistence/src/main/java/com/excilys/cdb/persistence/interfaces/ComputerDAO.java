package com.excilys.cdb.persistence.interfaces;

import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

/**
 * 
 * @author sclaudet
 *
 */
public interface ComputerDAO extends AbstractDAO<Computer> {

	/**
	 * Counts the number of computers in the database
	 * @param name
	 * 			  name of the computer or the company you are looking for
	 * @return total number of computers in the database (int)
	 */
	public int getNbComputers(String name);

	/**
	 * Gets a list of all computers
	 * 
	 * @return the list of all computers in the database
	 */
	public List<Computer> getAll();

	/**
	 * Gets a Computer by its id
	 * 
	 * @param id
	 * @return a Computer whose id was passed as parameter
	 */
	public Computer getById(int id);

	/**
	 * Gets a Page, can look for Computers by their name or the name of their company
	 * 
	 * @param name
	 * 			  name of the computer or the company you are looking for
	 * @param idx
	 *            offest for the select query
	 * @param size
	 *            number of computers per page
	 * @param orderBy
	 * 			  order by feature
	 * @return a Page with all computers containing the name
	 */
	public Page getPage(String name, int idx, int size, String orderBy);

	/**
	 * Inserts the computer passed in parameter into the database
	 * 
	 * @param c
	 *            the computer you want to add in the database
	 */
	public void set(Computer c);

	/**
	 * Replaces the computer in the database at the id passed in parameter by
	 * the computer passed in parameter
	 * @param c
	 *            the new computer that will replace the one in the database
	 */
	public void update(Computer c);

	/**
	 * Deletes a computer from the database at the id passed in parameter
	 * 
	 * @param id
	 *            the id of the computer you want to delete
	 */
	public void delete(int id);
	
	/**
	 * Deletes a computer from the database whose company has the id passed in parameter
	 * 
	 * @param id
	 *            the id of the company you want to delete
	 */
	public void deleteFromCompany(int id);
}
