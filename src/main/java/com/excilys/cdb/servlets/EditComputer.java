package com.excilys.cdb.servlets;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.DTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanyDAOImpl;
import com.excilys.cdb.persistence.ComputerDAOImpl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {

	private static final long serialVersionUID = 3L;
	public static final String ATT_COMPANIES = "companies";
	public static final String ATT_COMPUTER = "computer";
	public static final String ATT_COMPUTER_ID = "idComputer";
	public static final String VUE          = "/static/views/editComputer.jsp";
	public int computerId;
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	List<Company> companies = CompanyDAOImpl.INSTANCE.getAll();
    	request.setAttribute( ATT_COMPANIES, companies );
    	computerId = Integer.parseInt(request.getParameter("id"));
    	request.setAttribute ( ATT_COMPUTER_ID, computerId);
    	
    	Computer c = ComputerDAOImpl.INSTANCE.getById(computerId);
    	ComputerDTO cDTO = DTOMapper.computerToDTO(c);
    	request.setAttribute(ATT_COMPUTER, cDTO);

    	this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		String name = request.getParameter("computerName");
    	String introduced = request.getParameter("introduced");
    	String discontinued = request.getParameter("discontinued");
    	int companyId = Integer.parseInt(request.getParameter("companyId"));
    	Company company = CompanyDAOImpl.INSTANCE.getById(companyId);
    	ComputerDTO cDTO = new ComputerDTO(computerId, name, introduced, discontinued, company);
    	Computer c = DTOMapper.DTOToComputer(cDTO);
    	ComputerDAOImpl.INSTANCE.update(computerId, c);
    	
	    this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
	
}
	