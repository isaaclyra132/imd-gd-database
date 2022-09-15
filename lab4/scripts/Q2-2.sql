SELECT DISTINCT
	ename,
    city
FROM
	(	SELECT
			*
		FROM
			( 	parts
				NATURAL JOIN
					odetails
				NATURAL JOIN
					orders
			)
		WHERE
			qty*price > 50
	) AS ordersOver50
NATURAL JOIN
	employees
NATURAL JOIN
	zipcodes;
    
	