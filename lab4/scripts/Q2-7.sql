SELECT
	cname as "customers.cname"
FROM
	customers
NATURAL JOIN
	(
		SELECT
			cno
		FROM
			orders
		GROUP BY
			orders.cno
		HAVING
			count(cno) = 2
	) AS twoOrders;