/*
 Navicat Premium Data Transfer

 Source Server         : FirstConnection
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : online_examination

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 15/09/2019 15:04:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`  (
  `admin_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `admin_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员名称',
  `admin_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `admin_telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `admin_email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `admin_sex` int(20) NULL DEFAULT 1 COMMENT '性别【1代表女，2代表男】',
  `admin_realname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `admin_status` int(20) NOT NULL COMMENT '状态【1代表禁用，2代表启用】',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admins
-- ----------------------------
INSERT INTO `admins` VALUES (1, 'root', '123456', '17631970825', '2376817023@qq.com', 2, '张洪霖', 2);
INSERT INTO `admins` VALUES (2, 'admin', '123456', '17349868186', '250188595@qq.com', 2, '张洪霖', 2);

-- ----------------------------
-- Table structure for historyquestion
-- ----------------------------
DROP TABLE IF EXISTS `historyquestion`;
CREATE TABLE `historyquestion`  (
  `user_id` int(20) NOT NULL COMMENT '学生ID',
  `paper_id` int(20) NOT NULL COMMENT '试卷ID',
  `question_id` int(20) NOT NULL COMMENT '题目ID',
  `user_question` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所写答案',
  `is_right` int(20) NOT NULL COMMENT '是否正确【1为错误，2为正确】',
  PRIMARY KEY (`user_id`, `paper_id`, `question_id`) USING BTREE,
  INDEX `历史答题中的试卷ID`(`paper_id`) USING BTREE,
  INDEX `历史答题中的题目ID`(`question_id`) USING BTREE,
  CONSTRAINT `历史答题中的学生ID` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `历史答题中的试卷ID` FOREIGN KEY (`paper_id`) REFERENCES `paper` (`paper_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `历史答题中的题目ID` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of historyquestion
-- ----------------------------
INSERT INTO `historyquestion` VALUES (1, 3, 1, 'B', 1);
INSERT INTO `historyquestion` VALUES (1, 3, 2, '0', 1);
INSERT INTO `historyquestion` VALUES (1, 3, 3, '1', 1);
INSERT INTO `historyquestion` VALUES (1, 3, 4, 'B', 2);
INSERT INTO `historyquestion` VALUES (1, 3, 5, 'B', 1);
INSERT INTO `historyquestion` VALUES (1, 3, 6, '1', 1);
INSERT INTO `historyquestion` VALUES (1, 3, 7, '0', 2);
INSERT INTO `historyquestion` VALUES (4, 3, 1, 'B', 1);
INSERT INTO `historyquestion` VALUES (4, 3, 2, '1', 2);
INSERT INTO `historyquestion` VALUES (4, 3, 3, '0', 2);
INSERT INTO `historyquestion` VALUES (4, 3, 4, 'B', 2);
INSERT INTO `historyquestion` VALUES (4, 3, 5, 'A', 1);
INSERT INTO `historyquestion` VALUES (4, 3, 6, '0', 2);
INSERT INTO `historyquestion` VALUES (4, 3, 7, '0', 2);
INSERT INTO `historyquestion` VALUES (4, 7, 26, 'B', 1);
INSERT INTO `historyquestion` VALUES (4, 7, 27, 'B', 1);
INSERT INTO `historyquestion` VALUES (4, 7, 30, 'B', 1);
INSERT INTO `historyquestion` VALUES (4, 7, 35, '0', 2);
INSERT INTO `historyquestion` VALUES (4, 7, 40, '1', 1);
INSERT INTO `historyquestion` VALUES (5, 3, 1, 'B', 1);
INSERT INTO `historyquestion` VALUES (5, 3, 2, '1', 2);
INSERT INTO `historyquestion` VALUES (5, 3, 3, '0', 2);
INSERT INTO `historyquestion` VALUES (5, 3, 4, 'B', 2);
INSERT INTO `historyquestion` VALUES (5, 3, 5, 'B', 1);
INSERT INTO `historyquestion` VALUES (5, 3, 6, '1', 1);
INSERT INTO `historyquestion` VALUES (5, 3, 7, '0', 2);
INSERT INTO `historyquestion` VALUES (5, 8, 7, '0', 2);
INSERT INTO `historyquestion` VALUES (5, 8, 22, 'D', 1);
INSERT INTO `historyquestion` VALUES (5, 8, 33, 'B', 1);
INSERT INTO `historyquestion` VALUES (5, 8, 36, '0', 2);
INSERT INTO `historyquestion` VALUES (5, 8, 43, 'C', 1);

-- ----------------------------
-- Table structure for paper
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper`  (
  `paper_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试卷ID',
  `paper_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '试卷名称',
  `question_totalnum` int(20) NOT NULL COMMENT '试卷题目个数',
  `question_totalgrade` int(20) NOT NULL COMMENT '试卷总分值',
  `start_time` datetime(6) NOT NULL COMMENT '试卷开始考试时间',
  `end_time` datetime(6) NOT NULL COMMENT '试卷结束时间',
  `paper_level` int(20) NOT NULL COMMENT '试卷等级【1为公开，2为私密，3为需要密码】',
  `paper_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试卷密码',
  `paper_setter_id` int(20) NOT NULL COMMENT '出题人ID',
  `subject_id` int(20) NOT NULL COMMENT '科目ID',
  `exam_duration` int(20) NOT NULL COMMENT '考试时长',
  `pass_grade` int(20) NOT NULL COMMENT '及格分数',
  PRIMARY KEY (`paper_id`) USING BTREE,
  INDEX `试卷中出题人ID`(`paper_setter_id`) USING BTREE,
  INDEX `试卷中科目ID`(`subject_id`) USING BTREE,
  CONSTRAINT `试卷中出题人ID` FOREIGN KEY (`paper_setter_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `试卷中科目ID` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paper
-- ----------------------------
INSERT INTO `paper` VALUES (1, '试卷一', 7, 90, '2019-09-01 20:00:00.000000', '2019-09-07 05:00:00.000000', 3, '123456', 3, 1, 60, 60);
INSERT INTO `paper` VALUES (2, '试卷二', 7, 90, '2019-09-06 23:00:00.000000', '2019-09-10 23:00:00.000000', 1, '', 3, 1, 60, 60);
INSERT INTO `paper` VALUES (3, '试卷三', 7, 90, '2019-09-02 13:00:00.000000', '2019-09-06 13:00:00.000000', 1, '', 3, 1, 60, 60);
INSERT INTO `paper` VALUES (4, '试卷四', 7, 90, '2019-09-03 05:00:00.000000', '2019-09-04 11:00:00.000000', 2, '', 3, 1, 60, 60);
INSERT INTO `paper` VALUES (5, '试卷五', 4, 50, '2019-09-05 23:00:00.000000', '2019-09-06 23:00:00.000000', 3, '123456', 3, 1, 60, 40);
INSERT INTO `paper` VALUES (6, '试卷六', 6, 80, '2019-09-04 23:00:00.000000', '2019-09-07 23:00:00.000000', 2, NULL, 6, 1, 60, 60);
INSERT INTO `paper` VALUES (7, '试卷七', 5, 70, '2019-09-05 23:00:00.000000', '2019-09-07 23:00:00.000000', 2, NULL, 3, 1, 5, 60);
INSERT INTO `paper` VALUES (8, '试卷八', 5, 70, '2019-09-07 23:00:00.000000', '2019-09-08 23:00:00.000000', 3, '123456', 3, 1, 60, 60);

-- ----------------------------
-- Table structure for paperdetail
-- ----------------------------
DROP TABLE IF EXISTS `paperdetail`;
CREATE TABLE `paperdetail`  (
  `paper_id` int(20) NOT NULL COMMENT '试卷ID',
  `question_id` int(20) NOT NULL COMMENT '题目ID',
  `question_no` int(20) NOT NULL COMMENT '题目序号',
  PRIMARY KEY (`paper_id`, `question_id`) USING BTREE,
  INDEX `试卷内容中的题目ID`(`question_id`) USING BTREE,
  CONSTRAINT `试卷内容中的试卷ID` FOREIGN KEY (`paper_id`) REFERENCES `paper` (`paper_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `试卷内容中的题目ID` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paperdetail
-- ----------------------------
INSERT INTO `paperdetail` VALUES (1, 1, 5);
INSERT INTO `paperdetail` VALUES (1, 2, 1);
INSERT INTO `paperdetail` VALUES (1, 3, 2);
INSERT INTO `paperdetail` VALUES (1, 4, 6);
INSERT INTO `paperdetail` VALUES (1, 5, 7);
INSERT INTO `paperdetail` VALUES (1, 6, 3);
INSERT INTO `paperdetail` VALUES (1, 7, 4);
INSERT INTO `paperdetail` VALUES (2, 1, 5);
INSERT INTO `paperdetail` VALUES (2, 2, 1);
INSERT INTO `paperdetail` VALUES (2, 3, 2);
INSERT INTO `paperdetail` VALUES (2, 4, 6);
INSERT INTO `paperdetail` VALUES (2, 5, 7);
INSERT INTO `paperdetail` VALUES (2, 6, 3);
INSERT INTO `paperdetail` VALUES (2, 7, 4);
INSERT INTO `paperdetail` VALUES (3, 1, 5);
INSERT INTO `paperdetail` VALUES (3, 2, 1);
INSERT INTO `paperdetail` VALUES (3, 3, 2);
INSERT INTO `paperdetail` VALUES (3, 4, 6);
INSERT INTO `paperdetail` VALUES (3, 5, 7);
INSERT INTO `paperdetail` VALUES (3, 6, 3);
INSERT INTO `paperdetail` VALUES (3, 7, 4);
INSERT INTO `paperdetail` VALUES (4, 1, 5);
INSERT INTO `paperdetail` VALUES (4, 2, 1);
INSERT INTO `paperdetail` VALUES (4, 3, 2);
INSERT INTO `paperdetail` VALUES (4, 4, 6);
INSERT INTO `paperdetail` VALUES (4, 5, 7);
INSERT INTO `paperdetail` VALUES (4, 6, 3);
INSERT INTO `paperdetail` VALUES (4, 7, 4);
INSERT INTO `paperdetail` VALUES (5, 5, 4);
INSERT INTO `paperdetail` VALUES (5, 28, 3);
INSERT INTO `paperdetail` VALUES (5, 30, 2);
INSERT INTO `paperdetail` VALUES (5, 36, 1);
INSERT INTO `paperdetail` VALUES (6, 1, 6);
INSERT INTO `paperdetail` VALUES (6, 7, 1);
INSERT INTO `paperdetail` VALUES (6, 26, 4);
INSERT INTO `paperdetail` VALUES (6, 27, 5);
INSERT INTO `paperdetail` VALUES (6, 30, 3);
INSERT INTO `paperdetail` VALUES (7, 26, 4);
INSERT INTO `paperdetail` VALUES (7, 27, 5);
INSERT INTO `paperdetail` VALUES (7, 30, 3);
INSERT INTO `paperdetail` VALUES (7, 35, 2);
INSERT INTO `paperdetail` VALUES (7, 40, 1);
INSERT INTO `paperdetail` VALUES (8, 7, 1);
INSERT INTO `paperdetail` VALUES (8, 22, 3);
INSERT INTO `paperdetail` VALUES (8, 33, 5);
INSERT INTO `paperdetail` VALUES (8, 36, 2);
INSERT INTO `paperdetail` VALUES (8, 43, 4);

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `question_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '题目ID',
  `question` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目',
  `right_answer` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '正确答案【0、1、A、B、C、D】',
  `option_a` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项A',
  `option_b` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项B',
  `option_c` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项C',
  `option_d` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项D',
  `analysis` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '解析',
  `grade` int(20) NOT NULL COMMENT '分值',
  `complexity` int(20) NOT NULL COMMENT '难易程度【1为简单，2为中等，3为困难】',
  `question_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关键词',
  `question_type` int(20) NOT NULL COMMENT '题目类型【1为选择题，2为判断题】',
  `subject_id` int(20) NOT NULL COMMENT '科目ID',
  PRIMARY KEY (`question_id`) USING BTREE,
  INDEX `题目中的科目ID`(`subject_id`) USING BTREE,
  CONSTRAINT `题目中的科目ID` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1, '关于摩擦力下列说法中正确的是（   ）', 'C', '相互挤压的粗糙物体间一定产生摩擦力', '只有静止的物体才可能受到静摩擦力，只有运动的物体才可能受到滑动摩擦力 ', '一个物体在另一个物体表面滑动时，一定受到摩擦力作用', '摩擦力可以是阻力也可以是动力', '这个题考查的是摩擦力', 10, 1, '摩擦力', 1, 1);
INSERT INTO `question` VALUES (2, '力是相对的', '1', '', '', '', '', '略', 5, 1, '力', 2, 1);
INSERT INTO `question` VALUES (3, '力是不相对的', '0', '', '', '', '', '略', 5, 1, '力', 2, 1);
INSERT INTO `question` VALUES (4, '对于一定量的稀薄气体 ,下列说法正确的是 ( )', 'B', '压强变大时 ,分子热运动必然变得剧烈', '保持压强不变时 ,分子热运动可能变得剧烈', '压强变大时 ,分子间的平均距离必然变小', '压强变小时 ,分子间的平均距离可能变小', '对一定量的稀薄气体 ,压强变大 ,温度不一定升高 ,因此分子热运动不一定变得剧烈 ,A\r\n项错误 ;在保持压强不变时 ,如果气体体积变大则温度升高 ,分子热运动变得剧烈 ,选项 B 正确\r\n', 15, 2, '压强', 1, 1);
INSERT INTO `question` VALUES (5, '很多相同的绝缘铜圆环沿竖直方向叠放 ,形成一很长的竖直圆筒。一条形磁铁沿圆筒的中\r\n心轴竖直放置 ,其下端与圆筒上端开口平齐。让条形磁铁从静止开始下落\r\n', 'C', '均匀增大', '先增大 ,后减小', '逐渐增大 ,趋于不变', '先增大 ,再减小 ,最后不变', '对磁铁受力分析可知 ,磁铁重力不变 ,磁场力随速率的增大而增大 ,当重力等于磁场力\r\n时,磁铁匀速下落 ,所以选 C。\r\n', 15, 2, '磁力', 1, 1);
INSERT INTO `question` VALUES (6, '光的干涉和衍射不仅说明了光具有波动性，还说明了光是横波', '0', NULL, NULL, NULL, NULL, '略', 20, 2, '光的衍射和干涉', 2, 1);
INSERT INTO `question` VALUES (7, '拍摄玻璃橱窗内的物品时，往往在镜头前加一个偏振片以增加透射光的强度', '0', NULL, NULL, NULL, NULL, '略', 20, 3, '偏振', 2, 1);
INSERT INTO `question` VALUES (8, '下列关于生物体内化合物的说法，正确的是\n( )\n', 'C', '神经递质、生长激素的化学本质都是蛋白质', '淀粉是动植物细胞内主要的储能物质', '干旱时，植物细胞内自由水与结合水的比值下降', '抗体、酶发挥作用后均迅速失去活性', '选 C。神经递质的化学本质可以是乙酰胆碱等物质，不属于蛋白质， A 项错。淀粉是植物细胞内\n主要的储能物质，动物细胞中主要的储能物质是糖原， B 项错。植物细胞内自由水与结合水的比值下降，\n', 20, 3, '生物基本信息', 1, 6);
INSERT INTO `question` VALUES (9, '线粒体是有氧呼吸的主要场所，叶绿体是光合作用的场所，原核细胞没有线粒体与叶绿\r\n体，因此不能进行有氧呼吸与光合作用。\r\n', '0', NULL, NULL, NULL, NULL, '有氧呼吸场所是真核细胞的细胞质基质和线粒体， 原核细胞的细胞质基质中； 光合作\r\n用是真核细胞的叶绿体进行、 蓝藻等原核细胞的光合片层上进行， 光合片层上含有叶绿素和藻蓝素可以进行光合作用。能不能进行某种反应， 不是因为具有不具有某种细胞器， 而是因为具有不具有那种特定的反应酶。\r\n', 20, 3, '线粒体', 2, 6);
INSERT INTO `question` VALUES (10, '水绵、蓝藻、黑藻都属于自养型的原核生物。', '0', NULL, NULL, NULL, NULL, '水绵是一低等藻类植物， 含有带状叶绿体， 可以进行光合作用。 衣藻也是低等植物可进行光合作用。 蓝藻包括蓝球藻、 念珠藻及颤球藻， 含有叶绿素和藻蓝素， 是地球上最早的进行光合作用的生物。黑藻是高等被子植物，含有叶绿体能进行光合作用。', 15, 2, '原核生物', 2, 6);
INSERT INTO `question` VALUES (11, '－3 的绝对值等于', 'B', '-3', '3', ' ±3', '小于 3', '略', 5, 1, '绝对值', 1, 2);
INSERT INTO `question` VALUES (12, '下列说法中不正确的是', 'B', '实数与数轴上的点一一对应', '不带根号的数都是有理数', '开方开不尽的数都是无理数', '实数都有立方根', '略', 10, 1, '实数', 1, 2);
INSERT INTO `question` VALUES (13, '除数是一位数时，被除数中间有两个零，商的中间至少有一个零．', '0', NULL, NULL, NULL, NULL, '此题可以举例说明：如 1008÷3 ，2007÷3，据此即可解答．\r\n解： 1008÷3=336，2007÷3=669，被除数的中间有 2 个 0，但是商的中间没有 0，原题说法错 误．\r\n故答案为： ×．\r\n', 5, 1, '除法', 2, 2);
INSERT INTO `question` VALUES (14, '不管哪一年，下半年的天数总是相等的', '1', NULL, NULL, NULL, NULL, '不论哪一年，不论平年还是闰年，只有二月份的天数是变化的，其它月份的天数 是固定的；而二月份是在上半年，那么下半年中，每个月份的天数都是不变的，然后再进一 步判断即可．', 5, 1, '闰年', 2, 2);
INSERT INTO `question` VALUES (15, '马克思主义理论体系的本质特征是', 'B', '唯物论与辩证法的统一', '科学性和革命性的统一', '理论性与实践性的统一', '阶级性和群众性的统一', '本题考核的知识点是： 马克思主义理论体系的本质特征。马克思主义哲学是革命性和科学性相统一的哲学，马克思主义从产生到发展，表现出强大的生命力，这种强大生命力的根源在于它的以实践为基础的科学性与革命性的统一。马克思主义理论体系的本质特征就是科学性与革命性的统一因此，本题的正确答案是 B选项。', 15, 2, '马克思主义', 1, 9);
INSERT INTO `question` VALUES (16, '中国古代哲学家杨泉提出： “所以立天地者，水也。 成天地者，气也。\n水土之气，升而为天” 。这是：', 'D', '客观唯心主义的观点', '主观唯心主义的观点', '形而上学的观点 ', '朴素唯物主义的观点', '本题考核的知识点是： 古代朴素唯物主义的观点。杨泉的水一元论，认为水是根本。水里的混浊部分，下沉就成为了土，水变为蒸汽，就成为了天。这种观点属于古代朴素唯物主义的观点。因此，本题的正确答案应为 D选项。', 15, 2, '古代哲学', 1, 9);
INSERT INTO `question` VALUES (17, '学校举办首届“校园艺术节” ，班级推荐具有艺术特长的小峰参加歌手大奖赛，他担\n心训练和比赛影响学习拒绝参加\n', '0', '', '', '', '', '集体利益是个人利益的基础和保障，集体利益高于个人利益。当个人利益与集体利益发生冲突时，要自觉服从集体利益，必要时应不惜牺牲个人利益。必\n须反对为追求个人利益或小团体利益而损害集体利益的思想和行为。学校生活中应处理好个人利益与集体利益的关系，以集体利益为重。只要学会科学合理地安排时间 , 统筹兼顾 , 就不会影响自己的学习。\n', 10, 1, '集体利益', 2, 9);
INSERT INTO `question` VALUES (18, '生物判断题1', '0', '', '', '', '', '这道题目是错误的', 5, 1, '生物', 2, 6);
INSERT INTO `question` VALUES (19, '生物选择题1', 'A', '选项A', '选项B', '选项C', '选项D', '这道题选A', 5, 1, '生物', 1, 6);
INSERT INTO `question` VALUES (20, '物理选择题1', 'A', '选项A', '选项B', '选项C', '选项D', '这道题选A', 20, 3, '物理', 1, 1);
INSERT INTO `question` VALUES (21, '物理选择题2', 'A', '选项A', '选项B', '选项C', '选项D', '这道题正确答案是A', 20, 3, '物理', 1, 1);
INSERT INTO `question` VALUES (22, '物理选择题3', 'A', '选项A', '选项B', '选项C', '选项D\n', '这道题的正确答案是A', 20, 3, '物理', 1, 1);
INSERT INTO `question` VALUES (23, '物理选择题4', 'A', '选项A', '选项B', '选项C', '选项D', '这道题选择A', 20, 3, '物理', 1, 1);
INSERT INTO `question` VALUES (24, '物理选择题5', 'A', '选项A', '选项B', '选项C', '选项D', '这道题的正确答案是A', 20, 3, '物理', 1, 1);
INSERT INTO `question` VALUES (25, '物理选择题6', 'A', '选项A', '选项B', '选项C', '选项D', '这道题选择A', 15, 2, '物理', 1, 1);
INSERT INTO `question` VALUES (26, '物理选择题7', 'A', '选项A', '选项B', '选项C', '选项D', '这道题的答案是A', 15, 2, '物理', 1, 1);
INSERT INTO `question` VALUES (27, '物理选择题8', 'A', '选项A', '选项B', '选项C', '选项D', '这道题的答案是A', 15, 2, '物理', 1, 1);
INSERT INTO `question` VALUES (28, '物理选择题9', 'A', '选项A', '选项B', '选项C\n', '选项D', '这道题的答案是A', 15, 2, '物理', 1, 1);
INSERT INTO `question` VALUES (29, '物理选择题10', 'A', '选项A', '选项B', '选项C', '选项D', '这道题的答案是A', 15, 2, '物理', 1, 1);
INSERT INTO `question` VALUES (30, '物理选择题11', 'A', '选项A', '选项B', '选项C', '选项D', '这道题的答案是A', 10, 1, '物理', 1, 1);
INSERT INTO `question` VALUES (31, '物理选择题12', 'A', '选项A', '选项B', '选项C', '选项D', '这道题的答案是A', 10, 1, '物理', 1, 1);
INSERT INTO `question` VALUES (32, '物理选择题13', 'A', '选项A', '选项B', '选项C', '选项D', '这道题的答案是A', 10, 1, '物理', 1, 1);
INSERT INTO `question` VALUES (33, '物理选择题14', 'A', '选项A', '选项B', '选项C', '选项D', '这道题的答案是A', 10, 1, '物理', 1, 1);
INSERT INTO `question` VALUES (34, '物理选择题15', 'A', '选项A', '选项B', '选项C', '选项D', '这道题的答案是A', 10, 1, '物理', 1, 1);
INSERT INTO `question` VALUES (35, '物理判断题1', '0', NULL, NULL, NULL, NULL, '这道题是错的', 10, 1, '物理', 2, 1);
INSERT INTO `question` VALUES (36, '物理判断题2', '0', NULL, NULL, NULL, NULL, '这道题是错的', 10, 1, '物理', 2, 1);
INSERT INTO `question` VALUES (37, '物理判断题3', '0', NULL, NULL, NULL, NULL, '这道题是错的', 15, 2, '物理', 2, 1);
INSERT INTO `question` VALUES (38, '物理判断题4', '0', NULL, NULL, NULL, NULL, '这道题是错的', 15, 2, '物理', 2, 1);
INSERT INTO `question` VALUES (39, '物理判断题5', '0', NULL, NULL, NULL, NULL, '这道题是错的', 20, 3, '物理', 2, 1);
INSERT INTO `question` VALUES (40, '物理判断题6', '0', NULL, NULL, NULL, NULL, '这道题是错的', 20, 3, '物理', 2, 1);
INSERT INTO `question` VALUES (41, '物理判断题7', '0', '', '', '', '', '这道题是错的', 10, 1, '物理', 2, 1);
INSERT INTO `question` VALUES (42, '物理判断题8', '0', '', '', '', '', '这道题是错误的', 5, 1, '物理', 2, 1);
INSERT INTO `question` VALUES (43, '物理选择题10', 'A', 'A', 'B', 'C', 'D', '答案是A', 10, 1, '物理', 1, 1);

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `paper_id` int(20) NOT NULL COMMENT '试卷ID',
  `user_id` int(20) NOT NULL COMMENT '学生ID',
  `score` int(20) NOT NULL COMMENT '成绩',
  PRIMARY KEY (`paper_id`, `user_id`) USING BTREE,
  INDEX `成绩中的学生ID`(`user_id`) USING BTREE,
  CONSTRAINT `成绩中的学生ID` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `成绩中的试卷ID` FOREIGN KEY (`paper_id`) REFERENCES `paper` (`paper_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES (3, 1, 35);
INSERT INTO `score` VALUES (3, 4, 65);
INSERT INTO `score` VALUES (3, 5, 45);
INSERT INTO `score` VALUES (7, 4, 10);
INSERT INTO `score` VALUES (8, 5, 30);

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `subject_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '科目ID',
  `subject_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '科目名称',
  PRIMARY KEY (`subject_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES (1, '物理');
INSERT INTO `subject` VALUES (2, '数学');
INSERT INTO `subject` VALUES (3, '语文');
INSERT INTO `subject` VALUES (4, '英语');
INSERT INTO `subject` VALUES (5, '化学');
INSERT INTO `subject` VALUES (6, '生物');
INSERT INTO `subject` VALUES (7, '历史');
INSERT INTO `subject` VALUES (8, '地理');
INSERT INTO `subject` VALUES (9, '政治');
INSERT INTO `subject` VALUES (11, '数据库');
INSERT INTO `subject` VALUES (12, '软件工程');
INSERT INTO `subject` VALUES (13, 'web前端开发');
INSERT INTO `subject` VALUES (14, 'javaweb开发');
INSERT INTO `subject` VALUES (15, '离散数学');
INSERT INTO `subject` VALUES (16, '体育');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `user_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `user_telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `user_email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `user_sex` int(20) NULL DEFAULT NULL COMMENT '性别【1代表女，2代表男】',
  `user_realname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `user_status` int(20) NOT NULL COMMENT '状态【1代表禁用，2代表启用】',
  `user_academy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '院校',
  `user_role` int(20) NOT NULL COMMENT '用户角色【1为老师，2为学生】',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'lisi', '123456', '12345678901', '123456789@qq.com', 2, '李四', 2, '河北师范大学', 2);
INSERT INTO `users` VALUES (2, 'zhangsan', '123456', '12345678901', '123456789@qq.com', 1, '张三', 2, '河北师范大学', 1);
INSERT INTO `users` VALUES (3, 'wangwu', '123456', '12345678901', '123456789@qq.com', 2, '王五', 2, '廊坊师范学院', 1);
INSERT INTO `users` VALUES (4, 'maliu', '123456', '12345678901', '123456789@qq.com', 2, '麻六', 2, '廊坊师范学院', 2);
INSERT INTO `users` VALUES (5, 'chuqi', '123456', '12345678901', '123456789@qq.com', 1, '褚七', 2, '北华航天工业学院', 2);
INSERT INTO `users` VALUES (6, 'chenyi', '123456', '12345678901', '123456789@qq.com', 2, '陈一', 2, '北华航天工业学院', 1);
INSERT INTO `users` VALUES (7, 'liyi', '123456', '15831908988', '234578489@qq.com', 2, '李一', 2, '北华航天工业学院', 2);
INSERT INTO `users` VALUES (8, 'zhangshuai', '123456', '15831908988', '2376817023@qq.com', 1, '张帅', 2, '北华航天工业学院', 1);
INSERT INTO `users` VALUES (9, 'liruowei', '123456', '15831908988', '2376817023@qq.com', 1, '李若薇', 2, '北华航天工业学院', 2);
INSERT INTO `users` VALUES (10, 'wangliu', '123456', '12345678910', '123456789@qq.com', 1, '王六', 2, '清华', 1);

SET FOREIGN_KEY_CHECKS = 1;
