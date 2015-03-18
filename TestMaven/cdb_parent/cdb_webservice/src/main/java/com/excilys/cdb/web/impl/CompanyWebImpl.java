package com.excilys.cdb.web.impl;

import java.util.List;

import javax.jws.WebService;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.impl.CompanyServiceImpl;
import com.excilys.cdb.web.interfaces.CompanyWeb;
import com.excilys.cdb.web.wrapper.ListWrapper;

@WebService(endpointInterface = "com.excilys.cdb.web.interfaces.CompanyWeb")
public class CompanyWebImpl implements CompanyWeb {

	//@Autowired
	private CompanyServiceImpl companyServiceImpl;
	

	public CompanyWebImpl(CompanyServiceImpl companyServiceImpl) {
		this.companyServiceImpl = companyServiceImpl;
	}
	
	@Override
	public ListWrapper<Company> getAll() {
		List<Company> c = companyServiceImpl.getAll();
		ListWrapper<Company> cw = new ListWrapper<>();
		cw.setList(c);
		return cw;
	}

	@Override
	public Company getById(int id) {
		return companyServiceImpl.getById(id);
	}

	@Override
	public void delete(int id) {
		companyServiceImpl.delete(id);		
	}

}
