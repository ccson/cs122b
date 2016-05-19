<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>

<HTML>
<HEAD>
	<TITLE>Fabflix </TITLE>
</HEAD>

<body>
		<h2>Transaction 
		
		<%
			String confirm = (String)session.getAttribute("confirm");
			if (confirm != null && confirm.equals("succeed")){   
		%>
		success!
		
<FORM ACTION="<%=request.getContextPath()%>/success/main"
			METHOD="POST">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Main Page">
	</CENTER>
</FORM>

		<%
			}
			else{
		%>
		failed!
		
<FORM ACTION="<%=request.getContextPath()%>/success/main"
			METHOD="POST">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Main Page">
	</CENTER>
</FORM>
<FORM ACTION="<%=request.getContextPath()%>/success/info"
			METHOD="POST">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Try Again">
	</CENTER>
</FORM>

		<%
			}
		%>
		
		</h2>


</body>

</HTML>

