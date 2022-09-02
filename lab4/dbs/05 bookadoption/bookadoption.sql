USE bookadoption;

DELETE FROM students WHERE true;
INSERT INTO students VALUES(1111, 'Linekar', 'CSc', '1982-10-20');
INSERT INTO students VALUES(2222, 'Holmes', 'CSc', '1983-12-10');
INSERT INTO students VALUES(3333, 'Tyson', 'Math', '1985-09-21');
INSERT INTO students VALUES(4444, 'Blackwell', 'CSc', '1980-01-22');

DELETE FROM courses WHERE true;
INSERT INTO courses VALUES('CSc1010', 'Computers and Applications', 'Computer Science');
INSERT INTO courses VALUES('CSc2310', 'Java', 'Computer Science');
INSERT INTO courses VALUES('CSc4710', 'Databases', 'Computer Science');
INSERT INTO courses VALUES('CIS6710', 'Databases Programming', 'Computer Information Systems');
INSERT INTO courses VALUES('CIS8710', 'Databases', 'Computer Information Systems');

USE bookadoption;

DELETE FROM enrolls WHERE true;
INSERT INTO enrolls VALUES(1111, 'CSc1010', 'sp04', 'A');
INSERT INTO enrolls VALUES(1111, 'CSc4710', 'sp06', 'B');
INSERT INTO enrolls VALUES(2222, 'CSc1010', 'sp04', 'A');
INSERT INTO enrolls VALUES(2222, 'CIS6710', 'sp04', 'A');
INSERT INTO enrolls VALUES(3333, 'CSc1010', 'sp04', 'C');
INSERT INTO enrolls VALUES(4444, 'CIS8710', 'sp05', 'C');

DELETE FROM books WHERE true;
INSERT INTO books VALUES('02011000', 'Intro to Computers', 'Smith', 'Addison Wesley');
INSERT INTO books VALUES('02012000', 'Database Fundamentals', 'Navathe', 'Addison Wesley');
INSERT INTO books VALUES('02021000', 'Database Concepts', 'Silberschatz', 'McGraw Hill');
INSERT INTO books VALUES('02013000', 'Database Programming', 'Navathe', 'Addison Wesley');

DELETE FROM bookuses WHERE true;
INSERT INTO bookuses VALUES('CSc1010', 'sp04', '02011000');
INSERT INTO bookuses VALUES('CSc1010', 'fa05', '02011000');
INSERT INTO bookuses VALUES('CSc1010', 'sp05', '02011000');
INSERT INTO bookuses VALUES('CSc4710', 'sp05', '02012000');
INSERT INTO bookuses VALUES('CSc4710', 'sp06', '02021000');
INSERT INTO bookuses VALUES('CIS6710', 'sp05', '02013000');
INSERT INTO bookuses VALUES('CIS8710', 'sp05', '02012000');
