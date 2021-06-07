-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 07, 2021 at 05:58 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `billionaire`
--

-- --------------------------------------------------------

--
-- Table structure for table `goal`
--

CREATE TABLE `goal` (
  `ID_GOAL` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  `NAME_GOAL` varchar(100) NOT NULL,
  `DESCRIPTION_GOAL` text DEFAULT NULL,
  `MONEY_GOAL` double NOT NULL,
  `IMAGE_GOAL` blob DEFAULT NULL,
  `FINISH` int(11) NOT NULL,
  `MONEY_SAVING` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `income`
--

CREATE TABLE `income` (
  `ID_INCOME` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  `TOTAL_INCOME` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `income`
--

INSERT INTO `income` (`ID_INCOME`, `ID_USER`, `TOTAL_INCOME`) VALUES
(1, 1, 2000000),
(2, 2, 5000000),
(3, 3, 10000000);

-- --------------------------------------------------------

--
-- Table structure for table `incomecategory`
--

CREATE TABLE `incomecategory` (
  `ID_INCOME` int(11) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `IMAGE` blob DEFAULT NULL,
  `CHILD` int(1) NOT NULL,
  `PARENT_NAME` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `incomecategory`
--

INSERT INTO `incomecategory` (`ID_INCOME`, `NAME`, `IMAGE`, `CHILD`, `PARENT_NAME`) VALUES
(1, 'TIEN THUONG', NULL, 0, '0'),
(2, 'LUONG', NULL, 0, '0'),
(3, 'BAN HANG', NULL, 0, '0'),
(4, 'BAN HANG ONLINE', NULL, 1, 'BAN HANG'),
(5, 'THU NHAP KHAC', NULL, 0, '0');

-- --------------------------------------------------------

--
-- Table structure for table `incomedetail`
--

CREATE TABLE `incomedetail` (
  `ID_INCOMEDETAIL` int(11) NOT NULL,
  `ID_INCOME` int(11) NOT NULL,
  `MONEY` double NOT NULL,
  `ID_INCOMECATEGORY` int(11) NOT NULL,
  `DESCRIPTION` text DEFAULT NULL,
  `DATE` date NOT NULL,
  `HOUR` time NOT NULL,
  `IMAGE` blob DEFAULT NULL,
  `AUDIO` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `incomedetail`
--

INSERT INTO `incomedetail` (`ID_INCOMEDETAIL`, `ID_INCOME`, `MONEY`, `ID_INCOMECATEGORY`, `DESCRIPTION`, `DATE`, `HOUR`, `IMAGE`, `AUDIO`) VALUES
(1, 1, 500000, 2, NULL, '2021-06-07', '21:49:00', NULL, NULL),
(2, 1, 500000, 1, 'ANH CHO', '2021-06-06', '19:49:18', NULL, NULL),
(3, 2, 800000, 5, '', '2021-06-07', '08:49:18', NULL, NULL),
(4, 2, 200000, 1, '', '2021-06-07', '19:49:18', NULL, NULL),
(5, 3, 4000000, 2, '', '2021-06-06', '19:49:18', NULL, NULL),
(6, 3, 1000000, 1, '', '2021-06-02', '19:49:18', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `saving`
--

CREATE TABLE `saving` (
  `ID_SAVING` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  `TOTAL_SAVING` double NOT NULL,
  `TOTAL_INCOME` double NOT NULL,
  `TOTAL_SPENDING` double NOT NULL,
  `TOTAL_DAY` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `saving`
--

INSERT INTO `saving` (`ID_SAVING`, `ID_USER`, `TOTAL_SAVING`, `TOTAL_INCOME`, `TOTAL_SPENDING`, `TOTAL_DAY`) VALUES
(1, 2, 1000000, 2000000, 1000000, 10),
(2, 1, 1000000, 2000000, 1500000, 5);

-- --------------------------------------------------------

--
-- Table structure for table `savingdetail`
--

CREATE TABLE `savingdetail` (
  `ID_SAVINGDETAIL` int(11) NOT NULL,
  `ID_SAVING` int(11) NOT NULL,
  `SAVING_PER_DAY` double NOT NULL,
  `INCOME_PER_DAY` double NOT NULL,
  `SPENDING_PER_DAY` double NOT NULL,
  `DATE` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `spending`
--

CREATE TABLE `spending` (
  `ID_SPENDING` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  `TOTAL_SPENDING` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `spending`
--

INSERT INTO `spending` (`ID_SPENDING`, `ID_USER`, `TOTAL_SPENDING`) VALUES
(1, 1, 500000),
(2, 2, 200000),
(3, 3, 1000000);

-- --------------------------------------------------------

--
-- Table structure for table `spendingcategory`
--

CREATE TABLE `spendingcategory` (
  `ID_SPENDING` int(11) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `IMAGE` blob DEFAULT NULL,
  `CHILD` tinyint(1) NOT NULL,
  `PARENT_NAME` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `spendingcategory`
--

INSERT INTO `spendingcategory` (`ID_SPENDING`, `NAME`, `IMAGE`, `CHILD`, `PARENT_NAME`) VALUES
(1, 'THUC AN', NULL, 0, '0'),
(2, 'CA PHE', NULL, 1, 'THUC AN'),
(3, 'NHA HANG', NULL, 1, 'THUC AN'),
(4, 'HOA DON', NULL, 0, '0'),
(5, 'HOA DON DIEN', NULL, 1, 'HOA DON'),
(6, 'HOA DON NUOC', NULL, 1, 'HOA DON'),
(7, 'DI CHUYEN', NULL, 0, '0'),
(8, 'TAXI', NULL, 1, 'DI CHUYEN'),
(9, 'GUI XE', NULL, 1, 'DI CHUYEN'),
(10, 'MUA SAM', NULL, 0, '0');

-- --------------------------------------------------------

--
-- Table structure for table `spendingdetail`
--

CREATE TABLE `spendingdetail` (
  `ID_SPENDINGDETAIL` int(11) NOT NULL,
  `ID_SPENDING` int(11) NOT NULL,
  `MONEY` double NOT NULL,
  `ID_SPENDINGCATEGORY` int(11) NOT NULL,
  `DESCRIPTION` text DEFAULT NULL,
  `DATE` date NOT NULL,
  `TIME` time NOT NULL,
  `IMAGE` blob DEFAULT NULL,
  `AUDIO` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `spendingdetail`
--

INSERT INTO `spendingdetail` (`ID_SPENDINGDETAIL`, `ID_SPENDING`, `MONEY`, `ID_SPENDINGCATEGORY`, `DESCRIPTION`, `DATE`, `TIME`, `IMAGE`, `AUDIO`) VALUES
(1, 1, 500000, 3, NULL, '2021-06-07', '22:28:19', NULL, NULL),
(2, 2, 200000, 7, NULL, '2021-06-03', '22:08:19', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `ID_USER` int(11) NOT NULL,
  `USERNAME` varchar(100) NOT NULL,
  `PASSWORD` varchar(20) NOT NULL,
  `FULLNAME` varchar(100) NOT NULL,
  `DATESTART` datetime NOT NULL,
  `INCOME` double NOT NULL,
  `USERIMAGE` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID_USER`, `USERNAME`, `PASSWORD`, `FULLNAME`, `DATESTART`, `INCOME`, `USERIMAGE`) VALUES
(1, 'minhquan2k1', '12345678', 'Cao Nguyen Minh Quan', '2021-06-07 16:13:07', 1000000, NULL),
(2, 'ductam2k1', '12345678', 'Tran Duc Tam', '2021-06-02 13:43:00', 5000000, NULL),
(3, 'hoangson2k1', '12345678', 'Dang Hai Hoang Son', '2021-06-06 14:40:00', 10000000, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `goal`
--
ALTER TABLE `goal`
  ADD PRIMARY KEY (`ID_GOAL`);

--
-- Indexes for table `income`
--
ALTER TABLE `income`
  ADD PRIMARY KEY (`ID_INCOME`),
  ADD KEY `ID_USER` (`ID_USER`);

--
-- Indexes for table `incomecategory`
--
ALTER TABLE `incomecategory`
  ADD PRIMARY KEY (`ID_INCOME`);

--
-- Indexes for table `incomedetail`
--
ALTER TABLE `incomedetail`
  ADD PRIMARY KEY (`ID_INCOMEDETAIL`),
  ADD KEY `ID_INCOME` (`ID_INCOME`),
  ADD KEY `ID_SAVING` (`ID_INCOMECATEGORY`);

--
-- Indexes for table `saving`
--
ALTER TABLE `saving`
  ADD PRIMARY KEY (`ID_SAVING`),
  ADD KEY `ID_USER` (`ID_USER`);

--
-- Indexes for table `savingdetail`
--
ALTER TABLE `savingdetail`
  ADD PRIMARY KEY (`ID_SAVINGDETAIL`),
  ADD KEY `ID_SAVING` (`ID_SAVING`);

--
-- Indexes for table `spending`
--
ALTER TABLE `spending`
  ADD PRIMARY KEY (`ID_SPENDING`),
  ADD KEY `ID_USER` (`ID_USER`);

--
-- Indexes for table `spendingcategory`
--
ALTER TABLE `spendingcategory`
  ADD PRIMARY KEY (`ID_SPENDING`);

--
-- Indexes for table `spendingdetail`
--
ALTER TABLE `spendingdetail`
  ADD PRIMARY KEY (`ID_SPENDINGDETAIL`),
  ADD KEY `ID_SPENDING` (`ID_SPENDING`),
  ADD KEY `ID_SPENDINGCATEGORY` (`ID_SPENDINGCATEGORY`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID_USER`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `goal`
--
ALTER TABLE `goal`
  MODIFY `ID_GOAL` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `income`
--
ALTER TABLE `income`
  MODIFY `ID_INCOME` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `incomecategory`
--
ALTER TABLE `incomecategory`
  MODIFY `ID_INCOME` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `incomedetail`
--
ALTER TABLE `incomedetail`
  MODIFY `ID_INCOMEDETAIL` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `saving`
--
ALTER TABLE `saving`
  MODIFY `ID_SAVING` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `savingdetail`
--
ALTER TABLE `savingdetail`
  MODIFY `ID_SAVINGDETAIL` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `spending`
--
ALTER TABLE `spending`
  MODIFY `ID_SPENDING` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `spendingcategory`
--
ALTER TABLE `spendingcategory`
  MODIFY `ID_SPENDING` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `spendingdetail`
--
ALTER TABLE `spendingdetail`
  MODIFY `ID_SPENDINGDETAIL` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `ID_USER` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `income`
--
ALTER TABLE `income`
  ADD CONSTRAINT `income_ibfk_1` FOREIGN KEY (`ID_USER`) REFERENCES `user` (`ID_USER`);

--
-- Constraints for table `incomedetail`
--
ALTER TABLE `incomedetail`
  ADD CONSTRAINT `incomedetail_ibfk_1` FOREIGN KEY (`ID_INCOME`) REFERENCES `income` (`ID_INCOME`),
  ADD CONSTRAINT `incomedetail_ibfk_2` FOREIGN KEY (`ID_INCOMECATEGORY`) REFERENCES `incomecategory` (`ID_INCOME`);

--
-- Constraints for table `saving`
--
ALTER TABLE `saving`
  ADD CONSTRAINT `saving_ibfk_1` FOREIGN KEY (`ID_USER`) REFERENCES `user` (`ID_USER`);

--
-- Constraints for table `savingdetail`
--
ALTER TABLE `savingdetail`
  ADD CONSTRAINT `savingdetail_ibfk_1` FOREIGN KEY (`ID_SAVING`) REFERENCES `saving` (`ID_SAVING`);

--
-- Constraints for table `spending`
--
ALTER TABLE `spending`
  ADD CONSTRAINT `spending_ibfk_1` FOREIGN KEY (`ID_USER`) REFERENCES `user` (`ID_USER`);

--
-- Constraints for table `spendingdetail`
--
ALTER TABLE `spendingdetail`
  ADD CONSTRAINT `spendingdetail_ibfk_1` FOREIGN KEY (`ID_SPENDING`) REFERENCES `spending` (`ID_SPENDING`),
  ADD CONSTRAINT `spendingdetail_ibfk_2` FOREIGN KEY (`ID_SPENDINGCATEGORY`) REFERENCES `spendingcategory` (`ID_SPENDING`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
