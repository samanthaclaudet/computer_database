package com.excilys.cdb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.interfaces.ComputerDAO;
import com.excilys.cdb.service.interfaces.ComputerService;

/**
 * @see ComputerDAOImpl
 * 
 * @author sclaudet
 *
 */
@Service
public class ComputerServiceImpl implements ComputerService {

  @Autowired
  ComputerDAO computerDAO;

  /**
   * @see com.excilys.cdb.service.interfaces.ComputerService#getNbComputers()
   */
  @Override
  @Transactional(readOnly = true)
  public int getNbComputers() {
    return computerDAO.getNbComputers("");
  }

  /**
   * @see com.excilys.cdb.service.interfaces.ComputerService#getAll()
   */
  @Override
  @Transactional(readOnly = true)
  public List<Computer> getAll() {
    return computerDAO.getAll();
  }

  /**
   * @see com.excilys.cdb.service.interfaces.ComputerService#getById(int)
   */
  @Override
  @Transactional(readOnly = true)
  public Computer getById(int id) {
    return computerDAO.getById(id);
  }

  /**
   * @see com.excilys.cdb.service.interfaces.ComputerService#getPage(String, int, int, String)
   */
  @Override
  @Transactional(readOnly = true)
  public Page getPage(String name, int idx, int size, String orderBy) {
    Page p = computerDAO.getPage(name, idx, size, orderBy);
    int nb = computerDAO.getNbComputers(name);
    p.setNbComputers(nb);
    return p;
  }

  /**
   * @see com.excilys.cdb.service.interfaces.ComputerService#set(Computer)
   */
  @Override
  @Transactional
  public void set(Computer c) {
    computerDAO.set(c);
  }

  /**
   * @see com.excilys.cdb.service.interfaces.ComputerService#update(Computer)
   */
  @Override
  @Transactional
  public void update(Computer c) {
    computerDAO.update(c);
  }

  /**
   * @see com.excilys.cdb.service.interfaces.ComputerService#delete(int)
   */
  @Override
  @Transactional
  public void delete(int id) {
    computerDAO.delete(id);
  }

}
