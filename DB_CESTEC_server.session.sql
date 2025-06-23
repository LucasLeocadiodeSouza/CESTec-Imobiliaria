-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: CESTec_imobiliaria
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE CESTec_imobiliaria;

--
-- Table structure for table `banco`
--

DROP TABLE IF EXISTS `banco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banco` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `codigo` varchar(10) NOT NULL,
  `nome` varchar(60) NOT NULL,
  `criado_em` datetime NOT NULL,
  `atualizado_em` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banco`
--

LOCK TABLES `banco` WRITE;
/*!40000 ALTER TABLE `banco` DISABLE KEYS */;
INSERT INTO `banco` VALUES (1,'001','Banco do Brasil','2025-03-26 02:00:15','2025-03-26 02:00:15');
/*!40000 ALTER TABLE `banco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cargo`
--

DROP TABLE IF EXISTS `cargo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cargo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `datiregistro` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargo`
--

LOCK TABLES `cargo` WRITE;
/*!40000 ALTER TABLE `cargo` DISABLE KEYS */;
INSERT INTO `cargo` VALUES (1,'Diretor','2025-06-08'),(2,'Coordenador','2025-06-08'),(3,'Assistente Administrativo','2025-06-08'),(4,'Corretor','2025-06-08');
/*!40000 ALTER TABLE `cargo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conta`
--

DROP TABLE IF EXISTS `conta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conta` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `empresa_id` bigint unsigned NOT NULL,
  `agencia` varchar(10) NOT NULL,
  `conta` varchar(15) NOT NULL,
  `digito_agencia` varchar(1) DEFAULT NULL,
  `digito_conta` varchar(1) NOT NULL,
  `banco_id` bigint unsigned NOT NULL,
  `criado_em` datetime NOT NULL,
  `atualizado_em` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_conta_empresa1_idx` (`empresa_id`),
  KEY `fk_conta_banco1_idx` (`banco_id`),
  CONSTRAINT `fk_conta_banco1` FOREIGN KEY (`banco_id`) REFERENCES `banco` (`id`),
  CONSTRAINT `fk_conta_empresa1` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conta`
--

LOCK TABLES `conta` WRITE;
/*!40000 ALTER TABLE `conta` DISABLE KEYS */;
INSERT INTO `conta` VALUES (1,1,'452','123873','x','5',1,'2025-03-26 02:00:15','2025-03-26 02:00:15');
/*!40000 ALTER TABLE `conta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `convenio`
--

DROP TABLE IF EXISTS `convenio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `convenio` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `conta_id` bigint unsigned NOT NULL,
  `numero_contrato` varchar(30) NOT NULL,
  `carteira` varchar(5) NOT NULL,
  `variacao_carteira` varchar(5) DEFAULT NULL,
  `juros_porcentagem` decimal(10,2) DEFAULT NULL,
  `multa_porcentagem` decimal(10,2) DEFAULT NULL,
  `criado_em` datetime NOT NULL,
  `atualizado_em` datetime NOT NULL,
  `api_client_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_convenio_conta1_idx` (`conta_id`),
  CONSTRAINT `fk_convenio_conta1` FOREIGN KEY (`conta_id`) REFERENCES `conta` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `convenio`
--

LOCK TABLES `convenio` WRITE;
/*!40000 ALTER TABLE `convenio` DISABLE KEYS */;
INSERT INTO `convenio` VALUES (1,1,'3128557','17','35',1.00,2.00,'2025-03-26 02:00:15','2025-03-26 02:00:15','eyJpZCI6IjM5MyIsImNvZGlnb1B1YmxpY2Fkb3IiOjAsImNvZGlnb1NvZnR3YXJlIjoxMjk4OTUsInNlcXVlbmNpYWxJbnN0YWxhY2FvIjoxfQ');
/*!40000 ALTER TABLE `convenio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empresa` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `razao_social` varchar(70) NOT NULL,
  `cnpj` varchar(20) NOT NULL,
  `endereco_logradouro` varchar(70) NOT NULL,
  `endereco_numero` varchar(10) NOT NULL,
  `endereco_cidade` varchar(70) NOT NULL,
  `endereco_bairro` varchar(70) NOT NULL,
  `endereco_complemento` varchar(40) DEFAULT NULL,
  `endereco_uf` varchar(2) NOT NULL,
  `endereco_cep` varchar(20) NOT NULL,
  `criado_em` datetime NOT NULL,
  `atualizado_em` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'Rafael e Vitor Doces & Salgados Ltda','43347493000143','Av Dr Cândido Motta Filho','613','São Paulo','Vila São Francisco',NULL,'SP','05351-001','2025-03-26 02:00:15','2025-03-26 02:00:15');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fatura`
--

DROP TABLE IF EXISTS `fatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fatura` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `valor` decimal(10,2) NOT NULL,
  `data_vencimento` date DEFAULT NULL,
  `tipo` varchar(30) NOT NULL,
  `situacao` varchar(20) NOT NULL,
  `numero_documento` varchar(20) DEFAULT NULL,
  `nosso_numero` varchar(30) DEFAULT NULL,
  `tipo_pagamento` varchar(45) NOT NULL,
  `conta_id` bigint unsigned NOT NULL,
  `convenio_id` bigint unsigned NOT NULL,
  `pessoa_id` bigint unsigned NOT NULL,
  `criado_em` datetime NOT NULL,
  `atualizado_em` datetime NOT NULL,
  `pcp_cliente_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_fatura_conta1_idx` (`conta_id`),
  KEY `fk_fatura_convenio1_idx` (`convenio_id`),
  KEY `fk_fatura_pessoa1_idx` (`pessoa_id`),
  KEY `FK_FATURA_CLIENTE` (`pcp_cliente_id`),
  CONSTRAINT `FK_FATURA_CLIENTE` FOREIGN KEY (`pcp_cliente_id`) REFERENCES `pcp_cliente` (`codcliente`),
  CONSTRAINT `fk_fatura_conta1` FOREIGN KEY (`conta_id`) REFERENCES `conta` (`id`),
  CONSTRAINT `fk_fatura_convenio1` FOREIGN KEY (`convenio_id`) REFERENCES `convenio` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fatura`
--

LOCK TABLES `fatura` WRITE;
/*!40000 ALTER TABLE `fatura` DISABLE KEYS */;
INSERT INTO `fatura` VALUES (3,100.00,'2025-04-16','RECEITA','NAO_PAGA','71900','00031285570000071900','BOLETO',1,1,6,'2025-04-01 23:23:40','2025-04-01 23:23:40',NULL);
/*!40000 ALTER TABLE `fatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fatura_registrada`
--

DROP TABLE IF EXISTS `fatura_registrada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fatura_registrada` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `linha_digitavel` varchar(255) DEFAULT NULL,
  `qrcode_url` varchar(255) DEFAULT NULL,
  `qrcode_emv` varchar(255) DEFAULT NULL,
  `criado_em` datetime NOT NULL,
  `atualizado_em` datetime NOT NULL,
  `fatura_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_fatura_registrada_fatura1_idx` (`fatura_id`),
  CONSTRAINT `fk_fatura_registrada_fatura1` FOREIGN KEY (`fatura_id`) REFERENCES `fatura` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fatura_registrada`
--

LOCK TABLES `fatura_registrada` WRITE;
/*!40000 ALTER TABLE `fatura_registrada` DISABLE KEYS */;
/*!40000 ALTER TABLE `fatura_registrada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionario` (
  `codfuncionario` int NOT NULL AUTO_INCREMENT,
  `codsetor` int DEFAULT NULL,
  `salario` float DEFAULT NULL,
  `nome` varchar(35) DEFAULT NULL,
  `datinasc` date DEFAULT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `numtel` varchar(25) DEFAULT NULL,
  `endereco` varchar(80) DEFAULT NULL,
  `sp_user_id` int DEFAULT NULL,
  `cargo_id` int NOT NULL,
  PRIMARY KEY (`codfuncionario`),
  KEY `FK_USER_FUNCIONARIO` (`sp_user_id`) /*!80000 INVISIBLE */,
  KEY `FK_CARGO_FUNCIONARIO_idx` (`cargo_id`),
  CONSTRAINT `FK_USER_FUNCIONARIO` FOREIGN KEY (`sp_user_id`) REFERENCES `sp_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionario`
--

LOCK TABLES `funcionario` WRITE;
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
INSERT INTO `funcionario` VALUES (1,2,3500,'Lucas Leocadio','2025-10-05','13032891990','55997067494','brasil',1,1),(2,2,3000,'Daniel da silva','2025-12-21','1303258023','55997067494','maringa',2,3),(3,2,2500,'Sergio sacani','2025-08-21','2367258023','55997067494','Londrina',3,3);
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_aluguel`
--

DROP TABLE IF EXISTS `pcp_aluguel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_aluguel` (
  `codaluguel` int NOT NULL AUTO_INCREMENT,
  `codcliente` int DEFAULT NULL,
  `codimovel` int DEFAULT NULL,
  `codcorretor` int DEFAULT NULL,
  `datinicio` date DEFAULT NULL,
  `datfinal` date DEFAULT NULL,
  `valor` float DEFAULT NULL,
  `ativo` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`codaluguel`),
  KEY `FK_CORRETOR_ALUGUEL` (`codcorretor`),
  KEY `FK_CLIENTE_ALUGUEL` (`codcliente`),
  KEY `FK_IMOVEL_ALUGUEL` (`codimovel`),
  CONSTRAINT `FK_CLIENTE_ALUGUEL` FOREIGN KEY (`codcliente`) REFERENCES `pcp_cliente` (`codcliente`),
  CONSTRAINT `FK_CORRETOR_ALUGUEL` FOREIGN KEY (`codcorretor`) REFERENCES `pcp_corretor` (`codcorretor`),
  CONSTRAINT `FK_IMOVEL_ALUGUEL` FOREIGN KEY (`codimovel`) REFERENCES `pcp_imovel` (`codimovel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_aluguel`
--

LOCK TABLES `pcp_aluguel` WRITE;
/*!40000 ALTER TABLE `pcp_aluguel` DISABLE KEYS */;
/*!40000 ALTER TABLE `pcp_aluguel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_cliente`
--

DROP TABLE IF EXISTS `pcp_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_cliente` (
  `codcliente` int NOT NULL AUTO_INCREMENT,
  `numtel` varchar(25) DEFAULT NULL,
  `email` varchar(70) DEFAULT NULL,
  `documento` varchar(20) DEFAULT NULL,
  `endereco_logradouro` varchar(80) DEFAULT NULL,
  `endereco_numero` varchar(10) DEFAULT NULL,
  `endereco_cidade` varchar(70) DEFAULT NULL,
  `endereco_bairro` varchar(70) DEFAULT NULL,
  `endereco_complemento` varchar(40) DEFAULT NULL,
  `endereco_uf` varchar(2) DEFAULT NULL,
  `endereco_cep` varchar(20) DEFAULT NULL,
  `atualizado_em` datetime NOT NULL,
  `criado_em` datetime NOT NULL,
  `nome` varchar(80) NOT NULL,
  `pessoa_fisica` tinyint DEFAULT NULL,
  `id_usuario` varchar(7) DEFAULT NULL,
  PRIMARY KEY (`codcliente`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_cliente`
--

LOCK TABLES `pcp_cliente` WRITE;
/*!40000 ALTER TABLE `pcp_cliente` DISABLE KEYS */;
INSERT INTO `pcp_cliente` VALUES (6,'4499786432','estagioinvestindo@gmail.com','13032891990','Alameda da Alfazema','135','Araguaína','Loteamento Jardins Siena',NULL,'TO','77828-536','2025-06-07 15:48:51','2025-03-30 04:04:43','Cláudio Antonio Cauê Pereira',1,'LUCASSZ'),(7,'5544997067494','estagioinvestindo@gmail.com','96050176876','Joana dArc','613','Maringá','Vila São Francisco',NULL,'PR','87047-080','2025-06-07 19:36:50','2025-06-07 19:36:15','Rafael e Vitor Doces & Salgados Ltda',0,'LUCASSZ');
/*!40000 ALTER TABLE `pcp_cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_comissao`
--

DROP TABLE IF EXISTS `pcp_comissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_comissao` (
  `codcomissao` int NOT NULL AUTO_INCREMENT,
  `codcorretor` int DEFAULT NULL,
  `codvenda` int DEFAULT NULL,
  `percentual` decimal(2,1) DEFAULT NULL,
  `valor` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`codcomissao`),
  KEY `FK_CORRETOR_COMISSAO` (`codcorretor`),
  KEY `FK_VENDA_COMISSAO` (`codvenda`),
  CONSTRAINT `FK_CORRETOR_COMISSAO` FOREIGN KEY (`codcorretor`) REFERENCES `pcp_corretor` (`codcorretor`),
  CONSTRAINT `FK_VENDA_COMISSAO` FOREIGN KEY (`codvenda`) REFERENCES `pcp_venda` (`codvenda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_comissao`
--

LOCK TABLES `pcp_comissao` WRITE;
/*!40000 ALTER TABLE `pcp_comissao` DISABLE KEYS */;
/*!40000 ALTER TABLE `pcp_comissao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_cont_aprovacao`
--

DROP TABLE IF EXISTS `pcp_cont_aprovacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_cont_aprovacao` (
  `codcontaprov` int NOT NULL AUTO_INCREMENT,
  `pcp_contrato_codcontrato` int DEFAULT NULL,
  `valorLiberado` float DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `datregistro` date DEFAULT NULL,
  `ideusu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codcontaprov`),
  KEY `FK_CONTRATO_APROVA` (`pcp_contrato_codcontrato`),
  CONSTRAINT `FK_CONTRATO_APROVA` FOREIGN KEY (`pcp_contrato_codcontrato`) REFERENCES `pcp_contrato` (`codcontrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_cont_aprovacao`
--

LOCK TABLES `pcp_cont_aprovacao` WRITE;
/*!40000 ALTER TABLE `pcp_cont_aprovacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `pcp_cont_aprovacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_contrato`
--

DROP TABLE IF EXISTS `pcp_contrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_contrato` (
  `codcontrato` int NOT NULL AUTO_INCREMENT,
  `codcliente` int DEFAULT NULL,
  `codproprietario` int DEFAULT NULL,
  `codimovel` int DEFAULT NULL,
  `datinicio` date DEFAULT NULL,
  `datfinal` date DEFAULT NULL,
  `valor` float DEFAULT NULL,
  `situacao` int DEFAULT NULL,
  `valorliberado` float DEFAULT NULL,
  `codcorretor` int DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `datiregistro` date DEFAULT NULL,
  `ativo` tinyint(1) DEFAULT NULL,
  `ideusu` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`codcontrato`),
  KEY `FK_CORRETOR_CONTRATO` (`codcorretor`),
  KEY `FK_IMOVEL_CONTRATO` (`codimovel`),
  KEY `FK_CLIENTE_CONTRATO` (`codcliente`),
  KEY `FK_PROPRIETARIO_CONTRATO` (`codproprietario`),
  CONSTRAINT `FK_CLIENTE_CONTRATO` FOREIGN KEY (`codcliente`) REFERENCES `pcp_cliente` (`codcliente`),
  CONSTRAINT `FK_CORRETOR_CONTRATO` FOREIGN KEY (`codcorretor`) REFERENCES `pcp_corretor` (`codcorretor`),
  CONSTRAINT `FK_IMOVEL_CONTRATO` FOREIGN KEY (`codimovel`) REFERENCES `pcp_imovel` (`codimovel`),
  CONSTRAINT `FK_PROPRIETARIO_CONTRATO` FOREIGN KEY (`codproprietario`) REFERENCES `pcp_proprietario` (`codproprietario`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_contrato`
--

LOCK TABLES `pcp_contrato` WRITE;
/*!40000 ALTER TABLE `pcp_contrato` DISABLE KEYS */;
INSERT INTO `pcp_contrato` VALUES (23,6,13,14,'2025-05-31','2026-05-31',4500,2,4500,2,'Aprovado parabens!','2025-06-04',1,'LUCASSZ'),(24,7,14,15,'2025-06-01','2026-06-01',4000,2,0,2,NULL,'2025-06-08',1,'LUCASSZ');
/*!40000 ALTER TABLE `pcp_contrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_corretor`
--

DROP TABLE IF EXISTS `pcp_corretor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_corretor` (
  `codcorretor` int NOT NULL AUTO_INCREMENT,
  `email` varchar(70) DEFAULT NULL,
  `codfuncionario` int DEFAULT NULL,
  PRIMARY KEY (`codcorretor`),
  CONSTRAINT `FK_FUNCIONARIO_CORRETOR` FOREIGN KEY (`codcorretor`) REFERENCES `funcionario` (`codfuncionario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_corretor`
--

LOCK TABLES `pcp_corretor` WRITE;
/*!40000 ALTER TABLE `pcp_corretor` DISABLE KEYS */;
INSERT INTO `pcp_corretor` VALUES (2,'lucas.souza@cestec.com.br',1),(3,'daniel.silva@cestec.com.br',2);
/*!40000 ALTER TABLE `pcp_corretor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_imovel`
--

DROP TABLE IF EXISTS `pcp_imovel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_imovel` (
  `codimovel` int NOT NULL AUTO_INCREMENT,
  `codproprietario` int DEFAULT NULL,
  `tipo` int DEFAULT NULL,
  `endereco` varchar(120) DEFAULT NULL,
  `quartos` int DEFAULT NULL,
  `area` float DEFAULT NULL,
  `vlrcondominio` float DEFAULT NULL,
  `status` int DEFAULT NULL,
  `preco` float DEFAULT NULL,
  `negociacao` int DEFAULT NULL,
  `datinicontrato` date DEFAULT NULL,
  `datiregistro` date DEFAULT NULL,
  PRIMARY KEY (`codimovel`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_imovel`
--

LOCK TABLES `pcp_imovel` WRITE;
/*!40000 ALTER TABLE `pcp_imovel` DISABLE KEYS */;
INSERT INTO `pcp_imovel` VALUES (14,13,1,'Jardim das Americas',3,69,180,1,2017,1,'2025-05-31','2025-05-28'),(15,14,2,'Jardim das Americas 23',3,120,0,1,3000,1,'2025-06-15','2025-05-28');
/*!40000 ALTER TABLE `pcp_imovel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_meta`
--

DROP TABLE IF EXISTS `pcp_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_meta` (
  `codmeta` int NOT NULL AUTO_INCREMENT,
  `pcp_corretor_codcorretor` int DEFAULT NULL,
  `valor_meta` float DEFAULT NULL,
  `situacao` int DEFAULT NULL,
  `datinicio` date DEFAULT NULL,
  `datfinal` date DEFAULT NULL,
  `datregistro` date DEFAULT NULL,
  `ideusu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codmeta`),
  KEY `FK_CORRETOR_META` (`pcp_corretor_codcorretor`),
  CONSTRAINT `FK_CORRETOR_META` FOREIGN KEY (`pcp_corretor_codcorretor`) REFERENCES `pcp_corretor` (`codcorretor`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_meta`
--

LOCK TABLES `pcp_meta` WRITE;
/*!40000 ALTER TABLE `pcp_meta` DISABLE KEYS */;
INSERT INTO `pcp_meta` VALUES (3,2,25000,1,'2025-04-01','2025-04-29','2025-03-14','DANILSON'),(4,2,20000,1,'2025-05-01','2025-05-29','2025-03-14','DANILSON'),(5,3,18500,1,'2025-03-31','2025-04-29','2025-03-16','LUCASSZ'),(6,2,18500,1,'2025-02-28','2025-03-30','2025-03-16','LUCASSZ'),(7,3,1233,1,'2025-02-28','2025-03-30','2025-03-16','LUCASSZ'),(13,2,5000,1,'2025-05-31','2025-07-29','2025-06-08','LUCASSZ');
/*!40000 ALTER TABLE `pcp_meta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_pagamento`
--

DROP TABLE IF EXISTS `pcp_pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_pagamento` (
  `codpagamento` int NOT NULL AUTO_INCREMENT,
  `codaluguel` int DEFAULT NULL,
  `datipag` date DEFAULT NULL,
  `valor` decimal(7,2) DEFAULT NULL,
  `formapagamento` int DEFAULT NULL,
  PRIMARY KEY (`codpagamento`),
  KEY `FK_ALUGUEL_PAGAMENTO` (`codaluguel`),
  CONSTRAINT `FK_ALUGUEL_PAGAMENTO` FOREIGN KEY (`codaluguel`) REFERENCES `pcp_aluguel` (`codaluguel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_pagamento`
--

LOCK TABLES `pcp_pagamento` WRITE;
/*!40000 ALTER TABLE `pcp_pagamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `pcp_pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_pagamento_formapagamento`
--

DROP TABLE IF EXISTS `pcp_pagamento_formapagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_pagamento_formapagamento` (
  `codformapagamento` int NOT NULL,
  `codpagamento` int NOT NULL,
  `datiregistro` date DEFAULT NULL,
  PRIMARY KEY (`codformapagamento`,`codpagamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_pagamento_formapagamento`
--

LOCK TABLES `pcp_pagamento_formapagamento` WRITE;
/*!40000 ALTER TABLE `pcp_pagamento_formapagamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `pcp_pagamento_formapagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_proprietario`
--

DROP TABLE IF EXISTS `pcp_proprietario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_proprietario` (
  `codproprietario` int NOT NULL AUTO_INCREMENT,
  `numtel` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(80) NOT NULL,
  `documento` varchar(20) DEFAULT NULL,
  `endereco_logradouro` varchar(80) DEFAULT NULL,
  `endereco_numero` varchar(10) DEFAULT NULL,
  `endereco_cidade` varchar(70) DEFAULT NULL,
  `endereco_bairro` varchar(70) DEFAULT NULL,
  `endereco_complemento` varchar(40) DEFAULT NULL,
  `endereco_uf` varchar(2) DEFAULT NULL,
  `endereco_cep` varchar(20) DEFAULT NULL,
  `atualizado_em` datetime NOT NULL,
  `criado_em` datetime NOT NULL,
  `pessoa_fisica` tinyint DEFAULT NULL,
  `id_usuario` varchar(7) DEFAULT NULL,
  PRIMARY KEY (`codproprietario`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_proprietario`
--

LOCK TABLES `pcp_proprietario` WRITE;
/*!40000 ALTER TABLE `pcp_proprietario` DISABLE KEYS */;
INSERT INTO `pcp_proprietario` VALUES (13,'44865359172','vanildullll@gmail.com','Rafael e Vitor Doces & Salgados Ltda','43347493000143','Alameda da Alfazema','613','São Paulo','Vila São Francisco',NULL,'SP','87047-080','2025-03-30 04:15:22','2025-03-30 04:15:22',0,'LUCASSZ'),(14,'44997067493','estagioinvestindo@gmail.com','Pedro Henrique de Souza','13037522030','Logradouro 24 da rua 89','64','Maringá','Loteamento Jardins Siena',NULL,'PR','87048-080','2025-06-08 17:40:46','2025-04-03 22:15:57',0,'LUCASSZ');
/*!40000 ALTER TABLE `pcp_proprietario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_setor`
--

DROP TABLE IF EXISTS `pcp_setor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_setor` (
  `codsetor` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`codsetor`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_setor`
--

LOCK TABLES `pcp_setor` WRITE;
/*!40000 ALTER TABLE `pcp_setor` DISABLE KEYS */;
INSERT INTO `pcp_setor` VALUES (1,'FINANCEIRO'),(2,'COMERCIAL'),(3,'RECURSOS HUMANOS');
/*!40000 ALTER TABLE `pcp_setor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcp_venda`
--

DROP TABLE IF EXISTS `pcp_venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcp_venda` (
  `codvenda` int NOT NULL AUTO_INCREMENT,
  `codimovel` int DEFAULT NULL,
  `codcliente` int DEFAULT NULL,
  `codcorretor` int DEFAULT NULL,
  `datvenda` date DEFAULT NULL,
  `valor` float DEFAULT NULL,
  `ativo` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`codvenda`),
  KEY `FK_CLIENTE_VENDAS` (`codcliente`),
  KEY `FK_CORRETOR_VENDAS` (`codcorretor`),
  KEY `FK_IMOVEL_VENDAS` (`codimovel`),
  CONSTRAINT `FK_CLIENTE_VENDAS` FOREIGN KEY (`codcliente`) REFERENCES `pcp_cliente` (`codcliente`),
  CONSTRAINT `FK_CORRETOR_VENDAS` FOREIGN KEY (`codcorretor`) REFERENCES `pcp_corretor` (`codcorretor`),
  CONSTRAINT `FK_IMOVEL_VENDAS` FOREIGN KEY (`codimovel`) REFERENCES `pcp_imovel` (`codimovel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcp_venda`
--

LOCK TABLES `pcp_venda` WRITE;
/*!40000 ALTER TABLE `pcp_venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `pcp_venda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_aplicacoes`
--

DROP TABLE IF EXISTS `sp_aplicacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sp_aplicacoes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idmodulos` int DEFAULT NULL,
  `role` int DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `arquivo_inic` varchar(45) NOT NULL,
  `datregistro` date DEFAULT NULL,
  `ideusu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_APLICACOES_MODULOS` (`idmodulos`),
  KEY `FK_APLICACOES_ROLEACESS_idx` (`role`),
  CONSTRAINT `FK_APLICACOES_MODULOS` FOREIGN KEY (`idmodulos`) REFERENCES `sp_modulos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_aplicacoes`
--

LOCK TABLES `sp_aplicacoes` WRITE;
/*!40000 ALTER TABLE `sp_aplicacoes` DISABLE KEYS */;
INSERT INTO `sp_aplicacoes` VALUES (1,1,1,'Liberar Aplicação','wmrb001.html','2025-06-16','LUCASSZ'),(2,1,1,'Consulta de Tabelas','wmrb002.html','2025-06-16','LUCASSZ'),(3,2,1,'Cadastro de Contratos','wcri004.html','2025-06-16','LUCASSZ'),(4,2,1,'Cadastro de Imóvel','wcri001.html','2025-06-16','LUCASSZ'),(6,2,1,'Cadastro de Proprietario','wcri002.html','2025-06-16','LUCASSZ'),(7,2,1,'Cadastro de Cliente','wcri003.html','2025-06-20','LUCASSZ'),(8,2,1,'Aprovação','wcri005.html','2025-06-16','LUCASSZ'),(9,2,1,'Simular Financiamento','...','2025-06-16','LUCASSZ'),(10,3,1,'Cadastro de Metas','wgdc001.html','2025-06-16','LUCASSZ'),(11,3,1,'Cadastrar Corretor','wgdc002.html','2025-06-16','LUCASSZ'),(12,4,1,'Consultar Crédito','wpga001.html','2025-06-16','LUCASSZ'),(13,5,1,'Relatório do Imóvel','...','2025-06-16','LUCASSZ'),(14,1,1,'Cadastrar Usuário','wmrb003.html','2025-06-16','LUCASSZ'),(19,2,1,'Assinaturas','...','2025-06-16','LUCASSZ');
/*!40000 ALTER TABLE `sp_aplicacoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_libacesso`
--

DROP TABLE IF EXISTS `sp_libacesso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sp_libacesso` (
  `idusu` int NOT NULL,
  `role` int NOT NULL,
  `datregistro` date DEFAULT NULL,
  `ideusu` varchar(255) DEFAULT NULL,
  `datvenc` date DEFAULT NULL,
  PRIMARY KEY (`idusu`,`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_libacesso`
--

LOCK TABLES `sp_libacesso` WRITE;
/*!40000 ALTER TABLE `sp_libacesso` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_libacesso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_modulos`
--

DROP TABLE IF EXISTS `sp_modulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sp_modulos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `ind` varchar(7) DEFAULT NULL,
  `datregistro` date DEFAULT NULL,
  `ideusu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_modulos`
--

LOCK TABLES `sp_modulos` WRITE;
/*!40000 ALTER TABLE `sp_modulos` DISABLE KEYS */;
INSERT INTO `sp_modulos` VALUES (1,'Menu','mrb','2025-06-16','LUCASSZ'),(2,'Contratos','cri','2025-06-16','LUCASSZ'),(3,'Corretores','gdc','2025-06-16','LUCASSZ'),(4,'Pagamentos','pga','2025-06-16','LUCASSZ'),(5,'Relatório','rel','2025-06-16','LUCASSZ');
/*!40000 ALTER TABLE `sp_modulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_roleacess`
--

DROP TABLE IF EXISTS `sp_roleacess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sp_roleacess` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `datregistro` date DEFAULT NULL,
  `ideusu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_roleacess`
--

LOCK TABLES `sp_roleacess` WRITE;
/*!40000 ALTER TABLE `sp_roleacess` DISABLE KEYS */;
INSERT INTO `sp_roleacess` VALUES (1,'Aberta','2025-06-19','LUCASSZ'),(2,'Analístas TI','2025-06-19','LUCASSZ'),(3,'Gerente Unidade','2025-06-19','LUCASSZ'),(4,'Gerente Regional','2025-06-19','LUCASSZ');
/*!40000 ALTER TABLE `sp_roleacess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_user`
--

DROP TABLE IF EXISTS `sp_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sp_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(255) DEFAULT NULL,
  `passkey` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_user`
--

LOCK TABLES `sp_user` WRITE;
/*!40000 ALTER TABLE `sp_user` DISABLE KEYS */;
INSERT INTO `sp_user` VALUES (1,'LUCASSZ','$2a$10$7YcewEVhB49p4ebs0GXcM.KXP11/lJOdtuwWTovpr//uXT1ddCAU.','DIREC'),(2,'GABRIEL','$2a$10$tHBcGupBnzrKK6Qn2nbPxOPZ4cyicJwXFklNRlKModpsNT14xzFQm','USER'),(3,'SERGIO','$2a$10$JRG4c/voeRJbO9K12fhSIu2MTIx8pk/zmj4axoqAyGzN1zcymQ39G','ADMIN');
/*!40000 ALTER TABLE `sp_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-23 19:49:32
