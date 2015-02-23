package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

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

	private static BoneCP connectionPool = null;
	
	private static final Logger logger = LoggerFactory
			.getLogger(ConnectionDB.class);

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// change database depending on if run program or run tests
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(DatabaseProperties.INSTANCE.getDatabaseUrl());
			config.setUsername(DatabaseProperties.INSTANCE.getDatabaseUser());
			config.setPassword(DatabaseProperties.INSTANCE.getDatabasePassword());
				
			config.setMinConnectionsPerPartition(DatabaseProperties.INSTANCE.getBoneCPMin());
			config.setMaxConnectionsPerPartition(DatabaseProperties.INSTANCE.getBoneCPMax());
			config.setPartitionCount(DatabaseProperties.INSTANCE.getBoneCPPartitions());
	
			connectionPool = new BoneCP(config);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
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
			conn = connectionPool.getConnection();
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
