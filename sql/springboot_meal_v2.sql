-- MySQL dump 10.13  Distrib 8.3.0, for macos14 (x86_64)
--
-- Host: localhost    Database: springboot_meal_v2
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `diningcar`
--

DROP TABLE IF EXISTS `diningcar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diningcar` (
  `id` int NOT NULL AUTO_INCREMENT,
  `food_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `quantity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数量',
  `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '大小',
  `toppings` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '调料',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKqjhgrcx3ky7jsae7654f8wg33` (`food_id`) USING BTREE,
  KEY `FKdun5fk0t289o6lar9vohif1cc` (`user_id`) USING BTREE,
  CONSTRAINT `FKdun5fk0t289o6lar9vohif1cc` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKqjhgrcx3ky7jsae7654f8wg33` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diningcar`
--

LOCK TABLES `diningcar` WRITE;
/*!40000 ALTER TABLE `diningcar` DISABLE KEYS */;
INSERT INTO `diningcar` VALUES (159,40,2,'2','Medium','onion'),(162,30,2,'1','Medium',''),(163,40,2,'1','Small','');
/*!40000 ALTER TABLE `diningcar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comment` int DEFAULT NULL,
  `feature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `hits` int DEFAULT NULL,
  `material` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `seasoning` varchar(255) DEFAULT NULL,
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `price` int NOT NULL,
  `type_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK5uq6egy0y1jhbme9r7yfi0r7n` (`type_id`) USING BTREE,
  CONSTRAINT `FK5uq6egy0y1jhbme9r7yfi0r7n` FOREIGN KEY (`type_id`) REFERENCES `foodtype` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES (1,0,'暂无',1,'主料：菠菜300克，鸡蛋3个，盐、料酒、葱末、姜末、味精、香油各适量。','菠菜炒鸡蛋',NULL,'/images/jiachang/01.jpg',NULL,9,3),(2,-1,'工艺：炒    口味：清香味',0,'主料：韭菜4两（约160克），大鸡蛋3只。\r\n','韭菜炒鸡蛋',NULL,'/images/jiachang/02.jpg',NULL,8,3),(3,-1,'特色：色泽红亮、酸甜微辣，脆爽可口。',0,'主料：娃娃菜200克。','渝味辣白菜',NULL,'/images/jiachang/03.jpg',NULL,6,1),(4,-1,'特色：腰花鲜嫩，造形美观，味道醇厚，滑润不腻。',0,'主料：猪腰子200克。辅料： 冬笋片、水发木耳各50克；酱油10克、精盐3克、味精1克、绍酒20克、湿粉15克。','爆炒腰花',NULL,'/images/jiachang/04.jpg',NULL,12,1),(5,0,'暂无',0,'主料：韩国辣白菜1碗，洗米水一大碗（约500毫升），中型土豆1个，小型洋葱1个，黄豆芽1把，金针菇1把，豆腐1块，五花肉1小块（牛肉亦可）。','韩国泡菜汤',NULL,'/images/jiachang/05.jpg',NULL,16,1),(6,-1,'特色：色泽淡稚悦目，味似蟹肉，鲜香馥郁。\r\n辅料：熟火腿3片（25克）， 熟笋6片（60克），水发大香菇3朵，姜片2.5克、葱结1个\r\n',0,'主料：桂鱼一条（约重750克）。','清蒸桂鱼',NULL,'/images/jiachang/06.jpg',NULL,25,1),(7,-1,'口味：酸辣味。',0,'主料：大白菜 1/4棵（约400g）。','酸辣白菜',NULL,'/images/jiachang/07.jpg',NULL,14,1),(8,-1,'特色：特点：色泽银红，酸甜辣香。功效：润肠通便。',0,'主料：卷心菜750 克\r\n辅料：葱花3 克，干辣椒节4 克，花椒2 克。\r\n','醋溜白菜',NULL,'/images/jiachang/08.jpg',NULL,14,1),(9,-1,'暂无',0,'主料：猪肉、鸡蛋、木耳、黄瓜\r\n辅料：葱姜、盐、酱油、料酒、香油。','木须肉',NULL,'/images/jiachang/09.jpg',NULL,8,1),(10,-1,'特色：鲜嫩可口。功效：强壮身体，改善体质。',1,'暂无主料：嫩豆腐600 克，肉末150 克，酱油10 克，细盐3 克，味精3 克，黄酒5克，姜末5 克，葱花3 克，胡椒粉1 克，鲜汤300 克。','肉末豆腐',NULL,'/images/jiachang/10.jpg',NULL,7,1),(11,18,'特色：清香爽口，一清二白。',0,'暂无','小葱拌豆腐',NULL,'/images/liangcai/1.jpg',NULL,22,2),(12,-1,'暂无',0,'主料：凤爪1000克，野山椒50克。\r\n辅料：泡菜酸水一些，芹菜、花椒、味精、鸡精、盐、胡椒少许。\r\n主料：凤爪1000克，野山椒50克。\r\n辅料：泡菜酸水一些，芹菜、花椒、味精、鸡精、盐、胡椒少许。','泡椒鸡爪',NULL,'/images/liangcai/2.jpg',NULL,12,2),(13,-1,'暂无',0,'主料：鸡爪，花椒,盐（最好当然是专门的泡菜盐，如果没有也没关系,用一般的盐也可以), 八角,桂皮,少许糖,少许白酒。','泡椒凤爪',NULL,'/images/liangcai/3.jpg',NULL,12,2),(14,-1,'特色：海带是一种海洋蔬菜，含碘、藻胶酸和甘露醇等，可防治甲状腺肿大、克汀病、软骨病、佝偻病。现代药理学研究表明，吃海带可增加单核巨噬细胞活性，增强机体免疫力和抗辐射',0,'主料：海带300克、蒜茸、香油、醋、味精等。','凉拌海带丝',NULL,'/images/liangcai/4.jpg',NULL,8,2),(15,0,'特色：爽、脆可口。',0,'主料：花生米\r\n辅料：生菜、香干。','老醋花生米',NULL,'/images/liangcai/5.jpg',NULL,14,2),(16,-1,'暂无',0,'主料：黄瓜3根 （切成条长5―6厘米、宽1―1.5厘米）\r\n辅料：辣椒少许，大蒜1瓣，色拉油、盐、白醋、糖、味精、麻油少许。\r\n','凉拌黄瓜',NULL,'/images/liangcai/6.jpg',NULL,6,2),(17,-1,'特色：新疆的一道吃肉、抓饭用的菜，喜欢大口吃肉、大口喝酒的朋友，我推荐给你！',0,'主料：辣椒、洋葱、西红柿。醋、酱油、辣椒油、味精。','老虎菜',NULL,'/images/liangcai/7.jpg',NULL,7,2),(18,-1,'特色：透明、味鲜。',0,'主料：猪肉片500克，盐、味精、葱段、姜块(拍楹)花椒粒、大料瓣知适量。\r\n','肉皮冻',NULL,'/images/liangcai/8.jpg',NULL,9,2),(19,-1,'特色：爽口解腻、开胃下酒。',0,'主料：莲藕500克、酱油15克、精盐6克、味精2克、葱花3克、姜丝3克、蒜片3克。','凉拌藕片',NULL,'/images/liangcai/9.jpg',NULL,12,2),(20,15,'暂无',1,'主料：猪臀肉500克。 大蒜50克、上等酱油50克、红油10克、盐2克、冷汤50克、红糖10克、香料3克、味精1克。','蒜泥白肉',NULL,'/images/liangcai/10.jpg',NULL,20,2),(21,-1,'暂无',0,'粉条,葱末,姜末,蒜泥等','四川酸辣粉',NULL,'/images/zhushi/1.jpg',NULL,28,3),(22,-1,'香啊\r\n',0,'蛋一个，炒饭6两','蛋包饭',NULL,'/images/zhushi/2.jpg',NULL,6,3),(23,-1,'暂无',0,'鸡一只，白米,姜，香兰叶，辣椒等','海南鸡饭',NULL,'/images/zhushi/3.jpg',NULL,22,3),(24,-1,'口味：清香味。',1,'稻米100克，松花蛋50克','皮蛋粥',NULL,'/images/zhushi/4.jpg',NULL,5,3),(25,0,'口味：甜味',0,'赤小豆20克，稻米100克','红豆粥',NULL,'/images/zhushi/5.jpg',NULL,5,3),(26,-1,'饼表面软，饼底脆脆的，口感香甜',0,'玉米面粉200克 面粉100克 甜玉米粒一碗 酵母粉5克(1小勺) 白糖适量 鸡蛋。','玉米饼',NULL,'/images/zhushi/6.jpg',NULL,16,3),(27,-1,'暂无',0,'面粉.鸡蛋,菠菜,酱油,盐和水','猫耳朵',NULL,'/images/zhushi/7.jpg',NULL,5,3),(28,-1,'\r\n工艺：煮    \r\n口味：咸鲜味',0,'\r\n烙饼(标准粉)200克\r\n。辅料：黄花菜（干）50克,木耳(水发)50克,粉丝50克,青蒜10克,香菜10克,羊肉（熟）100克.\r\n','羊肉泡馍',NULL,'/images/zhushi/8.jpg',NULL,5,3),(29,-1,'暂无',0,'米饭250克，火腿30克，鸡蛋1个，豌豆20克，黄瓜、虾各25克，油30克，味精1克，葱花5克，精盐3克。','扬州炒饭',NULL,'/images/zhushi/9.jpg',NULL,7,3),(30,10,'暂无',2,'糯米粉100克、澄粉25克\r\n辅料：油35毫升、豆沙50克、水150毫升\r\n糯米粉100克、澄粉25克\r\n辅料：油35毫升、豆沙50克、水150毫升\r\n糯米粉100克、澄粉25克\r\n辅料：油35毫升.','驴打滚',NULL,'/images/zhushi/10.jpg',NULL,12,3),(31,-1,'暂无',2,'主料：甘蔗200克，马蹄150克，红枣50克，红糖50克，桂圆肉10克\r\n','甘蔗马蹄糖水',NULL,'/images/yinpin/01.jpg',NULL,8,4),(32,-1,'暂无',1,'主料：吉利丁片9片、红腰豆1瓶、淡奶油130g、白砂糖80g、鲜牛奶400g、蛋黄2个、抹茶粉4g。','抹茶慕司',NULL,'/images/yinpin/02.jpg',NULL,8,4),(33,-1,'特色：苹果有安眠养神、补中益气、消食化积之特长。瘦身苹果牛奶饮让你首先感觉到的是苹果的甜香，然后是牛奶的浓郁、香滑。临睡前喝一碗，不但可以帮助睡眠，也不会长胖。',5,'主料：苹果250g,牛奶200g。','瘦身苹果牛奶饮',NULL,'/images/yinpin/03.jpg',NULL,11,4),(34,9,'特色：利用西瓜本身的糖分使西米露变得甜美可口，一口入嘴，只说得出“好吃”两个字，水果的芬芳甜美中带有西米的软韧，最后吞下去后，还会觉得唇齿留香。',1,'主料：西米250g,西瓜200g','QQ西瓜西米露',NULL,'/images/yinpin/04.jpg',NULL,11,4),(35,-1,'特色：牛奶是早餐必不可少的一份子，无论是西式还是中式早餐，都别忘记喝牛奶。提高钙的摄取可以减少脂肪的沉积，而钙的最好来源正是牛奶。所以，就让早晨从一杯甜美可口的草莓牛奶开始吧',3,'主料：草莓口味奶拌 20g,牛奶 1杯\r\n辅料：大杏仁 适量','杏仁草莓奶拌早餐',NULL,'/images/yinpin/05.jpg',NULL,12,4),(36,-1,'暂无',8,'主料：腐竹约100克，鹌鹑蛋8只，雪耳约30克，冰糖约350克，清水6杯。','腐竹鹑蛋糖水',NULL,'/images/yinpin/06.jpg',NULL,9,4),(37,-1,'特色：奶香浓郁，茶味清香。',9,'主料：红茶、红豆汤、面粉 黄油、白糖、鸡蛋黄 炼乳、牛奶.','红豆相思茶',NULL,'/images/yinpin/07.jpg',NULL,5,4),(38,0,'暂无',14,'主料：鲜牛奶、ＱＱ糖、苹果（你爱吃那种水果就用那种，但最好跟ＱＱ糖口味一致，我觉得这样会比较好','布丁',NULL,'/images/yinpin/08.jpg',NULL,6,4),(40,-1,'暂无',21,'主料：鲜姜一大块，牛奶一袋，白糖，捣蒜缸','姜撞奶',NULL,'/images/yinpin/10.jpg',NULL,17,4);
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foodtype`
--

DROP TABLE IF EXISTS `foodtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foodtype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodtype`
--

LOCK TABLES `foodtype` WRITE;
/*!40000 ALTER TABLE `foodtype` DISABLE KEYS */;
INSERT INTO `foodtype` VALUES (1,'家常'),(2,'凉菜'),(3,'主食'),(4,'饮品');
/*!40000 ALTER TABLE `foodtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID，主键，自增',
  `delivery_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '送货方式',
  `payment_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付方式',
  `delivery_fee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '送货费',
  `address` varchar(255) DEFAULT NULL COMMENT '送货地址',
  `food_id` int DEFAULT NULL COMMENT '食物ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `quantity` int DEFAULT NULL COMMENT '点餐数量',
  `size` varchar(255) DEFAULT NULL COMMENT '分量',
  `toppings` varchar(255) DEFAULT NULL COMMENT '调料',
  `rating` varchar(255) DEFAULT NULL COMMENT '评价等级',
  `comment` varchar(255) DEFAULT NULL COMMENT '评价内容',
  `status` varchar(255) DEFAULT 'PENDING',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'express','cash','5','12312',51,3,3,'1份','纷纷','3','12321','COMPLETED'),(2,'','','5','绯闻分',36,3,12,'1份','312','3','12312','COMPLETED'),(3,'pickup','wx','5','12',36,3,12,'1份','312','3','121呃呃2','COMPLETED'),(4,'pickup','wx','5','123',36,3,12,'1份','312','3','121呃呃21212','COMPLETED'),(5,'express','wx','5','12',38,3,1,'1','1','3','121呃呃212121212121','COMPLETED'),(6,'pickup','cash','5','这似乎',51,3,1,'1','1',NULL,NULL,'COMPLETED'),(7,'express','cash','5','测试',51,3,1,'1','1',NULL,NULL,'COMPLETED'),(8,'express','wx','5','1',40,2,1,'2','3','1','不好吃','COMPLETED'),(9,'','','5','111',40,2,1,'2','3',NULL,NULL,'COMPLETED'),(10,'express','cash','5','111',38,2,1,'1','1',NULL,NULL,'COMPLETED'),(11,'pickup','cash','5','1',36,2,1,'1','1',NULL,NULL,'COMPLETED'),(12,'express','wx','5','1',40,2,1,'1','',NULL,NULL,'COMPLETED'),(13,'express','wx','5','1',40,2,1,'1','1',NULL,NULL,'COMPLETED'),(14,'express','cash','5','文星人才公寓',37,2,1,'1','1',NULL,NULL,'COMPLETED');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ident` int NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'0',1,'123','0','admin'),(2,'北京市朝阳区',0,'123','123456789','user1'),(3,'北京市朝阳区',0,'123','123456789','user2'),(4,'北京市朝阳区',0,'123','123456789','user3');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-23 23:07:14
