package com.excilys.cdb.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
*
* @author sclaudet
*
*/
public interface UserService {

	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException ;
	
}
