package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAOImpl;
import com.excilys.cdb.service.interfaces.CompanyService;

/**
 * @see CompanyDAOImpl
 * 
 * @author sclaudet
 *
 */
public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;

	private CompanyServiceImpl() {
	}

	/**
	 * Gets a list of all companies
	 * 
	 * @return the list of all companies in the database
	 */
	public List<Company> getAll() {
		return CompanyDAOImpl.INSTANCE.getAll();
	}

	/**
	 * Gets a Company by its id Used to instantiate the Company in a Computer
	 * 
	 * @param id
	 * @return a Company whose id was passed as parameter
	 */
	public Company getById(int id) {
		return CompanyDAOImpl.INSTANCE.getById(id);
	}

	/**
	 * Deletes a company from the database at the id passed in parameter and all
	 * the related computers
	 * 
	 * @param id
	 *            the id of the company you want to delete
	 */
	public void delete(int id) {
		CompanyDAOImpl.INSTANCE.delete(id);
	}
	
}
