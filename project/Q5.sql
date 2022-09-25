/*Quais o nome dos servidores que não possuem dependentes?*/
SELECT 
	S.nome
FROM 
	Servidor AS S
WHERE NOT EXISTS 
	(
		SELECT 
			*
		FROM 
			Dependente AS D
		WHERE 
			S.matricula=servidorMatricula
	);