<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>

<HTML>
	<BODY>

		<h1 align="center">Fablix Browse</h1>
			
<FORM ACTION="<%=request.getContextPath()%>/success/shoppingcart">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Checkout">
	</CENTER>
</FORM>

		<h2><a href="<%=request.getContextPath()%>/success/main">Back To Main Page</a></h2>
		<a href="<%=request.getContextPath()%>/success/browse/genre">Browse By Movie Genre</a>
		<br><br>
		<a href="<%=request.getContextPath()%>/success/browse/title">Browse By Movie Title</a>
		
	</BODY>

</HTML>