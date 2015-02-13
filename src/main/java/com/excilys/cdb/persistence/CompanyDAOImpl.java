package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.model.Company;

/**
 * CompanyDAO makes the connection between the database and the Company object
 * This is a singleton
 * The available methods are :
 * <ul>
 * <li> getAll()</li>
 * <li> getById(int id)</li>
 * </ul>
 * @see Company
 * 
 * @author sclaudet
 *
 */
public enum CompanyDAOImpl implements CompanyDAO{
	INSTANCE;
	
	private CompanyDAOImpl() {}
	
	/**
	 * Gets a list of all companies
	 * 
	 * @return the list of all companies in the database
	 */
	public List<Company> getAll() {
		List<Company> lc = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			conn = ConnectionDB.getConnection();
			pstm = conn.prepareStatement("SELECT * FROM company");
			r = pstm.executeQuery();
			// mapping the ResultSet into a list of companies
			lc = CompanyMapper.INSTANCE.toList(r);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, pstm, r);
		}
		return lc;
	}
	
	/**
	 * Gets a Company by its id
	 * Used to instantiate the Company in a Computer
	 * 
	 * @param id
	 * @return a Company whose id was passed as parameter
	 */
	public Company getById(int id) {
		Company c = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			conn = ConnectionDB.getConnection();
			pstm = conn.prepareStatement("SELECT * FROM company WHERE id = ?");
			pstm.setInt(1, id);
			r = pstm.executeQuery();
			// mapping the ResultSet into a company
			c = CompanyMapper.INSTANCE.toObject(r);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, pstm, r);
		}
		return c;
	}
	
}

