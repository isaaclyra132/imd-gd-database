SELECT DISTINCT 
	name 'students.name'
FROM 
	students, 
    enrolls, 
    courses, 
    bookuses, 
    (
		SELECT 
			* 
		FROM 
			books 
		WHERE 
			publisher = 'Addison Wesley'
	) as booksByAddisonWesley
WHERE 
	students_ssn=ssn 
    AND enrolls.courses_cnum=cnum 
    AND bookuses.courses_cnum=cnum
    AND books_isbn = isbn;