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
	
		<h1>Add Movie Results</h1>
		
		<FORM ACTION="<%=request.getContextPath()%>/_dashboard/employee/main">
			<CENTER>
				<INPUT TYPE="SUBMIT" VALUE="Back to DashBoard">
			</CENTER>
		</FORM>		
	
<%

		String title = (String)session.getAttribute("procedureTitle"); 
		String year = (String)session.getAttribute("procedureYear"); 
		String director = (String)session.getAttribute("procedureDirector"); 
		String genre = (String)session.getAttribute("procedureGenre"); 
		String firstName = (String)session.getAttribute("procedureStarFirstName");
		String lastName = (String)session.getAttribute("procedureStarLastName"); 

		String insertMovieStatus = (String)session.getAttribute("procedureInsertMovie"); 
		String insertStarStatus = (String)session.getAttribute("procedureInsertStar"); 
		String insertGenreStatus = (String)session.getAttribute("procedureInsertGenre"); 
		String insertStarInMovieStatus = (String)session.getAttribute("procedureInsertStarInMovie"); 
		String insertGenreInMovieStatus = (String)session.getAttribute("procedureInsertGenreInMovie"); 
		String updateMovieStatus = (String)session.getAttribute("procedureUpdateMovie"); 

%>
	
		<table>
			<tr>
				<td>Movie Insert Status: </td>
<%
				if (insertMovieStatus.equals("1")){
%>
					<td style="color:green">The Movie "<%=title%>" Has Been Inserted. </td>
<%
				}else {
%>
					<td>The Movie "<%=title%>" Already Exists. </td>
					
<%
				}
%>
			</tr>
			<tr>
				<td>Star Insert Status: </td>
<%
				if (insertStarStatus.equals("1")){
%>
					<td style="color:green">The Star "<%=firstName%> <%=lastName%>" Has Been Inserted. </td>
<%
				} 
				
				else if (insertStarStatus.equals("0") && insertMovieStatus.equals("1")){
				
%>

					<td>The Movie "<%=title%>" Has Been Inserted. However, the Star "<%=firstName%> <%=lastName%>" Already Exists. </td>
					
<%
				
				}
				
				else if (insertStarStatus.equals("2")){
					
%>
					<td>There Was No Star Provided By The User.</td>
<%
				}
				
				else {
%>
					<td>The Movie "<%=title%>" Has Not Been Inserted, Since It Already Exists. The Star "<%=firstName%> <%=lastName%>" Already Exists. </td>
					
<%
				}
%>
			</tr>
			<tr>
				<td>Genre Insert Status: </td>
<%
				if (insertGenreStatus.equals("1")){
%>
					<td style="color:green">The Genre "<%=genre%>" Has Been Inserted. </td>
<%
				}
				
				else if (insertGenreStatus.equals("0") && insertMovieStatus.equals("1")){
%>					

					<td>The Movie "<%=title%>" Has Been Inserted. However, The Genre "<%=genre%>" Already Exists. </td>
					
<%					
				}
				
				else if (insertGenreStatus.equals("2")){
%>					
					<td>There Was No Genre Provided By The User.</td>
					
					
<%					
				}
				
				else {
%>
					<td>The Movie "<%=title%>" Has Not Been Inserted, Since It Already Exists. The Genre "<%=genre%>" Already Exists. </td>
					
<%
				}
%>
			</tr>
			
			<tr>
				<td>Star In Movie Insert Status: </td>
<%
				if (insertStarInMovieStatus.equals("1")){
%>
					<td style="color:green">The Star "<%=firstName%> <%=lastName%>" In Movie "<%=title%>" Has Been Inserted. </td>
<%
				}
				else if (insertStarStatus.equals("2")){					
%>
					<td>There Was No Star Provided By The User.</td>
					
<%
				}
				else {
%>
					<td>The Star "<%=firstName%> <%=lastName%>" In Movie "<%=title%>" Has Not Been Inserted.  </td>
					
<%
				}
%>
			</tr>
			
			<tr>
				<td>Genre In Movie Insert Status: </td>
<%
				if (insertGenreInMovieStatus.equals("1")){
%>
					<td style="color:green">The Genre "<%=genre%>" In Movie "<%=title%>" Has Been Inserted. </td>
<%
				}
				
				else if (insertGenreStatus.equals("2")){
%>
					<td>There Was No Genre Provided By The User. </td>
<%					
				}
				
				else {
%>
					<td>The Genre "<%=genre%>" In Movie "<%=title%>" Has Not Been Inserted.  </td>
					
<%
				}
%>
			</tr>
			
		</table>
	</body>
	
<%

		session.setAttribute("procedureTitle", null); 
		session.setAttribute("procedureYear", null); 
		session.setAttribute("procedureDirector", null); 
		session.setAttribute("procedureGenre", null); 
		session.setAttribute("procedureStarFirstName", null);
		session.setAttribute("procedureStarLastName", null); 

		session.setAttribute("procedureInsertMovie", null); 
		session.setAttribute("procedureInsertStar", null); 
		session.setAttribute("procedureInsertGenre", null); 
		session.setAttribute("procedureInsertStarInMovie", null); 
		session.setAttribute("procedureInsertGenreInMovie", null); 

%>
	
</html>