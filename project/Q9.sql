/* Quais os servidores que possuem pelo menos um dependente?*/
SELECT 
	S.nome
FROM 
	Servidor S
WHERE EXISTS 
	(
		SELECT 
			* 
		FROM 
			Dependente D
		WHERE 
			S.matricula=D.servidorMatricula
	);