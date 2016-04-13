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

<body>
    <h2>WECOME...</h2>
    <%
    String first_name = (String)session.getAttribute("first_name");
    String last_name = (String)session.getAttribute("last_name");
    out.println(first_name);
    out.println(last_name);
    %>
</body>

<! change this later....
FORM ACTION="servlet/?"
      METHOD="POST">
<FORM>
  <CENTER>
    <INPUT TYPE="SUBMIT" VALUE="Search">
  </CENTER>
</FORM>

<FORM>
  <CENTER>
    <INPUT TYPE="SUBMIT" VALUE="Browse">
  </CENTER>
</FORM>

</HTML>

