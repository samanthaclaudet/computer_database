package com.excilys.cdb.web.interfaces;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@WebService
@SOAPBinding(style = Style.RPC)
public interface ComputerWeb {

	@WebMethod String getHelloWorldAsString(String name);

	@WebMethod int getNbComputers();

	@WebMethod List<Computer> getAll();

	@WebMethod Computer getById(int id);

	@WebMethod Page getPage(String name, int idx, int size, String orderBy);

	@WebMethod void set(Computer c);

	@WebMethod void update(Computer c);

	@WebMethod void delete(int id);
	
	
}
