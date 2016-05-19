package MyClasses; 

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class AuthenticationFilter implements Filter {
	
	public void init(FilterConfig config) throws ServletException{ }
	
	public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		
		String userLoggedIn = (String)session.getAttribute("userLoggedIn"); 

		if (userLoggedIn == null || userLoggedIn.equals("false")){
			
			RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/WEB-INF/JSP/Login.jsp");
			dispatcher.forward(httpRequest, httpResponse);
			
		}
			
		else{
			
			chain.doFilter(request, response);
			
		}
		
	}
	
	public void destroy(){ }
	
}