package com.excilys.cdb.persistence.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exceptions.SQLRuntimeException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.interfaces.CompanyDAO;
import com.excilys.cdb.persistence.mappers.CompanyMapperSpring;

/**
 * CompanyDAO makes the connection between the database and the Company object
 * The available methods are :
 * <ul>
 * <li>getAll()</li>
 * <li>getById(int id)</li>
 * <li>delete(int id, Connection conn)</li>
 * </ul>
 * 
 * @see Company
 * 
 * @author sclaudet
 *
 */
@Repository
public class CompanyDAOImpl implements CompanyDAO {
	
	@Autowired
	private CompanyMapperSpring mapperSpring;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public CompanyDAOImpl() {
	}

	/**
	 * Gets a list of all companies
	 * 
	 * @return the list of all companies in the database
	 */
	public List<Company> getAll() {
		return jdbcTemplate.query("SELECT * FROM company", mapperSpring);
	}

	/**
	 * Gets a Company by its id Used to instantiate the Company in a Computer
	 * 
	 * @param id
	 * @return a Company whose id was passed as parameter
	 */
	public Company getById(int id) {
		Company c = null;
		if (id != 0) {
			c = jdbcTemplate.queryForObject("SELECT * FROM company WHERE id = ?", new Object[] {id}, mapperSpring);
		}
		return c;
	}
	
	/**
	 * Deletes a company from the database at the id passed in parameter
	 * 
	 * @param id
	 *            the id of the company you want to delete
	 */
	public void delete(int id) throws SQLRuntimeException{
		try {
			jdbcTemplate.update("DELETE FROM company WHERE id=?", new Object[] {id});
		} catch (DataAccessException e) {
			throw new SQLRuntimeException();
		}
	}

}
