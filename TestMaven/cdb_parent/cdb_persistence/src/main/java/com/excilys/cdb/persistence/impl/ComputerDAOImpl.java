package com.excilys.cdb.persistence.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exceptions.SQLRuntimeException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.interfaces.ComputerDAO;

/**
 * ComputerDAO makes the connection between the database and the Computer object
 * The available methods are :
 * <ul>
 * <li>getAll()</li>
 * <li>getById(int id)</li>
 * <li>getPage(String name, int idx, int size)</li>
 * <li>getNbComputers()</li>
 * <li>set(Computer c)</li>
 * <li>update(int id, Computer c)</li>
 * <li>delete(int id)</li>
 * <li>delete(int id, Connection conn)</li>
 * </ul>
 * 
 * @see Computer
 * 
 * @author sclaudet
 *
 */
@Repository
public class ComputerDAOImpl implements ComputerDAO {
  	
    @Autowired
    private SessionFactory sessionFactory;
	
	public ComputerDAOImpl() {
	}

	/**
	 * Counts the number of computers in the database
	 * 
	 * @return total number of computers in the database (int)
	 */
	@Transactional
	@Override
	public int getNbComputers(String name) {
	  name = "%" + name + "%";
	  Criterion computerName = Restrictions.like("computer.name", name);
	  Criterion companyName = Restrictions.like("company.name", name);
	  LogicalExpression orExp = Restrictions.or(computerName, companyName);

	  Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Computer.class, "computer");
	  criteria.createCriteria("company", "company", JoinType.LEFT_OUTER_JOIN)
	  .add(orExp)
	  .setProjection(Projections.rowCount());
	  int nb = (new Long((Long) criteria.uniqueResult())).intValue();
	  return nb;
	}
	
	/**
	 * Gets a list of all computers
	 * 
	 * @return the list of all computers in the database
	 */
	@Transactional
	@Override
	public List<Computer> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(Computer.class).list();
	}

	/**
	 * Gets a Computer by its id
	 * 
	 * @param id
	 * @return a Computer whose id was passed as parameter
	 */
	@Transactional
	@Override
	public Computer getById(int id) {
	    Criteria cr = sessionFactory.getCurrentSession().createCriteria(Computer.class);
        return (Computer) cr.add(Restrictions.eq("id", id)).uniqueResult();
	}

	/**
	 * Gets a Page, can look for Computers by their name or the name of their company
	 * 
	 * @param name
	 * 			  name of the computer or the company you are looking for
	 * @param idx
	 *            offest for the select query
	 * @param size
	 *            number of computers per page
	 * @param orderBy
	 * 			  order by feature
	 * @return a Page with all computers containing the name
	 */
    @Transactional
    @Override
	public Page getPage(String name, int idx, int size, String orderBy) {
		switch (orderBy) {
		case "computerName" :
			orderBy = "computer.name";
			break;
		case "introduced" :
			orderBy = "computer.introduced";
			break;
		case "discontinued" :
			orderBy = "computer.discontinued";
			break;
		case "company" :
			orderBy = "company.name";
			break;
		default :
			orderBy = "computer.id";
			break;
		}

        name = "%" + name + "%";
        Criterion computerName = Restrictions.like("computer.name", name);
        Criterion companyName = Restrictions.like("company.name", name);
        LogicalExpression orExp = Restrictions.or(computerName, companyName);
  
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Computer.class, "computer");
        List<Computer> lc = criteria.createCriteria("company", "company", JoinType.LEFT_OUTER_JOIN)
        .add(orExp)
        .addOrder(Order.asc(orderBy))
        .setFirstResult(idx*size)
        .setMaxResults(size)
        .list();
        
        Page p = new Page(size, 0, idx);
        p.setComputers(lc);
        return p;
      
      
	}
	
	/**
	 * Inserts the computer passed in parameter into the database
	 * 
	 * @param c
	 *            the computer you want to add in the database
	 */
    @Transactional
    @Override
    public void set(Computer c) {
        sessionFactory.getCurrentSession().save(c); 
	}

	/**
	 * Replaces the computer in the database at the id passed in parameter by
	 * the computer passed in parameter
	 * 
	 * @param id
	 *            the id of the computer you want to modify in the database
	 * @param c
	 *            the new computer that will replace the one in the database
	 */
    @Transactional
    @Override
    public void update(int id, Computer c) {
        sessionFactory.getCurrentSession().update(c);
      
	}

	/**
	 * Deletes a computer from the database at the id passed in parameter
	 * 
	 * @param id
	 *            the id of the computer you want to delete
	 */
	@Transactional
	@Override
	public void delete(int id) {
        sessionFactory.getCurrentSession().delete(this.getById(id));
	}

	/**
	 * Deletes a computer from the database whose company has the id passed in parameter
	 * 
	 * @param id
	 *            the id of the company you want to delete
	 */
	@Override
	public void deleteFromCompany(int id) throws SQLRuntimeException{
	   try {
	       Criteria cr = sessionFactory.getCurrentSession().createCriteria(Computer.class);
	       List<Computer> computersToDelete = cr.add(Restrictions.eq("company_id", id)).list();
	       for (Computer c : computersToDelete) {
	         sessionFactory.getCurrentSession().delete(c);
	       }
       } catch (DataAccessException e) {
           throw new SQLRuntimeException();
       }
	}
	
}