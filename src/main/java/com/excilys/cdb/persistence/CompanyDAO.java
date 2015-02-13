package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Company;

/**
 *
 * @author sclaudet
 *
 */
public interface CompanyDAO extends AbstractDAO<Company>{
	
	public List<Company> getAll();
	public Company getById(int id);
}
