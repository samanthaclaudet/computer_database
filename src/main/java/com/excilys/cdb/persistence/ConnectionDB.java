package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.utilsdb.DatabaseProperties;

/**
 * ConnectionDB is used to get a connection with the database and to close it
 * when the query is done
 * 
 * @author sclaudet
 *
 */
public enum ConnectionDB {
	INSTANCE;

	private static final Logger logger = LoggerFactory
			.getLogger(ConnectionDB.class);

	/**
	 * This constructor is called only once
	 */
	private ConnectionDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// TODO
			// logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * Gives a connection url, user and password are defined as constants
	 * 
	 * @return a connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// change database depending on if run program or run tests
			conn = DriverManager.getConnection(
					DatabaseProperties.INSTANCE.getDatabaseUrl(),
					DatabaseProperties.INSTANCE.getDatabaseUser(),
					DatabaseProperties.INSTANCE.getDatabasePassword());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
		return conn;
	}

	/**
	 * Closes all elements used for the query
	 * 
	 * @param conn
	 *            the connection you want to close
	 * @param pstm
	 *            the preparedStatement you want to close
	 * @param rs
	 *            the resultset you want to close
	 */
	public static void closeConnection(Connection conn, PreparedStatement pstm,
			ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstm != null) {
				pstm.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
