SELECT
	cname
FROM
	customers
WHERE
	customers.cno
NOT IN
	(	
		SELECT DISTINCT
			cno
		FROM
			orders
	);