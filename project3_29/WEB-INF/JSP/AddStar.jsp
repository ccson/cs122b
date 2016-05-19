<<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>

<%@ page import="MyClasses.*"%>

<html>

	<head>
	</head>
	
	<body>
	
		<h1 align="center">Add Star</h1>
		
<%

		String insertStarStatus = (String)session.getAttribute("insertStarStatus"); 

		if (loginInfoCorrect != null && loginInfoCorrect.equals("false")){
%>

			<h3 style="color:red">Star Failed To Insert. Please Try Again.</h3>
			
<%
		}
%>

<%
		if (loginInfoCorrect != null && loginInfoCorrect.equals("false")){
%>

			<h3 style="color:green">Star Inserted Successfully. </h3>
						
<%
		}
%>
	
		<form method="post">
		<table>
			<tr>
				<td>First Name: </td>
				<td><input type="text" name="firstName" id="firstName"></input></td>
			</tr>
			<tr>
				<td>Last Name: </td>
				<td><input type="text" name="lastName" id="lastName" required></input></td>
			</tr>
		</table>
		</form>
		
	</body>
	
</html>