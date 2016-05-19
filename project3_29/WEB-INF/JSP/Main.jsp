<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 java.util.*"
%>

<%@ page import="MyClasses.*"%>

<%

	if (session.getAttribute("ShoppingCart") == null)
		session.setAttribute("ShoppingCart", new HashMap<Integer, Integer>()); 

%>

<HTML>
<HEAD>
	<TITLE>Fabflix Main Page</TITLE>
</HEAD>

<body>
		<h1 align="center">Fabflix Main Page</h1>
					
<FORM ACTION="<%=request.getContextPath()%>/success/shoppingcart">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Checkout">
	</CENTER>
</FORM>

<%
		String first_name = (String)session.getAttribute("first_name");
		String last_name = (String)session.getAttribute("last_name");
		out.println("Welcome back " + first_name + " " + last_name);
%>
		<br><br>
</body>

<! change this later....
FORM ACTION="servlet/?"
			METHOD="POST">

<FORM ACTION="<%=request.getContextPath()%>/success/Search">
	<INPUT TYPE="SUBMIT" VALUE="Search">
</FORM>

<FORM ACTION="<%=request.getContextPath()%>/success/browse">
	<INPUT TYPE="SUBMIT" VALUE="Browse">
</FORM>

</HTML>

