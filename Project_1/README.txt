PLEASE DO THIS IN ORDER

Creating the Database: 

1) How To Compile: 

1. Type "cd ~"
2. Type "unzip Project1_29.zip". 
3. Type "cd Project_1"
4. Type "cd jdbc"
5. Type "cd src"
6. Type "javac JDBC.java"
7. Type "mv JDBC.class ../bin/"

2) Creating the Database: 

1. Type "cd ~"
2. Type "cd Project_1"
3. Type "mysql -u root -p -D moviedb < createtable.sql"
4. Type "mysql -u root -p moviedb < data.sql"

3) How To Run: 

1. Type "cd ~"
2. Type "cd Project_1"
3. Type "cd jdbc"
4. Type "cd bin"
5. Type "java -classpath .:../lib/mysql-connector-java-5.0.8-bin.jar JDBC"
6. Database: moviedb | Username: root | Password: ccs
