package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.util.List;

public interface RowMapper <T> {
	public List<T> toList (ResultSet r);
	public T toObject (ResultSet r);
}
