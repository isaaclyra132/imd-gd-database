SELECT
	fname as "students.fname",
    minit as "students.minit",
    lname as "students.lname"
FROM
	( 
		SELECT 
			*
		FROM
			courses
		JOIN
			enrolls
		WHERE
			courses_secno = secno
            AND courses_term = term
    ) as courseEnrolls
NATURAL JOIN
	(
		SELECT
			*
		FROM
			catalogue
		WHERE
			ctitle = 'Automata'
    ) as automataTuples
JOIN
	students
WHERE
	sid = students_sid
    AND courses_term = 'f96';