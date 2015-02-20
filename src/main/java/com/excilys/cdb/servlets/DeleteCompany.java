package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyServiceImpl;

@WebServlet("/delete-company")
public class DeleteCompany extends HttpServlet {

	private static final long serialVersionUID = 5L;
	public static final String ATT_COMPANIES = "companies";
	public static final String VUE = "/static/views/deleteCompany.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Company> companies = CompanyServiceImpl.INSTANCE.getAll();
		request.setAttribute(ATT_COMPANIES, companies);
		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int companyId = Integer.parseInt(request.getParameter("companyId"));

		CompanyServiceImpl.INSTANCE.delete(companyId);

		response.sendRedirect("dashboard");
	}
	
}
