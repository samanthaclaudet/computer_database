package com.excilys.cdb.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;

@Component
public class CompanyMapperSpring implements RowMapper<Company>{

	@Override
	public Company mapRow(ResultSet r, int arg1) throws SQLException {
		Company c = null;
		c=  new Company(r.getInt("id"), r.getString("name"));
		return c;
	}

}
