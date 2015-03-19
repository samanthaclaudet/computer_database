package com.excilys.cdb.web.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.web.wrapper.ListWrapper;

@WebService
@SOAPBinding(style = Style.RPC)
public interface ComputerWeb {

  @WebMethod
  String getHelloWorldAsString(String name);

  @WebMethod
  int getNbComputers();

  @WebMethod
  ListWrapper<ComputerDTO> getAll();

  @WebMethod
  ComputerDTO getById(int id);

  @WebMethod
  Page getPage(String name, int idx, int size, String orderBy);

  @WebMethod
  void set(ComputerDTO c);

  @WebMethod
  void update(ComputerDTO c);

  @WebMethod
  void delete(int id);

}
