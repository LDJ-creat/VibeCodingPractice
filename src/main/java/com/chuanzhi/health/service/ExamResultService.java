package com.chuanzhi.health.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuanzhi.health.dao.ExamResultDao;
import com.chuanzhi.health.dao.impl.ExamResultDaoImpl;
import com.chuanzhi.health.model.ExamResult;

/**
 * 体检结果服务
 */
public class ExamResultService {
    private static final Logger logger = LoggerFactory.getLogger(ExamResultService.class);
    private ExamResultDao examResultDao;
    private AppointmentService appointmentService;

    public ExamResultService() {
        this.examResultDao = new ExamResultDaoImpl();
        this.appointmentService = new AppointmentService();
    }

    // For testing purpose
    public ExamResultService(ExamResultDao examResultDao, AppointmentService appointmentService) {
        this.examResultDao = examResultDao;
        this.appointmentService = appointmentService;
    }

    /**
     * 录入体检结果
     * @param results 体检结果列表
     * @return 是否成功
     */
    public boolean recordExamResults(List<ExamResult> results) {
        if (results == null || results.isEmpty()) {
            return false;
        }
        // 假设所有结果都属于同一次预约
        int appointmentId = results.get(0).getAppointmentId();
        examResultDao.addBatch(results);
        // 录入结果后，更新预约状态为“已完成”
        appointmentService.completeAppointment(appointmentId);
        return true;
    }

    /**
     * 根据预约ID查询体检报告
     * @param appointmentId 预约ID
     * @return 体检结果列表
     */
    public List<ExamResult> findByAppointmentId(int appointmentId) {
        logger.debug("查询预约ID为{}的体检结果", appointmentId);
        List<ExamResult> results = examResultDao.findByAppointmentId(appointmentId);
        logger.info("查询预约ID为{}的体检结果完成，返回{}条记录", appointmentId, results != null ? results.size() : 0);
        return results;
    }
}