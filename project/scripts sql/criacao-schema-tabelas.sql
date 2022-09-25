-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema pcas_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pcas_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pcas_db` ;
USE `pcas_db` ;

-- -----------------------------------------------------
-- Table `pcas_db`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pcas_db`.`Usuario` (
  `idUsuario` VARCHAR(38) NOT NULL,
  PRIMARY KEY (`idUsuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `pcas_db`.`Servidor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pcas_db`.`Servidor` (
  `nome` VARCHAR(60) NOT NULL,
  `matricula` VARCHAR(8) NOT NULL,
  `isAdmin` TINYINT NOT NULL,
  `avatar` VARCHAR(40) NULL,
  `idUsuario` VARCHAR(38) NOT NULL,
  PRIMARY KEY (`matricula`, `idUsuario`),
  UNIQUE INDEX `matricula` (`matricula` ASC) VISIBLE,
  INDEX `fk_Servidor_Usuario1_idx` (`idUsuario` ASC) VISIBLE,
  CONSTRAINT `fk_Servidor_Usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `pcas_db`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `pcas_db`.`Dependente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pcas_db`.`Dependente` (
  `codDependente` DECIMAL(6,0) NOT NULL,
  `parentesco` VARCHAR(20) NOT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `titulo` VARCHAR(12) NOT NULL,
  `email` VARCHAR(70) NOT NULL,
  `senha` VARCHAR(30) NULL,
  `ativo` TINYINT NOT NULL,
  `autorizado` TINYINT NOT NULL,
  `avatar` VARCHAR(40) NULL,
  `idUsuario` VARCHAR(38) NOT NULL,
  `servidorMatricula` VARCHAR(8) NOT NULL,
  `telefone` VARCHAR(12) NULL,
  PRIMARY KEY (`codDependente`, `idUsuario`, `servidorMatricula`),
  UNIQUE INDEX `codDependente` (`codDependente` ASC) VISIBLE,
  INDEX `fk_Dependente_Usuario1_idx` (`idUsuario` ASC) VISIBLE,
  INDEX `fk_Dependente_Servidor1_idx` (`servidorMatricula` ASC) VISIBLE,
  UNIQUE INDEX `titulo_UNIQUE` (`titulo` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_Dependente_Usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `pcas_db`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Dependente_Servidor1`
    FOREIGN KEY (`servidorMatricula`)
    REFERENCES `pcas_db`.`Servidor` (`matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `pcas_db`.`Envio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pcas_db`.`Envio` (
  `idEnvio` VARCHAR(38) NOT NULL,
  `mesRef` VARCHAR(10) NOT NULL,
  `anoRef` VARCHAR(4) NOT NULL,
  `tipoUsuario` VARCHAR(20) NOT NULL,
  `dataCriacao` DATETIME NOT NULL,
  `envioStatus` VARCHAR(20) NOT NULL,
  `idUsuario` VARCHAR(38) NOT NULL,
  PRIMARY KEY (`idEnvio`),
  INDEX `fk_Envio_Usuario1_idx` (`idUsuario` ASC) VISIBLE,
  CONSTRAINT `fk_Envio_Usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `pcas_db`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `pcas_db`.`Arquivo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pcas_db`.`Arquivo` (
  `idArquivo` VARCHAR(38) NOT NULL,
  `idEnvio` VARCHAR(38) NOT NULL,
  `data64` VARCHAR(40) NOT NULL,
  `tipoArquivo` VARCHAR(30) NOT NULL,
  `extensao` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`idArquivo`, `idEnvio`),
  INDEX `fk_Envio_arquivo_Envio1_idx` (`idEnvio` ASC) VISIBLE,
  UNIQUE INDEX `idArquivo_UNIQUE` (`idArquivo` ASC) VISIBLE,
  UNIQUE INDEX `data64_UNIQUE` (`data64` ASC) VISIBLE,
  CONSTRAINT `fk_Envio_arquivo_Envio1`
    FOREIGN KEY (`idEnvio`)
    REFERENCES `pcas_db`.`Envio` (`idEnvio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `pcas_db`.`ItemEnvio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pcas_db`.`ItemEnvio` (
  `idItem` VARCHAR(38) NOT NULL,
  `valorValidado` DECIMAL(6,2) NULL,
  `valorInformado` DECIMAL(6,2) NOT NULL,
  `idEnvio` VARCHAR(38) NOT NULL,
  PRIMARY KEY (`idItem`, `idEnvio`),
  INDEX `fk_ItemEnvio_Envio1_idx` (`idEnvio` ASC) VISIBLE,
  CONSTRAINT `fk_ItemEnvio_Envio1`
    FOREIGN KEY (`idEnvio`)
    REFERENCES `pcas_db`.`Envio` (`idEnvio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `pcas_db`.`PERTENCE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pcas_db`.`PERTENCE` (
  `idUsuario` VARCHAR(38) NOT NULL,
  `idItem` VARCHAR(38) NOT NULL,
  `idEnvio` VARCHAR(38) NOT NULL,
  PRIMARY KEY (`idUsuario`, `idItem`, `idEnvio`),
  INDEX `fk_PERTENCE_Usuario1_idx` (`idUsuario` ASC) VISIBLE,
  INDEX `fk_PERTENCE_ItemEnvio1_idx` (`idItem` ASC, `idEnvio` ASC) VISIBLE,
  CONSTRAINT `fk_PERTENCE_Usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `pcas_db`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PERTENCE_ItemEnvio1`
    FOREIGN KEY (`idItem` , `idEnvio`)
    REFERENCES `pcas_db`.`ItemEnvio` (`idItem` , `idEnvio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
