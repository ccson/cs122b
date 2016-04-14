<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*, 
 MyClasses.*"
%>

<%@ page import="../MyClasses.*"%>

<html>
	<body>
		<h1 align="center">Movie Information</h1>
		
<%
		singleMovie = (Movie)session.getAttribute("singleMovie"); 
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
				<td><img src=<%="\'" + singleMovie.getBannerURL() + "\'"%> alt="Movie Poster Is Not Available"/></td>
			</tr>	
			<tr>
				<td>List of Genres: </td>
				<td>
					<ul>
<%
						for (int i = 0; i < singleMovie.getGenres().size(); i++){
							
							String parameters = "\"/servlet/MovieList?type=browse&sort=none&genre=" + singleMovie.getGenres().get(i).getID() + "\""; 
%>
							<li><a href=<%=parameters%><%=singleMovie.getGenres().get(i).getName()%></a></li>
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
							
							String parameters = "\"" + "/servlet/SingleStar?" + singleMovie.getStars().get(j).getID() + "\""; 
%>
							<li><a href=<%=parameters%><%=singleMovie.getStars().get(j).getFirstName() + singleMovie.getStars().get(j).getLastName()%></a></li>
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
	</body>
</html>