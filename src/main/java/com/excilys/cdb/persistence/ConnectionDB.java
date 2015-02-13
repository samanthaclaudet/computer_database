package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.utilsdb.DatabaseName;

/**
 * ConnectionDB is used to get a connection with the database and to close it when the query is done
 * 
 * @author sclaudet
 *
 */
public enum ConnectionDB {
	INSTANCE;
	
	// change database depending on if run program or run tests
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/"+DatabaseName.INSTANCE.getDatabaseName()+"?zeroDateTimeBehavior=convertToNull";
	private static final String USER = "admincdb";
	private static final String PASSWD = "qwerty1234";

	/**
	 * This constructor is called only once
	 */
	private ConnectionDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * Gives a connection
	 * url, user and password are defined as constants
	 * 
	 * @return a connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWD);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return conn;
	}
	
	/**
	 * Closes all elements used for the query
	 * 
	 * @param conn
	 * 			the connection you want to close
	 * @param pstm
	 * 			the preparedStatement you want to close
	 * @param rs
	 * 			the resultset you want to close
	 */
	public static void closeConnection(Connection conn, PreparedStatement pstm, ResultSet rs) {
		try {
			if (conn!=null) {
				conn.close();
			}
			if (pstm!=null) {
				pstm.close();
			}
			if (rs!=null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
}
