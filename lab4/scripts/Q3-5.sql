SELECT
	fname,
    minit,
    lname
FROM
	students
WHERE NOT EXISTS
	(
		SELECT
			cno
		FROM
			catalogue
		WHERE
			cno
		NOT IN
			(
				SELECT
					cno
				FROM
					courses,
                    enrolls
				WHERE
					courses_term = term
					AND students_sid = sid
			)
    )