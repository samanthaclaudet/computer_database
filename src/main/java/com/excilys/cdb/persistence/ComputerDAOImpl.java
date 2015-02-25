package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.DTOMapper;
import com.excilys.cdb.exceptions.SQLRuntimeException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.interfaces.ComputerDAO;
import com.excilys.cdb.ui.Util;

/**
 * ComputerDAO makes the connection between the database and the Computer object
 * This is a singleton * The available methods are :
 * <ul>
 * <li>getAll()</li>
 * <li>getById(int id)</li>
 * <li>getByName(String name, int idx, int size)</li>
 * <li>getNbComputers()</li>
 * <li>getPage(int idx, int size)</li>
 * <li>set(Computer c)</li>
 * <li>update(int id, Computer c)</li>
 * <li>delete(int id)</li>
 * <li>delete(int id, Connection conn)</li>
 * </ul>
 * 
 * @see Computer
 * 
 * @author sclaudet
 *
 */
public enum ComputerDAOImpl implements ComputerDAO {
	INSTANCE;

	private static final Logger logger = LoggerFactory
			.getLogger(ComputerDAOImpl.class);

	private ComputerDAOImpl() {
	}

	/**
	 * Counts the number of computers in the database
	 * 
	 * @return total number of computers in the database (int)
	 */
	public int getNbComputers(String name) {
		int nb = 0;
		name = "%" + name + "%";
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			pstm = conn.prepareStatement("SELECT COUNT(*) FROM computer LEFT JOIN company ON computer.company_id = company.id"
						+ " WHERE computer.name LIKE ? OR company.name LIKE ?");
			pstm.setString(1, name);
			pstm.setString(2, name);
			r = pstm.executeQuery();
			if (r.next()) {
				nb = r.getInt(1);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeResultSet(r);
			ConnectionDB.closePreparedStatement(pstm);
			ConnectionDB.closeConnection();
		}
		return nb;
	}

	/**
	 * Gets a page
	 * 
	 * @param idx
	 *            offest for the select query
	 * @param size
	 *            number of computers per page
	 * @return a page
	 */
	public Page getPage(int idx, int size) {
		List<Computer> lc = null;
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			pstm = conn.prepareStatement("SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.id LIMIT ?, ?");
			pstm.setInt(1, idx * size);
			pstm.setInt(2, size);
			r = pstm.executeQuery();
			// mapping the ResultSet into a list of computers
			lc = ComputerMapper.INSTANCE.toList(r);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeResultSet(r);
			ConnectionDB.closePreparedStatement(pstm);
			ConnectionDB.closeConnection();
		}
		Page p = new Page(size, 0, idx);
		p.setComputers(DTOMapper.listToDto(lc));
		return p;
	}
	
	/**
	 * Gets a list of all computers
	 * 
	 * @return the list of all computers in the database
	 */
	public List<Computer> getAll() {
		List<Computer> lc = null;
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			pstm = conn.prepareStatement("SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id");
			r = pstm.executeQuery();
			// mapping the ResultSet into a list of computers
			lc = ComputerMapper.INSTANCE.toList(r);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeResultSet(r);
			ConnectionDB.closePreparedStatement(pstm);
			ConnectionDB.closeConnection();
		}
		return lc;
	}

	/**
	 * Gets a Computer by its id
	 * 
	 * @param id
	 * @return a Computer whose id was passed as parameter
	 */
	public Computer getById(int id) {
		Computer c = null;
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			pstm = conn.prepareStatement("SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?");
			pstm.setInt(1, id);
			r = pstm.executeQuery();
			// mapping the ResultSet into a computer
			if (r.next()) {
				c = ComputerMapper.INSTANCE.toObject(r);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeResultSet(r);
			ConnectionDB.closePreparedStatement(pstm);
			ConnectionDB.closeConnection();
		}
		return c;
	}

	/**
	 * Gets Computers by their name or the name of their company
	 * 
	 * @param name
	 * @return a Page with all computers containing the name
	 */
	public Page getByName(String name, int idx, int size) {
		name = "%" + name + "%";
		List<Computer> lc = new ArrayList<Computer>();
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null, pstm2 = null;
		ResultSet r = null;
		try {
			pstm = conn.prepareStatement("SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id"
					+ " WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY computer.id LIMIT ?, ?");

			pstm.setString(1, name);
			pstm.setString(2, name);
			pstm.setInt(3, idx * size);
			pstm.setInt(4, size);
			r = pstm.executeQuery();
			if (r.next()) {
				// mapping the ResultSet into a list of computers
				lc = ComputerMapper.INSTANCE.toList(r);

			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeResultSet(r);
			ConnectionDB.closePreparedStatement(pstm);
			ConnectionDB.closePreparedStatement(pstm2);
			ConnectionDB.closeConnection();
		}
		Page p = new Page(size, 0, idx);
		p.setComputers(DTOMapper.listToDto(lc));
		return p;
	}
	
	/**
	 * Inserts the computer passed in parameter into the database
	 * 
	 * @param c
	 *            the computer you want to add in the database
	 */
	public void set(Computer c) {
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null;
		try {
			pstm = conn
					.prepareStatement("INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)");
			pstm.setString(1, c.getName());
			// the date of introduction can be null
			if (c.getIntroduced() != null) {
				// converts LocalDateTime into a Timestamp
				pstm.setTimestamp(2, Timestamp.valueOf(c.getIntroduced()));
			} else {
				pstm.setNull(2, java.sql.Types.TIMESTAMP);
			}
			// the date of discontinuation can be null
			if (c.getDiscontinued() != null) {
				// converts LocalDateTime into a Timestamp
				pstm.setTimestamp(3, Timestamp.valueOf(c.getDiscontinued()));
			} else {
				pstm.setNull(3, java.sql.Types.TIMESTAMP);
			}
			// the company can be null
			if (c.getCompany() != null) {
				// retrieves the company's id
				pstm.setInt(4, c.getCompany().getId());
			} else {
				pstm.setNull(4, java.sql.Types.BIGINT);
			}
			System.out.println(pstm.toString());
			int queryExecuted = pstm.executeUpdate();
			// displays "success" or "failure"
			Util.checkSuccess(queryExecuted);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closePreparedStatement(pstm);
			ConnectionDB.closeConnection();
		}
	}

	/**
	 * Replaces the computer in the database at the id passed in parameter by
	 * the computer passed in parameter
	 * 
	 * @param id
	 *            the id of the computer you want to modify in the database
	 * @param c
	 *            the new computer that will replace the one in the database
	 */
	public void update(int id, Computer c) {
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null;
		try {
			pstm = conn
					.prepareStatement("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?");
			pstm.setString(1, c.getName());
			// the date of introduction can be null
			if (c.getIntroduced() != null) {
				// converts LocalDateTime into a Timestamp
				pstm.setTimestamp(2, Timestamp.valueOf(c.getIntroduced()));
			} else {
				pstm.setNull(2, java.sql.Types.TIMESTAMP);
			}
			// the date of discontinuation can be null
			if (c.getDiscontinued() != null) {
				// converts LocalDateTime into a Timestamp
				pstm.setTimestamp(3, Timestamp.valueOf(c.getDiscontinued()));
			} else {
				pstm.setNull(3, java.sql.Types.TIMESTAMP);
			}
			// the company can be null
			if (c.getCompany() != null) {
				// retrieves the company's id
				pstm.setInt(4, c.getCompany().getId());
			} else {
				pstm.setNull(4, java.sql.Types.BIGINT);
			}
			pstm.setInt(5, id);
			System.out.println(pstm.toString());
			int queryExecuted = pstm.executeUpdate();
			// displays "success" or "failure"
			Util.checkSuccess(queryExecuted);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closePreparedStatement(pstm);
			ConnectionDB.closeConnection();
		}
	}

	/**
	 * Deletes a computer from the database at the id passed in parameter
	 * 
	 * @param id
	 *            the id of the computer you want to delete
	 */
	public void delete(int id) {
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement("DELETE FROM computer WHERE id=" + id);
			int queryExecuted = pstm.executeUpdate();
			// displays "success" or "failure"
			Util.checkSuccess(queryExecuted);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closePreparedStatement(pstm);
			ConnectionDB.closeConnection();
		}
	}

	/**
	 * Deletes a computer from the database whose company has the id passed in parameter
	 * 
	 * @param id
	 *            the id of the company you want to delete
	 */
	public void deleteFromCompany(int id) throws SQLRuntimeException{
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement("DELETE FROM computer WHERE company_id=" + id);
			int queryExecuted = pstm.executeUpdate();
			if (!Util.checkSuccess(queryExecuted)) {
				throw new SQLRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException();
		} finally {
			ConnectionDB.closePreparedStatement(pstm);
			ConnectionDB.closeConnection();
		}
	}
	
}