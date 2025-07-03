-- 插入测试数据，用于验证体检结果显示功能

-- 确保有用户数据
INSERT IGNORE INTO `users` (`user_id`, `username`, `password`, `uname`, `tel`, `lev`) VALUES 
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXIzysEXvJZSVgLDnM3qI2LHY6e', '管理员', '13800138000', 'admin'),
(2, 'user001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXIzysEXvJZSVgLDnM3qI2LHY6e', '张三', '13800138001', 'user');

-- 插入检查项测试数据
INSERT IGNORE INTO `checkitem` (`cid`, `ccode`, `cname`, `refer_val`, `unit`, `create_date`, `option_user`, `status`) VALUES 
(1, 'HGB', '血红蛋白', '120-160', 'g/L', CURDATE(), 'admin', '1'),
(2, 'WBC', '白细胞计数', '4.0-10.0', '×10^9/L', CURDATE(), 'admin', '1'),
(3, 'PLT', '血小板计数', '100-300', '×10^9/L', CURDATE(), 'admin', '1'),
(4, 'GLU', '血糖', '3.9-6.1', 'mmol/L', CURDATE(), 'admin', '1'),
(5, 'TC', '总胆固醇', '3.1-5.7', 'mmol/L', CURDATE(), 'admin', '1');

-- 插入检查组测试数据
INSERT IGNORE INTO `checkgroup` (`gid`, `code`, `name`, `helpCode`, `remark`, `attention`, `sex`) VALUES 
(1, 'BASIC', '基础体检套餐', 'JCTH', '包含基本的血液检查项目', '检查前需要空腹8小时', 0),
(2, 'FULL', '全面体检套餐', 'QMTH', '全面的健康检查项目', '检查前需要空腹12小时', 0);

-- 插入检查组与检查项关联数据
INSERT IGNORE INTO `checkgroup_checkitem` (`gid`, `cid`) VALUES 
(1, 1), (1, 2), (1, 3),  -- 基础套餐包含血红蛋白、白细胞、血小板
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5);  -- 全面套餐包含所有检查项

-- 插入预约测试数据
INSERT IGNORE INTO `appointments` (`appointment_id`, `user_id`, `checkgroup_id`, `appointment_date`, `status`, `created_time`) VALUES 
(1, 2, 1, '2025-07-01 09:00:00', '已完成', '2025-06-25 10:00:00'),
(2, 2, 2, '2025-07-02 10:00:00', '已完成', '2025-06-26 11:00:00'),
(3, 2, 1, '2025-07-05 09:00:00', '待检查', '2025-07-01 14:00:00');

-- 插入体检结果测试数据
INSERT IGNORE INTO `exam_results` (`result_id`, `appointment_id`, `checkitem_id`, `result_value`, `is_normal`, `result_desc`) VALUES 
-- 预约1的体检结果（基础套餐）
(1, 1, 1, '145', 0, '血红蛋白水平正常'),
(2, 1, 2, '6.5', 0, '白细胞计数正常'),
(3, 1, 3, '250', 0, '血小板计数正常'),

-- 预约2的体检结果（全面套餐）
(4, 2, 1, '110', 1, '血红蛋白偏低，建议补铁'),
(5, 2, 2, '12.5', 1, '白细胞计数偏高，建议复查'),
(6, 2, 3, '180', 0, '血小板计数正常'),
(7, 2, 4, '6.8', 1, '血糖偏高，建议控制饮食'),
(8, 2, 5, '6.2', 1, '胆固醇偏高，建议低脂饮食');

COMMIT;
