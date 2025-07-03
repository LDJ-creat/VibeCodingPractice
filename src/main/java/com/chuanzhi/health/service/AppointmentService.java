package com.chuanzhi.health.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuanzhi.health.dao.AppointmentDao;
import com.chuanzhi.health.dao.impl.AppointmentDaoImpl;
import com.chuanzhi.health.model.Appointment;

/**
 * 预约服务
 */
public class AppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    private AppointmentDao appointmentDao;

    public AppointmentService() {
        this.appointmentDao = new AppointmentDaoImpl();
    }

    // For testing purpose
    public AppointmentService(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    /**
     * 创建预约
     * @param userId 用户ID
     * @param setmealId 套餐ID
     * @param appointmentDate 预约日期
     * @return 创建的预约对象
     */
    public Appointment createAppointment(int userId, int setmealId, Date appointmentDate) {
        logger.debug("开始创建预约 - 用户ID: {}, 套餐ID: {}, 预约日期: {}", userId, setmealId, appointmentDate);
        
        if (appointmentDate.before(new Date())) {
            logger.warn("用户{}尝试创建过期预约，预约日期: {}", userId, appointmentDate);
            return null;
        }
        
        Appointment appointment = new Appointment();
        appointment.setUserId(userId);
        appointment.setSetmealId(setmealId);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setStatus("pending"); // 初始状态为待处理
        
        int result = appointmentDao.add(appointment);
        if (result > 0) {
            logger.info("用户{}成功创建预约，套餐ID: {}, 预约日期: {}", userId, setmealId, appointmentDate);
            return appointment;
        } else {
            logger.error("用户{}创建预约失败，套餐ID: {}, 预约日期: {}", userId, setmealId, appointmentDate);
            return null;
        }
    }

    /**
     * 取消预约
     * @param appointmentId 预约ID
     * @return 是否成功
     */
    public boolean cancelAppointment(int appointmentId) {
        logger.debug("开始取消预约 - 预约ID: {}", appointmentId);
        
        int result = appointmentDao.updateStatus(appointmentId, "cancelled");
        if (result > 0) {
            logger.info("成功取消预约 - 预约ID: {}", appointmentId);
            return true;
        } else {
            logger.warn("取消预约失败 - 预约ID: {}", appointmentId);
            return false;
        }
    }

    /**
     * 获取用户的预约历史
     * @param userId 用户ID
     * @return 预约列表
     */
    public List<Appointment> findByUserId(int userId) {
        logger.debug("查询用户预约历史 - 用户ID: {}", userId);
        List<Appointment> appointments = appointmentDao.findByUserIdWithCheckgroupName(userId);
        logger.info("用户{}的预约历史查询完成，共{}条记录", userId, appointments != null ? appointments.size() : 0);
        return appointments;
    }

    /**
     * 完成预约
     * @param appointmentId 预约ID
     * @return 是否成功
     */
    public boolean completeAppointment(int appointmentId) {
        logger.debug("开始完成预约 - 预约ID: {}", appointmentId);
        
        int result = appointmentDao.updateStatus(appointmentId, "completed");
        if (result > 0) {
            logger.info("成功完成预约 - 预约ID: {}", appointmentId);
            return true;
        } else {
            logger.warn("完成预约失败 - 预约ID: {}", appointmentId);
            return false;
        }
    }
}