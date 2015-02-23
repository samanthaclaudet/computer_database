package com.excilys.cdb.persistence.interfaces;

import java.sql.Connection;
import java.util.List;

import com.excilys.cdb.model.Company;

/**
 *
 * @author sclaudet
 *
 */
public interface CompanyDAO extends AbstractDAO<Company> {

	public List<Company> getAll();

	public Company getById(int id);
	
	public void delete(int id, Connection conn);
}
