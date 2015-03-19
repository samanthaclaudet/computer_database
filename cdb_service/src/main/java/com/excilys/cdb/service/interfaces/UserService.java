package com.excilys.cdb.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
*
* @author sclaudet
*
*/
public interface UserService extends UserDetailsService {

  /**
   * Gets a User by its username
   * 
   * @param username
   * @return a User
   */
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException;

}
