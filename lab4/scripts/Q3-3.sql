SELECT
	*
FROM
	(
		SELECT
			sid as 'enrolls.sid'
		FROM
			students
		WHERE
			sid
		IN
			(
				SELECT
					students_sid
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
					) as coursesEnrolls
				NATURAL JOIN
					(
						SELECT
							*
						FROM
							catalogue
						WHERE
							cno = 'csc226'
                            OR cno = 'csc227'
					) as cno226And227
				JOIN
					students
				WHERE
					sid = students_sid
			)) as response;
								