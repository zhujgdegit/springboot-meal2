/*
 Navicat Premium Data Transfer

 Source Server         : aliyunMySQL8
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : rm-bp1f673ql77i4cp5sio.mysql.rds.aliyuncs.com:3306
 Source Schema         : springboot_meal_v2

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 11/05/2024 21:10:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for diningcar
-- ----------------------------
DROP TABLE IF EXISTS `diningcar`;
CREATE TABLE `diningcar`  (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `food_id` int NULL DEFAULT NULL,
                              `user_id` int NULL DEFAULT NULL,
                              `quantity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数量',
                              `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '大小',
                              `toppings` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '调料',
                              PRIMARY KEY (`id`) USING BTREE,
                              INDEX `FKqjhgrcx3ky7jsae7654f8wg33`(`food_id` ASC) USING BTREE,
                              INDEX `FKdun5fk0t289o6lar9vohif1cc`(`user_id` ASC) USING BTREE,
                              CONSTRAINT `FKdun5fk0t289o6lar9vohif1cc` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                              CONSTRAINT `FKqjhgrcx3ky7jsae7654f8wg33` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 168 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of diningcar
-- ----------------------------
INSERT INTO `diningcar` VALUES (166, 40, 2, '1', 'Small', 'onion');
INSERT INTO `diningcar` VALUES (168, 37, 2, '1', 'Small', '2');

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food`  (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `comment` int NULL DEFAULT NULL,
                         `feature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `hits` int NULL DEFAULT NULL,
                         `material` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `seasoning` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `price` int NOT NULL,
                         `type_id` int NULL DEFAULT NULL,
                         `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `FK5uq6egy0y1jhbme9r7yfi0r7n`(`type_id` ASC) USING BTREE,
                         CONSTRAINT `FK5uq6egy0y1jhbme9r7yfi0r7n` FOREIGN KEY (`type_id`) REFERENCES `foodtype` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of food
-- ----------------------------
INSERT INTO `food` VALUES (1, 0, 'None for now', 1, 'Main ingredients: 300g spinach, 3 eggs, appropriate amount of salt, cooking wine, chopped green onions, ginger, monosodium glutamate, and sesame oil.', 'Stir-fried Spinach with Eggs', NULL, '/images/jiachang/01.jpg', NULL, 9, 3, 'Shelf');
INSERT INTO `food` VALUES (2, -1, 'Technique: Stir-fry   Flavor: Fresh and fragrant', 0, 'Main ingredients: 4 taels of Chinese chives (about 160g), 3 large eggs.', 'Stir-fried Chinese Chives with Eggs', NULL, '/images/jiachang/02.jpg', NULL, 8, 3, 'Shelf');
INSERT INTO `food` VALUES (3, -1, 'Features: Bright red color, sweet and sour with a slight spiciness, crispy and delicious.', 0, 'Main ingredients: 200g baby cabbage.', 'Sichuan-style Spicy Cabbage', NULL, '/images/jiachang/03.jpg', NULL, 6, 1, 'Shelf');
INSERT INTO `food` VALUES (4, -1, 'Features: Tender pork kidneys, beautiful appearance, rich flavor, smooth and not greasy.', 0, 'Main ingredients: 200g pork kidneys. Auxiliary ingredients: 50g sliced bamboo shoots, 50g soaked wood ear mushrooms; 10g soy sauce, 3g salt, 1g monosodium glutamate, 20g Shaoxing wine, 15g wet starch.', 'Stir-fried Pork Kidneys', NULL, '/images/jiachang/04.jpg', NULL, 12, 1, 'Shelf');
INSERT INTO `food` VALUES (5, 0, 'None for now', 0, 'Main ingredients: 1 bowl of Korean kimchi, a large bowl of rinsed rice water (about 500ml), 1 medium-sized potato, 1 small onion, 1 handful of mung bean sprouts, 1 bunch of enoki mushrooms, 1 piece of tofu, 1 small piece of pork belly (or beef).', 'Korean Kimchi Soup', NULL, '/images/jiachang/05.jpg', NULL, 16, 1, 'Shelf');
INSERT INTO `food` VALUES (6, -1, 'Features: Light and pleasing color, tastes like crab meat, fresh and fragrant. Auxiliary ingredients: 3 slices of cooked ham (25g), 6 slices of cooked bamboo shoots (60g), 3 water-soaked shiitake mushrooms, 2.5g ginger slices, 1 spring onion knot.', 0, 'Main ingredients: 1 Guiyu fish (about 750g).', 'Steamed Guiyu Fish', NULL, '/images/jiachang/06.jpg', NULL, 25, 1, 'Shelf');
INSERT INTO `food` VALUES (7, -1, 'Flavor: Sour and spicy.', 0, 'Main ingredients: 1/4 Chinese cabbage (about 400g).', 'Sour and Spicy Chinese Cabbage', NULL, '/images/jiachang/07.jpg', NULL, 14, 1, 'Shelf');
INSERT INTO `food` VALUES (8, -1, 'Features: Silver-red color, sweet, sour, and spicy fragrance. Efficacy: Moistens the intestines and facilitates bowel movements.', 0, 'Main ingredients: 750g cabbage. Auxiliary ingredients: 3g chopped green onions, 4g dried chili sections, 2g Sichuan peppercorns.', 'Sautéed Cabbage in Vinegar', NULL, '/images/jiachang/08.jpg', NULL, 14, 1, 'Shelf');
INSERT INTO `food` VALUES (9, -1, 'None for now', 0, 'Main ingredients: Pork, eggs, wood ear mushrooms, cucumber. Auxiliary ingredients: Shallots, ginger, salt, soy sauce, cooking wine, sesame oil.', 'Mu Shu Pork', NULL, '/images/jiachang/09.jpg', NULL, 8, 1, 'Off Shelf');
INSERT INTO `food` VALUES (10, -1, 'Features: Tender and delicious. Efficacy: Strengthens the body, improves constitution.', 1, 'No main ingredients for now: 600g tender tofu, 150g minced meat, 10g soy sauce, 3g fine salt, 3g monosodium glutamate, 5g yellow wine, 5g ginger, 3g chopped green onions, 1g white pepper, 300g fresh soup.', 'Minced Meat Tofu', NULL, '/images/jiachang/10.jpg', NULL, 7, 1, 'Sold Out');
INSERT INTO `food` VALUES (11, 18, 'Features: Refreshing and delicious.', 0, 'None for now', 'Scallion Mixed with Tofu', NULL, '/images/liangcai/1.jpg', NULL, 22, 2, 'Shelf');
INSERT INTO `food` VALUES (12, -1, 'None for now', 0, 'Main ingredients: 1000g chicken feet, 50g wild peppers. Auxiliary ingredients: Some pickled vegetable sour water, celery, peppercorns, monosodium glutamate, chicken essence, salt, peppercorns.', 'Pickled Pepper Chicken Feet', NULL, '/images/liangcai/2.jpg', NULL, 12, 2, 'Shelf');
INSERT INTO `food` VALUES (13, -1, 'None for now', 0, 'Main ingredients: Chicken feet, peppercorns, salt (it is best to use special pickling salt, but if not, regular salt will also work), star anise, cinnamon, a little sugar, a little white wine.', 'Pickled Pepper Chicken Feet', NULL, '/images/liangcai/3.jpg', NULL, 12, 2, 'Shelf');
INSERT INTO `food` VALUES (15, 0, 'Features: Refreshing and crispy.', 0, 'Main ingredients: Peanuts. Auxiliary ingredients: Lettuce, dried tofu.', 'Vinegar Peanuts', NULL, '/images/liangcai/5.jpg', NULL, 14, 2, 'Shelf');
INSERT INTO `food` VALUES (16, -1, 'None for now', 0, 'Main ingredients: 3 cucumbers (cut into strips, about 5-6cm long, 1-1.5cm wide). Auxiliary ingredients: A little chili, 1 clove of garlic, salad oil, salt, white vinegar, sugar, monosodium glutamate, sesame oil.', 'Cucumber Salad', NULL, '/images/liangcai/6.jpg', NULL, 6, 2, 'Shelf');
INSERT INTO `food` VALUES (17, -1, 'Features: A dish from Xinjiang for eating meat and grabbing rice. If you like to eat meat and drink alcohol heartily, I recommend this to you!', 0, 'Main ingredients: Chili, onions, tomatoes. Condiments: Vinegar, soy sauce, chili oil, monosodium glutamate.', 'Tiger Salad', NULL, '/images/liangcai/7.jpg', NULL, 7, 2, 'Shelf');
INSERT INTO `food` VALUES (18, -1, 'Features: Transparent and fresh taste.', 0, 'Main ingredients: 500g pork slices, appropriate amount of salt, monosodium glutamate, spring onion segments, ginger slices (patted), peppercorns, bay leaves.', 'Pork Skin Jelly', NULL, '/images/liangcai/8.jpg', NULL, 9, 2, 'Shelf');
INSERT INTO `food` VALUES (19, -1, 'Features: Refreshing and appetizing.', 0, 'Main ingredients: 500g lotus roots, 15g soy sauce, 6g salt, 2g monosodium glutamate, 3g chopped green onions, 3g shredded ginger, 3g sliced garlic.', 'Lotus Root Salad', NULL, '/images/liangcai/9.jpg', NULL, 12, 2, 'Shelf');
INSERT INTO `food` VALUES (20, 15, 'None for now', 1, 'Main ingredients: 500g pork buttocks. 50g fresh garlic, 50g premium soy sauce, 10g chili oil, 2g salt, 50g cold soup, 10g brown sugar, 3g spices, 1g monosodium glutamate.', 'Garlic Pork Slices', NULL, '/images/liangcai/10.jpg', NULL, 20, 2, 'Shelf');
INSERT INTO `food` VALUES (21, -1, 'None for now', 0, 'Ingredients: Vermicelli, chopped green onions, minced ginger, minced garlic, etc.', 'Sichuan Hot and Sour Noodles', NULL, '/images/zhushi/1.jpg', NULL, 28, 3, 'Shelf');
INSERT INTO `food` VALUES (22, -1, 'Fragrant', 0, 'One egg, 6 servings of fried rice', 'Egg Fried Rice', NULL, '/images/zhushi/2.jpg', NULL, 6, 3, 'Shelf');
INSERT INTO `food` VALUES (23, -1, 'None for now', 0, 'One chicken, rice, ginger, pandan leaves, chili, etc.', 'Hainan Chicken Rice', NULL, '/images/zhushi/3.jpg', NULL, 22, 3, 'Shelf');
INSERT INTO `food` VALUES (24, -1, 'Flavor: Fragrant taste.', 1, '100g rice, 50g century egg', 'Century Egg Congee', NULL, '/images/zhushi/4.jpg', NULL, 5, 3, 'Shelf');
INSERT INTO `food` VALUES (25, 0, 'Flavor: Sweet taste', 0, '20g red beans, 100g rice', 'Red Bean Congee', NULL, '/images/zhushi/5.jpg', NULL, 5, 3, 'Shelf');
INSERT INTO `food` VALUES (26, -1, 'The surface of the cake is soft, and the bottom of the cake is crispy and fragrant.', 0, '200g corn flour, 100g flour, a bowl of sweet corn kernels, 5g yeast (1 teaspoon), appropriate amount of sugar, eggs.', 'Corn Cake', NULL, '/images/zhushi/6.jpg', NULL, 16, 3, 'Shelf');
INSERT INTO `food` VALUES (27, -1, 'None for now', 0, 'Flour, eggs, spinach, soy sauce, salt, and water.', 'Cat Ear Dumplings', NULL, '/images/zhushi/7.jpg', NULL, 5, 3, 'Shelf');
INSERT INTO `food` VALUES (28, -1, 'Technique: Boil   Flavor: Salty and fresh', 0, '200g flatbread (standard flour), 50g daylily (dried), 50g wood ear mushrooms (soaked), 50g mung bean vermicelli, 10g garlic chives, 10g cilantro, 100g cooked lamb.', 'Lamb Pita Bread Soaked in Soup', NULL, '/images/zhushi/8.jpg', NULL, 5, 3, 'Shelf');
INSERT INTO `food` VALUES (29, -1, 'None for now', 0, '250g rice, 30g ham, 1 egg, 20g peas, 25g cucumber, 25g shrimp, 30g oil, 1g monosodium glutamate, 5g chopped green onions, 3g salt.', 'Yangzhou Fried Rice', NULL, '/images/zhushi/9.jpg', NULL, 7, 3, 'Shelf');
INSERT INTO `food` VALUES (30, 10, 'None for now', 2, 'Main ingredients: 100g glutinous rice flour, 25g starch. Auxiliary ingredients: 35ml oil, 50g red bean paste, 150ml water.', 'Glutinous Rice Roll', NULL, '/images/zhushi/10.jpg', NULL, 12, 3, 'Shelf');
INSERT INTO `food` VALUES (31, -1, 'None for now', 2, 'Main ingredients: 200g sugarcane, 150g water chestnuts, 50g red dates, 50g brown sugar, 10g dried longan.', 'Sugarcane Water Chestnut Syrup', NULL, '/images/yinpin/01.jpg', NULL, 8, 4, 'Shelf');
INSERT INTO `food` VALUES (32, -1, 'None for now', 1, 'Main ingredients: 9 gelatin sheets, 1 bottle of red beans, 130g whipped cream, 80g granulated sugar, 400g fresh milk, 2 egg yolks, 4g matcha powder.', 'Matcha Mousse', NULL, '/images/yinpin/02.jpg', NULL, 8, 4, 'Shelf');
INSERT INTO `food` VALUES (36, -1, 'None for now', 8, 'Main ingredients: About 100g tofu skin, 8 quail eggs, about 30g white fungus, about 350g rock sugar, 6 cups of water.', 'Tofu Skin Quail Egg Sugar Water', NULL, '/images/yinpin/06.jpg', NULL, 9, 4, 'Shelf');
INSERT INTO `food` VALUES (37, -1, 'Features: Rich milk fragrance, clear tea flavor.', 10, 'Main ingredients: Black tea, red bean soup, flour, butter, sugar, egg yolks, condensed milk, milk.', 'Red Bean Black Tea', NULL, '/images/yinpin/07.jpg', NULL, 5, 4, 'Shelf');
INSERT INTO `food` VALUES (38, 0, 'None for now', 16, 'Main ingredients: Fresh milk, QQ candies, apples (use your favorite fruit, but it\'s best to match the QQ candy flavor, I think it will be better this way', 'Pudding', NULL, '/images/yinpin/08.jpg', NULL, 6, 4, 'Shelf');
INSERT INTO `food` VALUES (40, -1, 'None for now', 23, 'Main ingredients: Fresh ginger, a bag of milk, white sugar, mashed garlic jar', 'Ginger Milk', 'Seasoning', '/images/yinpin/09.jpg', 'Large', 17, 4, 'Shelf');

-- ----------------------------
-- Table structure for foodtype
-- ----------------------------
DROP TABLE IF EXISTS `foodtype`;
CREATE TABLE `foodtype`  (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of foodtype
-- ----------------------------
INSERT INTO `foodtype` VALUES (1, 'home style');
INSERT INTO `foodtype` VALUES (2, 'cold dishes');
INSERT INTO `foodtype` VALUES (3, 'staple food');
INSERT INTO `foodtype` VALUES (4, 'drink');


-- ----------------------------
-- Table structure for order_reviews
-- ----------------------------
DROP TABLE IF EXISTS `order_reviews`;
CREATE TABLE `order_reviews`  (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `food_id` int NOT NULL,
                                  `diningcar_id` int NOT NULL,
                                  `parent_id` int NOT NULL DEFAULT 0,
                                  `user_id` int NOT NULL,
                                  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                  `rating` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                  `creat_time` datetime NULL DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE,
                                  CONSTRAINT `FK5uq6egy0y1jhbme9r7yfi03333` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                  CONSTRAINT `FK5uq6egy0y1jhbme9r7yf44444` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                  CONSTRAINT `FK5uq6egy0y1jhbme9r7yf00000` FOREIGN KEY (`diningcar_id`) REFERENCES `orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_reviews
-- ----------------------------
INSERT INTO `order_reviews` VALUES (1, 38, 18, 0, 2, 'test_user1', '4', 'good', '2024-05-11 13:09:26');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID，主键，自增',
                           `ordcode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '订单编号',
                           `delivery_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '送货方式',
                           `payment_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付方式',
                           `delivery_fee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '送货费',
                           `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '送货地址',
                           `food_id` int NULL DEFAULT NULL COMMENT '食物ID',
                           `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
                           `quantity` int NULL DEFAULT NULL COMMENT '点餐数量',
                           `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分量',
                           `toppings` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '调料',
                           `rating` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评价等级',
                           `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评价内容',
                           `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'PENDING',
                           `arrival_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '到店时间',
                           `delivery_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '派送时间',
                           `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                           `creat_time` datetime NULL DEFAULT NULL COMMENT '订单创建时间-下单时间',
                           PRIMARY KEY (`id`) USING BTREE,
                           CONSTRAINT `FK5uq6egy0y1jhbm` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT `FK5uq6e` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, NULL, 'express', 'cash', '5', 'home', 51, 3, 3, 'small', 'sour', '3', 'good', 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (2, NULL, '', '', '5', 'home', 36, 3, 12, '1', '312', '3', '12312', 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (3, NULL, 'pickup', 'wx', '5', '12', 36, 3, 12, '1', '312', '3', 'good', 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (4, NULL, 'pickup', 'wx', '5', '123', 36, 3, 12, '1', '312', '3', 'good', 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (5, NULL, 'express', 'wx', '5', '12', 38, 3, 1, '1', '1', '3', 'bad', 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (6, NULL, 'pickup', 'cash', '5', 'home', 51, 3, 1, '1', '1', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (7, NULL, 'express', 'cash', '5', 'home', 51, 3, 1, '1', '1', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (8, NULL, 'express', 'wx', '5', '1', 40, 2, 1, '2', '3', '4', 'general', 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (9, NULL, '', '', '5', '111', 40, 2, 1, '2', '3', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (10, NULL, 'express', 'cash', '5', '111', 38, 2, 1, '1', '1', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (11, NULL, 'pickup', 'cash', '5', '1', 36, 2, 1, '1', '1', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (12, NULL, 'express', 'wx', '5', '1', 40, 2, 1, '1', '', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (13, NULL, 'express', 'wx', '5', '1', 40, 2, 1, '1', '1', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (14, NULL, 'express', 'cash', '5', 'Wenxing Talent apartment', 37, 2, 1, '1', '1', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (15, NULL, 'express', '', '5', '[object Object]', 40, 2, 1, 'Small', '', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (16, NULL, 'express', '', '5', '[object Object]', 40, 2, 1, 'Small', '', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (17, NULL, 'express', '', '5', 'Chaoyang District of Beijing', 30, 2, 1, 'Medium', '', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (18, NULL, 'express', 'alipay', '10', 'Chaoyang District of Beijing', 38, 2, 1, 'Small', '1', NULL, NULL, 'COMPLETED', NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (19, 'ORD202405110001', 'express', 'zfb', '5', 'Chaoyang District of Beijing', 38, 2, 1, 'Small', '', NULL, NULL, 'COMPLETED', '', '22:07', 'comment', '2024-05-11 13:06:12');


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `address` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `ident` int NOT NULL,
                         `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `nackname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `birthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '0', 1, '123', '0', 'admin', NULL, NULL, NULL);
INSERT INTO `user` VALUES (2, 'Chaoyang District of Beijing', 0, '123456', '123456789', 'user1', 'Test user 1', '11 March 1992', 'Has been imitated, never surpassed');
INSERT INTO `user` VALUES (3, 'Chaoyang District of Beijing', 0, '123', '123456789', 'user2', 'Test user 2', 'October 1, 2020', 'Is an excellent person');
INSERT INTO `user` VALUES (4, 'Chaoyang District of Beijing', 0, '123', '123456789', 'user3', NULL, NULL, NULL);


SET FOREIGN_KEY_CHECKS = 1;
