<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*"
%>

<%@ page import="../MyClasses.*"%>

<html>

  <body>

    <h1 align="center">Movie List</h1>
      <table>
        
<%

  ArrayList<Movie> movieList = (ArrayList<Movie>)session.getAttribute("movieList"); 
  for (int i = 0; i < movieList.size(); i++){

%>

      <tr>
        <td><%=movieList.get(i).getID()%></td>
      </tr>
      <tr>
<%
          String parameters = "\"" + "/servlet/SingleMovie?id=" + movieList.get(i).getID() + "\""; 
%>
        <td><a href=<%=parameters%>><%=movieList.get(i).getTitle()%></a</td>
      </tr>
      <tr>
        <td><%=movieList.get(i).getYear()%></td>
      </tr>
      <tr>
        <td><%=movieList.get(i).getYear()%></td>
      </tr>
      <tr>
        <td><%=movieList.get(i).getDirector()%></td>
      </tr>
      <tr>
        <td>List of Genres: </td>
        <td>
          <ul>
<%
            for (int k = 0; k < movieList.getGenres().size(); k++){
%>
              <li><%=movieList.get(i).getGenres().get(k).getName()%></li>
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
            for (int j = 0; j < movieList.getGenres().size(); j++){
              
              String genreParameters = "\"" + "/servlet/SingleStar?id=" + movieList.get(i).getStars().get(j).getID() + "\""
%>
              <li><a href=<%=genreParameters%><%=movieList.get(i).getStars().get(j).getName()%></a></li>
<%
            }
%>						
          </ul>
        </td>
      </tr>	

<%

  }

%>
        
      </table>
  </body>
</html>

