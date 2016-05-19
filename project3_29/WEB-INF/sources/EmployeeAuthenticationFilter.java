package MyClasses; 

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class EmployeeAuthenticationFilter implements Filter {
	
	public void init(FilterConfig config) throws ServletException{ }
	
	public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		
		String employeeLoggedIn = (String)session.getAttribute("employeeLoggedIn"); 

		if (employeeLoggedIn == null || employeeLoggedIn.equals("false")){
			
			RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/WEB-INF/JSP/DashBoard.jsp");
			dispatcher.forward(httpRequest, httpResponse);
			
		}
			
		else{
			
			chain.doFilter(request, response);
			
		}
		
	}
	
	public void destroy(){ }
	
}