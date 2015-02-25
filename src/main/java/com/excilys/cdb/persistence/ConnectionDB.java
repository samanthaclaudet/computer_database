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
	private static ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();
	
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
	
			// we use a pool of connections
			connectionPool = new BoneCP(config);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * Gets a connection
	 * 
	 * @return a connection
	 */
	public static Connection getConnection() {
		try {
			if (threadConnection.get() == null) {
				threadConnection.set(connectionPool.getConnection());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}	
		return threadConnection.get();
	}

	/**
	 * Opens a transaction
	 */
	public static void initTransaction() {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			conn.setAutoCommit(false);
			if (threadConnection.get() == null) {
				threadConnection.set(conn);
			}
			System.out.println("Transaction started");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * Closes a transaction
	 */
	public static void terminateTransaction() {
		try {
			if (threadConnection.get() != null) {
					threadConnection.get().commit();
					threadConnection.get().close();
					threadConnection.set(null);
					System.out.println("Transaction completed");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * Rollsback a connection when a transaction failed
	 * 
	 * @param conn
	 */
	public static void cancelTransaction() {
		try {
			if (threadConnection.get() != null) {
				System.err.print("Transaction is being rolled back");
				threadConnection.get().rollback();
				threadConnection.get().close();
				threadConnection.set(null);
			}
		} catch(SQLException e) {
		      	logger.error(e.getMessage());
		       	e.printStackTrace();
		       	throw new RuntimeException();
		}
	}
	
	/**
	 * Closes a Connection (sends it back to the pool)
	 * Commits it first if it's a transaction
	 * 
	 * @param isTransaction
	 */
	public static void closeConnection() {
		try {
			if (threadConnection.get() != null && threadConnection.get().getAutoCommit()) {
				threadConnection.get().close();
				threadConnection.set(null);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * Closes a ResultSet
	 *
	 * @param rs
	 *            the ResultSet you want to close
	 */
	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * Closes a PreparedStatement
	 * 
	 * @param pstm
	 *            the PreparedStatement you want to close
	 */
	public static void closePreparedStatement(PreparedStatement pstm) {
		try {
			if (pstm != null) {
				pstm.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
}
