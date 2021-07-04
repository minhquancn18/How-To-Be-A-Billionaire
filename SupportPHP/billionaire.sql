-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 04, 2021 at 04:02 PM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id17019440_billionaire`
--

-- --------------------------------------------------------

--
-- Table structure for table `goal`
--

CREATE TABLE `goal` (
  `ID_GOAL` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  `NAME_GOAL` varchar(100) DEFAULT NULL,
  `DESCRIPTION_GOAL` text DEFAULT NULL,
  `MONEY_GOAL` double DEFAULT NULL,
  `IMAGE_GOAL` varchar(255) DEFAULT NULL,
  `FINISH` int(11) DEFAULT NULL,
  `MONEY_SAVING` double DEFAULT NULL,
  `DATE_START` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `goal`
--

INSERT INTO `goal` (`ID_GOAL`, `ID_USER`, `NAME_GOAL`, `DESCRIPTION_GOAL`, `MONEY_GOAL`, `IMAGE_GOAL`, `FINISH`, `MONEY_SAVING`, `DATE_START`) VALUES
(138, 1, '3232323', '32323232', 3232323, '63243933_1624976829.jpeg', 0, 0, '9:26 PM ngày 29.06.2021'),
(144, 23, '32322', '3232323', 323232, '733659652_1625155378.jpeg', 0, 0, '11:02 PM ngày 01.07.2021'),
(145, 9, '112', '121212', 12122, '961212289_1625214374.jpeg', 1, 0, '3:26 PM ngày 02.07.2021'),
(148, 9, 'sieu nhan', '3232', 5000, '870406953_1625327986.jpeg', 1, 0, '10:59 PM ngày 03.07.2021'),
(149, 9, 'Lì xì cho cháu', 'Sắp năm mới phải lì xì', 100000, '208663802_1625331969.jpeg', 0, 0, '12:06 SA ngày 04.07.2021'),
(150, 16, 'dsd', 'dsds', 1000, '943554011_1625376083.jpeg', 1, 0, '12:21 PM ngày 04.07.2021'),
(151, 16, '12122', '1212', 2000, '378023021_1625376164.jpeg', 1, 0, '12:22 PM ngày 04.07.2021'),
(153, 16, '3232', '32323', 23232, '2117742914_1625411103.jpeg', 0, 0, '10:04 PM ngày 04.07.2021');

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
(1, 1, 24874461),
(2, 2, 6650000),
(3, 3, 10000000),
(9, 9, 39113774),
(13, 13, 1490000),
(14, 14, 60000),
(15, 15, 1000000),
(16, 16, 2068114),
(17, 18, 50000),
(18, 19, 2323237),
(19, 23, 1031000),
(20, 24, 100000),
(21, 25, 1000000),
(22, 26, 23232000);

-- --------------------------------------------------------

--
-- Table structure for table `incomecategory`
--

CREATE TABLE `incomecategory` (
  `ID_CATEGORY` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `IMAGE` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `incomecategory`
--

INSERT INTO `incomecategory` (`ID_CATEGORY`, `ID_USER`, `NAME`, `IMAGE`) VALUES
(1, 0, 'TIỀN THƯỞNG', 'bonus.jpeg'),
(2, 0, 'LƯƠNG', 'salary.jpeg'),
(3, 0, 'BÁN HÀNG', 'sale.jpeg'),
(4, 0, 'THU NHẬP KHÁC', 'others.jpeg'),
(24, 1, 'dsdsd', '2089478919_1624043024.jpeg'),
(25, 9, 'Test2', '945096849_1624345781.jpeg'),
(26, 9, 'Yyyyyy', '1472301277_1624941304.jpeg'),
(29, 23, 'Test2', '2133347815_1625370726.jpeg');

-- --------------------------------------------------------

--
-- Table structure for table `incomedetail`
--

CREATE TABLE `incomedetail` (
  `ID_INCOMEDETAIL` int(11) NOT NULL,
  `ID_INCOME` int(11) NOT NULL,
  `MONEY` double NOT NULL,
  `ID_CATEGORY` int(11) NOT NULL,
  `DESCRIPTION` text DEFAULT NULL,
  `DATE` datetime NOT NULL,
  `IMAGE` varchar(255) DEFAULT NULL,
  `AUDIO` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `incomedetail`
--

INSERT INTO `incomedetail` (`ID_INCOMEDETAIL`, `ID_INCOME`, `MONEY`, `ID_CATEGORY`, `DESCRIPTION`, `DATE`, `IMAGE`, `AUDIO`) VALUES
(66, 1, 2339763, 1, '', '2021-06-13 19:51:52', NULL, NULL),
(67, 2, 3633621, 1, '', '2021-06-13 19:52:18', '1796656007_1623588739.jpeg', NULL),
(68, 1, 3633621, 1, '', '2021-06-13 19:52:36', '1152826084_1623588757.jpeg', '2061062989_1623588757.3gp'),
(69, 2, 1835133, 1, '', '2021-06-13 20:03:51', '306565237_1623589432.jpeg', NULL),
(70, 1, 2513762, 1, '', '2021-06-13 20:14:09', NULL, '418963377_1623590049.3gp'),
(71, 2, 3783333, 1, '', '2021-06-14 00:30:10', '18672879_1623605414.jpeg', NULL),
(72, 1, 1504652, 1, '', '2021-06-14 09:04:35', NULL, NULL),
(73, 2, 1889908, 1, '', '2021-06-14 09:06:12', NULL, NULL),
(74, 2, 2466667, 1, '', '2021-06-14 17:33:38', NULL, NULL),
(76, 2, 2158333, 1, '', '2021-06-14 18:31:23', NULL, '343237635_1623670285.3gp'),
(77, 2, 2666667, 1, '', '2021-06-15 08:13:53', NULL, NULL),
(78, 2, 1375000, 1, '', '2021-06-15 08:15:02', NULL, NULL),
(79, 2, 1650000, 1, '', '2021-06-15 08:16:25', NULL, NULL),
(80, 1, 1883333, 1, '', '2021-06-16 12:43:26', NULL, '1385629866_1623822207.3gp'),
(81, 1, 3141667, 1, '', '2021-06-16 15:47:06', NULL, '2073624424_1623833227.3gp'),
(82, 1, 2041667, 2, '', '2021-06-16 20:30:18', NULL, NULL),
(83, 1, 833333, 3, '', '2021-06-16 20:30:28', NULL, NULL),
(84, 1, 3925000, 4, '', '2021-06-16 20:30:39', NULL, NULL),
(85, 1, 975000, 1, '', '2021-06-16 20:30:47', NULL, NULL),
(86, 1, 1408333, 3, '', '2021-06-16 20:31:00', NULL, NULL),
(87, 1, 2513762, 2, '', '2021-06-18 07:41:49', NULL, '1177312688_1623976910.3gp'),
(88, 1, 3532110, 2, '', '2021-06-18 07:43:32', NULL, '1261886149_1623977014.3gp'),
(89, 1, 2321101, 1, '', '2021-06-18 09:07:22', '933290712_1623982044.jpeg', NULL),
(90, 1, 3211009, 1, '', '2021-06-18 09:46:43', NULL, '1635930699_1623984404.3gp'),
(91, 1, 3321101, 1, '', '2021-06-18 09:49:34', NULL, '163209633_1623984575.3gp'),
(92, 1, 2110092, 1, '', '2021-06-18 10:33:52', NULL, NULL),
(93, 1, 2348624, 1, '', '2021-06-18 10:34:46', NULL, NULL),
(94, 1, 2669725, 3, '', '2021-06-18 10:39:01', NULL, NULL),
(95, 1, 899421, 1, '', '2021-06-18 10:43:21', NULL, NULL),
(96, 1, 2027523, 3, 'da cap lua dao buon ban trai phep van van va may may nhu anime nao do khong biet ten hahahahahahahh mudamudamudamuda', '2021-06-18 10:48:07', '1849006707_1623988089.jpeg', NULL),
(97, 1, 3232, 1, '3232323', '2021-06-19 00:16:15', '264397758_1624036577.jpeg', NULL),
(114, 1, 3232, 1, 'addd12', '2021-06-19 01:42:12', NULL, NULL),
(117, 1, 121, 1, '2121111', '2021-06-19 01:46:03', NULL, NULL),
(118, 1, 2600000, 1, '', '2021-06-19 07:55:59', NULL, NULL),
(120, 9, 3000000, 1, '', '2021-06-20 15:16:08', NULL, NULL),
(121, 9, 3100917, 1, '', '2021-06-20 16:47:51', NULL, NULL),
(122, 9, 3834863, 2, '', '2021-06-20 16:50:48', NULL, '1971271683_1624182649.3gp'),
(123, 9, 2807340, 2, '', '2021-06-20 16:53:41', '1709157619_1624182822.jpeg', NULL),
(124, 9, 1875000, 1, '', '2021-06-20 20:29:47', NULL, NULL),
(125, 9, 3183333, 1, '', '2021-06-20 22:58:22', NULL, NULL),
(126, 9, 1841667, 2, '', '2021-06-20 22:59:01', NULL, NULL),
(127, 9, 706422, 2, '', '2021-06-20 23:01:10', NULL, NULL),
(128, 9, 706422, 2, '', '2021-06-20 23:02:15', NULL, NULL),
(129, 9, 1600000, 3, '', '2021-06-21 09:05:19', NULL, NULL),
(130, 9, 1165138, 1, '', '2021-06-21 20:03:50', NULL, NULL),
(131, 9, 1192661, 1, '', '2021-06-22 14:12:03', NULL, NULL),
(132, 13, 100000, 1, '', '2021-06-22 14:34:27', NULL, '366967549_1624347269.3gp'),
(133, 14, 60000, 1, 'Mẹ cho', '2021-06-22 14:41:37', NULL, NULL),
(134, 9, 1000000, 1, '', '2021-06-22 15:00:03', NULL, NULL),
(135, 9, 500000, 1, '', '2021-06-23 07:10:23', NULL, NULL),
(136, 9, 60000, 1, 'Mẹ cho', '2021-06-23 20:51:06', '1420413420_1624456268.jpeg', NULL),
(137, 9, 2000000, 2, 'Mới lãnh', '2021-06-23 21:56:01', NULL, NULL),
(138, 9, 500000, 1, '', '2021-06-24 11:17:51', NULL, '815343274_1624508272.3gp'),
(139, 13, 30000, 2, '', '2021-06-25 21:07:49', NULL, NULL),
(140, 13, 50000, 3, '', '2021-06-25 21:09:43', NULL, NULL),
(141, 13, 50000, 4, '', '2021-06-25 21:12:50', NULL, NULL),
(142, 13, 10000, 1, '', '2021-06-25 21:15:38', NULL, NULL),
(143, 13, 5000, 1, '', '2021-06-25 21:19:16', NULL, NULL),
(144, 13, 5000, 1, '', '2021-06-25 21:20:44', NULL, NULL),
(145, 13, 10000, 1, '', '2021-06-25 21:26:16', NULL, NULL),
(146, 13, 10000, 1, '', '2021-06-25 22:01:08', NULL, NULL),
(147, 13, 10000, 2, '', '2021-06-25 22:01:22', NULL, NULL),
(148, 13, 10000, 1, '', '2021-06-25 22:03:04', NULL, NULL),
(149, 13, 100000, 1, '', '2021-06-25 22:18:36', NULL, NULL),
(150, 13, 100000, 1, '', '2021-06-25 22:50:04', NULL, NULL),
(156, 1, 111, 3, '1111111111', '2021-06-26 23:02:05', NULL, NULL),
(157, 1, 111, 1, '112121212', '2021-06-26 23:05:58', NULL, NULL),
(158, 1, 12121, 2, '11', '2021-06-26 23:10:27', NULL, NULL),
(160, 1, 111, 24, '1111', '2021-06-27 09:16:00', NULL, NULL),
(161, 1, 111, 3, '11111', '2021-06-27 09:24:19', NULL, NULL),
(162, 1, 111, 2, '1121', '2021-06-27 09:25:57', NULL, NULL),
(163, 1, 111, 2, '1121', '2021-06-27 09:25:57', NULL, NULL),
(164, 1, 1111, 2, '1111', '2021-06-27 09:31:01', NULL, NULL),
(165, 1, 1111, 2, '1111', '2021-06-27 09:31:01', NULL, NULL),
(166, 1, 111, 3, '1111', '2021-06-27 09:33:00', NULL, NULL),
(167, 1, 111, 3, '1111', '2021-06-27 09:33:00', NULL, NULL),
(168, 1, 111, 24, '1111', '2021-06-27 09:16:00', NULL, NULL),
(169, 1, 111, 3, '1111', '2021-06-27 09:39:04', NULL, NULL),
(170, 1, 111, 3, '1111', '2021-06-27 09:39:04', NULL, NULL),
(171, 1, 111, 3, '1111', '2021-06-27 09:42:36', NULL, NULL),
(172, 1, 111, 3, '1111', '2021-06-27 09:42:36', NULL, NULL),
(173, 1, 111, 3, '111', '2021-06-27 09:49:38', NULL, NULL),
(174, 1, 111, 24, '111', '2021-06-27 09:57:02', NULL, NULL),
(175, 1, 22232323, 24, '111', '2021-06-27 09:58:34', NULL, NULL),
(176, 1, 111, 3, '111', '2021-06-27 10:00:25', NULL, NULL),
(177, 1, 222, 24, '111', '2021-06-27 10:00:59', NULL, NULL),
(178, 1, 222, 24, '111', '2021-06-27 10:00:59', NULL, NULL),
(179, 1, 555, 3, '112', '2021-06-27 10:46:11', NULL, NULL),
(180, 1, 555, 3, '112', '2021-06-27 10:46:11', NULL, NULL),
(181, 9, 5555555, 1, 'fffff\nggg', '2021-06-29 10:47:54', NULL, NULL),
(182, 9, 10000, 1, '', '2021-06-29 16:41:49', NULL, NULL),
(183, 16, 2051114, 2, '', '2021-06-29 18:21:24', NULL, NULL),
(184, 18, 2313237, 1, 'hgmmmm', '2021-06-30 17:02:21', NULL, NULL),
(185, 9, 3204456, 1, 'mat', '2021-06-30 17:04:01', NULL, NULL),
(186, 9, 10000, 1, '', '2021-07-01 10:33:35', NULL, NULL),
(187, 9, 1000000, 1, '', '2021-07-01 10:33:56', NULL, NULL),
(188, 19, 10000, 1, '', '2021-07-01 11:27:23', NULL, NULL),
(189, 19, 11000, 4, '43434', '2021-07-01 23:41:08', NULL, NULL),
(190, 19, 10000, 2, '2323', '2021-07-02 08:44:45', NULL, NULL),
(191, 9, 13000, 1, '111', '2021-07-02 16:37:59', NULL, NULL),
(192, 9, 0, 1, '', '2021-07-03 13:40:49', NULL, NULL),
(193, 9, 5000, 1, '1212', '2021-07-03 23:00:53', '1474578144_1625328055.jpeg', NULL),
(194, 9, 10000, 1, '', '2021-07-03 23:35:26', NULL, NULL),
(195, 9, 10000, 1, '', '2021-07-03 23:35:39', NULL, NULL),
(196, 9, 10000, 1, '', '2021-07-03 23:36:50', NULL, NULL),
(197, 9, 10000, 1, '', '2021-07-03 23:42:25', NULL, NULL),
(198, 9, 10000, 1, '', '2021-07-03 23:44:01', NULL, NULL),
(199, 9, 10000, 1, '', '2021-07-03 23:45:45', NULL, NULL),
(200, 9, 1000, 1, '', '2021-07-03 23:46:04', NULL, NULL),
(201, 9, 1000, 1, '', '2021-07-03 23:47:17', NULL, NULL),
(202, 9, 10000, 1, '', '2021-07-03 23:48:38', '2055716691_1625330923.jpeg', NULL),
(203, 9, 10000, 1, '', '2021-07-03 23:50:30', NULL, '307919295_1625331034.3gp'),
(204, 9, 10000, 1, 'Tesss3', '2021-07-03 23:53:03', '1892089823_1625331187.jpeg', '217109141_1625331187.3gp'),
(205, 9, 10000, 1, '', '2021-07-04 00:06:25', NULL, NULL),
(206, 9, 10000, 1, '', '2021-07-04 00:06:57', NULL, '1041723755_1625332020.3gp'),
(207, 9, 10000, 1, '', '2021-07-04 00:07:19', '164524964_1625332043.jpeg', NULL),
(208, 9, 10000, 1, 'Năm mới tết đến sắp phải lì xì', '2021-07-04 00:07:55', '894482662_1625332079.jpeg', '779976047_1625332079.3gp'),
(210, 16, 2000, 1, '', '2021-07-04 12:22:17', NULL, NULL),
(211, 16, 5000, 1, '', '2021-07-04 12:38:17', NULL, NULL);

--
-- Triggers `incomedetail`
--
DELIMITER $$
CREATE TRIGGER `INS_incomedetail` AFTER INSERT ON `incomedetail` FOR EACH ROW BEGIN

	DECLARE money integer;
    DECLARE id_income integer;
    DECLARE id_user integer;
    DECLARE id_incomedetail integer;
    DECLARE goalMoney integer;

	set @money = NEW.MONEY;
	 
	set @id_income = NEW.ID_INCOME;
    
    set @id_user = (SELECT income.ID_USER FROM income WHERE income.ID_INCOME  = @id_income LIMIT 1);
    
    
    UPDATE income
    set income.TOTAL_INCOME = income.TOTAL_INCOME + @money
    WHERE income.ID_INCOME = @id_income;
		
	
    UPDATE saving
    set saving.TOTAL_INCOME = saving.TOTAL_INCOME +  @money , saving.TOTAL_SAVING  = saving.TOTAL_SAVING + @money
    where saving.ID_USER = @id_user;
    
    
    
    -- update goal 
    BEGIN
    DECLARE moneyGoal,moneySaving integer;
    DECLARE id_goal integer;

	select goal.ID_GOAL, goal.MONEY_GOAL, goal.MONEY_SAVING
	into @id_goal, @moneyGoal, @moneySaving 
    from goal 
	where goal.ID_USER = @id_user AND goal.FINISH = 0	
    ORDER BY goal.ID_GOAL DESC
	LIMIT 1;
    
    
    
    
     if( (@money + @moneySaving) >= @moneyGoal )
     THEN
    	UPDATE goal
        SET goal.FINISH = 1
     	where goal.ID_GOAL = @id_goal;
     
     ELSE
     	UPDATE goal
        SET goal.MONEY_SAVING   = goal.MONEY_SAVING + @money
     	where goal.ID_GOAL = @id_goal;
     
     END IF;

  END;
   
    
    
    
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `outcomdetail`
--

CREATE TABLE `outcomdetail` (
  `ID_OUTCOMEDETAIL` int(11) NOT NULL,
  `ID_OUTCOME` int(11) NOT NULL,
  `MONEY` double NOT NULL,
  `ID_CATEGORY` int(11) NOT NULL,
  `DESCRIPTION` text COLLATE utf8_unicode_ci NOT NULL,
  `DATE` datetime NOT NULL,
  `IMAGE` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AUDIO` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `outcomdetail`
--

INSERT INTO `outcomdetail` (`ID_OUTCOMEDETAIL`, `ID_OUTCOME`, `MONEY`, `ID_CATEGORY`, `DESCRIPTION`, `DATE`, `IMAGE`, `AUDIO`) VALUES
(1, 1, 3633621, 9, '', '2021-06-13 19:52:43', '1164344069_1623588762.jpeg', '1816217436_1623588762.3gp'),
(2, 1, 1670059, 2, '', '2021-06-13 20:03:38', NULL, NULL),
(6, 2, 3016667, 17, '', '2021-06-14 18:32:38', NULL, NULL),
(7, 2, 2675000, 1, '', '2021-06-15 08:18:41', NULL, NULL),
(18, 1, 3325000, 1, '', '2021-06-17 22:20:54', NULL, NULL),
(19, 1, 2766667, 4, 'Mua cardfight vanguard + pokemon + yugioh + battle spirit + digimon', '2021-06-17 22:23:22', '426053542_1623943404.jpeg', '1550460854_1623943404.3gp'),
(20, 1, 3933334, 4, '1 lá bài trong vanguard', '2021-06-18 09:22:59', '876900498_1623982982.jpeg', NULL),
(21, 1, 3350000, 7, 'Kiêt sức', '2021-06-18 09:24:52', '1183288472_1623983096.jpeg', NULL),
(22, 1, 3211009, 19, 'Blue Eye White Dragon\nDark Magician', '2021-06-18 10:12:56', NULL, NULL),
(23, 1, 2944954, 19, 'Blue Eye White Dragon', '2021-06-18 10:24:05', NULL, NULL),
(24, 1, 1357798, 1, '', '2021-06-18 13:56:54', '1872195017_1623999415.jpeg', NULL),
(33, 1, 3733333, 1, '', '2021-06-18 23:41:19', NULL, NULL),
(34, 1, 232323, 1, '3232', '2021-06-19 01:49:48', '1669661008_1624042190.jpeg', NULL),
(36, 9, 1385321, 1, '', '2021-06-20 16:48:15', '558979995_1624182496.jpeg', NULL),
(37, 9, 2192661, 1, '', '2021-06-20 16:50:33', NULL, '474095875_1624182634.3gp'),
(38, 9, 2293578, 1, '', '2021-06-20 16:52:54', '120516168_1624182776.jpeg', NULL),
(39, 9, 1247706, 1, '', '2021-06-20 16:53:59', '267874627_1624182841.jpeg', NULL),
(40, 9, 3575000, 1, '', '2021-06-20 20:17:11', NULL, NULL),
(41, 9, 3583333, 1, '', '2021-06-20 20:29:57', NULL, NULL),
(42, 9, 2108333, 20, 'net cafe', '2021-06-20 20:30:38', NULL, NULL),
(43, 9, 1678899, 1, '', '2021-06-20 23:08:03', NULL, NULL),
(44, 9, 1625000, 4, '', '2021-06-21 07:18:53', '1674239821_1624234736.jpeg', NULL),
(45, 9, 1450000, 2, '', '2021-06-21 08:19:37', NULL, NULL),
(46, 9, 3990826, 1, '', '2021-06-21 20:02:36', NULL, NULL),
(47, 9, 1330591, 1, '', '2021-06-22 14:12:21', NULL, NULL),
(48, 13, 50000, 1, 'Ăn sáng', '2021-06-22 14:33:53', NULL, NULL),
(49, 14, 20000, 1, 'Bánh mì', '2021-06-22 14:41:12', NULL, NULL),
(50, 14, 17000, 4, 'tiền mua sắm', '2021-06-22 14:42:13', NULL, NULL),
(51, 9, 50000, 1, '', '2021-06-22 14:57:55', NULL, NULL),
(52, 9, 100000, 1, '', '2021-06-22 14:58:29', NULL, NULL),
(53, 1, 1651376, 1, '', '2021-06-22 15:39:30', NULL, NULL),
(54, 9, 50000, 1, 'Ăn tối', '2021-06-23 20:50:28', NULL, NULL),
(55, 13, 70000, 1, 'Ăn hủ tiếu gõ', '2021-06-25 11:15:19', NULL, NULL),
(56, 13, 50000, 1, '', '2021-06-25 21:02:52', NULL, NULL),
(57, 13, 5000, 1, '', '2021-06-25 21:18:50', NULL, NULL),
(58, 13, 10000, 2, '', '2021-06-25 21:25:16', NULL, NULL),
(59, 13, 50000, 1, '', '2021-06-25 21:28:07', NULL, NULL),
(60, 13, 100000, 1, '', '2021-06-25 21:56:33', NULL, NULL),
(61, 9, 150000, 1, '', '2021-06-26 14:27:12', NULL, NULL),
(62, 1, 777, 21, '1111', '2021-06-27 10:42:10', NULL, NULL),
(63, 1, 111, 21, '111', '2021-06-27 10:44:02', NULL, NULL),
(64, 1, 111, 21, '111', '2021-06-27 10:45:18', NULL, NULL),
(65, 9, 1000000, 4, 'Hộp bài DSS cardfight vanguard', '2021-06-27 14:46:18', '506615272_1624779983.jpeg', NULL),
(66, 16, 21212121, 2, '323232323', '2021-06-29 18:29:12', '1609976030_1624966154.jpeg', NULL),
(67, 9, 100000, 5, '', '2021-06-29 23:32:35', '1578139774_1624984358.jpeg', '168405406_1624984358.3gp'),
(68, 9, 200000, 4, '', '2021-06-29 23:33:14', '37462768_1624984396.jpeg', NULL),
(69, 18, 688073, 2, 'mat', '2021-06-30 17:02:44', NULL, NULL),
(70, 9, 100000, 1, '', '2021-07-01 11:04:45', NULL, NULL),
(71, 14, 10000, 1, '', '2021-07-01 11:26:23', NULL, NULL),
(72, 22, 111000, 1, '121212', '2021-07-01 23:21:09', '584355947_1625156473.jpeg', NULL),
(73, 16, 1430000, 9, '3232323323323', '2021-07-03 02:07:12', '1111898803_1625252833.jpeg', '618867161_1625252833.3gp'),
(74, 9, 20000, 1, 'Ăn sáng', '2021-07-03 08:16:30', NULL, '466794264_1625274997.3gp'),
(75, 9, 10000, 1, '', '2021-07-03 23:42:04', NULL, NULL),
(76, 16, 2000, 2, 'oiyo', '2021-07-04 12:21:43', NULL, NULL),
(77, 22, 10000, 1, '', '2021-07-04 14:13:19', NULL, NULL),
(78, 22, 10000, 1, '', '2021-07-04 14:13:08', NULL, NULL),
(79, 22, 10000, 1, '', '2021-07-04 14:13:08', NULL, NULL),
(80, 22, 10000, 1, '', '2021-07-04 14:13:19', NULL, NULL),
(81, 9, 100000, 4, '', '2021-07-04 18:45:13', NULL, NULL),
(82, 9, 100000, 1, 'Test lỗi database', '2021-07-04 20:12:16', '1876971581_1625404340.jpeg', '1549792158_1625404340.3gp');

--
-- Triggers `outcomdetail`
--
DELIMITER $$
CREATE TRIGGER `INS_outcomedetail` AFTER INSERT ON `outcomdetail` FOR EACH ROW BEGIN

	DECLARE money integer;
    DECLARE id_outcome integer;
    DECLARE id_user integer;
    DECLARE id_incomedetail integer;

	set @money = NEW.MONEY;
	 
	set @id_outcome = NEW.ID_OUTCOME;
    
    set @id_user = (SELECT outcome.ID_USER FROM outcome WHERE outcome.ID_OUTCOME = @id_outcome LIMIT 1);
		
        
    UPDATE outcome
    set outcome.TOTAL_OUTCOME = outcome.TOTAL_OUTCOME + @money
    WHERE outcome.ID_OUTCOME = @id_outcome;
    
	UPDATE saving
    set saving.TOTAL_OUTCOME = saving.TOTAL_OUTCOME  +  @money , saving.TOTAL_SAVING  = saving.TOTAL_SAVING - @money
    where saving.ID_USER = @id_user;
    
 
   -- update goal 
    BEGIN
    DECLARE moneyGoal,moneySaving integer;
    DECLARE id_goal integer;

	select goal.ID_GOAL, goal.MONEY_GOAL, goal.MONEY_SAVING
	into @id_goal, @moneyGoal, @moneySaving 
    from goal 
	where goal.ID_USER = @id_user AND goal.FINISH = 0	
    ORDER BY goal.ID_GOAL DESC
	LIMIT 1;
    
    
    
    
     if( (@moneySaving - @money ) >= 0 )
     THEN
    	UPDATE goal
        SET goal.MONEY_SAVING = goal.MONEY_SAVING - @money
     	where goal.ID_GOAL = @id_goal;
     
     ELSE
     	UPDATE goal
        SET goal.MONEY_SAVING   =  0
     	where goal.ID_GOAL = @id_goal;
     
     END IF;

  END;
    
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `outcome`
--

CREATE TABLE `outcome` (
  `ID_OUTCOME` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  `TOTAL_OUTCOME` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `outcome`
--

INSERT INTO `outcome` (`ID_OUTCOME`, `ID_USER`, `TOTAL_OUTCOME`) VALUES
(1, 1, 27006793),
(2, 2, 2875000),
(3, 3, 1000000),
(9, 9, 28441248),
(13, 13, 335000),
(14, 14, 47000),
(15, 15, 0),
(16, 16, 22644121),
(17, 18, 0),
(18, 19, 688073),
(19, 20, 0),
(20, 21, 0),
(22, 23, 151000),
(23, 24, 0),
(24, 25, 0),
(25, 26, 0);

-- --------------------------------------------------------

--
-- Table structure for table `outcomecategory`
--

CREATE TABLE `outcomecategory` (
  `ID_CATEGORY` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `IMAGE` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `outcomecategory`
--

INSERT INTO `outcomecategory` (`ID_CATEGORY`, `ID_USER`, `NAME`, `IMAGE`) VALUES
(1, 0, 'THỨC ĂN', 'eating.jpeg'),
(2, 0, 'HÓA ĐƠN', 'bill.jpeg'),
(3, 0, 'DI CHUYỂN', 'transport.jpeg'),
(4, 0, 'MUA SẮM', 'shopping.jpeg'),
(5, 0, 'BẠN BÈ', 'friend.jpeg'),
(6, 0, 'GIẢI TRÍ', 'entertainment.jpeg'),
(7, 0, 'SỨC KHỎE', 'health.jpeg'),
(8, 0, 'GIA ĐÌNH', 'home.jpeg'),
(9, 0, 'GIÁO DỤC', 'education.jpeg'),
(10, 0, 'CHI TIÊU KHÁC', 'others.jpeg'),
(17, 2, 'New Year Lucky', '924932629_1623670348.jpeg'),
(18, 1, 'BILL', '1517612271_1623752347.jpeg'),
(19, 1, 'Card Game', '1451218315_1623985941.jpeg'),
(20, 9, 'Net', '307362185_1624195823.jpeg'),
(21, 13, 'Bill', '1949943670_1624639115.jpeg');

-- --------------------------------------------------------

--
-- Table structure for table `saving`
--

CREATE TABLE `saving` (
  `ID_SAVING` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  `TOTAL_SAVING` double NOT NULL,
  `TOTAL_INCOME` double NOT NULL,
  `TOTAL_OUTCOME` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `saving`
--

INSERT INTO `saving` (`ID_SAVING`, `ID_USER`, `TOTAL_SAVING`, `TOTAL_INCOME`, `TOTAL_OUTCOME`) VALUES
(1, 2, 1000000, 2000000, 1000000),
(2, 1, 25631714, 41018018, 15886304),
(3, 9, 8523672, 26270654, 17746982),
(4, 13, 155000, 490000, 335000),
(5, 14, 13000, 60000, 47000),
(6, 15, 0, 0, 0),
(7, 16, -20586007, 2058114, 22644121),
(8, 18, 0, 0, 0),
(9, 19, 1625164, 2313237, 688073),
(10, 20, 0, 0, 0),
(11, 21, 0, 0, 0),
(13, 23, -120000, 31000, 151000),
(14, 24, 0, 0, 0),
(15, 25, 0, 0, 0),
(16, 26, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `savingdetail`
--

CREATE TABLE `savingdetail` (
  `ID_SAVINGDETAIL` int(11) NOT NULL,
  `ID_SAVING` int(11) NOT NULL,
  `SAVING_PER_DAY` double NOT NULL,
  `INCOME_PER_DAY` double NOT NULL,
  `OUTCOME_PER_DAY` double NOT NULL,
  `DATE` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `savingdetail`
--

INSERT INTO `savingdetail` (`ID_SAVINGDETAIL`, `ID_SAVING`, `SAVING_PER_DAY`, `INCOME_PER_DAY`, `OUTCOME_PER_DAY`, `DATE`) VALUES
(1, 1, 10000, 100000, 90000, '2021-06-20'),
(2, 1, 10000, 100000, 90000, '2021-06-18'),
(3, 1, 10000, 200000, 190000, '2021-06-19'),
(4, 1, 10000, 30000, 20000, '2021-06-13'),
(5, 3, -1377762, 8847554, 10225316, '2021-06-22'),
(10, 3, 1000000, 200000, 190000, '2021-06-21'),
(11, 4, 50000, 100000, 50000, '2021-06-22'),
(12, 5, 10000, 15000, 5000, '2021-06-17'),
(13, 5, -12000, 8000, 20000, '2021-06-19'),
(16, 5, 30000, 60000, 30000, '2021-06-20'),
(17, 5, -5000, 0, 5000, '2021-06-21'),
(18, 5, 1000, 60000, 59000, '2021-06-22'),
(19, 5, 30000, 30000, 0, '2021-06-23'),
(20, 5, -12000, 0, 12000, '2021-06-24'),
(21, 5, -3000, 3000, 6000, '2021-06-26'),
(22, 5, 1000, 1000, 0, '2021-06-27'),
(23, 5, 10000, 20000, 10000, '2021-06-25'),
(24, 2, -1651376, 0, 1651376, '2021-06-22'),
(25, 3, 2510000, 2560000, 50000, '2021-06-23'),
(26, 3, 500000, 500000, 0, '2021-06-24'),
(27, 4, 105000, 390000, 285000, '2021-06-25'),
(28, 3, -150000, 0, 150000, '2021-06-26'),
(29, 3, -1000000, 0, 1000000, '2021-06-27'),
(30, 3, 5265555, 5565555, 300000, '2021-06-29'),
(31, 7, -19161007, 2051114, 21212121, '2021-06-29'),
(32, 9, 1625164, 2313237, 688073, '2021-06-30'),
(33, 3, 3204456, 3204456, 0, '2021-06-30'),
(34, 3, 910000, 1010000, 100000, '2021-07-01'),
(35, 5, -10000, 0, 10000, '2021-07-01'),
(36, 13, -90000, 21000, 111000, '2021-07-01'),
(37, 13, 10000, 10000, 0, '2021-07-02'),
(38, 3, 13000, 13000, 0, '2021-07-02'),
(39, 7, -1430000, 0, 1430000, '2021-07-03'),
(40, 3, 67000, 97000, 30000, '2021-07-03'),
(41, 3, -150000, 50000, 200000, '2021-07-04'),
(42, 3, 10000, 100000, 110000, '2021-06-20'),
(43, 7, 5000, 7000, 2000, '2021-07-04'),
(44, 13, -40000, 0, 40000, '2021-07-04');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `ID_USER` int(11) NOT NULL,
  `USERNAME` varchar(100) NOT NULL,
  `PASSWORD` text NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `FULLNAME` varchar(100) NOT NULL,
  `DATESTART` datetime NOT NULL,
  `USERIMAGE` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID_USER`, `USERNAME`, `PASSWORD`, `EMAIL`, `FULLNAME`, `DATESTART`, `USERIMAGE`) VALUES
(1, 'minhquan2k1', '$2y$10$l/YHgAfA5U5uC1oITHeXOejWT0fYU.L2a5qeP5UwndgYzri7SkmfG', 'minhquan2k1@gmail.com', 'SUPER PRO VJP1124', '2021-05-30 16:13:07', '161130523_1624910653.jpeg'),
(2, 'ductam2k1', '12345678', 'ductam2k1@gmail.com', 'Tran Duc Tam', '2021-06-02 13:43:00', NULL),
(3, 'hoangson2k1', '12345678', 'hoangson2k1@gmail.com', 'Dang Hai Hoang Son', '2021-06-06 14:40:00', NULL),
(9, 'test2', '$2y$10$xEG.BCxCZd5qEyCtKGF9TezAcClpanQfXJup92.rRugv42HXHrQ/q', '131@gmail.com', 'Đặng Hải Hoàng Sơn', '2021-06-20 09:06:51', '623543528_1624428376.jpeg'),
(13, 'minhquan2k1bt', '$2y$10$ahJmMwQ5f.EaNx4.USKNfOJJTyJ.CoSYnvD8BlUfi4PmKu4328beO', 'minhquancn1809@gmail.com', 'Cao Nguyễn Minh Quân', '2021-06-22 14:32:23', '1630830302_1624347145.jpeg'),
(14, 'test3', '$2y$10$OPNG6cJ4UHMIc.5cGqh2.eyyy.Hnc8PjZRZFhccWgjiDxEC4j5xfC', '121@gmail.com', 'Nguyen Van B', '2021-06-22 14:36:44', NULL),
(15, 'test4', '$2y$10$F/lFGZFYkr6HCO3U9ska5O0YpvzN/6y/tUSWd5QnVqAIK3wR3jlX2', 'abcd@gmail.com', 'Nguyen Van C', '2021-06-23 21:36:23', '1089563858_1624459457.jpeg'),
(16, 'sonvippro', '$2y$10$Dc0GhjhbjiTlgjAHcNx5Bu3aoqDnSFgeFxP9voHvSD7FGUe13XRrK', 'mukamuka3052001@gmail.com', 'Banh bao chien1112', '2021-06-29 17:50:53', '158373161_1625374017.jpeg'),
(17, '1', '1', '1@gmail.com', 'Sieu Nhan', '2021-06-29 18:15:30', NULL),
(18, '//a.dasd', '$2y$10$PLjSyFKsURdvEOYCc/xrMuUlkj32kOM78UgCegATwms.Do65IyCCy', 'asdasd@gm.cm', 'son', '2021-06-30 15:40:54', NULL),
(19, 'mukamuka', '$2y$10$IFuaDRGaKWg8sQJMVC6uxO7Et2hfKpRcFXDJ2VTITE9KL3yjyOsUG', 'mukamuka@gmail.com', 'son', '2021-06-30 16:02:19', NULL),
(20, '1121212', '$2y$10$4u.6qjLlwsBZsF38ddfWF.QqZhv.uZQtX//xZZNunfWHzOSYyjD.q', 'dieu@gmail.com', '2121212', '2021-06-30 23:01:43', '30732404_1625068905.jpeg'),
(21, '32323', '$2y$10$.i/NygR./6DtWX.2U343q.G2fzVBm3J1vpQKDFoZh6cRBetSlGqx6', '2232@gmail.com', '23232', '2021-07-01 00:09:04', NULL),
(23, 'userforprice', '$2y$10$X7RSvPnAjq9hp8aLMt1EUuMZJuAWFmdFo4bE0ut23egpGhgdrvzrm', 'tac@gmail.com', 'Trần An Cư', '2021-07-01 10:38:54', '1731088247_1625327095.jpeg'),
(24, 'userforprice2', '$2y$10$Jf7gUiIyIgynImKZ51HUaO45X6regk/XFjAAKiRHf0VZZVlNcYrP.', 'tac2@gmail.com', 'Trần An Cư', '2021-07-01 10:39:57', '1998803153_1625110798.jpeg'),
(25, 'userforlife', '$2y$10$6YpSvzkpb/L/SxWAC6IXH.GuvgZQI2FipZUPCmOgYd8FNSmQio/7q', 'minhquan2001@gmail.com', 'Cao Nguyễn Minh Quân', '2021-07-04 20:21:39', '2018346680_1625404912.jpeg'),
(26, '232', '$2y$10$KRgimvznjJI4o/TsGyLDqe6H7mq9OPvM4sMaE1cHUiDiocBYn10zm', '32@gmail.com', '2323', '2021-07-04 22:02:41', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `goal`
--
ALTER TABLE `goal`
  ADD PRIMARY KEY (`ID_GOAL`),
  ADD KEY `ID_USER` (`ID_USER`);

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
  ADD PRIMARY KEY (`ID_CATEGORY`);

--
-- Indexes for table `incomedetail`
--
ALTER TABLE `incomedetail`
  ADD PRIMARY KEY (`ID_INCOMEDETAIL`),
  ADD KEY `ID_INCOME` (`ID_INCOME`),
  ADD KEY `ID_CATEGORY` (`ID_CATEGORY`);

--
-- Indexes for table `outcomdetail`
--
ALTER TABLE `outcomdetail`
  ADD PRIMARY KEY (`ID_OUTCOMEDETAIL`),
  ADD KEY `ID_OUTCOME` (`ID_OUTCOME`),
  ADD KEY `ID_CATEGORY` (`ID_CATEGORY`);

--
-- Indexes for table `outcome`
--
ALTER TABLE `outcome`
  ADD PRIMARY KEY (`ID_OUTCOME`),
  ADD KEY `ID_USER` (`ID_USER`);

--
-- Indexes for table `outcomecategory`
--
ALTER TABLE `outcomecategory`
  ADD PRIMARY KEY (`ID_CATEGORY`);

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
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID_USER`),
  ADD UNIQUE KEY `USERNAME` (`USERNAME`,`EMAIL`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `goal`
--
ALTER TABLE `goal`
  MODIFY `ID_GOAL` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=154;

--
-- AUTO_INCREMENT for table `income`
--
ALTER TABLE `income`
  MODIFY `ID_INCOME` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `incomecategory`
--
ALTER TABLE `incomecategory`
  MODIFY `ID_CATEGORY` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `incomedetail`
--
ALTER TABLE `incomedetail`
  MODIFY `ID_INCOMEDETAIL` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=212;

--
-- AUTO_INCREMENT for table `outcomdetail`
--
ALTER TABLE `outcomdetail`
  MODIFY `ID_OUTCOMEDETAIL` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=83;

--
-- AUTO_INCREMENT for table `outcome`
--
ALTER TABLE `outcome`
  MODIFY `ID_OUTCOME` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `outcomecategory`
--
ALTER TABLE `outcomecategory`
  MODIFY `ID_CATEGORY` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `saving`
--
ALTER TABLE `saving`
  MODIFY `ID_SAVING` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `savingdetail`
--
ALTER TABLE `savingdetail`
  MODIFY `ID_SAVINGDETAIL` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `ID_USER` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `goal`
--
ALTER TABLE `goal`
  ADD CONSTRAINT `goal_ibfk_1` FOREIGN KEY (`ID_USER`) REFERENCES `user` (`ID_USER`);

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
  ADD CONSTRAINT `incomedetail_ibfk_2` FOREIGN KEY (`ID_CATEGORY`) REFERENCES `incomecategory` (`ID_CATEGORY`);

--
-- Constraints for table `outcomdetail`
--
ALTER TABLE `outcomdetail`
  ADD CONSTRAINT `outcomdetail_ibfk_1` FOREIGN KEY (`ID_OUTCOME`) REFERENCES `outcome` (`ID_OUTCOME`),
  ADD CONSTRAINT `outcomdetail_ibfk_2` FOREIGN KEY (`ID_CATEGORY`) REFERENCES `outcomecategory` (`ID_CATEGORY`);

--
-- Constraints for table `outcome`
--
ALTER TABLE `outcome`
  ADD CONSTRAINT `outcome_ibfk_1` FOREIGN KEY (`ID_USER`) REFERENCES `user` (`ID_USER`);

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
