
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(25) DEFAULT NULL,
  `api_key` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `users` VALUES (1,'ross','secret'),(2,'dave','apple'),(3,'bill','kiwi');
