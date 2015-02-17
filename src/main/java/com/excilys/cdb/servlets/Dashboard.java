package com.excilys.cdb.servlets;

import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDAOImpl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String ATT_COMPUTERS = "computers";
	public static final String ATT_NB_COMPUTERS = "nbcomputers";
	public static final String ATT_PAGE = "page";
	public static final String VUE = "/static/views/dashboard.jsp";

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		int nbComputerPerPage = 10;
		if (request.getParameter("nbPerPage")!=null) {
			nbComputerPerPage = Integer.parseInt(request.getParameter("nbPerPage"));
		}
		int pageNumber= 1;
		if (request.getParameter("page")!=null) {
			pageNumber = Integer.parseInt(request.getParameter("page"));
		}
		
		int nbComputers = ComputerDAOImpl.INSTANCE.getNbComputers();
		
		Page p = new Page(nbComputerPerPage, nbComputers, pageNumber-1);
    	p.setComputers(ComputerDAOImpl.INSTANCE.getPerPage(pageNumber-1, nbComputerPerPage));
	   
	    request.setAttribute( ATT_NB_COMPUTERS, nbComputers );
	    request.setAttribute( ATT_PAGE, p);
	    
	    this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
	    
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		String[] computersId = request.getParameter("selection").split(",");
		for (String id : computersId) {
			int computerId = Integer.parseInt(id);
			ComputerDAOImpl.INSTANCE.delete(computerId);			
		}
	    this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
