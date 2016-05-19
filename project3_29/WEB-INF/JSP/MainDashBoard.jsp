<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 java.util.*"
%>


<HTML>
<HEAD>
	<TITLE>Fabflix Main Page</TITLE>
</HEAD>

<body>
		<h1 align="center">Fabflix Employee Dashboard</h1>


<%
		String full_name = (String)session.getAttribute("employeefull_name");
		out.println("Welcome back " + full_name);
%>
		<br>
</body>

	<H3 style="color:red">

<%
		String sql_error = (String)session.getAttribute("sql_error");
		if(sql_error != null)
			out.println(sql_error);
		session.setAttribute("sql_error", null);
%>

	</H3>
	<br>
	
	<H3>Insert star</H3>
<FORM ACTION="<%=request.getContextPath()%>/_dashboard/employee/insertstar" METHOD = "POST">
	First Name: <INPUT TYPE="TEXT" NAME="star_firstname"><BR>
	Last Name (or single name): <INPUT TYPE="TEXT" NAME="star_lastname" REQUIRED><BR>
	Date of Birth [yyyy-mm-dd]: <INPUT TYPE="TEXT" NAME="star_dob"><BR>
	Photo URL: <INPUT TYPE="TEXT" NAME="star_url"><BR><BR>
	<INPUT TYPE="SUBMIT" VALUE="Insert">
</FORM>

<br><br><br>

	<H3>Print Metadata</H3>
	
	
<FORM ACTION="<%=request.getContextPath()%>/_dashboard/employee/metadata">
	<INPUT TYPE="SUBMIT" VALUE="Go">
</FORM>

<br><br><br>

	<h3>Add Movie</h3>
	
<%
	String yearInvalid = (String)session.getAttribute("yearInvalid"); 
	
	if (yearInvalid != null && yearInvalid.equals("true")){
%>
		<h4 style="color:red">Year or New Year Input Was Invalid. Please Try Again</h4>
<%
	}
%>

<%
	String starLastNameInvalid = (String)session.getAttribute("starLastNameInvalid"); 
	
	if (starLastNameInvalid != null && starLastNameInvalid.equals("true")){
%>
		<h4 style="color:red">If A Star Is To Be Added, The Last Name is Required. Please Try Again</h4>
<%
	}
%>
	
	
	<form action="<%=request.getContextPath()%>/_dashboard/employee/addmovie" method="post">
	<table>
		<tr>
			<td>Movie Title: </td>
			<td><input type="text" name="title" required></td>
		</tr>
		<tr>
			<td>Movie Year: </td>
			<td><input type="number" name="year" min="1" required></td>
		</tr>
		<tr>
			<td>Movie Director: </td>
			<td><input type="text" name="director" required></td>
		</tr>
		<tr>
			<td>Star First Name: </td>
			<td><input type="text" name="starFirstName"></td>
		</tr>
		<tr>
			<td>Star Last Name: </td>
			<td><input type="text" name="starLastName"></td>
		</tr>
		<tr>
			<td>Movie Genre: </td>
			<td><input type="text" name="genre"></td>
		</tr>
		<br><br>
		<tr>
			<td><input type="submit", value="Add"></td>
		</tr>
	</table>
	</form>
	
<%
	session.setAttribute("yearInvalid", null); 
	session.setAttribute("starLastNameInvalid", null); 
%>

</HTML>

