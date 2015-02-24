package com.excilys.cdb.servlets;

import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerServiceImpl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Displays all the computer with pagination
 * It is possible to search by computer name or company name
 * 
 * @author sclaudet
 *
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String ATT_COMPUTERS = "computers";
	public static final String ATT_NB_COMPUTERS = "nbcomputers";
	public static final String ATT_PAGE = "page";
	public static final String ATT_SEARCH = "search";
	public static final String VUE = "/static/views/dashboard.jsp";
	private String searchName = null;
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getParameter("search") != "") {
			searchName = request.getParameter("search");
			request.setAttribute(ATT_SEARCH,  searchName);
		} else {
			// reset when you click on the header
			searchName = null;
		}
				
		int nbComputerPerPage = 10;
		if (request.getParameter("nbPerPage") != null) {
			nbComputerPerPage = Integer.parseInt(request
					.getParameter("nbPerPage"));
		}
		int pageNumber = 1;
		if (request.getParameter("page") != null) {
			pageNumber = Integer.parseInt(request.getParameter("page"));
		}

		int nbComputers = 0;
		Page p = null;
		if (searchName == null) {
			// all results
			p = ComputerServiceImpl.INSTANCE.getPage(pageNumber - 1, nbComputerPerPage);
			nbComputers = p.getNbComputers();
		}
		else {
			// search by name
			p = ComputerServiceImpl.INSTANCE.getByName(searchName, pageNumber - 1, nbComputerPerPage);
			nbComputers = p.getNbComputers();
		}
		
		request.setAttribute(ATT_NB_COMPUTERS, nbComputers);
		request.setAttribute(ATT_PAGE, p);
		
		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
				
	}

}
