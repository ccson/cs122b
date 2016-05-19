<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*"
%>

<html>

	<head></head>
	<body>

		<h1 align="center">Browse By Title</h1>
		
<FORM ACTION="<%=request.getContextPath()%>/success/shoppingcart">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Checkout">
	</CENTER>
</FORM>

		<h2><a href="<%=request.getContextPath()%>/success/main">Back To Main Page</a></h2>
		<h2><a href="<%=request.getContextPath()%>/success/browse">Back To Browse Page</a></h2>
		<h2><a href="<%=request.getContextPath()%>/success/Search">Back To Search Page</a></h2>

		<br>
		<table>

<%

			ArrayList<String> movieBrowseList = (ArrayList<String>)session.getAttribute("movieBrowseList"); 

			if (movieBrowseList == null)
				movieBrowseList = new ArrayList<String>(); 

			for (int i = 0; i < movieBrowseList.size(); i++){

%>

			<tr>
				<td><a href="<%=request.getContextPath()%>/success/movielist?type=browse&title=<%=movieBrowseList.get(i)%>"><%=movieBrowseList.get(i)%></a></td>
			</tr>

<%
			}
%>

		</table>
	</body>
</html>

<%

		session.setAttribute("movieBrowseList", null); 

%>