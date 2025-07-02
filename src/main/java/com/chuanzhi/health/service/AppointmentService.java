package com.chuanzhi.health.service;

import java.util.Date;
import java.util.List;

import com.chuanzhi.health.dao.AppointmentDao;
import com.chuanzhi.health.dao.impl.AppointmentDaoImpl;
import com.chuanzhi.health.model.Appointment;

/**
 * 预约服务
 */
public class AppointmentService {
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
        if (appointmentDate.before(new Date())) {
            System.out.println("预约日期不能早于当前日期");
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setUserId(userId);
        appointment.setSetmealId(setmealId);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setStatus("pending"); // 初始状态为待处理
        int result = appointmentDao.add(appointment);
        return result > 0 ? appointment : null;
    }

    /**
     * 取消预约
     * @param appointmentId 预约ID
     * @return 是否成功
     */
    public boolean cancelAppointment(int appointmentId) {
        return appointmentDao.updateStatus(appointmentId, "cancelled") > 0;
    }

    /**
     * 获取用户的预约历史
     * @param userId 用户ID
     * @return 预约列表
     */
    public List<Appointment> findByUserId(int userId) {
        return appointmentDao.findByUserIdWithCheckgroupName(userId);
    }

    /**
     * 完成预约
     * @param appointmentId 预约ID
     * @return 是否成功
     */
    public boolean completeAppointment(int appointmentId) {
        return appointmentDao.updateStatus(appointmentId, "completed") > 0;
    }
}