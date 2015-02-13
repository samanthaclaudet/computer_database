package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;

/**
 * CompanyMapper is used to map the result of a query into :
 * <ul>
 * <li>a Company with toObject(ResultSet r)</li>
 * <li>a list of Companies with toList(ResultSet r)</li>
 * </ul>
 * It implements the interface RowMapper
 * 
 * @author sclaudet
 *
 */
public enum CompanyMapper implements RowMapper<Company>{
	INSTANCE;
	
	/**
	 * @param the resultset of the query
	 * @return a list of all companies
	 */
	@Override
	public List<Company> toList(ResultSet r) {
		List<Company> companyList = new ArrayList<Company>();

		try {
			while (r.next()) {
				Company c = new Company(r.getInt("id"), r.getString("name"));
				companyList.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return companyList;
	}

	/**
	 * @param the resultset of the query
	 * @return a company
	 */
	@Override
	public Company toObject(ResultSet r) {
		Company c = null;
		try {
			if (r.next()) {
				c = new Company(r.getInt("id"), r.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return c;
	}

}
