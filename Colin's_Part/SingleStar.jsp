<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*, 
 MyClasses.*"
%>

<%@ page import="../MyClasses.*"%>

<html>
	<body>
		<h1 align="center">Star Information</h1>
		
<%
		singleStar = (Star)session.getAttribute("singleStar"); 
%>
		
		<table>
			<tr>
				<td>ID: </td>
				<td><%=singleStar.getID()%></td>
			</tr>	
			<tr>
				<td>Name: </td>
				<td><%=singleStar.getFirstName() + singleStar.getLastName()%></td>
			</tr>	
			<tr>
				<td>Date of Birth: </td>
				<td><%=singleStar.getDOB()%></td>
			</tr>
			<tr>
				<td>Picture of Star: </td>
				<td><img src=<%="\'" + singleStar.getPhotoURL() + "\'"%> alt="Star Photo Is Not Available"/></td>
			</tr>
			<tr>
				<td>List of Genres: </td>
				<td>
					<ul>
<%
						for (int i = 0; i < singleStar.getMovies().size(); i++){
							
							String starParameters = "\"" + "/servlet/SingleMovie?id=" + singleStar.getMovies().get(i).getID() + "\"";
							
%>
							<li><a href=<%=starParameters%>><%=singleStar.getMovies().get(i).getName()%></a></li>
<%
						}
%>						
					</ul>
				</td>
			</tr>	
		</table>
		
<%

		session.setAttribute("singleStar", null); 

%>
		
	</body>
</html>