CREATE TABLE `policy` (
  `policy_id` int NOT NULL AUTO_INCREMENT,
  `policy_number` varchar(20) DEFAULT NULL,
  `owner_first_name` varchar(50) DEFAULT NULL,
  `owner_last_name` varchar(50) DEFAULT NULL,
  `effectivity_date` date DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `last_update` datetime DEFAULT NULL,
  PRIMARY KEY (`policy_id`)
);