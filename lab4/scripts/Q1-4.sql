SELECT 
	fname as "employee.fname",
    minit as "employee.minit",
    lname as "employee.lname"
FROM
	employee
WHERE ssn NOT IN
	(	
		SELECT DISTINCT
			ssn
		FROM
			employee, 
            project
		WHERE 
			(ssn, pnumber)
		NOT IN
			(	
				SELECT
					ssn, pnumber
				FROM
					employee,
                    project,
                    works_on
				WHERE
					employee.ssn = works_on.essn
                    AND project.pnumber = works_on.pno
			)
	)