package com.excilys.cdb.persistence.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface RowMapper<T> {
	public default List<T> toList(ResultSet r) throws SQLException {
		List<T> objectList = new ArrayList<T>();
		while (r.next()) {
			T t = toObject(r);
			objectList.add(t);
		}
		return objectList;
	}

	public T toObject(ResultSet r) throws SQLException;
}
