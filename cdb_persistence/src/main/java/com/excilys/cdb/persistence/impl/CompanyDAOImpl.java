package com.excilys.cdb.persistence.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exceptions.SQLRuntimeException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.interfaces.CompanyDAO;

/**
 * CompanyDAO makes the connection between the database and the Company object
 * using Criteria
 * The available methods are :
 * <ul>
 * <li>getAll()</li>
 * <li>getById(int id)</li>
 * <li>delete(int id)</li>
 * </ul>
 * 
 * @see Company
 * 
 * @author sclaudet
 *
 */
@Repository
public class CompanyDAOImpl implements CompanyDAO {

  @Autowired
  private SessionFactory sessionFactory;

  /**
   * @see com.excilys.cdb.persistence.interfaces.CompanyDAO#getAll()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Company> getAll() {
    return sessionFactory.getCurrentSession().createCriteria(Company.class).list();
  }

  /**
   * @see com.excilys.cdb.persistence.interfaces.CompanyDAO#getById(int)
   */
  @Override
  public Company getById(int id) {
    Criteria cr = sessionFactory.getCurrentSession().createCriteria(Company.class);
    return (Company) cr.add(Restrictions.eq("id", id)).uniqueResult();
  }

  /**
   * @see com.excilys.cdb.persistence.interfaces.CompanyDAO#delete(int)
   */
  @Override
  public void delete(int id) throws SQLRuntimeException {
    try {
      sessionFactory.getCurrentSession().delete(this.getById(id));
    } catch (DataAccessException e) {
      throw new SQLRuntimeException();
    }
  }

}
