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
 * using Criteria
 * The available methods are :
 * <ul>
 * <li>getAll()</li>
 * <li>getById(int id)</li>
 * <li>getPage(String name, int idx, int size, String orderBy)</li>
 * <li>getNbComputers(String name)</li>
 * <li>set(Computer c)</li>
 * <li>update(Computer c)</li>
 * <li>delete(int id)</li>
 * <li>deleteFromCompany(int id)</li>
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
    
	 /**
	  * @see com.excilys.cdb.persistence.interfaces.ComputerDAO#getNbComputers(String)
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
	  * @see com.excilys.cdb.persistence.interfaces.ComputerDAO#getAll()
	  */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Computer> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(Computer.class).list();
	}

	 /**
	  * @see com.excilys.cdb.persistence.interfaces.ComputerDAO#getById(int)
	  */
	@Transactional
	@Override
	public Computer getById(int id) {
	    Criteria cr = sessionFactory.getCurrentSession().createCriteria(Computer.class);
        return (Computer) cr.add(Restrictions.eq("id", id)).uniqueResult();
	}

	 /**
	  * @see com.excilys.cdb.persistence.interfaces.ComputerDAO#getPage(String, int, int, String)
	  */
    @SuppressWarnings("unchecked")
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
	  * @see com.excilys.cdb.persistence.interfaces.ComputerDAO#set(Computer)
	  */
    @Transactional
    @Override
    public void set(Computer c) {
        sessionFactory.getCurrentSession().save(c); 
	}

	 /**
	  * @see com.excilys.cdb.persistence.interfaces.ComputerDAO#update(Computer)
	  */
    @Transactional
    @Override
    public void update(Computer c) {
        sessionFactory.getCurrentSession().update(c);
      
	}

	 /**
	  * @see com.excilys.cdb.persistence.interfaces.ComputerDAO#delete(int)
	  */
	@Transactional
	@Override
	public void delete(int id) {
        sessionFactory.getCurrentSession().delete(this.getById(id));
	}

	 /**
	  * @see com.excilys.cdb.persistence.interfaces.ComputerDAO#deleteFromCompany(int)
	  */
	@SuppressWarnings("unchecked")
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