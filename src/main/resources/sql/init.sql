-- 用户表
CREATE TABLE `users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(11) NOT NULL UNIQUE,
  `password` VARCHAR(100) NOT NULL,
  `uname` VARCHAR(20) DEFAULT NULL,
  `tel` VARCHAR(11) DEFAULT NULL,
  `sex` VARCHAR(10) DEFAULT NULL,
  `bir` DATE DEFAULT NULL,
  `idcard` VARCHAR(18) DEFAULT NULL,
  `address` VARCHAR(255) DEFAULT NULL,
  `dep` VARCHAR(30) DEFAULT NULL,
  `lev` VARCHAR(10) DEFAULT NULL,
  `avatar` VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 角色表
CREATE TABLE `role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 用户角色关联表
CREATE TABLE `user_role` (
  `user_id` INT(11) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 权限表
CREATE TABLE `auth_info` (
  `auth_id` INT(11) NOT NULL AUTO_INCREMENT,
  `parent_id` INT(11) DEFAULT NULL,
  `auth_name` VARCHAR(100) NOT NULL,
  `auth_desc` VARCHAR(300) DEFAULT NULL,
  `auth_type` CHAR(1) DEFAULT NULL COMMENT '1模块/2列表/3按钮',
  `auth_url` VARCHAR(100) DEFAULT NULL,
  `auth_icon` VARCHAR(100) DEFAULT NULL,
  `auth_order` INT(11) DEFAULT NULL,
  `auth_state` CHAR(1) DEFAULT '1' COMMENT '1启用',
  `create_by` INT(11) DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `update_by` INT(11) DEFAULT NULL,
  PRIMARY KEY (`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 检查项表
CREATE TABLE `checkitem` (
  `cid` INT(11) NOT NULL AUTO_INCREMENT,
  `ccode` VARCHAR(20) DEFAULT NULL,
  `cname` VARCHAR(20) DEFAULT NULL,
  `refer_val` VARCHAR(50) DEFAULT NULL,
  `unit` VARCHAR(20) DEFAULT NULL,
  `create_date` DATE DEFAULT NULL,
  `upd_date` DATE DEFAULT NULL,
  `delete_date` DATE DEFAULT NULL,
  `option_user` VARCHAR(50) DEFAULT NULL,
  `status` VARCHAR(255) DEFAULT '1' COMMENT '1启用',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 检查组表
CREATE TABLE `checkgroup` (
  `gid` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(20) DEFAULT NULL,
  `name` VARCHAR(20) DEFAULT NULL,
  `helpCode` VARCHAR(20) DEFAULT NULL,
  `remark` VARCHAR(255) DEFAULT NULL,
  `attention` VARCHAR(255) DEFAULT NULL,
  `sex`  INT DEFAULT 0,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 检查组与检查项关联表
CREATE TABLE `checkgroup_checkitem` (
  `gid` INT NOT NULL,
  `cid` INT(11) NOT NULL,
  PRIMARY KEY (`gid`, `cid`),
  FOREIGN KEY (`gid`) REFERENCES `checkgroup` (`gid`),
  FOREIGN KEY (`cid`) REFERENCES `checkitem` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 预约表
CREATE TABLE `appointments` (
  `appointment_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `checkgroup_id` INT NOT NULL,
  `appointment_date` DATETIME NOT NULL,
  `status` VARCHAR(20) DEFAULT NULL,
  `created_time` DATETIME DEFAULT NULL,
  PRIMARY KEY (`appointment_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  FOREIGN KEY (`checkgroup_id`) REFERENCES `checkgroup` (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 体检结果表
CREATE TABLE `exam_results` (
  `result_id` INT NOT NULL AUTO_INCREMENT,
  `appointment_id` INT NOT NULL,
  `checkitem_id` INT(11) NOT NULL,
  `result_value` VARCHAR(100) DEFAULT NULL,
  `is_normal` BOOLEAN DEFAULT NULL,
  `result_desc` TEXT,
  PRIMARY KEY (`result_id`),
  FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`appointment_id`),
  FOREIGN KEY (`checkitem_id`) REFERENCES `checkitem` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入初始数据
INSERT INTO `role` (`role_name`) VALUES ('管理员'), ('普通用户');