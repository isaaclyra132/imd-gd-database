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


/*Quantos itens o dependente "Enzo Damasceno" está associado?*/
SELECT 
	D.nome, 
    COUNT(D.nome) 
FROM 
	Dependente D 
INNER JOIN 
	Usuario U 
    ON D.idUsuario=U.idUsuario 
INNER JOIN 
	PERTENCE P 
    ON U.idUsuario=P.idUsuario  
INNER JOIN 
	ItemEnvio I 
    ON P.idItem=I.idItem
WHERE
	D.nome="Enzo Damasceno"; 
    
    
/*Quantos arquivos "Ítalo Damasceno" enviou com extensão "pdf"? */
 SELECT 
	nome, 
    COUNT(S.nome) as qtyArquivosPDF
 FROM 
	Servidor S 
NATURAL JOIN 
	Usuario U 
NATURAL JOIN 
	Envio E 
NATURAL JOIN 
	Arquivo A
WHERE 
	S.nome= "Ítalo Damasceno" 
	AND A.extensao="pdf";
  
  
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
    
    
/*Qual foi o maior valor informado dos itens enviados e a sua média ? */
SELECT 
	MAX(IE.valorInformado) as MaiorValor, 
	AVG(IE.valorInformado) as MediaValores
FROM 
	ItemEnvio AS IE;
    


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
    
    
/* Quais os servidores o nome iniciado com a letra 'D'?*/
SELECT DISTINCT 
	S.nome
FROM 
	Servidor AS S
WHERE 
	S.nome LIKE "D%";