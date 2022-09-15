SELECT
	fname as "E.fname",
    minit as "E.minit",
    lname as "E.lname"
FROM
	employee
WHERE
	employee.ssn
NOT IN
    (	
		SELECT 
			essn
		FROM
			works_on
	)