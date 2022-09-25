/*Quais foram os servidores que enviaram documentos em Jan/2022?*/
SELECT 
	S.nome
FROM 
	Servidor S
INNER JOIN 
	Usuario U 
    ON S.idUsuario=U.idUsuario 
INNER JOIN 
	Envio E 
    ON U.idUsuario=E.idUsuario  
WHERE
	E.mesRef="janeiro"
    AND E.anoRef="2022"; 