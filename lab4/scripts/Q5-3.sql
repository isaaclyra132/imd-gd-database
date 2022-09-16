SELECT
	dept
FROM
(
	SELECT
		dept,
        count(*) AS total_dept
	FROM
		bookuses
	INNER JOIN
		courses
	ON
		bookuses.courses_cnum = courses.cnum
	GROUP BY
		dept
) as livros
INNER JOIN 
(

	SELECT
		dept,
        count(*) AS total_dept_addison
	FROM
		bookuses
	INNER JOIN
		(
			SELECT
				*
			FROM
				books
			WHERE 
				publisher='Addison Wesley'
		) AS booksByAddison
	ON
		bookuses.books_isbn = books_addison.isbn
	INNER JOIN
		courses
	ON
		courses.cnum = bookuses.courses_cnum
	GROUP BY
		dept

) AS booksByAddison
ON 
	livros_addison.dept = livros.dept
WHERE 
	total_dept = total_dept_addison;