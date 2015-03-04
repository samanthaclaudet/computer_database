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

	public int getNbComputers(String name);

	public List<Computer> getAll();

	public Computer getById(int id);

	public Page getPage(String name, int idx, int size, String orderBy);

	public void set(Computer c);

	public void update(int id, Computer c);

	public void delete(int id);
	
	public void deleteFromCompany(int id);
}
