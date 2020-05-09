-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Creato il: Mag 09, 2020 alle 17:49
-- Versione del server: 8.0.18
-- Versione PHP: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test_sk`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `models`
--

DROP TABLE IF EXISTS `models`;
CREATE TABLE IF NOT EXISTS `models` (
  `uid` varchar(200) NOT NULL,
  `url` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `date` varchar(100) NOT NULL,
  `like_count` int(11) NOT NULL,
  `view_count` int(11) NOT NULL,
  `comment_count` int(11) NOT NULL,
  `vertex_count` int(11) NOT NULL,
  `face_count` int(11) NOT NULL,
  `sound_count` int(11) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `model_categories`
--

DROP TABLE IF EXISTS `model_categories`;
CREATE TABLE IF NOT EXISTS `model_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_model` varchar(200) NOT NULL,
  `id_category` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_model` (`id_model`),
  KEY `id_category` (`id_category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `model_comments`
--

DROP TABLE IF EXISTS `model_comments`;
CREATE TABLE IF NOT EXISTS `model_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model_id` varchar(200) NOT NULL,
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `model_id` (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `model_tags`
--

DROP TABLE IF EXISTS `model_tags`;
CREATE TABLE IF NOT EXISTS `model_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_model` varchar(200) NOT NULL,
  `tag` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_model` (`id_model`),
  KEY `tag_index` (`tag`),
  KEY `index_tag` (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `model_categories`
--
ALTER TABLE `model_categories`
  ADD CONSTRAINT `model_categories_ibfk_1` FOREIGN KEY (`id_model`) REFERENCES `models` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `model_categories_ibfk_2` FOREIGN KEY (`id_category`) REFERENCES `categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `model_comments`
--
ALTER TABLE `model_comments`
  ADD CONSTRAINT `model_comments_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `models` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `model_tags`
--
ALTER TABLE `model_tags`
  ADD CONSTRAINT `model_tags_ibfk_1` FOREIGN KEY (`id_model`) REFERENCES `models` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
