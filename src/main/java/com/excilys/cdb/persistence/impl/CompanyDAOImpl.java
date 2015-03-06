package com.excilys.cdb.persistence.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exceptions.SQLRuntimeException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.interfaces.CompanyDAO;

/**
 * CompanyDAO makes the connection between the database and the Company object
 * The available methods are :
 * <ul>
 * <li>getAll()</li>
 * <li>getById(int id)</li>
 * <li>delete(int id, Connection conn)</li>
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
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public CompanyDAOImpl() {
	}

	/**
	 * Gets a list of all companies
	 * 
	 * @return the list of all companies in the database
	 */
	@Transactional
	@Override
	public List<Company> getAll() {
	    return sessionFactory.getCurrentSession().createCriteria(Company.class).list();
	}

	/**
	 * Gets a Company by its id Used to instantiate the Company in a Computer
	 * 
	 * @param id
	 * @return a Company whose id was passed as parameter
	 */
    @Transactional
    @Override
	public Company getById(int id) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(Company.class);
		return (Company) cr.add(Restrictions.eq("id", id)).uniqueResult();
	}
	
	/**
	 * Deletes a company from the database at the id passed in parameter
	 * 
	 * @param id
	 *            the id of the company you want to delete
	 */
    @Override
	public void delete(int id) throws SQLRuntimeException{
        try {
            sessionFactory.getCurrentSession().delete(this.getById(id));
        } catch (DataAccessException e) {
            throw new SQLRuntimeException();
        }
	}

}
