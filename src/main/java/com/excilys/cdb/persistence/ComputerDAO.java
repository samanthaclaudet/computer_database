package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Computer;

/**
 * 
 * @author sclaudet
 *
 */
public interface ComputerDAO extends AbstractDAO<Computer>{
	public List<Computer> getAll();
	public Computer getById(int id);
	public void set(Computer c);
	public void update(int id, Computer c);
	public void delete(int id);
}
