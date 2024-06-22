-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 22, 2024 at 08:48 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `check-rule`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `populateCounter` ()   BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE ruleCount INT DEFAULT 5;
    DECLARE userCount INT DEFAULT 5;
    DECLARE counterNameCount INT DEFAULT 5;
    DECLARE counter_value INT;
    
    DECLARE rule_id INT;
    DECLARE user_id INT;
    DECLARE counter_name VARCHAR(255);
    DECLARE attempts INT DEFAULT 0;
    
    WHILE i <= 980 DO
        SET rule_id = FLOOR(1 + (RAND() * ruleCount));
        SET user_id = FLOOR(1 + (RAND() * userCount));
        SET counter_name = ELT(FLOOR(1 + (RAND() * counterNameCount)), 'payment', 'promotion', 'login', 'comment', 'share');
        SET counter_value = FLOOR(1 + (RAND() * 20));
        
        -- Kiểm tra xem tổ hợp rule_id, user_id, counter_name đã tồn tại chưa
        IF NOT EXISTS (SELECT 1 FROM Counter WHERE rule_id = rule_id AND user_id = user_id AND counter_name = counter_name) THEN
            INSERT INTO Counter (rule_id, user_id, counter_name, counter_value) VALUES (rule_id, user_id, counter_name, counter_value);
            SET i = i + 1;
            SET attempts = 0;
        ELSE
            SET attempts = attempts + 1;
        END IF;

        -- Nếu số lần thử vượt quá một ngưỡng nhất định, hãy thay đổi một trong các điều kiện để tạo sự ngẫu nhiên
        IF attempts > 100 THEN
            SET rule_id = rule_id + 1;
            IF rule_id > ruleCount THEN
                SET rule_id = 1;
            END IF;
            SET attempts = 0;
        END IF;
    END WHILE;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `counter`
--

CREATE TABLE `counter` (
  `counter_id` int(11) NOT NULL,
  `rule_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `counter_name` varchar(255) NOT NULL,
  `counter_value` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `counter`
--

INSERT INTO `counter` (`counter_id`, `rule_id`, `user_id`, `counter_name`, `counter_value`) VALUES
(76, 1, 1, 'payment', 3),
(77, 2, 1, 'promotion', 2),
(78, 3, 1, 'login', 5),
(79, 4, 1, 'comment', 10),
(80, 5, 1, 'share', 12),
(81, 1, 2, 'payment', 6),
(82, 2, 2, 'promotion', 5),
(83, 3, 2, 'login', 7),
(84, 4, 2, 'comment', 8),
(85, 5, 2, 'share', 9),
(86, 1, 3, 'payment', 1),
(87, 2, 3, 'promotion', 4),
(88, 3, 3, 'login', 8),
(89, 4, 3, 'comment', 2),
(90, 5, 3, 'share', 6),
(91, 1, 4, 'payment', 5),
(92, 2, 4, 'promotion', 2),
(93, 3, 4, 'login', 9),
(94, 4, 4, 'comment', 7),
(95, 5, 4, 'share', 10),
(96, 1, 5, 'payment', 4),
(97, 2, 5, 'promotion', 1),
(98, 3, 5, 'login', 6),
(99, 4, 5, 'comment', 11),
(100, 5, 5, 'share', 8);

-- --------------------------------------------------------

--
-- Table structure for table `rule`
--

CREATE TABLE `rule` (
  `rule_id` int(11) NOT NULL,
  `rule_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rule`
--

INSERT INTO `rule` (`rule_id`, `rule_name`) VALUES
(1, 'rule1'),
(2, 'rule2'),
(3, 'rule3'),
(4, 'rule4'),
(5, 'rule5');

-- --------------------------------------------------------

--
-- Table structure for table `rule_counter_data`
--

CREATE TABLE `rule_counter_data` (
  `counter_data_id` int(11) NOT NULL,
  `rule_id` int(11) NOT NULL,
  `counter_key` varchar(255) NOT NULL,
  `limitValue` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rule_counter_data`
--

INSERT INTO `rule_counter_data` (`counter_data_id`, `rule_id`, `counter_key`, `limitValue`) VALUES
(1, 1, 'payment', 10),
(2, 2, 'promotion', 5),
(3, 3, 'login', 15),
(4, 4, 'comment', 20),
(5, 5, 'share', 25);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`) VALUES
(1, 'user1'),
(2, 'user2'),
(3, 'user3'),
(4, 'user4'),
(5, 'user5');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `counter`
--
ALTER TABLE `counter`
  ADD PRIMARY KEY (`counter_id`),
  ADD UNIQUE KEY `rule_id` (`rule_id`,`user_id`,`counter_name`),
  ADD KEY `user_id` (`user_id`,`rule_id`,`counter_value`);

--
-- Indexes for table `rule`
--
ALTER TABLE `rule`
  ADD PRIMARY KEY (`rule_id`);

--
-- Indexes for table `rule_counter_data`
--
ALTER TABLE `rule_counter_data`
  ADD PRIMARY KEY (`counter_data_id`),
  ADD KEY `fk_rule` (`rule_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `counter`
--
ALTER TABLE `counter`
  MODIFY `counter_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;

--
-- AUTO_INCREMENT for table `rule`
--
ALTER TABLE `rule`
  MODIFY `rule_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `rule_counter_data`
--
ALTER TABLE `rule_counter_data`
  MODIFY `counter_data_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `counter`
--
ALTER TABLE `counter`
  ADD CONSTRAINT `counter_ibfk_1` FOREIGN KEY (`rule_id`) REFERENCES `rule` (`rule_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `counter_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `rule_counter_data`
--
ALTER TABLE `rule_counter_data`
  ADD CONSTRAINT `fk_rule` FOREIGN KEY (`rule_id`) REFERENCES `rule` (`rule_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
