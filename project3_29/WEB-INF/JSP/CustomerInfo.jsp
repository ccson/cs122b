<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>

<HTML>
<HEAD>
	<TITLE>Fabflix Verification</TITLE>
</HEAD>

<body>
		<h2>Verfication</h2>

</body>


<FORM ACTION="<%=request.getContextPath()%>/success/confirmation"
			METHOD="POST">
	First Name: <INPUT TYPE="TEXT" NAME="first_name" REQUIRED><BR>

	Last Name: <INPUT TYPE="TEXT" NAME="last_name" REQUIRED><BR>

	Credit Card: <INPUT TYPE="TEXT" NAME="credit_card"REQUIRED><BR>

	Expiration Date [####-##-##]: <INPUT TYPE="TEXT" NAME="exp_date"REQUIRED><BR>
	
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Purchase">
	</CENTER>
</FORM>

<FORM ACTION="<%=request.getContextPath()%>/success/shoppingcart"
			METHOD="POST">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Back">
	</CENTER>
</FORM>

</HTML>

