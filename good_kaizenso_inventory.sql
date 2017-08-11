CREATE DATABASE  IF NOT EXISTS `kaizenso_inventory` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `kaizenso_inventory`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: kaizenso_inventory
-- ------------------------------------------------------
-- Server version	5.7.15-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `adjustment`
--

DROP TABLE IF EXISTS `adjustment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adjustment` (
  `adjustment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 NOT NULL,
  `date` datetime NOT NULL,
  `observation` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`adjustment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adjustment`
--

LOCK TABLES `adjustment` WRITE;
/*!40000 ALTER TABLE `adjustment` DISABLE KEYS */;
INSERT INTO `adjustment` VALUES (24,'SA-000001','2017-06-25 16:55:50','','Completado'),(25,'SA-000123','2017-06-25 17:12:12','','Completado');
/*!40000 ALTER TABLE `adjustment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adjustment_item`
--

DROP TABLE IF EXISTS `adjustment_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adjustment_item` (
  `adjustment_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `new_quantity` int(11) DEFAULT NULL,
  `old_quantity` int(11) DEFAULT NULL,
  `total_quantity` int(11) DEFAULT NULL,
  `adjustment_id` bigint(20) DEFAULT NULL,
  `idx` int(11) DEFAULT NULL,
  `product_id` int(11) unsigned DEFAULT NULL,
  `store_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`adjustment_item_id`),
  KEY `FK_6w2as2elq7tugrac016rg67xf` (`product_id`),
  KEY `FK_osu5tw0xi5suq59xpwxak93sr` (`store_id`),
  KEY `FK_b2n8tyo3bbsx6d199f51a05dn` (`adjustment_id`),
  CONSTRAINT `FK_6w2as2elq7tugrac016rg67xf` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK_b2n8tyo3bbsx6d199f51a05dn` FOREIGN KEY (`adjustment_id`) REFERENCES `adjustment` (`adjustment_id`) ON DELETE CASCADE,
  CONSTRAINT `FK_osu5tw0xi5suq59xpwxak93sr` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adjustment_item`
--

LOCK TABLES `adjustment_item` WRITE;
/*!40000 ALTER TABLE `adjustment_item` DISABLE KEYS */;
INSERT INTO `adjustment_item` VALUES (121,100,0,100,24,0,24,1),(122,200,100,300,24,1,24,1),(123,20,0,20,25,0,27,1),(124,50,20,70,25,1,27,1),(125,80,70,150,25,2,27,1);
/*!40000 ALTER TABLE `adjustment_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `category_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `department_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (5,'Sales');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `employee_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `cell_phone` varchar(15) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `idx` int(11) DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  KEY `FK_DEPT` (`department_id`),
  CONSTRAINT `FK_DEPT` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Nina','Mayers','2017-06-20','111',5,0),(2,'Tony','Almeida','2017-06-20','222',5,1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `measurement`
--

DROP TABLE IF EXISTS `measurement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `measurement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `abbreviation` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `abbreviation_UNIQUE` (`abbreviation`),
  UNIQUE KEY `UK_fp4cx5r4rqx2cvp5a8dfnncwa` (`name`),
  UNIQUE KEY `UK_bb7ir692s6t6whurcp3u6sa11` (`abbreviation`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `measurement`
--

LOCK TABLES `measurement` WRITE;
/*!40000 ALTER TABLE `measurement` DISABLE KEYS */;
/*!40000 ALTER TABLE `measurement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `unitPrice` double NOT NULL,
  `brand` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `category_id` bigint(10) DEFAULT NULL,
  `supplier_id` int(11) DEFAULT NULL,
  `unitsInStock` int(11) DEFAULT NULL,
  `reorderLevel` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rlaghtegr0yx2c1q1s6nkqjlh` (`category_id`),
  KEY `FK_qenrc8t76jmkwmw1offteiwq` (`supplier_id`),
  CONSTRAINT `FK_qenrc8t76jmkwmw1offteiwq` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`),
  CONSTRAINT `FK_rlaghtegr0yx2c1q1s6nkqjlh` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (24,'somecode',NULL,'somename',10,NULL,NULL,NULL,NULL,NULL),(25,'Code 01',NULL,'Name 01',10,NULL,NULL,NULL,NULL,NULL),(26,'Code 01',NULL,'Name 01',10,NULL,NULL,NULL,NULL,NULL),(27,'Code 01',NULL,'Name 01',10,NULL,NULL,NULL,NULL,NULL),(28,'Code 01',NULL,'Name 01',10,NULL,NULL,NULL,NULL,NULL),(29,'Code 01',NULL,'Name 01',10,NULL,NULL,NULL,NULL,NULL),(30,'Code 01',NULL,'Name 01',10,NULL,NULL,NULL,NULL,NULL),(31,'Code 01',NULL,'Name 01',10,NULL,NULL,NULL,NULL,NULL),(32,'Code 01',NULL,'Name 01',10,NULL,NULL,NULL,NULL,NULL),(33,'Code 01',NULL,'Name 01',10,NULL,NULL,NULL,NULL,NULL),(34,'Code 01',NULL,'Name 01',10,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES (1,'Tienda Candle','10a Oriente'),(2,'Tienda Kaizen','11a Poneinte'),(3,'name','location'),(8,'somename','somelocation'),(10,'somecode','somename'),(11,'somename','somelocation'),(12,'somename','somelocation'),(13,'Name 01',''),(14,'Name 01',''),(15,'Name 01','Location 01'),(16,'Name 01','Location 01'),(17,'Name 01','Location 01'),(18,'Name 01','Location 01'),(19,'Name 01','Location 01'),(20,'Name 01','Location 01'),(21,'Name 01','Location 01'),(22,'Name 01','Location 01');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_product`
--

DROP TABLE IF EXISTS `store_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store_product` (
  `store_id` int(10) unsigned NOT NULL,
  `product_id` int(10) unsigned NOT NULL,
  `default_units` int(10) NOT NULL,
  `reorder_level` int(10) NOT NULL,
  `units` int(10) NOT NULL,
  PRIMARY KEY (`store_id`,`product_id`),
  KEY `FK_PRODUCT_ID` (`product_id`),
  CONSTRAINT `FK_PRODUCT_ID` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_SP_STORE_ID` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_product`
--

LOCK TABLES `store_product` WRITE;
/*!40000 ALTER TABLE `store_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `store_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `companyName` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `contactName` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `contactTitle` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `country` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transfer`
--

DROP TABLE IF EXISTS `transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transfer` (
  `id` int(10) unsigned NOT NULL,
  `code` varchar(255) NOT NULL,
  `product_id` int(11) unsigned NOT NULL,
  `quantity` bigint(20) DEFAULT NULL,
  `description` text,
  `from_store` int(11) unsigned NOT NULL,
  `to_store` int(11) unsigned NOT NULL,
  `initial_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`,`from_store`,`to_store`,`product_id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `UK_pvng2ahmu3ketx3y7xm2cbssc` (`code`),
  KEY `FK_PRODUCT_TRANSFER_ID_idx` (`product_id`),
  KEY `FK_FROMSTORE_TRANSFER_ID_idx` (`from_store`),
  KEY `FK_TOSTORE_TRANSFER_ID_idx` (`to_store`),
  CONSTRAINT `FK_FROMSTORE_TRANSFER_ID` FOREIGN KEY (`from_store`) REFERENCES `store` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRODUCT_TRANSFER_ID` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TOSTORE_TRANSFER_ID` FOREIGN KEY (`to_store`) REFERENCES `store` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transfer`
--

LOCK TABLES `transfer` WRITE;
/*!40000 ALTER TABLE `transfer` DISABLE KEYS */;
/*!40000 ALTER TABLE `transfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (29,'vaksfk','jaqart_','2017-05-27 20:32:30'),(30,'max','escobar','2017-05-28 18:47:50'),(31,'diego','hernandez','2017-05-29 15:29:43');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_store`
--

DROP TABLE IF EXISTS `user_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_store` (
  `user_id` int(10) unsigned NOT NULL,
  `store_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`store_id`),
  KEY `FK_STORE_ID_idx` (`store_id`),
  CONSTRAINT `FK_STORE_ID` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_store`
--

LOCK TABLES `user_store` WRITE;
/*!40000 ALTER TABLE `user_store` DISABLE KEYS */;
INSERT INTO `user_store` VALUES (29,1),(30,1),(31,1),(31,2);
/*!40000 ALTER TABLE `user_store` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-25 17:53:57
