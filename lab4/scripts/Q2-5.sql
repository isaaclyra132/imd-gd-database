SELECT
	cname as "CUST.cname"
FROM
	customers
WHERE
	cno NOT IN
		(	
			SELECT DISTINCT
				cno
			FROM
				customers,
                (
					SELECT
						*
					FROM
						parts
					WHERE
						price < 20
				) AS partsPriceUnder20
			WHERE
				(cno, pno)
			NOT IN
				(
					SELECT
						cno,
                        pno
					FROM
						(
							customers
							NATURAL JOIN
								orders
							NATURAL JOIN
								odetails
							NATURAL JOIN
								parts
						)
				)
		);
		