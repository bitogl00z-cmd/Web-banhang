-- FashionRecommendation Database
-- Ch?y l?nh sau d? t?o database:
-- mysql -u root -p -h localhost < database.sql

CREATE DATABASE IF NOT EXISTS fashion_recommendation
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE fashion_recommendation;

-- 1. Clusters
DROP TABLE IF EXISTS `clusters`;
CREATE TABLE `clusters` (
  `cluster_id` int NOT NULL,
  `cluster_name` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`cluster_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `clusters` VALUES
(0,'Phong cách Th? thao (Sportswear)','Áo thun ch?y b?, qu?n jogger, áo khoác gió, giày th? thao nang d?ng'),
(1,'Phong cách Ðu?ng ph? (Streetwear & Hiphop)','Áo hoodie, qu?n cargo túi h?p, áo bomber, ph? ki?n street style cá tính'),
(2,'Phong cách Công s? Nam (Formal Nam)','Áo s? mi tay dài, qu?n tây âu, b? vest, cà v?t l?ch lãm'),
(3,'Phong cách Công s? N? (Formal N?)','Ð?m blazer, áo s? mi voan, chân váy công s?, áo vest n? thanh l?ch'),
(4,'Phong cách T?i gi?n & Hàn Qu?c (Minimalist & Korean)','Áo thun basic, áo gile len, qu?n kaki ?ng suông, ph? ki?n t?i gi?n'),
(5,'Phong cách Casual N? (Casual N?)','Váy hoa, áo croptop, qu?n short jeans, sandal n? d?u dàng');

-- 2. Products
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `product_id` int NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `price` int NOT NULL,
  `gender_nam` tinyint(1) DEFAULT '0',
  `gender_nu` tinyint(1) DEFAULT '0',
  `cluster_id` int DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `products` VALUES
(1,'Áo thun ch?y b? nam',199000,1,0,0,NULL),
(2,'Qu?n joger th? thao',299000,1,0,0,NULL),
(3,'Áo khoác gió ch?ng nu?c',599000,1,1,0,NULL),
(4,'Giày th? thao tr?ng',899000,1,1,0,NULL),
(5,'B? th? thao nam',799000,1,0,0,NULL),
(6,'Áo hoodie form r?ng',399000,1,0,1,NULL),
(7,'Qu?n cargo túi h?p',349000,1,0,1,NULL),
(8,'Áo bomber da l?n',899000,1,0,1,NULL),
(9,'M? bucket h?a ti?t',149000,1,1,1,NULL),
(10,'Áo phông graphic',249000,1,0,1,NULL),
(11,'Áo s? mi tr?ng công s?',399000,1,0,2,NULL),
(12,'Qu?n tây âu den',499000,1,0,2,NULL),
(13,'Cà v?t l?a',199000,1,0,2,NULL),
(14,'B? vest nam 2 m?nh',2990000,1,0,2,NULL),
(15,'Áo vest nam',1490000,1,0,2,NULL),
(16,'Ð?m blazer công s?',899000,0,1,3,NULL),
(17,'Áo s? mi voan n?',349000,0,1,3,NULL),
(18,'Chân váy bút chì',449000,0,1,3,NULL),
(19,'Áo vest n?',1290000,0,1,3,NULL),
(20,'Giày cao gót',699000,0,1,3,NULL),
(21,'Áo thun basic c? tròn',149000,1,1,4,NULL),
(22,'Qu?n kaki ?ng suông',399000,1,0,4,NULL),
(23,'Áo gile len',349000,1,0,4,NULL),
(24,'Túi tote da t?i gi?n',499000,0,1,4,NULL),
(25,'Áo s? mi linen tr?ng',349000,1,0,4,NULL),
(26,'Váy hoa xòe',449000,0,1,5,NULL),
(27,'Áo croptop tay b?ng',199000,0,1,5,NULL),
(28,'Qu?n short jeans n?',299000,0,1,5,NULL),
(29,'Sandal n? quai m?nh',399000,0,1,5,NULL),
(30,'Áo len cardigan n?',499000,0,1,5,NULL);

-- 3. Product Features
DROP TABLE IF EXISTS `product_features`;
CREATE TABLE `product_features` (
  `product_id` int NOT NULL,
  `cate_ao` tinyint(1) DEFAULT '0',
  `cate_quan` tinyint(1) DEFAULT '0',
  `cate_vay_dam` tinyint(1) DEFAULT '0',
  `cate_phu_kien` tinyint(1) DEFAULT '0',
  `mat_cotton` tinyint(1) DEFAULT '0',
  `mat_kaki` tinyint(1) DEFAULT '0',
  `mat_jean` tinyint(1) DEFAULT '0',
  `mat_thun` tinyint(1) DEFAULT '0',
  `mat_len_ni` tinyint(1) DEFAULT '0',
  `color_trang` tinyint(1) DEFAULT '0',
  `color_den` tinyint(1) DEFAULT '0',
  `color_mau_noi` tinyint(1) DEFAULT '0',
  `style_casual` tinyint(1) DEFAULT '0',
  `style_streetwear` tinyint(1) DEFAULT '0',
  `style_minimalist` tinyint(1) DEFAULT '0',
  `style_korean` tinyint(1) DEFAULT '0',
  `style_formal` tinyint(1) DEFAULT '0',
  `style_sportswear` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `product_features` VALUES
(1,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1),
(2,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1),
(3,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,1),
(4,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1),
(5,1,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1),
(6,1,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0),
(7,0,1,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,0),
(8,1,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0),
(9,0,0,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0),
(10,1,0,0,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0),
(11,1,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,1,0),
(12,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0),
(13,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,1,0),
(14,1,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0),
(15,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0),
(16,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0),
(17,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0),
(18,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0),
(19,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0),
(20,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0),
(21,1,0,0,0,1,0,0,0,0,1,0,0,0,0,1,1,0,0),
(22,0,1,0,0,0,1,0,0,0,0,0,0,0,0,1,1,0,0),
(23,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,0,0),
(24,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0),
(25,1,0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,0,0),
(26,0,0,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0),
(27,1,0,0,0,1,0,0,0,0,0,0,1,1,0,0,0,0,0),
(28,0,1,0,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0),
(29,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0),
(30,1,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0);

-- 4. Orders
DROP TABLE IF EXISTS `order_items`;
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `address` text NOT NULL,
  `note` text,
  `total` int NOT NULL DEFAULT '0',
  `status` varchar(50) NOT NULL DEFAULT 'pending',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. Order Items
CREATE TABLE `order_items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `price` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`item_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
