<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*"
%>

<%@ page import="MyClasses.*"%>


<HTML>
<HEAD>
	<TITLE>Fabflix Shopping Cart</TITLE>
</HEAD>

<body>


		<h1 align="center">Shopping Cart</h1>

<FORM ACTION="<%=request.getContextPath()%>/success/main"
			METHOD="POST">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Main Page">
	</CENTER>
</FORM>

<%

	HashMap<Integer, Integer> shoppingCart = (HashMap<Integer, Integer>)session.getAttribute("ShoppingCart"); 
	if (shoppingCart == null){
		shoppingCart = new HashMap<Integer, Integer>(); 
%>

		Your cart is empty!
		
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




<%
		boolean empty = true;
		for (Integer j : shoppingCart.keySet()){
			Integer temp = 0;
			Integer result = shoppingCart.get(j);
			if (temp != result)
				empty = false;
		}
		if(empty == false){
			
%>

<FORM ACTION="<%=request.getContextPath()%>/success/info">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Continue to payment screen">
	</CENTER>
</FORM>

<%
			for (Integer i : shoppingCart.keySet()){
%>

				<script type="text/javascript">
					
					function addToCart<%=i%>(){
						
						var movieID = "<%=i%>";
						var quantity = document.forms["CartForm<%=i%>"]["quantity"].value; 
						
						var link = "<%=request.getContextPath()%>" + "/success/addtocart?id=" + movieID + "&quantity=" + quantity;  
						
						window.location.href = link; 
						
					}
					
				</script>

				<table>
				<tr>
					<td>Movie ID: </td>
					<td><%=i%></td>
				</tr>
				<tr>
					<td>Movie Title: </td>
					<td><%=Movie.getTitleFromID(i)%></td>
				</table>
				<form name="CartForm<%=i%>" onSubmit="addToCart<%=i%>(); return false;">
					<table>
						<tr>
							<td class="order">Quantity in Shopping Cart: </td>
							<td class="order"><input type="number" name="quantity" min = 0 value=<%=shoppingCart.get(i)%> required></td>
							<td class="order"><input type="submit" value="Update"></td>
						</tr>	
					</table>
				</form>
				<br><br>
			
<%
			}
		}
		else{
%>

<br>Your shopping cart is empty
	
<%		
		}
	}
%>
		

</body>

</HTML>

