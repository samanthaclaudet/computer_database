package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDAOImpl;
import com.excilys.cdb.service.interfaces.ComputerService;

/**
 * @see ComputerDAOImpl
 * 
 * @author sclaudet
 *
 */
public enum ComputerServiceImpl implements ComputerService {
	INSTANCE;

	private ComputerServiceImpl() {
	}

	/**
	 * Counts the number of computers in the database
	 * 
	 * @return total number of computers in the database (int)
	 */
	public int getNbComputers() {
		return ComputerDAOImpl.INSTANCE.getNbComputers();
	}

	/**
	 * Gets a list of all computers
	 * 
	 * @return the list of all computers in the database
	 */
	public List<Computer> getAll() {
		return ComputerDAOImpl.INSTANCE.getAll();
	}

	/**
	 * Gets a Computer by its id
	 * 
	 * @param id
	 * @return a Computer whose id was passed as parameter
	 */
	public Computer getById(int id) {
		return ComputerDAOImpl.INSTANCE.getById(id);
	}

	/**
	 * Gets Computers by their name
	 * 
	 * @param name
	 * @return a Page with all computers containing the name
	 */
	public Page getByName(String name, int idx, int size) {
		return ComputerDAOImpl.INSTANCE.getByName(name, idx, size);
	}
	
	/**
	 * Gets a page
	 * 
	 * @param idx
	 *            offest for the select query
	 * @param size
	 *            number of computers per page
	 * @return a page
	 */
	public Page getPage(int idx, int size) {
		return ComputerDAOImpl.INSTANCE.getPage(idx, size);
	}

	/**
	 * Inserts the computer passed in parameter into the database
	 * 
	 * @param c
	 *            the computer you want to add in the database
	 */
	public void set(Computer c) {
		ComputerDAOImpl.INSTANCE.set(c);
	}

	/**
	 * Replaces the computer in the database at the id passed in parameter by
	 * the computer passed in parameter
	 * 
	 * @param id
	 *            the id of the computer you want to modify in the database
	 * @param c
	 *            the new computer that will replace the one in the database
	 */
	public void update(int id, Computer c) {
		ComputerDAOImpl.INSTANCE.update(id, c);
	}

	/**
	 * Deletes a computer from the database at the id passed in parameter
	 * 
	 * @param id
	 *            the id of the computer you want to delete
	 */
	public void delete(int id) {
		ComputerDAOImpl.INSTANCE.delete(id);
	}

}
