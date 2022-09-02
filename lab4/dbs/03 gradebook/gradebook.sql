use gradebook;

DELETE FROM catalogue WHERE true;
INSERT INTO catalogue VALUES ('csc226'  , 'Java Programming');
INSERT INTO catalogue VALUES ('csc227'  , 'C++ Programming');
INSERT INTO catalogue VALUES ('csc481'  , 'Automata');
INSERT INTO catalogue VALUES ('csc880'  , 'Logic Programming');

DELETE FROM courses WHERE true;
INSERT INTO courses VALUES ('f96', 1031, 'csc226', 90, 80, 65, 50);
INSERT INTO courses VALUES ('f96', 1032, 'csc227', 90, 80, 70, 50);
INSERT INTO courses VALUES ('f96', 1033, 'csc481', 90, 80, 70, 60);
INSERT INTO courses VALUES ('sp97', 1031, 'csc226', 90, 80, 70, 65);
INSERT INTO courses VALUES ('sp97', 1032, 'csc880', 90, 80, 70, 60);

DELETE FROM students WHERE true;
INSERT INTO students VALUES (1111, 'Nandita', 'Rajshekhar', 'K');
INSERT INTO students VALUES (2222, 'Sydney', 'Corn', 'A');
INSERT INTO students VALUES (3333, 'Susan', 'Williams', 'B');
INSERT INTO students VALUES (4444, 'Naveen', 'Rajshekhar', 'B');

DELETE FROM enrolls WHERE true;
INSERT INTO enrolls VALUES ('f96', 1031, 1111);
INSERT INTO enrolls VALUES ('f96', 1032, 1111);
INSERT INTO enrolls VALUES ('f96', 1033, 1111);
INSERT INTO enrolls VALUES ('sp97', 1031, 1111);
INSERT INTO enrolls VALUES ('sp97', 1032, 1111);
INSERT INTO enrolls VALUES ('sp97', 1031, 2222);
INSERT INTO enrolls VALUES ('f96', 1032, 4444);
