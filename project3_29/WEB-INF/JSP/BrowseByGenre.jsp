<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*"
%>

<%@ page import="MyClasses.*"%>

<html>

	<head></head>
	<body>
	
		<h1 align="center">Browse By Genre</h1>
				
<FORM ACTION="<%=request.getContextPath()%>/success/shoppingcart">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Checkout">
	</CENTER>
</FORM>

		<h2><a href="<%=request.getContextPath()%>/success/main">Back To Main Page</a></h2>
		<h2><a href="<%=request.getContextPath()%>/success/browse">Back To Browse Page</a></h2>
		<h2><a href="<%=request.getContextPath()%>/success/Search">Back To Search Page</a></h2>
		
		<table>

<%

			ArrayList<Genre> genreBrowseList = (ArrayList<Genre>)session.getAttribute("genreBrowseList"); 

			if (genreBrowseList == null)
				genreBrowseList = new ArrayList<Genre>(); 

			for (int i = 0; i < genreBrowseList.size(); i++){

%>

		<tr>
			<td><a href="<%=request.getContextPath()%>/success/movielist?type=browse&genre=<%=genreBrowseList.get(i).getID()%>"><%=genreBrowseList.get(i).getName()%></a></td>
		</tr>

<%
	}
%>

	</body>
	
</html>

<%

		session.setAttribute("genreBrowseList", null); 

%>