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
	
	public CompanyServiceImpl() {
	}

	/**
	 * Gets a list of all companies
	 * 
	 * @return the list of all companies in the database
	 */
	public List<Company> getAll() {
		return companyDAOImpl.getAll();
	}

	/**
	 * Gets a Company by its id Used to instantiate the Company in a Computer
	 * 
	 * @param id
	 * @return a Company whose id was passed as parameter
	 */
	public Company getById(int id) {
		return companyDAOImpl.getById(id);
	}

	/**
	 * Deletes a company from the database at the id passed in parameter and all
	 * the related computers in a transaction
	 * 
	 * @param id
	 *            the id of the company you want to delete
	 */
	@Transactional(rollbackFor=SQLRuntimeException.class)
	public void delete(int id) {
		computerDAOImpl.deleteFromCompany(id); // deletes all the computer with that company first
		companyDAOImpl.delete(id); // deletes the company
	}
	
}
