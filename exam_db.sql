CREATE DATABASE  IF NOT EXISTS `exam` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `exam`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: exam
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_correct` bit(1) NOT NULL,
  `media` json DEFAULT NULL,
  `question` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK54dobrdq2u51m4u8s7kg0as8v` (`question`),
  CONSTRAINT `FK54dobrdq2u51m4u8s7kg0as8v` FOREIGN KEY (`question`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (5,'3','2024-09-24 03:28:12',_binary '',NULL,2),(6,'4','2024-09-24 03:28:12',_binary '\0',NULL,2),(7,'5','2024-09-24 03:28:12',_binary '\0',NULL,2),(8,'2','2024-09-24 03:28:12',_binary '\0',NULL,2),(9,'4','2024-10-18 03:56:09',_binary '\0',NULL,1),(10,'1','2024-10-18 03:56:09',_binary '\0',NULL,1),(11,'3','2024-10-18 03:56:09',_binary '\0',NULL,1),(12,'15','2024-10-18 03:56:09',_binary '',NULL,1),(13,'1','2024-10-30 08:03:18',_binary '\0',NULL,3),(14,'2','2024-10-30 08:03:18',_binary '\0',NULL,3),(15,'6','2024-10-30 08:03:18',_binary '\0',NULL,3),(16,'5','2024-10-30 08:03:18',_binary '',NULL,3),(17,'1','2024-10-30 08:03:22',_binary '\0',NULL,4),(18,'2','2024-10-30 08:03:22',_binary '\0',NULL,4),(19,'6','2024-10-30 08:03:22',_binary '\0',NULL,4),(20,'5','2024-10-30 08:03:22',_binary '',NULL,4),(21,'3','2024-10-30 08:04:06',_binary '\0',NULL,5),(22,'5','2024-10-30 08:04:06',_binary '\0',NULL,5),(23,'7','2024-10-30 08:04:06',_binary '',NULL,5),(24,'9','2024-10-30 08:04:06',_binary '\0',NULL,5),(25,'10','2024-10-30 08:04:50',_binary '',NULL,6),(26,'12','2024-10-30 08:04:50',_binary '\0',NULL,6),(27,'14','2024-10-30 08:04:50',_binary '\0',NULL,6),(28,'15','2024-10-30 08:04:50',_binary '\0',NULL,6),(29,'125','2024-10-30 08:05:36',_binary '\0',NULL,7),(30,'60','2024-10-30 08:05:36',_binary '',NULL,7),(31,'234','2024-10-30 08:05:36',_binary '\0',NULL,7),(32,'453','2024-10-30 08:05:36',_binary '\0',NULL,7),(33,'113','2024-10-30 08:07:59',_binary '',NULL,8),(34,'114','2024-10-30 08:07:59',_binary '\0',NULL,8),(35,'115','2024-10-30 08:07:59',_binary '\0',NULL,8),(36,'116','2024-10-30 08:07:59',_binary '\0',NULL,8),(37,'3123','2024-10-30 08:08:45',_binary '\0',NULL,9),(38,'355','2024-10-30 08:08:45',_binary '\0',NULL,9),(39,'233','2024-10-30 08:08:45',_binary '',NULL,9),(40,'764','2024-10-30 08:08:45',_binary '\0',NULL,9),(41,'101','2024-10-30 08:09:37',_binary '\0',NULL,10),(42,'102','2024-10-30 08:09:37',_binary '\0',NULL,10),(43,'100','2024-10-30 08:09:37',_binary '',NULL,10),(44,'103','2024-10-30 08:09:37',_binary '\0',NULL,10),(45,'104','2024-10-30 08:10:07',_binary '\0',NULL,11),(46,'103','2024-10-30 08:10:07',_binary '\0',NULL,11),(47,'102','2024-10-30 08:10:07',_binary '',NULL,11),(48,'101','2024-10-30 08:10:07',_binary '\0',NULL,11),(49,'10','2024-10-30 11:42:07',_binary '',NULL,12),(50,'11','2024-10-30 11:42:07',_binary '\0',NULL,12),(51,'21','2024-10-30 11:42:07',_binary '\0',NULL,12),(52,'14','2024-10-30 11:42:07',_binary '\0',NULL,12),(53,'33','2025-04-19 09:37:50',_binary '',NULL,13),(54,'11','2025-04-19 09:37:50',_binary '\0',NULL,13),(55,'22','2025-04-19 09:37:50',_binary '\0',NULL,13),(56,'44','2025-04-19 09:37:50',_binary '\0',NULL,13);
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `status` enum('Active','Deleted') DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlggcffvsuyxh30eeunpfsw6gg` (`create_by`),
  CONSTRAINT `FKlggcffvsuyxh30eeunpfsw6gg` FOREIGN KEY (`create_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'2024-09-10 03:15:57','Description Toán 12','Active','Toán 12','c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(2,'2024-09-24 03:24:06','Test 1','Active','Toán 1','c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(3,'2024-09-24 03:24:18','test 2','Active','Toán 2','c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(4,'2024-09-24 03:24:28','Test 3','Active','Toán 3','c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(5,'2024-09-24 03:24:44','Test 5\n','Active','Toán 5','c6c05b6f-5f84-4841-b052-438b5a8fb7ef');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forgot_password`
--

DROP TABLE IF EXISTS `forgot_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forgot_password` (
  `fpid` int NOT NULL AUTO_INCREMENT,
  `expiration_time` datetime(6) NOT NULL,
  `otp` int NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`fpid`),
  UNIQUE KEY `UK_ss96nm4ed1jmllpxib14p1r7v` (`user_id`),
  CONSTRAINT `FKjfa13lhndn1q66kheuyjk2i5l` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forgot_password`
--

LOCK TABLES `forgot_password` WRITE;
/*!40000 ALTER TABLE `forgot_password` DISABLE KEYS */;
INSERT INTO `forgot_password` VALUES (60,'2024-12-13 22:42:02.449681',622073,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef');
/*!40000 ALTER TABLE `forgot_password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `media` json DEFAULT NULL,
  `question_type` tinyint DEFAULT NULL,
  `status` enum('Active','Deleted') DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKctl6tuf74n8cufkb3ulj6b3fc` (`category_id`),
  CONSTRAINT `FKctl6tuf74n8cufkb3ulj6b3fc` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `questions_chk_1` CHECK ((`question_type` between 0 and 1))
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'<p>1 + 14 = ?</p>','2024-09-24 03:27:38','null',1,'Active',2),(2,'<p>1 + 2 = ?</p>','2024-09-24 03:28:12',NULL,1,'Active',2),(3,'<p>1+4 = ?</p>','2024-10-30 08:03:17',NULL,1,'Active',2),(4,'<p>1+4 = ?</p>','2024-10-30 08:03:21',NULL,1,'Deleted',2),(5,'<p>3 + 4 =?</p>','2024-10-30 08:04:06',NULL,0,'Active',3),(6,'<p>3 + 7 =?</p>','2024-10-30 08:04:50',NULL,1,'Active',3),(7,'<p>5 x 12 = ? </p>','2024-10-30 08:05:36',NULL,1,'Active',5),(8,'<p>3 + 110</p>','2024-10-30 08:07:59',NULL,1,'Active',4),(9,'<p><strong>123 + 110 =? </strong></p>','2024-10-30 08:08:45',NULL,1,'Active',4),(10,'<p>99 + 1 = ?</p>','2024-10-30 08:09:37',NULL,1,'Active',3),(11,'<p><strong>99 + 3 = ?</strong></p>','2024-10-30 08:10:07',NULL,1,'Active',4),(12,'<p>1 + 9 = ?</p>','2024-10-30 11:42:07',NULL,1,'Active',3),(13,'<p>99 : 3 = ?</p>','2025-04-19 09:37:50',NULL,1,'Active',5);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_questions`
--

DROP TABLE IF EXISTS `quiz_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_questions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `marks_of_question` int DEFAULT NULL,
  `question_id` bigint DEFAULT NULL,
  `quiz_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKev41c723fx659v28pjycox15o` (`question_id`),
  KEY `FKanfmgf6ksbdnv7ojb0pfve54q` (`quiz_id`),
  CONSTRAINT `FKanfmgf6ksbdnv7ojb0pfve54q` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`id`),
  CONSTRAINT `FKev41c723fx659v28pjycox15o` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_questions`
--

LOCK TABLES `quiz_questions` WRITE;
/*!40000 ALTER TABLE `quiz_questions` DISABLE KEYS */;
INSERT INTO `quiz_questions` VALUES (1,5,1,1),(2,5,2,1),(3,5,1,2),(4,5,2,2),(5,5,1,3),(6,5,2,3),(7,6,1,4),(8,4,2,4),(9,10,1,5),(10,2,5,6),(11,3,6,6),(12,2,10,6),(13,3,12,6),(14,5,8,7),(15,5,9,7),(16,5,7,8),(17,5,9,8),(18,3,1,9),(19,3,2,9),(20,4,3,9),(21,5,2,10),(22,5,6,10),(23,5,8,10),(24,5,8,11),(25,5,9,11),(26,5,3,11),(27,1,3,12),(28,1,1,12),(29,1,6,12),(30,1,8,12),(31,1,9,12),(32,1,11,12),(33,2,10,12),(34,2,5,12),(35,5,1,13),(36,5,2,13),(37,5,1,14),(38,5,2,14);
/*!40000 ALTER TABLE `quiz_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizzes`
--

DROP TABLE IF EXISTS `quizzes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizzes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text,
  `duration_minutes` int DEFAULT NULL,
  `max_marks` int NOT NULL,
  `number_of_questions` int NOT NULL,
  `status` enum('Active','Deleted') DEFAULT NULL,
  `title` text NOT NULL,
  `category_id` bigint DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpo9fnqd9hnnmg8qxiyue40cot` (`category_id`),
  KEY `FK86c55rtpocqab8y1rimtd8a3p` (`create_by`),
  CONSTRAINT `FK86c55rtpocqab8y1rimtd8a3p` FOREIGN KEY (`create_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKpo9fnqd9hnnmg8qxiyue40cot` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizzes`
--

LOCK TABLES `quizzes` WRITE;
/*!40000 ALTER TABLE `quizzes` DISABLE KEYS */;
INSERT INTO `quizzes` VALUES (1,'2024-09-24 03:29:42','Ôn thi toán lớp 1',5,10,2,'Active','Ôn Thi toán 1',2,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(2,'2024-09-24 03:40:03','Làm nhanh về sớm',15,10,2,'Active','Bài thi toán 1',2,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(3,'2024-09-24 07:23:57','On thi',10,0,2,'Active','Toán 111',2,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(4,'2024-09-24 07:26:17','10',10,0,2,'Deleted','1',2,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(5,'2024-09-24 07:54:12','123213213213',12,0,1,'Deleted','123',2,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(6,'2024-10-30 15:00:24','NO DESCRIPTION',10,10,4,'Active','BÀI TẬP 1',3,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(7,'2024-10-30 15:01:03','111',10,10,2,'Active','BÀI TẬP 2',4,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(8,'2024-10-30 15:02:08','10',10,10,2,'Active','BÀI TẬP 4',5,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(9,'2024-10-30 15:03:19','100',10,10,3,'Active','BÀI TẬP 6',2,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(10,'2024-10-30 15:10:34','3',3,15,3,'Active','2',2,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(11,'2024-10-30 15:11:27','gdđ',34,15,3,'Active','ffff',4,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(12,'2025-04-19 08:59:52','Khó nha quý dị',30,10,8,'Active','Toán Cao Cấp 1',1,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(13,'2025-04-19 09:03:14','ez',10,10,2,'Active','Toán lớp 1 ha',2,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef'),(14,'2025-04-19 09:03:29','ez',10,10,2,'Active','Toán lớp 1 ha',2,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef');
/*!40000 ALTER TABLE `quizzes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_question_result`
--

DROP TABLE IF EXISTS `user_question_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_question_result` (
  `id` varchar(255) NOT NULL,
  `answers` json DEFAULT NULL,
  `generate_question` json DEFAULT NULL,
  `is_done` bit(1) NOT NULL,
  `mark_of_question` int NOT NULL,
  `question` json DEFAULT NULL,
  `result` bit(1) NOT NULL,
  `user_quiz_result_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4y57hdqppq22neh4bhfryil6m` (`user_quiz_result_id`),
  CONSTRAINT `FK4y57hdqppq22neh4bhfryil6m` FOREIGN KEY (`user_quiz_result_id`) REFERENCES `user_quiz_results` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_question_result`
--

LOCK TABLES `user_question_result` WRITE;
/*!40000 ALTER TABLE `user_question_result` DISABLE KEYS */;
INSERT INTO `user_question_result` VALUES ('02f86685-1850-43c0-85c8-678b75fd4a08','{\"answers\": [{\"id\": 49, \"media\": null, \"select\": false, \"content\": \"10\"}, {\"id\": 50, \"media\": null, \"select\": false, \"content\": \"11\"}, {\"id\": 51, \"media\": null, \"select\": true, \"content\": \"21\"}, {\"id\": 52, \"media\": null, \"select\": false, \"content\": \"14\"}]}','{\"id\": 12, \"answers\": [{\"id\": 51, \"media\": null, \"content\": \"21\", \"correct\": false, \"createdAt\": 1730288527000}, {\"id\": 49, \"media\": null, \"content\": \"10\", \"correct\": true, \"createdAt\": 1730288527000}, {\"id\": 52, \"media\": null, \"content\": \"14\", \"correct\": false, \"createdAt\": 1730288527000}, {\"id\": 50, \"media\": null, \"content\": \"11\", \"correct\": false, \"createdAt\": 1730288527000}], \"content\": \"<p>1 + 9 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730288527000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',3,'{\"id\": 12, \"media\": null, \"content\": \"<p>1 + 9 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730288527000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '\0',12),('06e7282d-fc79-43b2-a315-1a307945eb8b','{\"answers\": [{\"id\": 41, \"media\": null, \"select\": false, \"content\": \"101\"}, {\"id\": 42, \"media\": null, \"select\": true, \"content\": \"102\"}, {\"id\": 43, \"media\": null, \"select\": false, \"content\": \"100\"}, {\"id\": 44, \"media\": null, \"select\": false, \"content\": \"103\"}]}','{\"id\": 10, \"answers\": [{\"id\": 41, \"media\": null, \"content\": \"101\", \"correct\": false, \"createdAt\": 1730275777000}, {\"id\": 42, \"media\": null, \"content\": \"102\", \"correct\": false, \"createdAt\": 1730275777000}, {\"id\": 44, \"media\": null, \"content\": \"103\", \"correct\": false, \"createdAt\": 1730275777000}, {\"id\": 43, \"media\": null, \"content\": \"100\", \"correct\": true, \"createdAt\": 1730275777000}], \"content\": \"<p>99 + 1 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275777000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',2,'{\"id\": 10, \"media\": null, \"content\": \"<p>99 + 1 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275777000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '\0',12),('06ee0e7f-0184-4e90-a49a-8451fab7ecd0','{\"answers\": [{\"id\": 9, \"media\": null, \"select\": false, \"content\": \"4\"}, {\"id\": 10, \"media\": null, \"select\": false, \"content\": \"1\"}, {\"id\": 11, \"media\": null, \"select\": false, \"content\": \"3\"}, {\"id\": 12, \"media\": null, \"select\": true, \"content\": \"15\"}]}','{\"id\": 1, \"media\": \"null\", \"answers\": [{\"id\": 11, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 9, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 12, \"media\": null, \"content\": \"15\", \"correct\": true, \"createdAt\": 1729223769000}, {\"id\": 10, \"media\": null, \"content\": \"1\", \"correct\": false, \"createdAt\": 1729223769000}], \"content\": \"<p>1 + 14 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',5,'{\"id\": 1, \"media\": \"null\", \"content\": \"<p>1 + 14 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '',9),('15a5e8d9-2f92-4c64-962e-b634418793de','{\"answers\": [{\"id\": 49, \"media\": null, \"select\": true, \"content\": \"10\"}, {\"id\": 50, \"media\": null, \"select\": false, \"content\": \"11\"}, {\"id\": 51, \"media\": null, \"select\": false, \"content\": \"21\"}, {\"id\": 52, \"media\": null, \"select\": false, \"content\": \"14\"}]}','{\"id\": 12, \"answers\": [{\"id\": 50, \"media\": null, \"content\": \"11\", \"correct\": false, \"createdAt\": 1730288527000}, {\"id\": 51, \"media\": null, \"content\": \"21\", \"correct\": false, \"createdAt\": 1730288527000}, {\"id\": 49, \"media\": null, \"content\": \"10\", \"correct\": true, \"createdAt\": 1730288527000}, {\"id\": 52, \"media\": null, \"content\": \"14\", \"correct\": false, \"createdAt\": 1730288527000}], \"content\": \"<p>1 + 9 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730288527000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',3,'{\"id\": 12, \"media\": null, \"content\": \"<p>1 + 9 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730288527000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '',10),('16295c4b-6d01-48c4-92e9-8f1632946725','{\"answers\": [{\"id\": 25, \"media\": null, \"select\": true, \"content\": \"10\"}, {\"id\": 26, \"media\": null, \"select\": false, \"content\": \"12\"}, {\"id\": 27, \"media\": null, \"select\": false, \"content\": \"14\"}, {\"id\": 28, \"media\": null, \"select\": false, \"content\": \"15\"}]}','{\"id\": 6, \"answers\": [{\"id\": 25, \"media\": null, \"content\": \"10\", \"correct\": true, \"createdAt\": 1730275490000}, {\"id\": 27, \"media\": null, \"content\": \"14\", \"correct\": false, \"createdAt\": 1730275490000}, {\"id\": 28, \"media\": null, \"content\": \"15\", \"correct\": false, \"createdAt\": 1730275490000}, {\"id\": 26, \"media\": null, \"content\": \"12\", \"correct\": false, \"createdAt\": 1730275490000}], \"content\": \"<p>3 + 7 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275490000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',3,'{\"id\": 6, \"media\": null, \"content\": \"<p>3 + 7 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275490000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '',10),('2e51ee20-2bae-49c0-9dc6-6e11991a2ca7','{\"answers\": [{\"id\": 5, \"media\": null, \"select\": true, \"content\": \"3\"}, {\"id\": 6, \"media\": null, \"select\": false, \"content\": \"4\"}, {\"id\": 7, \"media\": null, \"select\": false, \"content\": \"5\"}, {\"id\": 8, \"media\": null, \"select\": false, \"content\": \"2\"}]}','{\"id\": 2, \"answers\": [{\"id\": 5, \"media\": null, \"content\": \"3\", \"correct\": true, \"createdAt\": 1727148492000}, {\"id\": 6, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 8, \"media\": null, \"content\": \"2\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 7, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1727148492000}], \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',5,'{\"id\": 2, \"media\": null, \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '',9),('43e6480a-eec7-4d0a-86ae-c3edcaf62fa2','{\"answers\": [{\"id\": 5, \"media\": null, \"select\": false, \"content\": \"3\"}, {\"id\": 6, \"media\": null, \"select\": true, \"content\": \"4\"}, {\"id\": 7, \"media\": null, \"select\": false, \"content\": \"5\"}, {\"id\": 8, \"media\": null, \"select\": false, \"content\": \"2\"}]}','{\"id\": 2, \"answers\": [{\"id\": 5, \"media\": null, \"content\": \"3\", \"correct\": true, \"createdAt\": 1727148492000}, {\"id\": 6, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 7, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 8, \"media\": null, \"content\": \"2\", \"correct\": false, \"createdAt\": 1727148492000}], \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',5,'{\"id\": 2, \"media\": null, \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '\0',8),('4e9ad19b-a9a9-4cdb-8dcf-b02bf7be5a13','{\"answers\": [{\"id\": 25, \"media\": null, \"select\": false, \"content\": \"10\"}, {\"id\": 26, \"media\": null, \"select\": true, \"content\": \"12\"}, {\"id\": 27, \"media\": null, \"select\": false, \"content\": \"14\"}, {\"id\": 28, \"media\": null, \"select\": false, \"content\": \"15\"}]}','{\"id\": 6, \"answers\": [{\"id\": 25, \"media\": null, \"content\": \"10\", \"correct\": true, \"createdAt\": 1730275490000}, {\"id\": 26, \"media\": null, \"content\": \"12\", \"correct\": false, \"createdAt\": 1730275490000}, {\"id\": 28, \"media\": null, \"content\": \"15\", \"correct\": false, \"createdAt\": 1730275490000}, {\"id\": 27, \"media\": null, \"content\": \"14\", \"correct\": false, \"createdAt\": 1730275490000}], \"content\": \"<p>3 + 7 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275490000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',3,'{\"id\": 6, \"media\": null, \"content\": \"<p>3 + 7 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275490000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '\0',12),('7cee021d-9402-4f4b-b015-36ea57350092','{\"answers\": [{\"id\": 21, \"media\": null, \"select\": true, \"content\": \"3\"}, {\"id\": 22, \"media\": null, \"select\": false, \"content\": \"5\"}, {\"id\": 23, \"media\": null, \"select\": true, \"content\": \"7\"}, {\"id\": 24, \"media\": null, \"select\": false, \"content\": \"9\"}]}','{\"id\": 5, \"answers\": [{\"id\": 21, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1730275446000}, {\"id\": 23, \"media\": null, \"content\": \"7\", \"correct\": true, \"createdAt\": 1730275446000}, {\"id\": 22, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1730275446000}, {\"id\": 24, \"media\": null, \"content\": \"9\", \"correct\": false, \"createdAt\": 1730275446000}], \"content\": \"<p>3 + 4 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275446000, \"questionType\": {\"alias\": \"multiple_choice\", \"displayName\": \"Câu hỏi nhiều lựa chọn\"}, \"additionalFields\": {}}',_binary '',2,'{\"id\": 5, \"media\": null, \"content\": \"<p>3 + 4 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275446000, \"questionType\": {\"alias\": \"multiple_choice\", \"displayName\": \"Câu hỏi nhiều lựa chọn\"}}',_binary '\0',12),('b18f596c-eb73-4ad0-9e6d-378d012ca507','{\"answers\": [{\"id\": 5, \"media\": null, \"select\": true, \"content\": \"3\"}, {\"id\": 6, \"media\": null, \"select\": false, \"content\": \"4\"}, {\"id\": 7, \"media\": null, \"select\": false, \"content\": \"5\"}, {\"id\": 8, \"media\": null, \"select\": false, \"content\": \"2\"}]}','{\"id\": 2, \"answers\": [{\"id\": 5, \"media\": null, \"content\": \"3\", \"correct\": true, \"createdAt\": 1727148492000}, {\"id\": 7, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 6, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 8, \"media\": null, \"content\": \"2\", \"correct\": false, \"createdAt\": 1727148492000}], \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',5,'{\"id\": 2, \"media\": null, \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '',11),('cec170ef-3170-4e68-b4f0-f1bb7c7eb47f','{\"answers\": [{\"id\": 21, \"media\": null, \"select\": false, \"content\": \"3\"}, {\"id\": 22, \"media\": null, \"select\": true, \"content\": \"5\"}, {\"id\": 23, \"media\": null, \"select\": true, \"content\": \"7\"}, {\"id\": 24, \"media\": null, \"select\": false, \"content\": \"9\"}]}','{\"id\": 5, \"answers\": [{\"id\": 22, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1730275446000}, {\"id\": 24, \"media\": null, \"content\": \"9\", \"correct\": false, \"createdAt\": 1730275446000}, {\"id\": 21, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1730275446000}, {\"id\": 23, \"media\": null, \"content\": \"7\", \"correct\": true, \"createdAt\": 1730275446000}], \"content\": \"<p>3 + 4 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275446000, \"questionType\": {\"alias\": \"multiple_choice\", \"displayName\": \"Câu hỏi nhiều lựa chọn\"}, \"additionalFields\": {}}',_binary '',2,'{\"id\": 5, \"media\": null, \"content\": \"<p>3 + 4 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275446000, \"questionType\": {\"alias\": \"multiple_choice\", \"displayName\": \"Câu hỏi nhiều lựa chọn\"}}',_binary '\0',10),('d1acd44a-99eb-4db4-a285-8582fe064239','{\"answers\": [{\"id\": 9, \"media\": null, \"select\": false, \"content\": \"4\"}, {\"id\": 10, \"media\": null, \"select\": false, \"content\": \"1\"}, {\"id\": 11, \"media\": null, \"select\": false, \"content\": \"3\"}, {\"id\": 12, \"media\": null, \"select\": true, \"content\": \"15\"}]}','{\"id\": 1, \"media\": \"null\", \"answers\": [{\"id\": 10, \"media\": null, \"content\": \"1\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 9, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 12, \"media\": null, \"content\": \"15\", \"correct\": true, \"createdAt\": 1729223769000}, {\"id\": 11, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1729223769000}], \"content\": \"<p>1 + 14 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',5,'{\"id\": 1, \"media\": \"null\", \"content\": \"<p>1 + 14 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '',8),('d9038730-2949-4720-8eba-a1aed55c1d46','{\"answers\": [{\"id\": 41, \"media\": null, \"select\": false, \"content\": \"101\"}, {\"id\": 42, \"media\": null, \"select\": false, \"content\": \"102\"}, {\"id\": 43, \"media\": null, \"select\": true, \"content\": \"100\"}, {\"id\": 44, \"media\": null, \"select\": false, \"content\": \"103\"}]}','{\"id\": 10, \"answers\": [{\"id\": 44, \"media\": null, \"content\": \"103\", \"correct\": false, \"createdAt\": 1730275777000}, {\"id\": 43, \"media\": null, \"content\": \"100\", \"correct\": true, \"createdAt\": 1730275777000}, {\"id\": 42, \"media\": null, \"content\": \"102\", \"correct\": false, \"createdAt\": 1730275777000}, {\"id\": 41, \"media\": null, \"content\": \"101\", \"correct\": false, \"createdAt\": 1730275777000}], \"content\": \"<p>99 + 1 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275777000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',2,'{\"id\": 10, \"media\": null, \"content\": \"<p>99 + 1 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275777000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '',10),('f3e6496d-a3a2-423a-9032-2acba6fab01f','{\"answers\": [{\"id\": 9, \"media\": null, \"select\": false, \"content\": \"4\"}, {\"id\": 10, \"media\": null, \"select\": false, \"content\": \"1\"}, {\"id\": 11, \"media\": null, \"select\": false, \"content\": \"3\"}, {\"id\": 12, \"media\": null, \"select\": true, \"content\": \"15\"}]}','{\"id\": 1, \"media\": \"null\", \"answers\": [{\"id\": 11, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 10, \"media\": null, \"content\": \"1\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 9, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 12, \"media\": null, \"content\": \"15\", \"correct\": true, \"createdAt\": 1729223769000}], \"content\": \"<p>1 + 14 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}',_binary '',5,'{\"id\": 1, \"media\": \"null\", \"content\": \"<p>1 + 14 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}}',_binary '',11);
/*!40000 ALTER TABLE `user_question_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_quiz_results`
--

DROP TABLE IF EXISTS `user_quiz_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_quiz_results` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `duration` varchar(255) DEFAULT NULL,
  `exam` json DEFAULT NULL,
  `marks` int DEFAULT NULL,
  `number_of_correct` int DEFAULT NULL,
  `number_of_incorrect` int DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `submit_time` datetime(6) DEFAULT NULL,
  `submitted` bit(1) DEFAULT NULL,
  `quiz_id` bigint DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `status` enum('Active','Deleted') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeqajiqktmo5ry9h4sa7fxn00f` (`quiz_id`),
  KEY `FKojhp2lti8qdape4toi6cy0lx` (`user_id`),
  CONSTRAINT `FKeqajiqktmo5ry9h4sa7fxn00f` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`id`),
  CONSTRAINT `FKojhp2lti8qdape4toi6cy0lx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_quiz_results`
--

LOCK TABLES `user_quiz_results` WRITE;
/*!40000 ALTER TABLE `user_quiz_results` DISABLE KEYS */;
INSERT INTO `user_quiz_results` VALUES (2,NULL,'{\"title\": \"Ôn Thi toán 1\", \"quizId\": 1, \"maxMarks\": 10, \"description\": \"Ôn thi toán lớp 1\", \"durationMinutes\": 5, \"numberOfQuestions\": 2, \"questionResponseList\": [{\"id\": 1, \"answers\": [{\"id\": 2, \"media\": null, \"content\": \"2\", \"correct\": true, \"createdAt\": 1727148458000}, {\"id\": 4, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148458000}, {\"id\": 3, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1727148458000}, {\"id\": 1, \"media\": null, \"content\": \"1\", \"correct\": false, \"createdAt\": 1727148458000}], \"content\": \"<p>1 + 1 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 2, \"answers\": [{\"id\": 6, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 8, \"media\": null, \"content\": \"2\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 7, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 5, \"media\": null, \"content\": \"3\", \"correct\": true, \"createdAt\": 1727148492000}], \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}]}',0,NULL,NULL,'2024-09-24 10:35:45.627000',NULL,_binary '\0',1,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef','Active'),(3,NULL,'{\"title\": \"Ôn Thi toán 1\", \"quizId\": 1, \"maxMarks\": 10, \"description\": \"Ôn thi toán lớp 1\", \"durationMinutes\": 5, \"numberOfQuestions\": 2, \"questionResponseList\": [{\"id\": 1, \"answers\": [{\"id\": 4, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148458000}, {\"id\": 3, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1727148458000}, {\"id\": 2, \"media\": null, \"content\": \"2\", \"correct\": true, \"createdAt\": 1727148458000}, {\"id\": 1, \"media\": null, \"content\": \"1\", \"correct\": false, \"createdAt\": 1727148458000}], \"content\": \"<p>1 + 1 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 2, \"answers\": [{\"id\": 7, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 5, \"media\": null, \"content\": \"3\", \"correct\": true, \"createdAt\": 1727148492000}, {\"id\": 6, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 8, \"media\": null, \"content\": \"2\", \"correct\": false, \"createdAt\": 1727148492000}], \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}]}',0,NULL,NULL,'2024-09-24 10:36:14.310000',NULL,_binary '\0',1,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef','Active'),(4,NULL,'{\"title\": \"Ôn Thi toán 1\", \"quizId\": 1, \"maxMarks\": 10, \"description\": \"Ôn thi toán lớp 1\", \"durationMinutes\": 5, \"numberOfQuestions\": 2, \"questionResponseList\": [{\"id\": 1, \"answers\": [{\"id\": 3, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1727148458000}, {\"id\": 1, \"media\": null, \"content\": \"1\", \"correct\": false, \"createdAt\": 1727148458000}, {\"id\": 4, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148458000}, {\"id\": 2, \"media\": null, \"content\": \"2\", \"correct\": true, \"createdAt\": 1727148458000}], \"content\": \"<p>1 + 1 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 2, \"answers\": [{\"id\": 7, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 8, \"media\": null, \"content\": \"2\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 6, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 5, \"media\": null, \"content\": \"3\", \"correct\": true, \"createdAt\": 1727148492000}], \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}]}',0,NULL,NULL,'2024-09-24 10:36:19.199000',NULL,_binary '\0',1,'c6c05b6f-5f84-4841-b052-438b5a8fb7ef','Deleted'),(8,'00:00:10','{\"title\": \"Ôn Thi toán 1\", \"quizId\": 1, \"maxMarks\": 10, \"description\": \"Ôn thi toán lớp 1\", \"durationMinutes\": 5, \"numberOfQuestions\": 2, \"questionResponseList\": [{\"id\": 1, \"media\": \"null\", \"answers\": [{\"id\": 11, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 9, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 10, \"media\": null, \"content\": \"1\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 12, \"media\": null, \"content\": \"15\", \"correct\": true, \"createdAt\": 1729223769000}], \"content\": \"<p>1 + 14 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 2, \"answers\": [{\"id\": 8, \"media\": null, \"content\": \"2\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 5, \"media\": null, \"content\": \"3\", \"correct\": true, \"createdAt\": 1727148492000}, {\"id\": 7, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 6, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148492000}], \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}]}',5,1,1,'2025-01-04 22:07:46.939000','2025-01-04 22:07:57.887000',_binary '',1,'9f30d6e2-096b-44d6-a593-c2f73e880c1e','Deleted'),(9,'00:00:08','{\"title\": \"Bài thi toán 1\", \"quizId\": 2, \"maxMarks\": 10, \"description\": \"Làm nhanh về sớm\", \"durationMinutes\": 15, \"numberOfQuestions\": 2, \"questionResponseList\": [{\"id\": 1, \"media\": \"null\", \"answers\": [{\"id\": 12, \"media\": null, \"content\": \"15\", \"correct\": true, \"createdAt\": 1729223769000}, {\"id\": 9, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 10, \"media\": null, \"content\": \"1\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 11, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1729223769000}], \"content\": \"<p>1 + 14 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 2, \"answers\": [{\"id\": 8, \"media\": null, \"content\": \"2\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 5, \"media\": null, \"content\": \"3\", \"correct\": true, \"createdAt\": 1727148492000}, {\"id\": 7, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 6, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148492000}], \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}]}',10,2,0,'2025-01-04 22:20:02.138000','2025-01-04 22:20:11.118000',_binary '',2,'9f30d6e2-096b-44d6-a593-c2f73e880c1e','Active'),(10,'00:00:22','{\"title\": \"BÀI TẬP 1\", \"quizId\": 6, \"maxMarks\": 10, \"description\": \"NO DESCRIPTION\", \"durationMinutes\": 10, \"numberOfQuestions\": 4, \"questionResponseList\": [{\"id\": 5, \"answers\": [{\"id\": 21, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1730275446000}, {\"id\": 22, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1730275446000}, {\"id\": 23, \"media\": null, \"content\": \"7\", \"correct\": true, \"createdAt\": 1730275446000}, {\"id\": 24, \"media\": null, \"content\": \"9\", \"correct\": false, \"createdAt\": 1730275446000}], \"content\": \"<p>3 + 4 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275446000, \"questionType\": {\"alias\": \"multiple_choice\", \"displayName\": \"Câu hỏi nhiều lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 6, \"answers\": [{\"id\": 28, \"media\": null, \"content\": \"15\", \"correct\": false, \"createdAt\": 1730275490000}, {\"id\": 25, \"media\": null, \"content\": \"10\", \"correct\": true, \"createdAt\": 1730275490000}, {\"id\": 27, \"media\": null, \"content\": \"14\", \"correct\": false, \"createdAt\": 1730275490000}, {\"id\": 26, \"media\": null, \"content\": \"12\", \"correct\": false, \"createdAt\": 1730275490000}], \"content\": \"<p>3 + 7 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275490000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 10, \"answers\": [{\"id\": 43, \"media\": null, \"content\": \"100\", \"correct\": true, \"createdAt\": 1730275777000}, {\"id\": 44, \"media\": null, \"content\": \"103\", \"correct\": false, \"createdAt\": 1730275777000}, {\"id\": 41, \"media\": null, \"content\": \"101\", \"correct\": false, \"createdAt\": 1730275777000}, {\"id\": 42, \"media\": null, \"content\": \"102\", \"correct\": false, \"createdAt\": 1730275777000}], \"content\": \"<p>99 + 1 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275777000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 12, \"answers\": [{\"id\": 51, \"media\": null, \"content\": \"21\", \"correct\": false, \"createdAt\": 1730288527000}, {\"id\": 52, \"media\": null, \"content\": \"14\", \"correct\": false, \"createdAt\": 1730288527000}, {\"id\": 50, \"media\": null, \"content\": \"11\", \"correct\": false, \"createdAt\": 1730288527000}, {\"id\": 49, \"media\": null, \"content\": \"10\", \"correct\": true, \"createdAt\": 1730288527000}], \"content\": \"<p>1 + 9 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730288527000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}]}',8,3,1,'2025-01-05 10:54:32.317000','2025-01-05 10:54:54.759000',_binary '',6,'9f30d6e2-096b-44d6-a593-c2f73e880c1e','Active'),(11,'00:00:17','{\"title\": \"Bài thi toán 1\", \"quizId\": 2, \"maxMarks\": 10, \"description\": \"Làm nhanh về sớm\", \"durationMinutes\": 15, \"numberOfQuestions\": 2, \"questionResponseList\": [{\"id\": 1, \"media\": \"null\", \"answers\": [{\"id\": 11, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 9, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1729223769000}, {\"id\": 12, \"media\": null, \"content\": \"15\", \"correct\": true, \"createdAt\": 1729223769000}, {\"id\": 10, \"media\": null, \"content\": \"1\", \"correct\": false, \"createdAt\": 1729223769000}], \"content\": \"<p>1 + 14 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148458000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 2, \"answers\": [{\"id\": 8, \"media\": null, \"content\": \"2\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 5, \"media\": null, \"content\": \"3\", \"correct\": true, \"createdAt\": 1727148492000}, {\"id\": 7, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1727148492000}, {\"id\": 6, \"media\": null, \"content\": \"4\", \"correct\": false, \"createdAt\": 1727148492000}], \"content\": \"<p>1 + 2 = ?</p>\", \"category\": {\"id\": 2, \"title\": \"Toán 1\", \"description\": \"Test 1\"}, \"createdAt\": 1727148492000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}]}',10,2,0,'2025-01-05 10:55:53.647000','2025-01-05 10:56:11.119000',_binary '',2,'9f30d6e2-096b-44d6-a593-c2f73e880c1e','Active'),(12,'00:00:17','{\"title\": \"BÀI TẬP 1\", \"quizId\": 6, \"maxMarks\": 10, \"description\": \"NO DESCRIPTION\", \"durationMinutes\": 10, \"numberOfQuestions\": 4, \"questionResponseList\": [{\"id\": 5, \"answers\": [{\"id\": 23, \"media\": null, \"content\": \"7\", \"correct\": true, \"createdAt\": 1730275446000}, {\"id\": 22, \"media\": null, \"content\": \"5\", \"correct\": false, \"createdAt\": 1730275446000}, {\"id\": 21, \"media\": null, \"content\": \"3\", \"correct\": false, \"createdAt\": 1730275446000}, {\"id\": 24, \"media\": null, \"content\": \"9\", \"correct\": false, \"createdAt\": 1730275446000}], \"content\": \"<p>3 + 4 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275446000, \"questionType\": {\"alias\": \"multiple_choice\", \"displayName\": \"Câu hỏi nhiều lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 6, \"answers\": [{\"id\": 27, \"media\": null, \"content\": \"14\", \"correct\": false, \"createdAt\": 1730275490000}, {\"id\": 25, \"media\": null, \"content\": \"10\", \"correct\": true, \"createdAt\": 1730275490000}, {\"id\": 28, \"media\": null, \"content\": \"15\", \"correct\": false, \"createdAt\": 1730275490000}, {\"id\": 26, \"media\": null, \"content\": \"12\", \"correct\": false, \"createdAt\": 1730275490000}], \"content\": \"<p>3 + 7 =?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275490000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 10, \"answers\": [{\"id\": 42, \"media\": null, \"content\": \"102\", \"correct\": false, \"createdAt\": 1730275777000}, {\"id\": 43, \"media\": null, \"content\": \"100\", \"correct\": true, \"createdAt\": 1730275777000}, {\"id\": 41, \"media\": null, \"content\": \"101\", \"correct\": false, \"createdAt\": 1730275777000}, {\"id\": 44, \"media\": null, \"content\": \"103\", \"correct\": false, \"createdAt\": 1730275777000}], \"content\": \"<p>99 + 1 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730275777000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}, {\"id\": 12, \"answers\": [{\"id\": 49, \"media\": null, \"content\": \"10\", \"correct\": true, \"createdAt\": 1730288527000}, {\"id\": 51, \"media\": null, \"content\": \"21\", \"correct\": false, \"createdAt\": 1730288527000}, {\"id\": 50, \"media\": null, \"content\": \"11\", \"correct\": false, \"createdAt\": 1730288527000}, {\"id\": 52, \"media\": null, \"content\": \"14\", \"correct\": false, \"createdAt\": 1730288527000}], \"content\": \"<p>1 + 9 = ?</p>\", \"category\": {\"id\": 3, \"title\": \"Toán 2\", \"description\": \"test 2\"}, \"createdAt\": 1730288527000, \"questionType\": {\"alias\": \"single_choice\", \"displayName\": \"Câu hỏi một lựa chọn\"}, \"additionalFields\": {}}]}',0,0,4,'2025-04-13 15:17:21.776000','2025-04-13 15:17:39.240000',_binary '',6,'9f30d6e2-096b-44d6-a593-c2f73e880c1e','Active');
/*!40000 ALTER TABLE `user_quiz_results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `email` text NOT NULL,
  `firebase_id` text,
  `full_name` text NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` text NOT NULL,
  `status` enum('Active','Deleted') DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r53o2ojjw4fikudfnsuuga336` (`password`),
  KEY `FKkhsgcesxek0dbj7n1ji4dfrws` (`create_by`),
  CONSTRAINT `FKkhsgcesxek0dbj7n1ji4dfrws` FOREIGN KEY (`create_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('72da1300-ef4a-4192-b34e-43324d1b934a','2024-12-16 13:58:37','123Aaa@gmail.com','riIcJ6S8T9VrgmLLnoLlHMQDxRF3','Trần Văn A','$2a$10$Rv1m0aF1zmvaIzV/OHiP0eB7gPZIp.A5QRQFPTCaQfOJq1Zmpjv4y','student','Active',NULL),('9f30d6e2-096b-44d6-a593-c2f73e880c1e','2024-12-12 11:33:41','tranhau.010120199@gmail.com','isangxA0D4cV5lbxE48EZGNscWm1','Hau Tran','$2a$10$x5UlspO/GbrnXBsh0e.EXeT3Jw5nVHj9wVOvnX4i4ECAqoP6WBkCa','student','Active',NULL),('c6c05b6f-5f84-4841-b052-438b5a8fb7ef','2024-09-10 03:10:26','6251071029@st.utc2.edu.vn','zLQf8rpNgbW4ZnZBNbjPNrqTQzU2','Văn Hậu Trần','$2a$10$LIt00JBLVzRm09.o2inrP.s6KfWNDeu1UIr3xyEqVHuCUjFHdhs/i','admin','Active',NULL),('e3b3892a-e25e-406c-8e58-5d78efcebccb','2024-12-17 09:30:23','12345@st.utc2.edu.vn','mcfGLd7twiMmRPdyxa7xnDvjDss2','abc123','$2a$10$.klnjHRR7XxNZ/5JmCEEMOx3C4Og5KY.S0OxzMeo6QUNkiSAZ/8le','student','Active',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'exam'
--

--
-- Dumping routines for database 'exam'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-08 21:36:58
