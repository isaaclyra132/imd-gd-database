SELECT
	fname as "employee.fname",
    minit as "employee.minit",
    lname as "employee.lname"
FROM
	employee
WHERE
	employee.ssn
IN
	(
		SELECT
			mgr_ssn
		FROM
			department
	)
AND
	employee.ssn
NOT IN 
	(
		SELECT
			essn
		FROM
			dependent
    );