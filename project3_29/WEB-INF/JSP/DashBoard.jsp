<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*"
%>

<HTML>
<HEAD>
	<TITLE>Employee Login</TITLE>
	
	<script type="text/javascript">
		
		function redirectMainPage(){
			
			var userLoggedIn = "<%=session.getAttribute("employeeLoggedIn")%>"; 

			if (userLoggedIn == "true"){
				
				window.location.href = "<%=request.getContextPath()%>/_dashboard/employee/main"; 
				
			}
			
		}
		
		redirectMainPage(); 
	
	</script>
	
</HEAD>

<BODY BGCOLOR="#FDF5E6">

<%

	String loginInfoCorrect = (String)session.getAttribute("employeeloginInfoCorrect"); 

%>

<H1 ALIGN="CENTER">Employee Login</H1>

<%

	if (loginInfoCorrect != null && loginInfoCorrect.equals("false")){

%>

	<H3 style="color:red">Email or Password is Incorrect. Please Try Again</H3>
	
<%
	}
%>



<FORM ACTION="<%=request.getContextPath()%>/_dashboard/main"
			METHOD="POST">
	Email: <INPUT TYPE="TEXT" NAME="employeeemail" REQUIRED><BR>

	Password: <INPUT TYPE="PASSWORD" NAME="employeepw" REQUIRED><BR>
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Login">
	</CENTER>
</FORM>

</BODY>

</HTML>
