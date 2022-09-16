SELECT
	fname as "students.fname",
    minit as "students.minit",
    lname as "students.lname"
FROM
	students
WHERE sid NOT IN(
	SELECT
		students_sid
	FROM
		enrolls
)