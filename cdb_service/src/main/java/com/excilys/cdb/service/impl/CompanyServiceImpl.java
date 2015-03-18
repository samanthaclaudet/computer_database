package com.excilys.cdb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exceptions.SQLRuntimeException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.impl.CompanyDAOImpl;
import com.excilys.cdb.persistence.impl.ComputerDAOImpl;
import com.excilys.cdb.service.interfaces.CompanyService;

/**
 * @see CompanyDAOImpl
 * 
 * @author sclaudet
 *
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDAOImpl companyDAOImpl;
	
	@Autowired
	private ComputerDAOImpl computerDAOImpl;

	 /**
	  * @see com.excilys.cdb.service.interfaces.CompanyService#getAll()
	  */
	public List<Company> getAll() {
		return companyDAOImpl.getAll();
	}


	 /**
	  * @see com.excilys.cdb.service.interfaces.CompanyService#getById(int)
	  */
	public Company getById(int id) {
		return companyDAOImpl.getById(id);
	}

	 /**
	  * @see com.excilys.cdb.service.interfaces.CompanyService#delete(int)
	  */
	@Transactional(rollbackFor=SQLRuntimeException.class)
	public void delete(int id) {
		computerDAOImpl.deleteFromCompany(id); // deletes all the computer with that company first
		companyDAOImpl.delete(id); // deletes the company
	}
	
}
