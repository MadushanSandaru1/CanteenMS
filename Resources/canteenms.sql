-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: May 31, 2021 at 01:53 AM
-- Server version: 5.7.32
-- PHP Version: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `canteenms`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_orders_log` (IN `order_id` INT)  BEGIN
INSERT INTO `orders_log`(`item_id`, `user_id`, `quantity`, `total_price`, `ordered_at`) SELECT `item_id`, `user_id`, `quantity`, `unit_price`*`quantity` AS `total_price`, `ordered_at` FROM `orders` WHERE `id` = order_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cancel_order` (IN `order_id` INT)  BEGIN
UPDATE `orders` SET `is_canceled`= 1 WHERE `id` = order_id AND `is_canceled` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `inventory_create` (IN `inventory_id` INT, IN `product_quantity` INT, IN `product_unit_price` FLOAT, IN `product_expiry_at` DATETIME)  BEGIN
INSERT INTO `inventory`(`product_id`, `quantity`, `unit_price`, `expiry_at`) VALUES (inventory_id, product_quantity, product_unit_price, product_expiry_at);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `inventory_delete` (IN `inventory_id` INT)  BEGIN
UPDATE `inventory` SET `is_deleted`= 1 WHERE `id` = inventory_id  AND `is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `myorders_for_customers` (IN `user_registered_no` VARCHAR(5))  BEGIN
SELECT o.`id`, p.`name`, c.`type`, o.`quantity`, o.`quantity`*o.`unit_price` AS `total`, o.`ordered_at` 
FROM `orders` o, `product` p, `user` u, `category` c 
WHERE o.`item_id` = p.`id` AND o.`user_id` = u.`id` AND p.`category_id` = c.`id` AND o.`is_canceled` = 0 AND u.`registered_no` = user_registered_no 
ORDER BY o.`ordered_at` DESC;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `myorders_for_owner` ()  BEGIN
SELECT o.`id`, p.`name`, c.`type`, u.`registered_no`, o.`quantity`, o.`quantity`*o.`unit_price` AS `total`, o.`ordered_at` 
FROM `orders` o, `product` p, `user` u, `category` c 
WHERE o.`item_id` = p.`id` AND o.`user_id` = u.`id` AND p.`category_id` = c.`id` AND o.`is_canceled` = 0
ORDER BY o.`ordered_at` ASC;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `place_order` (IN `inventory_id` INT, IN `order_quantity` INT, IN `order_unit_price` FLOAT, IN `user_id` INT)  BEGIN
INSERT INTO `orders`(`item_id`, `quantity`, `unit_price`, `user_id`) VALUES (inventory_id, order_quantity, order_unit_price, user_id);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `product_create` (IN `product_name` VARCHAR(100), IN `product_category_id` INT)  BEGIN
INSERT INTO `product`(`name`, `category_id`) VALUES (product_name, product_category_id);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `product_delete` (IN `product_id` VARCHAR(5))  BEGIN
UPDATE `product` SET `is_deleted`= 1 WHERE `id` = product_id  AND `is_deleted` = 0;
END$$

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

CREATE DEFINER=`root`@`localhost` PROCEDURE `profile_update` (IN `user_registered_no` VARCHAR(5), IN `user_name` VARCHAR(100), IN `user_mobile` CHAR(10), IN `user_email` VARCHAR(100), IN `user_room_no` CHAR(4))  BEGIN
UPDATE `user` SET `name`= user_name,`mobile`= user_mobile,`email`= user_email,`room_no`= user_room_no WHERE `registered_no` = user_registered_no;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `user_account_create` (IN `user_registered_no` VARCHAR(5), IN `user_name` VARCHAR(100), IN `user_role_id` INT)  BEGIN
INSERT INTO `user`(`registered_no`, `name`, `role_id`) VALUES (user_registered_no , user_name, user_role_id);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `user_account_delete` (IN `user_id` VARCHAR(5))  BEGIN
UPDATE `user` SET `is_deleted`= 1 WHERE `id` = user_id AND `is_deleted` = 0;
END$$

--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `calculate_transactions_summery` () RETURNS FLOAT BEGIN
DECLARE transactions_amount FLOAT;
SELECT SUM(`quantity`*`total_price`) INTO transactions_amount 
FROM `orders_log` 
WHERE DATE(`delivered_at`) = DATE(NOW()) 
GROUP BY `delivered_at`;
RETURN (transactions_amount);
END$$

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
CREATE TABLE `active_products` (
`id` int(11)
,`name` varchar(100)
,`type` varchar(20)
,`is_hide` tinyint(1)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `active_users`
-- (See below for the actual view)
--
CREATE TABLE `active_users` (
`id` int(11)
,`registered_no` varchar(5)
,`name` varchar(100)
,`mobile` char(10)
,`email` varchar(100)
,`role_name` varchar(15)
,`room_no` char(4)
,`password` varchar(255)
);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `type` varchar(20) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `type`, `is_deleted`) VALUES
(1, 'Bakery', 0),
(2, 'Beverages', 0),
(3, 'Chilled', 0),
(4, 'Grocery', 0),
(5, 'Meals', 0),
(6, 'Household', 0),
(7, 'Pharmacy', 0);

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` float NOT NULL,
  `inserted_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expiry_at` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`id`, `product_id`, `quantity`, `unit_price`, `inserted_at`, `expiry_at`, `is_deleted`) VALUES
(1, 1, 200, 75, '2021-05-29 15:08:09', '2021-05-30 15:06:47', 0),
(2, 2, 200, 65, '2021-05-29 15:08:09', '2021-05-30 15:06:47', 0),
(3, 3, 200, 60, '2021-05-29 15:08:09', '2021-05-30 15:06:47', 0),
(4, 6, 20, 40, '2021-05-29 15:11:18', '2021-06-16 15:08:12', 0),
(5, 6, -7, 40, '2021-05-29 15:11:18', '2021-05-30 15:06:47', 0),
(6, 3, 300, 10, '2021-05-31 05:23:08', '2021-12-20 10:20:55', 1),
(7, 3, 300, 10, '2021-05-31 06:44:27', '2021-12-20 10:20:55', 0);

-- --------------------------------------------------------

--
-- Stand-in structure for view `inventory_for_customers`
-- (See below for the actual view)
--
CREATE TABLE `inventory_for_customers` (
`id` int(11)
,`name` varchar(100)
,`type` varchar(20)
,`unit_price` float
,`quantity` int(11)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `inventory_for_owner`
-- (See below for the actual view)
--
CREATE TABLE `inventory_for_owner` (
`id` int(11)
,`name` varchar(100)
,`type` varchar(20)
,`quantity` int(11)
,`unit_price` float
,`inserted_at` datetime
,`expiry_at` datetime
);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `password`) VALUES
(1, '123'),
(2, '456'),
(3, '789'),
(4, 'newpwd'),
(5, '222'),
(8, 'Student@pwd'),
(9, 'Student@pwd');

-- --------------------------------------------------------

--
-- Stand-in structure for view `meals_for_owner`
-- (See below for the actual view)
--
CREATE TABLE `meals_for_owner` (
`id` int(11)
,`name` varchar(100)
,`quantity` int(11)
,`unit_price` float
,`inserted_at` datetime
,`expiry_at` datetime
);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` float NOT NULL,
  `ordered_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `is_canceled` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `item_id`, `quantity`, `unit_price`, `ordered_at`, `user_id`, `is_canceled`) VALUES
(1, 1, 2, 75, '2021-05-29 15:28:07', 4, 0),
(2, 3, 1, 60, '2021-05-29 15:29:11', 5, 0),
(3, 1, 2, 75, '2021-05-29 15:28:07', 5, 0),
(4, 5, 10, 40, '2021-05-31 05:37:22', 5, 1),
(5, 5, 3, 40, '2021-05-31 06:02:29', 5, 1),
(6, 5, 1, 40, '2021-05-31 06:03:27', 5, 0),
(7, 5, 5, 40, '2021-05-31 06:05:08', 5, 0),
(8, 5, 6, 40, '2021-05-31 06:07:53', 5, 1),
(9, 5, 10, 40, '2021-05-31 06:44:27', 5, 0);

--
-- Triggers `orders`
--
DELIMITER $$
CREATE TRIGGER `TR_after_orders_cancel` AFTER UPDATE ON `orders` FOR EACH ROW BEGIN
UPDATE `inventory` SET `quantity`= (`quantity`+NEW.`quantity`) WHERE `id` = NEW.`item_id` AND `is_deleted` = 0;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `TR_after_orders_insert` AFTER INSERT ON `orders` FOR EACH ROW BEGIN
UPDATE `inventory` SET `quantity`= (`quantity`-NEW.`quantity`) WHERE `id` = NEW.`item_id` AND `is_deleted` = 0;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `orders_log`
--

CREATE TABLE `orders_log` (
  `id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total_price` float NOT NULL DEFAULT '0',
  `ordered_at` datetime NOT NULL,
  `delivered_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders_log`
--

INSERT INTO `orders_log` (`id`, `item_id`, `user_id`, `quantity`, `total_price`, `ordered_at`, `delivered_at`) VALUES
(1, 1, 5, 2, 150, '2021-05-29 15:28:07', '2021-05-31 02:09:32'),
(2, 1, 4, 2, 150, '2021-05-29 15:28:07', '2021-05-29 02:17:44'),
(8, 1, 5, 2, 150, '2021-05-29 15:28:07', '2021-05-31 02:09:32'),
(9, 1, 4, 2, 150, '2021-05-29 15:28:07', '2021-05-29 02:17:44'),
(10, 1, 4, 2, 150, '2021-05-29 15:28:07', '2021-05-31 06:44:27');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `category_id` int(11) NOT NULL,
  `is_hide` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
(7, 'Test Product', 2, 0, 1),
(8, 'Test Product', 2, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `role_name` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE `transactions` (
  `transaction_date` date NOT NULL,
  `total_amount` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transaction_date`, `total_amount`) VALUES
('2021-05-31', 600);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `registered_no` varchar(5) NOT NULL,
  `name` varchar(100) NOT NULL,
  `mobile` char(10) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `room_no` char(4) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `registered_no`, `name`, `mobile`, `email`, `role_id`, `room_no`, `is_deleted`) VALUES
(1, 'ADM01', 'Administrator FOT', '0416537981', 'admin@fot.lk', 1, NULL, 0),
(2, 'OWN01', 'Canteen Owner', '0764983109', 'owner1@fot.lk', 2, NULL, 0),
(3, 'LCR01', 'Lecturer Name 01', '0789056342', 'lecrurer1@fot.lk', 3, NULL, 0),
(4, 'TG001', 'Test Name', '0771234567', 'abc@fot.lk', 4, '302', 0),
(5, 'TG002', 'Student Name 02', '0723419845', 'tg002@fot.lk', 4, '302', 0),
(8, 'TG003', 'Test Name 1', NULL, NULL, 2, NULL, 1),
(9, 'TG003', 'Test Name 1', NULL, NULL, 2, NULL, 0);

--
-- Triggers `user`
--
DELIMITER $$
CREATE TRIGGER `TR_after_user_insert` AFTER INSERT ON `user` FOR EACH ROW BEGIN
INSERT INTO `login`(`id`, `password`) VALUES (NEW.`id`, 'Student@pwd');
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure for view `active_products`
--
DROP TABLE IF EXISTS `active_products`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `active_products`  AS SELECT `p`.`id` AS `id`, `p`.`name` AS `name`, `c`.`type` AS `type`, `p`.`is_hide` AS `is_hide` FROM (`product` `p` join `category` `c`) WHERE ((`p`.`category_id` = `c`.`id`) AND (`p`.`is_deleted` = 0)) ORDER BY `p`.`is_hide` DESC, `p`.`name` ASC, `c`.`type` ASC ;

-- --------------------------------------------------------

--
-- Structure for view `active_users`
--
DROP TABLE IF EXISTS `active_users`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `active_users`  AS SELECT `u`.`id` AS `id`, `u`.`registered_no` AS `registered_no`, `u`.`name` AS `name`, `u`.`mobile` AS `mobile`, `u`.`email` AS `email`, `r`.`role_name` AS `role_name`, `u`.`room_no` AS `room_no`, `l`.`password` AS `password` FROM ((`login` `l` join `user` `u`) join `role` `r`) WHERE ((`l`.`id` = `u`.`id`) AND (`u`.`role_id` = `r`.`id`) AND (`u`.`is_deleted` = 0)) ;

-- --------------------------------------------------------

--
-- Structure for view `inventory_for_customers`
--
DROP TABLE IF EXISTS `inventory_for_customers`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `inventory_for_customers`  AS SELECT `i`.`id` AS `id`, `p`.`name` AS `name`, `c`.`type` AS `type`, `i`.`unit_price` AS `unit_price`, `i`.`quantity` AS `quantity` FROM ((`inventory` `i` join `product` `p`) join `category` `c`) WHERE ((`i`.`product_id` = `p`.`id`) AND (`p`.`category_id` = `c`.`id`) AND (`p`.`is_hide` = 0) AND (`i`.`quantity` > 0) AND (`i`.`is_deleted` = 0)) ORDER BY rand() ASC ;

-- --------------------------------------------------------

--
-- Structure for view `inventory_for_owner`
--
DROP TABLE IF EXISTS `inventory_for_owner`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `inventory_for_owner`  AS SELECT `i`.`id` AS `id`, `p`.`name` AS `name`, `c`.`type` AS `type`, `i`.`quantity` AS `quantity`, `i`.`unit_price` AS `unit_price`, `i`.`inserted_at` AS `inserted_at`, `i`.`expiry_at` AS `expiry_at` FROM ((`inventory` `i` join `product` `p`) join `category` `c`) WHERE ((`i`.`product_id` = `p`.`id`) AND (`p`.`category_id` = `c`.`id`) AND (`i`.`quantity` > 0) AND (`i`.`is_deleted` = 0)) ORDER BY `i`.`expiry_at` ASC ;

-- --------------------------------------------------------

--
-- Structure for view `meals_for_owner`
--
DROP TABLE IF EXISTS `meals_for_owner`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `meals_for_owner`  AS SELECT `i`.`id` AS `id`, `p`.`name` AS `name`, `i`.`quantity` AS `quantity`, `i`.`unit_price` AS `unit_price`, `i`.`inserted_at` AS `inserted_at`, `i`.`expiry_at` AS `expiry_at` FROM ((`inventory` `i` join `product` `p`) join `category` `c`) WHERE ((`i`.`product_id` = `p`.`id`) AND (`p`.`category_id` = `c`.`id`) AND (`c`.`type` = 'Meals') AND (`i`.`quantity` > 0) AND (`i`.`is_deleted` = 0)) ORDER BY `i`.`expiry_at` ASC ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders_log`
--
ALTER TABLE `orders_log`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_date`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `inventory`
--
ALTER TABLE `inventory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `orders_log`
--
ALTER TABLE `orders_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `add_daily_transaction_summery` ON SCHEDULE EVERY 1 DAY STARTS '2021-05-31 23:59:55' ON COMPLETION PRESERVE ENABLE DO INSERT INTO `transactions`(`transaction_date`, `total_amount`) VALUES (DATE(NOW()), calculate_transactions_summery())$$

DELIMITER ;
