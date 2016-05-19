DROP DATABASE IF EXISTS moviedb;
CREATE DATABASE moviedb;
USE moviedb;

CREATE TABLE movies(
id INT NOT NULL AUTO_INCREMENT,
title VARCHAR(100) NOT NULL,
year INT NOT NULL, 
director VARCHAR(100) NOT NULL, 
banner_url VARCHAR(200), 
trailer_url VARCHAR(200),
FULLTEXT(title),
PRIMARY KEY (id)
);

CREATE TABLE stars(
id INT NOT NULL AUTO_INCREMENT, 
first_name VARCHAR(50) NOT NULL, 
last_name VARCHAR(50) NOT NULL, 
dob DATE, 
photo_url VARCHAR(200), 
PRIMARY KEY(id)
); 

CREATE TABLE stars_in_movies(
star_id INT NOT NULL, 
movie_id INT NOT NULL, 
FOREIGN KEY (star_id) REFERENCES stars(id) ON DELETE CASCADE ON UPDATE CASCADE, 
FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE genres(
id INT NOT NULL AUTO_INCREMENT, 
name VARCHAR(32) NOT NULL, 
PRIMARY KEY(id)
);

CREATE TABLE genres_in_movies(
genre_id INT NOT NULL, 
movie_id INT NOT NULL, 
FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE ON UPDATE CASCADE, 
FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE creditcards(
id VARCHAR(20) NOT NULL, 
first_name VARCHAR(50) NOT NULL, 
last_name VARCHAR(50) NOT NULL, 
expiration DATE NOT NULL, 
PRIMARY KEY(id)
);

CREATE TABLE customers(
id INT NOT NULL AUTO_INCREMENT, 
first_name VARCHAR(50) NOT NULL, 
last_name VARCHAR(50) NOT NULL, 
cc_id VARCHAR(20) NOT NULL, 
address VARCHAR(200) NOT NULL, 
email VARCHAR(50) NOT NULL, 
password VARCHAR(20) NOT NULL, 
PRIMARY KEY(id),
FOREIGN KEY (cc_id) REFERENCES creditcards(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE sales(
id INT NOT NULL AUTO_INCREMENT, 
customer_id INT NOT NULL, 
movie_id INT NOT NULL, 
sale_date DATE NOT NULL, 
PRIMARY KEY(id), 
FOREIGN KEY(customer_id) REFERENCES customers(id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(movie_id) REFERENCES movies(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE employees(
email VARCHAR(50), 
password VARCHAR(20) NOT NULL, 
fullname VARCHAR(100), 
PRIMARY KEY(email)
); 

DELIMITER //
CREATE PROCEDURE add_movie
(IN inputTitle VARCHAR(100), IN inputYear INT, IN inputDirector VARCHAR(100), IN inputGenre VARCHAR(32), 
IN inputStarFirstName VARCHAR(50), IN inputStarLastName VARCHAR(50),
IN newTitle VARCHAR(100), IN newYear INT, IN newDirector VARCHAR(100), 
INOUT insert_movie_status INT, INOUT insert_star_status INT, INOUT insert_genre_status INT, 
INOUT insert_star_in_movie_status INT, INOUT insert_genre_in_movie_status INT, INOUT update_movie_status INT)
proc_label:BEGIN

	DECLARE movieID INT DEFAULT 0; 
	DECLARE starID INT DEFAULT 0; 
    DECLARE genreID INT DEFAULT 0; 
    
    SET insert_movie_status = 0; 
    SET insert_star_status = 0; 
    SET insert_genre_status = 0; 
    SET insert_star_in_movie_status = 0; 
    SET insert_genre_in_movie_status = 0; 
    SET update_movie_status = 0; 

	IF NOT EXISTS (SELECT * FROM movies WHERE title = inputTitle AND year = inputYear AND director = inputDirector) THEN
		INSERT INTO movies (title, year, director)
		VALUES (inputTitle, inputYear, inputDirector); 
		
		SET insert_movie_status = 1; 
	END IF; 
    
    SET movieID = (SELECT id FROM movies WHERE title = inputTitle AND year = inputYear AND director = inputDirector LIMIT 1); 
    
    IF (inputGenre NOT LIKE "") THEN 
    
		IF NOT EXISTS (SELECT * FROM genres WHERE name = inputGenre) THEN
			SET insert_genre_status = 1; 
		END IF; 
	
		INSERT INTO genres (name)
		SELECT inputGenre FROM dual WHERE NOT EXISTS
		(SELECT * FROM genres WHERE name = inputGenre); 
        
        SET genreID = (SELECT id FROM genres WHERE name = inputGenre LIMIT 1); 
        
		IF NOT EXISTS (SELECT * FROM genres_in_movies WHERE genre_id = genreID AND movie_id = movieID) THEN
			SET insert_genre_in_movie_status = 1; 
		END IF; 
            
		INSERT INTO genres_in_movies(genre_id, movie_id)
		SELECT genreID, movieID FROM dual WHERE NOT EXISTS
		(SELECT * FROM genres_in_movies WHERE genre_id = genreID AND movie_id = movieID); 
        
	ELSE
		SET insert_genre_status = 2; 
        
	END IF; 
	
    
    IF (inputStarLastName NOT LIKE "") THEN
    
		IF NOT EXISTS (SELECT * FROM stars WHERE first_name = inputStarFirstName AND last_name = inputStarLastName) THEN
			SET insert_star_status = 1; 
		END IF;
    
		INSERT INTO stars (first_name, last_name)
		SELECT inputStarFirstName, inputStarLastName FROM dual WHERE NOT EXISTS
		(SELECT * FROM stars WHERE first_name = inputStarFirstName AND last_name = inputStarLastName);
        
		SET starID = (SELECT id FROM stars WHERE first_name = inputStarFirstName AND last_name = inputStarLastName LIMIT 1); 
    
		IF NOT EXISTS (SELECT * FROM stars_in_movies WHERE star_id = starID AND movie_id = movieID) THEN
			SET insert_star_in_movie_status = 1; 
		END IF; 
    
		INSERT INTO stars_in_movies (star_id, movie_id)
		SELECT starID, movieID FROM dual WHERE NOT EXISTS
		(SELECT * FROM stars_in_movies WHERE star_id = starID AND movie_id = movieID); 
        
	ELSE
		SET insert_star_status = 2; 
        
	END IF; 
    
    
END //
DELIMITER ;

