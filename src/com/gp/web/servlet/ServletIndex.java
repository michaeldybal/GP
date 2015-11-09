package com.gp.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gp.algorithm.Calculator;

/**
 * 
 * Servlet for index.jsp
 * 
 */
@WebServlet("/home")
public class ServletIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Get expression from jsp page
		 */
		String expression = request.getParameter("expression");
		
		/**
		 * Get notation from jsp page
		 */
		String notation = request.getParameter("notation");
		
		/**
		 * if expression is null, then redirect index.jsp
		 * else calculate expression
		 * and redirect to result.jsp
		 */
		if((expression==null) || (expression=="")){
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		else{
			request.setAttribute("expression", expression);
			request.setAttribute("result", Calculator.calculateIt(expression, Integer.valueOf(notation)));
			getServletContext().getRequestDispatcher("/output.jsp").forward(request, response);
		}
	}
}
