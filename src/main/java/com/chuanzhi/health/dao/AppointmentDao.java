package com.chuanzhi.health.dao;

import java.util.List;

import com.chuanzhi.health.model.Appointment;

/**
 * 预约DAO接口
 */
public interface AppointmentDao {
    /**
     * 新增预约
     * @param appointment 预约信息
     * @return 返回影响的行数
     */
    int add(Appointment appointment);

    /**
     * 根据用户ID查询预约列表
     * @param userId 用户ID
     * @return 预约列表
     */
    List<Appointment> findByUserId(int userId);

    /**
     * 根据用户ID查询预约列表，并包含套餐名称
     * @param userId 用户ID
     * @return 预约列表
     */
    List<Appointment> findByUserIdWithCheckgroupName(int userId);

    /**
     * 更新预约状态
     * @param id 预约ID
     * @param status 新的状态
     * @return 返回影响的行数
     */
    int updateStatus(int id, String status);
}