package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * ComputerMapper is used to map the result of a query into :
 * <ul>
 * <li>a Computer with toObject(ResultSet r)</li>
 * <li>a list of Computers with toList(ResultSet r)</li>
 * </ul>
 * It implements the interface RowMapper
 * 
 * @author sclaudet
 *
 */
public enum ComputerMapper implements RowMapper<Computer>{
	INSTANCE;
	
	/**
	 * @param the resultset of the query
	 * @return a list of all computers
	 */
	@Override
	public List<Computer> toList(ResultSet r) {
		List<Computer> computerList = new ArrayList<Computer>();
		try {
			while (r.next()) {
				Company company = null;
				if (r.getInt("company_id")!=0) {
					company = CompanyDAOImpl.INSTANCE.getById(r.getInt("company_id"));
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
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return computerList;
	}

	/**
	 * @param the resultset of the query
	 * @return a computer
	 */
	@Override
	public Computer toObject(ResultSet r) {
		Company company = null;
		Computer c = null;
		
		try {
			if (r.next()) {
				if (r.getInt("company_id")!=0) {
					company = CompanyDAOImpl.INSTANCE.getById(r.getInt("company_id"));
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
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return c;
	}

}
