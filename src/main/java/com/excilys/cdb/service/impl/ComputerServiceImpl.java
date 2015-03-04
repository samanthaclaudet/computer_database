package com.excilys.cdb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.impl.ComputerDAOImpl;
import com.excilys.cdb.service.interfaces.ComputerService;

/**
 * @see ComputerDAOImpl
 * 
 * @author sclaudet
 *
 */
@Service
public class ComputerServiceImpl implements ComputerService {
	
	@Autowired
	ComputerDAOImpl computerDAOImpl;
	
	public ComputerServiceImpl() {
	}

	/**
	 * Counts the number of computers in the database
	 * 
	 * @return total number of computers in the database (int)
	 */
	public int getNbComputers() {
		return computerDAOImpl.getNbComputers("");
	}

	/**
	 * Gets a list of all computers
	 * 
	 * @return the list of all computers in the database
	 */
	public List<Computer> getAll() {
		return computerDAOImpl.getAll();
	}

	/**
	 * Gets a Computer by its id
	 * 
	 * @param id
	 * @return a Computer whose id was passed as parameter
	 */
	public Computer getById(int id) {
		return computerDAOImpl.getById(id);
	}

	/**
	 * Gets a Page, can look for Computers by their name or the name of their company
	 * 
	 * @param name
	 * 			  name of the computer or the company you are looking for
	 * @param idx
	 *            offset for the select query
	 * @param size
	 *            number of computers per page
	 * @param orderBy
	 * 			  order by feature
	 * @return a Page with all computers containing the name
	 */
	public Page getPage(String name, int idx, int size, String orderBy) {
		Page p = computerDAOImpl.getPage(name, idx, size, orderBy);
		int nb = computerDAOImpl.getNbComputers(name);
		p.setNbComputers(nb);
		return p;
	}

	/**
	 * Inserts the computer passed in parameter into the database
	 * 
	 * @param c
	 *            the computer you want to add in the database
	 */
	public void set(Computer c) {
		computerDAOImpl.set(c);
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
		computerDAOImpl.update(id, c);
	}

	/**
	 * Deletes a computer from the database at the id passed in parameter
	 * 
	 * @param id
	 *            the id of the computer you want to delete
	 */
	public void delete(int id) {
		computerDAOImpl.delete(id);
	}

}
