<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*"
%>

<%@ page import="MyClasses.*"%>

<html>
	<body>
		<h1 align="center">Star Information</h1>
							
<FORM ACTION="<%=request.getContextPath()%>/success/shoppingcart">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Checkout">
	</CENTER>
</FORM>

		<h2><a href="<%=request.getContextPath()%>/success/main">Back To Main Page</a></h2>
		<h2><a href="<%=request.getContextPath()%>/success/browse">Back To Browse Page</a></h2>
		<h2><a href="<%=request.getContextPath()%>/success/Search">Back To Search Page</a></h2>
		
<%
		Star singleStar = (Star)session.getAttribute("singleStar");
		
		if (singleStar != null){
		 
%>
		
		<table>
			<tr>
				<td>ID: </td>
				<td><%=singleStar.getID()%></td>
			</tr>	
			<tr>
				<td>Name: </td>
				<td><%=singleStar.getName()%></td>
			</tr>	
			<tr>
				<td>Date of Birth: </td>
				<td><%=singleStar.getDOB()%></td>
			</tr>
			<tr>
				<td>Picture of Star: </td>
				<td><img src="<%=singleStar.getPhotoURL()%>" alt="Star Photo Is Not Available"/></td>
			</tr>
			<tr>
				<td>List of Movies: </td>
				<td>
					<ul>
<%
						for (int i = 0; i < singleStar.getMovies().size(); i++){
							
%>
							<li><a href="<%=request.getContextPath()%>/success/singlemovie?id=<%=singleStar.getMovies().get(i).getID()%>"><%=singleStar.getMovies().get(i).getTitle()%></a></li>
<%
						}
%>						
					</ul>
				</td>
			</tr>	
		</table>
		
<%
		}
%>				
		
	</body>
</html>

<%

		session.setAttribute("singleStar", null); 

%>