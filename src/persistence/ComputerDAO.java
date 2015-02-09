package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.Company;
import model.Computer;

public class ComputerDAO {

	public ComputerDAO() {}
	
	public ArrayList<Computer> getComputersList() throws SQLException {
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		
		ConnexionDB conDB = ConnexionDB.getInstance();
		conDB.executeQuerySelect("SELECT * FROM computer");
		ResultSet r = conDB.getResults();
		
		while (r.next()) {
			Company company = null;
			if (r.getInt("company_id")!=0) {
				CompanyDAO cDAO = new CompanyDAO();
				company = cDAO.getACompany(r.getInt("company_id"));
			}
			
			LocalDateTime ldti = null, ldtd = null;
			if (r.getTimestamp("introduced")!=null) {
				ldti = r.getTimestamp("introduced").toLocalDateTime();
			}
			if (r.getTimestamp("discontinued")!=null) {
				ldtd = r.getTimestamp("discontinued").toLocalDateTime();
			}
				
			Computer c = new Computer(r.getInt("id"), r.getString("name"), ldti, ldtd, company);
			computerList.add(c);
		}
		return computerList;
	}
	
	public Computer getAComputer(int id) throws SQLException {
		ConnexionDB conDB = ConnexionDB.getInstance();
		conDB.executeQuerySelect("SELECT * FROM computer WHERE id="+id);

		ResultSet r = conDB.getResults();
		
		Company company = null;
		Computer c = null;
		if (r.next()) {
			if (r.getInt("company_id")!=0) {
				CompanyDAO cDAO = new CompanyDAO();
				company = cDAO.getACompany(r.getInt("company_id"));
			}
			LocalDateTime ldti = null, ldtd = null;
			if (r.getTimestamp("introduced")!=null) {
				ldti = r.getTimestamp("introduced").toLocalDateTime();
			}
				if (r.getTimestamp("discontinued")!=null) {
					ldtd = r.getTimestamp("discontinued").toLocalDateTime();
			}
			c = new Computer(r.getInt("id"), r.getString("name"), ldti, ldtd, company);
		}
		return c;
	}
	
	public void setAComputer(Computer c) throws SQLException {
		
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES('"+c.getName()+"', ";
		if (c.getDate_introduced()==null) {
			query += null + ", ";
		} else {
			query +="'"+ c.getDate_introduced()+"', ";
		}
		if (c.getDate_discontinued()==null) {
			query += null + ", ";
		} else {
			query +="'"+ c.getDate_discontinued()+"', ";
		}
		if (c.getManufacturer()==null) {
			query += null + ")";
		} else {
			query += c.getManufacturer().getId()+")"; 
		}
		
		System.out.println(query);
		ConnexionDB conDB = ConnexionDB.getInstance();
		conDB.executeQueryAction(query);
	}
	
	public void setAComputer(int id, Computer c) throws SQLException {
		
		String query = "UPDATE computer SET name = '"+ c.getName() +"', introduced = ";
		
		if (c.getDate_introduced()==null) {
			query += null + ", discontinued = ";
		} else {
			query +="'"+ c.getDate_introduced()+"', discontinued = ";
		}
		if (c.getDate_discontinued()==null) {
			query += null + ", company_id = ";
		} else {
			query +="'"+ c.getDate_discontinued()+"', company_id = ";
		}
		if (c.getManufacturer()==null) {
			query += null + " WHERE id = "+id;
		} else {
			query += c.getManufacturer().getId()+" WHERE id = "+id;
		}
		
		System.out.println(query);
		ConnexionDB conDB = ConnexionDB.getInstance();
		conDB.executeQueryAction(query);
	}
	
}
