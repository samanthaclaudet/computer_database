package com.excilys.cdb.persistence.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.User;
import com.excilys.cdb.persistence.interfaces.UserDAO;

@Repository
public class UserDAOImpl implements UserDAO {

	 @Autowired
	 private SessionFactory sessionFactory;
	
	 @Transactional
	 @Override
	 public User loadUserByUsername(String username){
			Criteria cr = sessionFactory.getCurrentSession().createCriteria(User.class);
			return (User) cr.add(Restrictions.eq("username", username)).uniqueResult();
	 }
	 
}
