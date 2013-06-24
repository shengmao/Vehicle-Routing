-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 13, 2013 at 04:01 AM
-- Server version: 5.1.44
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `VPR1`
--

-- --------------------------------------------------------

--
-- Table structure for table `distance`
--

CREATE TABLE IF NOT EXISTS `distance` (
  `ID_Distance` int(20) NOT NULL AUTO_INCREMENT,
  `ID_Cus1` int(11) NOT NULL,
  `ID_Cus2` int(11) NOT NULL,
  `Distance` varchar(20) NOT NULL,
  PRIMARY KEY (`ID_Distance`),
  KEY `ID_Cus2` (`ID_Cus2`),
  KEY `ID_Cus1` (`ID_Cus1`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=52 ;

--
-- Dumping data for table `distance`
--

INSERT INTO `distance` (`ID_Distance`, `ID_Cus1`, `ID_Cus2`, `Distance`) VALUES
(2, 0, 2, '2'),
(3, 0, 3, '4'),
(4, 0, 4, '3'),
(5, 1, 0, '3'),
(6, 1, 1, '0'),
(7, 1, 2, '1'),
(8, 1, 3, '5'),
(9, 1, 4, '4.242641'),
(10, 2, 0, '2'),
(11, 2, 2, '0'),
(12, 2, 3, '4.242641'),
(13, 2, 4, '3.605551'),
(14, 3, 0, '4'),
(15, 3, 1, '5'),
(16, 3, 2, '4.472136'),
(17, 3, 3, '0'),
(18, 3, 4, '1'),
(19, 4, 4, '0'),
(21, 2, 1, '1'),
(22, 4, 1, '4.242641'),
(23, 4, 0, '3'),
(24, 4, 2, '3.605551'),
(25, 4, 3, '1'),
(41, 0, 0, '0.000000'),
(51, 0, 1, '3');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `distance`
--
ALTER TABLE `distance`
  ADD CONSTRAINT `distance_ibfk_2` FOREIGN KEY (`ID_Cus2`) REFERENCES `customer` (`ID`),
  ADD CONSTRAINT `distance_ibfk_3` FOREIGN KEY (`ID_Cus1`) REFERENCES `customer` (`ID`);
