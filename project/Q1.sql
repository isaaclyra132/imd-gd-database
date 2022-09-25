/* Selecionar o nome de todos os servidores que realizaram envios*/
SELECT DISTINCT
	Servidor.nome
FROM
	Servidor
JOIN	
	Usuario
ON
	Servidor.idUsuario = Usuario.idUsuario
JOIN
	Envio
ON 
	Envio.idUsuario = Usuario.idUsuario;