SELECT DISTINCT
	dept 
FROM 
	courses
WHERE 
	dept 
NOT IN
	(
		SELECT DISTINCT
			dept 
		FROM 
			courses, 
            books
		WHERE 
			(dept, isbn)
		NOT IN 
			(
				SELECT 
					dept, isbn 
				FROM 
					courses, 
					books, 
					bookuses
				WHERE 
					cnum = courses_cnum 
					AND books_isbn = isbn
			)
		AND author = 'Navathe' 
        AND publisher = 'Addison Wesley'
	)