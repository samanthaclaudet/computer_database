package com.excilys.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User is composed of :
 * <ul>
 * <li>a username</li>
 * <li>a password</li>
 * <li>a role (ROLE_ADMIN or ROLE_USER)</li>
 * </ul>
 * 
 * @author sclaudet
 *
 */
@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;

  /**
   * 
   * @return a username
   */
  public String getUsername() {
    return username;
  }

  /**
   * 
   * @param username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * 
   * @return a password
   */
  public String getPassword() {
    return password;
  }

  /**
   * 
   * @param password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * 
   * @return a role
   */
  public String getRole() {
    return role;
  }

  /**
   * 
   * @param role
   */
  public void setRole(String role) {
    this.role = role;
  }

}
