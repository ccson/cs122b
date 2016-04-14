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
<%

	ArrayList<Genre> genreBrowseList = (ArrayList<Genre>)session.getAttribute("genreBrowseList"); 

	if (genreBrowseList == null)
	genreBrowseList = new ArrayList<Genre>(); 

	for (int i = 0; i < genreBrowseList.size(); i++){

%>
		<a href="/browse/genre/listing?genre=<%=genreBrowseList.get(i).getID()%>"><%=genreBrowseList.get(i).getName()%></a>

<%
	}
%>

</body>
</html>