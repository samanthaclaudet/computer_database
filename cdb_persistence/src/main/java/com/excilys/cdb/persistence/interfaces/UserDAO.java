package com.excilys.cdb.persistence.interfaces;

import com.excilys.cdb.model.User;

/**
*
* @author sclaudet
*
*/
public interface UserDAO {

	/**
	 * Gets a User by its username
	 * 
	 * @param username
	 * @return a User
	 */
	public User loadUserByUsername(String username) ;
	
}
