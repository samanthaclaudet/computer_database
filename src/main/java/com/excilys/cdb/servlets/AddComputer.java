package com.excilys.cdb.servlets;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.DTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyServiceImpl;
import com.excilys.cdb.service.ComputerServiceImpl;
import com.excilys.cdb.validators.DTOValidator;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet adding a computer to the database
 * 
 * @author sclaudet
 *
 */
@WebServlet("/add-computer")
public class AddComputer extends HttpServlet {

	private static final long serialVersionUID = 2L;
	public static final String ATT_COMPANIES = "companies";
	public static final String VUE = "/static/views/addComputer.jsp";

	/**
	 * Displays all the companies in the drop-down menu
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Company> companies = CompanyServiceImpl.INSTANCE.getAll();
		request.setAttribute(ATT_COMPANIES, companies);
		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
	}

	/**
	 * Adds a computer to the database
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		int companyId = Integer.parseInt(request.getParameter("companyId"));
		Company company = CompanyServiceImpl.INSTANCE.getById(companyId);
		ComputerDTO cDTO = new ComputerDTO(0, name, introduced, discontinued,
				company);
		
		// validates the DTO
		List<String> errors = DTOValidator.validate(cDTO);
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			doGet(request, response);
			return;
		}
		
		Computer c = DTOMapper.DTOToComputer(cDTO);

		ComputerServiceImpl.INSTANCE.set(c);

		response.sendRedirect("dashboard");
	}

}
