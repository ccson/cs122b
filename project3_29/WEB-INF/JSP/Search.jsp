<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>

<HTML>
<HEAD>
	<TITLE>Fabflix Search</TITLE>
	
	<script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.js"> </script>
	<script type="text/javascript" src="//code.jquery.com/ui/1.10.3/jquery-ui.js"> </script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
	
	<style>
	
		.ui-autocomplete {
			max-height: 200px;
			overflow-y: auto;
			overflow-x: hidden;
			width: 600px; 
		}
		
		.searchBox {
			
			width: 500px; 
			
		}
		
	</style>
	
</HEAD>

<body>
		<h1 align="center">Fabflix Search</h1>
		
<FORM ACTION="<%=request.getContextPath()%>/success/shoppingcart">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Checkout">
	</CENTER>
</FORM>

		<h2><a href="<%=request.getContextPath()%>/success/main">Back To Main Page</a></h2>
		<h2><a href="<%=request.getContextPath()%>/success/browse">Back To Browse Page</a></h2>

</body>

<FORM NAME="SearchForm" ACTION="<%=request.getContextPath()%>/success/movielist?type=search" method="POST">

	<table>
		<tr>
			<td>Title: </td>
			<td><div class="search-container"><div class="ui-widget"><INPUT type="TEXT" name="title" id="search" class="searchBox"></div></div></td>
		</tr>
		<tr>
			<td>Year: </td>
			<td><INPUT TYPE="TEXT" NAME="year"></td>
		</tr>
		<tr>
			<td>Director: </td>
			<td><INPUT TYPE="TEXT" NAME="director"><td>
		</tr>	
		<tr>
			<td>First Name: </td>
			<td><INPUT TYPE="TEXT" NAME="firstName"></td>
		</tr>
		<tr>	
			<td>Last Name: </td>
			<td><INPUT TYPE="TEXT" NAME="lastName"></td>
		</tr>
	</table>
	<br><INPUT TYPE="SUBMIT" VALUE="Search">
	
<%	
	if(session.getAttribute("empty_search") != null){
		if(((Boolean)session.getAttribute("empty_search"))){
%>
		<h3><font color = "red">Search parameters cannot be empty!</font></h3>
<%	
		session.setAttribute("empty_search", false);
		}
	}
	else{
		session.setAttribute("empty_search", false);
	}
%>

</FORM>

<script>

		$("#search").autocomplete({   
		source : function(request, response) {
		$.ajax({
			url : "<%=request.getContextPath()%>/success/autocomplete?input=" + $("#search").val(),
			type : "GET",
			data : {
				term : request.term
			},
			dataType : "json",
			success : function(data) {
				response(data);
			}
			
		});
	}
});

</script>

</HTML>

