package com.excilys.cdb.web.publishers;

import javax.xml.ws.Endpoint;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.dto.DTOMapper;
import com.excilys.cdb.service.interfaces.CompanyService;
import com.excilys.cdb.service.interfaces.ComputerService;
import com.excilys.cdb.web.impl.CompanyWebImpl;
import com.excilys.cdb.web.impl.ComputerWebImpl;

public class ServicePublisher {

	public static void main(String[] args) {
		System.out.println("Publishing...");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/application-context-webservice.xml");

		CompanyService companyService = (CompanyService) context.getBean(CompanyService.class);
		Endpoint.publish("http://localhost:8081/cdb/companyPublish", new CompanyWebImpl(companyService));
		
		ComputerService computerService = (ComputerService) context.getBean(ComputerService.class);
		DTOMapper dtoMapper = (DTOMapper) context.getBean(DTOMapper.class);
		Endpoint.publish("http://localhost:8081/cdb/computerPublish", new ComputerWebImpl(computerService, dtoMapper));
		
		System.out.println("Published !");
	}

}
