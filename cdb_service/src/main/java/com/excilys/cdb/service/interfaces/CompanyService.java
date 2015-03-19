package com.excilys.cdb.service.interfaces;

import java.util.List;

import com.excilys.cdb.model.Company;

/**
 *
 * @author sclaudet
 *
 */
public interface CompanyService {

  /**
   * Gets a list of all companies
   * 
   * @return the list of all companies in the database
   */
  public List<Company> getAll();

  /**
   * Gets a Company by its id Used to instantiate the Company in a Computer
   * 
   * @param id
   * @return a Company whose id was passed as parameter
   */
  public Company getById(int id);

  /**
   * Deletes a company from the database at the id passed in parameter and all
   * the related computers in a transaction
   * 
   * @param id
   *            the id of the company you want to delete
   */
  public void delete(int id);
}
