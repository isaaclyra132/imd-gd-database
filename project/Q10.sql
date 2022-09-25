/* Quais os servidores o nome iniciado com a letra 'D'?*/

SELECT DISTINCT 
	S.nome
FROM 
	Servidor AS S
WHERE 
	S.nome LIKE "D%";