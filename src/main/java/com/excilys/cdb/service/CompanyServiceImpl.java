package com.excilys.cdb.service;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAOImpl;
import com.excilys.cdb.persistence.ComputerDAOImpl;
import com.excilys.cdb.persistence.ConnectionDB;
import com.excilys.cdb.service.interfaces.CompanyService;

import exceptions.SQLRuntimeException;

/**
 * @see CompanyDAOImpl
 * 
 * @author sclaudet
 *
 */
public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;

	private static final Logger logger = LoggerFactory
			.getLogger(CompanyServiceImpl.class);
	
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
	 * the related computers in a transaction
	 * 
	 * @param id
	 *            the id of the company you want to delete
	 */
	public void delete(int id) {
		Connection conn = ConnectionDB.getConnection(false); // set AutoCommit to false	
		try {
			ComputerDAOImpl.INSTANCE.delete(id, conn); // deletes all the computer with that company first
			CompanyDAOImpl.INSTANCE.delete(id, conn); // deletes the comnpany
		} catch (SQLRuntimeException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			ConnectionDB.cancelTransaction(conn); // rollback
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, true); // commit then close the connection
		}
	}
	
}
