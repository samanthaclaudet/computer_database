package com.excilys.cdb.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerMapperSpring implements RowMapper<Computer>{

	@Override
	public Computer mapRow(ResultSet r, int arg1) throws SQLException {
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
