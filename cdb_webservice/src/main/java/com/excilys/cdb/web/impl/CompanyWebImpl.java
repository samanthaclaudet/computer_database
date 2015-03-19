package com.excilys.cdb.web.impl;

import java.util.List;

import javax.jws.WebService;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.interfaces.CompanyService;
import com.excilys.cdb.web.interfaces.CompanyWeb;
import com.excilys.cdb.web.wrapper.ListWrapper;

@WebService(endpointInterface = "com.excilys.cdb.web.interfaces.CompanyWeb")
public class CompanyWebImpl implements CompanyWeb {

  private CompanyService companyService;

  public CompanyWebImpl(CompanyService companyService) {
    this.companyService = companyService;
  }

  @Override
  public ListWrapper<Company> getAll() {
    List<Company> c = companyService.getAll();
    ListWrapper<Company> cw = new ListWrapper<>();
    cw.setList(c);
    return cw;
  }

  @Override
  public Company getById(int id) {
    return companyService.getById(id);
  }

  @Override
  public void delete(int id) {
    companyService.delete(id);
  }

}
