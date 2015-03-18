package com.excilys.cdb.web.impl;

import java.util.List;

import javax.jws.WebService;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.impl.ComputerServiceImpl;
import com.excilys.cdb.web.interfaces.ComputerWeb;
import com.excilys.cdb.web.wrapper.ListWrapper;

@WebService(endpointInterface = "com.excilys.cdb.web.interfaces.ComputerWeb")
public class ComputerWebImpl implements ComputerWeb {

	private ComputerServiceImpl computerServiceImpl;
	
	public ComputerWebImpl(ComputerServiceImpl computerServiceImpl) {
		this.computerServiceImpl = computerServiceImpl;
	}
	
	@Override
	public String getHelloWorldAsString(String name) {
		return "Hello World JAX-WS " + name;
	}

	@Override
	public int getNbComputers() {
		return computerServiceImpl.getNbComputers();
	}

	@Override
	public ListWrapper<Computer> getAll() {
		List<Computer> c = computerServiceImpl.getAll();
		ListWrapper<Computer> cw = new ListWrapper<>();
		cw.setList(c);
		return cw;
	}

	@Override
	public Computer getById(int id) {
		return computerServiceImpl.getById(id);
	}

	@Override
	public Page getPage(String name, int idx, int size, String orderBy) {
		return computerServiceImpl.getPage(name, idx, size, orderBy);
	}

	@Override
	public void set(Computer c) {
		computerServiceImpl.set(c);		
	}

	@Override
	public void update(Computer c) {
		computerServiceImpl.update(c);
	}

	@Override
	public void delete(int id) {
		computerServiceImpl.delete(id);
	}

}
