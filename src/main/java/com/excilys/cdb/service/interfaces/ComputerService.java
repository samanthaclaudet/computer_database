package com.excilys.cdb.service.interfaces;

import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

/**
 * 
 * @author sclaudet
 *
 */
public interface ComputerService {

	public int getNbComputers();
	
	public Page getPage(int idx, int size);
	
	public List<Computer> getAll();

	public Computer getById(int id);
	
	public Page getByName(String name, int idx, int size);

	public void set(Computer c);

	public void update(int id, Computer c);

	public void delete(int id);
}