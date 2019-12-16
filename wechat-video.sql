/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50712
 Source Host           : localhost:3306
 Source Schema         : wechat-video

 Target Server Type    : MySQL
 Target Server Version : 50712
 File Encoding         : 65001

 Date: 15/12/2019 13:35:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bgm
-- ----------------------------
DROP TABLE IF EXISTS `bgm`;
CREATE TABLE `bgm`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '背景音乐的作者',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '背景音乐名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '播放地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '背景音乐表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bgm
-- ----------------------------
INSERT INTO `bgm` VALUES ('1001', 'Borgeous', 'Making_Me_Feel', '/bgm/Borgeous_Making_Me_Feel.mp3');
INSERT INTO `bgm` VALUES ('1002', 'Janji', 'Horizon', '/bgm/Janji_Horizon.mp3');
INSERT INTO `bgm` VALUES ('1003', '萧全', '海草舞', '/bgm/萧全_海草舞.mp3');

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `father_comment_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `to_user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `video_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频id',
  `from_user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '留言评论的用户id',
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '留言评论内容',
  `create_time` datetime(0) NOT NULL COMMENT '留言评论时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '留言评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES ('1', NULL, NULL, '191209G2N2778280', '191205CSF50HM800', '测试留言', '2019-12-11 15:46:58');
INSERT INTO `comments` VALUES ('191211DRSZP46894', '1', '191205CSF50HM800', '191209G2N2778280', '191205CSF50HM800', '回复测试', '2019-12-11 19:18:26');
INSERT INTO `comments` VALUES ('1912129CDRWRA0DP', 'undefined', 'undefined', '19121293X470P2Y8', '191205CSF50HM800', '0.0', '2019-12-12 13:14:08');
INSERT INTO `comments` VALUES ('1912129DFSK37TR4', 'undefined', 'undefined', '191209G2N2778280', '191205CSF50HM800', '1', '2019-12-12 13:17:15');
INSERT INTO `comments` VALUES ('1912129DG5SB8G9P', 'undefined', 'undefined', '191209G2N2778280', '191205CSF50HM800', '2', '2019-12-12 13:17:18');
INSERT INTO `comments` VALUES ('1912129DGFR50G9P', 'undefined', 'undefined', '191209G2N2778280', '191205CSF50HM800', '3', '2019-12-12 13:17:20');
INSERT INTO `comments` VALUES ('1912129DGSB0XWX4', 'undefined', 'undefined', '191209G2N2778280', '191205CSF50HM800', '4', '2019-12-12 13:17:21');
INSERT INTO `comments` VALUES ('1912129DK6S282K4', 'undefined', 'undefined', '191209G2N2778280', '191205CSF50HM800', '5', '2019-12-12 13:17:31');
INSERT INTO `comments` VALUES ('1912129DKMBMXN0H', 'undefined', 'undefined', '191209G2N2778280', '191205CSF50HM800', '6', '2019-12-12 13:17:33');
INSERT INTO `comments` VALUES ('1912129FZFFCP84H', '1912129CDRWRA0DP', '191205CSF50HM800', '19121293X470P2Y8', '191205CSF50HM800', '回复一下', '2019-12-12 13:21:37');

-- ----------------------------
-- Table structure for search_records
-- ----------------------------
DROP TABLE IF EXISTS `search_records`;
CREATE TABLE `search_records`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户搜索的内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频搜索记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of search_records
-- ----------------------------
INSERT INTO `search_records` VALUES ('1', '搞笑');
INSERT INTO `search_records` VALUES ('191210CYXGTP837C', '动漫');
INSERT INTO `search_records` VALUES ('191211DPZ43PZ44H', '动漫');
INSERT INTO `search_records` VALUES ('19121294AXN42FFW', '生活');
INSERT INTO `search_records` VALUES ('1912129H8DGHPTTC', 'test');
INSERT INTO `search_records` VALUES ('1912129ZTKZK2A80', '动漫');
INSERT INTO `search_records` VALUES ('2', '生活');
INSERT INTO `search_records` VALUES ('3', '动漫');
INSERT INTO `search_records` VALUES ('4', '动漫');
INSERT INTO `search_records` VALUES ('5', '动漫');
INSERT INTO `search_records` VALUES ('6', '搞笑');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `face_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '我的头像，如果没有默认给一张',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `fans_counts` int(11) NULL DEFAULT 0 COMMENT '我的粉丝数量',
  `follow_counts` int(11) NULL DEFAULT 0 COMMENT '我的关注总数',
  `receive_like_counts` int(11) NULL DEFAULT 0 COMMENT '我收到赞/收藏的数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('191204B7GZATWKYW', 'Andersen', '4QrcOUm6Wau+VuBX8g+IPg==', '', 'Andersen', 2, 0, 3);
INSERT INTO `users` VALUES ('191204BH60GWGP4H', 'Olivia', 'ICy5YqxZB1uWSwcVLSNLcA==', '/191204BH60GWGP4H/face/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.1gFa3zTfnqR23d981f1a71112dd64e641042c62ffeb2.jpg', 'Olivia', 1, 0, 0);
INSERT INTO `users` VALUES ('191204BX5A7Y4F14', 'Atlantis', '4QrcOUm6Wau+VuBX8g+IPg==', '/191204BX5A7Y4F14/face/user.jpg', 'Atlantis', 1, 0, 1);
INSERT INTO `users` VALUES ('191205CSF50HM800', 'test', 'ICy5YqxZB1uWSwcVLSNLcA==', '/191205CSF50HM800/face/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.fuVNLcDeO9Yeb9defb4ff7543a35343218918a616efb.jpg', 'test', 1, 1, 2);
INSERT INTO `users` VALUES ('1912129RZAG1DPZC', '安徒生', 'ICy5YqxZB1uWSwcVLSNLcA==', '/1912129RZAG1DPZC/face/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.ZhOvgtJrrWvuc51918ac8f831e403463c9ff75ef1629.jpg', '安徒生', 0, 4, 3);

-- ----------------------------
-- Table structure for users_fans
-- ----------------------------
DROP TABLE IF EXISTS `users_fans`;
CREATE TABLE `users_fans`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户',
  `fan_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '粉丝',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `fan_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户粉丝关联关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_fans
-- ----------------------------
INSERT INTO `users_fans` VALUES ('191211F70FDTDB54', '191204B7GZATWKYW', '191205CSF50HM800');
INSERT INTO `users_fans` VALUES ('191212A79RA3XZTC', '191204B7GZATWKYW', '1912129RZAG1DPZC');
INSERT INTO `users_fans` VALUES ('191212A7BSRR7T2W', '191204BH60GWGP4H', '1912129RZAG1DPZC');
INSERT INTO `users_fans` VALUES ('191212A52DZ1KDD4', '191204BX5A7Y4F14', '1912129RZAG1DPZC');
INSERT INTO `users_fans` VALUES ('191212A546HM939P', '191205CSF50HM800', '1912129RZAG1DPZC');

-- ----------------------------
-- Table structure for users_like_videos
-- ----------------------------
DROP TABLE IF EXISTS `users_like_videos`;
CREATE TABLE `users_like_videos`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户',
  `video_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_video_rel`(`user_id`, `video_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户收藏/赞的视频关联关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_like_videos
-- ----------------------------
INSERT INTO `users_like_videos` VALUES ('191212935Y9R0G9P', '191205CSF50HM800', '191209BSGBAPN25P');
INSERT INTO `users_like_videos` VALUES ('191211F74ZHFWM80', '191205CSF50HM800', '191209BWGPHNDHBC');
INSERT INTO `users_like_videos` VALUES ('191212B0809W46W0', '1912129RZAG1DPZC', '191209BWGPHNDHBC');
INSERT INTO `users_like_videos` VALUES ('191212B07134ZX1P', '1912129RZAG1DPZC', '191209BWKG6YP8SW');
INSERT INTO `users_like_videos` VALUES ('191212B06AWA79P0', '1912129RZAG1DPZC', '191209G2N2778280');
INSERT INTO `users_like_videos` VALUES ('1912129ZSK0BZMW0', '1912129RZAG1DPZC', '19121293X470P2Y8');
INSERT INTO `users_like_videos` VALUES ('191212B057486ZXP', '1912129RZAG1DPZC', '1912129Y6WDWPTTC');
INSERT INTO `users_like_videos` VALUES ('191212B04ADWA0SW', '1912129RZAG1DPZC', '1912129YBPM5SSA8');
INSERT INTO `users_like_videos` VALUES ('191212B03KZSTH6W', '1912129RZAG1DPZC', '1912129YXS2MFC6W');

-- ----------------------------
-- Table structure for users_report
-- ----------------------------
DROP TABLE IF EXISTS `users_report`;
CREATE TABLE `users_report`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `deal_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '被举报用户id',
  `deal_video_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '被举报的视频信息id',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '举报类型',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '举报内容',
  `userid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '举报人的id',
  `create_date` datetime(0) NOT NULL COMMENT '举报时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '举报用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_report
-- ----------------------------
INSERT INTO `users_report` VALUES ('1912129D8CXY9CDP', '191205CSF50HM800', '19121293X470P2Y8', '其它原因', '测试举报', '191205CSF50HM800', '2019-12-12 13:16:34');

-- ----------------------------
-- Table structure for videos
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布者id',
  `bgm_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'BGM音频信息id',
  `video_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频描述',
  `video_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频存放的路径',
  `video_seconds` float(6, 2) NULL DEFAULT NULL COMMENT '视频秒数',
  `video_width` int(6) NULL DEFAULT NULL COMMENT '视频宽度',
  `video_height` int(6) NULL DEFAULT NULL COMMENT '视频高度',
  `cover_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频封面图',
  `like_counts` bigint(20) NOT NULL DEFAULT 0 COMMENT '收藏/赞的数量',
  `status` int(1) NOT NULL COMMENT '视频状态：1为发布成功;2为禁止播放，管理员操作',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of videos
-- ----------------------------
INSERT INTO `videos` VALUES ('191208D8XB7K8FNC', '191205CSF50HM800', '1002', '真机测试上传视频', '/191205CSF50HM800/video/c0cb4fbf-b48f-4e7f-bbbd-f6ef5e141f5d.mp4', 8.00, 368, 640, '/191205CSF50HM800/video/tmp_8b6be31ca369cf9b03cc481beb6a574623c480d8fbd3aaaf.jpg', 0, 1, '2019-12-08 18:39:39');
INSERT INTO `videos` VALUES ('191209BSAXFP5CSW', '191204BX5A7Y4F14', '', '动漫', '/191204BX5A7Y4F14/video/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.CULUccVBXkTS64e7f9c7577178a7e8dea5a87690ee07.mp4', 14.85, 720, 1280, '/191204BX5A7Y4F14/video/wxfa747018385a3923o6zAJszEL2IRqNBHW65JTnTG_Ew0CULUccVBXkTS64e7f9c7577178a7e8dea5a87690ee07.jpg', 0, 1, '2019-12-09 16:32:00');
INSERT INTO `videos` VALUES ('191209BSGBAPN25P', '191204BX5A7Y4F14', '', '动漫', '/191204BX5A7Y4F14/video/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.74FSWbSAc21Q5cee01537fd204978b8b51d188093c86.mp4', 12.53, 720, 1280, '/191204BX5A7Y4F14/video/wxfa747018385a3923o6zAJszEL2IRqNBHW65JTnTG_Ew074FSWbSAc21Q5cee01537fd204978b8b51d188093c86.jpg', 1, 1, '2019-12-09 16:32:29');
INSERT INTO `videos` VALUES ('191209BTGXR9FW94', '191204BH60GWGP4H', '', '可爱', '/191204BH60GWGP4H/video/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.vaKdMuwQwV0Ub451cc87d12e821548517e3814e2cb46.mp4', 6.66, 720, 1280, '/191204BH60GWGP4H/video/wxfa747018385a3923o6zAJszEL2IRqNBHW65JTnTG_Ew0vaKdMuwQwV0Ub451cc87d12e821548517e3814e2cb46.jpg', 0, 1, '2019-12-09 16:35:32');
INSERT INTO `videos` VALUES ('191209BWD0PRW5GC', '191204B7GZATWKYW', '', '采访', '/191204B7GZATWKYW/video/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.0gleo4GinQWSe1ae3374de3872e2688fe7d9331ab495.mp4', 12.33, 720, 1280, '/191204B7GZATWKYW/video/wxfa747018385a3923o6zAJszEL2IRqNBHW65JTnTG_Ew00gleo4GinQWSe1ae3374de3872e2688fe7d9331ab495.jpg', 0, 1, '2019-12-09 16:38:14');
INSERT INTO `videos` VALUES ('191209BWGPHNDHBC', '191204B7GZATWKYW', '', '搞笑', '/191204B7GZATWKYW/video/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.JCr5zTrKVonD73d1120d22add22f1a3af6af2cb46aa4.mp4', 41.33, 720, 1280, '/191204B7GZATWKYW/video/wxfa747018385a3923o6zAJszEL2IRqNBHW65JTnTG_Ew0JCr5zTrKVonD73d1120d22add22f1a3af6af2cb46aa4.jpg', 2, 1, '2019-12-09 16:38:31');
INSERT INTO `videos` VALUES ('191209BWKG6YP8SW', '191204B7GZATWKYW', '', '搞笑', '/191204B7GZATWKYW/video/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.X0ncIMraHNA1b721f73bdb9fa78665ea17edada50f15.mp4', 16.10, 720, 1280, '/191204B7GZATWKYW/video/wxfa747018385a3923o6zAJszEL2IRqNBHW65JTnTG_Ew0X0ncIMraHNA1b721f73bdb9fa78665ea17edada50f15.jpg', 1, 1, '2019-12-09 16:38:43');
INSERT INTO `videos` VALUES ('191209G2N2778280', '191205CSF50HM800', '', '生活', '/191205CSF50HM800/video/wxfa747018385a3923..DPjvvOz6s46A2467a47682efa1349af06f48463a19c7.mp4', 13.54, 720, 1280, '/191205CSF50HM800/video/wxfa747018385a3923DPjvvOz6s46A2467a47682efa1349af06f48463a19c7.jpg', 2, 1, '2019-12-09 21:09:06');
INSERT INTO `videos` VALUES ('19121293X470P2Y8', '191205CSF50HM800', '', '啦啦啦', '/191205CSF50HM800/video/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.cerh3uyPImr9d334ecfb7d0126a2ee29a6cff2d65321.mp4', 15.04, 720, 1280, '/191205CSF50HM800/video/wxfa747018385a3923o6zAJszEL2IRqNBHW65JTnTG_Ew0cerh3uyPImr9d334ecfb7d0126a2ee29a6cff2d65321.jpg', 1, 1, '2019-12-12 12:48:20');
INSERT INTO `videos` VALUES ('1912129Y6WDWPTTC', '1912129RZAG1DPZC', '', '~~~~~', '/1912129RZAG1DPZC/video/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.Oy0IKMIe1nkH204fffdad0cb755f4b7e84299adc6c2d.mp4', 15.19, 720, 1280, '/1912129RZAG1DPZC/video/wxfa747018385a3923o6zAJszEL2IRqNBHW65JTnTG_Ew0Oy0IKMIe1nkH204fffdad0cb755f4b7e84299adc6c2d.jpg', 1, 1, '2019-12-12 13:55:26');
INSERT INTO `videos` VALUES ('1912129YBPM5SSA8', '1912129RZAG1DPZC', '', '0.0', '/1912129RZAG1DPZC/video/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.6wsVsP6vxpIE916053e4125c80aef78313483998afe6.mp4', 15.91, 720, 1280, '/1912129RZAG1DPZC/video/wxfa747018385a3923o6zAJszEL2IRqNBHW65JTnTG_Ew06wsVsP6vxpIE916053e4125c80aef78313483998afe6.jpg', 1, 1, '2019-12-12 13:55:57');
INSERT INTO `videos` VALUES ('1912129YXS2MFC6W', '1912129RZAG1DPZC', '', '经典', '/1912129RZAG1DPZC/video/wxfa747018385a3923.o6zAJszEL2IRqNBHW65JTnTG_Ew0.uzISAURm2iCXafe7c96cc923fd26453372fdf671389e.mp4', 13.31, 720, 1280, '/1912129RZAG1DPZC/video/wxfa747018385a3923o6zAJszEL2IRqNBHW65JTnTG_Ew0uzISAURm2iCXafe7c96cc923fd26453372fdf671389e.jpg', 1, 1, '2019-12-12 13:57:28');

SET FOREIGN_KEY_CHECKS = 1;
