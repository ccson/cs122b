<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*"
%>

<%@ page import="MyClasses.*"%>

<%

    ArrayList<Movie> movieList = (ArrayList<Movie>)session.getAttribute("movieList");
    int corpusSize = (Integer)session.getAttribute("corpusSize");  

    if (movieList == null)
      movieList = new ArrayList<Movie>(); 

%>

<html>

  <head>
  
    <style>
    
      td {
        
        text-align: center; 
        border: solid black 2px; 
        
      }

      td.button {
        
        text-align: center; 
        border: none; 
        
      }
      
      td.order {
        
        border: solid blue 2px; 
        
      }
      
      .modal {
          display: none;
          position: absolute;
          z-index: 1; 
          overflow: auto;
      }

      .modal-content {
        
          background-color: #fefefe;
          margin: auto;
          padding: 20px;
          border: 5px solid #888;
      }
    
    </style>
    
    <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.2.min.js"></script>
    
    <script type="text/javascript">
    
      function forwardSort(){
        
        var type = "<%=request.getParameter("type")%>"; 
        if (type == "null")
          type = ""; 
        else
          type = "type=" + type; 
        var title = "<%=request.getParameter("title")%>"; 
        if (title == "null")
          title = ""; 
        else
          title = "&title=" + title; 
        var year = "<%=request.getParameter("year")%>"; 
        if (year == "null")
          year = ""; 
        else
          year = "&year=" + year; 
        var director = "<%=request.getParameter("director")%>"; 
        if (director == "null")
          director = ""; 
        else
          director = "&director=" + director; 
        var firstName = "<%=request.getParameter("firstName")%>"; 
        if (firstName == "null")
          firstName = "";
        else
          firstName = "&firstName=" + firstName; 
        var lastName = "<%=request.getParameter("lastName")%>"; 
        if (lastName == "null")
          lastName = "";
        else
          lastName = "&lastName=" + lastName; 
        var genre = "<%=request.getParameter("genre")%>"; 
        if (genre == "null")
          genre = ""; 
        else
          genre = "&genre=" + genre; 
        var amountPerPage = "<%=request.getParameter("amountPerPage")%>";
        if (amountPerPage == "null")
          amountPerPage = ""; 
        else
          amountPerPage = "&amountPerPage=" + amountPerPage;
        
             
          
        var sort = "&sort=" + document.forms["SortForm"]["sort"].value; 
        
        var link = "<%=request.getAttribute("javax.servlet.forward.request_uri")%>" + "?" + type + title + year + director + firstName + lastName + genre + amountPerPage + "&pageNumber=1" + sort; 
        
        window.location.href = link; 
        
      }
      
      function forwardLimit(){
        
        var type = "<%=request.getParameter("type")%>"; 
        if (type == "null")
          type = ""; 
        else
          type = "type=" + type; 
        var title = "<%=request.getParameter("title")%>"; 
        if (title == "null")
          title = ""; 
        else
          title = "&title=" + title; 
        var year = "<%=request.getParameter("year")%>"; 
        if (year == "null")
          year = ""; 
        else
          year = "&year=" + year; 
        var director = "<%=request.getParameter("director")%>"; 
        if (director == "null")
          director = ""; 
        else
          director = "&director=" + director; 
        var firstName = "<%=request.getParameter("firstName")%>"; 
        if (firstName == "null")
          firstName = "";
        else
          firstName = "&firstName=" + firstName; 
        var lastName = "<%=request.getParameter("lastName")%>"; 
        if (lastName == "null")
          lastName = "";
        else
          lastName = "&lastName=" + lastName; 
        var genre = "<%=request.getParameter("genre")%>"; 
        if (genre == "null")
          genre = ""; 
        else
          genre = "&genre=" + genre; 
        var sort = "<%=request.getParameter("sort")%>";
        if (sort == "null")
          sort = ""; 
        else
          sort = "&sort=" + sort; 
          
        var amountPerPage = "&amountPerPage=" + document.forms["LimitForm"]["limit"].value; 
        var pageNumber = "&pageNumber=1"; 
        
        var link = "<%=request.getAttribute("javax.servlet.forward.request_uri")%>" + "?" + type + title + year + director + firstName + lastName + genre + sort + amountPerPage + pageNumber; 
        
        window.location.href = link; 
        
      }
      
      function previous(){
        
        var amountPerPage = "<%=request.getParameter("amountPerPage")%>";
        amountPerPage = "&amountPerPage=" + amountPerPage; 
        
        var pageNumber = "<%=request.getParameter("pageNumber")%>";
        pageNumber = pageNumber - 1; 
        
        var type = "<%=request.getParameter("type")%>"; 
        if (type == "null")
          type = ""; 
        else
          type = "type=" + type; 
        var title = "<%=request.getParameter("title")%>"; 
        if (title == "null")
          title = ""; 
        else
          title = "&title=" + title; 
        var year = "<%=request.getParameter("year")%>"; 
        if (year == "null")
          year = ""; 
        else
          year = "&year=" + year; 
        var director = "<%=request.getParameter("director")%>"; 
        if (director == "null")
          director = ""; 
        else
          director = "&director=" + director; 
        var firstName = "<%=request.getParameter("firstName")%>"; 
        if (firstName == "null")
          firstName = "";
        else
          firstName = "&firstName=" + firstName; 
        var lastName = "<%=request.getParameter("lastName")%>"; 
        if (lastName == "null")
          lastName = "";
        else
          lastName = "&lastName=" + lastName; 
        var genre = "<%=request.getParameter("genre")%>"; 
        if (genre == "null")
          genre = ""; 
        else
          genre = "&genre=" + genre; 
        var sort = "<%=request.getParameter("sort")%>";
        if (sort == "null")
          sort = ""; 
        else
          sort = "&sort=" + sort; 
          
          
        var pageNumberString = "&pageNumber=" + pageNumber; 
        var link = "<%=request.getAttribute("javax.servlet.forward.request_uri")%>" + "?" + type + title + year + director + firstName + lastName + genre + sort + amountPerPage + pageNumberString; 
        
        window.location.href = link; 
        
      }
      
      function load(){
        
        var type = "<%=request.getParameter("type")%>"; 
        if (type == "null")
          type = ""; 
        else
          type = "type=" + type; 
        var title = "<%=request.getParameter("title")%>"; 
        if (title == "null")
          title = ""; 
        else
          title = "&title=" + title; 
        var year = "<%=request.getParameter("year")%>"; 
        if (year == "null")
          year = ""; 
        else
          year = "&year=" + year; 
        var director = "<%=request.getParameter("director")%>"; 
        if (director == "null")
          director = ""; 
        else
          director = "&director=" + director; 
        var firstName = "<%=request.getParameter("firstName")%>"; 
        if (firstName == "null")
          firstName = "";
        else
          firstName = "&firstName=" + firstName; 
        var lastName = "<%=request.getParameter("lastName")%>"; 
        if (lastName == "null")
          lastName = "";
        else
          lastName = "&lastName=" + lastName; 
        var genre = "<%=request.getParameter("genre")%>"; 
        if (genre == "null")
          genre = ""; 
        else
          genre = "&genre=" + genre; 
        var sort = "<%=request.getParameter("sort")%>";
        if (sort == "null")
          sort = ""; 
        else
          sort = "&sort=" + sort; 
        
        if (<%=request.getParameter("amountPerPage")%> == "null" || <%=request.getParameter("pageNumber")%> == "null" || <%=request.getParameter("sort")%> == null){
          
          var link = "<%=request.getAttribute("javax.servlet.forward.request_uri")%>" + "?" + type + title + year + director + firstName + lastName + genre + "&sort=None&amountPerPage=5&pageNumber=1"; 
          
          window.location.href = link; 
          
        }
        
      }
      
      function buttonLoad(){
        
        var pageNumber = "<%=request.getParameter("pageNumber")%>"; 
        var amountPerPage = "<%=request.getParameter("amountPerPage")%>"; 
        var size = "<%=corpusSize%>"; 
        
        if (pageNumber == "null" || amountPerPage == "null"){
          
          return false; 
          
        }
        
        if (((parseInt(pageNumber) * parseInt(amountPerPage)) - parseInt(size)) >= 0){
          
          document.getElementById("nextButton").style.display = "none"; 
          
        }
        
        if (<%=request.getParameter("pageNumber")%> == "1"){
          
          document.getElementById("previousButton").style.display = "none"; 
          
        }
        
      }
      
      function initializeSelect(){
        
          var sort = "<%=request.getParameter("sort")%>"; 
          var index = 0; 
          
          if (sort == "None")
            index = 0; 
          else if (sort == "TitleAscending")
            index = 1; 
          else if (sort == "TitleDescending")
            index = 2; 
          else if (sort == "YearAscending")
            index = 3; 
          else if (sort == "YearDescending")
            index = 4; 
          
          document.getElementById("SortSelect").selectedIndex = index; 
          
          var amountPerPage = "<%=request.getParameter("amountPerPage")%>";
          
          if (amountPerPage == "5")
            index = 0; 
          else if (amountPerPage == "10")
            index = 1; 
          else if (amountPerPage == "25")
            index = 2; 
          else if (amountPerPage == "50")
            index = 3; 
          else if (amountPerPage == "100")
            index = 4; 
            
          document.getElementById("LimitSelect").selectedIndex = index; 
        
      }
      
      load(); 
      
    </script>
    
  </head>

  <body onload="buttonLoad(); initializeSelect(); return false;">
  
<%

   HashMap<Integer, Integer> shoppingCart = (HashMap<Integer, Integer>)session.getAttribute("ShoppingCart"); 

   int start = 0;
   int end = 0; 
  
   if (request.getParameter("pageNumber") != null && request.getParameter("amountPerPage") != null){
    
     start = ((Integer.parseInt(request.getParameter("pageNumber")) - 1) * Integer.parseInt(request.getParameter("amountPerPage"))) + 1; 
     end = (Integer.parseInt(request.getParameter("pageNumber")) * Integer.parseInt(request.getParameter("amountPerPage"))); 
    
   }
  
   if (corpusSize == 0)
     start = 0; 
   
   if (end > corpusSize)
     end = corpusSize; 

%>

    <h1 align="center">Movie List</h1>
	
	
<FORM ACTION="<%=request.getContextPath()%>/success/shoppingcart">
	<CENTER>
		<INPUT TYPE="SUBMIT" VALUE="Checkout">
	</CENTER>
</FORM>


    <h2><a href="<%=request.getContextPath()%>/success/main">Back To Main Page</a></h2>
    <h2><a href="<%=request.getContextPath()%>/success/browse">Back To Browse Page</a></h2>
    <h2><a href="<%=request.getContextPath()%>/success/Search">Back To Search Page</a></h2>
    <h3>Page Number: <%=request.getParameter("pageNumber")%></h3>
    <h3>Showing: <%=start%> - <%=end%> out of <%=corpusSize%></h3>
    
    <form name="SortForm" onSubmit="forwardSort(); return false;">
      <table>
        <tr>
          <td>Sort Method: </td>
          <td><select id="SortSelect" name="sort">
                  <option value="None">None</option>
                  <option value="TitleAscending">Title Ascending</option>
                  <option value="TitleDescending">Title Descending</option>
                  <option value="YearAscending">Year Ascending</option>
                  <option value="YearDescending">Year Descending</option>
                 </select></td>
        </tr>
        <tr>
          <td><input type="submit" value="Sort"/></td>
        </tr>
      </table>
    </form>
    
    <form name="LimitForm" onSubmit="forwardLimit(); return false;">
      <table>
        <tr>
          <td>Movies Per Page: </td>
          <td><select id="LimitSelect" name="limit">
                  <option value="5">5</option>
                  <option value="10">10</option>
                  <option value="25">25</option>
                  <option value="50">50</option>
                  <option value="100">100</option>
                 </select></td>
        </tr>
        <tr>
          <td><input type="submit" value="Set Limit"/></td>
        </tr>
      </table>
    </form>
        
<%
  
  for (int i = 0; i < movieList.size(); i++){
    
      int currentQuantity; 
      
      if (shoppingCart.containsKey(Integer.valueOf(movieList.get(i).getID())))
        currentQuantity = shoppingCart.get(Integer.valueOf(movieList.get(i).getID()));
      else
        currentQuantity = 0; 

%>

      <script type="text/javascript">
      function addToCart<%=i%>(){
        
        var movieID = "<%=movieList.get(i).getID()%>";
        var quantity = document.forms["CartForm<%=i%>"]["quantity"].value; 
        
        var link = "<%=request.getContextPath()%>" + "/success/addtocart?id=" + movieID + "&quantity=" + quantity;  
        
        window.location.href = link; 
        
      }
      
      function autoPopup<%=i%>(movieID){
        
        var ajaxRequest;
        try{
          ajaxRequest = new XMLHttpRequest();
        } catch (e){
          try{
            ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
          } catch (e) {
            try{
              ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e){
              return false;
            }
          }
        }
        
        
        ajaxRequest.onreadystatechange = function(){
          
          if(ajaxRequest.readyState == 4 && ajaxRequest.status == 200){
            
            document.getElementById("modal-text<%=i%>").innerHTML = ajaxRequest.responseText; 
            
            
          }
          
        }
        
        ajaxRequest.open("GET", "<%=request.getContextPath()%>/success/autopopup?movieID=" + movieID + "&contextPath=<%=request.getContextPath()%>", true);
        ajaxRequest.send(null);
        
      }
      
      </script>
      
      <table>
      <tr>
        <td>Movie ID: </td>
        <td><%=movieList.get(i).getID()%></td>
      </tr>
      <tr>
        <td>Title: </td>
        <td>
        <span id="whole<%=i%>"><a id="popup<%=i%>" onmouseover="return autoPopup<%=i%>(<%=movieList.get(i).getID()%>); " href="<%=request.getContextPath()%>/success/singlemovie?id=<%=movieList.get(i).getID()%>"><%=movieList.get(i).getTitle()%></a>
        <div id="myModal<%=i%>" class="modal">

          <div id="modal-text<%=i%>" class="modal-content">
          </div>

        </div>
        </span>
        </td>
      </tr>
      <tr>
        <td>Year: </td>
        <td><%=movieList.get(i).getYear()%></td>
      </tr>
      <tr>
        <td>Director: </td>
        <td><%=movieList.get(i).getDirector()%></td>
      </tr>
      <tr>
        <td>List of Genres: </td>
        <td>
          <ul>
<%
            for (int k = 0; k < movieList.get(i).getGenres().size(); k++){
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
            for (int j = 0; j < movieList.get(i).getStars().size(); j++){
              
%>
              <li><a href="<%=request.getContextPath()%>/success/singlestar?id=<%=movieList.get(i).getStars().get(j).getID()%>"><%=movieList.get(i).getStars().get(j).getName()%></a></li>
<%
            }
%>						
          </ul>
        </td>
      </tr>	
      </table>
      <form name="CartForm<%=i%>" onSubmit="addToCart<%=i%>(); return false;">
        <table>
            <td class="order">Quantity in Shopping Cart: </td>
            <td class="order"><input type="number" name="quantity" min = 0 value=<%=currentQuantity%> required></td>
            <td class="order"><input type="submit" value="Update"></td>
          </tr>	
        </table>
      </form>
      <br><br>
      
      <script type="text/javascript">
        
        $("#popup<%=i%>").mouseenter(function(event) {
          
          if ($("#myModal<%=i%>").css("display") === "none"){
            
            $("#myModal<%=i%>").css("display", "block"); 
            $("#myModal<%=i%>").css("top", event.pageY); 
            $("#myModal<%=i%>").css("left", event.pageX); 
            
          }
          
        });
        
        $("#whole<%=i%>").mouseleave(function(event) {
          
            $("#myModal<%=i%>").css("display", "none"); 
          
        }); 
        
        $(window).on('beforeunload', function() {
<%
          for (int z = 0; z < movieList.size(); z++){
%>
            $("#myModal<%=z%>").css("display", "none"); 
            
<%
          }
%>
          
        });
        
//        $(window).scroll(function() {
//          $("#myModal<%=i%>").css("display", "none"); 
//        });
//        
//        $( '.modal' ).bind( 'mousewheel DOMMouseScroll', function ( e ) {
//          var e0 = e.originalEvent,
//            delta = e0.wheelDelta || -e0.detail;
//          
//          this.scrollTop += ( delta < 0 ? 1 : -1 ) * 30;
//          e.preventDefault();
//        });
        
      </script>
      

<%

  }

%>
        
      </table>
      
      <table>
        <tr>
          <td class="button"><button id="previousButton" type="button" onclick="previous(); return false; ">Previous</button></td>
          <td class="button"><button id="nextButton" type="button" onclick="next(); return false; ">Next</button></td>
        </tr>
      </table>
      
      
      
      <script type="text/javascript">
      
      function next(){
          
          var amountPerPage = "<%=request.getParameter("amountPerPage")%>";
          
          var pageNumber = "<%=request.getParameter("pageNumber")%>"; 
          pageNumber = parseInt(pageNumber) + 1; 
          
          var type = "<%=request.getParameter("type")%>"; 
          if (type == "null")
            type = ""; 
          else
            type = "type=" + type; 
          var title = "<%=request.getParameter("title")%>"; 
          if (title == "null")
            title = ""; 
          else
            title = "&title=" + title; 
          var year = "<%=request.getParameter("year")%>"; 
          if (year == "null")
            year = ""; 
          else
            year = "&year=" + year; 
          var director = "<%=request.getParameter("director")%>"; 
          if (director == "null")
            director = ""; 
          else
            director = "&director=" + director; 
          var firstName = "<%=request.getParameter("firstName")%>"; 
          if (firstName == "null")
            firstName = "";
          else
            firstName = "&firstName=" + firstName; 
          var lastName = "<%=request.getParameter("lastName")%>"; 
          if (lastName == "null")
            lastName = "";
          else
            lastName = "&lastName=" + lastName; 
          var genre = "<%=request.getParameter("genre")%>"; 
          if (genre == "null")
            genre = ""; 
          else
            genre = "&genre=" + genre; 
          var sort = "<%=request.getParameter("sort")%>";
          if (sort == "null")
            sort = ""; 
          else
            sort = "&sort=" + sort; 
            
          var pageNumberString = "&pageNumber=" + pageNumber; 
          var amountPerPageString = "&amountPerPage=" + amountPerPage; 
          var link = "<%=request.getAttribute("javax.servlet.forward.request_uri")%>" + "?" + type + title + year + director + firstName + lastName + genre + sort + amountPerPageString + pageNumberString; 
          
          window.location.href = link; 
          
        }
      
      </script>
      
  </body>
</html>

<%

    session.setAttribute("movieList", null); 

%>