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