-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 16, 2021 at 05:35 AM
-- Server version: 5.7.21
-- PHP Version: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `canteen_ms`
--

DELIMITER $$
--
-- Procedures
--
DROP PROCEDURE IF EXISTS `add_orders_log`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_orders_log` (IN `item` INT, IN `customer_id` INT, IN `quan` INT, IN `total` FLOAT, IN `ordered` DATETIME)  BEGIN
INSERT INTO `orders_log`(`item_id`, `user_id`, `quantity`, `total_price`, `ordered_at`) VALUES(item,customer_id,quan,total,'ordered');
END$$

DROP PROCEDURE IF EXISTS `cancel_order`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `cancel_order` (IN `order_id` INT)  BEGIN
UPDATE `orders` SET `is_canceled`= 1 WHERE `id` = order_id AND `is_canceled` = 0;
END$$

DROP PROCEDURE IF EXISTS `get_last_transactions_amount`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_last_transactions_amount` ()  BEGIN
SELECT * FROM `transactions` ORDER BY `transaction_date` DESC LIMIT 1;
END$$

DROP PROCEDURE IF EXISTS `inventory_create`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inventory_create` (IN `inventory_id` INT, IN `product_quantity` INT, IN `product_unit_price` FLOAT, IN `product_expiry_at` DATETIME)  BEGIN
INSERT INTO `inventory`(`product_id`, `quantity`, `unit_price`, `expiry_at`) VALUES (inventory_id, product_quantity, product_unit_price, product_expiry_at);
END$$

DROP PROCEDURE IF EXISTS `inventory_delete`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inventory_delete` (IN `inventory_id` INT)  BEGIN
UPDATE `inventory` SET `is_deleted`= 1 WHERE `id` = inventory_id  AND `is_deleted` = 0;
END$$

DROP PROCEDURE IF EXISTS `myorders_for_customers`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `myorders_for_customers` (IN `user_registered_no` VARCHAR(5))  BEGIN
SELECT o.`id`, o.`item_id`, o.`quantity`, o.`quantity` * o.`unit_price` AS `unit_price`, o.`ordered_at`, o.`user_id`, o.`is_canceled` 
FROM `orders` o, `user` u
WHERE o.`user_id` = u.`id` AND o.`is_canceled` = 0 AND u.`registered_no` = user_registered_no 
ORDER BY o.`ordered_at` DESC;
END$$

DROP PROCEDURE IF EXISTS `myorders_for_owner`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `myorders_for_owner` ()  BEGIN
SELECT o.`id`, o.`item_id`,o.`quantity`, o.`quantity`,o.`unit_price` AS `unit_price`, o.`ordered_at`,o.`user_id`, o.`is_canceled`
FROM `orders` o
WHERE o.`is_canceled` = 0
ORDER BY o.`ordered_at` ASC;
END$$

DROP PROCEDURE IF EXISTS `place_order`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `place_order` (IN `inventory_id` INT, IN `order_quantity` INT, IN `order_unit_price` FLOAT, IN `user_id` INT)  BEGIN
INSERT INTO `orders`(`item_id`, `quantity`, `unit_price`, `user_id`) VALUES (inventory_id, order_quantity, order_unit_price, user_id);
END$$

DROP PROCEDURE IF EXISTS `product_create`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `product_create` (IN `product_name` VARCHAR(100), IN `product_category_id` INT)  BEGIN
INSERT INTO `product`(`name`, `category_id`) VALUES (product_name, product_category_id);
END$$

DROP PROCEDURE IF EXISTS `product_delete`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `product_delete` (IN `product_id` VARCHAR(5))  BEGIN
UPDATE `product` SET `is_deleted`= 1 WHERE `id` = product_id  AND `is_deleted` = 0;
END$$

DROP PROCEDURE IF EXISTS `profile_password_update`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `profile_password_update` (IN `user_registered_no` VARCHAR(5), IN `user_old_password` VARCHAR(255), IN `user_new_password` VARCHAR(255))  BEGIN
DECLARE  status VARCHAR(255);
IF user_check_old_password(user_registered_no, user_old_password) = 1 THEN
UPDATE `login` SET `password`= user_new_password WHERE `id` = (SELECT `id` FROM `user` WHERE `registered_no` = user_registered_no AND `is_deleted` = 0 LIMIT 1);
SET status = 'Updated';
ELSE
SET status = 'Old password Incorrect';
END IF;
SELECT status;
END$$

DROP PROCEDURE IF EXISTS `profile_update`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `profile_update` (IN `user_registered_no` VARCHAR(5), IN `user_name` VARCHAR(100), IN `user_mobile` CHAR(10), IN `user_email` VARCHAR(100), IN `user_room_no` CHAR(4))  BEGIN
UPDATE `user` SET `name`= user_name,`mobile`= user_mobile,`email`= user_email,`room_no`= user_room_no WHERE `registered_no` = user_registered_no;
END$$

DROP PROCEDURE IF EXISTS `user_account_create`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `user_account_create` (IN `user_id` INT, IN `user_registered_no` VARCHAR(5), IN `user_name` VARCHAR(100), IN `user_role_id` INT)  BEGIN
DECLARE result int;
SET result=1;
INSERT INTO `user`(id,`registered_no`, `name`, `role_id`) VALUES (user_id,user_registered_no , user_name, user_role_id);
SELECT *FROM user ORDER BY id desc limit 1;
END$$

DROP PROCEDURE IF EXISTS `user_account_delete`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `user_account_delete` (IN `user_id` VARCHAR(5))  BEGIN
UPDATE `user` SET `is_deleted`= 1 WHERE `id` = user_id AND `is_deleted` = 0;
END$$

--
-- Functions
--
DROP FUNCTION IF EXISTS `calculate_transactions_summery`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `calculate_transactions_summery` () RETURNS FLOAT BEGIN
DECLARE transactions_amount FLOAT DEFAULT 0.0;
SELECT SUM(`quantity`*`total_price`) INTO transactions_amount 
FROM `orders_log` 
WHERE DATE(`delivered_at`) = DATE(NOW()) 
GROUP BY `delivered_at`;
RETURN (transactions_amount);
END$$

DROP FUNCTION IF EXISTS `user_check_old_password`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `user_check_old_password` (`user_registered_no` VARCHAR(5), `user_old_password` VARCHAR(255)) RETURNS INT(11) BEGIN
DECLARE  pwd VARCHAR(255);
DECLARE  status INT;
SELECT l.`password` INTO pwd FROM `user` u, `login` l WHERE u.`id` = l.`id` AND u.`registered_no` = user_registered_no AND `is_deleted` = 0 LIMIT 1;
IF pwd = user_old_password THEN
SET status = 1;
ELSE
SET status = 0;
END IF;
RETURN (status);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `active_products`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `active_products`;
CREATE TABLE IF NOT EXISTS `active_products` (
`id` int(11)
,`name` varchar(100)
,`type` varchar(255)
,`is_hide` tinyint(1)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `active_users`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `active_users`;
CREATE TABLE IF NOT EXISTS `active_users` (
`id` int(11)
,`registered_no` varchar(255)
,`name` varchar(255)
,`mobile` varchar(255)
,`email` varchar(255)
,`role_name` varchar(255)
,`room_no` varchar(255)
,`password` varchar(255)
);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL,
  `is_deleted` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `is_deleted`, `type`) VALUES
(1, 0, 'Bakery'),
(2, 0, 'Beverages'),
(3, 0, 'Chilled'),
(4, 0, 'Grocery'),
(5, 0, 'Meals'),
(6, 0, 'Household'),
(7, 0, 'Pharmacy');

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(9);

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
CREATE TABLE IF NOT EXISTS `inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` float NOT NULL,
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expiry_at` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Stand-in structure for view `inventory_for_customers`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `inventory_for_customers`;
CREATE TABLE IF NOT EXISTS `inventory_for_customers` (
`id` int(11)
,`product_id` int(11)
,`type` varchar(255)
,`unit_price` float
,`quantity` int(11)
,`inserted_at` datetime
,`expiry_at` datetime
,`is_deleted` tinyint(1)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `inventory_for_owner`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `inventory_for_owner`;
CREATE TABLE IF NOT EXISTS `inventory_for_owner` (
`id` int(11)
,`name` varchar(100)
,`type` varchar(255)
,`quantity` int(11)
,`unit_price` float
,`inserted_at` datetime
,`expiry_at` datetime
);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
CREATE TABLE IF NOT EXISTS `login` (
  `id` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `password`) VALUES
(1,'7110eda4d09e062aa5e4a390b0a572ac0d2c0220');

-- --------------------------------------------------------

--
-- Stand-in structure for view `meals_for_owner`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `meals_for_owner`;
CREATE TABLE IF NOT EXISTS `meals_for_owner` (
`id` int(11)
,`product_id` int(11)
,`quantity` int(11)
,`unit_price` float
,`inserted_at` datetime
,`expiry_at` datetime
,`is_deleted` tinyint(1)
);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` float NOT NULL,
  `ordered_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `is_canceled` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `orders_log`
--

DROP TABLE IF EXISTS `orders_log`;
CREATE TABLE IF NOT EXISTS `orders_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total_price` float NOT NULL DEFAULT '0',
  `ordered_at` datetime NOT NULL,
  `delivered_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Triggers `orders_log`
--
DROP TRIGGER IF EXISTS `TR_after_orders_log_insert`;
DELIMITER $$
CREATE TRIGGER `TR_after_orders_log_insert` AFTER INSERT ON `orders_log` FOR EACH ROW BEGIN
DELETE FROM orders WHERE  item_id = NEW.item_id AND user_id = NEW.user_id AND ordered_at = NEW.ordered_at AND is_canceled =0;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `category_id` int(11) NOT NULL,
  `is_hide` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `category_id`, `is_hide`, `is_deleted`) VALUES
(1, 'Rice and Curry (Chicken)', 5, 0, 0),
(2, 'Rice and Curry (Fish)', 5, 0, 0),
(3, 'Rice and Curry (Egg)', 5, 0, 0),
(4, 'Fried Rice', 5, 0, 0),
(5, 'Paratha', 5, 0, 0),
(6, 'Chocolate Bun', 1, 0, 0),
(7, 'Test Product', 2, 0, 0),
(8, 'Panadol Card 500g', 7, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `role_name`) VALUES
(1, 'Administrator'),
(2, 'Owner'),
(3, 'Lecturer'),
(4, 'Student');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
  `transaction_date` datetime(6) NOT NULL,
  `total_amount` float DEFAULT NULL,
  PRIMARY KEY (`transaction_date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `registered_no` varchar(255) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `room_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `registered_no`, `name`, `mobile`, `email`, `role_id`, `room_no`, `is_deleted`) VALUES
(1,'ADM01','Administrator FOT','0414567346','admin@fot.com',1,'',0);

--
-- Triggers `user`
--
DROP TRIGGER IF EXISTS `TR_after_user_insert`;
DELIMITER $$
CREATE TRIGGER `TR_after_user_insert` AFTER INSERT ON `user` FOR EACH ROW BEGIN
INSERT INTO `login`(`id`, `password`) VALUES (NEW.`id`, 'User@pwd');
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure for view `active_products`
--
DROP TABLE IF EXISTS `active_products`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `active_products`  AS  select `p`.`id` AS `id`,`p`.`name` AS `name`,`c`.`type` AS `type`,`p`.`is_hide` AS `is_hide` from (`product` `p` join `category` `c`) where ((`p`.`category_id` = `c`.`id`) and (`p`.`is_deleted` = 0)) order by `p`.`is_hide` desc,`p`.`name`,`c`.`type` ;

-- --------------------------------------------------------

--
-- Structure for view `active_users`
--
DROP TABLE IF EXISTS `active_users`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `active_users`  AS  select `u`.`id` AS `id`,`u`.`registered_no` AS `registered_no`,`u`.`name` AS `name`,`u`.`mobile` AS `mobile`,`u`.`email` AS `email`,`r`.`role_name` AS `role_name`,`u`.`room_no` AS `room_no`,`l`.`password` AS `password` from ((`login` `l` join `user` `u`) join `role` `r`) where ((`l`.`id` = `u`.`id`) and (`u`.`role_id` = `r`.`id`) and (`u`.`is_deleted` = 0)) ;

-- --------------------------------------------------------

--
-- Structure for view `inventory_for_customers`
--
DROP TABLE IF EXISTS `inventory_for_customers`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `inventory_for_customers`  AS  select `i`.`id` AS `id`,`i`.`product_id` AS `product_id`,`c`.`type` AS `type`,`i`.`unit_price` AS `unit_price`,`i`.`quantity` AS `quantity`,`i`.`inserted_at` AS `inserted_at`,`i`.`expiry_at` AS `expiry_at`,`i`.`is_deleted` AS `is_deleted` from ((`inventory` `i` join `product` `p`) join `category` `c`) where ((`i`.`product_id` = `p`.`id`) and (`p`.`category_id` = `c`.`id`) and (`p`.`is_hide` = 0) and (`i`.`quantity` > 0) and (`i`.`is_deleted` = 0) and (`i`.`expiry_at` > now())) order by rand() ;

-- --------------------------------------------------------

--
-- Structure for view `inventory_for_owner`
--
DROP TABLE IF EXISTS `inventory_for_owner`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `inventory_for_owner`  AS  select `i`.`id` AS `id`,`p`.`name` AS `name`,`c`.`type` AS `type`,`i`.`quantity` AS `quantity`,`i`.`unit_price` AS `unit_price`,`i`.`inserted_at` AS `inserted_at`,`i`.`expiry_at` AS `expiry_at` from ((`inventory` `i` join `product` `p`) join `category` `c`) where ((`i`.`product_id` = `p`.`id`) and (`p`.`category_id` = `c`.`id`) and (`i`.`quantity` > 0) and (`i`.`is_deleted` = 0)) order by `i`.`expiry_at` ;

-- --------------------------------------------------------

--
-- Structure for view `meals_for_owner`
--
DROP TABLE IF EXISTS `meals_for_owner`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `meals_for_owner`  AS  select `i`.`id` AS `id`,`i`.`product_id` AS `product_id`,`i`.`quantity` AS `quantity`,`i`.`unit_price` AS `unit_price`,`i`.`inserted_at` AS `inserted_at`,`i`.`expiry_at` AS `expiry_at`,`i`.`is_deleted` AS `is_deleted` from ((`inventory` `i` join `product` `p`) join `category` `c`) where ((`i`.`product_id` = `p`.`id`) and (`p`.`category_id` = `c`.`id`) and (`c`.`type` = 'Meals') and (`i`.`quantity` > 0) and (`i`.`is_deleted` = 0)) order by `i`.`expiry_at` ;

DELIMITER $$
--
-- Events add_daily_transaction_summery
--
CREATE DEFINER=`root`@`localhost` EVENT IF NOT EXISTS `add_daily_transaction_summery` ON SCHEDULE EVERY 1 DAY STARTS '2021-06-15 23:59:55' ON COMPLETION PRESERVE ENABLE DO INSERT INTO `transactions`(`transaction_date`, `total_amount`) VALUES (DATE(NOW()), calculate_transactions_summery())$$

DELIMITER ;
COMMIT;


DELIMITER $$
--
-- Events delete_daily_deleted_invemtory
--
CREATE DEFINER=`root`@`localhost` EVENT IF NOT EXISTS `delete_daily_deleted_invemtory` ON SCHEDULE EVERY 1 DAY STARTS '2021-06-15 23:59:55' ON COMPLETION PRESERVE ENABLE DO DELETE FROM `inventory` WHERE `is_deleted` = 1$$

DELIMITER ;
COMMIT;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
