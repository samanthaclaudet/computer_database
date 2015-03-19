package com.excilys.cdb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exceptions.SQLRuntimeException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.interfaces.CompanyDAO;
import com.excilys.cdb.persistence.interfaces.ComputerDAO;
import com.excilys.cdb.service.interfaces.CompanyService;

/**
 * @see CompanyDAOImpl
 * 
 * @author sclaudet
 *
 */
@Service
public class CompanyServiceImpl implements CompanyService {

  @Autowired
  private CompanyDAO  companyDAO;

  @Autowired
  private ComputerDAO computerDAO;

  /**
   * @see com.excilys.cdb.service.interfaces.CompanyService#getAll()
   */
  @Override
  @Transactional(readOnly = true)
  public List<Company> getAll() {
    return companyDAO.getAll();
  }

  /**
   * @see com.excilys.cdb.service.interfaces.CompanyService#getById(int)
   */
  @Override
  @Transactional(readOnly = true)
  public Company getById(int id) {
    return companyDAO.getById(id);
  }

  /**
   * @see com.excilys.cdb.service.interfaces.CompanyService#delete(int)
   */
  @Override
  @Transactional(rollbackFor = SQLRuntimeException.class)
  public void delete(int id) {
    computerDAO.deleteFromCompany(id); // deletes all the computer with that company first
    companyDAO.delete(id); // deletes the company
  }

}
