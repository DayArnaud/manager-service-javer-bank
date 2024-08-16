DROP DATABASE if EXISTS `javer_bank_db`;
CREATE DATABASE `javer_bank_db`;
USE `javer_bank_db`;

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `id` BINARY(16) NOT NULL PRIMARY KEY,
  `name` varchar(255),
  `phone` BIGINT,
  `email` varchar(255),
  `account_holder` BOOLEAN,
  `account_number` varchar(255) UNIQUE,
  `balance` DOUBLE,
  `credit_score` DOUBLE,
  `created_at` TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

SELECT * FROM javer_bank_db.customers;