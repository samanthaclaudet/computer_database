package com.excilys.cdb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.User;
import com.excilys.cdb.persistence.interfaces.UserDAO;
import com.excilys.cdb.service.interfaces.UserService;

/**
 * @see UserDAOImpl
 * 
 * @author sclaudet
 *
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDAO userDAO;

  /**
   * @see com.excilys.cdb.service.interfaces.UserService#loadUserByUsername(String)
   */
  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDAO.loadUserByUsername(username);
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    authorities.add(new SimpleGrantedAuthority(user.getRole()));
    if (user.getRole().equals("ROLE_ADMIN")) {
      // admin is also user
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), true, true, true, true, authorities);

  }

}
