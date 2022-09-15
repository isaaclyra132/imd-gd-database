SELECT
	*
FROM
	(	
		customers
		NATURAL JOIN
			employees
		NATURAL JOIN
			zipcodes
	)
WHERE
	city LIKE '%Wichita%';
    
    

SELECT DISTINCT
	*
FROM
	(
		employees
		NATURAL JOIN
			zipcodes
		NATURAL JOIN
			orders
    )
