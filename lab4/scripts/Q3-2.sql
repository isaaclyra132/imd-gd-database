SELECT
	sid as 'enrolls.sid'
FROM
	(
		SELECT 
			sid
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
					) as courseEnrolls
				NATURAL JOIN
					(
						SELECT
							*
						FROM
							catalogue
						WHERE
							cno = 'csc226'
					) as cno226
				JOIN
					students
				WHERE
					sid = students_sid
			)
		AND 	
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
					) as courseEnrolls
				NATURAL JOIN
					(
						SELECT
							*
						FROM
							catalogue
						WHERE
							cno = 'csc227'
					) as cno226
				JOIN
					students
				WHERE
					sid = students_sid
			)
		) as response;