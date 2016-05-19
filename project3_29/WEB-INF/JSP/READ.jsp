<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>

<html>
	<body>

PLEASE DO THIS IN ORDER<br>
<br>
1) How To Compile: <br>
<br>
1. Type "cd fabflix"<br>
2. Type "cd WEB-INF"<br>
3. Type "cd sources"<br>
4. Type "javac -classpath .:../lib/mysql-connector-java-5.0.8-bin.jar:../lib/servlet-api.jar:../lib/javax.json-1.0.jar:../lib/recaptcha4j-0.0.7.jar BrowseByGenre.java Genre.java Movie.java MovieList.java SingleMovie.java SingleStar.java Star.java BrowseByTitle.java Login.java AuthenticationFilter.java Confirmation.java AddToCart.java AddMovie.java EmployeeAuthenticationFilter.java InsertDashBoard.java LoginDashBoard.java MetaData.java VerifyUtils.java MyConstants.java -d ../classes"<br>
5. Type "javac -classpath .:../lib/mysql-connector-java-5.0.8-bin.jar:../lib/servlet-api.jar:../lib/javax.json-1.0.jar:../lib/recaptcha4j-0.0.7.jar BrowseByGenre.java Genre.java Movie.java MovieList.java SingleMovie.java SingleStar.java Star.java BrowseByTitle.java Login.java AuthenticationFilter.java Confirmation.java AddToCart.java AddMovie.java EmployeeAuthenticationFilter.java InsertDashBoard.java LoginDashBoard.java MetaData.java VerifyUtils.java MyConstants.java -d ../.."<br>
<br>
2) Creating the Database: <br>
<br>
1. Type "cd ~"<br>
2. Type "cd fablix"<br>
3. Type "mysql -u root -pccs < createtable.sql"<br>
4. Type "mysql -u root -pccs moviedb < data.sql"<br>
<br>
MYSQL Username: root<br>
MYSQL Password: ccs<br>

<br>
<br>

Important Note: The Employee information is already inserted using the data.sql. 

	</body>
</html>