package com.excilys.cdb.web.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.web.wrapper.ListWrapper;

@WebService
@SOAPBinding(style = Style.RPC)
public interface CompanyWeb {

  @WebMethod
  ListWrapper<Company> getAll();

  @WebMethod
  Company getById(int id);

  @WebMethod
  void delete(int id);

}
