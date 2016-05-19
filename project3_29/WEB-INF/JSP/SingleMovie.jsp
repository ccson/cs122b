<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*"
%>

<%@ page import="MyClasses.*"%>

<%

	Movie singleMovie = (Movie)session.getAttribute("singleMovie"); 

%>

<html>
	
	<head>	
	
		<script type="text/javascript">
		
			function addToCart(){
				
				var movieID = "<%=singleMovie.getID()%>";
				var quantity = document.forms["CartForm"]["quantity"].value; 
				
				var link = "<%=request.getContextPath()%>" + "/success/addtocart?id=" + movieID + "&quantity=" + quantity;  
				
				window.location.href = link; 
				
			}
		
		</script>
		
	</head>

	<body>
		<h1 align="center">Movie Information</h1>
			
<FORM ACTION="<%=request.getContextPath()%>/success/shoppingcart">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Checkout">
	</CENTER>
</FORM>

		<h2><a href="<%=request.getContextPath()%>/success/main">Back To Main Page</a></h2>
		<h2><a href="<%=request.getContextPath()%>/success/browse">Back To Browse Page</a></h2>
		<h2><a href="<%=request.getContextPath()%>/success/Search">Back To Search Page</a></h2>
		
<%
		
		HashMap<Integer, Integer> shoppingCart = (HashMap<Integer, Integer>)session.getAttribute("ShoppingCart"); 
		
		if (singleMovie != null){
			
			int currentQuantity; 
			
			if (shoppingCart.containsKey(Integer.valueOf(singleMovie.getID())))
				currentQuantity = shoppingCart.get(Integer.valueOf(singleMovie.getID()));
			else
				currentQuantity = 0; 
				
%>
		
		<table>
			<tr>
				<td>ID: </td>
				<td><%=singleMovie.getID()%></td>
			</tr>	
			<tr>
				<td>Title: </td>
				<td><%=singleMovie.getTitle()%></td>
			</tr>	
			<tr>
				<td>Year: </td>
				<td><%=singleMovie.getYear()%></td>
			</tr>	
			<tr>
				<td>Director: </td>
				<td><%=singleMovie.getDirector()%></td>
			</tr>	
			<tr>
				<td>Poster: </td>
				<td><img src="<%=singleMovie.getBannerURL()%>" alt="Movie Poster Is Not Available"/></td>
			</tr>	
			<tr>
				<td>List of Genres: </td>
				<td>
					<ul>
<%
						for (int i = 0; i < singleMovie.getGenres().size(); i++){
							
%>
							<li><a href="<%=request.getContextPath()%>/success/movielist?type=browse&genre=<%=singleMovie.getGenres().get(i).getID()%>"><%=singleMovie.getGenres().get(i).getName()%></a></li>
<%
						}
%>						
					</ul>
				</td>
			</tr>	
			<tr>
				<td>List of Stars: </td>
				<td>
					<ul>
<%
						for (int j = 0; j < singleMovie.getStars().size(); j++){
							
%>
							<li><a href="<%=request.getContextPath()%>/success/singlestar?id=<%=singleMovie.getStars().get(j).getID()%>"><%=singleMovie.getStars().get(j).getName()%></a></li>
<%
						}
%>						
					</ul>
				</td>
			</tr>	
			<tr>
				<td>Preview Trailer Link: </td>
				<td><%=singleMovie.getTrailerURL()%></td>
			</tr>
		</table>
		<br><br>
		<form name="CartForm" onSubmit="addToCart(); return false;">
			<table>
					<td>Quantity in Shopping Cart: </td>
					<td><input type="number" name="quantity" min = 0 value=<%=currentQuantity%> required></td>
					<td><input type="submit" value="Update"></td>
				</tr>	
			</table>
		</form>
				
		

<%
		}
%>	
	 
	
	
	
	</body>
</html>


<%

		session.setAttribute("singleMovie", null); 

%>