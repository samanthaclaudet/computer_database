package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.interfaces.CompanyDAO;
import com.excilys.cdb.ui.Util;

/**
 * CompanyDAO makes the connection between the database and the Company object
 * This is a singleton The available methods are :
 * <ul>
 * <li>getAll()</li>
 * <li>getById(int id)</li>
 * </ul>
 * 
 * @see Company
 * 
 * @author sclaudet
 *
 */
public enum CompanyDAOImpl implements CompanyDAO {
	INSTANCE;

	private static final Logger logger = LoggerFactory
			.getLogger(CompanyDAOImpl.class);

	private CompanyDAOImpl() {
	}

	/**
	 * Gets a list of all companies
	 * 
	 * @return the list of all companies in the database
	 */
	public List<Company> getAll() {
		List<Company> lc = null;
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			pstm = conn.prepareStatement("SELECT * FROM company");
			r = pstm.executeQuery();
			// mapping the ResultSet into a list of companies
			lc = CompanyMapper.INSTANCE.toList(r);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, pstm, r);
		}
		return lc;
	}

	/**
	 * Gets a Company by its id Used to instantiate the Company in a Computer
	 * 
	 * @param id
	 * @return a Company whose id was passed as parameter
	 */
	public Company getById(int id) {
		Company c = null;
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			pstm = conn.prepareStatement("SELECT * FROM company WHERE id = ?");
			pstm.setInt(1, id);
			r = pstm.executeQuery();
			// mapping the ResultSet into a company
			if (r.next()) {
				c = CompanyMapper.INSTANCE.toObject(r);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, pstm, r);
		}
		return c;
	}

	/**
	 * Deletes a company from the database at the id passed in parameter and all
	 * the related computers
	 * 
	 * @param id
	 *            the id of the company you want to delete
	 */
	public void delete(int id) {
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null, pstm2 = null;
		;
		try {
			// delete all computers with the company to be deleted
			pstm = conn
					.prepareStatement("DELETE FROM computer WHERE company_id =" + id);
			int queryExecuted = pstm.executeUpdate();
			// displays "success" or "failure"
			Util.checkSuccess(queryExecuted);

			// delete the company
			pstm2 = conn
					.prepareStatement("DELETE FROM company WHERE id =" + id);
			queryExecuted = pstm2.executeUpdate();
			// displays "success" or "failure"
			Util.checkSuccess(queryExecuted);

		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (pstm2 != null) {
					pstm2.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				throw new RuntimeException();
			}
			ConnectionDB.closeConnection(conn, pstm, null);
		}

	}

}
