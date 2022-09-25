/*Quais servidores tiveram os valores de seus arquivos informados acima de R$ 300 em Fevereiro?*/
SELECT DISTINCT 
	S.nome
FROM 
	Servidor S 
JOIN 
	Usuario U 
	ON U.idUsuario = S.idUsuario
JOIN 
	PERTENCE P
	ON P.idUsuario = S.idUsuario
JOIN 
	ItemEnvio IE
    ON IE.idEnvio = P.idEnvio
JOIN
	Envio E 
    ON E.idEnvio = IE.idEnvio
WHERE  
	E.mesRef="fevereiro" 
    AND IE.valorInformado>300;