use supplier;

DELETE FROM suppliers WHERE true;
INSERT INTO suppliers VALUES ('S1', 'Jones', 'Atlanta');
INSERT INTO suppliers VALUES ('S2', 'Smith', 'Duluth');
INSERT INTO suppliers VALUES ('S3', 'Blake', 'Marietta');

DELETE FROM parts WHERE true;
INSERT INTO parts VALUES ('P1', 'Nut', 'Red', 'Duluth');
INSERT INTO parts VALUES ('P2', 'Bolt', 'Blue', 'Marietta');
INSERT INTO parts VALUES ('P3', 'Screw', 'Red', 'Dunwoody');
INSERT INTO parts VALUES ('P4', 'Axle', 'Green', 'Marietta');

DELETE FROM projects WHERE true;
INSERT INTO projects VALUES ('J1', 'Bicycles', 'Duluth');
INSERT INTO projects VALUES ('J2', 'Motorcycles', 'Dunwoody');
INSERT INTO projects VALUES ('J3', 'Cars', 'Newark');

DELETE FROM sppj WHERE true;
INSERT INTO sppj VALUES ('S1', 'P1', 'J1', 100);
INSERT INTO sppj VALUES ('S1', 'P2', 'J1', 100);
INSERT INTO sppj VALUES ('S1', 'P2', 'J2', 100);
INSERT INTO sppj VALUES ('S1', 'P1', 'J2', 100);
INSERT INTO sppj VALUES ('S1', 'P2', 'J3', 100);
INSERT INTO sppj VALUES ('S2', 'P1', 'J1', 100);
INSERT INTO sppj VALUES ('S2', 'P2', 'J1', 100);
INSERT INTO sppj VALUES ('S2', 'P3', 'J1', 100);
INSERT INTO sppj VALUES ('S2', 'P3', 'J2', 100);
INSERT INTO sppj VALUES ('S3', 'P1', 'J1', 100);
INSERT INTO sppj VALUES ('S3', 'P2', 'J2', 100);
