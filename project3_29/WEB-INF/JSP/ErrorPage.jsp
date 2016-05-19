<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 java.util.*"
%>

<html>
	<head>
	</head>
	
	<body>
	
<%
		String error = (String)session.getAttribute("errorPage"); 
		
		if (error != null && error.equals("yearNumberError")){
%>
			<p style="color:red">Error: Year has to be a numerical value. Click on one of the following links to take you back. </p>
<%
		}
%>

<%		
		if (error != null && error.equals("amountPerPageNumberError")){			
%>
			<p style="color:red">Error: Amount Per Page has to be a numerical value. Click on one of the following links to take you back. </p>
<%
		}
%>

<%		
		if (error != null && error.equals("pageNumberError")){			
%>
			<p style="color:red">Error: Page Number has to be a numerical value. Click on one of the following links to take you back. </p>
<%
		}
%>

<%		
		if (error != null && error.equals("quantityNumberError")){			
%>
			<p style="color:red">Error: Page Number has to be a numerical value. Click on one of the following links to take you back. </p>
<%
		}
%>

<%		
		if (error != null && error.equals("idNumberError")){			
%>
			<p style="color:red">Error: ID has to be a numerical value. Click on one of the following links to take you back. </p>
<%
		}
%>
	
		<br><br>
		<h3><a href="<%=request.getContextPath()%>/success/main">Back To Main Page</a></h3>
		<h3><a href="<%=request.getContextPath()%>/success/browse">Back To Browse Page</a></h3>
		<h3><a href="<%=request.getContextPath()%>/success/Search">Back To Search Page</a></h3>
	
	</body>
	
</html>

<%

	session.setAttribute("errorPage", null); 

%>