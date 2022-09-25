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