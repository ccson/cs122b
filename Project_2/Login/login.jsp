<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>

<HTML>
<HEAD>
  <TITLE>Fabflix</TITLE>
</HEAD>

<BODY BGCOLOR="#FDF5E6">
<H1 ALIGN="CENTER">Login Page</H1>

<FORM ACTION="servlet/Main"
      METHOD="POST">
  Email: <INPUT TYPE="TEXT" NAME="email"><BR>

  Password: <INPUT TYPE="PASSWORD" NAME="pw"><BR>
  <CENTER>
    <INPUT TYPE="SUBMIT" VALUE="Login">
  </CENTER>
</FORM>

</BODY>
</HTML>

