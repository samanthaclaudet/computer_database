package com.excilys.cdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.service.ComputerServiceImpl;

@WebServlet("/delete-computer")
public class DeleteComputer extends HttpServlet {

	private static final long serialVersionUID = 4L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] computersId = request.getParameter("selection").split(",");
		for (String id : computersId) {
			int computerId = Integer.parseInt(id);
			ComputerServiceImpl.INSTANCE.delete(computerId);
		}
		response.sendRedirect("dashboard");
	}

}
