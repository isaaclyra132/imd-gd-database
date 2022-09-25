/* Quantos dependentes autorizados fizeram envios?*/
SELECT
	COUNT(*) as numEnviosDependentes
FROM
(
	SELECT DISTINCT  
		D.nome
	FROM 
		Dependente D 
	INNER JOIN 
		Usuario U 
		ON D.idUsuario=U.idUsuario 
	INNER JOIN 
		Envio E 
        ON U.idUsuario=E.idUsuario 
	WHERE 
		D.autorizado=1
) as enviosDependentesAutorizados;