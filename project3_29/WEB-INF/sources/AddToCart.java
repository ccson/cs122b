package MyClasses; 

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddToCart extends HttpServlet {
	
	
	private static boolean isValidNumber(String input){
		
		for (int i = 0; i < input.length(); i++){
			
			if (!Character.isDigit(input.charAt(i)))
				return false; 
			
		}
		
		return true; 
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		try {
			
			if (request.getParameter("id") != null && !request.getParameter("id").equals("") && !isValidNumber(request.getParameter("id"))){
				
				String error = "idNumberError"; 
				session.setAttribute("errorPage", error); 
				RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/WEB-INF/JSP/ErrorPage.jsp");
				RequetsDispatcherObj.forward(request, response);
				
			}
			
			if (request.getParameter("quantity") != null && !request.getParameter("quantity").equals("") && !isValidNumber(request.getParameter("quantity"))){
				
				String error = "quantityNumberError"; 
				session.setAttribute("errorPage", error); 
				RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/WEB-INF/JSP/ErrorPage.jsp");
				RequetsDispatcherObj.forward(request, response);
				
			}
			
			if (request.getParameter("id") != null && request.getParameter("quantity") != null && !request.getParameter("id").equals("") && !request.getParameter("quantity").equals("")){
				
				@SuppressWarnings("unchecked")
				HashMap<Integer, Integer> shoppingCart = (HashMap<Integer, Integer>)session.getAttribute("ShoppingCart");
				if (Integer.parseInt(request.getParameter("quantity")) == 0)
					shoppingCart.remove(Integer.valueOf(request.getParameter("id"))); 
				else
					shoppingCart.put(Integer.valueOf(request.getParameter("id")), Integer.valueOf(request.getParameter("quantity"))); 
				session.setAttribute("ShoppingCart", shoppingCart); 
				
			}
			
			String referer = request.getHeader("Referer");
			response.sendRedirect(referer);
			
		}

		catch(java.lang.Exception ex){
			
			out.println("<HTML>" +
						"<HEAD><TITLE>" +
						"MovieDB: Error" +
						"</TITLE></HEAD>\n<BODY>" +
						"<P>SQL error in doGet: " +
						ex.getMessage() + "</P></BODY></HTML>");
			
		}
		
		out.close(); 
			
	}
	
}