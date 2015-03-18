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

	 /**
	  * @see com.excilys.cdb.service.interfaces.ComputerService#getNbComputers()
	  */
	public int getNbComputers() {
		return computerDAOImpl.getNbComputers("");
	}

	 /**
	  * @see com.excilys.cdb.service.interfaces.ComputerService#getAll()
	  */
	public List<Computer> getAll() {
		return computerDAOImpl.getAll();
	}

	 /**
	  * @see com.excilys.cdb.service.interfaces.ComputerService#getById(int)
	  */
	public Computer getById(int id) {
		return computerDAOImpl.getById(id);
	}

	 /**
	  * @see com.excilys.cdb.service.interfaces.ComputerService#getPage(String, int, int, String)
	  */
	public Page getPage(String name, int idx, int size, String orderBy) {
		Page p = computerDAOImpl.getPage(name, idx, size, orderBy);
		int nb = computerDAOImpl.getNbComputers(name);
		p.setNbComputers(nb);
		return p;
	}

	 /**
	  * @see com.excilys.cdb.service.interfaces.ComputerService#set(Computer)
	  */
	public void set(Computer c) {
		computerDAOImpl.set(c);
	}

	 /**
	  * @see com.excilys.cdb.service.interfaces.ComputerService#update(Computer)
	  */
	public void update(Computer c) {
		computerDAOImpl.update(c);
	}

	 /**
	  * @see com.excilys.cdb.service.interfaces.ComputerService#delete(int)
	  */
	public void delete(int id) {
		computerDAOImpl.delete(id);
	}

}
