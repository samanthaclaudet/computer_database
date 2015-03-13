package com.excilys.cdb.service.interfaces;

import java.util.List;

import com.excilys.cdb.model.Company;

/**
 *
 * @author sclaudet
 *
 */
public interface CompanyService {
	
	public List<Company> getAll();

	public Company getById(int id);
	
	public void delete(int id);
}
