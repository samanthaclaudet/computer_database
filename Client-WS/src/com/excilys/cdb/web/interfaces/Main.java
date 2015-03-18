package com.excilys.cdb.web.interfaces;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Main {
	public static void main(String... args) throws MalformedURLException {

		URL url = new URL("http://localhost:8080/cdb/computerPublish?wsdl");

		// 1st argument service URI, refer to wsdl document above
		// 2nd argument is service name, refer to wsdl document above
		QName qname = new QName("http://impl.web.cdb.excilys.com/",
				"ComputerWebImplService");

		Service service = Service.create(url, qname);

		ComputerWeb hello = service.getPort(ComputerWeb.class);

		System.out.println(hello.getHelloWorldAsString("mkyong"));
	}
}
