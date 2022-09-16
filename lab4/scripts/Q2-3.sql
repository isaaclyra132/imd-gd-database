SELECT
	C1.cno as 'C1.cno',
    C2.cno as 'C2.cno'
FROM
	customers C1,
    customers C2
WHERE
	C1.zip = C2.zip
    AND C1.cno != C2.cno
ORDER BY
	C1.cno ASC;