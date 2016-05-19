cd src

sudo javac -classpath .:../lib/mysql-connector-java-5.0.8-bin.jar XMLParser/AllFour.java XMLParser/AllFourStar.java XMLParser/StarInMovie.java XMLParser/AllFourMovie.java XMLParser/GenreInMovie.java XMLParser/AllFourActor.java -d ../bin

cd ..

cd bin

sudo java -classpath .:../lib/mysql-connector-java-5.0.8-bin.jar XMLParser.AllFour


