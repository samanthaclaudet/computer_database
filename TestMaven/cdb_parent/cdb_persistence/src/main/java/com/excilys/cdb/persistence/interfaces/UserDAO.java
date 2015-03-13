package com.excilys.cdb.persistence.interfaces;

import com.excilys.cdb.model.User;

/**
*
* @author sclaudet
*
*/
public interface UserDAO {

	public User loadUserByUsername(String username) ;
	
}
