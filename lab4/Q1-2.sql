SELECT employee.fname, employee.minit, employee.lname FROM employee, dependent
	WHERE ssn=essn AND fname=dependent_name;