package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.interfaces.RowMapper;

/**
 * ComputerMapper is used to map the result of a query into :
 * <ul>
 * <li>a Computer with toObject(ResultSet r)</li>
 * <li>a list of Computers with toList(ResultSet r), implemented by default</li>
 * </ul>
 * It implements the interface RowMapper
 * 
 * @author sclaudet
 *
 */
public enum ComputerMapper implements RowMapper<Computer> {
	INSTANCE;

	/**
	 * @param the
	 *            resultset of the query
	 * @return a computer
	 */
	@Override
	public Computer toObject(ResultSet r) throws SQLException {
		Company company = null;
		Computer c = null;

		if (r.getInt("company_id") != 0) {
			company = new Company(r.getInt("company.id"), r.getString("company.name"));
		}
		LocalDateTime ldti = null, ldtd = null;
		if (r.getTimestamp("introduced") != null) {
			ldti = r.getTimestamp("introduced").toLocalDateTime();
		}
		if (r.getTimestamp("discontinued") != null) {
			ldtd = r.getTimestamp("discontinued").toLocalDateTime();
		}
		c = new Computer(r.getInt("id"), r.getString("name"), ldti, ldtd,
				company);

		return c;
	}

}
