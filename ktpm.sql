-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: 127.0.0.1    Database: ktpm
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chuyenbay`
--

DROP TABLE IF EXISTS `chuyenbay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chuyenbay` (
  `id_chuyenbay` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `arrive_id` int DEFAULT NULL,
  `depart_id` int DEFAULT NULL,
  `daytime` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `timeflight` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_chuyenbay`),
  KEY `chuyenbay_ibfk_1_idx` (`arrive_id`),
  KEY `chuyenbay_ibfk_2_idx` (`depart_id`),
  CONSTRAINT `chuyenbay_ibfk_1` FOREIGN KEY (`arrive_id`) REFERENCES `sanbay` (`id_sanbay`),
  CONSTRAINT `chuyenbay_ibfk_2` FOREIGN KEY (`depart_id`) REFERENCES `sanbay` (`id_sanbay`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chuyenbay`
--

LOCK TABLES `chuyenbay` WRITE;
/*!40000 ALTER TABLE `chuyenbay` DISABLE KEYS */;
INSERT INTO `chuyenbay` VALUES (1,'A01',1,2,'01 Nov - 16:50','1h 0m'),(2,'A02',3,4,'15 Dec - 11:15','2h 20m'),(3,'A03',5,6,'21 Mar - 20:35','3h 15m'),(4,'A04',7,8,'21 Feb - 16:50','3h 25m'),(5,'A05',9,10,'25 Sep - 22:00','10h 00m'),(6,'A06',1,3,'08 Mar - 17:00','5h 15m'),(7,'A07',11,12,'18 Apr - 07:15','43h 25m'),(8,'A08',13,3,'20 Apr - 16:30','2h 55m'),(9,'A09',6,8,'21 Nov - 13:00','5h 00m'),(10,'A10',9,3,'04 Jan - 01:15','4h 20m');
/*!40000 ALTER TABLE `chuyenbay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ghe`
--

DROP TABLE IF EXISTS `ghe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ghe` (
  `id_ghe` int NOT NULL AUTO_INCREMENT,
  `loai` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `gia` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `soluong` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `chuyenbay_id` int DEFAULT NULL,
  PRIMARY KEY (`id_ghe`),
  KEY `ghe_ibfk_1_idx` (`chuyenbay_id`),
  CONSTRAINT `ghe_ibfk_1` FOREIGN KEY (`chuyenbay_id`) REFERENCES `chuyenbay` (`id_chuyenbay`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ghe`
--

LOCK TABLES `ghe` WRITE;
/*!40000 ALTER TABLE `ghe` DISABLE KEYS */;
INSERT INTO `ghe` VALUES (1,'1','10','1000000',1),(2,'2','40','615000',1),(3,'1','10','7000000',2),(4,'2','40','5000000',2),(5,'1','10','20000000',3),(6,'2','40','15000000',3),(7,'1','10','25000000',4),(8,'2','40','21000000',4),(9,'1','10','22000000',5),(10,'2','40','17000000',5);
/*!40000 ALTER TABLE `ghe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khachhang`
--

DROP TABLE IF EXISTS `khachhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khachhang` (
  `id_khachhang` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cmnd` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nganhang_id` int DEFAULT NULL,
  `password` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `stk_id` int DEFAULT NULL,
  PRIMARY KEY (`id_khachhang`),
  KEY `khachhang_ibfk_1_idx` (`nganhang_id`),
  KEY `khachhang_ibfk_2_idx` (`stk_id`),
  CONSTRAINT `khachhang_ibfk_1` FOREIGN KEY (`nganhang_id`) REFERENCES `nganhang` (`id_nganhang`),
  CONSTRAINT `khachhang_ibfk_2` FOREIGN KEY (`stk_id`) REFERENCES `taikhoan` (`id_taikhoan`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khachhang`
--

LOCK TABLES `khachhang` WRITE;
/*!40000 ALTER TABLE `khachhang` DISABLE KEYS */;
INSERT INTO `khachhang` VALUES (1,'admin','123456',3,'admin',1),(3,'truong','654321',2,'123',2);
/*!40000 ALTER TABLE `khachhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nganhang`
--

DROP TABLE IF EXISTS `nganhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nganhang` (
  `id_nganhang` int NOT NULL AUTO_INCREMENT,
  `ten` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_nganhang`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nganhang`
--

LOCK TABLES `nganhang` WRITE;
/*!40000 ALTER TABLE `nganhang` DISABLE KEYS */;
INSERT INTO `nganhang` VALUES (1,'ACB, Ngân hàng Á châu'),(2,'BIDV Ngân hàng Đầu tư và Phát triển Việt Nam'),(3,'DongA Bank, Ngân hàng thương mại cổ phần Đông Á'),(4,'VBB, Ngân hàng Việt Nam Thương Tín	'),(5,'Sacombank, Ngân hàng Sài Gòn Thương Tín'),(6,'NAB, Ngân hàng thương mại cổ phần Nam Á'),(7,'TPBank, Ngân hàng Tiên Phong'),(8,'Techcombank, Ngân hàng Kỹ Thương Việt Nam'),(9,'Vietcombank, Ngân hàng Ngoại thương Việt Nam'),(10,'BIDV, Ngân hàng Đầu tư và Phát triển Việt Nam');
/*!40000 ALTER TABLE `nganhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sanbay`
--

DROP TABLE IF EXISTS `sanbay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanbay` (
  `id_sanbay` int NOT NULL AUTO_INCREMENT,
  `sanbay` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_sanbay`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sanbay`
--

LOCK TABLES `sanbay` WRITE;
/*!40000 ALTER TABLE `sanbay` DISABLE KEYS */;
INSERT INTO `sanbay` VALUES (1,'TP HCM (SGN)'),(2,'Hà nội (HAN)'),(3,'Seoul (ICN)'),(4,'Tokyo (TYOA)'),(5,'Hồng Kông (HKG)'),(6,'Paris (PARA)'),(7,'Venice (VCE)'),(8,'Maldives (MLE)'),(9,'Sydney (SYD)'),(10,'London (LONA)'),(11,'Washington (WASA)'),(12,'Taipei (TAIA)'),(13,'Mexico City (MEX)');
/*!40000 ALTER TABLE `sanbay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taikhoan`
--

DROP TABLE IF EXISTS `taikhoan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taikhoan` (
  `id_taikhoan` int NOT NULL AUTO_INCREMENT,
  `stk` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `money` decimal(65,0) DEFAULT NULL,
  PRIMARY KEY (`id_taikhoan`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taikhoan`
--

LOCK TABLES `taikhoan` WRITE;
/*!40000 ALTER TABLE `taikhoan` DISABLE KEYS */;
INSERT INTO `taikhoan` VALUES (1,'11223344',1000000000),(2,'159211120',NULL);
/*!40000 ALTER TABLE `taikhoan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vechuyenbay`
--

DROP TABLE IF EXISTS `vechuyenbay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vechuyenbay` (
  `id_vechuyenbay` int NOT NULL AUTO_INCREMENT,
  `chuyenbay_id` int NOT NULL,
  `khachhang_id` int DEFAULT NULL,
  `ghe_id` int DEFAULT NULL,
  PRIMARY KEY (`id_vechuyenbay`),
  KEY `vechuyenbay_ibfk_1_idx` (`chuyenbay_id`),
  KEY `vechuyenbay_ibfk_2_idx` (`ghe_id`),
  KEY `vechuyenbay_ibfk_1_idx1` (`khachhang_id`),
  CONSTRAINT `vechuyenbay_ibfk_1` FOREIGN KEY (`chuyenbay_id`) REFERENCES `chuyenbay` (`id_chuyenbay`),
  CONSTRAINT `vechuyenbay_ibfk_2` FOREIGN KEY (`ghe_id`) REFERENCES `ghe` (`id_ghe`),
  CONSTRAINT `vechuyenbay_ibfk_3` FOREIGN KEY (`khachhang_id`) REFERENCES `khachhang` (`id_khachhang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vechuyenbay`
--

LOCK TABLES `vechuyenbay` WRITE;
/*!40000 ALTER TABLE `vechuyenbay` DISABLE KEYS */;
/*!40000 ALTER TABLE `vechuyenbay` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-17  2:26:07
