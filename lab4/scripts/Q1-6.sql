SELECT DISTINCT 
	fname as "employee.fname",
    minit as "employee.minit",
    lname as "employee.lname",
    address as "employee.address"
FROM
	employee, works_on, project
WHERE
    project.pnumber = works_on.pno
    AND project.plocation LIKE '%Houston%'
    AND employee.ssn = works_on.essn
	AND employee.dno NOT IN
		(	
			SELECT
				dnumber
			FROM
				dept_locations
			WHERE
				dept_locations.dlocation LIKE '%Houston%'
		);