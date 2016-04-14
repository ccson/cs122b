<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*"
%>

<html>

<head>Browse By Title</head>
<body>

	<br>
	<table>

<%

		ArrayList<String> movieBrowseList = (ArrayList<String>)session.getAttribute("movieBrowseList"); 

		if (movieBrowseList == null)
			movieBrowseList = new ArrayList<String>(); 

		for (int i = 0; i < movieBrowseList.size(); i++){

%>

		<tr>
			<td><a href="/MovieList/movielist?type=browse&title=<%=movieBrowseList.get(i)%>"><%=movieBrowseList.get(i)%></a></td>
		</tr>

<%
		}
%>

	</table>
</body>
</html>