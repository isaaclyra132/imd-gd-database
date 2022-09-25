/*Qual foi o maior valor informado dos itens enviados e a sua média ? */
SELECT 
	MAX(IE.valorInformado) as MaiorValor, 
	AVG(IE.valorInformado) as MediaValores
FROM 
	ItemEnvio AS IE;