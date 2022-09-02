use mailorder;

DELETE FROM parts WHERE true;
INSERT INTO parts VALUES(10506     , 'Land Before Time I'  , 200       , 19.99       , 20);
INSERT INTO parts VALUES(10507     , 'Land Before Time II' , 156       , 19.99       , 20);
INSERT INTO parts VALUES(10508     , 'Land Before Time III', 190       , 19.99       , 20);
INSERT INTO parts VALUES(10509     , 'Land Before Time IV' , 60        , 19.99       , 20);
INSERT INTO parts VALUES(10601     , 'Sleeping Beauty'     , 300       , 24.99       , 20);
INSERT INTO parts VALUES(10701     , 'When Harry Met Sally', 120       , 19.99       , 30);
INSERT INTO parts VALUES(10800     , 'Dirty Harry'         , 140       , 14.99       , 30);
INSERT INTO parts VALUES(10900     , 'Dr. Zhivago'         , 100       , 24.99       , 30);

DELETE FROM zipcodes WHERE true;
INSERT INTO zipcodes VALUES(67226, 'Wichita');
INSERT INTO zipcodes VALUES(60606, 'Fort Dodge');
INSERT INTO zipcodes VALUES(50302, 'Kansas City');
INSERT INTO zipcodes VALUES(54444, 'Columbia');
INSERT INTO zipcodes VALUES(66002, 'Liberal');
INSERT INTO zipcodes VALUES(61111, 'Fort Hays');

DELETE FROM employees WHERE true;
INSERT INTO employees VALUES(1000      , 'Jones'     , 67226     , '1995-12-12');
INSERT INTO employees VALUES(1001      , 'Smith'     , 60606     , '1992-01-01');
INSERT INTO employees VALUES(1002      , 'Brown'     , 50302     , '1994-09-01');

DELETE FROM customers WHERE true;
INSERT INTO customers VALUES(1111      , 'Charles'   , '123 Main St.'   , 67226     , '316-636-5555');
INSERT INTO customers VALUES(2222      , 'Bertram'   , '237 Ash Avenue' , 67226     , '316-689-5555');
INSERT INTO customers VALUES(3333      , 'Barbara'   , '111 Inwood St.' , 60606     , '316-111-1234');
INSERT INTO customers VALUES(4444      , 'Marcel'    , '112 Inwood St.' , 60606     , '316-111-1235');
INSERT INTO customers VALUES(5555      , 'Reynold'   , '112 Outwood St.', 61111     , '316-111-1236');

DELETE FROM orders WHERE true;
INSERT INTO orders VALUES(1020, 1111, 1000, '1994-12-10', '1994-10-12');
INSERT INTO orders VALUES(1021, 1111, 1001, '1995-01-12', '1995-01-15');
INSERT INTO orders VALUES(1022, 2222, 1002, '1995-02-13', '1995-02-20');
INSERT INTO orders VALUES(1023, 3333, 1000, '1997-06-20', '1997-06-22');
INSERT INTO orders VALUES(1024, 4444, 1000, '1997-06-20', '1997-06-22');

DELETE FROM odetails WHERE true;
INSERT INTO odetails VALUES(1020, 10506, 1);
INSERT INTO odetails VALUES(1020, 10507, 1);
INSERT INTO odetails VALUES(1020, 10508, 2);
INSERT INTO odetails VALUES(1020, 10509, 3);
INSERT INTO odetails VALUES(1021, 10601, 4);
INSERT INTO odetails VALUES(1021, 10701, 4);
INSERT INTO odetails VALUES(1021, 10800, 4);
INSERT INTO odetails VALUES(1022, 10601, 1);
INSERT INTO odetails VALUES(1022, 10701, 1);
INSERT INTO odetails VALUES(1023, 10800, 1);
INSERT INTO odetails VALUES(1023, 10900, 1);
INSERT INTO odetails VALUES(1024, 10900, 1);
