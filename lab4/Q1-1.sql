SELECT employee.fname as "employee.fname", employee.minit as "employee.minit", employee.lname as "employee.lname"
	FROM employee, works_on, project 
	WHERE ssn=essn AND pno=pnumber AND dno=dnum AND pname="ProductX" AND hours >= 10 AND dno=5;
