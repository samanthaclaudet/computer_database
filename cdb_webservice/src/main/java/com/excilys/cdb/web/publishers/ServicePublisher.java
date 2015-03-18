package com.excilys.cdb.web.publishers;

import javax.xml.ws.Endpoint;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.dto.DTOMapper;
import com.excilys.cdb.service.impl.CompanyServiceImpl;
import com.excilys.cdb.service.impl.ComputerServiceImpl;
import com.excilys.cdb.web.impl.CompanyWebImpl;
import com.excilys.cdb.web.impl.ComputerWebImpl;

public class ServicePublisher {

	public static void main(String[] args) {
		System.out.println("Publishing...");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/application-context-webservice.xml");

		CompanyServiceImpl companyServiceImpl = (CompanyServiceImpl) context.getBean(CompanyServiceImpl.class);
		Endpoint.publish("http://localhost:8081/cdb/companyPublish", new CompanyWebImpl(companyServiceImpl));
		
		ComputerServiceImpl computerServiceImpl = (ComputerServiceImpl) context.getBean(ComputerServiceImpl.class);
		DTOMapper dtoMapper = (DTOMapper) context.getBean(DTOMapper.class);
		Endpoint.publish("http://localhost:8081/cdb/computerPublish", new ComputerWebImpl(computerServiceImpl, dtoMapper));
		//context.close();
		System.out.println("Published !");
	}

}
