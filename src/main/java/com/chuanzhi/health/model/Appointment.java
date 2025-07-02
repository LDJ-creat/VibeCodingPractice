package com.chuanzhi.health.model;

import java.util.Date;

/**
 * 预约信息模型
 */
public class Appointment {
    private Integer id;
    private Integer userId;
    private Integer setmealId; // 套餐ID
    private Date appointmentDate; // 预约日期
    private String status; // 预约状态: pending, completed, cancelled
    private Date createdAt;

    // This field is not in the database table, but is used for displaying data in the view.
    private String checkgroupName;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSetmealId() {
        return setmealId;
    }

    public void setSetmealId(Integer setmealId) {
        this.setmealId = setmealId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCheckgroupName() {
        return checkgroupName;
    }

    public void setCheckgroupName(String checkgroupName) {
        this.checkgroupName = checkgroupName;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", userId=" + userId +
                ", setmealId=" + setmealId +
                ", appointmentDate=" + appointmentDate +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", checkgroupName='" + checkgroupName + '\'' +
                '}';
    }
}