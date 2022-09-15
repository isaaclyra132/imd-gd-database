SELECT 
	fname as "E.fname",
    minit as "E.minit",
    lname as "E.lname"
FROM
	employee
WHERE
	employee.super_ssn IN
		(	
			SELECT 
				ssn
			FROM
				employee
			WHERE
				employee.fname = 'Franklin'
                AND employee.lname = 'Wong'
		)
			