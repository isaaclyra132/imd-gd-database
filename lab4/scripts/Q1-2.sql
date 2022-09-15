SELECT 
	employee.fname as "employee.fname",
    employee.minit as "employee.minit",
    employee.lname as "employee.lname"
FROM 
	employee, 
    dependent
WHERE 
	ssn=essn 
    AND fname=dependent_name;