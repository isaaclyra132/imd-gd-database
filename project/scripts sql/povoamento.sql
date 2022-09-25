use pcas_db;

DELETE FROM Usuario WHERE true;
INSERT INTO Usuario (idUsuario)
VALUES ('001');
INSERT INTO Usuario (idUsuario)
VALUES ('002');
INSERT INTO Usuario (idUsuario)
VALUES ('003');
INSERT INTO Usuario (idUsuario)
VALUES ('004');
INSERT INTO Usuario (idUsuario)
VALUES ('005');
INSERT INTO Usuario (idUsuario)
VALUES ('006');
INSERT INTO Usuario (idUsuario)
VALUES ('007');
INSERT INTO Usuario (idUsuario)
VALUES ('008');
INSERT INTO Usuario (idUsuario)
VALUES ('009');
INSERT INTO Usuario (idUsuario)
VALUES ('010');
INSERT INTO Usuario (idUsuario)
VALUES ('011');
INSERT INTO Usuario (idUsuario)
VALUES ('012');
INSERT INTO Usuario (idUsuario)
VALUES ('013');
INSERT INTO Usuario (idUsuario)
VALUES ('014');
INSERT INTO Usuario (idUsuario)
VALUES ('015');

SELECT 
	*
FROM 
	Usuario;


DELETE FROM Servidor WHERE true;
INSERT INTO Servidor (matricula, nome, isAdmin, idUsuario)
VALUES ('0101', 'Leandro Queiroz', '0', '001');
INSERT INTO Servidor (matricula, nome, isAdmin, idUsuario)
VALUES ('0202', 'Ítalo Damasceno', '0', '002');
INSERT INTO Servidor (matricula, nome, isAdmin, idUsuario)
VALUES ('0303', 'Douglas Moura', '0', '003');
INSERT INTO Servidor (matricula, nome, isAdmin, idUsuario)
VALUES ('0404', 'Noé Albuquerque', '0', '004');
INSERT INTO Servidor (matricula, nome, isAdmin, idUsuario)
VALUES ('0505', 'Erick Barbosa', '1', '005');

SELECT 
	*
FROM 
	Servidor;


DELETE FROM Dependente WHERE true;
INSERT INTO Dependente (codDependente, nome, parentesco, titulo, email, ativo, autorizado, idUsuario, servidorMatricula )
VALUES ( '010101', 'Roberta Queiroz', 'Esposa', '12345670', 'robertaqueiroz@gmail.com', '1', '0', '006', '0101');
INSERT INTO Dependente (codDependente, nome, parentesco, titulo, email, ativo, autorizado, idUsuario, servidorMatricula )
VALUES ( '020202', 'Giovana Queiroz', 'Filho(a)', '12345671', 'giovanaqueiroz@gmail.com', '1', '0', '007', '0101');
INSERT INTO Dependente (codDependente, nome, parentesco, titulo, email, ativo, autorizado, idUsuario, servidorMatricula )
VALUES ( '030303', 'Enzo Damasceno', 'Filho(a)', '12345672', 'enzodamasceno@yahoo.com.br', '1', '0', '008', '0202');
INSERT INTO Dependente (codDependente, nome, parentesco, titulo, email, ativo, autorizado, idUsuario, servidorMatricula )
VALUES ( '040404', 'Valentina Damasceno', 'Filho(a)', '12345673', 'valentinadamasceno@gmail.com', '1', '0', '009', '0202');
INSERT INTO Dependente (codDependente, nome, parentesco, titulo, email, ativo, autorizado, idUsuario, servidorMatricula )
VALUES ( '050505', 'Nicolas Damasceno', 'Filho Universitário', '12345674', 'nicolasdamasceno@outlook.com', '0', '0', '010', '0202');
INSERT INTO Dependente (codDependente, nome, parentesco, titulo, email, ativo, autorizado, idUsuario, servidorMatricula )
VALUES ( '060606', 'Iasmin Moura', 'Filho Universitário', '12345675', 'iasminmoura@gmail.com', '1', '0', '011', '0303');
INSERT INTO Dependente (codDependente, nome, parentesco, titulo, email, ativo, autorizado, idUsuario, servidorMatricula )
VALUES ( '070707', 'Fernanda Moura', 'Esposa', '12345676', 'fernandamoura@gmail.com', '1', '0', '012', '0303');
INSERT INTO Dependente (codDependente, nome, parentesco, titulo, email, ativo, autorizado, idUsuario, servidorMatricula )
VALUES ( '080808', 'Rita Albuquerque', 'Mãe', '12345677', 'ritaalbuquerque@gmail.com', '1', '1', '013', '0404');
INSERT INTO Dependente (codDependente, nome, parentesco, titulo, email, ativo, autorizado, idUsuario, servidorMatricula )
VALUES ( '090909', 'Angelica Albuquerque', 'Esposa', '12345678', 'angelicaalbuquerque', '1', '1', '014', '0404');
INSERT INTO Dependente (codDependente, nome, parentesco, titulo, email, ativo, autorizado, idUsuario, servidorMatricula )
VALUES ( '101010', 'Sophia Albuquerque', 'Enteado(a)', '12345679', 'sophiaalbuquerque@gmail.com', '1', '0', '015', '0404');

SELECT 
	*
FROM 
	Dependente;


DELETE FROM Envio WHERE true;
INSERT INTO Envio (idEnvio, mesRef, anoRef, tipoUsuario, dataCriacao, envioStatus, idUsuario)
VALUES ('12908312', 'janeiro', '2022', 'pensionista', '2022-01-12 14:03:34', 'atestado', '013');
INSERT INTO Envio (idEnvio, mesRef, anoRef, tipoUsuario, dataCriacao, envioStatus, idUsuario)
VALUES ('90281312', 'agosto', '2022', 'pensionista', '2022-08-23 18:34:23', 'nao atestado', '013'); -- dependente1
INSERT INTO Envio (idEnvio, mesRef, anoRef, tipoUsuario, dataCriacao, envioStatus, idUsuario)
VALUES ('19028391', 'janeiro', '2022', 'pensionista', '2022-01-13 14:23:53', 'atestado', '014'); -- dependente2
INSERT INTO Envio (idEnvio, mesRef, anoRef, tipoUsuario, dataCriacao, envioStatus, idUsuario)
VALUES ('26173612', 'janeiro', '2022', 'servidor', '2022-01-02 09:43:23', 'atestado', '001'); -- leandro
INSERT INTO Envio (idEnvio, mesRef, anoRef, tipoUsuario, dataCriacao, envioStatus, idUsuario)
VALUES ('29832726', 'fevereiro', '2022', 'servidor', '2022-02-04 12:21:14', 'nao atestado', '002');
INSERT INTO Envio (idEnvio, mesRef, anoRef, tipoUsuario, dataCriacao, envioStatus, idUsuario)
VALUES ('28319392', 'março', '2022', 'servidor', '2022-03-08 13:41:49', 'pendente', '002'); -- italo
INSERT INTO Envio (idEnvio, mesRef, anoRef, tipoUsuario, dataCriacao, envioStatus, idUsuario)
VALUES ('12983729', 'junho', '2022', 'servidor', '2022-06-12 16:16:23', 'atestado', '005');
INSERT INTO Envio (idEnvio, mesRef, anoRef, tipoUsuario, dataCriacao, envioStatus, idUsuario)
VALUES ('29382672', 'julho', '2022', 'servidor', '2022-07-29 17:50:20', 'pendente', '005'); -- erick

SELECT
	*
FROM
	Envio, ItemEnvio
WHERE
	Envio.idEnvio = ItemEnvio.idEnvio;


DELETE FROM Arquivo WHERE true;
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('akjhdqukwey189237', '12908312', 'fatura comprovante', 'kjhasdmzx129873', 'pdf');
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('129083alksdj12321', '90281312', 'fatura', 'A232KJZXhcakjdhq', 'png');
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('aksdjkaljm2130128', '90281312', 'comprovante', 'AJSdhqweuqyw123', 'png');
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('qioweuaslskjdas12', '19028391', 'fatura', 'QIOWUEasksldj214', 'jpg');
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('kzmxcaklsdjqwieu1', '19028391', 'comprovante', 'AKJHzcjkans927', 'jpg');
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('aklsjqwieu1231245', '26173612', 'fatura', 'AdhkjakjdhaKSDJ124', 'png');
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('zmncxkjahsdqw1234', '26173612', 'comprovante', 'AKJDzncaskj128AS', 'png');
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('zxmckasdjqiwu1234', '29832726', 'fatura comprovante', 'AAsjdhakASDJAj12', 'pdf');
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('zimncqiowueas8979', '29832726', 'fatura comprovante', 'KJAHDSzkjahsd283', 'pdf');
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('sizmxcklasqw43872', '28319392', 'fatura comprovante', 'AKJdhakjshdYu289', 'pdf');
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('lkzxcmzasklquw432', '12983729', 'fatura comprovante', 'SAJDKASKnzxj2345', 'pdf');
INSERT INTO Arquivo (idArquivo, idEnvio, tipoArquivo, data64, extensao)
VALUES ('zxnmcajkdhqwu1234', '29382672', 'fatura comprovante', 'LKJSjKASHdakdhs2', 'pdf');

SELECT
	*
FROM
	Arquivo;


DELETE FROM ItemEnvio WHERE true;
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('iOASUuqwoe234', '482.89', '12908312');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('ioASIUDJN21ad', '349.49', '19028391');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('aklASdjkh1289', '289.89', '90281312');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('kdjlASDhk1238', '230.39', '12983729');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('UWYQi1231289A', '340.30', '29382672');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('jOIASJinZMX29', '230.29', '26173612');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('ALSKDJmxcz290', '322.30', '26173612');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('kZXKCaksjQ203', '280.29', '26173612');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('mAKSqwio128Ad', '302.39', '28319392');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('AISqowej1i290', '302.89', '28319392');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('ZKakjQqwui123', '480.89', '29832726');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('PoqweiUASj928', '309.29', '29832726');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('AShzxjnaAJq29', '309.29', '29832726');
INSERT INTO ItemEnvio (idItem, valorInformado, idEnvio)
VALUES ('MZNasmqwio120', '309.29', '29832726');

SELECT 
	*
FROM 
	ItemEnvio
ORDER BY idEnvio;


DELETE FROM PERTENCE WHERE true;
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('013', 'iOASUuqwoe234', '12908312');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('005', 'kdjlASDhk1238', '12983729');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('014', 'ioASIUDJN21ad', '19028391');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('001', 'ALSKDJmxcz290', '26173612');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('006', 'jOIASJinZMX29', '26173612');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('007', 'kZXKCaksjQ203', '26173612');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('002', 'AISqowej1i290', '28319392');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('008', 'mAKSqwio128Ad', '28319392');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('005', 'UWYQi1231289A', '29382672');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('002', 'AShzxjnaAJq29', '29832726');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('008', 'MZNasmqwio120', '29832726');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('009', 'PoqweiUASj928', '29832726');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('010', 'ZKakjQqwui123', '29832726');
INSERT INTO PERTENCE (idUsuario, idItem, idEnvio)
VALUES ('013', 'aklASdjkh1289', '90281312');

SELECT
	*
FROM
	PERTENCE;
    
SELECT
	Servidor.nome,
    Servidor.idUsuario as idServidor,
    Dependente.nome,
    Dependente.idUsuario as idDependente
FROM
	Dependente, Servidor 
WHERE
	Servidor.matricula = Dependente.servidorMatricula;