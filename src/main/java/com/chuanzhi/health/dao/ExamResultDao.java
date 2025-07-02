package com.chuanzhi.health.dao;

import java.util.List;

import com.chuanzhi.health.model.ExamResult;

/**
 * 体检结果DAO接口
 */
public interface ExamResultDao {
    /**
     * 批量新增体检结果
     * @param results 体检结果列表
     */
    void addBatch(List<ExamResult> results);

    /**
     * 根据预约ID查询体检结果
     * @param appointmentId 预约ID
     * @return 体检结果列表
     */
    List<ExamResult> findByAppointmentId(int appointmentId);
}