<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 java.util.*"
%>


<HTML>
<HEAD>
	<TITLE>Fabflix Results</TITLE>
</HEAD>


<%
	String dashboard = (String)session.getAttribute("dashboard"); 




	if (dashboard != null && dashboard.equals("metadata")){
%>
		<H1 ALIGN="CENTER">MetaData</H1>
	<FORM ACTION="<%=request.getContextPath()%>/_dashboard/employee/main">
		<CENTER>
			<INPUT TYPE="SUBMIT" VALUE="Back to DashBoard">
		</CENTER>
	</FORM>		
<%		
		String metadata = (String)session.getAttribute("metadata"); 
		out.println(metadata);
	}
	
	
	
	if (dashboard != null && dashboard.equals("insert")){
%>
		<H1 ALIGN="CENTER">Insert Star</H1>
		
	
	<FORM ACTION="<%=request.getContextPath()%>/_dashboard/employee/main">
		<CENTER>
			<INPUT TYPE="SUBMIT" VALUE="Back to DashBoard">
		</CENTER>
	</FORM>		
		
		<br>

<%		
		String full_name = (String)session.getAttribute("star_insert"); 
		out.println(full_name + " was successfully inserted into database");
	}
%>




</HTML>

