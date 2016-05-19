<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*"
%>

<HTML>
<HEAD>
	<TITLE>Fabflix</TITLE>
	
	<script src='https://www.google.com/recaptcha/api.js'></script>
	<script type="text/javascript">
		
		function redirectMainPage(){
			
			var userLoggedIn = "<%=session.getAttribute("userLoggedIn")%>"; 

			if (userLoggedIn == "true"){
				
				window.location.href = "<%=request.getContextPath()%>/success/main"; 
				
			}
			
		}
		
		redirectMainPage(); 
	
	</script>
	
</HEAD>

<BODY BGCOLOR="#FDF5E6">

<%

	String loginInfoCorrect = (String)session.getAttribute("loginInfoCorrect"); 
	String captchaCorrect = (String)session.getAttribute("captchaCorrect");

%>

<H1 ALIGN="CENTER">Login Page</H1>

<%

	if (loginInfoCorrect != null && loginInfoCorrect.equals("false")){

%>

	<H3 style="color:red">Email or Password is Incorrect. Please Try Again</H3>
	
<%
	}
%>

<%

	if (captchaCorrect != null && captchaCorrect.equals("false")){

%>

	<H3 style="color:red">Please complete the Captcha!</H3>
	
<%
	}
%>

<FORM ACTION="<%=request.getContextPath()%>/main"
			METHOD="POST">
	Email: <INPUT TYPE="TEXT" NAME="email" REQUIRED><BR>

	Password: <INPUT TYPE="PASSWORD" NAME="pw" REQUIRED><BR>
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Login">
	</CENTER>
<div class="g-recaptcha" data-sitekey="6Ld1iB4TAAAAAMRFEWGYVFqGVu_z3THeQd16OkMo"></div>
</FORM>

</BODY>

</HTML>
