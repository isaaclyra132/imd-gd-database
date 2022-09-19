%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Password: root
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Criação, exibição e remoção de esquemas
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

DROP DATABASE RESTAURANTS;

CREATE DATABASE RESTAURANTS;
USE RESTAURANTS;

CREATE TABLE restaurant(
    _id     VARCHAR(100)     NOT NULL,
    borough VARCHAR(100),
    cuisine VARCHAR(100),
    name    VARCHAR(100),
    restaurant_id  VARCHAR(100),
    CONSTRAINT RESPK PRIMARY KEY(_id)
);

CREATE TABLE grade(
    id    INT NOT NULL AUTO_INCREMENT,
    date  DATE,
    grade VARCHAR(15),
    score INT,
    restaurant_fk VARCHAR(100),
    CONSTRAINT GRPK PRIMARY KEY(id),
    CONSTRAINT RESSUPERFK
        FOREIGN KEY(restaurant_fk)
        REFERENCES restaurant(_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE address(
    id    INT NOT NULL AUTO_INCREMENT,
    building  VARCHAR(100),
    longitude DOUBLE,
    latitude DOUBLE,
    street  VARCHAR(100),
    zipcode  VARCHAR(10),
    restaurant_fk VARCHAR(100),
    CONSTRAINT ADDPK PRIMARY KEY(id),
    CONSTRAINT RES_SUPERFK
        FOREIGN KEY(restaurant_fk)
        REFERENCES restaurant(_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

SELECT _id, borough, cuisine, name, restaurant_id, g.id AS grade_id, date, grade, score, a.id AS address_id, building, longitude, latitude, street, zipcode FROM 
	(restaurant AS r LEFT JOIN grade as g ON r._id=g.restaurant_fk) 
    LEFT JOIN address AS a ON r._id=a.restaurant_fk
    ORDER BY r._id;

