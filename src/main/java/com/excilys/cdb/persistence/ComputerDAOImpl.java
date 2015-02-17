package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.Util;

/**
 * ComputerDAO makes the connection between the database and the Computer object
 * This is a singleton
 *  * The available methods are :
 * <ul>
 * <li> getAll()</li>
 * <li> getById(int id)</li>
 * <li> set(Computer c)</li>
 * <li> update(int id, Computer c)</li>
 * <li> delete(int id)</li>
 * </ul>
 * @see Computer
 * 
 * @author sclaudet
 *
 */
public enum ComputerDAOImpl implements ComputerDAO {
	INSTANCE;
	
	private ComputerDAOImpl() {}
	
	/**
	 * Counts the number of computers in the database
	 * 
	 * @return total number of computers in the database (int)
	 */
	public int getNbComputers() {
		int nb = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			conn = ConnectionDB.getConnection();
			pstm = conn.prepareStatement("SELECT COUNT(*) FROM computer");
			r = pstm.executeQuery();
			if (r.next()) {
				nb = r.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, pstm, r);
		}
		return nb;
	}
	
	/**
	 * Gets a list of computers pageable
	 * 
	 * @param idx 
	 * 			offest for the select query
	 * @param size
	 * 			number of computers selected
	 * @return a list of limited size of computers
	 */
	public List<Computer> getPerPage(int idx, int size){
		List<Computer> lc = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			conn = ConnectionDB.getConnection();
			pstm = conn.prepareStatement("SELECT * FROM computer ORDER BY id LIMIT ?, ?");
			pstm.setInt(1, idx*size);
			pstm.setInt(2, size);
			r = pstm.executeQuery();
			// mapping the ResultSet into a list of computers
			lc = ComputerMapper.INSTANCE.toList(r);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, pstm, r);
		}
		return lc;
	}
	
	/**
	 * Gets a list of all computers
	 * 
	 * @return the list of all computers in the database
	 */
	public List<Computer> getAll(){
		List<Computer> lc = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			conn = ConnectionDB.getConnection();
			pstm = conn.prepareStatement("SELECT * FROM computer");
			r = pstm.executeQuery();
			// mapping the ResultSet into a list of computers
			lc = ComputerMapper.INSTANCE.toList(r);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, pstm, r);
		}
		return lc;
	}
	
	/**
	 * Gets a Computer by its id
	 * 
	 * @param id
	 * @return a Computer whose id was passed as parameter
	 */
	public Computer getById(int id){
		Computer c = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet r = null;
		try {
			conn = ConnectionDB.getConnection();
			pstm = conn.prepareStatement("SELECT * FROM computer WHERE id = ?");
			pstm.setInt(1, id);
			r = pstm.executeQuery();
			// mapping the ResultSet into a computer
			c = ComputerMapper.INSTANCE.toObject(r);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, pstm, r);
		}
		return c;
	}
	
	/**
	 * Inserts the computer passed in parameter into the database
	 * 
	 * @param c
	 * 			the computer you want to add in the database
	 */
	public void set(Computer c){
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConnectionDB.getConnection();
			pstm = conn.prepareStatement("INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)");
			pstm.setString(1, c.getName());
			// the date of introduction can be null
			if (c.getIntroduced()!=null) {
				// converts LocalDateTime into a Timestamp
				pstm.setTimestamp(2, Timestamp.valueOf(c.getIntroduced()));
			} else {
				pstm.setNull(2, java.sql.Types.TIMESTAMP);
			}
			// the date of discontinuation can be null
			if (c.getDiscontinued()!=null) {
				// converts LocalDateTime into a Timestamp
				pstm.setTimestamp(3, Timestamp.valueOf(c.getDiscontinued()));
			} else {
				pstm.setNull(3, java.sql.Types.TIMESTAMP);
			}
			// the company can be null
			if (c.getCompany()!=null) {
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
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, pstm, null);
		}
	}
	
	/**
	 * Replaces the computer in the database at the id passed in parameter by the computer passed in parameter
	 * 
	 * @param id
	 * 			the id of the computer you want to modify in the database
	 * @param c
	 * 			the new computer that will replace the one in the database
	 */
	public void update(int id, Computer c){
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConnectionDB.getConnection();
			pstm = conn.prepareStatement("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?");
			pstm.setString(1, c.getName());
			// the date of introduction can be null
			if (c.getIntroduced()!=null) {
				// converts LocalDateTime into a Timestamp
				pstm.setTimestamp(2, Timestamp.valueOf(c.getIntroduced()));
			} else {
				pstm.setNull(2, java.sql.Types.TIMESTAMP);
			}
			// the date of discontinuation can be null
			if (c.getDiscontinued()!=null) {
				// converts LocalDateTime into a Timestamp
				pstm.setTimestamp(3, Timestamp.valueOf(c.getDiscontinued()));
			} else {
				pstm.setNull(3, java.sql.Types.TIMESTAMP);
			}
			// the company can be null
			if (c.getCompany()!=null) {
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
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, pstm, null);
		}
	}
	
	/**
	 * Deletes a computer from the database at the id passed in parameter
	 * 
	 * @param id
	 * 			the id of the computer you want to delete
	 */
	public void delete(int id) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConnectionDB.getConnection();
			pstm = conn.prepareStatement("DELETE FROM computer WHERE id="+id);
			int queryExecuted = pstm.executeUpdate();
			// displays "success" or "failure"
			Util.checkSuccess(queryExecuted);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			ConnectionDB.closeConnection(conn, pstm, null);
		}
	}
	
}