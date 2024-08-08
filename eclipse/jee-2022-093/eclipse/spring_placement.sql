-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2023 at 10:41 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spring_placement`
--

-- --------------------------------------------------------

--
-- Table structure for table `academicyear`
--

CREATE TABLE `academicyear` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `academicyear`
--

INSERT INTO `academicyear` (`id`, `name`) VALUES
(2, '1st Year'),
(3, '2nd Year'),
(4, '3rd Year'),
(5, 'Passout');

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `id` int(11) NOT NULL,
  `course` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`id`, `course`, `name`) VALUES
(1, 3, 'Computer science and Engineering'),
(2, 1, 'MCA'),
(3, 3, 'Electrical and Electronics Engineering'),
(4, 3, 'Electronics and Communication Engineering');

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `website` varchar(255) NOT NULL,
  `about` text DEFAULT NULL,
  `startedyear` int(11) NOT NULL,
  `recruitmenthead` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`id`, `user`, `website`, `about`, `startedyear`, `recruitmenthead`) VALUES
(1, 3, 'https://test.com', 'test', 2002, 'test');

-- --------------------------------------------------------

--
-- Table structure for table `contactus`
--

CREATE TABLE `contactus` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `message` text NOT NULL,
  `creationtime` timestamp NOT NULL DEFAULT current_timestamp(),
  `ipaddress` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `contactus`
--

INSERT INTO `contactus` (`id`, `name`, `email`, `message`, `creationtime`, `ipaddress`) VALUES
(1, 'test', 'a@b.com', 'test test', '2023-03-22 22:05:40', '0:0:0:0:0:0:0:1'),
(2, 'test', 'a@b.com', 'test test', '2023-03-22 22:06:16', '0:0:0:0:0:0:0:1'),
(3, 'test', 'a@b.com', 'test test', '2023-03-22 22:06:45', '0:0:0:0:0:0:0:1'),
(4, 'test', 'a@b.com', 'tesst test', '2023-03-22 22:08:44', '0:0:0:0:0:0:0:1'),
(5, 'test', 'a@b.com', 'test test', '2023-03-22 22:05:40', '0:0:0:0:0:0:0:1'),
(6, 'test', 'a@b.com', 'test test', '2023-03-22 22:06:16', '0:0:0:0:0:0:0:1'),
(7, 'test', 'a@b.com', 'test test', '2023-03-22 22:06:45', '0:0:0:0:0:0:0:1'),
(8, 'test', 'a@b.com', 'tesst test', '2023-03-22 22:08:44', '0:0:0:0:0:0:0:1'),
(9, 'test', 'vijayis2010@gmail.com', 'test test', '2023-03-23 06:20:38', '0:0:0:0:0:0:0:1');

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`id`, `name`) VALUES
(1, 'MCA'),
(3, 'Diploma Engineering');

-- --------------------------------------------------------

--
-- Table structure for table `jobpost`
--

CREATE TABLE `jobpost` (
  `id` int(11) NOT NULL,
  `company` int(11) NOT NULL,
  `jobtitle` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `location` varchar(255) NOT NULL,
  `fromdate` date NOT NULL,
  `todate` date NOT NULL,
  `minimumsalary` float NOT NULL,
  `maximumsalary` float NOT NULL,
  `requiredscore` float NOT NULL,
  `creator` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 0,
  `date_added` timestamp NOT NULL DEFAULT current_timestamp(),
  `date_updated` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jobpost`
--

INSERT INTO `jobpost` (`id`, `company`, `jobtitle`, `description`, `location`, `fromdate`, `todate`, `minimumsalary`, `maximumsalary`, `requiredscore`, `creator`, `status`, `date_added`, `date_updated`) VALUES
(1, 3, 'test', '<p>test</p>\r\n', 'test', '2023-03-21', '2023-03-22', 400000, 300000, 70, 1, 1, '2023-03-21 17:00:21', '2023-03-22 20:53:42'),
(2, 3, 'test1', '<p>test1</p>\r\n', 'test', '2023-03-25', '2023-03-25', 500000, 400000, 65, 3, 1, '2023-03-22 03:55:29', '2023-03-22 20:51:54'),
(4, 3, 'test', '<p>test</p>\r\n', 'test', '2023-03-24', '2023-03-25', 400000, 400000, 65, 3, 1, '2023-03-22 18:16:01', '2023-03-22 20:18:37');

-- --------------------------------------------------------

--
-- Table structure for table `jobpost_academicyear`
--

CREATE TABLE `jobpost_academicyear` (
  `id` int(11) NOT NULL,
  `jobpost` int(11) NOT NULL,
  `academicyear` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jobpost_academicyear`
--

INSERT INTO `jobpost_academicyear` (`id`, `jobpost`, `academicyear`) VALUES
(9, 2, 5),
(13, 4, 4),
(15, 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `jobpost_apply`
--

CREATE TABLE `jobpost_apply` (
  `id` int(11) NOT NULL,
  `jobpost` int(11) NOT NULL,
  `student` int(11) NOT NULL,
  `status` varchar(255) NOT NULL,
  `date_added` timestamp NOT NULL DEFAULT current_timestamp(),
  `date_updated` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jobpost_apply`
--

INSERT INTO `jobpost_apply` (`id`, `jobpost`, `student`, `status`, `date_added`, `date_updated`) VALUES
(1, 1, 2, '0', '2023-03-22 21:25:37', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `jobpost_branch`
--

CREATE TABLE `jobpost_branch` (
  `id` int(11) NOT NULL,
  `jobpost` int(11) NOT NULL,
  `branch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jobpost_branch`
--

INSERT INTO `jobpost_branch` (`id`, `jobpost`, `branch`) VALUES
(9, 2, 1),
(13, 4, 1),
(15, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `jobpost_placed`
--

CREATE TABLE `jobpost_placed` (
  `id` int(11) NOT NULL,
  `jobpost` int(11) NOT NULL,
  `jobpostapply` int(11) NOT NULL,
  `placementround` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `date_updated` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jobpost_placed`
--

INSERT INTO `jobpost_placed` (`id`, `jobpost`, `jobpostapply`, `placementround`, `status`, `date_updated`) VALUES
(1, 1, 1, 1, 1, '2023-03-23 04:00:23'),
(2, 1, 1, 3, 1, '2023-03-23 04:00:23'),
(3, 1, 1, 4, 1, '2023-03-23 04:00:23'),
(4, 1, 1, 6, 1, '2023-03-23 04:00:23');

-- --------------------------------------------------------

--
-- Table structure for table `jobpost_placementrounds`
--

CREATE TABLE `jobpost_placementrounds` (
  `id` int(11) NOT NULL,
  `jobpost` int(11) NOT NULL,
  `placementround` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jobpost_placementrounds`
--

INSERT INTO `jobpost_placementrounds` (`id`, `jobpost`, `placementround`) VALUES
(19, 2, 4),
(26, 4, 1),
(27, 4, 5),
(32, 1, 1),
(33, 1, 3),
(34, 1, 4),
(35, 1, 6);

-- --------------------------------------------------------

--
-- Table structure for table `jobpost_skill`
--

CREATE TABLE `jobpost_skill` (
  `id` int(11) NOT NULL,
  `jobpost` int(11) NOT NULL,
  `skill` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jobpost_skill`
--

INSERT INTO `jobpost_skill` (`id`, `jobpost`, `skill`) VALUES
(20, 2, 4),
(21, 2, 7),
(22, 2, 8),
(29, 4, 2),
(30, 4, 4),
(33, 1, 2),
(34, 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `notice`
--

CREATE TABLE `notice` (
  `id` int(11) NOT NULL,
  `title` varchar(250) NOT NULL,
  `notice` varchar(255) DEFAULT NULL,
  `audience` varchar(255) DEFAULT NULL,
  `fromdate` date NOT NULL,
  `todate` date NOT NULL,
  `date_added` timestamp NOT NULL DEFAULT current_timestamp(),
  `date_updated` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `notice`
--

INSERT INTO `notice` (`id`, `title`, `notice`, `audience`, `fromdate`, `todate`, `date_added`, `date_updated`) VALUES
(1, 'test1', '<p>test</p>\r\n', 'ALL', '2023-03-21', '2023-03-21', '2023-03-21 10:19:30', NULL),
(2, 'test', '<p>test1</p>\r\n', 'COMPANY', '2023-03-21', '2023-03-25', '2023-03-21 10:20:25', NULL),
(3, 'test', '<p>test2</p>\r\n', 'STUDENT', '2023-03-21', '2023-03-29', '2023-03-21 10:21:10', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `placementrounds`
--

CREATE TABLE `placementrounds` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `placementrounds`
--

INSERT INTO `placementrounds` (`id`, `name`) VALUES
(1, 'Basic aptitude round'),
(3, 'Technical round'),
(4, 'Face-to-face'),
(5, 'HR round'),
(6, 'Joining');

-- --------------------------------------------------------

--
-- Table structure for table `skill`
--

CREATE TABLE `skill` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `skill`
--

INSERT INTO `skill` (`id`, `name`) VALUES
(2, 'Communication skills'),
(3, 'Active listening skills'),
(4, 'Computer skills'),
(5, 'Customer service skills'),
(6, 'Interpersonal skills'),
(7, 'Leadership skills'),
(8, 'Management skills'),
(9, 'Problem-solving skills'),
(10, 'Time management skills'),
(11, ' Transferable skills');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `regno` varchar(255) NOT NULL,
  `address` text DEFAULT NULL,
  `joiningyear` int(11) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `aboutme` text DEFAULT NULL,
  `course` int(11) NOT NULL,
  `branch` int(11) NOT NULL,
  `ayear` int(11) NOT NULL,
  `avgscore` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`id`, `user`, `regno`, `address`, `joiningyear`, `dob`, `aboutme`, `course`, `branch`, `ayear`, `avgscore`) VALUES
(1, 2, '1234', 'test', 2020, '2000-03-03', 'test', 3, 1, 4, 70);

-- --------------------------------------------------------

--
-- Table structure for table `studentfinalstatus`
--

CREATE TABLE `studentfinalstatus` (
  `id` int(11) NOT NULL,
  `student` int(11) NOT NULL,
  `status` varchar(255) NOT NULL,
  `updationtime` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `studentfinalstatus`
--

INSERT INTO `studentfinalstatus` (`id`, `student`, `status`, `updationtime`) VALUES
(3, 2, 'PLACED', '2023-03-30 14:30:50');

-- --------------------------------------------------------

--
-- Table structure for table `trainings`
--

CREATE TABLE `trainings` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `location` varchar(255) NOT NULL,
  `fromdate` date NOT NULL,
  `todate` date NOT NULL,
  `status` int(11) NOT NULL,
  `date_added` timestamp NOT NULL DEFAULT current_timestamp(),
  `date_updated` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `trainings`
--

INSERT INTO `trainings` (`id`, `title`, `description`, `location`, `fromdate`, `todate`, `status`, `date_added`, `date_updated`) VALUES
(2, 'test', '<p>test</p>\r\n', 'test', '2023-03-21', '2023-03-24', 1, '2023-03-21 14:36:12', '2023-03-22 19:47:18');

-- --------------------------------------------------------

--
-- Table structure for table `trainings_academicyear`
--

CREATE TABLE `trainings_academicyear` (
  `id` int(11) NOT NULL,
  `training` int(11) NOT NULL,
  `academicyear` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `trainings_academicyear`
--

INSERT INTO `trainings_academicyear` (`id`, `training`, `academicyear`) VALUES
(12, 2, 4),
(13, 2, 5);

-- --------------------------------------------------------

--
-- Table structure for table `trainings_branch`
--

CREATE TABLE `trainings_branch` (
  `id` int(11) NOT NULL,
  `training` int(11) NOT NULL,
  `branch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `trainings_branch`
--

INSERT INTO `trainings_branch` (`id`, `training`, `branch`) VALUES
(8, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(50) NOT NULL,
  `name` varchar(250) NOT NULL,
  `password` text NOT NULL,
  `email` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `type` varchar(50) NOT NULL DEFAULT 'STUDENT',
  `status` int(11) NOT NULL DEFAULT 1,
  `date_added` timestamp NOT NULL DEFAULT current_timestamp(),
  `date_updated` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `password`, `email`, `contact`, `last_login`, `type`, `status`, `date_added`, `date_updated`) VALUES
(1, 'admin', '$2a$10$NccWkdMT0HbgqnLHm18ewuqiunaR8ugFqxssyFo8YUyMJB.63Q2FK', 'admin@placement.com', '111111111111', NULL, 'ADMIN', 1, '2023-03-21 05:44:05', NULL),
(2, 'test', '$2a$10$Vjd4YZaS0aFjpnrKv1ioie4iGkeUMXY89zJf4DEXALTRiL239eMZS', 'a@b.com', '1111111111', NULL, 'STUDENT', 1, '2023-03-21 11:08:02', NULL),
(3, 'test', '$2a$10$zQWa4GMg8iiSAw4OmDuLAeSvntaT7BA8OR6x.taHl/gPw5eGOeNOy', 'test@gmail.com', '1111111112', NULL, 'COMPANY', 1, '2023-03-21 11:28:30', NULL),
(4, 'test', '$2a$10$coxUtDtNMXnuTuoY1nOtAecP2khIjoR6mEWyPGTJ5Aw4jV4bytVUa', 'test1@gmail.com', '1111111123', NULL, 'COMPANY', 1, '2023-03-21 11:28:56', NULL),
(5, 'test', '$2a$10$4NAjHTPcZLjyNeyudIRvQuEMUXX.xYKgjbBBLBk769tCJ1tCywqte', 'a1@b.com', '1111111116', NULL, 'STUDENT', 0, '2023-03-22 22:16:31', NULL),
(6, 'test1', '$2a$10$RNVGEDBK6vNzWa0BvMk/Oux9xwq1v2rBEgl7euNs9FShNSJTjrCNu', 'test2@gmail.com', '1111111127', NULL, 'COMPANY', 0, '2023-03-22 22:19:26', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `academicyear`
--
ALTER TABLE `academicyear`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `contactus`
--
ALTER TABLE `contactus`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jobpost`
--
ALTER TABLE `jobpost`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jobpost_academicyear`
--
ALTER TABLE `jobpost_academicyear`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jobpost_apply`
--
ALTER TABLE `jobpost_apply`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jobpost_branch`
--
ALTER TABLE `jobpost_branch`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jobpost_placed`
--
ALTER TABLE `jobpost_placed`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jobpost_placementrounds`
--
ALTER TABLE `jobpost_placementrounds`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jobpost_skill`
--
ALTER TABLE `jobpost_skill`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notice`
--
ALTER TABLE `notice`
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `placementrounds`
--
ALTER TABLE `placementrounds`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `skill`
--
ALTER TABLE `skill`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `studentfinalstatus`
--
ALTER TABLE `studentfinalstatus`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `student` (`student`);

--
-- Indexes for table `trainings`
--
ALTER TABLE `trainings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `trainings_academicyear`
--
ALTER TABLE `trainings_academicyear`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `trainings_branch`
--
ALTER TABLE `trainings_branch`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `contact` (`contact`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `academicyear`
--
ALTER TABLE `academicyear`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `branch`
--
ALTER TABLE `branch`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `company`
--
ALTER TABLE `company`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `contactus`
--
ALTER TABLE `contactus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `jobpost`
--
ALTER TABLE `jobpost`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `jobpost_academicyear`
--
ALTER TABLE `jobpost_academicyear`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `jobpost_apply`
--
ALTER TABLE `jobpost_apply`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `jobpost_branch`
--
ALTER TABLE `jobpost_branch`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `jobpost_placed`
--
ALTER TABLE `jobpost_placed`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `jobpost_placementrounds`
--
ALTER TABLE `jobpost_placementrounds`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `jobpost_skill`
--
ALTER TABLE `jobpost_skill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `notice`
--
ALTER TABLE `notice`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `placementrounds`
--
ALTER TABLE `placementrounds`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `skill`
--
ALTER TABLE `skill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `studentfinalstatus`
--
ALTER TABLE `studentfinalstatus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `trainings`
--
ALTER TABLE `trainings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `trainings_academicyear`
--
ALTER TABLE `trainings_academicyear`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `trainings_branch`
--
ALTER TABLE `trainings_branch`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
