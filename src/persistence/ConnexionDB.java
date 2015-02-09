package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionDB {

	private ResultSet results;
	private static ConnexionDB connDB;
	private static Connection conn;
	private static Statement stmt;

	private ConnexionDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
			String user = "admincdb";
			String passwd = "qwerty1234";

			conn = DriverManager.getConnection(url, user, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ConnexionDB getInstance() {
		if (connDB == null) {
			connDB = new ConnexionDB();
		} 
		return connDB;
	}
	
	public void executeQuerySelect(String query) {
		try {
			stmt = conn.createStatement();
			results = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void executeQueryAction(String query) {
		try {
			stmt = conn.createStatement();
			int queryExecuted = stmt.executeUpdate(query);
			System.out.print("Requete executee ? ");
			if (queryExecuted == 1) {
				System.out.println(" OK");
			} else {
				System.out.println(" echec");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getResults() {
		return results;
	}
	
	public static void closeConnexion() throws SQLException {
		//results.close();
		if (connDB != null) {
			stmt.close();
			conn.close();
		}
	}
	
}
