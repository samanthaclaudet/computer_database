package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Company;

public class CompanyDAO {
	
	public CompanyDAO() {}
	
	public ArrayList<Company> getCompaniesList() throws SQLException {
		ArrayList<Company> companyList = new ArrayList<Company>();
		
		ConnexionDB conDB = ConnexionDB.getInstance();
		conDB.executeQuerySelect("SELECT * FROM company");
		ResultSet r = conDB.getResults();

		while (r.next()) {
			Company c = new Company(r.getInt("id"), r.getString("name"));
			companyList.add(c);
		}
		return companyList;
	}
	
	public Company getACompany(int id) throws SQLException {
		ConnexionDB conDB = ConnexionDB.getInstance();
		conDB.executeQuerySelect("SELECT * FROM company WHERE id="+id);
		ResultSet r = conDB.getResults();
		Company c = null;
		if (r.next()) {
			c = new Company(r.getInt("id"), r.getString("name"));
		}
		return c;
	}
	
}
