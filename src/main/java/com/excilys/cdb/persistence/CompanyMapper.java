package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.interfaces.RowMapper;

/**
 * CompanyMapper is used to map the result of a query into :
 * <ul>
 * <li>a Company with toObject(ResultSet r)</li>
 * <li>a list of Companies with toList(ResultSet r), implemented by default</li>
 * </ul>
 * It implements the interface RowMapper
 * 
 * @author sclaudet
 *
 */
public enum CompanyMapper implements RowMapper<Company> {
	INSTANCE;

	/**
	 * @param the
	 *            resultset of the query
	 * @return a company
	 * @throws SQLException
	 */
	@Override
	public Company toObject(ResultSet r) throws SQLException {
		return new Company(r.getInt("id"), r.getString("name"));
	}

}
