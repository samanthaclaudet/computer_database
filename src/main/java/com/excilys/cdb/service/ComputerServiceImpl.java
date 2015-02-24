package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDAOImpl;
import com.excilys.cdb.persistence.ConnectionDB;
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
		ConnectionDB.openConnection(true);
		int nb = ComputerDAOImpl.INSTANCE.getNbComputers();
		ConnectionDB.closeConnection(false);
		return nb;
	}

	/**
	 * Gets a list of all computers
	 * 
	 * @return the list of all computers in the database
	 */
	public List<Computer> getAll() {
		ConnectionDB.openConnection(true);
		List<Computer> listComputers = ComputerDAOImpl.INSTANCE.getAll();
		ConnectionDB.closeConnection(false);
		return listComputers;
	}

	/**
	 * Gets a Computer by its id
	 * 
	 * @param id
	 * @return a Computer whose id was passed as parameter
	 */
	public Computer getById(int id) {
		ConnectionDB.openConnection(true);
		Computer computer = ComputerDAOImpl.INSTANCE.getById(id);
		ConnectionDB.closeConnection(false);
		return computer;
	}

	/**
	 * Gets Computers by their name or the name of their company
	 * 
	 * @param name
	 * @return a Page with all computers containing the name
	 */
	public Page getByName(String name, int idx, int size) {
		ConnectionDB.openConnection(true);
		Page page = ComputerDAOImpl.INSTANCE.getByName(name, idx, size);
		ConnectionDB.closeConnection(false);
		return page;
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
		ConnectionDB.openConnection(true);
		Page page = ComputerDAOImpl.INSTANCE.getPage(idx, size);
		ConnectionDB.closeConnection(false);
		return page;
	}

	/**
	 * Inserts the computer passed in parameter into the database
	 * 
	 * @param c
	 *            the computer you want to add in the database
	 */
	public void set(Computer c) {
		ConnectionDB.openConnection(true);
		ComputerDAOImpl.INSTANCE.set(c);
		ConnectionDB.closeConnection(false);
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
		ConnectionDB.openConnection(true);
		ComputerDAOImpl.INSTANCE.update(id, c);
		ConnectionDB.closeConnection(false);
	}

	/**
	 * Deletes a computer from the database at the id passed in parameter
	 * 
	 * @param id
	 *            the id of the computer you want to delete
	 */
	public void delete(int id) {
		ConnectionDB.openConnection(true);
		ComputerDAOImpl.INSTANCE.delete(id);
		ConnectionDB.closeConnection(false);
	}

}
